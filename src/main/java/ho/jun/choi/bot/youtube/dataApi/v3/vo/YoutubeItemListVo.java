package ho.jun.choi.bot.youtube.dataApi.v3.vo;

import java.util.ArrayList;
import java.util.List;

public class YoutubeItemListVo {
  private List<YoutubeItemVo> items = new ArrayList<>();
  private StringBuffer text = new StringBuffer();

  public List<YoutubeItemVo> getItems() {
    return items;
  }

  public void setItems(List<YoutubeItemVo> items) {
    this.items = items;
  }

  public void addItem(YoutubeItemVo item) {
    this.items.add(item);
  }

  @Override
  public String toString() {
    items.forEach(item -> {
      text.append(". " + item.getTitle() + "\n");
    });

    text.append("ㅊ: 취소");
    return text.toString();
  }
}
