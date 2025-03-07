package fredstone.avionwars.commands;

import fredstone.avionwars.config.AvionWars;
import fredstone.avionwars.config.CommonProxy;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.server.CommandScoreboard;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;

import static fredstone.avionwars.config.AvionWars.server;

public class TeamJoinCommand extends CommandBase {
    public static boolean teamsCreated = false;

    @Override
    @Nonnull
    public String getName() {
        return "join";
    }

    @Override
    @Nonnull
    public String getUsage(ICommandSender sender) {
        return "join <Team Name>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (AvionWars.server == null) {
            AvionWars.server = server;
        }
        if (args.length != 1) {
            sender.sendMessage(new TextComponentString(TextFormatting.RED + "Usage: /join <Team Name>"));
            return;
        }
        args[0] = args[0].toLowerCase();
        if (sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;
            switch (args[0]) {
                case "yellow": {
                    if (!teamsCreated) {
                        createTeams(sender);
                    }
                    if (CommonProxy.teamGreen.getPlayers().contains(player) || CommonProxy.teamYellow.getPlayers().contains(player)) {
                        sender.sendMessage(new TextComponentString(TextFormatting.RED + "You are already in a team!"));
                    }
                    else {
                        CommonProxy.teamYellow.addPlayer(player);
                        joinTeam("TeamYellow", sender);
                        sender.sendMessage(new TextComponentString(TextFormatting.GOLD + "You have joined team " +
                                TextFormatting.YELLOW + "Yellow"));
                        player.setSpawnPoint(CommonProxy.teamYellow.spawnpoint, true);
                    }
                    break;
                }
                case "green": {
                    if (!teamsCreated) {
                        createTeams(sender);
                    }
                    if (CommonProxy.teamYellow.getPlayers().contains(player) || CommonProxy.teamGreen.getPlayers().contains(player)) {
                        sender.sendMessage(new TextComponentString(TextFormatting.RED + "You are already in a team!"));
                    }
                    else {
                        CommonProxy.teamGreen.addPlayer(player);
                        joinTeam("TeamGreen", sender);
                        sender.sendMessage(new TextComponentString(TextFormatting.GOLD + "You have joined team " +
                                TextFormatting.GREEN + "Green"));
                        player.setSpawnPoint(CommonProxy.teamGreen.spawnpoint, true);
                    }
                    break;
                }
                default: {
                    sender.sendMessage(new TextComponentString(TextFormatting.RED + "This team does not exist!"));
                    break;
                }
            }
        }
    }

    private void joinTeam(String team, ICommandSender sender) throws CommandException {
        CommandScoreboard scoreboard = new CommandScoreboard();
        String[] args = {"teams", "join", team, sender.getName()};
        scoreboard.execute(server, sender, args);
    }

    private void createTeams(ICommandSender sender) throws CommandException {
        CommandScoreboard scoreboard = new CommandScoreboard();
        String[] teamYellow = {"teams", "add", "TeamYellow"};
        String[] teamYellowColor = {"teams", "option", "TeamYellow", "color", "yellow"};
        String[] disableFFY = {"teams", "option", "TeamYellow", "friendlyFire", "false"};
        String[] teamGreen = {"teams", "add", "TeamGreen"};
        String[] teamGreenColor = {"teams", "option", "TeamGreen", "color", "green"};
        String[] disableFFG = {"teams", "option", "TeamGreen", "friendlyFire", "false"};
        try {
            scoreboard.execute(server, sender, teamYellow);
        }
        catch (CommandException e) {
            teamsCreated = true;
            return;
        }
        scoreboard.execute(server, sender, teamYellowColor);
        scoreboard.execute(server, sender, disableFFY);
        scoreboard.execute(server, sender, teamGreen);
        scoreboard.execute(server, sender, teamGreenColor);
        scoreboard.execute(server, sender, disableFFG);
        teamsCreated = true;
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }
}
