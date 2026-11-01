package biomesoplenty.common.blocks;

import net.minecraft.block.BlockCrops;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import biomesoplenty.api.content.BOPCItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTurnip extends BlockCrops {

    @SideOnly(Side.CLIENT)
    private IIcon[] iconArray;

    public BlockTurnip() {
        this.setBlockTextureName("turnip");
    }

    public IIcon getIcon(int side, int meta) {
        if (meta < 7) {
            if (meta == 6) {
                meta = 5;
            }

            return this.iconArray[meta >> 1];
        } else {
            return this.iconArray[3];
        }
    }

    public void fertilize(World world, int x, int y, int z) {
        this.func_149853_b(world, world.rand, x, y, z);
    }

    protected Item getSeed() {
        return BOPCItems.turnipSeeds;
    }

    protected Item getCrop() {
        return BOPCItems.food;
    }

    public int damageDropped(int meta) {
        return meta == 7 ? 11 : 0;
    }

    public void registerBlockIcons(IIconRegister iconRegister) {
        this.iconArray = new IIcon[4];

        for (int i = 0; i < this.iconArray.length; i++) {
            this.iconArray[i] = iconRegister.registerIcon("biomesoplenty:" + this.getTextureName() + "_stage_" + i);
        }
    }
}
