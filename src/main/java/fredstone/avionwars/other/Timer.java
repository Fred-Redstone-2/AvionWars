package fredstone.avionwars.other;

import fredstone.avionwars.config.AvionWars;
import fredstone.avionwars.config.CommonProxy;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.server.CommandScoreboard;
import net.minecraft.util.text.TextComponentString;

public class Timer {
    private int nbSeconds;

    public Timer(int nbSeconds) {
        this.nbSeconds = nbSeconds;
    }

    public void startPreparation(ICommandSender sender) {
        CommandScoreboard scoreboard = new CommandScoreboard();
        String[] argsPrep = {"objectives", "add", "Préparation", "dummy"};
        String[] showPrep = {"objectives", "setDisplay", "sidebar", "Préparation"};
        String[] showMinutes = {"players", "set", "Minutes", "Préparation", String.valueOf(nbSeconds / 60)};
        String[] showSeconds = {"players", "set", "Secondes", "Préparation", String.valueOf(nbSeconds % 60)};
        String[] resetSeconds = {"players", "set", "Secondes", "Préparation", String.valueOf(59)};
        String[] removeSecond = {"players", "remove", "Secondes", "Préparation", String.valueOf(1)};
        String[] removeMinute = {"players", "remove", "Minutes", "Préparation", String.valueOf(1)};

        try {
            scoreboard.execute(AvionWars.server, sender, argsPrep);
            scoreboard.execute(AvionWars.server, sender, showPrep);
            scoreboard.execute(AvionWars.server, sender, showMinutes);
            scoreboard.execute(AvionWars.server, sender, showSeconds);
            for (int i = nbSeconds; i > 0; i--) {
                Thread.sleep(1000);
                scoreboard.execute(AvionWars.server, sender, removeSecond);
                if (i % 60 == 0) {
                    scoreboard.execute(AvionWars.server, sender, resetSeconds);
                    scoreboard.execute(AvionWars.server, sender, removeMinute);
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Timer interrupted");
        } catch (CommandException e) {
            sender.sendMessage(new TextComponentString(e.getMessage()));
        }
    }
}
