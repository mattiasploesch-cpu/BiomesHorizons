package biomesoplenty.common.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemBlockColorizedSapling extends ItemBlock {

    private static final String[] saplings = new String[] { "sacredoak", "mangrove", "palm", "redwood", "willow",
        "pine", "mahogany", "flowering" };

    public ItemBlockColorizedSapling(Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    public int getMetadata(int meta) {
        return meta & 15;
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        int meta = itemStack.getItemDamage();
        if (meta < 0 || meta >= saplings.length) {
            meta = 0;
        }

        return super.getUnlocalizedName() + "." + saplings[meta] + "Sapling";
    }

    public IIcon getIconFromDamage(int meta) {
        return this.field_150939_a.getIcon(0, meta);
    }

    public boolean hasEffect(ItemStack itemStack, int pass) {
        return itemStack.getItemDamage() == 0;
    }
}
