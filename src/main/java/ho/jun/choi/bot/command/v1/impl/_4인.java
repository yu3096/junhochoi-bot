package ho.jun.choi.bot.command.v1.impl;

import java.io.IOException;
import java.util.List;

import ho.jun.choi.bot.command.Command;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

public class _4인 extends Command{

  public _4인(MessageReceivedEvent event) {
      super(event);
  }

  @Override
  public MessageAction process(List<String> parameters) throws IOException {
    TextChannel textChannel = event.getTextChannel();
    Guild guild = event.getGuild();
    Long ownerId = guild.getOwnerIdLong();
    
    return textChannel.sendMessage("너만 오면 5인 " + "<@"+ ownerId + ">");
  }    
}
