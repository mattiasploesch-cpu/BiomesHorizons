package biomesoplenty.common.eventhandler.world;

import net.minecraft.init.Blocks;
import net.minecraftforge.event.terraingen.BiomeEvent.GetVillageBlockID;
import net.minecraftforge.event.terraingen.BiomeEvent.GetVillageBlockMeta;

import biomesoplenty.api.content.BOPCBiomes;
import biomesoplenty.api.content.BOPCBlocks;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class VillageMaterialEventHandler {

    @SubscribeEvent
    public void getVillageBlockID(GetVillageBlockID event) {
        if (event.biome == BOPCBiomes.brushland && event.original == Blocks.gravel) {
            event.replacement = Blocks.sand;
            event.setResult(Result.DENY);
        }

        if (event.biome == BOPCBiomes.grove) {
            if (event.original == Blocks.log) {
                event.replacement = Blocks.log2;
                event.setResult(Result.DENY);
            }

            if (event.original == Blocks.oak_stairs) {
                event.replacement = Blocks.dark_oak_stairs;
                event.setResult(Result.DENY);
            }
        }

        if (event.biome == BOPCBiomes.heathland) {
            if (event.original == Blocks.log) {
                event.replacement = BOPCBlocks.logs4;
                event.setResult(Result.DENY);
            }

            if (event.original == Blocks.planks) {
                event.replacement = BOPCBlocks.planks;
                event.setResult(Result.DENY);
            }

            if (event.original == Blocks.oak_stairs) {
                event.replacement = BOPCBlocks.jacarandaStairs;
                event.setResult(Result.DENY);
            }

            if (event.original == Blocks.gravel) {
                event.replacement = Blocks.sand;
                event.setResult(Result.DENY);
            }
        }

        if (event.biome == BOPCBiomes.meadow && event.original == Blocks.oak_stairs) {
            event.replacement = Blocks.spruce_stairs;
            event.setResult(Result.DENY);
        }

        if (event.biome == BOPCBiomes.outback) {
            if (event.original == Blocks.log) {
                event.replacement = Blocks.log2;
                event.setResult(Result.DENY);
            }

            if (event.original == Blocks.planks) {
                event.replacement = Blocks.planks;
                event.setResult(Result.DENY);
            }

            if (event.original == Blocks.oak_stairs) {
                event.replacement = Blocks.acacia_stairs;
                event.setResult(Result.DENY);
            }

            if (event.original == Blocks.gravel) {
                event.replacement = Blocks.sand;
                event.setResult(Result.DENY);
            }
        }

        if (event.biome == BOPCBiomes.prairie && event.original == Blocks.gravel) {
            event.replacement = Blocks.sand;
            event.setResult(Result.DENY);
        }
    }

    @SubscribeEvent
    public void getVillageBlockMeta(GetVillageBlockMeta event) {
        if (event.biome == BOPCBiomes.grove) {
            if (event.original == Blocks.log) {
                event.replacement = 1;
                event.setResult(Result.DENY);
            }

            if (event.original == Blocks.planks) {
                event.replacement = 5;
                event.setResult(Result.DENY);
            }
        }

        if (event.biome == BOPCBiomes.heathland) {
            if (event.original == Blocks.log) {
                event.replacement = 2;
                event.setResult(Result.DENY);
            }

            if (event.original == Blocks.planks) {
                event.replacement = 13;
                event.setResult(Result.DENY);
            }
        }

        if (event.biome == BOPCBiomes.meadow) {
            if (event.original == Blocks.log) {
                event.replacement = 1;
                event.setResult(Result.DENY);
            }

            if (event.original == Blocks.planks) {
                event.replacement = 1;
                event.setResult(Result.DENY);
            }
        }

        if (event.biome == BOPCBiomes.outback && event.original == Blocks.planks) {
            event.replacement = 4;
            event.setResult(Result.DENY);
        }
    }
}
