package fredstone.avionwars.commands;

import fredstone.avionwars.config.CommonProxy;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandGameRule;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nonnull;

public class ToggleBuildingCommand extends CommandBase {
    @Override
    @Nonnull
    public String getName() {
        return "toggleBuilding";
    }

    @Override
    @Nonnull
    public String getUsage(ICommandSender sender) {
        return "toggleBuilding";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        CommonProxy.replaceBlocks = !CommonProxy.replaceBlocks;
        if (CommonProxy.replaceBlocks) {
            sender.sendMessage(new TextComponentString("Replace blocks enabled"));
        } else {
            sender.sendMessage(new TextComponentString("Replace blocks disabled"));
        }
    }
}
