package ho.jun.choi.bot.storage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class _CHAT_KEYWORD {
  private volatile static _CHAT_KEYWORD instance = null;

  private static Set<String> keySet;
  private static Map<String, String> keywords;

  private _CHAT_KEYWORD() throws IOException {
    this.keywords = new HashMap<>();
    keywords.put("4인", "너만오면 5인큐 <@#{ownerId}>");
    keywords.put("유부녀", "유부녀 최고야");
    keySet = keywords.keySet();
  }

  public static _CHAT_KEYWORD getInstance() throws IOException {
    if( null == instance ){
      instance = new _CHAT_KEYWORD();
    }
    return instance;
  }

  public String getResponse(String key){
    return this.keywords.getOrDefault(key, "");
  }
  public Set<String> getKeywords(){
    return keySet;
  }
}
