package fredstone.avionwars.items;

import fredstone.avionwars.config.AvionWars;
import net.minecraft.item.Item;

public class Flag extends Item {
    public Flag() {
        setUnlocalizedName(AvionWars.MODID + ".flag");
        setRegistryName("flag");
    }
}
