package biomesoplenty.common.fluids.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.FluidRegistry;

public class BlockBloodFluid extends BlockFluidClassic {

    public static IIcon bloodStillIcon;
    public static IIcon bloodFlowingIcon;

    public BlockBloodFluid() {
        super(FluidRegistry.getFluid("hell_blood"), Material.water);
    }

    public void registerBlockIcons(IIconRegister iconRegister) {
        bloodStillIcon = iconRegister.registerIcon("biomesoplenty:blood_still");
        bloodFlowingIcon = iconRegister.registerIcon("biomesoplenty:blood_flowing");
    }

    public IIcon getIcon(int side, int meta) {
        return side != 0 && side != 1 ? bloodFlowingIcon : bloodStillIcon;
    }
}
