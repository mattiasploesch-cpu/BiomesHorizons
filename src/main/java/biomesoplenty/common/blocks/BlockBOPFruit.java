package biomesoplenty.common.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import biomesoplenty.BiomesOPlenty;
import biomesoplenty.api.content.BOPCBlocks;
import biomesoplenty.api.content.BOPCItems;
import biomesoplenty.common.blocks.templates.BOPBlockWorldDecor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBOPFruit extends BOPBlockWorldDecor {

    private static final String[] fruit = new String[] { "apple_block", "persimmon_block", "peach_block", "pear_block",
        "pinecone_block" };
    private IIcon[] textures;

    public BlockBOPFruit() {
        super(Material.plants);
        this.setHardness(0.0F);
        this.setStepSound(Block.soundTypeGrass);
        this.setTickRandomly(true);
        this.setBlockBounds(0.25F, 0.25F, 0.25F, 0.75F, 1.0F, 0.75F);
        this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
    }

    public void registerBlockIcons(IIconRegister iconRegister) {
        this.textures = new IIcon[fruit.length];

        for (int i = 0; i < fruit.length; i++) {
            this.textures[i] = iconRegister.registerIcon("biomesoplenty:" + fruit[i]);
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

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item block, CreativeTabs creativeTabs, List list) {}

    @Override
    public boolean isValidPosition(World world, int x, int y, int z, int metadata) {
        Block block = world.getBlock(x, y + 1, z);
        switch (metadata) {
            default:
                return block == Blocks.leaves || block == Blocks.leaves2
                    || block == BOPCBlocks.leaves1
                    || block == BOPCBlocks.leaves2
                    || block == BOPCBlocks.leaves3
                    || block == BOPCBlocks.leaves4;
        }
    }

    @Override
    public boolean canReplace(World world, int x, int y, int z, int side, ItemStack itemStack) {
        int metadata = itemStack != null ? itemStack.getItemDamage() : 0;
        return this.isValidPosition(world, x, y, z, metadata);
    }

    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        switch (meta) {
            case 0:
                return new ItemStack(Items.apple, 1, 0);
            case 1:
                return new ItemStack(BOPCItems.food, 1, 8);
            case 2:
                return new ItemStack(BOPCItems.food, 1, 3);
            case 3:
                return new ItemStack(BOPCItems.food, 1, 12);
            case 4:
                return new ItemStack(BOPCItems.misc, 1, 13);
            default:
                return new ItemStack(this, 1, meta);
        }
    }

    public int getDamageValue(World world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z);
    }

    public Item getItemDropped(int metadata, Random random, int fortune) {
        if (metadata == 0) {
            return Items.apple;
        } else if (metadata == 1) {
            return BOPCItems.food;
        } else if (metadata == 2) {
            return BOPCItems.food;
        } else if (metadata == 3) {
            return BOPCItems.food;
        } else {
            return metadata == 4 ? BOPCItems.misc : null;
        }
    }

    public int damageDropped(int meta) {
        if (meta == 1) {
            return 8;
        } else if (meta == 2) {
            return 3;
        } else if (meta == 3) {
            return 12;
        } else {
            return meta == 4 ? 13 : 0;
        }
    }

    public int quantityDropped(int meta, int fortune, Random random) {
        return 1;
    }
}
