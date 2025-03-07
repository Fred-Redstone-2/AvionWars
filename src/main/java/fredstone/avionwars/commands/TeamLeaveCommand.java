package fredstone.avionwars.commands;

import fredstone.avionwars.config.CommonProxy;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.server.CommandScoreboard;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;

import static fredstone.avionwars.config.AvionWars.server;

public class TeamLeaveCommand extends CommandBase {

    @Override
    @Nonnull
    public String getName() {
        return "leave";
    }

    @Override
    @Nonnull
    public String getUsage(ICommandSender sender) {
        return "leave";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length > 0) {
            sender.sendMessage(new TextComponentString(TextFormatting.RED + "Usage: /leave"));
            return;
        }
        if (sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;
            if (CommonProxy.teamGreen.getPlayers().contains(player)) {
                CommonProxy.teamGreen.removePlayer(player);
                sender.sendMessage(new TextComponentString(TextFormatting.GOLD + "You have left team " +
                        TextFormatting.GREEN + "Green"));
                leaveTeam(sender);
                player.setSpawnPoint(new BlockPos(0, 80, 0), false);
            }
            else if (CommonProxy.teamYellow.getPlayers().contains(player)) {
                CommonProxy.teamYellow.removePlayer(player);
                sender.sendMessage(new TextComponentString(TextFormatting.GOLD + "You have left team " +
                        TextFormatting.YELLOW + "Yellow"));
                player.setSpawnPoint(new BlockPos(0, 80, 0), false);
                leaveTeam(sender);
            }
            else {
                sender.sendMessage(new TextComponentString(TextFormatting.RED + "You are not in a team!"));
            }
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    private static void leaveTeam(ICommandSender sender) throws CommandException {
        CommandScoreboard scoreboard = new CommandScoreboard();
        String[] args = {"teams", "leave", sender.getName()};
        scoreboard.execute(server, sender, args);
    }
}
