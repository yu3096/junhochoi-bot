package ho.jun.choi.bot.command.v1.todo;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import ho.jun.choi.bot.command.Command;
import java.io.IOException;
import java.util.List;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class _노래 extends Command {
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  public _노래(MessageReceivedEvent event) {
    super(event);
  }


  @Override
  public MessageAction process(List<String> parameters) throws IOException {
    AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
    AudioSourceManagers.registerRemoteSources(playerManager);
    AudioSourceManagers.registerLocalSource(playerManager);
    return null;
  }
}
