package ho.jun.choi.bot.command.v1.impl;

import ho.jun.choi.bot.command.Command;
import ho.jun.choi.bot.daemon.callJunhoChoi.CallJunhoChoi;
import ho.jun.choi.bot.daemon.callJunhoChoi.vo.CallJunhoChoiVo;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class _디버그 extends Command {

  public _디버그(MessageReceivedEvent event) {
    super(event);
  }

  @Override
  public MessageAction process(List<String> parameters) throws IOException {
    Calendar cal = Calendar.getInstance(Locale.KOREA);
    SimpleDateFormat sdf = new SimpleDateFormat();

    System.out.println(cal.getTime());
    CallJunhoChoi.getInstance().getSchedule().forEach(vo -> {
      System.out.println(vo.getGuildId() + " // " + vo.getHH24MI());
    });

    return event.getTextChannel().sendMessage("로그에나오나? 확인필요");
  }
}
