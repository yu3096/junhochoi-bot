package ho.jun.choi.bot.storage;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import ho.jun.choi.bot.player.GuildMusicManager;
import net.dv8tion.jda.api.entities.Guild;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class _STORAGE_BY_GUILDID {
  private volatile static _STORAGE_BY_GUILDID instance = null;
  private final AudioPlayerManager playerManager;
  private final Map<Long, GuildMusicManager> musicManagers;

  private _STORAGE_BY_GUILDID() {
    this.musicManagers = new HashMap<>();
    this.playerManager = new DefaultAudioPlayerManager();
    AudioSourceManagers.registerRemoteSources(playerManager);
    AudioSourceManagers.registerLocalSource(playerManager);
  }

  public static _STORAGE_BY_GUILDID getInstance() {
    if( null == instance ){
      instance = new _STORAGE_BY_GUILDID();
    }
    return instance;
  }

  private Map<Long, Map<String, Long>> data = new HashMap<Long, Map<String, Long>>();
  private final Map<Long, GuildMusicManager> s = new HashMap<>();

  public void put(Long guildId, String k, Long v){
    if( data.containsKey(guildId) ){
      data.get(guildId).put(k, v);
    }
    else{
      Map<String, Long> tempMap = new HashMap<String, Long>();
      tempMap.put(k, v);
      data.put(guildId, tempMap);
    }
  }

  public Map<String, Long> get(Long guildId){
    return this.data.get(guildId);
  }
  public Long get(Long guildId, String k){
    return this.data.get(guildId).get(k);
  }
  public Set<Long> getGuilds(){
    return this.data.keySet();
  }

  public void setGuildMusicManager(Guild guild, GuildMusicManager guildMusicManager){
    this.musicManagers.put(guild.getIdLong(), guildMusicManager);
  }
  public synchronized GuildMusicManager getGuildMusicManager(Guild guild){
    GuildMusicManager musicManager = musicManagers.get(guild.getIdLong());

    if( null == musicManager ){
      System.out.println("뮤직매니저 없어서 신규 생성");
      musicManager = new GuildMusicManager(playerManager);
      musicManagers.put(guild.getIdLong(), musicManager);
    }
    else{
      System.out.println("기존 뮤직매니저 반납");
    }

    guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());
    return musicManager;
  }
}
