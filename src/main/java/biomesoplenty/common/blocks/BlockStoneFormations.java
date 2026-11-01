package biomesoplenty.common.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import biomesoplenty.BiomesOPlenty;
import biomesoplenty.common.blocks.templates.BOPBlockWorldDecor;
import biomesoplenty.common.utils.ISubLocalization;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockStoneFormations extends BOPBlockWorldDecor implements ISubLocalization {

    private static final String[] forms = new String[] { "stalagmite", "stalactite" };
    private IIcon[] textures;

    public BlockStoneFormations() {
        super(Material.vine);
        this.setHardness(0.5F);
        this.setStepSound(Block.soundTypePiston);
        this.setTickRandomly(true);
        float var4 = 0.2F;
        this.setBlockBounds(0.5F - var4, 0.0F, 0.5F - var4, 0.5F + var4, var4 * 3.0F, 0.5F + var4);
        this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
    }

    public void registerBlockIcons(IIconRegister iconRegister) {
        this.textures = new IIcon[forms.length];

        for (int i = 0; i < forms.length; i++) {
            this.textures[i] = iconRegister.registerIcon("biomesoplenty:" + forms[i]);
        }
    }

    public IIcon getIcon(int side, int meta) {
        if (meta < 0 || meta >= this.textures.length) {
            meta = 0;
        }

        return this.textures[meta];
    }

    public int getRenderType() {
        return 1;
    }

    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        switch (meta) {
            default:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item block, CreativeTabs creativeTabs, List list) {
        for (int i = 0; i < forms.length; i++) {
            list.add(new ItemStack(block, 1, i));
        }
    }

    @Override
    public boolean isValidPosition(World world, int x, int y, int z, int metadata) {
        Block blockBottom = world.getBlock(x, y - 1, z);
        Block blockTop = world.getBlock(x, y + 1, z);
        switch (metadata) {
            case 0:
                return blockBottom instanceof BlockStone;
            case 1:
                return blockTop instanceof BlockStone;
            default:
                return blockBottom instanceof BlockStone;
        }
    }

    @Override
    public boolean canReplace(World world, int x, int y, int z, int side, ItemStack itemStack) {
        int metadata = itemStack != null ? itemStack.getItemDamage() : 0;
        return this.isValidPosition(world, x, y, z, metadata);
    }

    public int getDamageValue(World world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z);
    }

    public int damageDropped(int meta) {
        return meta & 15;
    }

    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return true;
    }

    @Override
    public String getUnlocalizedName(String baseName, ItemStack itemStack) {
        return itemStack.getItemDamage() > 1 ? null : baseName + "." + forms[itemStack.getItemDamage()];
    }
}
