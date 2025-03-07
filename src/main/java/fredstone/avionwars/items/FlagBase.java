package fredstone.avionwars.items;

import fredstone.avionwars.config.AvionWars;
import net.minecraft.item.Item;

public abstract class FlagBase extends Item {
    public FlagBase(String color) {
        setUnlocalizedName(AvionWars.MODID + ".flag_" + color);
        setRegistryName("flag_" + color);
        setCreativeTab(AvionWars.CREATIVE_TAB);
    }
}
