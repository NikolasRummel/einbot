package de.nikolas.schoolbot.command;

import de.nikolas.schoolbot.DiscordBot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;

public class CommandRegisterer extends ListenerAdapter {

    private DiscordBot bot;

    private HashMap<String, Command> games;
    private HashMap<String, Command> commands;
    private HashMap<Command, String> description;

    public CommandRegisterer(DiscordBot bot) {
        this.bot = bot;
        this.games = new HashMap<>();
        this.commands = new HashMap<>();
        this.description = new HashMap<>();
    }

    public void register(String name, Command command, String description, CommandType type) {
        this.description.put(command, description);
        if (type.equals(CommandType.COMMAND)) {
            commands.put(name, command);
        } else {
            games.put(name, command);
        }
    }

    public void call(String name, String[] args, MessageReceivedEvent event) {
        if (existsGame(name)) {
            games.get(name.toLowerCase()).execute(args, event);
            return;
        }
        if (existsCommand(name)) {
            commands.get(name.toLowerCase()).execute(args, event);
        }
    }

    public Command getGame(String game) {
        return this.games.getOrDefault(game.toLowerCase(), null);
    }

    public Command getCommand(String command) {
        return this.commands.getOrDefault(command.toLowerCase(), null);
    }

    public String getDescription(Command command) {
        return this.description.getOrDefault(command, "not found");
    }

    public boolean existsGame(String game) {
        return this.games.containsKey(game.toLowerCase());
    }

    public boolean existsCommand(String command) {
        return this.commands.containsKey(command.toLowerCase());
    }

    public HashMap<String, Command> getGames() {
        return games;
    }

    public HashMap<String, Command> getCommands() {
        return commands;
    }
}
