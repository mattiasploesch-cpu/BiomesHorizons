package biomesoplenty.common.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import biomesoplenty.BiomesOPlenty;
import biomesoplenty.client.render.RenderUtils;

public class BlockBamboo extends Block implements IPlantable {

    private IIcon bambooSide;
    private IIcon bambooTop;

    public BlockBamboo() {
        super(Material.plants);
        this.setHardness(0.2F);
        this.setStepSound(Block.soundTypeWood);
        this.setTickRandomly(true);
        this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
    }

    public void registerBlockIcons(IIconRegister iconRegister) {
        this.bambooSide = iconRegister.registerIcon("biomesoplenty:bamboo");
        this.bambooTop = iconRegister.registerIcon("biomesoplenty:bambootop");
    }

    public IIcon getIcon(int side, int meta) {
        return side > 1 ? this.bambooSide : this.bambooTop;
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return this.getBoundingBox(world, x, y, z);
    }

    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
        return this.getBoundingBox(world, x, y, z);
    }

    private AxisAlignedBB getBoundingBox(World world, int x, int y, int z) {
        float pixel = 0.0625F;
        return AxisAlignedBB
            .getBoundingBox(x + pixel * 4.0F, y, z + pixel * 4.0F, x + 1 - pixel * 4.0F, y + 1, z + 1 - pixel * 4.0F);
    }

    public void updateTick(World world, int x, int y, int z, Random random) {
        if (world.isAirBlock(x, y + 1, z)) {
            int var6 = 1;

            while (world.getBlock(x, y - var6, z) == this) {
                var6++;
            }

            if (var6 < 3) {
                int var7 = world.getBlockMetadata(x, y, z);
                if (var7 == 15) {
                    world.setBlock(x, y + 1, z, this, 0, 2);
                    world.setBlockMetadataWithNotify(x, y, z, 0, 2);
                } else {
                    world.setBlockMetadataWithNotify(x, y, z, var7 + 1, 2);
                }
            }
        }
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
        this.checkBlockCoordValid(world, x, y, z);
    }

    public final void checkBlockCoordValid(World world, int x, int y, int z) {
        if (!this.canBlockStay(world, x, y, z)) {
            this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlockToAir(x, y, z);
        }
    }

    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return super.canPlaceBlockAt(world, x, y, z) && this.canBlockStay(world, x, y, z);
    }

    public boolean canBlockStay(World world, int x, int y, int z) {
        Block bottomBlock = world.getBlock(x, y - 1, z);
        return bottomBlock.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, this) || bottomBlock == this;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public int getRenderType() {
        return RenderUtils.bambooModel;
    }

    public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z) {
        return false;
    }

    public boolean canSustainLeaves(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
        return EnumPlantType.Plains;
    }

    public Block getPlant(IBlockAccess world, int x, int y, int z) {
        return this;
    }

    public int getPlantMetadata(IBlockAccess world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z);
    }
}
