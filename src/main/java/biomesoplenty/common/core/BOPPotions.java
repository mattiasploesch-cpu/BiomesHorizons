package biomesoplenty.common.core;

import java.lang.reflect.Field;

import net.minecraft.potion.Potion;

import biomesoplenty.api.content.BOPCPotions;
import biomesoplenty.common.configuration.BOPConfigurationIDs;
import biomesoplenty.common.potions.PotionParalysis;
import biomesoplenty.common.potions.PotionPossession;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class BOPPotions {

    public static void init() {
        BOPCPotions.paralysis = new PotionParalysis(
            BOPConfigurationIDs.potionAutoAssign ? getSparePotionId() : BOPConfigurationIDs.paralysisPotionID,
            true,
            16767262).setPotionName("potion.paralysis");
        BOPCPotions.possession = new PotionPossession(
            BOPConfigurationIDs.potionAutoAssign ? getSparePotionId() : BOPConfigurationIDs.possessionPotionID,
            true,
            1280).setPotionName("potion.possession");
    }

    public static int getSparePotionId() {
        int n = Potion.potionTypes.length;

        for (int i = 1; i < n; i++) {
            if (Potion.potionTypes[i] == null) {
                return i;
            }
        }

        if (n >= 128) {
            throw new RuntimeException(
                "There are not enough spare potion IDs - Biomes O Plenty cannot create potions - you might have to remove some mods");
        }

        Potion[] expandedPotionTypes = new Potion[n + 1];
        System.arraycopy(Potion.potionTypes, 0, expandedPotionTypes, 0, n);
        Field field = ReflectionHelper.findField(
            Potion.class,
            ObfuscationReflectionHelper
                .remapFieldNames(Potion.class.getName(), new String[] { "potionTypes", "potionTypes" }));

        try {
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & -17);
            field.set(null, expandedPotionTypes);
            return n;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
