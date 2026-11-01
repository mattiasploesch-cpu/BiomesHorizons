package biomesoplenty.common.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import biomesoplenty.BiomesOPlenty;
import biomesoplenty.api.content.BOPCBlocks;

public class BlockBOPMushroom extends BlockBush {

    private static final String[] plants = new String[] { "toadstool", "portobello", "bluemilk", "glowshroom",
        "flatmushroom", "shadowshroom" };
    private IIcon[] textures;

    public BlockBOPMushroom() {
        super(Material.plants);
        this.setHardness(0.0F);
        this.setStepSound(Block.soundTypeGrass);
        this.setTickRandomly(true);
        float var4 = 0.2F;
        this.setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 0.4F, 0.7F);
        this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
    }

    public void registerBlockIcons(IIconRegister iconRegister) {
        this.textures = new IIcon[plants.length];

        for (int i = 0; i < plants.length; i++) {
            this.textures[i] = iconRegister.registerIcon("biomesoplenty:" + plants[i]);
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

    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        return meta == 3 ? 6 : 0;
    }

    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        switch (meta) {
            case 0:
                this.setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 0.4F, 0.7F);
                break;
            default:
                this.setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 0.4F, 0.7F);
        }
    }

    public void getSubBlocks(Item block, CreativeTabs creativeTabs, List list) {
        for (int i = 0; i < plants.length; i++) {
            list.add(new ItemStack(block, 1, i));
        }
    }

    public boolean isValidPosition(World world, int x, int y, int z, int metadata) {
        Block block = world.getBlock(x, y - 1, z);
        switch (metadata) {
            case 0:
                return block == Blocks.grass || block == Blocks.dirt
                    || block == Blocks.mycelium
                    || block == Blocks.netherrack
                    || block == BOPCBlocks.overgrownNetherrack;
            case 1:
                return block == Blocks.grass || block == Blocks.dirt || block == Blocks.mycelium;
            case 2:
                return block == Blocks.grass || block == Blocks.dirt || block == Blocks.mycelium;
            case 3:
                return block == Blocks.grass || block == Blocks.dirt
                    || block == Blocks.mycelium
                    || block == Blocks.stone
                    || block == Blocks.netherrack
                    || block == BOPCBlocks.overgrownNetherrack;
            case 4:
            default:
                return block == Blocks.grass || block == Blocks.dirt
                    || block == Blocks.mycelium
                    || block == BOPCBlocks.overgrownNetherrack
                    || block == BOPCBlocks.bopGrass;
            case 5:
                return block == Blocks.grass || block == Blocks.dirt
                    || block == Blocks.mycelium
                    || block == Blocks.end_stone
                    || block == BOPCBlocks.bopGrass;
        }
    }

    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side) {
        return this.isValidPosition(world, x, y, z, -1);
    }

    public boolean canBlockStay(World world, int x, int y, int z) {
        return this.isValidPosition(world, x, y, z, -1);
    }

    public int damageDropped(int meta) {
        return meta & 15;
    }
}
