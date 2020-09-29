package de.nikolas.schoolbot;

import de.nikolas.schoolbot.command.CommandRegisterer;
import de.nikolas.schoolbot.command.impl.*;
import de.nikolas.schoolbot.command.impl.games.RockPaperScissorsCommand;
import de.nikolas.schoolbot.exam.Exam;
import de.nikolas.schoolbot.listeners.MessageReceivedListener;
import de.nikolas.schoolbot.mysql.Database;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class DiscordBot {

    private CommandRegisterer commandRegisterer;
    private Database database;

    public DiscordBot() {
        try {
            this.commandRegisterer = new CommandRegisterer(this);
            this.database = new Database();

            JDABuilder builder = new JDABuilder(AccountType.BOT)
                    .setToken("token")
                    .setActivity(Activity.playing("!help"))
                    .setAutoReconnect(true);
            builder.addEventListeners(new MessageReceivedListener(this));
            builder.build();

            Exam exam = new Exam(1, "Deutsch", "21-09-20");
            Exam update = new Exam(1, "Deutsch", "09-05-20");
            database.createExam(exam);
            database.getExamMediator().setValue(exam.getId(), update);

            this.initializeCommands();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    private void initializeCommands() {
        new RockPaperScissorsCommand(this);
        new HelpCommand(this);
        new RandomFactCommand(this);
        new ExamCommand(this);
        new DateCommand(this);
        new PointsCommand(this);
    }

    public CommandRegisterer getCommandRegisterer() {
        return commandRegisterer;
    }

    public Database getDatabase() {
        return database;
    }
}
