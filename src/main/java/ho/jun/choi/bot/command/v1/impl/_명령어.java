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
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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

      //String[] commands = new File(this.getClass().getResource("").getPath()).list();
      File jarFile = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());

      helpMsg.append("```최준호봇의 명령어 리스트입니다.\n");
      if( jarFile.isFile() ){ //Jar Run
        JarFile jar = new JarFile(jarFile);
        Enumeration<JarEntry> entries = jar.entries();
        while( entries.hasMoreElements() ){
          JarEntry jarEntrty = entries.nextElement();
          String commandPackage = _COMMAND_DIR.replaceAll("\\.", "/");

          if( !jarEntrty.isDirectory() && jarEntrty.getName().startsWith(commandPackage) ){
            helpMsg.append("\n" + jarEntrty.getName().replaceAll(commandPackage + "/", "")
                                                     .replaceFirst("\\_", "")
                                                     .replaceAll("\\.class", "")
            );
          }
        }
      }
      else{
        String[] commands = new File(this.getClass().getResource("").getPath()).list();
        for(String command: commands){
          helpMsg.append(_COMMAND_PREFIX + command.replaceAll("_", " ").replaceAll(".class", "") + "\n");
        }
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
