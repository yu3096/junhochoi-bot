package ho.jun.choi.bot;

import ho.jun.choi.bot.listener.JunhoChoiBotProtocol;
import ho.jun.choi.bot.listener.JunhoChoiBotReady;
import ho.jun.choi.bot.utils.JunhoChoiProperties;
import java.io.IOException;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class BotApplication {
  public static void main(String[] args) throws LoginException {
    try {
      JunhoChoiProperties prop = new JunhoChoiProperties();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    JDA api = JDABuilder.createDefault("발급받은 키를 넣어주세오")
                        .addEventListeners(new JunhoChoiBotReady())
                        .addEventListeners(new JunhoChoiBotProtocol())
                        .build();
  }
}
