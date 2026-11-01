package biomesoplenty.common.fluids;

import net.minecraftforge.fluids.Fluid;

public class HoneyFluid extends Fluid {

    public HoneyFluid(String fluidName) {
        super(fluidName);
        this.setViscosity(1500);
    }
}
