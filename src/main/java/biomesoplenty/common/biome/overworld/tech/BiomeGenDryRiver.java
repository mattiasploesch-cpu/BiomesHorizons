package biomesoplenty.common.biome.overworld.tech;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeGenBase.Height;

import biomesoplenty.common.biome.BOPOverworldBiome;

public class BiomeGenDryRiver extends BOPOverworldBiome {

    private static final Height biomeHeight = new Height(-0.2F, 0.0F);

    public BiomeGenDryRiver(int biomeId) {
        super(biomeId);
        this.setHeight(biomeHeight);
        this.setDisableRain();
        this.setTemperatureRainfall(1.0F, 0.0F);
        this.spawnableCreatureList.clear();
        this.topBlock = Blocks.sand;
        this.fillerBlock = Blocks.sand;
    }
}
