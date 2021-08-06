package ho.jun.choi.bot.command.v1.impl;


import ho.jun.choi.bot.command.Command;
import ho.jun.choi.bot.storage._PHOTO;
import java.io.IOException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class 사진 extends Command {
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  public 사진(MessageReceivedEvent event) {
    super(event);
  }

  @Override
  public MessageAction process(String parameter) throws IOException {
    if( "명령어".equals(parameter) ){
      return super.help();
    }
    else{
      String randomPhotoUri = _PHOTO.randomAccess();
      logger.info(randomPhotoUri);
      return event.getChannel().sendMessage(randomPhotoUri);
    }
  }

}
