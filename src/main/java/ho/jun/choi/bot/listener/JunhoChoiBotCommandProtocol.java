package ho.jun.choi.bot.listener;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import ho.jun.choi.bot.player.GuildMusicManager;
import ho.jun.choi.bot.storage._CHAT_KEYWORD;
import ho.jun.choi.bot.storage._STORAGE_BY_GUILDID;
import ho.jun.choi.bot.utils.JunhoChoiProperties;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JunhoChoiBotCommandProtocol extends ListenerAdapter {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private static String _COMMAND_DIR = (String) JunhoChoiProperties.getOrDefault("command.impl.dir", "");
  private static String _COMMAND_PREFIX = (String) JunhoChoiProperties.getOrDefault("command.prefix.normal", "!준호");
  private static String _COMMAND_PREFIX_ERROR = (String) JunhoChoiProperties.getOrDefault("command.prefix.error", "!준수");

  @Override
  public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
    if( event.getAuthor().isBot() ) return;

    Message message = event.getMessage();
    String content = message.getContentRaw();

    MessageChannel channel = event.getChannel();
    if( content.startsWith(_COMMAND_PREFIX) ) {
      content = content.replaceFirst(_COMMAND_PREFIX, "").trim();

      String[] command = content.split("[\\s\\n]", 2);
      try {
        Class<?> clazz = Class.forName(_COMMAND_DIR + "._" + command[0]);
        Constructor<?> constructor = clazz.getConstructor(MessageReceivedEvent.class);
        Object obj = constructor.newInstance(event);

        Method method = clazz.getDeclaredMethod("process", String.class);
        MessageAction ma;
        if( command.length > 1 ){
          ma = (MessageAction) method.invoke(obj, command[1]);
        }
        else{
          ma = (MessageAction) method.invoke(obj, "");
        }

        if (null != ma && !ma.isEmpty()) {
          ma.queue();
        }
      }
      catch (ClassNotFoundException e) {
        e.printStackTrace();
        channel.sendMessage("병신아 명령어 틀렸다. 궁금하면 !준호 명령어 [궁금한거]").queue();
      }
      catch (InstantiationException e) {
        e.printStackTrace();
      }
      catch (InvocationTargetException e) {
        e.printStackTrace();
      }
      catch (NoSuchMethodException e) {
        e.printStackTrace();
      }
      catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    else if( content.startsWith(_COMMAND_PREFIX_ERROR) ){
      channel.sendMessage(_COMMAND_PREFIX + "임.").queue();
    }
    else{
      try {
        String finalContent = content;
        _CHAT_KEYWORD.getInstance().getKeywords().forEach(s -> {
          if( finalContent.indexOf(s) > -1 ){
            try {
              String ownerId = event.getGuild().getOwnerId();
              String msg = _CHAT_KEYWORD.getInstance().getResponse(s).replaceAll("#\\{ownerId}", ownerId);
              channel.sendMessage(msg).queue();
            }
            catch (IOException e) {
              e.printStackTrace();
            }
          }
        });
      }
      catch (IOException e) {
        e.printStackTrace();
      }

    }
  }

  @Override
  public void onGuildVoiceJoin(@NotNull GuildVoiceJoinEvent event) {
    if( event.getMember().isOwner() ){
      event.getJDA().getTextChannelById(_STORAGE_BY_GUILDID.getInstance().get(event.getGuild().getIdLong(), "textChannelId")).sendMessage("<@284576455390658560> 헬스 몇시?").queue();
    }
  }

  @Override
  public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
    if( event.getMember().isOwner() ){
      event.getJDA().getTextChannelById(_STORAGE_BY_GUILDID.getInstance().get(event.getGuild().getIdLong(), "textChannelId")).sendMessage("잘자요.").queue();
    }
  }
}