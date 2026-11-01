package biomesoplenty.common.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import biomesoplenty.BiomesOPlenty;

public class BlockBOPRocks extends Block {

    private static final String[] types = new String[] { "limestone", "limestonesmooth", "siltstone", "siltstonesmooth",
        "shale", "shalesmooth" };
    private IIcon[] textures = new IIcon[] { null, null, null };

    public BlockBOPRocks() {
        super(Material.rock);
        this.setHarvestLevel("pickaxe", 1, 0);
        this.setHarvestLevel("pickaxe", 2, 2);
        this.setHarvestLevel("pickaxe", 3, 4);
        this.setStepSound(Block.soundTypePiston);
        this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
    }

    public void registerBlockIcons(IIconRegister iconRegister) {
        this.textures = new IIcon[types.length];

        for (int i = 0; i < types.length; i++) {
            this.textures[i] = iconRegister.registerIcon("biomesoplenty:" + types[i]);
        }
    }

    public IIcon getIcon(int side, int meta) {
        if (meta < 0 || meta >= this.textures.length) {
            meta = 0;
        }

        return this.textures[meta];
    }

    public int getDamageValue(World world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z);
    }

    public void getSubBlocks(Item block, CreativeTabs creativeTabs, List list) {
        for (int i = 0; i < types.length; i++) {
            list.add(new ItemStack(block, 1, i));
        }
    }

    public int damageDropped(int meta) {
        return meta == 0 ? 1 : meta;
    }

    public float getBlockHardness(World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        float hardness = this.blockHardness;
        switch (meta) {
            case 0:
                hardness = 3.0F;
                break;
            case 1:
                hardness = 1.5F;
                break;
            case 2:
                hardness = 3.0F;
                break;
            case 3:
                hardness = 1.5F;
                break;
            case 4:
                hardness = 3.0F;
                break;
            case 5:
                hardness = 1.5F;
        }

        return hardness;
    }

    public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX,
        double explosionY, double explosionZ) {
        int meta = world.getBlockMetadata(x, y, z);
        float resistance = this.blockResistance;
        switch (meta) {
            case 0:
                resistance = 5.0F;
                break;
            case 1:
                resistance = 7.0F;
                break;
            case 2:
                resistance = 5.0F;
                break;
            case 3:
                resistance = 7.0F;
                break;
            case 4:
                resistance = 5.0F;
                break;
            case 5:
                resistance = 7.0F;
        }

        return resistance / 5.0F;
    }
}
