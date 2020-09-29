package de.nikolas.schoolbot.command.impl.games;

import de.nikolas.schoolbot.DiscordBot;
import de.nikolas.schoolbot.command.Command;
import de.nikolas.schoolbot.command.CommandType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RockPaperScissorsCommand implements Command {

    private DiscordBot bot;
    private List<String> answers;

    public RockPaperScissorsCommand(DiscordBot bot) {
        this.bot = bot;
        this.bot.getCommandRegisterer().register("!ssp", this, "Schere Stein Papier", CommandType.GAME);
        this.answers = new ArrayList<>();
        answers.add("Schere");
        answers.add("Stein");
        answers.add("Papier");
    }

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {
        if(args.length != 2) {
            event.getTextChannel().sendMessage("Falsche Benutzung! Verwende `!ssp <Schere, Stein, Papier>`.").queue();
            return;
        }

        EmbedBuilder builder = new EmbedBuilder();

        String input = args[1];
        if(input.equalsIgnoreCase("Schere") || input.equalsIgnoreCase("Stein") || input.equalsIgnoreCase("Papier") || input.equalsIgnoreCase("Brunnen")) {
            String answer = answers.get(new Random().nextInt(answers.size()));

            builder.appendDescription("**" + hasWon(input, answer) + "**!" + " Du hattest `" + input + "`, ich hatte `" + answer + "`.");
            builder.setFooter("Schere Stein Papier");
            event.getTextChannel().sendMessage(builder.build()).queue();
            return;
        }
        event.getTextChannel().sendMessage("Falsche Benutzung! Verwende `!ssp <Schere, Stein, Papier>`.").queue();
    }

    private String hasWon(String input, String answer) {
        if(input.toLowerCase().equalsIgnoreCase(answer.toLowerCase())) {
            return "Unentschieden";
        }else if(input.toLowerCase().equalsIgnoreCase("stein") && answer.equalsIgnoreCase("Papier")) {
            return "Verloren";
        }else  if(input.toLowerCase().equalsIgnoreCase("schere") && answer.equalsIgnoreCase("Stein")) {
            return "Verloren";
        } else if(input.toLowerCase().equalsIgnoreCase("papier") && answer.equalsIgnoreCase("Schere")) {
            return "Verloren";
        }else {
            return "Gewonnen";
        }
    }

}
