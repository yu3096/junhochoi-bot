package ho.jun.choi.bot.command.v1.impl;

import ho.jun.choi.bot.command.Command;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.Presence;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.io.IOException;
import java.util.List;

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
  public MessageAction process(List<String> parameters) throws IOException {
    Presence presence = event.getJDA().getPresence();
    StringBuffer sb = new StringBuffer();
    sb.append("나 이제 " + parameters.get(1));

    switch(parameters.get(2) ){
      case "하는중":
        presence.setActivity(Activity.playing(parameters.get(1)));
        sb.append("하러");
        break;
      case "보는중":
        presence.setActivity(Activity.watching(parameters.get(1)));
        sb.append("보러");
        break;
      case "듣는중":
        presence.setActivity(Activity.listening(parameters.get(1)));
        sb.append("들으러");
        break;
      case "경쟁중":
        presence.setActivity(Activity.competing(parameters.get(1)));
        sb.append("경쟁하러");
        break;
      case "몸캠중":
        presence.setActivity(Activity.streaming(parameters.get(1), parameters.get(3)));
        sb.append("몸캠하러");
        break;
      default:
        presence.setActivity(Activity.playing("혼란"));
        sb.append("혼란스러워하러");
    }
    sb.append("갈게");
    return event.getTextChannel().sendMessage(sb.toString());
  }
}
