package biomesoplenty.common.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import biomesoplenty.api.content.BOPCBlocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockIvy extends ItemColored {

    @SideOnly(Side.CLIENT)
    private IIcon texture;

    public ItemBlockIvy(Block block) {
        super(block, false);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.texture = iconRegister.registerIcon("biomesoplenty:ivy");
    }

    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemStack, int par2) {
        return BOPCBlocks.ivy.getRenderColor(itemStack.getItemDamage());
    }

    public IIcon getIconFromDamage(int meta) {
        return this.texture;
    }
}
