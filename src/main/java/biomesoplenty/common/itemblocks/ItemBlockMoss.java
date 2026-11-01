package biomesoplenty.common.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockMoss extends ItemBlock {

    @SideOnly(Side.CLIENT)
    private IIcon texture;

    public ItemBlockMoss(Block block) {
        super(block);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.texture = iconRegister.registerIcon("biomesoplenty:item_moss");
    }

    public IIcon getIconFromDamage(int meta) {
        return this.texture;
    }
}
