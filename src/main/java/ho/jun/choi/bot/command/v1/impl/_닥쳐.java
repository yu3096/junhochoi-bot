package ho.jun.choi.bot.command.v1.impl;

import ho.jun.choi.bot.command.MusicCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class _닥쳐 extends MusicCommand {

  public _닥쳐(MessageReceivedEvent event) {
    super(event);
  }

  @Override
  public MessageAction process(String parameters) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    controlVolume(event.getTextChannel(), 0);
    return null;
  }

  @Override
  public MessageAction help() {
    StringBuffer help = new StringBuffer();
    help.append("```준호를 닥치게만든다.\n");
    help.append(" -!준호 닥쳐");
    help.append("```");
    return event.getTextChannel().sendMessage(help);
  }
}
