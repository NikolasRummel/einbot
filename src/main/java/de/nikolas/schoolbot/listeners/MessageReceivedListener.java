package de.nikolas.schoolbot.listeners;

import de.nikolas.schoolbot.DiscordBot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageReceivedListener extends ListenerAdapter {

    private DiscordBot bot;

    public MessageReceivedListener(DiscordBot bot) {
        this.bot = bot;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String input = event.getMessage().getContentDisplay();
        bot.getDatabase().handleXP(event.getAuthor(), event);

        if(!input.startsWith("!")) return;

        String game = input.split(" ")[0];
        bot.getCommandRegisterer().call(game, input.split(" "), event);
    }
}
