package biomesoplenty.common.core;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;

import org.apache.logging.log4j.Level;

import biomesoplenty.api.BOPObfuscationHelper;
import biomesoplenty.api.biome.BOPOverriddenBiome;
import biomesoplenty.api.content.BOPCBiomes;
import biomesoplenty.common.biome.end.BiomeGenSpectralGarden;
import biomesoplenty.common.biome.nether.BiomeGenBoneyard;
import biomesoplenty.common.biome.nether.BiomeGenCorruptedSands;
import biomesoplenty.common.biome.nether.BiomeGenPhantasmagoricInferno;
import biomesoplenty.common.biome.nether.BiomeGenPolarChasm;
import biomesoplenty.common.biome.nether.BiomeGenUndergarden;
import biomesoplenty.common.biome.nether.BiomeGenVisceralHeap;
import biomesoplenty.common.biome.overridden.BiomeGenBOPBirchForest;
import biomesoplenty.common.biome.overridden.BiomeGenBOPDesert;
import biomesoplenty.common.biome.overridden.BiomeGenBOPExtremeHills;
import biomesoplenty.common.biome.overridden.BiomeGenBOPForest;
import biomesoplenty.common.biome.overridden.BiomeGenBOPHell;
import biomesoplenty.common.biome.overridden.BiomeGenBOPIcePlains;
import biomesoplenty.common.biome.overridden.BiomeGenBOPJungle;
import biomesoplenty.common.biome.overridden.BiomeGenBOPMesa;
import biomesoplenty.common.biome.overridden.BiomeGenBOPMushroomIsland;
import biomesoplenty.common.biome.overridden.BiomeGenBOPOcean;
import biomesoplenty.common.biome.overridden.BiomeGenBOPPlains;
import biomesoplenty.common.biome.overridden.BiomeGenBOPRiver;
import biomesoplenty.common.biome.overridden.BiomeGenBOPRoofedForest;
import biomesoplenty.common.biome.overridden.BiomeGenBOPSavanna;
import biomesoplenty.common.biome.overridden.BiomeGenBOPSwamp;
import biomesoplenty.common.biome.overridden.BiomeGenBOPTaiga;
import biomesoplenty.common.biome.overworld.BiomeGenAlps;
import biomesoplenty.common.biome.overworld.BiomeGenArctic;
import biomesoplenty.common.biome.overworld.BiomeGenBambooForest;
import biomesoplenty.common.biome.overworld.BiomeGenBayou;
import biomesoplenty.common.biome.overworld.BiomeGenBog;
import biomesoplenty.common.biome.overworld.BiomeGenBorealForest;
import biomesoplenty.common.biome.overworld.BiomeGenBrushland;
import biomesoplenty.common.biome.overworld.BiomeGenCanyon;
import biomesoplenty.common.biome.overworld.BiomeGenChaparral;
import biomesoplenty.common.biome.overworld.BiomeGenCherryBlossomGrove;
import biomesoplenty.common.biome.overworld.BiomeGenConiferousForest;
import biomesoplenty.common.biome.overworld.BiomeGenConiferousForestSnow;
import biomesoplenty.common.biome.overworld.BiomeGenCrag;
import biomesoplenty.common.biome.overworld.BiomeGenDeadForest;
import biomesoplenty.common.biome.overworld.BiomeGenDeadSwamp;
import biomesoplenty.common.biome.overworld.BiomeGenDeciduousForest;
import biomesoplenty.common.biome.overworld.BiomeGenDenseForest;
import biomesoplenty.common.biome.overworld.BiomeGenEucalyptusForest;
import biomesoplenty.common.biome.overworld.BiomeGenFen;
import biomesoplenty.common.biome.overworld.BiomeGenFlowerField;
import biomesoplenty.common.biome.overworld.BiomeGenFrostForest;
import biomesoplenty.common.biome.overworld.BiomeGenFungiForest;
import biomesoplenty.common.biome.overworld.BiomeGenGarden;
import biomesoplenty.common.biome.overworld.BiomeGenGrassland;
import biomesoplenty.common.biome.overworld.BiomeGenGrove;
import biomesoplenty.common.biome.overworld.BiomeGenHeathland;
import biomesoplenty.common.biome.overworld.BiomeGenHighland;
import biomesoplenty.common.biome.overworld.BiomeGenJadeCliffs;
import biomesoplenty.common.biome.overworld.BiomeGenLandOfLakes;
import biomesoplenty.common.biome.overworld.BiomeGenLavenderFields;
import biomesoplenty.common.biome.overworld.BiomeGenLushDesert;
import biomesoplenty.common.biome.overworld.BiomeGenLushSwamp;
import biomesoplenty.common.biome.overworld.BiomeGenMapleWoods;
import biomesoplenty.common.biome.overworld.BiomeGenMarsh;
import biomesoplenty.common.biome.overworld.BiomeGenMeadow;
import biomesoplenty.common.biome.overworld.BiomeGenMoor;
import biomesoplenty.common.biome.overworld.BiomeGenMountain;
import biomesoplenty.common.biome.overworld.BiomeGenMysticGrove;
import biomesoplenty.common.biome.overworld.BiomeGenOminousWoods;
import biomesoplenty.common.biome.overworld.BiomeGenOrchard;
import biomesoplenty.common.biome.overworld.BiomeGenOriginValley;
import biomesoplenty.common.biome.overworld.BiomeGenOutback;
import biomesoplenty.common.biome.overworld.BiomeGenPrairie;
import biomesoplenty.common.biome.overworld.BiomeGenRainforest;
import biomesoplenty.common.biome.overworld.BiomeGenRedwoodForest;
import biomesoplenty.common.biome.overworld.BiomeGenSacredSprings;
import biomesoplenty.common.biome.overworld.BiomeGenSeasonalForest;
import biomesoplenty.common.biome.overworld.BiomeGenShield;
import biomesoplenty.common.biome.overworld.BiomeGenShrubland;
import biomesoplenty.common.biome.overworld.BiomeGenSludgepit;
import biomesoplenty.common.biome.overworld.BiomeGenSteppe;
import biomesoplenty.common.biome.overworld.BiomeGenTemperateRainforest;
import biomesoplenty.common.biome.overworld.BiomeGenThicket;
import biomesoplenty.common.biome.overworld.BiomeGenTropicalRainforest;
import biomesoplenty.common.biome.overworld.BiomeGenTundra;
import biomesoplenty.common.biome.overworld.BiomeGenWasteland;
import biomesoplenty.common.biome.overworld.BiomeGenWetland;
import biomesoplenty.common.biome.overworld.BiomeGenWoodland;
import biomesoplenty.common.biome.overworld.BiomeGenXericShrubland;
import biomesoplenty.common.biome.overworld.ocean.BiomeGenCoralReef;
import biomesoplenty.common.biome.overworld.ocean.BiomeGenKelpForest;
import biomesoplenty.common.biome.overworld.sub.BiomeGenAlpsForest;
import biomesoplenty.common.biome.overworld.sub.BiomeGenCanyonRavine;
import biomesoplenty.common.biome.overworld.sub.BiomeGenGlacier;
import biomesoplenty.common.biome.overworld.sub.BiomeGenLandOfLakesMarsh;
import biomesoplenty.common.biome.overworld.sub.BiomeGenMangrove;
import biomesoplenty.common.biome.overworld.sub.BiomeGenMeadowForest;
import biomesoplenty.common.biome.overworld.sub.BiomeGenOasis;
import biomesoplenty.common.biome.overworld.sub.BiomeGenQuagmire;
import biomesoplenty.common.biome.overworld.sub.BiomeGenScrubland;
import biomesoplenty.common.biome.overworld.sub.BiomeGenSeasonalForestClearing;
import biomesoplenty.common.biome.overworld.sub.BiomeGenSilkglades;
import biomesoplenty.common.biome.overworld.sub.BiomeGenSpruceWoods;
import biomesoplenty.common.biome.overworld.sub.BiomeGenTropics;
import biomesoplenty.common.biome.overworld.sub.BiomeGenVolcano;
import biomesoplenty.common.biome.overworld.tech.BiomeGenDryRiver;
import biomesoplenty.common.biome.overworld.tech.BiomeGenLushRiver;
import biomesoplenty.common.configuration.BOPConfigurationBiomeGen;
import biomesoplenty.common.configuration.BOPConfigurationBiomeWeights;
import biomesoplenty.common.configuration.BOPConfigurationIDs;
import biomesoplenty.common.configuration.BOPConfigurationMisc;
import biomesoplenty.common.helpers.BOPReflectionHelper;
import biomesoplenty.common.utils.BOPLogger;
import biomesoplenty.common.world.BOPBiomeManager;
import biomesoplenty.common.world.WorldTypeBOP;

public class BOPBiomes {

    public static WorldTypeBOP worldTypeBOP;
    public static BiomeGenBase onlyBiome = null;

    public static void init() {
        try {
            BOPConfigurationIDs.config.load();
            BOPConfigurationBiomeGen.config.load();
            BOPConfigurationBiomeWeights.config.load();
            registerBiomes();
            registerOverriddenBiomes();
        } catch (Exception e) {
            BOPLogger.log(Level.ERROR, "Biomes O Plenty has had a problem loading its configuration", e);
        } finally {
            if (BOPConfigurationIDs.config.hasChanged()) {
                BOPConfigurationIDs.config.save();
            }

            if (BOPConfigurationBiomeGen.config.hasChanged()) {
                BOPConfigurationBiomeGen.config.save();
            }

            if (BOPConfigurationBiomeWeights.config.hasChanged()) {
                BOPConfigurationBiomeWeights.config.save();
            }
        }

        addBiomesToDictionary();
        disableRivers();
        addSpawnBiomes();
    }

    private static void registerBiomes() {
        BOPCBiomes.alps = registerOverworldBiome(BiomeGenAlps.class, "Alps", 3, 5);
        BOPCBiomes.arctic = registerOverworldBiome(BiomeGenArctic.class, "Arctic", 3, 10);
        BOPCBiomes.bambooForest = registerOverworldBiome(BiomeGenBambooForest.class, "Bamboo Forest", 0, 5);
        BOPCBiomes.bayou = registerOverworldBiome(BiomeGenBayou.class, "Bayou", 1, 10);
        BOPCBiomes.bog = registerOverworldBiome(BiomeGenBog.class, "Bog", 2, 7);
        BOPCBiomes.borealForest = registerOverworldBiome(BiomeGenBorealForest.class, "Boreal Forest", 2, 10);
        BOPCBiomes.brushland = registerOverworldBiome(BiomeGenBrushland.class, "Brushland", 0, 10);
        BOPCBiomes.canyon = registerOverworldBiome(BiomeGenCanyon.class, "Canyon", 0, 5);
        BOPCBiomes.chaparral = registerOverworldBiome(BiomeGenChaparral.class, "Chaparral", 1, 10);
        BOPCBiomes.cherryBlossomGrove = registerOverworldBiome(
            BiomeGenCherryBlossomGrove.class,
            "Cherry Blossom Grove",
            2,
            3);
        BOPCBiomes.coniferousForest = registerOverworldBiome(
            BiomeGenConiferousForest.class,
            "Coniferous Forest",
            2,
            10);
        BOPCBiomes.snowyConiferousForest = registerOverworldBiome(
            BiomeGenConiferousForestSnow.class,
            "Snowy Coniferous Forest",
            3,
            10);
        BOPCBiomes.crag = registerOverworldBiome(BiomeGenCrag.class, "Crag", 2, 3);
        BOPCBiomes.deadForest = registerOverworldBiome(BiomeGenDeadForest.class, "Dead Forest", 2, 5);
        BOPCBiomes.deadSwamp = registerOverworldBiome(BiomeGenDeadSwamp.class, "Dead Swamp", 2, 5);
        BOPCBiomes.deciduousForest = registerOverworldBiome(BiomeGenDeciduousForest.class, "Deciduous Forest", 1, 10);
        BOPCBiomes.denseForest = registerOverworldBiome(BiomeGenDenseForest.class, "Dense Forest", 1, 7);
        BOPCBiomes.eucalyptusForest = registerOverworldBiome(BiomeGenEucalyptusForest.class, "Eucalyptus Forest", 0, 7);
        BOPCBiomes.fen = registerOverworldBiome(BiomeGenFen.class, "Fen", 2, 10);
        BOPCBiomes.flowerField = registerOverworldBiome(BiomeGenFlowerField.class, "Flower Field", 1, 3);
        BOPCBiomes.frostForest = registerOverworldBiome(BiomeGenFrostForest.class, "Frost Forest", 3, 7);
        BOPCBiomes.fungiForest = registerOverworldBiome(BiomeGenFungiForest.class, "Fungi Forest", 1, 3);
        BOPCBiomes.garden = registerOverworldBiome(BiomeGenGarden.class, "Garden", 2, 3);
        BOPCBiomes.grassland = registerOverworldBiome(BiomeGenGrassland.class, "Grassland", 2, 10);
        BOPCBiomes.grove = registerOverworldBiome(BiomeGenGrove.class, "Grove", 2, 7);
        BOPCBiomes.heathland = registerOverworldBiome(BiomeGenHeathland.class, "Heathland", 1, 10);
        BOPCBiomes.highland = registerOverworldBiome(BiomeGenHighland.class, "Highland", 1, 7);
        BOPCBiomes.jadeCliffs = registerOverworldBiome(BiomeGenJadeCliffs.class, "Jade Cliffs", 1, 3);
        BOPCBiomes.landOfLakes = registerOverworldBiome(BiomeGenLandOfLakes.class, "Land of Lakes", 2, 5);
        BOPCBiomes.lavenderFields = registerOverworldBiome(BiomeGenLavenderFields.class, "Lavender Fields", 1, 3);
        BOPCBiomes.lushDesert = registerOverworldBiome(BiomeGenLushDesert.class, "Lush Desert", 0, 5);
        BOPCBiomes.lushSwamp = registerOverworldBiome(BiomeGenLushSwamp.class, "Lush Swamp", 1, 10);
        BOPCBiomes.mapleWoods = registerOverworldBiome(BiomeGenMapleWoods.class, "Maple Woods", 2, 10);
        BOPCBiomes.marsh = registerOverworldBiome(BiomeGenMarsh.class, "Marsh", 1, 7);
        BOPCBiomes.meadow = registerOverworldBiome(BiomeGenMeadow.class, "Meadow", 2, 10);
        BOPCBiomes.moor = registerOverworldBiome(BiomeGenMoor.class, "Moor", 2, 7);
        BOPCBiomes.mountain = registerOverworldBiome(BiomeGenMountain.class, "Mountain", 1, 10);
        BOPCBiomes.mysticGrove = registerOverworldBiome(BiomeGenMysticGrove.class, "Mystic Grove", 2, 2);
        BOPCBiomes.ominousWoods = registerOverworldBiome(BiomeGenOminousWoods.class, "Ominous Woods", 2, 2);
        BOPCBiomes.orchard = registerOverworldBiome(BiomeGenOrchard.class, "Orchard", 1, 3);
        BOPCBiomes.originValley = registerOverworldBiome(BiomeGenOriginValley.class, "Origin Valley", 1, 1);
        BOPCBiomes.outback = registerOverworldBiome(BiomeGenOutback.class, "Outback", 0, 7);
        BOPCBiomes.prairie = registerOverworldBiome(BiomeGenPrairie.class, "Prairie", 1, 10);
        BOPCBiomes.rainforest = registerOverworldBiome(BiomeGenRainforest.class, "Rainforest", 1, 7);
        BOPCBiomes.redwoodForest = registerOverworldBiome(BiomeGenRedwoodForest.class, "Redwood Forest", 1, 5);
        BOPCBiomes.sacredSprings = registerOverworldBiome(BiomeGenSacredSprings.class, "Sacred Springs", 1, 3);
        BOPCBiomes.seasonalForest = registerOverworldBiome(BiomeGenSeasonalForest.class, "Seasonal Forest", 2, 10);
        BOPCBiomes.shield = registerOverworldBiome(BiomeGenShield.class, "Shield", 2, 7);
        BOPCBiomes.shrubland = registerOverworldBiome(BiomeGenShrubland.class, "Shrubland", 2, 10);
        BOPCBiomes.sludgepit = registerOverworldBiome(BiomeGenSludgepit.class, "Sludgepit", 1, 5);
        BOPCBiomes.steppe = registerOverworldBiome(BiomeGenSteppe.class, "Steppe", 0, 5);
        BOPCBiomes.temperateRainforest = registerOverworldBiome(
            BiomeGenTemperateRainforest.class,
            "Temperate Rainforest",
            2,
            10);
        BOPCBiomes.thicket = registerOverworldBiome(BiomeGenThicket.class, "Thicket", 2, 5);
        BOPCBiomes.tropicalRainforest = registerOverworldBiome(
            BiomeGenTropicalRainforest.class,
            "Tropical Rainforest",
            0,
            5);
        BOPCBiomes.tundra = registerOverworldBiome(BiomeGenTundra.class, "Tundra", 3, 7);
        BOPCBiomes.wasteland = registerOverworldBiome(BiomeGenWasteland.class, "Wasteland", 0, 3);
        BOPCBiomes.wetland = registerOverworldBiome(BiomeGenWetland.class, "Wetland", 1, 7);
        BOPCBiomes.woodland = registerOverworldBiome(BiomeGenWoodland.class, "Woodland", 2, 10);
        BOPCBiomes.xericShrubland = registerOverworldBiome(BiomeGenXericShrubland.class, "Xeric Shrubland", 0, 5);
        BOPCBiomes.coralReef = registerOverworldSubBiome(BiomeGenCoralReef.class, "Coral Reef", 10, BiomeGenBase.ocean);
        BOPCBiomes.kelpForest = registerOverworldSubBiome(
            BiomeGenKelpForest.class,
            "Kelp Forest",
            10,
            BiomeGenBase.ocean);
        BOPCBiomes.tropics = registerOverworldSubBiome(BiomeGenTropics.class, "Tropics", 10, BiomeGenBase.deepOcean);
        BOPCBiomes.volcano = registerOverworldSubBiome(BiomeGenVolcano.class, "Volcano", 10, BiomeGenBase.deepOcean);
        BOPCBiomes.mangrove = registerOverworldSubBiome(BiomeGenMangrove.class, "Mangrove", 10, BiomeGenBase.deepOcean);
        BOPCBiomes.alpsForest = registerOverworldSubBiome(BiomeGenAlpsForest.class, "Alps Forest", 10, BOPCBiomes.alps);
        BOPCBiomes.canyonRavine = registerOverworldSubBiome(
            BiomeGenCanyonRavine.class,
            "Canyon Ravine",
            10,
            BOPCBiomes.canyon);
        BOPCBiomes.glacier = registerOverworldSubBiome(BiomeGenGlacier.class, "Glacier", 10, BOPCBiomes.arctic);
        BOPCBiomes.landOfLakesMarsh = registerOverworldSubBiome(
            BiomeGenLandOfLakesMarsh.class,
            "Land of Lakes Marsh",
            10,
            BOPCBiomes.landOfLakes);
        BOPCBiomes.meadowForest = registerOverworldSubBiome(
            BiomeGenMeadowForest.class,
            "Meadow Forest",
            10,
            BOPCBiomes.meadow);
        BOPCBiomes.oasis = registerOverworldSubBiome(BiomeGenOasis.class, "Oasis", 10, BiomeGenBase.desert);
        BOPCBiomes.quagmire = registerOverworldSubBiome(BiomeGenQuagmire.class, "Quagmire", 10, BOPCBiomes.sludgepit);
        BOPCBiomes.scrubland = registerOverworldSubBiome(
            BiomeGenScrubland.class,
            "Scrubland",
            10,
            BiomeGenBase.savanna);
        BOPCBiomes.seasonalForestClearing = registerOverworldSubBiome(
            BiomeGenSeasonalForestClearing.class,
            "Seasonal Forest Clearing",
            10,
            BOPCBiomes.seasonalForest);
        BOPCBiomes.silkglades = registerOverworldSubBiome(
            BiomeGenSilkglades.class,
            "Silkglades",
            10,
            BOPCBiomes.sludgepit);
        BOPCBiomes.spruceWoods = registerOverworldSubBiome(
            BiomeGenSpruceWoods.class,
            "Spruce Woods",
            10,
            BiomeGenBase.forest);
        BOPCBiomes.corruptedSands = registerNetherBiome(BiomeGenCorruptedSands.class, "Corrupted Sands", 10);
        BOPCBiomes.phantasmagoricInferno = registerNetherBiome(
            BiomeGenPhantasmagoricInferno.class,
            "Phantasmagoric Inferno",
            10);
        BOPCBiomes.boneyard = registerNetherBiome(BiomeGenBoneyard.class, "Boneyard", 10);
        BOPCBiomes.visceralHeap = registerNetherBiome(BiomeGenVisceralHeap.class, "Visceral Heap", 10);
        BOPCBiomes.undergarden = registerNetherBiome(BiomeGenUndergarden.class, "Undergarden", 10);
        BOPCBiomes.polarChasm = registerNetherBiome(BiomeGenPolarChasm.class, "Polar Chasm", 1);
        BOPCBiomes.spectralGarden = registerEndBiome(BiomeGenSpectralGarden.class, "Spectral Garden", 10);
        BOPCBiomes.lushRiver = registerOverworldRiverBiome(
            BiomeGenLushRiver.class,
            "Lush River",
            BOPCBiomes.lushSwamp,
            BOPCBiomes.lavenderFields,
            BOPCBiomes.flowerField,
            BOPCBiomes.bambooForest,
            BOPCBiomes.cherryBlossomGrove,
            BOPCBiomes.lushDesert,
            BOPCBiomes.meadow,
            BOPCBiomes.spruceWoods,
            BOPCBiomes.rainforest,
            BiomeGenBase.forest,
            BiomeGenBase.forestHills,
            BiomeGenBase.jungle,
            BiomeGenBase.jungleEdge,
            BiomeGenBase.jungleHills);
        BOPCBiomes.dryRiver = registerOverworldRiverBiome(
            BiomeGenDryRiver.class,
            "Dry River",
            BOPCBiomes.outback,
            BOPCBiomes.steppe,
            BiomeGenBase.desert,
            BiomeGenBase.desertHills);
    }

    private static void registerOverriddenBiomes() {
        registerOverriddenBiome(
            BiomeGenBOPBirchForest.class,
            BOPObfuscationHelper.birchForest,
            BOPObfuscationHelper.birchForestHills);
        registerOverriddenBiome(BiomeGenBOPDesert.class, BOPObfuscationHelper.desert, BOPObfuscationHelper.desertHills);
        registerOverriddenBiome(
            BiomeGenBOPExtremeHills.class,
            BOPObfuscationHelper.extremeHills,
            BOPObfuscationHelper.extremeHillsEdge);
        registerOverriddenBiome(BiomeGenBOPForest.class, BOPObfuscationHelper.forest, BOPObfuscationHelper.forestHills);
        registerOverriddenBiome(BiomeGenBOPIcePlains.class, BOPObfuscationHelper.icePlains);
        registerOverriddenBiome(
            BiomeGenBOPJungle.class,
            BOPObfuscationHelper.jungle,
            BOPObfuscationHelper.jungleEdge,
            BOPObfuscationHelper.jungleHills);
        registerOverriddenBiome(
            BiomeGenBOPMesa.class,
            BOPObfuscationHelper.mesa,
            BOPObfuscationHelper.mesaPlateau,
            BOPObfuscationHelper.mesaPlateau_F);
        registerOverriddenBiome(
            BiomeGenBOPMushroomIsland.class,
            BOPObfuscationHelper.mushroomIsland,
            BOPObfuscationHelper.mushroomIslandShore);
        registerOverriddenBiome(BiomeGenBOPOcean.class, BOPObfuscationHelper.ocean);
        registerOverriddenBiome(BiomeGenBOPPlains.class, BOPObfuscationHelper.plains);
        registerOverriddenBiome(BiomeGenBOPRiver.class, BOPObfuscationHelper.river);
        registerOverriddenBiome(BiomeGenBOPRoofedForest.class, BOPObfuscationHelper.roofedForest);
        registerOverriddenBiome(
            BiomeGenBOPSavanna.class,
            BOPObfuscationHelper.savanna,
            BOPObfuscationHelper.savannaPlateau);
        registerOverriddenBiome(BiomeGenBOPSwamp.class, BOPObfuscationHelper.swampland);
        registerOverriddenBiome(
            BiomeGenBOPTaiga.class,
            BOPObfuscationHelper.taiga,
            BOPObfuscationHelper.taigaHills,
            BOPObfuscationHelper.coldTaiga,
            BOPObfuscationHelper.coldTaigaHills);
        registerOverriddenBiome(BiomeGenBOPHell.class, BOPObfuscationHelper.hell);
    }

    private static void addBiomesToDictionary() {
        registerBiomeToDictionary(BOPCBiomes.alps, Type.SNOWY, Type.MOUNTAIN, Type.COLD);
        registerBiomeToDictionary(BOPCBiomes.alpsForest, Type.SNOWY, Type.MOUNTAIN, Type.FOREST, Type.COLD);
        registerBiomeToDictionary(BOPCBiomes.arctic, Type.SNOWY, Type.WASTELAND, Type.COLD, Type.DEAD);
        registerBiomeToDictionary(BOPCBiomes.bambooForest, Type.JUNGLE, Type.FOREST, Type.DENSE, Type.LUSH);
        registerBiomeToDictionary(BOPCBiomes.bayou, Type.SWAMP, Type.WATER, Type.LUSH, Type.WET);
        registerBiomeToDictionary(BOPCBiomes.bog, Type.SWAMP, Type.FOREST, Type.WET, Type.DEAD);
        registerBiomeToDictionary(BOPCBiomes.boneyard, Type.NETHER, Type.WASTELAND, Type.SPOOKY);
        registerBiomeToDictionary(BOPCBiomes.borealForest, Type.FOREST, Type.DENSE, Type.CONIFEROUS, Type.LUSH);
        registerBiomeToDictionary(BOPCBiomes.brushland, Type.PLAINS, Type.DRY, Type.HOT, Type.SAVANNA);
        registerBiomeToDictionary(
            BOPCBiomes.canyon,
            Type.SANDY,
            Type.MOUNTAIN,
            Type.HILLS,
            Type.SPARSE,
            Type.DRY,
            Type.HOT);
        registerBiomeToDictionary(BOPCBiomes.canyonRavine, Type.SANDY, Type.HILLS, Type.DRY, Type.HOT);
        registerBiomeToDictionary(BOPCBiomes.chaparral, Type.PLAINS, Type.SPARSE);
        registerBiomeToDictionary(BOPCBiomes.cherryBlossomGrove, Type.MAGICAL, Type.FOREST, Type.LUSH);
        registerBiomeToDictionary(BOPCBiomes.coniferousForest, Type.FOREST, Type.HILLS, Type.CONIFEROUS, Type.DENSE);
        registerBiomeToDictionary(
            BOPCBiomes.snowyConiferousForest,
            Type.SNOWY,
            Type.FOREST,
            Type.HILLS,
            Type.COLD,
            Type.CONIFEROUS,
            Type.DENSE);
        registerBiomeToDictionary(BOPCBiomes.corruptedSands, Type.NETHER, Type.SANDY, Type.SPOOKY);
        registerBiomeToDictionary(BOPCBiomes.crag, Type.WASTELAND, Type.MOUNTAIN, Type.SPOOKY, Type.DEAD, Type.DRY);
        registerBiomeToDictionary(BOPCBiomes.deadForest, Type.FOREST, Type.DEAD, Type.SPARSE, Type.SPOOKY);
        registerBiomeToDictionary(BOPCBiomes.deadSwamp, Type.SWAMP, Type.DEAD, Type.SPARSE, Type.SPOOKY);
        registerBiomeToDictionary(BOPCBiomes.deciduousForest, Type.FOREST, Type.DENSE, Type.DRY);
        registerBiomeToDictionary(BOPCBiomes.fen, Type.FOREST, Type.SWAMP, Type.DEAD, Type.WET);
        registerBiomeToDictionary(BOPCBiomes.flowerField, Type.PLAINS, Type.LUSH);
        registerBiomeToDictionary(BOPCBiomes.frostForest, Type.SNOWY, Type.FOREST, Type.COLD, Type.SPARSE);
        registerBiomeToDictionary(
            BOPCBiomes.fungiForest,
            Type.MAGICAL,
            Type.MUSHROOM,
            Type.FOREST,
            Type.SWAMP,
            Type.LUSH,
            Type.WET);
        registerBiomeToDictionary(BOPCBiomes.garden, Type.MAGICAL, Type.PLAINS, Type.LUSH);
        registerBiomeToDictionary(BOPCBiomes.glacier, Type.SNOWY, Type.HILLS, Type.COLD, Type.DEAD);
        registerBiomeToDictionary(BOPCBiomes.grassland, Type.PLAINS, Type.SWAMP, Type.HILLS, Type.SPARSE, Type.LUSH);
        registerBiomeToDictionary(BOPCBiomes.grove, Type.FOREST, Type.PLAINS, Type.DENSE, Type.LUSH);
        registerBiomeToDictionary(BOPCBiomes.heathland, Type.PLAINS, Type.DRY, Type.SAVANNA);
        registerBiomeToDictionary(BOPCBiomes.highland, Type.HILLS, Type.MOUNTAIN, Type.SPARSE);
        registerBiomeToDictionary(BOPCBiomes.jadeCliffs, Type.FOREST, Type.MOUNTAIN, Type.DENSE);
        registerBiomeToDictionary(BOPCBiomes.lavenderFields, Type.PLAINS, Type.LUSH, Type.SPARSE);
        registerBiomeToDictionary(BOPCBiomes.lushDesert, Type.SANDY, Type.SAVANNA, Type.DRY, Type.LUSH, Type.HOT);
        registerBiomeToDictionary(BOPCBiomes.lushSwamp, Type.SWAMP, Type.WATER, Type.LUSH, Type.WET);
        registerBiomeToDictionary(BOPCBiomes.mangrove, Type.WATER, Type.FOREST, Type.WET, Type.LUSH);
        registerBiomeToDictionary(BOPCBiomes.mapleWoods, Type.FOREST, Type.COLD);
        registerBiomeToDictionary(BOPCBiomes.marsh, Type.SWAMP, Type.WATER, Type.WET, Type.SPARSE, Type.LUSH);
        registerBiomeToDictionary(BOPCBiomes.meadow, Type.FOREST, Type.PLAINS, Type.LUSH);
        registerBiomeToDictionary(BOPCBiomes.meadowForest, Type.FOREST, Type.PLAINS, Type.LUSH);
        registerBiomeToDictionary(BOPCBiomes.moor, Type.HILLS, Type.SWAMP, Type.SPARSE, Type.WET);
        registerBiomeToDictionary(BOPCBiomes.mountain, Type.MOUNTAIN, Type.FOREST, Type.DRY);
        registerBiomeToDictionary(BOPCBiomes.mysticGrove, Type.MAGICAL, Type.FOREST, Type.LUSH, Type.WET);
        registerBiomeToDictionary(BOPCBiomes.oasis, Type.SANDY, Type.JUNGLE, Type.LUSH, Type.DRY, Type.HOT);
        registerBiomeToDictionary(BOPCBiomes.coralReef, Type.WATER);
        registerBiomeToDictionary(BOPCBiomes.kelpForest, Type.WATER, Type.FOREST);
        registerBiomeToDictionary(
            BOPCBiomes.ominousWoods,
            Type.MAGICAL,
            Type.FOREST,
            Type.SPOOKY,
            Type.SWAMP,
            Type.DEAD);
        registerBiomeToDictionary(BOPCBiomes.orchard, Type.FOREST, Type.PLAINS, Type.LUSH, Type.SPARSE);
        registerBiomeToDictionary(BOPCBiomes.outback, Type.SANDY, Type.PLAINS, Type.SAVANNA, Type.DRY, Type.HOT);
        registerBiomeToDictionary(BOPCBiomes.phantasmagoricInferno, Type.NETHER, Type.SPOOKY);
        registerBiomeToDictionary(BOPCBiomes.prairie, Type.PLAINS, Type.DRY, Type.SPARSE);
        registerBiomeToDictionary(BOPCBiomes.quagmire, Type.SWAMP, Type.WASTELAND, Type.SPOOKY, Type.WET, Type.DEAD);
        registerBiomeToDictionary(
            BOPCBiomes.rainforest,
            Type.JUNGLE,
            Type.HILLS,
            Type.FOREST,
            Type.LUSH,
            Type.WET,
            Type.DENSE);
        registerBiomeToDictionary(BOPCBiomes.redwoodForest, Type.FOREST, Type.CONIFEROUS, Type.DENSE);
        registerBiomeToDictionary(
            BOPCBiomes.sacredSprings,
            Type.MOUNTAIN,
            Type.FOREST,
            Type.MAGICAL,
            Type.WET,
            Type.DENSE,
            Type.LUSH);
        registerBiomeToDictionary(BOPCBiomes.scrubland, Type.SAVANNA, Type.PLAINS, Type.DRY, Type.HOT, Type.SPARSE);
        registerBiomeToDictionary(BOPCBiomes.seasonalForest, Type.FOREST, Type.LUSH, Type.DENSE);
        registerBiomeToDictionary(BOPCBiomes.shield, Type.FOREST, Type.WATER, Type.CONIFEROUS, Type.WET);
        registerBiomeToDictionary(BOPCBiomes.shrubland, Type.PLAINS, Type.SPARSE, Type.DRY);
        registerBiomeToDictionary(BOPCBiomes.silkglades, Type.SWAMP, Type.FOREST, Type.SPOOKY, Type.DEAD);
        registerBiomeToDictionary(
            BOPCBiomes.sludgepit,
            Type.SWAMP,
            Type.FOREST,
            Type.WASTELAND,
            Type.WET,
            Type.DEAD,
            Type.SPOOKY);
        registerBiomeToDictionary(BOPCBiomes.spruceWoods, Type.FOREST, Type.CONIFEROUS, Type.LUSH, Type.DENSE);
        registerBiomeToDictionary(
            BOPCBiomes.steppe,
            Type.PLAINS,
            Type.SANDY,
            Type.DRY,
            Type.HOT,
            Type.SAVANNA,
            Type.SPARSE,
            Type.DEAD);
        registerBiomeToDictionary(
            BOPCBiomes.temperateRainforest,
            Type.FOREST,
            Type.HILLS,
            Type.WET,
            Type.CONIFEROUS,
            Type.LUSH);
        registerBiomeToDictionary(BOPCBiomes.thicket, Type.PLAINS, Type.FOREST, Type.DRY, Type.DEAD, Type.DENSE);
        registerBiomeToDictionary(
            BOPCBiomes.tropicalRainforest,
            Type.JUNGLE,
            Type.HOT,
            Type.WET,
            Type.LUSH,
            Type.DENSE);
        registerBiomeToDictionary(BOPCBiomes.tropics, Type.JUNGLE, Type.BEACH, Type.WATER, Type.WET, Type.LUSH);
        registerBiomeToDictionary(BOPCBiomes.tundra, Type.COLD, Type.WASTELAND, Type.DRY, Type.DEAD, Type.SPARSE);
        registerBiomeToDictionary(BOPCBiomes.undergarden, Type.NETHER, Type.JUNGLE, Type.SPOOKY);
        registerBiomeToDictionary(BOPCBiomes.visceralHeap, Type.NETHER, Type.SPOOKY);
        registerBiomeToDictionary(BOPCBiomes.volcano, Type.MOUNTAIN, Type.WASTELAND, Type.HOT, Type.DRY);
        registerBiomeToDictionary(BOPCBiomes.wasteland, Type.WASTELAND, Type.SPOOKY, Type.DEAD, Type.SPARSE);
        registerBiomeToDictionary(BOPCBiomes.wetland, Type.SWAMP, Type.FOREST, Type.LUSH, Type.WET, Type.DENSE);
        registerBiomeToDictionary(BOPCBiomes.woodland, Type.FOREST, Type.DRY, Type.DENSE);
    }

    private static void registerBiomeToDictionary(BiomeGenBase biome, Type... types) {
        if (biome != null) {
            BiomeDictionary.registerBiomeType(biome, types);
        }
    }

    private static void disableRivers() {
        disableRiver(BOPCBiomes.quagmire);
        disableRiver(BOPCBiomes.sludgepit);
        disableRiver(BOPCBiomes.silkglades);
        disableRiver(BOPCBiomes.wetland);
        disableRiver(BOPCBiomes.wasteland);
        disableRiver(BOPCBiomes.tropicalRainforest);
        disableRiver(BOPCBiomes.originValley);
        disableRiver(BOPCBiomes.ominousWoods);
        disableRiver(BOPCBiomes.mysticGrove);
        disableRiver(BOPCBiomes.deadSwamp);
        disableRiver(BOPCBiomes.crag);
        disableRiver(BOPCBiomes.bayou);
    }

    private static void addSpawnBiomes() {
        if (BOPConfigurationMisc.onlySpawnOnBeaches) {
            clearAllSpawnBiomes();
            addSpawnBiome(BiomeGenBase.beach);
            addSpawnBiome(BiomeGenBase.stoneBeach);
            addSpawnBiome(BiomeGenBase.coldBeach);
        } else {
            List[] var0 = BOPBiomeManager.overworldBiomes;
            int var1 = var0.length;

            for (int var2 = 0; var2 < var1; var2++) {
                for (BiomeEntry entry : (List<BiomeEntry>) var0[var2]) {
                    addSpawnBiome(entry.biome);
                }
            }
        }
    }

    private static BiomeGenBase registerOverworldBiome(Class<? extends BiomeGenBase> biomeClass, String biomeName,
        int temperatureType, int weight) {
        if (BOPBiomeManager.overworldBiomes[temperatureType] == null) {
            BOPBiomeManager.overworldBiomes[temperatureType] = new ArrayList<>();
        }

        return BOPBiomeManager.createAndRegisterBiome(
            biomeClass,
            "Overworld",
            biomeName,
            BOPBiomeManager.overworldBiomes[temperatureType],
            weight);
    }

    private static BiomeGenBase registerOverworldSubBiome(Class<? extends BiomeGenBase> biomeClass, String biomeName,
        int weight, BiomeGenBase... parents) {
        BiomeGenBase biome = BOPBiomeManager.createBiome(biomeClass, biomeName);
        if (biome == null) {
            return null;
        }

        BiomeEntry entry = new BiomeEntry(biome, weight);
        if (BOPConfigurationBiomeGen.config.get("Overworld (Sub) Biomes To Generate", biome.biomeName, true)
            .getBoolean(false)) {
            for (BiomeGenBase parent : parents) {
                if (parent != null) {
                    if (BOPBiomeManager.overworldSubBiomes[parent.biomeID] == null) {
                        BOPBiomeManager.overworldSubBiomes[parent.biomeID] = new ArrayList<>();
                    }

                    BOPBiomeManager.overworldSubBiomes[parent.biomeID].add(entry);
                }
            }
        }

        return biome;
    }

    private static BiomeGenBase registerOverworldRiverBiome(Class<? extends BiomeGenBase> biomeClass, String biomeName,
        BiomeGenBase... parents) {
        BiomeGenBase biome = BOPBiomeManager.createBiome(biomeClass, biomeName);
        if (biome != null
            && BOPConfigurationBiomeGen.config.get("Overworld (River) Biomes To Generate", biome.biomeName, true)
                .getBoolean(false)) {
            for (BiomeGenBase parent : parents) {
                if (parent != null) {
                    BOPBiomeManager.overworldRiverBiomes[parent.biomeID] = biome;
                }
            }
        }

        return null;
    }

    private static void disableRiver(BiomeGenBase biome) {
        BOPBiomeManager.overworldRiverBiomes[biome.biomeID] = biome;
    }

    private static BiomeGenBase registerNetherBiome(Class<? extends BiomeGenBase> biomeClass, String biomeName,
        int weight) {
        return BOPBiomeManager
            .createAndRegisterBiome(biomeClass, "Nether", biomeName, BOPBiomeManager.netherBiomes, weight);
    }

    private static BiomeGenBase registerEndBiome(Class<? extends BiomeGenBase> biomeClass, String biomeName,
        int weight) {
        return BOPBiomeManager.createAndRegisterBiome(biomeClass, "End", biomeName, BOPBiomeManager.endBiomes, weight);
    }

    private static void registerOverriddenBiome(Class<? extends BOPOverriddenBiome> biomeClass,
        String[]... overriddenBiomeNames) {
        for (String[] overriddenBiomeName : overriddenBiomeNames) {
            Field field = BOPReflectionHelper.removeFinal(BiomeGenBase.class, null, overriddenBiomeName);

            try {
                BiomeGenBase biomeToOverride = (BiomeGenBase) field.get(null);
                if (biomeToOverride != null) {
                    BiomeGenBase newBiome = BOPBiomeManager
                        .createBiome(biomeClass, biomeToOverride.biomeName, biomeToOverride.biomeID);
                    if (BOPConfigurationBiomeGen.config
                        .get("Vanilla Biomes To Override", biomeToOverride.biomeName, true)
                        .getBoolean(false)) {
                        field.set(null, newBiome);
                        BiomeGenBase.getBiomeGenArray()[biomeToOverride.biomeID] = newBiome;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void addSpawnBiome(BiomeGenBase biome) {
        BiomeManager.addSpawnBiome(biome);
    }

    public static void clearAllSpawnBiomes() {
        WorldChunkManager.allowedBiomes.clear();
    }
}
