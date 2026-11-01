package biomesoplenty.common.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import biomesoplenty.api.content.BOPCBlocks;

public class BlockBOPCoral extends BOPBlock {

    private static final String[] coral = new String[] { "kelpbottom", "kelpmiddle", "kelptop", "kelpsingle",
        "pinkcoral", "orangecoral", "bluecoral", "glowcoral", "algae" };
    private IIcon[] textures;
    private final BlockBOPCoral.CoralCategory category;

    public BlockBOPCoral(BlockBOPCoral.CoralCategory category) {
        super(Material.water);
        this.category = category;
        this.setHardness(0.0F);
        this.setStepSound(Block.soundTypeGrass);
        this.setTickRandomly(true);
        float f = 0.4F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.8F, 0.5F + f);
    }

    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
        return meta < 8 ? meta + 8 : meta;
    }

    public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
        return false;
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return null;
    }

    @Override
    public boolean canReplace(World world, int x, int y, int z, int side, ItemStack itemStack) {
        int metadata = itemStack != null ? itemStack.getItemDamage() : 0;
        return world.getBlock(x, y + 1, z) == Blocks.water && this.canBlockStay(world, x, y, z, metadata);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
        int meta = world.getBlockMetadata(x, y, z);
        if (this.category == BlockBOPCoral.CoralCategory.CAT1 && meta >= 8 && meta <= 11) {
            if (world.getBlock(x, y - 1, z) != this) {
                if (world.getBlock(x, y + 1, z) != this) {
                    world.setBlock(x, y, z, this, 11, 2);
                } else {
                    world.setBlock(x, y, z, this, 8, 2);
                }
            } else if (world.getBlock(x, y + 1, z) != this && world.getBlock(x, y - 1, z) == this) {
                world.setBlock(x, y, z, this, 10, 2);
            } else if (world.getBlock(x, y + 1, z) == this) {
                world.setBlock(x, y, z, this, 9, 2);
            }
        }

        super.onNeighborBlockChange(world, x, y, z, neighborBlock);
    }

    @Override
    public boolean canBlockStay(World world, int x, int y, int z, int metadata) {
        Block block = world.getBlock(x, y - 1, z);
        if (this.category == BlockBOPCoral.CoralCategory.CAT1) {
            switch (metadata) {
                case 9:
                    return block == this;
                case 10:
                    return block == this;
                case 11:
                    return block == this || block == Blocks.dirt
                        || block == Blocks.sand
                        || block == Blocks.sponge
                        || block == Blocks.stone
                        || block == Blocks.clay
                        || block == Blocks.gravel
                        || block == Blocks.grass
                        || block == BOPCBlocks.mud;
            }
        }

        return block == Blocks.dirt || block == Blocks.sand
            || block == Blocks.sponge
            || block == Blocks.stone
            || block == Blocks.clay
            || block == Blocks.gravel
            || block == Blocks.grass
            || block == BOPCBlocks.mud;
    }

    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        return this.category == BlockBOPCoral.CoralCategory.CAT1 && meta == 15 ? 10 : 0;
    }

    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int silkTouch) {
        world.setBlock(x, y, z, Blocks.water);
    }

    @Override
    public int damageDropped(int meta) {
        return this.category == BlockBOPCoral.CoralCategory.CAT1 && meta < 11 ? 11 : meta;
    }

    public int getDamageValue(World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        return this.category == BlockBOPCoral.CoralCategory.CAT1 && meta < 11 ? 11 : meta;
    }

    public void getSubBlocks(Item block, CreativeTabs creativeTabs, List list) {
        for (int i = 8; i < this.getCategorySize() + 8; i++) {
            if (this.category != BlockBOPCoral.CoralCategory.CAT1 || i > 10) {
                list.add(new ItemStack(block, 1, i));
            }
        }
    }

    public int getRenderType() {
        return 1;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public void registerBlockIcons(IIconRegister iconRegister) {
        this.textures = new IIcon[coral.length];

        for (int i = 0; i < coral.length; i++) {
            this.textures[i] = iconRegister.registerIcon("biomesoplenty:" + coral[i]);
        }
    }

    public IIcon getIcon(int side, int meta) {
        if (meta < 8 || meta >= coral.length + 8) {
            meta = 8;
        }

        return this.textures[this.getMetaIndex(meta)];
    }

    public String getCoralType(int meta) {
        if (meta < 8 || meta >= coral.length + 8) {
            meta = 8;
        }

        return coral[this.getMetaIndex(meta)];
    }

    public int getMetaIndex(int meta) {
        return meta - 8 + this.category.ordinal() * 8;
    }

    public int getCategorySize() {
        return this.category.ordinal() == BlockBOPCoral.CoralCategory.values().length - 1
            ? coral.length - this.category.ordinal() * 8
            : 8;
    }

    public enum CoralCategory {
        CAT1,
        CAT2;
    }
}
