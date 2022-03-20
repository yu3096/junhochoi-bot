package ho.jun.choi.bot.command.v1.impl;

import ho.jun.choi.bot.command.MusicCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class _불러봐 extends MusicCommand {

  public _불러봐(MessageReceivedEvent event) {
    super(event);
  }

  @Override
  public MessageAction process(String parameters) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    loadAndPlay(event.getTextChannel(), parameters);
    return null;
  }

  @Override
  public MessageAction help() {
    StringBuffer help = new StringBuffer();
    help.append("```준호가 노래를 부르게 한다.\n");
    help.append(" -!준호 불러봐 URL");
    help.append("```");
    return event.getTextChannel().sendMessage(help);
  }


}
