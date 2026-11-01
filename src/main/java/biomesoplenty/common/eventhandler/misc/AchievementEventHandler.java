package biomesoplenty.common.eventhandler.misc;

import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;

import biomesoplenty.api.content.BOPCBlocks;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemPickupEvent;

public class AchievementEventHandler {

    @SubscribeEvent
    public void woodMined(ItemPickupEvent event) {
        for (int i = 0; i < 4; i++) {
            int z = 0;

            while (z < 4) {
                switch (i) {
                    case 0:
                        if (event.pickedUp.getEntityItem()
                            .isItemEqual(new ItemStack(BOPCBlocks.logs1, 0, z))) {
                            event.player.addStat(AchievementList.mineWood, 1);
                            return;
                        }
                    case 1:
                        if (event.pickedUp.getEntityItem()
                            .isItemEqual(new ItemStack(BOPCBlocks.logs2, 0, z))) {
                            event.player.addStat(AchievementList.mineWood, 1);
                            return;
                        }
                    case 2:
                        if (event.pickedUp.getEntityItem()
                            .isItemEqual(new ItemStack(BOPCBlocks.logs3, 0, z))) {
                            event.player.addStat(AchievementList.mineWood, 1);
                            return;
                        }
                    case 3:
                        if (event.pickedUp.getEntityItem()
                            .isItemEqual(new ItemStack(BOPCBlocks.logs4, 0, z))) {
                            event.player.addStat(AchievementList.mineWood, 1);
                            return;
                        }
                    default:
                        z++;
                }
            }
        }
    }
}
