/*
package ho.jun.choi.bot.youtube.dataApi.v3.service.impl;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import ho.jun.choi.bot.youtube.dataApi.v3.vo.YoutubeItemListVo;
import ho.jun.choi.bot.youtube.dataApi.v3.vo.YoutubeItemVo;
import java.io.IOException;
import java.util.Collections;
import org.jetbrains.annotations.NotNull;

public class YoutubeSearchList {
  private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
  private static final JsonFactory JSON_FACTORY = new GsonFactory();
  private static YouTube youtube;
  private static String youtubeKey;

  public YoutubeSearchList(String youtubeKey) {
    this.youtubeKey = youtubeKey;
  }

  public YoutubeItemListVo get(@NotNull String q) throws IOException {
    YoutubeItemListVo itemList = new YoutubeItemListVo();

    youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
      @Override
      public void initialize(HttpRequest request) throws IOException {}
    }).setApplicationName("JunhoChoi-bot").build();

    YouTube.Search.List search = youtube.search().list(Collections.singletonList("id,snippet"));
    search.setKey(youtubeKey);
    search.setQ(q);
    search.setMaxResults(9L);

    SearchListResponse searchListResponse = search.execute();
    searchListResponse.getItems().forEach((item) -> {
      System.out.println(item.getId() + " - " + item.getSnippet().getTitle());
      itemList.addItem(new YoutubeItemVo(item.getId(), item.getSnippet().getTitle()));
    });

    return itemList;
  }
}
*/
