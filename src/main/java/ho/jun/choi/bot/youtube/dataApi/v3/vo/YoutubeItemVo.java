package ho.jun.choi.bot.youtube.dataApi.v3.vo;

import com.google.api.services.youtube.model.ResourceId;

public class YoutubeItemVo {
  private ResourceId id;
  private String title;

  public YoutubeItemVo(ResourceId id, String title) {
    this.id = id;
    this.title = title;
  }

  public ResourceId getId() {
    return id;
  }

  public void setId(ResourceId id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
