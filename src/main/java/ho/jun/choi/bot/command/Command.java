package ho.jun.choi.bot.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

public abstract class Command {
  protected MessageReceivedEvent event;

  public Command(MessageReceivedEvent event) {
    this.event = event;
  }

  public abstract MessageAction process(List<String> parameters) throws IOException;

  public MessageAction help() throws IOException {
    return this.help(this.getClass().getSimpleName());
  }

  public MessageAction help(String command) throws IOException {
    InputStream is = this.getClass().getClassLoader().getResourceAsStream("help/" + command);
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    StringBuffer helpText = new StringBuffer();

    String line;
    while( (line = br.readLine()) != null ){
      helpText.append(line + "\r\n");
    }

    br.close();
    is.close();

    return event.getChannel().sendMessage(helpText.toString());
  }
}
