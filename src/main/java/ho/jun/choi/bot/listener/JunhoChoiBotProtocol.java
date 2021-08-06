package ho.jun.choi.bot.listener;

import ho.jun.choi.bot.musicPlayer.JunhoChoiMusicPlayer;
import ho.jun.choi.bot.utils.JunhoChoiProperties;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JunhoChoiBotProtocol extends ListenerAdapter {
  private static String _COMMAND_DIR = (String) JunhoChoiProperties.getOrDefault("command.impl.dir", "");
  private Logger logger = LoggerFactory.getLogger(this.getClass());
  private static String _COMMAND_PREFIX = (String) JunhoChoiProperties.getOrDefault("command.prefix.normal", "!준호");;
  private static String _COMMAND_PREFIX_ERROR = (String) JunhoChoiProperties.getOrDefault("command.prefix.error", "!준수");;

  private JunhoChoiMusicPlayer musicPlayer;

  public JunhoChoiBotProtocol() {
    musicPlayer = new JunhoChoiMusicPlayer();
  }

  @Override
  public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
    if( event.getAuthor().isBot() ) return;

    Message message = event.getMessage();
    String content = message.getContentRaw();

    MessageChannel channel = event.getChannel();
    if( content.startsWith(_COMMAND_PREFIX) ) {
      content = content.replaceFirst(_COMMAND_PREFIX, "").trim();

      String regex = "^([가-힣]*)(.*)$";
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(content);

      matcher.find();

      String command = matcher.group(1);
      String parameters = matcher.group(2).trim();

      try {
        Class<?> clazz = Class.forName(_COMMAND_DIR + "." + command);
        Constructor<?> constructor = clazz.getConstructor(MessageReceivedEvent.class);
        Object obj = constructor.newInstance(event);

        Method method;
        MessageAction ma;
        if( "명령어".equals( parameters ) ){
          method = clazz.getSuperclass().getDeclaredMethod("help", String.class);
          ma = (MessageAction) method.invoke(obj, command);
        }
        else{
          method = clazz.getDeclaredMethod("process", String.class);
          ma = (MessageAction) method.invoke(obj, parameters);
        }

        ma.queue();
      }
      catch (ClassNotFoundException e) {
        e.printStackTrace();
        channel.sendMessage("병신아 명령어 틀렸다. 궁금하면 !명령어 [궁금한거]").queue();
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
  }

  @Override
  public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
    String[] command = event.getMessage().getContentRaw().split(" ", 2);
    if("~play".equals(command[0]) && command.length == 2){
      this.musicPlayer.loadAndPlay(event.getChannel(), command[1]);
    }
    super.onGuildMessageReceived(event);
  }
}
