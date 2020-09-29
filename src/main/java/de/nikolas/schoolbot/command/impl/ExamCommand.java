package de.nikolas.schoolbot.command.impl;

import de.nikolas.schoolbot.DiscordBot;
import de.nikolas.schoolbot.command.Command;
import de.nikolas.schoolbot.command.CommandType;
import de.nikolas.schoolbot.exam.Exam;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;

public class ExamCommand implements Command {

    private DiscordBot bot;

    public ExamCommand(DiscordBot bot) {
        this.bot = bot;
        this.bot.getCommandRegisterer().register("!arbeit", this, "Arbeiten anzeigen, erstellen, editieren & löschen", CommandType.COMMAND);
    }

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.BLACK);

        if(args.length <= 1) {
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("!arbeiten list\n");
            stringBuilder.append("!arbeiten add <fach> <datum>\n");
            stringBuilder.append("!arbeiten remove <id>\n");
            stringBuilder.append("!arbeiten edit <id> <neues datum>\n");

            embedBuilder.setAuthor("Verfügbare Commands");
            embedBuilder.setDescription(stringBuilder.toString());
            event.getTextChannel().sendMessage(embedBuilder.build()).queue();

        } else {
            if(args[1].equalsIgnoreCase("list")) {
                StringBuilder stringBuilder = new StringBuilder();
                bot.getDatabase().getExams().forEach(exam -> {
                    stringBuilder.append(exam.getSubject() + " am " + exam.getDate() + " | id: "  + exam.getId());
                });
                embedBuilder.setAuthor("Klausuren");
                embedBuilder.setDescription(stringBuilder.toString());
                event.getTextChannel().sendMessage(embedBuilder.build()).queue();

                return;
            }
            if(args[1].equalsIgnoreCase("add")) {
                Exam exam = new Exam(bot.getDatabase().getNextId(), args[2], args[3]);
                bot.getDatabase().createExam(exam);
                event.getTextChannel().sendMessage("add").queue();
                return;
            }
            if(args[1].equalsIgnoreCase("remove")) {
                event.getTextChannel().sendMessage("remove").queue();
                return;
            }
            if(args[1].equalsIgnoreCase("edit")) {
                event.getTextChannel().sendMessage("edit").queue();
                return;
            }
        }
    }
}
