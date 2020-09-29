package de.nikolas.schoolbot.command.impl;

import de.nikolas.schoolbot.DiscordBot;
import de.nikolas.schoolbot.command.Command;
import de.nikolas.schoolbot.command.CommandType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateCommand implements Command {

    private DiscordBot bot;

    public DateCommand(DiscordBot bot) {
        this.bot = bot;
        this.bot.getCommandRegisterer().register("!date", this, "Zeigt das Datum an", CommandType.COMMAND);
    }

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM HH:mm:ss yyyy");

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.CYAN);
        embedBuilder.setTitle(formatter.format(calendar.getTime()));

        event.getTextChannel().sendMessage(embedBuilder.build()).queue();
    }

    public boolean isGeradeWoche() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("MM");
        boolean callback = false;
        String value = formatter.format(calendar.getTime());
        System.out.println(value);
        int i = calendar.get(Calendar.WEEK_OF_MONTH);

        if(value.equalsIgnoreCase("09")) {

        }

        return callback;
    }
}
