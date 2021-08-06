package ho.jun.choi.bot;

import ho.jun.choi.bot.listener.JunhoChoiBotProtocol;
import ho.jun.choi.bot.listener.JunhoChoiBotReady;
import ho.jun.choi.bot.utils.JunhoChoiProperties;
import java.io.IOException;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BotApplication {
  private static Logger logger = LoggerFactory.getLogger(BotApplication.class);

  public static void main(String[] args) throws LoginException {
    logger.info("---------------- 최준호 봇을 시작합니다.");
    logger.info(System.getProperty("TOKEN"));

    try {
      JunhoChoiProperties prop = new JunhoChoiProperties();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    JDA api = JDABuilder.createDefault("ODcwOTcyMTg4MTQ3MjE2NDA0.YQUiDw.Ui4sKCF6YCoeBxvDvK3ee75Nqsw")
                        .addEventListeners(new JunhoChoiBotReady())
                        .addEventListeners(new JunhoChoiBotProtocol())
                        .build();
  }
}
