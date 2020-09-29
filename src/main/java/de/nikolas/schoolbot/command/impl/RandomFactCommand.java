package de.nikolas.schoolbot.command.impl;

import de.nikolas.schoolbot.DiscordBot;
import de.nikolas.schoolbot.command.Command;
import de.nikolas.schoolbot.command.CommandType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class RandomFactCommand implements Command {

    private DiscordBot bot;
    private ArrayList<String> randomFacts;

    public RandomFactCommand(DiscordBot bot) {
        this.bot = bot;
        this.bot.getCommandRegisterer().register("!rfact", this::execute, "Zeigt irgendwelche Fakten", CommandType.COMMAND);

        this.randomFacts = new ArrayList<>();
        randomFacts.add("Würde ein Baby jedes Jahr im selben Tempo weiterwachsen wie in seinem ersten Lebensjahr, wäre es an seinem 18. Geburtstag ungefähr 5 Meter groß.");
        randomFacts.add("Das tödlichste Tier der Welt ist die Stechmücke! Mosquitos töteten 2014, unter anderem durch die Übertragung von Malaria- und Dengue-Fieber-Infektionen, 275.000 Menschen. Haie töteten im selben Jahr nur 10 Menschen.\n");
        randomFacts.add("Der Planet Uranus hieß ungefähr 70 Jahre lang George. Sein Entdecker Wilhelm Herschel gab ihm 1781, zu Ehren Königs George III., den Namen „Georgium Sidus“ – Georges Stern. Erst in den 1850ern wurde der Name offiziell in Uranus geändert.");
        randomFacts.add("Wenn man drei Minuten Zähne putzt, verbrennt man durchschnittlich 10 Kalorien.");
        randomFacts.add("Wombats machen ihre Häufchen in Würfelform.");
        randomFacts.add("Die amerikanischen Synchronsprecher von Minnie und Mickey Mouse (Russi Taylor und Wayne Allwine) waren verheiratet. Von 1929 bis 1946 synchronisierte Walt Disney seine berühmten Mäuse übrigens noch selbst, weil ihm kein Synchronsprecher zusagte.");
        randomFacts.add("Rund 11 Prozent der Deutschen tragen den Nachnamen Müller.");
        randomFacts.add("Mit der Mine eines durchschnittlichen Bleistifts kann man eine 56 Kilometer lange Linie zeichnen.");
        randomFacts.add("Koalas bekommen Schluckauf, wenn sie gestresst sind. Manchmal beginnen sie dann sogar, zusätzlich nervös mit den Ohren zu wackeln. Warum sie das tun, ist bisher nicht erforscht.");
        randomFacts.add("Otter legen sich zum Schlafen auf den Rücken und halten dabei Händchen. Damit sorgen sie dafür, dass sie im Wasser nicht voneinander wegtreiben.");
        randomFacts.add("Es leben mehr Bakterien in deinem Körper (ca. 39 Billionen) als Menschen auf der Erde (ca. 7,6, Milliarden).");
        randomFacts.add("Hippopotamomonstrosesquipedaliophobie ist der offizielle Name für die Angst vor langen Wörtern.");
        randomFacts.add("Mit beeindruckenden 161,5 Metern besitzt das Ulmer Münster den höchsten Kirchturm der Welt.");
        randomFacts.add("Der griechische Dichter Aeschylus soll den Überlieferungen zufolge 455 v. Chr. zu Tode gekommen sein, indem ihn eine Schildkröte, die ein Greifvogel fallen ließ, erschlug. Das Missgeschick kam wohl zustande, weil der Vogel die Glatze des Dichters mit einem Stein verwechselte, den er nutzen wollte, um den Panzer des Tieres zu knacken.");
        randomFacts.add("Rund 75.000 Regenschirme werden jedes Jahr in der Londoner U-Bahn liegen gelassen.");
        randomFacts.add("In der amerikanischen Stadt Daytona ist es verboten, Mülltonnen sexuell zu belästigen.");
        randomFacts.add("Faultiere brauchen bis zu einen Monat, um ihr Essen zu verdauen.");
        randomFacts.add("Sommerloch, Katzenhirn und Langweiler sind Orte in Deutschland.");
        randomFacts.add("Jeder sechste deutsche Arzt der inneren Medizin wurde schon mindestens einmal von einem Patienten verprügelt.");
        randomFacts.add("Schottlands Wappentier ist ein Einhorn.");
        randomFacts.add("Der japanische Spielekonsolen-Hersteller Nintendo existiert bereits seit 1889.");
        randomFacts.add("Das Krümelmonster heißt eigentlich Sid.");
        randomFacts.add("Bluetooth wurde nach einem dänischen Wikingerkönig namens Harald Blauzahn benannt.");
        randomFacts.add("Nimmt man die jeweiligen Anfangsbuchstaben der Monate Juli bis November zusammen, ergibt sich der Name „Jason“.");
        randomFacts.add("Täglich verliert jeder Mensch bis zu 200 Haare.");
        randomFacts.add("Schweine dürfen in Frankreich nicht Napoleon genannt werden.");
        randomFacts.add("2011 entschied sich ein israelisches Elternpaar dazu, seine neugeborene Tochter „Like“ zu nennen – nach dem allseits bekannten Facebook-Button.");
    }

    @Override
    public void execute(String[] args, MessageReceivedEvent event) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        int index = new Random().nextInt(randomFacts.size());

        embedBuilder.setColor(Color.YELLOW);
        embedBuilder.setAuthor("Random Fact");
        embedBuilder.setDescription(randomFacts.get(index));

        event.getTextChannel().sendMessage(embedBuilder.build()).queue();
    }
}
