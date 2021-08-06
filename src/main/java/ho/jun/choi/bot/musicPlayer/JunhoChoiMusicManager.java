package ho.jun.choi.bot.musicPlayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

public class JunhoChoiMusicManager {
  public final AudioPlayer player;
  public final JunhoChoiTrackScheduler scheduler;

  public JunhoChoiMusicManager(AudioPlayerManager playerManager) {
    this.player = playerManager.createPlayer();
    this.scheduler = new JunhoChoiTrackScheduler(player);
    player.addListener(scheduler);
  }

  public JunhoChoiAudioPlayerSendHandler getSendHandler() {
    return new JunhoChoiAudioPlayerSendHandler(player);
  }
}
