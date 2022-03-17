package ho.jun.choi.bot;

import ho.jun.choi.bot.daemon.callJunhoChoi.CallJunhoChoi;
import ho.jun.choi.bot.listener.JunhoChoiBotCommandProtocol;
import ho.jun.choi.bot.listener.JunhoChoiBotReady;
import ho.jun.choi.bot.utils.JunhoChoiProperties;
import java.io.IOException;
import java.time.ZoneId;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BotApplication {
  private static Logger logger = LoggerFactory.getLogger(BotApplication.class);

  public static void main(String[] args) throws LoginException {
    logger.info("---------------- 최준호 봇을 시작합니다.");
    logger.info(System.getProperty("discordToken"));

    try {
      JunhoChoiProperties prop = new JunhoChoiProperties();
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("UTC")));

    JDA jda = JDABuilder.createDefault(System.getProperty("discordToken"))
                        .addEventListeners(new JunhoChoiBotReady())
                        .addEventListeners(new JunhoChoiBotCommandProtocol())
                        .build();

    CallJunhoChoi callJunhoChoi = CallJunhoChoi.getInstance(jda);

    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    executorService.scheduleAtFixedRate(callJunhoChoi, 0, 60, TimeUnit.SECONDS);
  }
}
