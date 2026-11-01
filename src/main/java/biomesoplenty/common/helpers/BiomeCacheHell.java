package biomesoplenty.common.helpers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.LongHashMap;
import net.minecraft.world.biome.BiomeGenBase;

import biomesoplenty.common.world.WorldChunkManagerBOPHell;

public class BiomeCacheHell {

    private final WorldChunkManagerBOPHell chunkManager;
    private long lastCleanupTime = 0L;
    private LongHashMap cacheMap = new LongHashMap();
    private List cache = new ArrayList();

    public BiomeCacheHell(WorldChunkManagerBOPHell par1WorldChunkManager) {
        this.chunkManager = par1WorldChunkManager;
    }

    public BiomeCacheBlockHell getBiomeCacheBlock(int par1, int par2) {
        par1 >>= 4;
        par2 >>= 4;
        long var3 = par1 & 4294967295L | (par2 & 4294967295L) << 32;
        BiomeCacheBlockHell var5 = (BiomeCacheBlockHell) this.cacheMap.getValueByKey(var3);
        if (var5 == null) {
            var5 = new BiomeCacheBlockHell(this, par1, par2);
            this.cacheMap.add(var3, var5);
            this.cache.add(var5);
        }

        var5.lastAccessTime = System.currentTimeMillis();
        return var5;
    }

    public BiomeGenBase getBiomeGenAt(int par1, int par2) {
        return this.getBiomeCacheBlock(par1, par2)
            .getBiomeGenAt(par1, par2);
    }

    public void cleanupCache() {
        long var1 = System.currentTimeMillis();
        long var3 = var1 - this.lastCleanupTime;
        if (var3 > 7500L || var3 < 0L) {
            this.lastCleanupTime = var1;

            for (int var5 = 0; var5 < this.cache.size(); var5++) {
                BiomeCacheBlockHell var6 = (BiomeCacheBlockHell) this.cache.get(var5);
                long var7 = var1 - var6.lastAccessTime;
                if (var7 > 30000L || var7 < 0L) {
                    this.cache.remove(var5--);
                    long var9 = var6.xPosition & 4294967295L | (var6.zPosition & 4294967295L) << 32;
                    this.cacheMap.remove(var9);
                }
            }
        }
    }

    public BiomeGenBase[] getCachedBiomes(int par1, int par2) {
        return this.getBiomeCacheBlock(par1, par2).biomes;
    }

    static WorldChunkManagerBOPHell getChunkManager(BiomeCacheHell par0BiomeCache) {
        return par0BiomeCache.chunkManager;
    }
}
