package ho.jun.choi.bot.daemon.callJunhoChoi.vo;


public class CallJunhoChoiVo {
  private Long guildId;
  private Long ownerId;
  private Long textChannelId;
  private String HH24MI;

  public Long getGuildId() {
    return guildId;
  }

  public void setGuildId(Long guildId) {
    this.guildId = guildId;
  }

  public Long getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(Long ownerId) {
    this.ownerId = ownerId;
  }

  public Long getTextChannelId() {
    return textChannelId;
  }

  public void setTextChannelId(Long textChannelId) {
    this.textChannelId = textChannelId;
  }

  public String getHH24MI() {
    return HH24MI;
  }

  public void setHH24MI(String HH24MI) {
    this.HH24MI = HH24MI;
  }
}
