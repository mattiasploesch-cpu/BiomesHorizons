package biomesoplenty.common.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;

import biomesoplenty.common.blocks.BlockBOPSlab;

public class ItemBlockSlab extends ItemSlab {

    public ItemBlockSlab(Block block, BlockBOPSlab singleSlab, BlockBOPSlab doubleSlab) {
        super(block, singleSlab, doubleSlab, block == doubleSlab);
    }

    public int getMetadata(int meta) {
        return meta & 7;
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        BlockBOPSlab slab = (BlockBOPSlab) Block.getBlockFromItem(itemStack.getItem());
        return super.getUnlocalizedName() + "." + slab.func_150002_b(itemStack.getItemDamage());
    }
}
