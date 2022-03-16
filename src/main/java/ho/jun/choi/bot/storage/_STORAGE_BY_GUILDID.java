package ho.jun.choi.bot.storage;

import java.util.HashMap;
import java.util.Map;

public class _STORAGE_BY_GUILDID {
  private volatile static _STORAGE_BY_GUILDID instance = null;
  private _STORAGE_BY_GUILDID() {}

  public static _STORAGE_BY_GUILDID getInstance() {
    if( null == instance ){
      instance = new _STORAGE_BY_GUILDID();
    }
    return instance;
  }

  private Map<Long, Map<String, Long>> data = new HashMap<Long, Map<String, Long>>();

  public void put(Long guildId, String k, Long v){
    if( data.containsKey(guildId) ){
      data.get(guildId).put(k, v);
    }
    else{
      Map<String, Long> tempMap = new HashMap<String, Long>();
      tempMap.put(k, v);
      data.put(guildId, tempMap);
    }
  }

  public Map<String, Long> get(Long guildId){
    return this.data.get(guildId);
  }

  public Long get(Long guildId, String k){
    return this.data.get(guildId).get(k);
  }
}
