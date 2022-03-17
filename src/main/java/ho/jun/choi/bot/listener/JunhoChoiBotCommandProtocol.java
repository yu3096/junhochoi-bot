package ho.jun.choi.bot.listener;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import ho.jun.choi.bot.player.GuildMusicManager;
import ho.jun.choi.bot.utils.JunhoChoiProperties;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JunhoChoiBotCommandProtocol extends ListenerAdapter {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private static String _COMMAND_DIR = (String) JunhoChoiProperties.getOrDefault("command.impl.dir", "");
  private static String _COMMAND_PREFIX = (String) JunhoChoiProperties.getOrDefault("command.prefix.normal", "!준호");
  private static String _COMMAND_PREFIX_ERROR = (String) JunhoChoiProperties.getOrDefault("command.prefix.error", "!준수");

  @Override
  public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
    if( event.getAuthor().isBot() ) return;

    Message message = event.getMessage();
    String content = message.getContentRaw();

    MessageChannel channel = event.getChannel();
    if( content.startsWith(_COMMAND_PREFIX) ) {
      content = content.replaceFirst(_COMMAND_PREFIX, "").trim();

      String[] command = content.split(" ");
      try {
        if ("불러봐".equals(command[0]) && command.length == 2) {
          loadAndPlay(event.getTextChannel(), command[1]);
        }
        else if ("다음곡".equals(command[0])) {
          skipTrack(event.getTextChannel());
        }
        else {
          Class<?> clazz = Class.forName(_COMMAND_DIR + "._" + command[0]);
          Constructor<?> constructor = clazz.getConstructor(MessageReceivedEvent.class);
          Object obj = constructor.newInstance(event);

          Method method;
          MessageAction ma;
          int commandLength = command.length;
          if (commandLength > 1 && "명령어".equals(command[commandLength - 1])) { //마지막 배열이 '명령어'로 끝날 경우
            method = clazz.getSuperclass().getDeclaredMethod("help", String.class);
            ma = (MessageAction) method.invoke(obj, "_" + command[0]);
          } else {
            method = clazz.getDeclaredMethod("process", List.class);
            ma = (MessageAction) method.invoke(obj, Arrays.asList(command));
          }

          if (null != ma && !ma.isEmpty()) {
            ma.queue();
          }
        }
      }
      catch (ClassNotFoundException e) {
        e.printStackTrace();
        channel.sendMessage("병신아 명령어 틀렸다. 궁금하면 !명령어 [궁금한거]").queue();
      }
      catch (InstantiationException e) {
        e.printStackTrace();
      }
      catch (InvocationTargetException e) {
        e.printStackTrace();
      }
      catch (NoSuchMethodException e) {
        e.printStackTrace();
      }
      catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    else if( content.startsWith(_COMMAND_PREFIX_ERROR) ){
      channel.sendMessage(_COMMAND_PREFIX + "임.").queue();
    }
  }

  /**
   * 이하 Music Player 관련
   */

  private final AudioPlayerManager playerManager;
  private final Map<Long, GuildMusicManager> musicManagers;

  public JunhoChoiBotCommandProtocol(){
    this.musicManagers = new HashMap<>();
    this.playerManager = new DefaultAudioPlayerManager();
    AudioSourceManagers.registerRemoteSources(playerManager);
    AudioSourceManagers.registerLocalSource(playerManager);
  }

  private synchronized GuildMusicManager getGuildAudioPlayer(Guild guild){
    long guildId = guild.getIdLong();
    GuildMusicManager musicManager = musicManagers.get(guildId);

    if( null == musicManager ){
      musicManager = new GuildMusicManager(playerManager);
      musicManagers.put(guildId, musicManager);
    }

    guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());
    return musicManager;
  }

  private void loadAndPlay(final TextChannel channel, final String trackUrl){
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

  private void skipTrack(TextChannel channel){
    GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
    musicManager.scheduler.nextTrack();

    channel.sendMessage("다음곡으로 가즈ㅏ").queue();
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
