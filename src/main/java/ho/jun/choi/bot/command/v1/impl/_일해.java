package ho.jun.choi.bot.command.v1.impl;

import ho.jun.choi.bot.command.Command;
import ho.jun.choi.bot.daemon.callJunhoChoi.CallJunhoChoi;
import ho.jun.choi.bot.daemon.callJunhoChoi.vo.CallJunhoChoiVo;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.io.IOException;
import java.util.List;

public class _일해 extends Command {

  public _일해(MessageReceivedEvent event) {
    super(event);
  }

  @Override
  public MessageAction process(List<String> parameters) throws IOException {
    CallJunhoChoiVo vo = new CallJunhoChoiVo();
    vo.setGuildId(event.getGuild().getIdLong());
    vo.setOwnerId(event.getGuild().getOwnerIdLong());
    vo.setTextChannelId(event.getTextChannel().getIdLong());
    vo.setHH24MI(parameters.get(1).replaceAll(":", ""));

    CallJunhoChoi.getInstance().addSchdule(vo);

    return event.getTextChannel().sendMessage("업무시간 등록 완료");
  }
}
