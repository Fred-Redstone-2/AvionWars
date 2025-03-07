package fredstone.avionwars.blocks;

import fredstone.avionwars.items.ModItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;
import java.util.Random;

public class OreLead extends OreBase {
    public OreLead() {
        super("lead");
    }

    @Override
    @Nonnull
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ModItems.ingot_lead;
    }
}
