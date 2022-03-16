package ho.jun.choi.bot.command.v1.impl;

import ho.jun.choi.bot.command.Command;
import ho.jun.choi.bot.utils.JunhoChoiProperties;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

public class _명령어 extends Command {
  private static String _COMMAND_PREFIX = (String) JunhoChoiProperties.getOrDefault("command.prefix.normal", "!준호");;
  public _명령어(MessageReceivedEvent event) {
    super(event);
  }

  @Override
  public MessageAction process(List<String> parameters) throws IOException {
    if( null == parameters || "".equals( parameters.get(1) ) ){
      List<String> resourceList = new ArrayList<String>();
      StringBuffer helpMsg = new StringBuffer();

      BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("help")));

      helpMsg.append("```최준호봇의 명령어 리스트입니다.\n");
      String resource;
      while( ( resource = br.readLine() ) != null ){
        helpMsg.append(_COMMAND_PREFIX + " " + resource + "\n");
      }
      helpMsg.append("```");

      return event.getChannel().sendMessage(helpMsg);
    }
    else{
      return help(parameters.get(1));
    }
  }

  @Override
  public MessageAction help() {
    return null;
  }
}
