package fredstone.avionwars.config;

import fredstone.avionwars.commands.*;
import fredstone.avionwars.other.CreativeTab;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = AvionWars.MODID, name = AvionWars.NAME, version = AvionWars.VERSION)
public class AvionWars {
    public static final String MODID = "avionwars";
    public static final String NAME = "AvionWars Extension Mod";
    public static final String VERSION = "1.0";
    public static final CreativeTabs CREATIVE_TAB = new CreativeTab();
    public static MinecraftServer server;

    public static Logger logger = LogManager.getLogger(AvionWars.MODID);

    @SidedProxy(clientSide = "fredstone.avionwars.config.ClientProxy", serverSide = "fredstone.avionwars.config.ServerProxy")
    public static CommonProxy proxy;

    @Instance
    public static AvionWars instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        // some example code
        logger.info(AvionWars.NAME + " says hi!");
        proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new TeamJoinCommand());
        event.registerServerCommand(new TeamLeaveCommand());
        event.registerServerCommand(new GameStartCommand());
        event.registerServerCommand(new ToggleBuildingCommand());
        event.registerServerCommand(new SetSpawnpointCommand());
        event.registerServerCommand(new TeamStatusCommand());
        event.registerServerCommand(new ToggleExplosionsCommand());
        event.registerServerCommand(new ResetCommand());
    }
}
