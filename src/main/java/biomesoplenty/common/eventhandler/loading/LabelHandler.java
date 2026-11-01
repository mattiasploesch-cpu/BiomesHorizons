package biomesoplenty.common.eventhandler.loading;

import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.event.world.WorldEvent.Load;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class LabelHandler {

    public static final int CURRENT_LOAD_VERSION = 0;

    @SubscribeEvent
    public void onWorldLoad(Load event) {
        World world = event.world;
        MapStorage mapStorage = world.perWorldStorage;
        BOPSavedData savedData = (BOPSavedData) mapStorage.loadData(BOPSavedData.class, "biomesoplenty");
        if (savedData == null) {
            savedData = new BOPSavedData("biomesoplenty");
            mapStorage.setData("biomesoplenty", savedData);
            savedData.markDirty();
        }

        if (savedData.lastLoadVersion != 0) {
            savedData.lastLoadVersion = 0;
            savedData.markDirty();
        }
    }
}
