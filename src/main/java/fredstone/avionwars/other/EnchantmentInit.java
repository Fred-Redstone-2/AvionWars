package fredstone.avionwars.other;

import fredstone.avionwars.config.AvionWars;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AvionWars.MODID)
public class EnchantmentInit {
    public static final Enchantment DEATH_KEEPING = new EnchantmentDeathKeeping();
}
