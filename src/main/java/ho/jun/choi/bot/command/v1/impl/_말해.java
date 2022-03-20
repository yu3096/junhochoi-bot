package ho.jun.choi.bot.command.v1.impl;

import ho.jun.choi.bot.command.Command;
import ho.jun.choi.bot.storage._STORAGE_BY_GUILDID;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.io.IOException;

public class _말해 extends Command {

  public _말해(MessageReceivedEvent event) {
    super(event);
  }

  @Override
  public MessageAction process(String parameters) throws IOException {
    String adminGuildId = System.getProperty("ADMIN_GUILD_ID");
    String adminOwnerId = System.getProperty("ADMIN_OWNER_ID");
    String adminChannelId = System.getProperty("ADMIN_CHANNEL_ID");

    String msgAuthorId = event.getMessage().getAuthor().getId();
    String msgChannelId = event.getMessage().getTextChannel().getId();
    String msgGuildId = event.getMessage().getGuild().getId();

    if( adminGuildId.equals(msgGuildId)
        && adminChannelId.equals(msgChannelId)
        && adminOwnerId.equals(msgAuthorId) ){
      _STORAGE_BY_GUILDID.getInstance().getGuilds().forEach(guildId -> {
        event.getJDA().getGuildById(guildId).getTextChannelById(_STORAGE_BY_GUILDID.getInstance().get(guildId, "textChannelId")).sendMessage(parameters).queue();
      });
    }

    return null;
  }

  @Override
  public MessageAction help() {
    return null;
  }
}
