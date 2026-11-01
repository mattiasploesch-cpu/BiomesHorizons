package biomesoplenty.api.biome;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType;
import net.minecraftforge.event.terraingen.TerrainGen;

import biomesoplenty.common.utils.RandomForcedPositiveOwned;
import biomesoplenty.common.world.generation.IBOPWorldGenerator;
import biomesoplenty.common.world.generation.WorldGenFieldAssociation;

public class BOPBiomeDecorator<T extends BiomeFeatures> extends BiomeDecorator {

    public T bopFeatures;

    public BOPBiomeDecorator(Class<T> biomeFeaturesClass) {
        try {
            this.bopFeatures = biomeFeaturesClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void decorateChunk(World world, Random random, BiomeGenBase biome, int chunkX, int chunkZ) {
        if (this.currentWorld == null) {
            this.currentWorld = world;
            this.randomGenerator = new RandomForcedPositiveOwned(random);
            this.chunk_X = chunkX;
            this.chunk_Z = chunkZ;
            this.genDecorations(biome);
            this.currentWorld = null;
            this.randomGenerator = null;
        }
    }

    protected void genDecorations(BiomeGenBase biome) {
        super.genDecorations(biome);
        BOPBiome bopBiome = (BOPBiome) biome;

        for (String featureName : this.bopFeatures.getFeatureNames()) {
            if (featureName.equals("bopFlowersPerChunk")
                ? TerrainGen
                    .decorate(this.currentWorld, this.randomGenerator, this.chunk_X, this.chunk_Z, EventType.FLOWERS)
                : !featureName.equals("bopGrassPerChunk") || TerrainGen
                    .decorate(this.currentWorld, this.randomGenerator, this.chunk_X, this.chunk_Z, EventType.GRASS)) {
                WorldGenFieldAssociation.WorldFeature worldFeature = WorldGenFieldAssociation
                    .getAssociatedFeature(featureName);
                if (worldFeature != null) {
                    IBOPWorldGenerator worldGenerator = worldFeature.getBOPWorldGenerator();
                    if (worldGenerator != null) {
                        worldGenerator.setupGeneration(
                            this.currentWorld,
                            this.randomGenerator,
                            bopBiome,
                            featureName,
                            this.chunk_X,
                            this.chunk_Z);
                    }
                }
            }
        }
    }

    public static <T extends WorldGenerator> T getRandomWeightedWorldGenerator(
        HashMap<T, ? extends Number> worldGeneratorMap) {
        double completeWeight = 0.0;

        for (Number weight : worldGeneratorMap.values()) {
            completeWeight += Double.parseDouble(weight.toString());
        }

        double random = Math.random() * completeWeight;
        double countWeight = 0.0;

        for (Entry<T, ? extends Number> entry : worldGeneratorMap.entrySet()) {
            countWeight += Double.parseDouble(
                entry.getValue()
                    .toString());
            if (countWeight >= random) {
                return entry.getKey();
            }
        }

        return null;
    }

    protected int nextInt(int i) {
        return i <= 1 ? 0 : this.randomGenerator.nextInt(i);
    }
}
