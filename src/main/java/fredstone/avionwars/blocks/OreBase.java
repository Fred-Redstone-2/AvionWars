package fredstone.avionwars.blocks;

import fredstone.avionwars.config.AvionWars;
import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import java.util.Random;

public abstract class OreBase extends BlockOre {
    public OreBase(String name) {
        super();
        setUnlocalizedName(AvionWars.MODID + ".ore_" + name);
        setRegistryName("ore_" + name);
        setCreativeTab(AvionWars.CREATIVE_TAB);
        setHardness(3.0F);
        setResistance(15.0F);
        setHarvestLevel("pickaxe", 2);
    }

    @Override
    public int getExpDrop(@Nonnull IBlockState state, @Nonnull net.minecraft.world.IBlockAccess world, @Nonnull BlockPos pos, int fortune) {
        return 0;
    }
}
