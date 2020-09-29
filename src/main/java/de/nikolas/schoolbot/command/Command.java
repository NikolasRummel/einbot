package de.nikolas.schoolbot.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface Command {

    void execute(String[] args, MessageReceivedEvent event);

}
