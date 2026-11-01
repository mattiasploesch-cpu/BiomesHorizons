package biomesoplenty.common.biome.decoration;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Post;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Pre;
import net.minecraftforge.event.terraingen.TerrainGen;

import biomesoplenty.api.biome.BOPBiome;
import biomesoplenty.api.biome.BOPBiomeDecorator;
import biomesoplenty.common.world.generation.IBOPWorldGenerator;
import biomesoplenty.common.world.generation.WorldGenFieldAssociation;

public class BOPNetherBiomeDecorator extends BOPBiomeDecorator<NetherBiomeFeatures> {

    public BOPNetherBiomeDecorator() {
        super(NetherBiomeFeatures.class);
    }

    @Override
    protected void genDecorations(BiomeGenBase biome) {
        BOPBiome bopBiome = (BOPBiome) biome;
        MinecraftForge.EVENT_BUS.post(new Pre(this.currentWorld, this.randomGenerator, this.chunk_X, this.chunk_Z));
        int perChunk = this.treesPerChunk;
        if (this.randomGenerator.nextInt(10) == 0) {
            perChunk++;
        }

        boolean doGen = TerrainGen
            .decorate(this.currentWorld, this.randomGenerator, this.chunk_X, this.chunk_Z, EventType.TREE);

        for (int i = 0; doGen && i < perChunk; i++) {
            int x = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
            int z = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
            int y = this.nextInt(128);
            WorldGenAbstractTree worldgenabstracttree = biome.func_150567_a(this.randomGenerator);
            worldgenabstracttree.setScale(1.0, 1.0, 1.0);
            if (worldgenabstracttree.generate(this.currentWorld, this.randomGenerator, x, y, z)) {
                worldgenabstracttree.func_150524_b(this.currentWorld, this.randomGenerator, x, y, z);
            }
        }

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

        MinecraftForge.EVENT_BUS.post(new Post(this.currentWorld, this.randomGenerator, this.chunk_X, this.chunk_Z));
    }
}
