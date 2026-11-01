package biomesoplenty.common.world;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerBiomeEdge;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraftforge.common.DimensionManager;

import biomesoplenty.common.world.layer.GenLayerBiomeBOP;

public class WorldTypeBOP extends WorldType {

    public WorldTypeBOP() {
        super("BIOMESOP");
        DimensionManager.unregisterProviderType(0);
        DimensionManager.registerProviderType(0, WorldProviderSurfaceBOP.class, true);
    }

    public GenLayer getBiomeLayer(long worldSeed, GenLayer parentLayer) {
        GenLayer ret = new GenLayerBiomeBOP(200L, parentLayer, this);
        ret = GenLayerZoom.magnify(1000L, ret, 2);
        return new GenLayerBiomeEdge(1000L, ret);
    }

    public WorldChunkManager getChunkManager(World world) {
        return new WorldChunkManagerBOP(world);
    }
}
