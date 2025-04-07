package fredstone.avionwars.config;

import fredstone.avionwars.blocks.*;
import fredstone.avionwars.commands.GameStartCommand;
import fredstone.avionwars.items.*;
import fredstone.avionwars.other.EnchantmentInit;
import fredstone.avionwars.other.ItemGenerator;
import fredstone.avionwars.other.ScoreboardAvionWars;
import fredstone.avionwars.other.Team;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.command.CommandException;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static fredstone.avionwars.config.AvionWars.server;

@Mod.EventBusSubscriber(modid = AvionWars.MODID)
public class CommonProxy {

    public static Configuration config;

    public static Team teamYellow;
    public static Team teamGreen;
    public static BlockPos yellowFlagLocation;
    public static BlockPos greenFlagLocation;
    public static boolean replaceBlocks = true;
    public static boolean explosionEnable = false;
    private static final List<EntityPlayer> playersRespawn = new ArrayList<>();

    public void preInit(FMLPreInitializationEvent event) {
        File directory = event.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), "avionwars.cfg"));
        Config.readConfig();
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {
        if (config.hasChanged()) {
            config.save();
        }
        teamYellow = new Team("Yellow", Config.spawnYellow);
        teamGreen = new Team("Green", Config.spawnGreen);
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new OreLead());
        event.getRegistry().register(new OreCopper());
        event.getRegistry().register(new OreUranium());
        event.getRegistry().register(new FlagBlockGreen());
        event.getRegistry().register(new FlagBlockYellow());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(ModBlocks.ore_lead).setRegistryName(ModBlocks.ore_lead.getRegistryName()));
        event.getRegistry().register(new IngotLead());
        event.getRegistry().register(new ItemBlock(ModBlocks.ore_copper).setRegistryName(ModBlocks.ore_copper.getRegistryName()));
        event.getRegistry().register(new IngotCopper());
        event.getRegistry().register(new ItemBlock(ModBlocks.ore_uranium).setRegistryName(ModBlocks.ore_uranium.getRegistryName()));
        event.getRegistry().register(new IngotUranium());
        event.getRegistry().register(new ItemBlock(ModBlocks.flag_block_green).setRegistryName(ModBlocks.flag_block_green.getRegistryName()));
        event.getRegistry().register(new FlagGreen());
        event.getRegistry().register(new ItemBlock(ModBlocks.flag_block_yellow).setRegistryName(ModBlocks.flag_block_yellow.getRegistryName()));
        event.getRegistry().register(new FlagYellow());
        event.getRegistry().register(new Flag());
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        registerModel(Item.getItemFromBlock(ModBlocks.ore_lead), 0);
        registerModel(ModItems.ingot_lead, 0);
        registerModel(Item.getItemFromBlock(ModBlocks.ore_copper), 0);
        registerModel(ModItems.ingot_copper, 0);
        registerModel(Item.getItemFromBlock(ModBlocks.ore_uranium), 0);
        registerModel(ModItems.ingot_uranium, 0);
        registerModel(Item.getItemFromBlock(ModBlocks.flag_block_green), 0);
        registerModel(ModItems.flag_green, 0);
        registerModel(Item.getItemFromBlock(ModBlocks.flag_block_yellow), 0);
        registerModel(ModItems.flag_yellow, 0);
        registerModel(ModItems.flag, 0);
    }

    private static void registerModel(Item item, int meta) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    @SubscribeEvent
    public static void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
        event.getRegistry().registerAll(EnchantmentInit.DEATH_KEEPING);
    }

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            if (GameStartCommand.greenStealer != null && GameStartCommand.greenStealer.getName().equals(player.getName())) {
                returnGreenFlag();
                for (EntityPlayer teamMember : teamGreen.getPlayers()) {
                    List<ItemStack> items = teamMember.inventory.mainInventory;
                    for (ItemStack item : items) {
                        if (item.getItem().getRegistryName() == ModItems.flag_green.getRegistryName()) {
                            item.setCount(0);
                        }
                    }
                }
                server.getPlayerList().sendMessage(new TextComponentString(TextFormatting.BOLD + "The " +
                        TextFormatting.GREEN + "Green" + TextFormatting.RESET + TextFormatting.BOLD + " flag " +
                        "returns to it's original place because its stealer is dead."));
            } else if (GameStartCommand.yellowStealer != null && GameStartCommand.yellowStealer.getName().equals(player.getName())) {
                returnYellowFlag();
                for (EntityPlayer teamMember : teamYellow.getPlayers()) {
                    List<ItemStack> items = teamMember.inventory.mainInventory;
                    for (ItemStack item : items) {
                        if (item.getItem().getRegistryName() == ModItems.flag_yellow.getRegistryName()) {
                            item.setCount(0);
                        }
                    }
                }
                server.getPlayerList().sendMessage(new TextComponentString(TextFormatting.BOLD + "The " +
                        TextFormatting.YELLOW + "Yellow" + TextFormatting.RESET + TextFormatting.BOLD + " flag " +
                        "returns to it's original place because its stealer is dead."));
            }
            List<ItemStack> deathInv = player.inventory.mainInventory;
            for (ItemStack item : deathInv) {
                checkOres(player, item);
            }
            player.inventory.addItemStackToInventory(ItemGenerator.getFood());
            for (EntityPlayer playerY : teamYellow.getPlayers()) {
                if (playerY.getDisplayName().equals(player.getDisplayName())) {
                    teamYellow.addDeath();
                }
            }
            for (EntityPlayer playerG : teamGreen.getPlayers()) {
                if (playerG.getDisplayName().equals(player.getDisplayName())) {
                    teamGreen.addDeath();
                }
            }
            try {
                checkPoints();
            } catch (CommandException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @SubscribeEvent
    public static void onRespawn(PlayerEvent.PlayerRespawnEvent event) {
        EntityPlayer player = event.player;
        Thread stun = new Thread(() -> {
            playersRespawn.add(player);
            for (int i = 0; i < Config.spawnImmunity * 100; i++) {
                if (i < Config.spawnImmunity * 100) {
                    player.attemptTeleport(player.posX, player.posY, player.posZ);
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    player.sendMessage(new TextComponentString("Thread interrupted"));
                }
            }
            playersRespawn.remove(player);
        });
        stun.start();
    }

    private static void checkOres(EntityPlayer player, @Nonnull ItemStack item) {
        if (item.getItem().getRegistryName() == ModItems.ingot_lead.getRegistryName()) {
            int originalCount = item.getCount();
            int nbIngotsToDrop = originalCount * Config.ingotPercentToDrop / 100;
            ItemStack ingots = new ItemStack(ModItems.ingot_lead, nbIngotsToDrop);
            player.dropItem(ingots, false);
            item.setCount(originalCount - nbIngotsToDrop);
        } else if (item.getItem().getRegistryName() == ModItems.ingot_copper.getRegistryName()) {
            int originalCount = item.getCount();
            int nbIngotsToDrop = originalCount * Config.ingotPercentToDrop / 100;
            ItemStack ingots = new ItemStack(ModItems.ingot_copper, nbIngotsToDrop);
            player.dropItem(ingots, false);
            item.setCount(originalCount - nbIngotsToDrop);
        } else if (item.getItem().getRegistryName() == ModItems.ingot_uranium.getRegistryName()) {
            int originalCount = item.getCount();
            int nbIngotsToDrop = originalCount * Config.ingotPercentToDrop / 100;
            ItemStack ingots = new ItemStack(ModItems.ingot_uranium, nbIngotsToDrop);
            player.dropItem(ingots, false);
            item.setCount(originalCount - nbIngotsToDrop);
        }
    }

    public static void checkPoints() throws CommandException {
        if (teamGreen.nbDeaths >= Config.deathsForPoint) {
            teamYellow.addPoint();
            teamGreen.nbDeaths = 0;
            server.getPlayerList().sendMessage(new TextComponentString(TextFormatting.BOLD + "The " +
                    TextFormatting.YELLOW + "Yellow" + TextFormatting.RESET + TextFormatting.BOLD + " team scores a point by killing the " +
                    TextFormatting.GRAY + "Green" + TextFormatting.RESET + TextFormatting.BOLD + " team 10 times!"));
            ScoreboardAvionWars.addPointYellow(GameStartCommand.admin);
        }
        if (teamYellow.nbDeaths >= Config.deathsForPoint) {
            teamGreen.addPoint();
            teamYellow.nbDeaths = 0;
            server.getPlayerList().sendMessage(new TextComponentString(TextFormatting.BOLD + "The " +
                    TextFormatting.GREEN + "Green" + TextFormatting.RESET + TextFormatting.BOLD + " team scores a point by killing the " + TextFormatting.GRAY +
                    "Yellow" + TextFormatting.RESET + TextFormatting.BOLD + " team 10 times!"));
            ScoreboardAvionWars.addPointGreen(GameStartCommand.admin);
        }
        if (teamGreen.nbPoints >= Config.winningPoints || teamYellow.nbPoints >= Config.winningPoints) {
            endGame();
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.HarvestDropsEvent event) {
        Block block = event.getState().getBlock();
        BlockPos pos = event.getPos();

        //If game not started, replace blocks
        if (block == ModBlocks.flag_block_green && GameStartCommand.gameStarted) {
            server.getPlayerList().sendMessage(new TextComponentString(TextFormatting.BOLD + "The " +
                    TextFormatting.GREEN + "Green" + TextFormatting.RESET + TextFormatting.BOLD + " flag was stolen by "
                    + TextFormatting.YELLOW + event.getHarvester().getName() + TextFormatting.RESET + TextFormatting.BOLD + "!"));
            greenFlagLocation = pos;
            GameStartCommand.greenStealer = event.getHarvester();
            event.getHarvester().addPotionEffect(new PotionEffect(MobEffects.GLOWING, 999999, 1));
        } else if (block == ModBlocks.flag_block_yellow && GameStartCommand.gameStarted) {
            server.getPlayerList().sendMessage(new TextComponentString(TextFormatting.BOLD + "The " +
                    TextFormatting.YELLOW + "Yellow" + TextFormatting.RESET + TextFormatting.BOLD + " flag was stolen by "
                    + TextFormatting.GREEN + event.getHarvester().getName() + TextFormatting.RESET + TextFormatting.BOLD + "!"));
            yellowFlagLocation = pos;
            GameStartCommand.yellowStealer = event.getHarvester();
            event.getHarvester().addPotionEffect(new PotionEffect(MobEffects.GLOWING, 999999, 1));
        } else if (block == ModBlocks.ore_lead && GameStartCommand.oresStarted) {
            GameStartCommand.leadOres.add(pos);
        } else if (block == ModBlocks.ore_copper && GameStartCommand.oresStarted) {
            GameStartCommand.copperOres.add(pos);
        } else if (block == ModBlocks.ore_uranium && GameStartCommand.oresStarted) {
            GameStartCommand.uraniumOres.add(pos);
        } else if (block != Block.getBlockFromName(Config.breakableBlock) && replaceBlocks) {
            event.getDrops().clear();
            Thread replace = new Thread(() -> {
                try {
                    Thread.sleep(Config.blockReplaceDelay);
                    event.getWorld().setBlockState(pos, event.getState());
                } catch (InterruptedException e) {
                    System.out.println("Interrupted while waiting for block state");
                }
            });
            replace.start();
        }
    }

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
        Block block = event.getPlacedBlock().getBlock();
        BlockPos pos = event.getPos();
        if (block != Block.getBlockFromName(Config.breakableBlock) && replaceBlocks) {
            Thread replace = new Thread(() -> {
                try {
                    Thread.sleep(Config.blockReplaceDelay);
                    event.getWorld().setBlockToAir(pos);
                    if (event.getEntity() instanceof EntityPlayer) {
                        EntityPlayer player = (EntityPlayer) event.getEntity();
                        ItemStack item = new ItemStack(block, 1, block.getMetaFromState(event.getState()));
                        player.inventory.addItemStackToInventory(item);
                    }
                } catch (InterruptedException e) {
                    System.out.println("Interrupted while waiting for block state");
                }
            });
            replace.start();
        }
    }

    @SubscribeEvent
    public static void onDamage(LivingDamageEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            if (!GameStartCommand.gameStarted || playersRespawn.contains((EntityPlayer) event.getEntity())) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onExplosion(ExplosionEvent.Detonate event) {
        if (!explosionEnable) {
            event.getAffectedBlocks().clear();
        }
    }

    public static void returnGreenFlag() {
        server.getWorld(0).setBlockState(greenFlagLocation, ModBlocks.flag_block_green.getDefaultState());
        GameStartCommand.greenStealer.removePotionEffect(MobEffects.GLOWING);
        GameStartCommand.greenStealer = null;
    }

    public static void returnYellowFlag() {
        server.getWorld(0).setBlockState(yellowFlagLocation, ModBlocks.flag_block_yellow.getDefaultState());
        GameStartCommand.yellowStealer.removePotionEffect(MobEffects.GLOWING);
        GameStartCommand.yellowStealer = null;
    }

    public static void endGame() {
        GameStartCommand.gameStarted = false;
        GameStartCommand.oresStarted = false;
        String team;
        if (teamGreen.nbPoints >= Config.winningPoints) {
            team = TextFormatting.GREEN + "Green";
        } else {
            team = TextFormatting.YELLOW + "Yellow";
        }
        server.getPlayerList().sendMessage(new TextComponentString(TextFormatting.BOLD + "The " +
                team + TextFormatting.RESET + TextFormatting.BOLD + " team wins the game by accumulating 3 points!"));
    }
}




