package ho.jun.choi.bot.command.v1.impl;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import ho.jun.choi.bot.command.Command;
import java.io.IOException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class 노래 extends Command {
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  public 노래(MessageReceivedEvent event) {
    super(event);
  }


  @Override
  public MessageAction process(String parameter) throws IOException {
    AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
    AudioSourceManagers.registerRemoteSources(playerManager);
    AudioSourceManagers.registerLocalSource(playerManager);
    return null;
  }
}
