package fredstone.avionwars.commands;

import fredstone.avionwars.blocks.ModBlocks;
import fredstone.avionwars.config.AvionWars;
import fredstone.avionwars.config.CommonProxy;
import fredstone.avionwars.config.Config;
import fredstone.avionwars.items.ModItems;
import fredstone.avionwars.other.ItemGenerator;
import fredstone.avionwars.other.ScoreboardAvionWars;
import fredstone.avionwars.other.Timer;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class GameStartCommand extends CommandBase {
    private static World world;
    public static List<BlockPos> leadOres = new ArrayList<>();
    public static List<BlockPos> copperOres = new ArrayList<>();
    public static List<BlockPos> uraniumOres = new ArrayList<>();
    public static EntityPlayer greenStealer, yellowStealer;
    public static boolean gameStarted = false;
    public static boolean oresStarted = false;
    public static ICommandSender admin;

    @Override
    @Nonnull
    public String getName() {
        return "start";
    }

    @Override
    @Nonnull
    public String getUsage(ICommandSender sender) {
        return "start";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        admin = sender;
        world = server.getWorld(0);
        Thread messages = new Thread(() -> {
            try {
                server.getPlayerList().sendMessage(new TextComponentString(TextFormatting.BOLD + "The game is starting!"));
                Thread.sleep(500);
                server.getPlayerList().sendMessage(new TextComponentString("5..."));
                Thread.sleep(1000);
                server.getPlayerList().sendMessage(new TextComponentString("4..."));
                Thread.sleep(1000);
                server.getPlayerList().sendMessage(new TextComponentString("3..."));
                Thread.sleep(1000);
                server.getPlayerList().sendMessage(new TextComponentString("2..."));
                Thread.sleep(1000);
                server.getPlayerList().sendMessage(new TextComponentString("1..."));
                Thread.sleep(1000);
                server.getPlayerList().sendMessage(new TextComponentString(TextFormatting.RED + "Let the game begin!"));

                // Force KeepInventory True and sendCommandFeedback False
                CommandGameRule command = new CommandGameRule();
                String[] keepInv = {"keepInventory", "true"};
                String[] commandFeedback = {"sendCommandFeedback", "false"};
                command.execute(server, sender, commandFeedback);
                command.execute(server, sender, keepInv);

                BlockPos yellowSpawn = CommonProxy.teamYellow.spawnpoint;
                BlockPos greenSpawn = CommonProxy.teamGreen.spawnpoint;
                for (EntityPlayer player : CommonProxy.teamYellow.getPlayers()) {
                    player.setPositionAndUpdate(yellowSpawn.getX(), yellowSpawn.getY(), yellowSpawn.getZ());
                    player.inventory.armorInventory.set(2, ItemGenerator.getChestplate(false));
                    player.inventory.armorInventory.set(1, ItemGenerator.getLeggings(false));
                    player.inventory.armorInventory.set(0, ItemGenerator.getBoots(false));
                    player.inventory.addItemStackToInventory(ItemGenerator.getSword());
                    player.inventory.addItemStackToInventory(ItemGenerator.getPickaxe());
                    player.inventory.addItemStackToInventory(ItemGenerator.getFood());
                }
                for (EntityPlayer player : CommonProxy.teamGreen.getPlayers()) {
                    player.setPositionAndUpdate(greenSpawn.getX(), greenSpawn.getY(), greenSpawn.getZ());
                    player.inventory.armorInventory.set(2, ItemGenerator.getChestplate(true));
                    player.inventory.armorInventory.set(1, ItemGenerator.getLeggings(true));
                    player.inventory.armorInventory.set(0, ItemGenerator.getBoots(true));
                    player.inventory.addItemStackToInventory(ItemGenerator.getSword());
                    player.inventory.addItemStackToInventory(ItemGenerator.getPickaxe());
                    player.inventory.addItemStackToInventory(ItemGenerator.getFood());
                }

                //Start the game
                oresStarted = true;
                runOres();
                Timer timer = new Timer(Config.preparationDelay);
                timer.startPreparation(sender);
                gameStarted = true;
                runGame();
                ScoreboardAvionWars.start(sender);
                server.getPlayerList().sendMessage(new TextComponentString(TextFormatting.RED + "Preparation is over! Fight for the flags!"));
            } catch (InterruptedException | CommandException e) {
                sender.sendMessage(new TextComponentString(TextFormatting.RED + e.getMessage()));
            }
        });
        messages.start();
    }

    private void runOres() {
        Thread lead = new Thread(() -> {
            while (oresStarted) {
                try {
                    Thread.sleep(Config.leadRespawnTime * 1000L);
                    for (BlockPos pos : leadOres) {
                        world.setBlockState(pos, ModBlocks.ore_lead.getDefaultState());
                    }
                    leadOres.clear();
                } catch (InterruptedException e) {
                    System.out.println("Interrupted while waiting for lead ores");
                }
            }
        });
        Thread copper = new Thread(() -> {
            while (oresStarted) {
                try {
                    Thread.sleep(Config.copperRespawnTime * 1000L);
                    for (BlockPos pos : copperOres) {
                        world.setBlockState(pos, ModBlocks.ore_copper.getDefaultState());
                    }
                    copperOres.clear();
                } catch (InterruptedException e) {
                    System.out.println("Interrupted while waiting for copper ores");
                }
            }
        });
        Thread uranium = new Thread(() -> {
            while (oresStarted) {
                try {
                    Thread.sleep(Config.uraniumRespawnTime * 1000L);
                    for (BlockPos pos : uraniumOres) {
                        world.setBlockState(pos, ModBlocks.ore_uranium.getDefaultState());
                    }
                    uraniumOres.clear();
                } catch (InterruptedException e) {
                    System.out.println("Interrupted while waiting for uranium ores");
                }
            }
        });
        lead.start();
        copper.start();
        uranium.start();
    }

    private void runGame() {
        Thread game = new Thread(() -> {
            while (gameStarted) {
                try {
                    Thread.sleep(500);
                    if (yellowStealer != null) {
                        if (inRange(yellowStealer, CommonProxy.teamGreen.spawnpoint)) {
                            List<ItemStack> items = yellowStealer.inventory.mainInventory;
                            for (ItemStack item : items) {
                                if (item.getItem().getRegistryName() == ModItems.flag_yellow.getRegistryName()) {
                                    item.setCount(0);
                                    CommonProxy.teamGreen.addPoint();
                                    AvionWars.server.getPlayerList().sendMessage(new TextComponentString(TextFormatting.BOLD +
                                            "The " + TextFormatting.GREEN + "Green" + TextFormatting.RESET + TextFormatting.BOLD +
                                            " team scores a point by brining back the " + TextFormatting.GRAY + "Yellow" +
                                            TextFormatting.RESET + TextFormatting.BOLD + " flag!"));
                                    ScoreboardAvionWars.modifyPoints(admin, "add", "Green");

                                    CommonProxy.checkPoints();
                                    CommonProxy.returnYellowFlag();
                                }
                            }
                        }
                    }
                    if (greenStealer != null) {
                        if (inRange(greenStealer, CommonProxy.teamYellow.spawnpoint)) {
                            List<ItemStack> items = greenStealer.inventory.mainInventory;
                            for (ItemStack item : items) {
                                if (item.getItem().getRegistryName() == ModItems.flag_green.getRegistryName()) {
                                    item.setCount(0);
                                    CommonProxy.teamYellow.addPoint();
                                    AvionWars.server.getPlayerList().sendMessage(new TextComponentString(TextFormatting.BOLD +
                                            "The " + TextFormatting.YELLOW + "Yellow" + TextFormatting.RESET + TextFormatting.BOLD +
                                            " team scores a point by brining back the " + TextFormatting.GRAY + "Green" +
                                            TextFormatting.RESET + TextFormatting.BOLD + " flag!"));
                                    ScoreboardAvionWars.modifyPoints(admin, "add", "Yellow");

                                    CommonProxy.checkPoints();
                                    CommonProxy.returnGreenFlag();
                                }
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    System.out.println("Interrupted while waiting for game");
                } catch (CommandException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        game.start();
    }

    private boolean inRange(EntityPlayer player, BlockPos coordinate) {
        return Math.sqrt(Math.pow(player.posX - coordinate.getX(), 2) + Math.pow(player.posZ - coordinate.getZ(), 2)) < Config.flagReturnRadius;
    }
}
