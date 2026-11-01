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
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import biomesoplenty.BiomesOPlenty;
import biomesoplenty.api.content.BOPCBlocks;
import biomesoplenty.client.render.RenderUtils;
import cpw.mods.fml.common.Loader;

public class BlockBOPColorizedLeaves extends BlockLeavesBase implements IShearable {

    private static final String[] leaves = new String[] { "sacredoak", "mangrove", "palm", "redwood", "willow", "pine",
        "mahogany", "flowering" };
    private IIcon[][] textures;
    private final BlockBOPColorizedLeaves.ColourizedLeafCategory category;
    int[] adjacentTreeBlocks;
    public IIcon floweringIcon;

    public BlockBOPColorizedLeaves(BlockBOPColorizedLeaves.ColourizedLeafCategory cat) {
        super(Material.leaves, false);
        this.category = cat;
        this.setTickRandomly(true);
        this.setHardness(0.2F);
        this.setStepSound(Block.soundTypeGrass);
        this.setLightOpacity(1);
        this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
    }

    public void registerBlockIcons(IIconRegister iconRegister) {
        this.textures = new IIcon[3][leaves.length];
        if (Loader.isModLoaded("BetterFoliage")) {
            for (int i = 0; i < leaves.length; i++) {
                this.textures[0][i] = iconRegister.registerIcon("biomesoplenty:leaves_" + leaves[i] + "_round");
                this.textures[1][i] = iconRegister.registerIcon("biomesoplenty:leaves_" + leaves[i] + "_fast");
                this.textures[2][i] = iconRegister.registerIcon("biomesoplenty:better_leaves_" + leaves[i]);
            }
        } else {
            for (int i = 0; i < leaves.length; i++) {
                this.textures[0][i] = iconRegister.registerIcon("biomesoplenty:leaves_" + leaves[i] + "_fancy");
                this.textures[1][i] = iconRegister.registerIcon("biomesoplenty:leaves_" + leaves[i] + "_fast");
            }
        }

        this.floweringIcon = iconRegister.registerIcon("biomesoplenty:leaves_flowering_overlay");
    }

    public IIcon getIconBetterLeaves(int metadata, float randomIndex) {
        int type = getTypeFromMeta(metadata) + this.category.ordinal() * 4;
        return this.textures[2][type >= leaves.length ? 0 : type];
    }

    public IIcon getIconFallingLeaves(int metadata) {
        int type = getTypeFromMeta(metadata) + this.category.ordinal() * 4;
        return this.textures[1][type >= leaves.length ? 0 : type];
    }

    public int getBlockColor() {
        double temperature = 0.5;
        double humidity = 1.0;
        return ColorizerFoliage.getFoliageColor(temperature, humidity);
    }

    public int getRenderColor(int par1) {
        switch (par1) {
            default:
                return ColorizerFoliage.getFoliageColorBasic();
        }
    }

    public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
        int var6 = 0;
        int var7 = 0;
        int var8 = 0;

        for (int var9 = -1; var9 <= 1; var9++) {
            for (int var10 = -1; var10 <= 1; var10++) {
                int var11 = world.getBiomeGenForCoords(x + var10, z + var9)
                    .getBiomeFoliageColor(x + var10, y, z + var9);
                var6 += (var11 & 0xFF0000) >> 16;
                var7 += (var11 & 0xFF00) >> 8;
                var8 += var11 & 0xFF;
            }
        }

        return (var6 / 9 & 0xFF) << 16 | (var7 / 9 & 0xFF) << 8 | var8 / 9 & 0xFF;
    }

    public IIcon getIcon(int side, int metadata) {
        int type = getTypeFromMeta(metadata) + this.category.ordinal() * 4;
        return this.textures[!this.isOpaqueCube() ? 0 : 1][type >= leaves.length ? 0 : type];
    }

    public boolean isOpaqueCube() {
        return Blocks.leaves.isOpaqueCube();
    }

    public void getSubBlocks(Item block, CreativeTabs creativeTabs, List list) {
        for (int i = 0; i < 4; i++) {
            list.add(new ItemStack(block, 1, i));
        }
    }

    public void randomDisplayTick(World world, int x, int y, int z, Random random) {
        if (world.canLightningStrikeAt(x, y + 1, z) && !World.doesBlockHaveSolidTopSurface(world, x, y - 1, z)
            && random.nextInt(15) == 1) {
            double d0 = x + random.nextFloat();
            double d1 = y - 0.05;
            double d2 = z + random.nextFloat();
            world.spawnParticle("dripWater", d0, d1, d2, 0.0, 0.0, 0.0);
        }

        super.randomDisplayTick(world, x, y, z, random);
    }

    public void breakBlock(World world, int x, int y, int z, Block par5, int par6) {
        byte radius = 1;
        int bounds = radius + 1;
        if (world.checkChunksExist(x - bounds, y - bounds, z - bounds, x + bounds, y + bounds, z + bounds)) {
            for (int i = -radius; i <= radius; i++) {
                for (int j = -radius; j <= radius; j++) {
                    for (int k = -radius; k <= radius; k++) {
                        Block block = world.getBlock(x + i, y + j, z + k);
                        if (block.isLeaves(world, x, y, z)) {
                            block.beginLeavesDecay(world, x + i, y + j, z + k);
                        }
                    }
                }
            }
        }
    }

    public void updateTick(World world, int x, int y, int z, Random random) {
        if (!world.isRemote) {
            int meta = world.getBlockMetadata(x, y, z);
            if ((meta & 8) != 0 && (meta & 4) == 0) {
                byte b0 = 4;
                int i1 = b0 + 1;
                byte b1 = 32;
                int j1 = b1 * b1;
                int k1 = b1 / 2;
                if (this.adjacentTreeBlocks == null) {
                    this.adjacentTreeBlocks = new int[b1 * b1 * b1];
                }

                if (world.checkChunksExist(x - i1, y - i1, z - i1, x + i1, y + i1, z + i1)) {
                    for (int l1 = -b0; l1 <= b0; l1++) {
                        for (int i2 = -b0; i2 <= b0; i2++) {
                            for (int j2 = -b0; j2 <= b0; j2++) {
                                Block block = world.getBlock(x + l1, y + i2, z + j2);
                                if (block != null && block.canSustainLeaves(world, x + l1, y + i2, z + j2)) {
                                    this.adjacentTreeBlocks[(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = 0;
                                } else if (block != null && block.isLeaves(world, x + l1, y + i2, z + j2)) {
                                    this.adjacentTreeBlocks[(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = -2;
                                } else {
                                    this.adjacentTreeBlocks[(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = -1;
                                }
                            }
                        }
                    }

                    for (int var17 = 1; var17 <= 4; var17++) {
                        for (int i2 = -b0; i2 <= b0; i2++) {
                            for (int j2 = -b0; j2 <= b0; j2++) {
                                for (int k2 = -b0; k2 <= b0; k2++) {
                                    if (this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1]
                                        == var17 - 1) {
                                        if (this.adjacentTreeBlocks[(i2 + k1 - 1) * j1 + (j2 + k1) * b1 + k2 + k1]
                                            == -2) {
                                            this.adjacentTreeBlocks[(i2 + k1 - 1) * j1 + (j2 + k1) * b1
                                                + k2
                                                + k1] = var17;
                                        }

                                        if (this.adjacentTreeBlocks[(i2 + k1 + 1) * j1 + (j2 + k1) * b1 + k2 + k1]
                                            == -2) {
                                            this.adjacentTreeBlocks[(i2 + k1 + 1) * j1 + (j2 + k1) * b1
                                                + k2
                                                + k1] = var17;
                                        }

                                        if (this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1 - 1) * b1 + k2 + k1]
                                            == -2) {
                                            this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1 - 1) * b1
                                                + k2
                                                + k1] = var17;
                                        }

                                        if (this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1 + 1) * b1 + k2 + k1]
                                            == -2) {
                                            this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1 + 1) * b1
                                                + k2
                                                + k1] = var17;
                                        }

                                        if (this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + (k2 + k1 - 1)]
                                            == -2) {
                                            this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1
                                                + (k2 + k1 - 1)] = var17;
                                        }

                                        if (this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 + 1]
                                            == -2) {
                                            this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1
                                                + k2
                                                + k1
                                                + 1] = var17;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                int l1 = this.adjacentTreeBlocks[k1 * j1 + k1 * b1 + k1];
                if (l1 >= 0) {
                    world.setBlockMetadataWithNotify(x, y, z, meta & -9, 4);
                } else {
                    this.removeLeaves(world, x, y, z);
                }
            }
        }
    }

    private void removeLeaves(World world, int x, int y, int z) {
        this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
        world.setBlockToAir(x, y, z);
    }

    public Item getItemDropped(int metadata, Random random, int fortune) {
        return Item.getItemFromBlock(BOPCBlocks.colorizedSaplings);
    }

    public int damageDropped(int meta) {
        return getTypeFromMeta(meta) + this.category.ordinal() * 4;
    }

    public int getDamageValue(World world, int x, int y, int z) {
        return getTypeFromMeta(world.getBlockMetadata(x, y, z));
    }

    public int quantityDropped(Random random) {
        return random.nextInt(20) == 0 ? 1 : 0;
    }

    public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z) {
        return true;
    }

    public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<>();
        ret.add(new ItemStack(this, 1, getTypeFromMeta(world.getBlockMetadata(x, y, z))));
        return ret;
    }

    public String getLeafType(int metadata) {
        int type = getTypeFromMeta(metadata) + this.category.ordinal() * 4;
        return leaves[type >= leaves.length ? 0 : type];
    }

    private static int getTypeFromMeta(int meta) {
        meta &= 3;
        if (meta < 0 || meta >= leaves.length) {
            meta = 0;
        }

        return meta;
    }

    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        return true;
    }

    public int getRenderType() {
        return RenderUtils.leavesModel;
    }

    public void beginLeavesDecay(World world, int x, int y, int z) {
        world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z) | 8, 4);
    }

    public boolean isLeaves(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    public enum ColourizedLeafCategory {
        CAT1,
        CAT2,
        CAT3,
        CAT4;
    }
}
