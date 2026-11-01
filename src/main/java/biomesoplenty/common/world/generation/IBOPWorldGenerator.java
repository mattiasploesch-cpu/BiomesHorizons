package biomesoplenty.common.world.generation;

import java.util.Random;

import net.minecraft.world.World;

import biomesoplenty.api.biome.BOPBiome;

public interface IBOPWorldGenerator {

    boolean generate(World var1, Random var2, int var3, int var4, int var5);

    void setupGeneration(World var1, Random var2, BOPBiome var3, String var4, int var5, int var6);
}
