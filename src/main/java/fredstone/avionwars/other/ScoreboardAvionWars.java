package fredstone.avionwars.other;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.server.CommandScoreboard;
import net.minecraft.util.text.TextComponentString;

import static fredstone.avionwars.config.AvionWars.server;

public class ScoreboardAvionWars {
    public static void start(ICommandSender sender) {
        CommandScoreboard scoreboard = new CommandScoreboard();
        String[] argsRemove = {"objectives", "remove", "Préparation"};
        String[] argsCreate = {"objectives", "add", "DeathCount", "deathCount"};
        String[] argsDisplay = {"objectives", "setDisplay", "list", "DeathCount"};
        String[] argsAvionWars = {"objectives", "add", "AvionWars", "dummy"};
        String[] show = {"objectives", "setDisplay", "sidebar", "AvionWars"};
        String[] yellowScore = {"players", "set", "Yellow", "AvionWars", String.valueOf(0)};
        String[] greenScore = {"players", "set", "Green", "AvionWars", String.valueOf(0)};
        String[] applyYellow = {"teams", "join", "TeamYellow", "Yellow"};
        String[] applyGreen = {"teams", "join", "TeamGreen", "Green"};

        try {
            scoreboard.execute(server, sender, argsRemove);
            scoreboard.execute(server, sender, argsCreate);
            scoreboard.execute(server, sender, argsDisplay);
            scoreboard.execute(server, sender, argsAvionWars);
            scoreboard.execute(server, sender, show);
            scoreboard.execute(server, sender, yellowScore);
            scoreboard.execute(server, sender, greenScore);
            scoreboard.execute(server, sender, applyYellow);
            scoreboard.execute(server, sender, applyGreen);
        } catch (CommandException e) {
             sender.sendMessage(new TextComponentString(e.getMessage()));
        }
    }

    public static void modifyPoints(ICommandSender sender, String mode, String team) throws CommandException {
        CommandScoreboard scoreboard = new CommandScoreboard();
        String[] args = {"players", mode, team, "AvionWars", String.valueOf(1)};
        scoreboard.execute(server, sender, args);
    }
}
