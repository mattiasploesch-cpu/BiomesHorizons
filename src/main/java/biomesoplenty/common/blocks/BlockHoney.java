package biomesoplenty.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.world.IBlockAccess;

import biomesoplenty.BiomesOPlenty;

public class BlockHoney extends Block {

    public BlockHoney() {
        super(Material.glass);
        this.setHardness(0.5F);
        this.setStepSound(Block.soundTypePiston);
        this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
    }

    public void registerBlockIcons(IIconRegister iconRegister) {
        this.blockIcon = iconRegister.registerIcon("biomesoplenty:honeyblock");
    }

    public int getRenderBlockPass() {
        return 1;
    }

    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        return super.shouldSideBeRendered(world, x, y, z, 1 - side);
    }

    public boolean isOpaqueCube() {
        return false;
    }
}
