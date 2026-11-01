package biomesoplenty.common.eventhandler.misc;

import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;

import biomesoplenty.api.content.BOPCItems;
import biomesoplenty.common.blocks.BlockBOPFoliage;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class OreDictionaryEventHandler {

    @SubscribeEvent
    public void onOreRegister(OreRegisterEvent event) {
        if (event.Name == "seedTurnip" && event.Ore.getItem() != BOPCItems.turnipSeeds) {
            BlockBOPFoliage.turnipSeedStack = event.Ore.copy();
        }
    }
}
