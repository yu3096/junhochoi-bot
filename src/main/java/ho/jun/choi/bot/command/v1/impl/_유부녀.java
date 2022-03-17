package ho.jun.choi.bot.command.v1.impl;

import ho.jun.choi.bot.command.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.io.IOException;
import java.util.List;

public class _유부녀 extends Command {
  public _유부녀(MessageReceivedEvent event) {
    super(event);
  }

  @Override
  public MessageAction process(List<String> parameters) throws IOException {
    return event.getTextChannel().sendMessage("너무좋아~");
  }
}
