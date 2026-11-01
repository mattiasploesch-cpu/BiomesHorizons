package biomesoplenty.common.core;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import biomesoplenty.api.content.BOPCBlocks;
import biomesoplenty.api.content.BOPCFluids;
import biomesoplenty.common.fluids.BloodFluid;
import biomesoplenty.common.fluids.HoneyFluid;
import biomesoplenty.common.fluids.PoisonFluid;

public class BOPFluids {

    public static void preInit() {
        registerFluids();
    }

    public static void init() {
        registerFluidBlocks();
    }

    private static void registerFluids() {
        BOPCFluids.poison = registerFluid(new PoisonFluid("poison"));
        BOPCFluids.blood = registerFluid(new BloodFluid("hell_blood"));
        BOPCFluids.honey = registerFluid(new HoneyFluid("honey"));
    }

    private static void registerFluidBlocks() {
        BOPCFluids.poison.setBlock(BOPCBlocks.poison);
        BOPCFluids.blood.setBlock(BOPCBlocks.blood);
        BOPCFluids.honey.setBlock(BOPCBlocks.honey);
    }

    public static Fluid registerFluid(Fluid fluid) {
        FluidRegistry.registerFluid(fluid);
        return FluidRegistry.getFluid(fluid.getName());
    }
}
