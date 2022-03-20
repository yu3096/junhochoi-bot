package ho.jun.choi.bot.command;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import ho.jun.choi.bot.player.GuildMusicManager;
import ho.jun.choi.bot.storage._STORAGE_BY_GUILDID;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public abstract class MusicCommand {
  protected MessageReceivedEvent event;
  private final AudioPlayerManager playerManager;
  private final Map<Long, GuildMusicManager> musicManagers;

  public MusicCommand(MessageReceivedEvent event) {
    this.event = event;

    this.musicManagers = new HashMap<>();
    this.playerManager = new DefaultAudioPlayerManager();
    AudioSourceManagers.registerRemoteSources(playerManager);
    AudioSourceManagers.registerLocalSource(playerManager);
  }

  public abstract MessageAction process(String parameters) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;
  public abstract MessageAction help();


  private GuildMusicManager getGuildAudioPlayer(Guild guild){
    return _STORAGE_BY_GUILDID.getInstance().getGuildMusicManager(guild);
  }

  protected void loadAndPlay(final TextChannel channel, final String trackUrl){
    GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
    playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
      @Override
      public void trackLoaded(AudioTrack track) {
        channel.sendMessage("예약완료 " + track.getInfo().title).queue();
        play(channel.getGuild(), musicManager, track);
      }

      @Override
      public void playlistLoaded(AudioPlaylist playlist) {
        AudioTrack firstTrack = playlist.getSelectedTrack();

        if( null == firstTrack ){
          firstTrack = playlist.getTracks().get(0);
        }
        channel.sendMessage("다음곡은 " + firstTrack.getInfo().title + " (재생목록 첫 곡은 " + playlist.getName() + "임)").queue();
        play(channel.getGuild(), musicManager, firstTrack);
      }

      @Override
      public void noMatches() {
        channel.sendMessage("찾을 수 없음: " + trackUrl).queue();
      }

      @Override
      public void loadFailed(FriendlyException exception) {
        channel.sendMessage("로딩 실패: " + exception.getMessage()).queue();
      }
    });
  }

  private void play(Guild guild, GuildMusicManager musicManager, AudioTrack track){
    connectToFirstVoiceChannel(guild.getAudioManager());
    musicManager.scheduler.queue(track);
  }

  protected void skipTrack(TextChannel channel){
    GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
    musicManager.scheduler.nextTrack();

    channel.sendMessage("다음곡으로 가즈ㅏ").queue();
  }

  protected void controlVolume(TextChannel channel, int volume){
    if( volume >= 0 && volume <= 150){
      GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
      System.out.println(channel.getGuild());
      musicManager.player.setVolume(volume);
    }
    else{
      channel.sendMessage("볼륨 [0 ~ 150]만 가능").queue();
    }
  }

  private static void connectToFirstVoiceChannel(AudioManager audioManager){
    if( !audioManager.isConnected() ){
      for(VoiceChannel voiceChannel : audioManager.getGuild().getVoiceChannels()){
        audioManager.openAudioConnection(voiceChannel);
        break;
      }
    }
  }
}
