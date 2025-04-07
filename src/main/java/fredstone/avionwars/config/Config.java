package fredstone.avionwars.config;

import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

public class Config {
    private static final String CATEGORY_TEAMS = "Teams";
    private static final String CATEGORY_GENERAL = "General";
    private static final String CATEGORY_ORES = "Ores";

    // This values below you can access elsewhere in your mod:
    public static String spawnYellow = "0,80,0";
    public static String spawnGreen = "0,80,0";
    public static int preparationDelay = 20 * 60;
    public static int spawnImmunity = 6;
    public static int spawnDelay = 5;
    public static int winningPoints = 3;
    public static int flagReturnRadius = 5;
    public static int deathsForPoint = 10;
    public static String breakableBlock = "cobblestone";
    public static int blockReplaceDelay = 20;
    public static int ingotPercentToDrop = 50;
    public static int leadRespawnTime = 60;
    public static int copperRespawnTime = 60;
    public static int uraniumRespawnTime = 60;


    // Call this from CommonProxy.preInit(). It will create our config if it doesn't
    // exist yet and read the values if it does exist.
    public static void readConfig() {
        Configuration cfg = CommonProxy.config;
        try {
            cfg.load();
            initGeneralConfig(cfg);
            initOreConfig(cfg);
            initTeamConfig(cfg);
        } catch (Exception e1) {
            AvionWars.logger.log(Level.ERROR, "Problem loading config file!", e1);
        } finally {
            if (cfg.hasChanged()) {
                cfg.save();
            }
        }
    }

    private static void initGeneralConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General configuration");
        breakableBlock = cfg.getString("Breakable block", CATEGORY_GENERAL, breakableBlock,
                "Set the block you want to be able to break during the game (the building block)");
        blockReplaceDelay = cfg.getInt("Delay before replacing blocks", CATEGORY_GENERAL, blockReplaceDelay,
                0, 100, "Set the Integer delay before replacing blocks in ms");
        ingotPercentToDrop = cfg.getInt("Percentage of ingots to drop when dead", CATEGORY_GENERAL, ingotPercentToDrop,
                0, 100, "Set the Integer percentage of ingots to drop when dead");
        preparationDelay = cfg.getInt("Time for preparation", CATEGORY_GENERAL, preparationDelay,
                1, 40 * 60, "Set the Integer for the preparation time in seconds");
        winningPoints = cfg.getInt("Points to win the game", CATEGORY_GENERAL, winningPoints,
                1, 10, "Set the Integer for the amount of points to win the game");
        flagReturnRadius = cfg.getInt("Radius around the team spawnpoint that will be accepted for the flag to be returned",
                CATEGORY_GENERAL, flagReturnRadius, 1, 10, "Set the Integer for the radius in blocks");
        deathsForPoint = cfg.getInt("Number of deaths for the enemy team to get a point", CATEGORY_GENERAL, deathsForPoint,
                1, 100, "Set the Integer for the number of deaths");
        spawnImmunity = cfg.getInt("Delay before the player takes damage after respawning", CATEGORY_GENERAL,
                spawnImmunity, 0, 100, "Set the Integer delay for player respawn immunity");
        spawnDelay = cfg.getInt("Delay before the player can move after respawning", CATEGORY_GENERAL,
                spawnDelay, 0, 100, "Set the Integer delay for player respawn delay");
    }

    private static void initOreConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_ORES, "Ore configuration");
        leadRespawnTime = cfg.getInt("Lead Ores respawn time", CATEGORY_ORES, leadRespawnTime,
                1, 120, "Set the Integer delay before replacing ores in seconds");
        copperRespawnTime = cfg.getInt("Copper Ores respawn time", CATEGORY_ORES, copperRespawnTime,
                1, 120, "Set the Integer delay before replacing ores in seconds");
        uraniumRespawnTime = cfg.getInt("Uranium Ores respawn time", CATEGORY_ORES, uraniumRespawnTime,
                1, 120, "Set the Integer delay before replacing ores in seconds");
    }

    private static void initTeamConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_TEAMS, "Team configuration");
        spawnYellow = cfg.getString("Yellow Team spawnpoint", CATEGORY_TEAMS, spawnYellow, "Set the spawnpoint here in format x,y,z");
        spawnGreen = cfg.getString("Green Team spawnpoint", CATEGORY_TEAMS, spawnGreen, "Set the spawnpoint here in format x,y,z");
    }
}
