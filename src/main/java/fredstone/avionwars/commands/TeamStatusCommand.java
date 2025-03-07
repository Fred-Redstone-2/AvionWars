package fredstone.avionwars.commands;

import fredstone.avionwars.config.CommonProxy;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;

public class TeamStatusCommand extends CommandBase {
    @Override
    @Nonnull
    public String getName() {
        return "team";
    }

    @Override
    @Nonnull
    public String getUsage(ICommandSender sender) {
        return "team status <Team Name>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("status")) {
                String teamName = args[1].toLowerCase(), msg;
                switch (teamName) {
                    case "yellow": {
                        msg = "Team Yellow:\nPlayers: ";
                        for (int i = 0; i < CommonProxy.teamYellow.getPlayers().size(); i++) {
                            if (i != 0) {
                                msg += ", ";
                            }
                            msg += CommonProxy.teamYellow.getPlayers().get(i).getName();
                        }
                        msg += "\nSpawnpoint: " + CommonProxy.teamYellow.spawnpoint.getX() + ", " +
                                CommonProxy.teamYellow.spawnpoint.getY() + ", " + CommonProxy.teamYellow.spawnpoint.getZ();
                        sender.sendMessage(new TextComponentString(msg));
                        break;
                    }
                    case "green": {
                        msg = "Team Green:\nPlayers: ";
                        for (int i = 0; i < CommonProxy.teamGreen.getPlayers().size(); i++) {
                            if (i != 0) {
                                msg += ", ";
                            }
                            msg += CommonProxy.teamGreen.getPlayers().get(i).getName();
                        }
                        msg += "\nSpawnpoint: " + CommonProxy.teamGreen.spawnpoint.getX() + ", " +
                                CommonProxy.teamGreen.spawnpoint.getY() + ", " + CommonProxy.teamGreen.spawnpoint.getZ();
                        sender.sendMessage(new TextComponentString(msg));
                        break;
                    }
                    default: {
                        sender.sendMessage(new TextComponentString(TextFormatting.RED + "That team does not exist!"));
                    }
                }
            }
            else {
                sender.sendMessage(new TextComponentString(TextFormatting.RED + "Usage: /team status <Team Name>"));
            }
        }
        else {
            sender.sendMessage(new TextComponentString(TextFormatting.RED + "Usage: /team status <Team Name>"));
        }
    }
}
