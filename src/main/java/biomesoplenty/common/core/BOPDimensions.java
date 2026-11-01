package biomesoplenty.common.core;

import net.minecraftforge.common.DimensionManager;

import biomesoplenty.common.world.WorldProviderBOPHell;

public class BOPDimensions {

    public static void init() {
        unregisterProviders();
        registerProviders();
        registerDimensions();
    }

    private static void unregisterProviders() {
        DimensionManager.unregisterProviderType(-1);
    }

    private static void registerProviders() {
        DimensionManager.registerProviderType(-1, WorldProviderBOPHell.class, true);
    }

    private static void registerDimensions() {}
}
