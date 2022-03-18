package ho.jun.choi.bot.listener;

import javax.annotation.Nonnull;

import ho.jun.choi.bot.storage._STORAGE_BY_GUILDID;
import ho.jun.choi.bot.utils.JunhoChoiProperties;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JunhoChoiBotReady implements EventListener {
  private Logger logger = LoggerFactory.getLogger(this.getClass());
  private static String _CHAT_DIR = (String) JunhoChoiProperties.getOrDefault("command.chat.dir", "");
  @Override
  public void onEvent(@Nonnull GenericEvent event) {
    logger.info(event.getJDA().getStatus().toString());

    if( event instanceof ReadyEvent){
      logger.info("Junho Choi Bot Ready");

      event.getJDA().getGuilds().forEach(guild -> {
        Long guildId = guild.getIdLong();
        Long ownerId = event.getJDA().getGuildById(guildId).getOwnerIdLong();
        Long textChannelId = event.getJDA().getGuildById(guildId).getTextChannels().get(0).getIdLong();

        _STORAGE_BY_GUILDID.getInstance().put(guildId, "ownerId", ownerId);
        _STORAGE_BY_GUILDID.getInstance().put(guildId, "textChannelId", textChannelId);

        logger.info("Junho Choi Bot Ready Guild Info {}",_STORAGE_BY_GUILDID.getInstance().get(guildId));
      });
    }
    else if( event instanceof GuildJoinEvent){
      Long guildId = ((GuildJoinEvent) event).getGuild().getIdLong();
      Long ownerId = ((GuildJoinEvent) event).getGuild().getOwnerIdLong();
      Long textChannelId = ((GuildJoinEvent) event).getGuild().getTextChannels().get(0).getIdLong();

      _STORAGE_BY_GUILDID.getInstance().put(guildId, "ownerId", ownerId);
      _STORAGE_BY_GUILDID.getInstance().put(guildId, "textChannelId", textChannelId);

      logger.info("Junho Choi Bot Join Guild Info: {}", _STORAGE_BY_GUILDID.getInstance().get(guildId));
    }
  }
}
