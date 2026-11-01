package biomesoplenty.common.world;

import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderBOPHell extends WorldProviderHell {

    public void registerWorldChunkManager() {
        this.worldChunkMgr = new WorldChunkManagerBOPHell(this.worldObj);
        this.isHellWorld = true;
        this.hasNoSky = true;
        this.dimensionId = -1;
    }

    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderBOPHell(this.worldObj, this.worldObj.getSeed());
    }
}
