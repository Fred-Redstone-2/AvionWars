package fredstone.avionwars.commands;

import fredstone.avionwars.config.CommonProxy;
import fredstone.avionwars.config.Config;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SetSpawnpointCommand extends CommandBase {
    @Override
    @Nonnull
    public String getName() {
        return "setSpawnpoint";
    }

    @Override
    @Nonnull
    public String getUsage(ICommandSender sender) {
        return "setSpawnpoint <Team Name>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 1) {
            sender.sendMessage(new TextComponentString(TextFormatting.RED + "Usage: /setSpawnpoint <Team Name>"));
            return;
        }
        args[0] = args[0].toLowerCase();
        if (sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;
            switch (args[0]) {
                case "yellow": {
                    CommonProxy.teamYellow.setSpawnpoint(player.posX, player.posY, player.posZ);
                    sender.sendMessage(new TextComponentString(TextFormatting.GREEN + "Spawnpoint set for team Yellow at " + player.posX + ", " + player.posY + ", " + player.posZ));
                    break;
                }
                case "green": {
                    CommonProxy.teamGreen.setSpawnpoint(player.posX, player.posY, player.posZ);
                    sender.sendMessage(new TextComponentString(TextFormatting.GREEN + "Spawnpoint set for team Green at " + player.posX + ", " + player.posY + ", " + player.posZ));
                    break;
                }
                default: {
                    sender.sendMessage(new TextComponentString(TextFormatting.RED + "This team does not exist!"));
                    break;
                }
            }
        }
    }
}
