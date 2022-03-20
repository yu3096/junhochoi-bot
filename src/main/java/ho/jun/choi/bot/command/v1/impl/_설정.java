package ho.jun.choi.bot.command.v1.impl;

import ho.jun.choi.bot.command.Command;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.Presence;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.io.IOException;

public class _설정 extends Command {
  public _설정(MessageReceivedEvent event) {
    super(event);
  }

  /**
   *
   * @param parameters
   * !준호 설정 XXX 하는중
   * !준호 설정 XXX 보는중
   * !준호 설정 XXX 듣는중
   * !준호 설정 XXX 경쟁중
   * !준호 설정 XXX 스트리밍중 url
   * @return
   * @throws IOException
   */
  @Override
  public MessageAction process(String parameters) throws IOException {
    String[] arr_parameters = parameters.split("\\s");
    Presence presence = event.getJDA().getPresence();
    StringBuffer sb = new StringBuffer();
    sb.append("나 이제 " + arr_parameters[0]);

    switch( arr_parameters[1] ){
      case "하는중":
        presence.setActivity(Activity.playing(arr_parameters[0]));
        sb.append("하러");
        break;
      case "보는중":
        presence.setActivity(Activity.watching(arr_parameters[0]));
        sb.append("보러");
        break;
      case "듣는중":
        presence.setActivity(Activity.listening(arr_parameters[0]));
        sb.append("들으러");
        break;
      case "경쟁중":
        presence.setActivity(Activity.competing(arr_parameters[0]));
        sb.append("경쟁하러");
        break;
      case "몸캠중":
        presence.setActivity(Activity.streaming(arr_parameters[0], arr_parameters[2]));
        sb.append("몸캠하러");
        break;
      default:
        presence.setActivity(Activity.playing("혼란"));
        sb.append("혼란스러워하러");
    }
    sb.append("갈게");
    return event.getTextChannel().sendMessage(sb.toString());
  }

  @Override
  public MessageAction help() {
    StringBuffer help = new StringBuffer();
    help.append("```상태값을 변경한다\n");
    help.append(" -!준호 설정 [XXX] [하는중|보는중|듣는중|경쟁중]\n");
    help.append(" -!준호 설정 [XXX] [몸캠중] url");
    help.append("```");
    return event.getTextChannel().sendMessage(help);
  }
}
