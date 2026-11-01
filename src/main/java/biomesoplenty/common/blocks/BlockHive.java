package biomesoplenty.common.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import biomesoplenty.BiomesOPlenty;
import biomesoplenty.api.content.BOPCItems;
import biomesoplenty.common.entities.EntityWasp;

public class BlockHive extends Block {

    private static final String[] hiveTypes = new String[] { "honeycomb", "hive", "honeycombempty", "honeycombfilled" };
    private IIcon[] textures;

    public BlockHive() {
        super(Material.wood);
        this.setHardness(0.5F);
        this.setStepSound(Block.soundTypeGrass);
        this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
    }

    public void breakBlock(World world, int x, int y, int z, Block par5, int par6) {
        if (world.getBlockMetadata(x, y, z) == 2) {
            EntityWasp wasp = new EntityWasp(world);
            wasp.setLocationAndAngles(x + 0.6, y, z + 0.3, 0.0F, 0.0F);
            world.spawnEntityInWorld(wasp);
        }
    }

    public void registerBlockIcons(IIconRegister iconRegister) {
        this.textures = new IIcon[hiveTypes.length];

        for (int i = 0; i < hiveTypes.length; i++) {
            this.textures[i] = iconRegister.registerIcon("biomesoplenty:" + hiveTypes[i]);
        }
    }

    public IIcon getIcon(int side, int meta) {
        if (meta < 0 || meta >= hiveTypes.length) {
            meta = 0;
        }

        return this.textures[meta];
    }

    public void getSubBlocks(Item block, CreativeTabs creativeTabs, List list) {
        for (int i = 0; i < hiveTypes.length; i++) {
            list.add(new ItemStack(block, 1, i));
        }
    }

    public Item getItemDropped(int metadata, Random random, int fortune) {
        if (metadata == 0) {
            return BOPCItems.misc;
        } else {
            return metadata == 3 ? BOPCItems.food : Item.getItemFromBlock(this);
        }
    }

    public int damageDropped(int meta) {
        if (meta == 0) {
            return 2;
        } else if (meta == 2) {
            return 0;
        } else {
            return meta == 3 ? 9 : meta;
        }
    }

    public int quantityDropped(int meta, int fortune, Random random) {
        if (meta == 0) {
            return random.nextInt(3) + 1;
        } else if (meta == 2) {
            return 0;
        } else {
            return meta == 3 ? random.nextInt(2) : 1;
        }
    }
}
