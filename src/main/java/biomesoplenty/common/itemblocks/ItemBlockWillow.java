package biomesoplenty.common.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import biomesoplenty.api.content.BOPCBlocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockWillow extends ItemColored {

    @SideOnly(Side.CLIENT)
    private IIcon texture;

    public ItemBlockWillow(Block block) {
        super(block, false);
    }

    public void registerIcons(IIconRegister iconRegister) {
        this.texture = iconRegister.registerIcon("biomesoplenty:willow");
    }

    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemStack, int par2) {
        return BOPCBlocks.willow.getRenderColor(itemStack.getItemDamage());
    }

    public IIcon getIconFromDamage(int meta) {
        return this.texture;
    }
}
