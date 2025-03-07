package fredstone.avionwars.commands;

import fredstone.avionwars.config.CommonProxy;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.server.CommandScoreboard;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

import javax.annotation.Nonnull;

public class ResetCommand extends CommandBase {
    @Override
    @Nonnull
    public String getName() {
        return "reset";
    }

    @Override
    @Nonnull
    public String getUsage(ICommandSender sender) {
        return "reset";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        TeamJoinCommand.teamsCreated = false;
        CommonProxy.teamGreen.reset();
        CommonProxy.teamYellow.reset();
        GameStartCommand.yellowStealer = null;
        GameStartCommand.greenStealer = null;
        GameStartCommand.oresStarted = false;
        GameStartCommand.gameStarted = false;
        CommandScoreboard scoreboard = new CommandScoreboard();
        for (EntityPlayer player : CommonProxy.teamGreen.getPlayers()) {
            String[] removePlayer = {"teams", "leave", player.getName()};
            scoreboard.execute(server, sender, removePlayer);
        }
        for (EntityPlayer player : CommonProxy.teamYellow.getPlayers()) {
            String[] removePlayer = {"teams", "leave", player.getName()};
            scoreboard.execute(server, sender, removePlayer);
        }
        String[] removeY = {"teams", "remove", "TeamYellow"};
        String[] removeG = {"teams", "remove", "TeamGreen"};
        String[] removeAW = {"objectives", "remove", "AvionWars"};
        String[] removeDeath = {"objectives", "remove", "DeathCount"};
        scoreboard.execute(server, sender, removeY);
        scoreboard.execute(server, sender, removeG);
        scoreboard.execute(server, sender, removeAW);
        scoreboard.execute(server, sender, removeDeath);
    }
}
