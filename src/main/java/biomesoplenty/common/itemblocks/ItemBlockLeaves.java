package biomesoplenty.common.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import biomesoplenty.common.blocks.BlockBOPLeaves;

public class ItemBlockLeaves extends ItemBlock {

    public ItemBlockLeaves(Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    public int getMetadata(int meta) {
        return meta | 4;
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        BlockBOPLeaves block = (BlockBOPLeaves) this.field_150939_a;
        return super.getUnlocalizedName() + "." + block.getLeafType(itemStack.getItemDamage());
    }
}
