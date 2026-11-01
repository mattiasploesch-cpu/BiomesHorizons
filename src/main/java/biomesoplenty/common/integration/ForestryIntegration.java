package biomesoplenty.common.integration;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;

import biomesoplenty.api.content.BOPCBlocks;
import biomesoplenty.api.content.BOPCItems;
import cpw.mods.fml.common.registry.GameRegistry;
import forestry.api.apiculture.FlowerManager;
import forestry.api.core.ForestryAPI;
import forestry.api.recipes.RecipeManagers;
import forestry.api.storage.BackpackManager;

public class ForestryIntegration {

    private static final String LIQUID_WATER = "water";
    private static final String LIQUID_BIOMASS = "biomass";
    private static final String LIQUID_JUICE = "juice";
    private static final String LIQUID_HONEY = "honey";
    private static final int MINER = 0;
    private static final int DIGGER = 1;
    private static final int FORESTER = 2;
    private static final Item mulch = GameRegistry.findItem("Forestry", "mulch");

    protected static void init() {
        addFermenterRecipes();
        addSqueezerRecipes();
        if (ForestryAPI.enabledPlugins.contains("APICULTURE")) {
            addBOPFlowers();
        }

        if (ForestryAPI.enabledPlugins.contains("STORAGE")) {
            addBlocksToBackpack();
        }
    }

    private static void addFermenterRecipes() {
        addFermenterRecipe(new ItemStack(BOPCBlocks.saplings, 1, 32767), 250, "biomass");
        addFermenterRecipe(new ItemStack(BOPCBlocks.colorizedSaplings, 1, 32767), 250, "biomass");
    }

    private static void addSqueezerRecipes() {
        RecipeManagers.squeezerManager.addRecipe(
            10,
            new ItemStack[] { new ItemStack(BOPCItems.food) },
            FluidRegistry.getFluidStack("juice", 50),
            new ItemStack(mulch),
            5);
        RecipeManagers.squeezerManager.addRecipe(
            10,
            new ItemStack[] { new ItemStack(BOPCItems.food, 1, 8) },
            FluidRegistry.getFluidStack("juice", 200),
            new ItemStack(mulch),
            20);
    }

    private static void addBOPFlowers() {
        for (int i = 0; i < 16; i++) {
            FlowerManager.flowerRegistry
                .registerPlantableFlower(BOPCBlocks.flowers, i, 1.0, new String[] { "flowersVanilla" });
        }

        for (int i = 0; i < 9; i++) {
            FlowerManager.flowerRegistry
                .registerPlantableFlower(BOPCBlocks.flowers2, i, 1.0, new String[] { "flowersVanilla" });
        }
    }

    private static void addBlocksToBackpack() {
        for (int i = 0; i < 16; i++) {
            if (i % 2 == 0) {
                BackpackManager.backpackItems[0].add(new ItemStack(BOPCBlocks.gemOre, 1, i));
            }
        }

        BackpackManager.backpackItems[0].add(new ItemStack(BOPCBlocks.driedDirt));
        BackpackManager.backpackItems[0].add(new ItemStack(BOPCBlocks.overgrownNetherrack));
        BackpackManager.backpackItems[0].add(new ItemStack(BOPCBlocks.cragRock));
        BackpackManager.backpackItems[0].add(new ItemStack(BOPCBlocks.ashStone));
        BackpackManager.backpackItems[0].add(new ItemStack(BOPCItems.gems, 1, 32767));
        BackpackManager.backpackItems[1].add(new ItemStack(BOPCBlocks.bopGrass, 1, 32767));
        BackpackManager.backpackItems[1].add(new ItemStack(BOPCBlocks.longGrass));
        BackpackManager.backpackItems[1].add(new ItemStack(BOPCBlocks.mud));
        BackpackManager.backpackItems[1].add(new ItemStack(BOPCBlocks.hardDirt));
        BackpackManager.backpackItems[1].add(new ItemStack(BOPCBlocks.hardSand));
        BackpackManager.backpackItems[1].add(new ItemStack(BOPCBlocks.originGrass));
        BackpackManager.backpackItems[1].add(new ItemStack(BOPCBlocks.ash));
        BackpackManager.backpackItems[2].add(new ItemStack(BOPCBlocks.logs1, 1, 32767));
        BackpackManager.backpackItems[2].add(new ItemStack(BOPCBlocks.logs2, 1, 32767));
        BackpackManager.backpackItems[2].add(new ItemStack(BOPCBlocks.logs3, 1, 32767));
        BackpackManager.backpackItems[2].add(new ItemStack(BOPCBlocks.logs4, 1, 32767));
        BackpackManager.backpackItems[2].add(new ItemStack(BOPCBlocks.colorizedSaplings, 1, 32767));
        BackpackManager.backpackItems[2].add(new ItemStack(BOPCBlocks.saplings, 1, 32767));
        BackpackManager.backpackItems[2].add(new ItemStack(BOPCBlocks.leaves1, 1, 32767));
        BackpackManager.backpackItems[2].add(new ItemStack(BOPCBlocks.leaves2, 1, 32767));
        BackpackManager.backpackItems[2].add(new ItemStack(BOPCBlocks.leaves3, 1, 32767));
        BackpackManager.backpackItems[2].add(new ItemStack(BOPCBlocks.leaves4, 1, 32767));
        BackpackManager.backpackItems[2].add(new ItemStack(BOPCBlocks.colorizedLeaves1, 1, 32767));
        BackpackManager.backpackItems[2].add(new ItemStack(BOPCBlocks.colorizedLeaves2, 1, 32767));
        BackpackManager.backpackItems[2].add(new ItemStack(BOPCBlocks.appleLeaves, 1, 32767));
        BackpackManager.backpackItems[2].add(new ItemStack(BOPCBlocks.persimmonLeaves, 1, 32767));
        BackpackManager.backpackItems[2].add(new ItemStack(BOPCBlocks.bamboo, 1, 32767));
        BackpackManager.backpackItems[2].add(new ItemStack(BOPCBlocks.petals, 1, 32767));
    }

    private static void addFermenterRecipe(ItemStack resource, int fermentationValue, String output) {
        RecipeManagers.fermenterManager.addRecipe(
            resource,
            fermentationValue,
            1.0F,
            FluidRegistry.getFluidStack(output, 1),
            FluidRegistry.getFluidStack("water", 1));
        if (FluidRegistry.isFluidRegistered("juice")) {
            RecipeManagers.fermenterManager.addRecipe(
                resource,
                fermentationValue,
                1.5F,
                FluidRegistry.getFluidStack(output, 1),
                FluidRegistry.getFluidStack("juice", 1));
        }

        if (FluidRegistry.isFluidRegistered("honey")) {
            RecipeManagers.fermenterManager.addRecipe(
                resource,
                fermentationValue,
                1.5F,
                FluidRegistry.getFluidStack(output, 1),
                FluidRegistry.getFluidStack("honey", 1));
        }
    }
}
