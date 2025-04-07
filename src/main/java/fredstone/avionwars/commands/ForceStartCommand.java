package fredstone.avionwars.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class ForceStartCommand extends CommandBase {
    @Override
    public String getName() {
        return "forcestart";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/forcestart";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        GameStartCommand.gameStarted = true;
    }
}
