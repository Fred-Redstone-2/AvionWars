package fredstone.avionwars.items;

import fredstone.avionwars.config.AvionWars;
import net.minecraft.item.Item;

public abstract class IngotBase extends Item {
    public IngotBase(String name) {
        setUnlocalizedName(AvionWars.MODID + ".ingot_" + name);
        setRegistryName("ingot_" + name);
        setCreativeTab(AvionWars.CREATIVE_TAB);
    }
}
