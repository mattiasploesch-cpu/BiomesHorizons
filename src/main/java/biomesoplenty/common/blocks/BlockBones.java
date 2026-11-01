package biomesoplenty.common.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import biomesoplenty.BiomesOPlenty;
import biomesoplenty.client.render.RenderUtils;

public class BlockBones extends Block {

    private static final String[] boneTypes = new String[] { "bones_small", "bones_medium", "bones_large" };
    private IIcon[] textures;

    public BlockBones() {
        super(Material.rock);
        this.setHardness(3.0F);
        this.setResistance(5.0F);
        this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
    }

    public void registerBlockIcons(IIconRegister iconRegister) {
        this.textures = new IIcon[boneTypes.length];

        for (int i = 0; i < boneTypes.length; i++) {
            this.textures[i] = iconRegister.registerIcon("biomesoplenty:" + boneTypes[i]);
        }
    }

    public IIcon getIcon(int side, int meta) {
        if (meta == 5 || meta == 6) {
            meta = 1;
        }

        if (meta < 0 || meta >= this.textures.length) {
            meta = 0;
        }

        return this.textures[meta];
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        switch (meta) {
            case 0:
                return AxisAlignedBB.getBoundingBox(x + 0.374, y, z + 0.374, x + 0.626, y + 1.0, z + 0.626);
            case 1:
                return AxisAlignedBB.getBoundingBox(x + 0.187, y, z + 0.187, x + 0.813, y + 1.0, z + 0.813);
            case 2:
            default:
                return AxisAlignedBB.getBoundingBox(x, y, z, x + 1.0, y + 1.0, z + 1.0);
            case 3:
                return AxisAlignedBB.getBoundingBox(x + 0.374, y + 0.374, z, x + 0.626, y + 0.626, z + 1.0);
            case 4:
                return AxisAlignedBB.getBoundingBox(x, y + 0.374, z + 0.374, x + 1.0, y + 0.626, z + 0.626);
            case 5:
                return AxisAlignedBB.getBoundingBox(x + 0.187, y + 0.187, z, x + 0.813, y + 0.813, z + 1.0);
            case 6:
                return AxisAlignedBB.getBoundingBox(x, y + 0.187, z + 0.187, x + 1.0, y + 0.813, z + 0.813);
        }
    }

    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        switch (meta) {
            case 0:
                return AxisAlignedBB.getBoundingBox(x + 0.374, y, z + 0.374, x + 0.626, y + 1.0, z + 0.626);
            case 1:
                return AxisAlignedBB.getBoundingBox(x + 0.187, y, z + 0.187, x + 0.813, y + 1.0, z + 0.813);
            case 2:
            default:
                return AxisAlignedBB.getBoundingBox(x, y, z, x + 1.0, y + 1.0, z + 1.0);
            case 3:
                return AxisAlignedBB.getBoundingBox(x + 0.374, y + 0.374, z, x + 0.626, y + 0.626, z + 1.0);
            case 4:
                return AxisAlignedBB.getBoundingBox(x, y + 0.374, z + 0.374, x + 1.0, y + 0.626, z + 0.626);
            case 5:
                return AxisAlignedBB.getBoundingBox(x + 0.187, y + 0.187, z, x + 0.813, y + 0.813, z + 1.0);
            case 6:
                return AxisAlignedBB.getBoundingBox(x, y + 0.187, z + 0.187, x + 1.0, y + 0.813, z + 0.813);
        }
    }

    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        float minX;
        float minY;
        float minZ;
        float maxX;
        float maxY;
        float maxZ;
        switch (meta) {
            case 0:
                minY = 0.0F;
                minZ = 0.374F;
                minX = 0.374F;
                maxZ = 0.626F;
                maxX = 0.626F;
                maxY = 1.0F;
                break;
            case 1:
                minY = 0.0F;
                minZ = 0.187F;
                minX = 0.187F;
                maxZ = 0.813F;
                maxX = 0.813F;
                maxY = 1.0F;
                break;
            case 2:
            default:
                minY = 0.0F;
                minZ = 0.0F;
                minX = 0.0F;
                maxZ = 1.0F;
                maxX = 1.0F;
                maxY = 1.0F;
                break;
            case 3:
                minY = 0.374F;
                minX = 0.374F;
                minZ = 0.0F;
                maxY = 0.626F;
                maxX = 0.626F;
                maxZ = 1.0F;
                break;
            case 4:
                minX = 0.0F;
                minZ = 0.374F;
                minY = 0.374F;
                maxX = 1.0F;
                maxZ = 0.626F;
                maxY = 0.626F;
                break;
            case 5:
                minY = 0.187F;
                minX = 0.187F;
                minZ = 0.0F;
                maxY = 0.813F;
                maxX = 0.813F;
                maxZ = 1.0F;
                break;
            case 6:
                minX = 0.0F;
                minZ = 0.187F;
                minY = 0.187F;
                maxX = 1.0F;
                maxZ = 0.813F;
                maxY = 0.813F;
        }

        this.setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public int getDamageValue(World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta == 3 || meta == 4) {
            meta = 0;
        }

        if (meta == 5 || meta == 6) {
            meta = 1;
        }

        return meta;
    }

    public void getSubBlocks(Item block, CreativeTabs creativeTabs, List list) {
        for (int i = 0; i < boneTypes.length; i++) {
            list.add(new ItemStack(block, 1, i));
        }
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public int getRenderType() {
        return RenderUtils.bonesModel;
    }

    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        return true;
    }

    public int damageDropped(int meta) {
        if (meta == 3 || meta == 4) {
            meta = 0;
        }

        if (meta == 5 || meta == 6) {
            meta = 1;
        }

        return meta;
    }
}
