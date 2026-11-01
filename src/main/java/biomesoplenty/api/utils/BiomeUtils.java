package biomesoplenty.api.utils;

import net.minecraft.world.biome.BiomeGenBase;

import biomesoplenty.common.configuration.BOPConfigurationTerrainGen;

public class BiomeUtils {

    public static boolean areBiomesEqual(BiomeGenBase biome1, BiomeGenBase biome2) {
        return biome1 != null && biome2 != null ? biome1.biomeID == biome2.biomeID : false;
    }

    public static boolean oreWithMetaEnabled(int metaOfOre) {
        if (!BOPConfigurationTerrainGen.genOreGeneral) {
            return false;
        }

        switch (metaOfOre) {
            case 0:
                if (BOPConfigurationTerrainGen.genAmethystOre) {
                    return true;
                }
            case 1:
            case 3:
            case 5:
            case 7:
            case 9:
            case 11:
            case 13:
            default:
                break;
            case 2:
                if (BOPConfigurationTerrainGen.genRubyOre) {
                    return true;
                }
                break;
            case 4:
                if (BOPConfigurationTerrainGen.genPeridotOre) {
                    return true;
                }
                break;
            case 6:
                if (BOPConfigurationTerrainGen.genTopazOre) {
                    return true;
                }
                break;
            case 8:
                if (BOPConfigurationTerrainGen.genTanzaniteOre) {
                    return true;
                }
                break;
            case 10:
                if (BOPConfigurationTerrainGen.genMalachiteOre) {
                    return true;
                }
                break;
            case 12:
                if (BOPConfigurationTerrainGen.genSapphireOre) {
                    return true;
                }
                break;
            case 14:
                if (BOPConfigurationTerrainGen.genAmberOre) {
                    return true;
                }
        }

        return false;
    }
}
