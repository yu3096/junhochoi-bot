package ho.jun.choi.bot.command.v1.impl;

import ho.jun.choi.bot.command.Command;
import ho.jun.choi.bot.utils.JunhoChoiProperties;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class _명령어 extends Command {
  private static String _COMMAND_PREFIX = (String) JunhoChoiProperties.getOrDefault("command.prefix.normal", "!준호");;
  private static String _COMMAND_DIR = (String) JunhoChoiProperties.getOrDefault("command.impl.dir", "");

  public _명령어(MessageReceivedEvent event) {
    super(event);
  }

  @Override
  public MessageAction process(String parameters) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    if( null == parameters || "".equals( parameters ) ){
      StringBuffer helpMsg = new StringBuffer();

      String[] commands = new File(this.getClass().getResource("").getPath()).list();
      helpMsg.append("```최준호봇의 명령어 리스트입니다.\n");
      for(String command: commands){
        helpMsg.append(_COMMAND_PREFIX + command.replaceAll("_", " ").replaceAll(".class", "") + "\n");
      }
      helpMsg.append("```");

      return event.getChannel().sendMessage(helpMsg);
    }
    else{
      Class<?> clazz = Class.forName(_COMMAND_DIR + "._" + parameters);
      Constructor<?> constructor = clazz.getConstructor(MessageReceivedEvent.class);
      Object obj = constructor.newInstance(event);

      Method method = clazz.getDeclaredMethod("help");
      MessageAction ma = (MessageAction) method.invoke(obj);

      return ma;
    }
  }

  @Override
  public MessageAction help() {
    StringBuffer help = new StringBuffer();
    help.append("```명령어 도움말\n");
    help.append(" -!준호 명령어 [명령어]");
    help.append("```");
    return event.getTextChannel().sendMessage(help);
  }
}
