package fredstone.avionwars.commands;

import fredstone.avionwars.config.AvionWars;
import fredstone.avionwars.config.CommonProxy;
import fredstone.avionwars.other.ScoreboardAvionWars;
import fredstone.avionwars.other.Timer;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nonnull;

public class ToggleExplosionsCommand extends CommandBase {
    @Override
    @Nonnull
    public String getName() {
        return "toggleExplosions";
    }

    @Override
    @Nonnull
    public String getUsage(ICommandSender sender) {
        return "toggleExplosions";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        CommonProxy.explosionEnable = !CommonProxy.explosionEnable;
        if (CommonProxy.explosionEnable) {
            sender.sendMessage(new TextComponentString("Explosions enabled"));
        } else {
            sender.sendMessage(new TextComponentString("Explosions disabled"));
        }
    }
}
