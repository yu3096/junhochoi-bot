package ho.jun.choi.bot.utils;

import java.io.IOException;
import java.util.Properties;

public class JunhoChoiProperties {
  private static Properties prop = null;

  public JunhoChoiProperties() throws IOException {
    if( null == this.prop ){
      prop = new Properties();
      prop.load(this.getClass().getClassLoader().getResourceAsStream("JunhoChoiBot.properties"));
    }
  }

  public static Object getOrDefault(Object key, Object defaultValue){
    return prop.getOrDefault(key, defaultValue);
  }
}
