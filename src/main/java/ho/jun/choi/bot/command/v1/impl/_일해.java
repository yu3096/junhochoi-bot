package ho.jun.choi.bot.command.v1.impl;

import ho.jun.choi.bot.command.Command;
import ho.jun.choi.bot.daemon.callJunhoChoi.CallJunhoChoi;
import ho.jun.choi.bot.daemon.callJunhoChoi.vo.CallJunhoChoiVo;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.io.IOException;

public class _일해 extends Command {

  public _일해(MessageReceivedEvent event) {
    super(event);
  }

  @Override
  public MessageAction process(String parameters) throws IOException {
    /*
    CallJunhoChoiVo vo = new CallJunhoChoiVo();
    vo.setGuildId(event.getGuild().getIdLong());
    vo.setOwnerId(event.getGuild().getOwnerIdLong());
    vo.setTextChannelId(event.getTextChannel().getIdLong());
    vo.setHH24MI(parameters.get(1).replaceAll(":", ""));

    CallJunhoChoi.getInstance().addSchedule(vo);
    */
    return event.getTextChannel().sendMessage("2022.03.20 비 정상 동작으로 인한 수정 중");
  }

  @Override
  public MessageAction help() {
    StringBuffer help = new StringBuffer();
    /*
    help.append("```지정된 시간에 준수를 호출한다.");
    help.append(" -!준호 일해 HH24:MI");
    help.append("```");
    */
    help.append("2022.03.20 비 정상 동작으로 인한 명령어 수정 중");
    return event.getTextChannel().sendMessage(help);
  }
}
