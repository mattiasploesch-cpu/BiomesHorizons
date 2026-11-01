package biomesoplenty.common.configuration;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;

public class BOPConfigurationIDs {

    public static Configuration config;
    public static boolean potionAutoAssign;
    public static int paralysisPotionID;
    public static int possessionPotionID;

    public static void init(File configFile) {
        config = new Configuration(configFile);

        try {
            config.load();
            potionAutoAssign = config
                .get(
                    "Potion Auto Assign",
                    "Potion Auto Assign",
                    true,
                    "Automatically assign potion ids if false set potion ids manually.")
                .getBoolean();
            paralysisPotionID = config.get("Potions IDs", "Paralysis Potion ID", 20, null)
                .getInt();
            possessionPotionID = config.get("Potions IDs", "Possession Potion ID", 21, null)
                .getInt();
        } catch (Exception e) {
            FMLLog.log(Level.ERROR, e, "Biomes O Plenty has had a problem loading its configuration", new Object[0]);
        } finally {
            if (config.hasChanged()) {
                config.save();
            }
        }
    }
}
