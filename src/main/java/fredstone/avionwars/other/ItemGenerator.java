package fredstone.avionwars.other;

import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class ItemGenerator {
    public static ItemStack getHelmet(boolean isGreen) {
        ItemStack helmet = new ItemStack(Items.LEATHER_HELMET);
        if (isGreen) {
            applyDataGreen(helmet);
        }
        else {
            applyDataYellow(helmet);
        }
        return helmet;
    }

    public static ItemStack getChestplate(boolean isGreen) {
        ItemStack chestplate = new ItemStack(Items.LEATHER_CHESTPLATE);
        if (isGreen) {
            applyDataGreen(chestplate);
        }
        else {
            applyDataYellow(chestplate);
        }
        return chestplate;
    }

    public static ItemStack getLeggings(boolean isGreen) {
        ItemStack leggings = new ItemStack(Items.LEATHER_LEGGINGS);
        if (isGreen) {
            applyDataGreen(leggings);
        }
        else {
            applyDataYellow(leggings);
        }
        return leggings;
    }

    public static ItemStack getBoots(boolean isGreen) {
        ItemStack boots = new ItemStack(Items.LEATHER_BOOTS);
        if (isGreen) {
            applyDataGreen(boots);
        }
        else {
            applyDataYellow(boots);
        }
        return boots;
    }

    private static void applyDataGreen(ItemStack item) {
        applyEnchantments(item);
        item.addEnchantment(Enchantments.BINDING_CURSE, 1);
        item.addEnchantment(Enchantments.PROTECTION, 5);
        NBTTagCompound color = new NBTTagCompound();
        color.setInteger("color", 6192150);
        item.getTagCompound().setTag("display", color);
    }

    private static void applyDataYellow(ItemStack item) {
        applyEnchantments(item);
        item.addEnchantment(Enchantments.BINDING_CURSE, 1);
        item.addEnchantment(Enchantments.PROTECTION, 5);
        NBTTagCompound color = new NBTTagCompound();
        color.setInteger("color", 16701501);
        item.getTagCompound().setTag("display", color);
    }

    public static ItemStack getSword() {
        ItemStack sword = new ItemStack(Items.IRON_SWORD);
        applyEnchantments(sword);
        sword.addEnchantment(Enchantments.SHARPNESS, 5);
        sword.setStackDisplayName("Iron Sting");
        NBTTagCompound displayTag = (NBTTagCompound) sword.getTagCompound().getTag("display");

        NBTTagList loreList = new NBTTagList();
        NBTTagString lore = new NBTTagString("A legendary sword only used by the bravest of knights.");
        loreList.appendTag(lore);

        displayTag.setTag("Lore", loreList);
        sword.setTagInfo("display", displayTag);
        return sword;
    }

    public static ItemStack getPickaxe() {
        ItemStack pickaxe = new ItemStack(Items.IRON_PICKAXE);
        applyEnchantments(pickaxe);
        pickaxe.addEnchantment(Enchantments.EFFICIENCY, 4);

        pickaxe.setStackDisplayName("Soldier's Pick");
        NBTTagCompound displayTag = (NBTTagCompound) pickaxe.getTagCompound().getTag("display");

        NBTTagList loreList = new NBTTagList();
        NBTTagString lore = new NBTTagString("A useful pickaxe that can serve every situation.");
        loreList.appendTag(lore);

        displayTag.setTag("Lore", loreList);
        pickaxe.setTagInfo("display", displayTag);
        return pickaxe;
    }

    private static void applyEnchantments(ItemStack item) {
        item.addEnchantment(EnchantmentInit.DEATH_KEEPING, 1);
        item.getTagCompound().setBoolean("Unbreakable", true);
    }

    public static ItemStack getFood() {
        ItemStack steaks = new ItemStack(Items.COOKED_BEEF);
        steaks.setCount(64);
        return steaks;
    }
}
