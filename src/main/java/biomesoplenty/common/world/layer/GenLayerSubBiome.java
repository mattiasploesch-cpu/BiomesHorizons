package biomesoplenty.common.world.layer;

import java.util.List;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraftforge.common.BiomeManager.BiomeEntry;

import org.apache.commons.lang3.tuple.Pair;

import biomesoplenty.common.biome.BOPSubBiome;
import biomesoplenty.common.world.BOPBiomeManager;
import biomesoplenty.common.world.noise.SimplexNoise;

public class GenLayerSubBiome extends GenLayer {

    private final int OFFSET_RANGE = 500000;
    private Pair<Integer, Integer>[] offsets = new Pair[BiomeGenBase.getBiomeGenArray().length];

    public GenLayerSubBiome(long seed, GenLayer parent) {
        super(seed);
        this.parent = parent;
        this.setOffsets();
    }

    public int[] getInts(int x, int z, int width, int length) {
        int[] inputBiomeIDs = this.parent.getInts(x - 1, z - 1, width + 2, length + 2);
        int[] outputBiomeIDs = IntCache.getIntCache(width * length);

        for (int xi = 0; xi < width; xi++) {
            for (int zi = 0; zi < length; zi++) {
                this.initChunkSeed(xi + x, zi + z);
                int currentBiomeId = inputBiomeIDs[xi + 1 + (zi + 1) * (width + 2)];
                List<BiomeEntry> currentSubBiomes = BOPBiomeManager.overworldSubBiomes[currentBiomeId];
                BOPSubBiome selectedSubBiome = currentSubBiomes != null
                    ? (BOPSubBiome) currentSubBiomes.get(this.nextInt(currentSubBiomes.size())).biome
                    : null;
                if (selectedSubBiome != null) {
                    Pair<Integer, Integer> offset = this.getOffset(selectedSubBiome);
                    if (SimplexNoise.noise(
                        (xi + x + (Integer) offset.getLeft()) * selectedSubBiome.zoom,
                        (zi + z + (Integer) offset.getRight()) * selectedSubBiome.zoom) > selectedSubBiome.threshold) {
                        outputBiomeIDs[xi + zi * width] = selectedSubBiome.biomeID;
                    } else {
                        outputBiomeIDs[xi + zi * width] = currentBiomeId;
                    }
                } else {
                    outputBiomeIDs[xi + zi * width] = currentBiomeId;
                }
            }
        }

        return outputBiomeIDs;
    }

    private Pair<Integer, Integer> getOffset(BiomeGenBase biome) {
        return this.offsets[biome.biomeID];
    }

    private void setOffsets() {
        for (int i = 0; i < this.offsets.length; i++) {
            this.offsets[i] = Pair.of(this.nextInt(500000) - 250000, this.nextInt(500000) - 250000);
        }
    }
}
