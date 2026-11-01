package biomesoplenty.common.utils;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

public class BiomeUtils {

    public static void setBiomeAt(World world, int x, int z, BiomeGenBase biome) {
        Chunk chunk = world.getChunkFromBlockCoords(x, z);
        byte[] array = chunk.getBiomeArray();
        array[(z & 15) << 4 | x & 15] = (byte) (biome.biomeID & 0xFF);
        chunk.setBiomeArray(array);
    }
}
