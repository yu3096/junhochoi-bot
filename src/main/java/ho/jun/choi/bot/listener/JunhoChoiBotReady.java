package ho.jun.choi.bot.listener;

import javax.annotation.Nonnull;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JunhoChoiBotReady implements EventListener {
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  public void onEvent(@Nonnull GenericEvent event) {
    logger.info(event.getJDA().getStatus().toString());

    if( event instanceof ReadyEvent){
      logger.info("Junho Choi Bot Ready");

    }
  }
}
