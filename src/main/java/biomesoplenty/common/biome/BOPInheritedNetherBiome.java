package biomesoplenty.common.biome;

import net.minecraft.world.biome.BiomeGenBase;

import biomesoplenty.api.biome.BOPInheritedBiome;
import biomesoplenty.common.biome.decoration.BOPNetherBiomeDecorator;

public class BOPInheritedNetherBiome extends BOPInheritedBiome<BOPNetherBiomeDecorator> {

    public BOPInheritedNetherBiome(int biomeID, BiomeGenBase inheritedBiome) {
        super(biomeID, BOPNetherBiomeDecorator.class, inheritedBiome);
    }
}
