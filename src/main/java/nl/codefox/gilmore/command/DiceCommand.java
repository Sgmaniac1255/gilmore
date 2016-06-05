package nl.codefox.gilmore.command;

import net.dv8tion.jda.Permission;
import net.dv8tion.jda.entities.TextChannel;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import nl.codefox.gilmore.command.dice.Dice;

public class DiceCommand extends GilmoreCommand {

    public DiceCommand() {
        super("Rolls dice based on input", "Usage: ![roll|dice] [expression]\n"
                + "for example: !roll 1d20 + 5 [to hit]\n"
                + "[comment]: this is ignored\n"
                + "2d20khX: keep the X highest dice\n"
                + "2d20klX: keep the X lowest dice\n"
                + "4d6r<X: reroll every die lower than X\n"
                + "4d6ro<X: reroll every die lower than X, but only once\n"
                + "1d10!: exploding die - every time you roll a crit, add an extra die", 2, 1000, (Permission) null, "!roll", "!dice");
    }

    @Override
    public void process(String command, String[] args, TextChannel channel, User author, MessageReceivedEvent event) {

        String expression = "";
        for (int i = 1; i < args.length; i++) {
            expression += " " + args[i];
        }
        expression = expression.substring(1);

        Dice dice = new Dice(expression);
        int result = dice.roll();

        String message = String.format("[%s] %s = %d", author.getAsMention(), dice.getBreakdown(), result);
        if (message.length() > 500) {
            message = message.substring(0, 500);
            message += "[This message got cut off because it is too long.]";
        }

        channel.sendMessage(message);
    }

}
