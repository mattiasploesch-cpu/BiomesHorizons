package biomesoplenty.common.biome.overridden;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase;

import biomesoplenty.api.content.BOPCBlocks;
import biomesoplenty.common.biome.BOPInheritedOverworldBiome;
import biomesoplenty.common.world.features.WorldGenBOPTallGrass;

public class BiomeGenBOPMesa extends BOPInheritedOverworldBiome {

    public BiomeGenBOPMesa(int biomeID, BiomeGenBase inheritedBiome) {
        super(biomeID, inheritedBiome);
        this.theBiomeDecorator.bopFeatures.tinyCactiPerChunk = 10;
        this.theBiomeDecorator.bopFeatures.bromeliadsPerChunk = 10;
        this.theBiomeDecorator.bopFeatures.bopGrassPerChunk = 5;
        this.theBiomeDecorator.bopFeatures.weightedGrassGen.put(new WorldGenBOPTallGrass(BOPCBlocks.plants, 1), 1.0);
        this.theBiomeDecorator.bopFeatures.weightedGrassGen.put(new WorldGenBOPTallGrass(Blocks.tallgrass, 1), 1.0);
    }
}
