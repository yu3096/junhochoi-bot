package ho.jun.choi.bot;

import ho.jun.choi.bot.listener.JunhoChoiBotCommandProtocol;
import ho.jun.choi.bot.listener.JunhoChoiBotReady;
import ho.jun.choi.bot.storage._COMMON_PROPERTIES;
import ho.jun.choi.bot.utils.JunhoChoiProperties;
import java.io.IOException;
import java.time.ZoneId;
import java.util.TimeZone;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BotApplication {
  private static Logger logger = LoggerFactory.getLogger(BotApplication.class);

  public static void main(String[] args) throws LoginException {
    logger.info("---------------- 최준호 봇을 시작합니다.");

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

    _COMMON_PROPERTIES.getInstance().setProperties("ADMIN_CHANNEL_ID", System.getProperty("ADMIN_CHANNEL_ID"));
    _COMMON_PROPERTIES.getInstance().setProperties("ADMIN_GUILD_ID", System.getProperty("ADMIN_GUILD_ID"));
    _COMMON_PROPERTIES.getInstance().setProperties("ADMIN_OWNER_ID", System.getProperty("ADMIN_OWNER_ID"));
/*
    CallJunhoChoi callJunhoChoi = CallJunhoChoi.getInstance(jda);

    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    executorService.scheduleAtFixedRate(callJunhoChoi, 0, 60, TimeUnit.SECONDS);
 */
  }
}
