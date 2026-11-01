package biomesoplenty.common.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemBlockSapling extends ItemBlock {

    private static final String[] saplings = new String[] { "apple", "yellowautumn", "bamboo", "magic", "dark", "dead",
        "fir", "ethereal", "orangeautumn", "origin", "pinkcherry", "maple", "whitecherry", "hellbark", "jacaranda",
        "persimmon" };
    private static final int MAX = 15;

    public ItemBlockSapling(Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    public int getMetadata(int meta) {
        return meta & 15;
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        int meta = itemStack.getItemDamage() > 15 ? 0 : itemStack.getItemDamage();
        return super.getUnlocalizedName() + "." + saplings[meta] + "Sapling";
    }

    public IIcon getIconFromDamage(int meta) {
        return this.field_150939_a.getIcon(0, meta);
    }
}
