package biomesoplenty.common.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import biomesoplenty.BiomesOPlenty;

public class BlockBOPLog extends Block {

    private static final String[] types = new String[] { "sacredoak", "cherry", "dark", "fir", "ethereal", "magic",
        "mangrove", "palm", "redwood", "willow", "dead", "bigflowerstem", "pine", "hellbark", "jacaranda", "mahogany" };
    private IIcon[] textures;
    private IIcon[] logHearts;
    private final BlockBOPLog.LogCategory category;

    public BlockBOPLog(BlockBOPLog.LogCategory cat) {
        super(Material.wood);
        this.category = cat;
        this.setHardness(2.0F);
        this.setHarvestLevel("axe", 0);
        this.setResistance(5.0F);
        this.setStepSound(Block.soundTypeWood);
        this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
    }

    public void registerBlockIcons(IIconRegister iconRegister) {
        this.textures = new IIcon[types.length];
        this.logHearts = new IIcon[types.length];

        for (int i = 0; i < types.length; i++) {
            if (i != 11) {
                this.textures[i] = iconRegister.registerIcon("biomesoplenty:log_" + types[i] + "_side");
                this.logHearts[i] = iconRegister.registerIcon("biomesoplenty:log_" + types[i] + "_heart");
            }
        }

        this.textures[11] = iconRegister.registerIcon("biomesoplenty:bigflowerstem_side");
        this.logHearts[11] = iconRegister.registerIcon("biomesoplenty:bigflowerstem_heart");
    }

    public IIcon getIcon(int side, int meta) {
        int pos = meta & 12;
        return (pos != 0 || side != 1 && side != 0) && (pos != 4 || side != 5 && side != 4)
            && (pos != 8 || side != 2 && side != 3) ? this.textures[getTypeFromMeta(meta) + this.category.ordinal() * 4]
                : this.logHearts[getTypeFromMeta(meta) + this.category.ordinal() * 4];
    }

    public void getSubBlocks(Item block, CreativeTabs creativeTabs, List list) {
        for (int i = 0; i < 4; i++) {
            list.add(new ItemStack(this, 1, i));
        }
    }

    public void breakBlock(World world, int x, int y, int z, Block par5, int par6) {
        byte radius = 4;
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

    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
        int type = getTypeFromMeta(meta);
        byte orientation = 0;
        switch (side) {
            case 0:
            case 1:
                orientation = 0;
                break;
            case 2:
            case 3:
                orientation = 8;
                break;
            case 4:
            case 5:
                orientation = 4;
        }

        return type | orientation;
    }

    public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return this.category == BlockBOPLog.LogCategory.CAT4 && world.getBlockMetadata(x, y, z) == 1 ? 0
            : Blocks.fire.getFlammability(this);
    }

    public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return this.category == BlockBOPLog.LogCategory.CAT4 && world.getBlockMetadata(x, y, z) == 1 ? 0
            : Blocks.fire.getEncouragement(this);
    }

    public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return this.category == BlockBOPLog.LogCategory.CAT4 && world.getBlockMetadata(x, y, z) == 1 ? false
            : this.getFlammability(world, x, y, z, face) > 0;
    }

    public int damageDropped(int meta) {
        return getTypeFromMeta(meta);
    }

    protected ItemStack createStackedBlock(int meta) {
        return new ItemStack(this, 1, getTypeFromMeta(meta));
    }

    public int getRenderType() {
        return 31;
    }

    public boolean canSustainLeaves(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    public boolean isWood(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    public String getWoodType(int meta) {
        return types[getTypeFromMeta(meta) + this.category.ordinal() * 4];
    }

    private static int getTypeFromMeta(int meta) {
        return meta & 3;
    }

    public enum LogCategory {
        CAT1,
        CAT2,
        CAT3,
        CAT4;
    }
}
