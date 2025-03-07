package fredstone.avionwars.blocks;

import fredstone.avionwars.config.AvionWars;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public abstract class FlagBlockBase extends Block {

    public FlagBlockBase(String color) {
        super(Material.ROCK);
        setUnlocalizedName(AvionWars.MODID + ".flag_block_" + color);
        setRegistryName("flag_block_" + color);
        setCreativeTab(AvionWars.CREATIVE_TAB);
        setHardness(3.0F);
        setResistance(15.0F);
        setHarvestLevel("pickaxe", 2);
    }
}
