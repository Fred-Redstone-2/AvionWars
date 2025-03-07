package fredstone.avionwars.other;

import fredstone.avionwars.config.AvionWars;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentDeathKeeping extends Enchantment {
    public EnchantmentDeathKeeping() {
        super(Rarity.RARE, EnumEnchantmentType.ALL, EntityEquipmentSlot.values());
        this.setName(AvionWars.MODID + ".death_keeping");
        this.setRegistryName("death_keeping");
    }

    @Override
    protected boolean canApplyTogether(Enchantment ench) {
        return this != ench && ench != Enchantments.VANISHING_CURSE;
    }
}
