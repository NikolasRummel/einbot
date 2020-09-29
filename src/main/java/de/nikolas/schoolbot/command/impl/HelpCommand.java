package de.nikolas.schoolbot.command.impl;

import de.nikolas.schoolbot.DiscordBot;
import de.nikolas.schoolbot.command.Command;
import de.nikolas.schoolbot.command.CommandType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class HelpCommand implements Command {

    private DiscordBot bot;

    public HelpCommand(DiscordBot bot) {
        this.bot = bot;
        this.bot.getCommandRegisterer().register("!help", this, "Listet alle Commands auf", CommandType.COMMAND);
    }

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.ORANGE);

        StringBuilder stringBuilder = new StringBuilder();
        for (String command: bot.getCommandRegisterer().getCommands().keySet()) {
            if(!command.equalsIgnoreCase("!commands")) {
                stringBuilder.append(command + " | " + bot.getCommandRegisterer().getDescription(bot.getCommandRegisterer().getCommand(command)) + "\n");
            }
        }

        for (String game: bot.getCommandRegisterer().getGames().keySet()) {
            stringBuilder.append(game + " | " + bot.getCommandRegisterer().getDescription(bot.getCommandRegisterer().getGame(game)) + "\n");
        }

        embedBuilder.setAuthor("Verfügbare Commands");
        embedBuilder.setDescription(stringBuilder.toString());

        event.getTextChannel().sendMessage(embedBuilder.build()).queue();
    }
}
