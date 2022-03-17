package ho.jun.choi.bot.daemon.callJunhoChoi;

import ho.jun.choi.bot.daemon.callJunhoChoi.vo.CallJunhoChoiVo;
import ho.jun.choi.bot.storage._STORAGE_BY_GUILDID;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class CallJunhoChoi implements Runnable{
  private static Logger logger = LoggerFactory.getLogger(CallJunhoChoi.class);
  private volatile static CallJunhoChoi instance = null;
  private ArrayList<CallJunhoChoiVo> voList = new ArrayList<>();
  private JDA jda;
  private CallJunhoChoi() {}
  private CallJunhoChoi(JDA jda){
    this.jda = jda;
  }

  public static CallJunhoChoi getInstance() {
    if( null == instance ){
      instance = new CallJunhoChoi();
    }
    return instance;
  }

  public static CallJunhoChoi getInstance(JDA jda) {
    if( null == instance ){
      instance = new CallJunhoChoi(jda);
    }
    return instance;
  }

  public void addSchedule( CallJunhoChoiVo vo ){
    voList.add(vo);
  }

  public ArrayList<CallJunhoChoiVo> getSchedule( ){
    return voList;
  }

  @Override
  public void run() {
    OffsetDateTime odt = OffsetDateTime.now(ZoneOffset.of("+9"));

    voList.forEach(vo -> {
      TextChannel textChannel = jda.getGuildById(vo.getGuildId()).getTextChannelById(vo.getTextChannelId());

      if( vo.getHH24MI().equals( odt.format(DateTimeFormatter.ofPattern("HHmm")) ) ) {
        textChannel.sendMessage("<@"+ vo.getOwnerId() + ">" + " 업무해").queue();
      }
    });
  }  
}
