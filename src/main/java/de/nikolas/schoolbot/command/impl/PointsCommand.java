package de.nikolas.schoolbot.command.impl;

import de.nikolas.schoolbot.DiscordBot;
import de.nikolas.schoolbot.command.Command;
import de.nikolas.schoolbot.command.CommandType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class PointsCommand  implements Command {

    private DiscordBot bot;

    public PointsCommand(DiscordBot bot) {
        this.bot = bot;
        this.bot.getCommandRegisterer().register("!xp", this::execute, "Zeigt die XP an", CommandType.COMMAND);
    }

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.WHITE);
        embedBuilder.addField("LEVEL", "" + bot.getDatabase().getLevel(event.getAuthor()), true);
        embedBuilder.addField("XP", bot.getDatabase().getXp(event.getAuthor()) + "/" + bot.getDatabase().getXpTo(event.getAuthor()), true);
        embedBuilder.setAuthor("Stats von " + event.getAuthor().getName());

        event.getTextChannel().sendMessage(embedBuilder.build()).queue();
    }
}