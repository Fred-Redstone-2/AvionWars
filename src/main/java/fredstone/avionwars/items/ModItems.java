package fredstone.avionwars.items;

import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

public class ModItems {
    @ObjectHolder("avionwars:ingot_lead")
    public static IngotLead ingot_lead;

    @ObjectHolder("avionwars:ingot_copper")
    public static IngotCopper ingot_copper;

    @ObjectHolder("avionwars:ingot_uranium")
    public static IngotUranium ingot_uranium;

    @ObjectHolder("avionwars:flag_green")
    public static FlagGreen flag_green;

    @ObjectHolder("avionwars:flag_yellow")
    public static FlagYellow flag_yellow;

    @ObjectHolder("avionwars:flag")
    public static Flag flag;
}
