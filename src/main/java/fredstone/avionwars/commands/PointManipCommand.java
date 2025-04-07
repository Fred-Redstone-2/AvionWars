package fredstone.avionwars.commands;

import fredstone.avionwars.config.CommonProxy;
import fredstone.avionwars.other.Team;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class PointManipCommand extends CommandBase {
    @Override
    public String getName() {
        return "point";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/point [add|remove] [team]";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 2) {
            sender.sendMessage(new TextComponentString(TextFormatting.RED + "Usage: /point [add|remove] <Team Name>"));
            return;
        }
        Team team;
        switch (args[1].toLowerCase()) {
            case "yellow": {
                team = CommonProxy.teamYellow;
                break;
            }
            case "green": {
                team = CommonProxy.teamGreen;
                break;
            }
            default: {
                sender.sendMessage(new TextComponentString(TextFormatting.RED + "Wrong first argument! Usage: /point [add|remove] <Team Name>"));
                return;
            }
        }
        switch (args[0]) {
            case "add": {
                team.nbPoints++;
                break;
            }
            case "remove": {
                team.nbPoints--;
                break;
            }
            default: {
                sender.sendMessage(new TextComponentString(TextFormatting.RED + "Wrong first argument! Usage: /point [add|remove] <Team Name>"));
            }
        }
    }
}
