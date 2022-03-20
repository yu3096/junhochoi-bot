package ho.jun.choi.bot.command.v1.impl;

import ho.jun.choi.bot.command.Command;
import java.io.IOException;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

public class _들어와 extends Command {

  public _들어와(MessageReceivedEvent event) {
    super(event);
  }

  @Override
  public MessageAction process(String parameters) throws IOException {
    TextChannel textChannel = event.getTextChannel();
    if( !event.getGuild().getSelfMember().hasPermission(textChannel, Permission.VOICE_CONNECT) ) {
      return textChannel.sendMessage("VOICE_CONNECT 권한이 없습니다.");
    }

    VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
    if(connectedChannel == null) {
      return textChannel.sendMessage("뭐");
    }

    AudioManager audioManager = event.getGuild().getAudioManager();
    if(audioManager.isConnected()) { //이미 음성채널에 연결 되어있을 때
      //들어오라는 음성채널과 기존에 들어가있는 음성채널의 ID를 비교하여 다를 경우 옮긴다.
      if( audioManager.getConnectedChannel().getId().equals(connectedChannel.getId()) ){
        return textChannel.sendMessage("여기 있잖아");
      }
      // 다른 음성 채널에 들어가 있을 경우 채널을 옮긴다.
    }

    audioManager.openAudioConnection(connectedChannel); //음성 채널에 접속한다.
    return textChannel.sendMessage("왜");
  }

  @Override
  public MessageAction help() {
    StringBuffer help = new StringBuffer();
    help.append("```voice 채널에 들어간다.\n");
    help.append(" -!준호 들어와");
    help.append("```");
    return event.getTextChannel().sendMessage(help);
  }
}
