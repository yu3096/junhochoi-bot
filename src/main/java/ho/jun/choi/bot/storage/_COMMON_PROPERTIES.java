package ho.jun.choi.bot.storage;

import java.util.HashMap;
import java.util.Map;

public class _COMMON_PROPERTIES {
  private volatile static _COMMON_PROPERTIES instance = null;
  private Map<String, String> data = new HashMap<>();

  private _COMMON_PROPERTIES() {}

  public static _COMMON_PROPERTIES getInstance() {
    if( null == instance ){
      instance = new _COMMON_PROPERTIES();
    }
    return instance;
  }

  public void setProperties(String k, String v){
    this.data.put(k, v);
  }
  public String getProperties(String k){
    return this.data.getOrDefault(k, "");
  }
}
