package biomesoplenty.common.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import biomesoplenty.common.blocks.BlockBOPLog;

public class ItemBlockLog extends ItemBlock {

    public ItemBlockLog(Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    public int getMetadata(int meta) {
        return meta & 3;
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        BlockBOPLog block = (BlockBOPLog) this.field_150939_a;
        return super.getUnlocalizedName() + "." + block.getWoodType(itemStack.getItemDamage()) + "Wood";
    }
}
