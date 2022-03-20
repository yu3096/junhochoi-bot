package ho.jun.choi.bot.command.v1.impl;

import ho.jun.choi.bot.command.MusicCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class _소리 extends MusicCommand {

  public _소리(MessageReceivedEvent event) {
    super(event);
  }

  @Override
  public MessageAction process(String parameters) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    controlVolume(event.getTextChannel(), Integer.parseInt(parameters));
    return null;
  }

  @Override
  public MessageAction help() {
    return null;
  }
}
