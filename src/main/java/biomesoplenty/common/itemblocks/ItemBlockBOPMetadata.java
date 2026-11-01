package biomesoplenty.common.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

import biomesoplenty.common.utils.ISubLocalization;

public class ItemBlockBOPMetadata extends ItemBlockWithMetadata {

    private Block block;

    public ItemBlockBOPMetadata(Block block) {
        super(block, block);
        this.block = block;
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        if (this.block instanceof ISubLocalization) {
            try {
                return ((ISubLocalization) this.block)
                    .getUnlocalizedName(super.getUnlocalizedName(itemStack), itemStack);
            } catch (Exception e) {
                return null;
            }
        } else {
            return super.getUnlocalizedName(itemStack);
        }
    }
}
