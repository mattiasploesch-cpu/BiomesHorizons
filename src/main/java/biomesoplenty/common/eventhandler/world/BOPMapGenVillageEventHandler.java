package biomesoplenty.common.eventhandler.world;

import net.minecraft.world.gen.structure.MapGenVillage;

import biomesoplenty.common.configuration.structures.BOPConfigurationVillages;
import cpw.mods.fml.common.ObfuscationReflectionHelper;

public class BOPMapGenVillageEventHandler extends MapGenVillage {

    public BOPMapGenVillageEventHandler() {
        ObfuscationReflectionHelper.setPrivateValue(
            MapGenVillage.class,
            this,
            BOPConfigurationVillages.villageDistance,
            new String[] { "field_82665_g" });
        ObfuscationReflectionHelper.setPrivateValue(
            MapGenVillage.class,
            this,
            BOPConfigurationVillages.villageDistance / 4,
            new String[] { "field_82666_h" });
    }
}
