package biomesoplenty.common.world;

import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderBOPEnd extends WorldProviderEnd {

    public void registerWorldChunkManager() {
        this.worldChunkMgr = new WorldChunkManagerBOPEnd();
        this.dimensionId = 1;
        this.hasNoSky = true;
    }

    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderBOPEnd(this.worldObj, this.worldObj.getSeed());
    }
}
