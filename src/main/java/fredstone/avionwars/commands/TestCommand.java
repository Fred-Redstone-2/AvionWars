package fredstone.avionwars.commands;

import fredstone.avionwars.config.AvionWars;
import fredstone.avionwars.other.ScoreboardAvionWars;
import fredstone.avionwars.other.Timer;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nonnull;

public class TestCommand extends CommandBase {
    @Override
    @Nonnull
    public String getName() {
        return "test";
    }

    @Override
    @Nonnull
    public String getUsage(ICommandSender sender) {
        return "test";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        AvionWars.server = server;
        Thread messages = new Thread(() -> {
            Timer timer = new Timer(1);
            timer.startPreparation(sender);
            sender.sendMessage(new TextComponentString("Timer finished"));
            ScoreboardAvionWars.start(sender);
        });
        messages.start();
    }
}
