package fredstone.avionwars.other;

import fredstone.avionwars.config.AvionWars;
import fredstone.avionwars.items.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class CreativeTab extends CreativeTabs {
    public CreativeTab() {
        super(AvionWars.MODID);
    }

    @SideOnly(Side.CLIENT)
    @Override
    @Nonnull
    public ItemStack getTabIconItem() {
        return new ItemStack(ModItems.flag);
    }
}
