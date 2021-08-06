package ho.jun.choi.bot.command.v1.impl;

import ho.jun.choi.bot.command.Command;
import java.io.IOException;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

public class 꺼져 extends Command {
  public 꺼져(MessageReceivedEvent event) {
    super(event);
  }

  @Override
  public MessageAction process(String parameter) throws IOException {
    TextChannel textChannel = event.getTextChannel();
    VoiceChannel connectedChannel = event.getGuild().getSelfMember().getVoiceState().getChannel();
    if(connectedChannel == null) {
      return textChannel.sendMessage("없어");
    }
    event.getGuild().getAudioManager().closeAudioConnection();
    return null;
  }
}
