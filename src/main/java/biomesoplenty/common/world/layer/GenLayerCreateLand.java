package biomesoplenty.common.world.layer;

import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

import biomesoplenty.common.configuration.BOPConfigurationTerrainGen;

public class GenLayerCreateLand extends GenLayer {

    public GenLayerCreateLand(long seed) {
        super(seed);
    }

    public int[] getInts(int x, int z, int length, int width) {
        int[] outputBiomeIds = IntCache.getIntCache(length * width);

        for (int xItr = 0; xItr < length; xItr++) {
            for (int zItr = 0; zItr < width; zItr++) {
                this.initChunkSeed(x + xItr, z + zItr);
                if (this.nextInt(100) < BOPConfigurationTerrainGen.landmassPercentage) {
                    outputBiomeIds[xItr + zItr * length] = 1;
                } else {
                    outputBiomeIds[xItr + zItr * length] = 0;
                }
            }
        }

        if (x > -length && x <= 0 && z > -width && z <= 0) {
            outputBiomeIds[-x + -z * length] = 1;
        }

        return outputBiomeIds;
    }
}
