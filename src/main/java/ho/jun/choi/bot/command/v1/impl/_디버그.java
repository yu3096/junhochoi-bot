package ho.jun.choi.bot.command.v1.impl;

import ho.jun.choi.bot.command.Command;
import ho.jun.choi.bot.daemon.callJunhoChoi.CallJunhoChoi;
import ho.jun.choi.bot.daemon.callJunhoChoi.vo.CallJunhoChoiVo;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class _디버그 extends Command {

  public _디버그(MessageReceivedEvent event) {
    super(event);
  }

  @Override
  public MessageAction process(List<String> parameters) throws IOException {
    OffsetDateTime odt = OffsetDateTime.now(ZoneOffset.of("+9"));
    System.out.println(odt.format(DateTimeFormatter.ofPattern("HHmm")));

    return event.getTextChannel().sendMessage("로그에나오나? 확인필요");
  }
}
