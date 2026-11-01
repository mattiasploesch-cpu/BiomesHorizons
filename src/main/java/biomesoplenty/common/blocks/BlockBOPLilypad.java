package biomesoplenty.common.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import biomesoplenty.BiomesOPlenty;
import biomesoplenty.client.render.RenderUtils;
import biomesoplenty.common.blocks.templates.BOPBlockWorldDecor;

public class BlockBOPLilypad extends BOPBlockWorldDecor {

    private static final String[] lilyTypes = new String[] { "mediumlily", "smalllily", "tinylily" };
    private IIcon[] textures;

    public BlockBOPLilypad() {
        super(Material.plants);
        this.setHardness(0.0F);
        this.setStepSound(Block.soundTypeGrass);
        float f = 0.5F;
        float f1 = 0.015625F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
        this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
    }

    public void registerBlockIcons(IIconRegister iconRegister) {
        this.textures = new IIcon[lilyTypes.length];

        for (int i = 0; i < this.textures.length; i++) {
            this.textures[i] = iconRegister.registerIcon("biomesoplenty:" + lilyTypes[i]);
        }
    }

    public IIcon getIcon(int side, int meta) {
        if (meta >= this.textures.length) {
            meta = 0;
        }

        return this.textures[meta];
    }

    public void getSubBlocks(Item block, CreativeTabs creativeTabs, List list) {
        for (int i = 0; i < lilyTypes.length; i++) {
            list.add(new ItemStack(block, 1, i));
        }
    }

    public void addCollisionBoxesToList(World p_149743_1_, int p_149743_2_, int p_149743_3_, int p_149743_4_,
        AxisAlignedBB p_149743_5_, List p_149743_6_, Entity p_149743_7_) {
        if (p_149743_7_ == null || !(p_149743_7_ instanceof EntityBoat)) {
            super.addCollisionBoxesToList(
                p_149743_1_,
                p_149743_2_,
                p_149743_3_,
                p_149743_4_,
                p_149743_5_,
                p_149743_6_,
                p_149743_7_);
        }
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_,
        int p_149668_4_) {
        return AxisAlignedBB.getBoundingBox(
            p_149668_2_ + this.minX,
            p_149668_3_ + this.minY,
            p_149668_4_ + this.minZ,
            p_149668_2_ + this.maxX,
            p_149668_3_ + this.maxY,
            p_149668_4_ + this.maxZ);
    }

    public int getDamageValue(World world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z);
    }

    public int damageDropped(int meta) {
        return meta;
    }

    @Override
    public boolean isValidPosition(World world, int x, int y, int z, int metadata) {
        Block block = world.getBlock(x, y - 1, z);
        if (block != Blocks.air || world.provider.dimensionId == -1
            || world.getFullBlockLightValue(x, y, z) < 8 && !world.canBlockSeeTheSky(x, y, z)) {
            switch (metadata) {
                default:
                    return block == Blocks.water;
            }
        } else {
            return false;
        }
    }

    public int getRenderType() {
        return RenderUtils.foliageModel;
    }
}
