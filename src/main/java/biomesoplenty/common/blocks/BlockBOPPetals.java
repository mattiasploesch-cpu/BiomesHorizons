package biomesoplenty.common.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IShearable;

import biomesoplenty.BiomesOPlenty;

public class BlockBOPPetals extends BlockLeavesBase implements IShearable {

    private static final String[] petals = new String[] { "bigflowerred", "bigfloweryellow" };
    private IIcon[][] textures;

    public BlockBOPPetals() {
        super(Material.leaves, false);
        this.setTickRandomly(true);
        this.setHardness(0.2F);
        this.setStepSound(Block.soundTypeGrass);
        this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
    }

    public void registerBlockIcons(IIconRegister iconRegister) {
        this.textures = new IIcon[petals.length][2];

        for (int i = 0; i < petals.length; i++) {
            this.textures[i][0] = iconRegister.registerIcon("biomesoplenty:" + petals[i]);
            this.textures[i][1] = iconRegister.registerIcon("biomesoplenty:better_" + petals[i]);
        }
    }

    public IIcon getIcon(int side, int meta) {
        return this.textures[meta >= 0 && meta < this.textures.length ? meta : 0][0];
    }

    public IIcon getIconBetterLeaves(int meta, float randomIndex) {
        return this.textures[meta >= 0 && meta < this.textures.length ? meta : 0][1];
    }

    public void getSubBlocks(Item block, CreativeTabs creativeTabs, List list) {
        for (int i = 0; i < this.textures.length; i++) {
            list.add(new ItemStack(block, 1, i));
        }
    }

    public Item getItemDropped(int metadata, Random random, int fortune) {
        return metadata == 0 ? Item.getItemFromBlock(Blocks.red_flower) : Item.getItemFromBlock(Blocks.yellow_flower);
    }

    public int damageDropped(int meta) {
        return 0;
    }

    public int quantityDropped(Random random) {
        return random.nextInt(20) == 0 ? 1 : 0;
    }

    public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z) {
        return true;
    }

    public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<>();
        ret.add(new ItemStack(this, 1, world.getBlockMetadata(x, y, z) & 15));
        return ret;
    }
}
