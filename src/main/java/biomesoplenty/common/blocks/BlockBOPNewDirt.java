package biomesoplenty.common.blocks;

import java.util.List;

import net.minecraft.block.BlockDirt;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import biomesoplenty.BiomesOPlenty;
import biomesoplenty.common.utils.ISubLocalization;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBOPNewDirt extends BlockDirt implements ISubLocalization {

    private static final String[] dirtTypes = new String[] { "loamy", "sandy", "silty" };
    private static final IIcon[] dirtIcons = new IIcon[dirtTypes.length * 2];

    public BlockBOPNewDirt() {
        this.setHardness(0.5F);
        this.setHarvestLevel("shovel", 0);
        this.setStepSound(soundTypeGravel);
        this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
    }

    public int damageDropped(int meta) {
        return meta;
    }

    @Override
    public String getUnlocalizedName(String baseName, ItemStack itemStack) {
        int meta = itemStack.getItemDamage();
        return baseName + "." + (this.isCoarseDirt(meta) ? "coarse_dirt_" : "dirt_") + this.getBaseType(meta);
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item block, CreativeTabs creativeTab, List blockList) {
        for (int i = 0; i < dirtTypes.length * 2; i++) {
            blockList.add(new ItemStack(block, 1, i));
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        for (int i = 0; i < dirtTypes.length; i++) {
            String dirtType = dirtTypes[i];
            dirtIcons[i * 2 + 0] = iconRegister.registerIcon("biomesoplenty:dirt_" + dirtType);
            dirtIcons[i * 2 + 1] = iconRegister.registerIcon("biomesoplenty:coarse_dirt_" + dirtType);
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return dirtIcons[meta > dirtIcons.length - 1 ? 0 : meta];
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        int meta = world.getBlockMetadata(x, y, z);
        return this.getIcon(side, meta);
    }

    private boolean isCoarseDirt(int meta) {
        return (meta & 1) != 0;
    }

    private String getBaseType(int meta) {
        return dirtTypes[(meta - (meta & 1)) / 2];
    }

    public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction,
        IPlantable plantable) {
        switch (plantable.getPlantType(world, x, y + 1, z)) {
            case Cave:
                return this.isSideSolid(world, x, y, z, ForgeDirection.UP);
            case Plains:
                return true;
            case Beach:
                return world.getBlock(x - 1, y, z)
                    .getMaterial() == Material.water
                    || world.getBlock(x + 1, y, z)
                        .getMaterial() == Material.water
                    || world.getBlock(x, y, z - 1)
                        .getMaterial() == Material.water
                    || world.getBlock(x, y, z + 1)
                        .getMaterial() == Material.water;
            default:
                return false;
        }
    }

    public int getDamageValue(World world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z);
    }
}
