package biomesoplenty.common.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import biomesoplenty.BiomesOPlenty;
import biomesoplenty.api.content.BOPCBlocks;

public class BlockBOPSlab extends BlockSlab {

    private static final String[] woodTypes = new String[] { "sacredoak", "cherry", "dark", "fir", "ethereal", "magic",
        "mangrove", "palm", "redwood", "willow", "pine", "hell_bark", "jacaranda", "mahogany" };
    private static final String[] rockTypes = new String[] { "mudbrick" };
    private IIcon[] textures;
    private final BlockBOPSlab.SlabCategory category;

    public BlockBOPSlab(boolean isDoubleSlab, Material material, BlockBOPSlab.SlabCategory cat) {
        super(isDoubleSlab, material);
        this.category = cat;
        if (material == Material.wood) {
            this.setHardness(2.0F);
            this.setResistance(5.0F);
            this.setStepSound(Block.soundTypeWood);
        } else if (material == Material.rock) {
            this.setStepSound(Block.soundTypePiston);
        }

        if (!isDoubleSlab) {
            this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
        }

        this.useNeighborBrightness = true;
    }

    public void registerBlockIcons(IIconRegister iconRegister) {
        if (this.category == BlockBOPSlab.SlabCategory.STONE) {
            this.textures = new IIcon[rockTypes.length];

            for (int i = 0; i < rockTypes.length; i++) {
                this.textures[i] = iconRegister.registerIcon("biomesoplenty:" + rockTypes[i]);
            }
        } else {
            this.textures = new IIcon[woodTypes.length];

            for (int i = 0; i < woodTypes.length; i++) {
                this.textures[i] = iconRegister.registerIcon("biomesoplenty:plank_" + woodTypes[i]);
            }
        }
    }

    public IIcon getIcon(int side, int meta) {
        return this.category == BlockBOPSlab.SlabCategory.STONE ? this.textures[this.getRockType(meta)]
            : this.textures[this.getWoodType(meta)];
    }

    public void getSubBlocks(Item block, CreativeTabs creativeTabs, List list) {
        int max = 0;
        if (this.category == BlockBOPSlab.SlabCategory.WOOD1) {
            max = 8;
        } else if (this.category == BlockBOPSlab.SlabCategory.WOOD2) {
            max = 5;
        } else if (this.category == BlockBOPSlab.SlabCategory.STONE) {
            max = 1;
        }

        for (int i = 0; i < max; i++) {
            list.add(new ItemStack(block, 1, i));
        }
    }

    public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return this.category == BlockBOPSlab.SlabCategory.WOOD2 && world.getBlockMetadata(x, y, z) == 3 ? 0
            : Blocks.fire.getFlammability(this);
    }

    public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return this.category == BlockBOPSlab.SlabCategory.WOOD2 && world.getBlockMetadata(x, y, z) == 3 ? 0
            : Blocks.fire.getEncouragement(this);
    }

    public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return this.category == BlockBOPSlab.SlabCategory.WOOD2 && world.getBlockMetadata(x, y, z) == 3 ? false
            : this.getFlammability(world, x, y, z, face) > 0;
    }

    public String func_150002_b(int meta) {
        return this.category == BlockBOPSlab.SlabCategory.STONE ? rockTypes[this.getRockType(meta)] + "Slab"
            : woodTypes[this.getWoodType(meta)] + "Slab";
    }

    public int damageDropped(int meta) {
        return meta & 7;
    }

    public Item getItemDropped(int metadata, Random random, int fortune) {
        if (this.field_150004_a) {
            if (this == BOPCBlocks.woodenDoubleSlab1) {
                return Item.getItemFromBlock(BOPCBlocks.woodenSingleSlab1);
            } else {
                return this == BOPCBlocks.woodenDoubleSlab2 ? Item.getItemFromBlock(BOPCBlocks.woodenSingleSlab2)
                    : Item.getItemFromBlock(BOPCBlocks.stoneSingleSlab);
            }
        } else {
            return Item.getItemFromBlock(this);
        }
    }

    public float getBlockHardness(World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        float hardness = this.blockHardness;
        if (this.category == BlockBOPSlab.SlabCategory.STONE) {
            switch (getTypeFromMeta(meta)) {
                case 0:
                case 3:
                    hardness = 1.6F;
                    break;
                case 1:
                case 4:
                    hardness = 1.1F;
                    break;
                case 2:
                    hardness = 1.0F;
            }
        }

        return hardness;
    }

    public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX,
        double explosionY, double explosionZ) {
        int meta = world.getBlockMetadata(x, y, z);
        float resistance = this.blockResistance;
        if (this.category == BlockBOPSlab.SlabCategory.STONE) {
            switch (getTypeFromMeta(meta)) {
                case 0:
                case 3:
                    resistance = 7.0F;
                    break;
                case 1:
                case 4:
                    resistance = 7.5F;
                    break;
                case 2:
                    resistance = 2.0F;
            }
        }

        return resistance / 5.0F;
    }

    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
        Block block = (Block) (!this.field_150004_a ? this
            : (this == BOPCBlocks.woodenDoubleSlab1 ? BOPCBlocks.woodenSingleSlab1
                : (this == BOPCBlocks.woodenDoubleSlab2 ? BOPCBlocks.woodenSingleSlab2 : BOPCBlocks.stoneSingleSlab)));
        return new ItemStack(block, 1, world.getBlockMetadata(x, y, z));
    }

    protected ItemStack createStackedBlock(int meta) {
        return new ItemStack(this, 2, meta);
    }

    private int getWoodType(int meta) {
        meta = getTypeFromMeta(meta) + this.category.ordinal() * 8;
        return meta < woodTypes.length ? meta : 0;
    }

    private int getRockType(int meta) {
        meta = getTypeFromMeta(meta);
        return meta < rockTypes.length ? meta : 0;
    }

    private static int getTypeFromMeta(int meta) {
        return meta & 7;
    }

    public enum SlabCategory {
        WOOD1,
        WOOD2,
        STONE;
    }
}
