package ho.jun.choi.bot.command.v1.impl;

import ho.jun.choi.bot.command.Command;
import ho.jun.choi.bot.daemon.callJunhoChoi.CallJunhoChoi;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class _디버그 extends Command {

  public _디버그(MessageReceivedEvent event) {
    super(event);
  }

  @Override
  public MessageAction process(String parameters) throws IOException {
    OffsetDateTime koreanTime = OffsetDateTime.now(ZoneOffset.of("+9"));
    OffsetDateTime UTCTime = OffsetDateTime.now();
    StringBuffer msg = new StringBuffer();
    msg.append("Korea Time: " + koreanTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    msg.append("\nUTC Time: " + UTCTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    msg.append("\nDefault TimeZon: " + TimeZone.getDefault().getDisplayName());
    msg.append("\nCall Time Setting Info: " + CallJunhoChoi.getInstance().getSchedule());

    return event.getTextChannel().sendMessage(msg);
  }

  @Override
  public MessageAction help() {
    StringBuffer help = new StringBuffer();
    help.append("```개발 테스트를 위한 임시 명령어\n");
    help.append(" -!준호 디버그");
    help.append("```");
    return event.getTextChannel().sendMessage(help);
  }
}
