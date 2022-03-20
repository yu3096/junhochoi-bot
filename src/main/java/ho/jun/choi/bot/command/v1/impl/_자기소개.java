package ho.jun.choi.bot.command.v1.impl;

import ho.jun.choi.bot.command.Command;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.io.IOException;

public class _자기소개 extends Command {

  public _자기소개(MessageReceivedEvent event) {
    super(event);
  }

  @Override
  public MessageAction process(String parameters) throws IOException {
    TextChannel textChannel = event.getTextChannel();

    return textChannel.sendMessage("```Ver. Alpha\n -dateOfChange: 2022.03.20```");
  }

  @Override
  public MessageAction help() {
    StringBuffer help = new StringBuffer();
    help.append("```버전확인용 명령어\n");
    help.append(" -!준호 자기소개");
    help.append("```");
    return event.getTextChannel().sendMessage(help);
  }
}
