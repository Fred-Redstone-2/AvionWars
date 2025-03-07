package fredstone.avionwars.blocks;

import fredstone.avionwars.items.ModItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;
import java.util.Random;

public class FlagBlockYellow extends FlagBlockBase {
    public FlagBlockYellow() {
        super("yellow");
    }

    @Override
    @Nonnull
    public Item getItemDropped(@Nonnull IBlockState state, @Nonnull Random rand, int fortune) {
        return ModItems.flag_yellow;
    }
}
