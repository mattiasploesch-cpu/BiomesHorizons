package biomesoplenty.common.core;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;

import biomesoplenty.BiomesOPlenty;
import biomesoplenty.api.BOPItemHelper;
import biomesoplenty.api.content.BOPCBlocks;
import biomesoplenty.api.content.BOPCItems;
import biomesoplenty.common.armor.ItemAmethystArmor;
import biomesoplenty.common.armor.ItemFlippers;
import biomesoplenty.common.armor.ItemFlowerBand;
import biomesoplenty.common.armor.ItemMuddyArmor;
import biomesoplenty.common.armor.ItemWadingBoots;
import biomesoplenty.common.items.ItemBOPAncientStaff;
import biomesoplenty.common.items.ItemBOPAxe;
import biomesoplenty.common.items.ItemBOPBiomeEssence;
import biomesoplenty.common.items.ItemBOPBucket;
import biomesoplenty.common.items.ItemBOPFood;
import biomesoplenty.common.items.ItemBOPHoe;
import biomesoplenty.common.items.ItemBOPMisc;
import biomesoplenty.common.items.ItemBOPMudball;
import biomesoplenty.common.items.ItemBOPPickaxe;
import biomesoplenty.common.items.ItemBOPRecord;
import biomesoplenty.common.items.ItemBOPScythe;
import biomesoplenty.common.items.ItemBOPSeeds;
import biomesoplenty.common.items.ItemBOPSpade;
import biomesoplenty.common.items.ItemBOPSword;
import biomesoplenty.common.items.ItemBiomeFinder;
import biomesoplenty.common.items.ItemDart;
import biomesoplenty.common.items.ItemDartBlower;
import biomesoplenty.common.items.ItemEnderporter;
import biomesoplenty.common.items.ItemGems;
import biomesoplenty.common.items.ItemJarEmpty;
import biomesoplenty.common.items.ItemJarFilled;
import cpw.mods.fml.common.registry.GameRegistry;

public class BOPItems {

    public static void init() {
        initializeMaterials();
        registerItems();
    }

    private static void initializeMaterials() {
        BOPItemHelper.toolMaterialMud = EnumHelper.addToolMaterial("MUD", 0, 32, 0.5F, 0.0F, 1);
        BOPItemHelper.armorMaterialMud = EnumHelper.addArmorMaterial("MUD", 2, new int[] { 1, 1, 1, 1 }, 5);
        BOPItemHelper.toolMaterialAmethyst = EnumHelper.addToolMaterial("AMETHYST", 4, 2013, 15.0F, 5.0F, 16);
        BOPItemHelper.armorMaterialAmethyst = EnumHelper.addArmorMaterial("AMETHYST", 40, new int[] { 3, 8, 8, 3 }, 20);
        BOPItemHelper.armorMaterialUnprotective = EnumHelper
            .addArmorMaterial("UNPROTECTIVE", -1, new int[] { 0, 0, 0, 0 }, 0);
        registerCraftingMaterials();
    }

    private static void registerItems() {
        BOPCItems.food = registerItem(new ItemBOPFood(0).setUnlocalizedName("food"));
        BOPCItems.turnipSeeds = registerItem(
            new ItemBOPSeeds(BOPCBlocks.turnip, Blocks.farmland).setUnlocalizedName("turnipSeeds"));
        BOPCItems.misc = registerItem(new ItemBOPMisc().setUnlocalizedName("misc"));
        BOPCItems.gems = registerItem(new ItemGems().setUnlocalizedName("gems"));
        BOPCItems.mudball = registerItem(new ItemBOPMudball().setUnlocalizedName("mudball"));
        BOPCItems.jarEmpty = registerItem(new ItemJarEmpty().setUnlocalizedName("jarEmpty"));
        BOPCItems.jarFilled = registerItem(new ItemJarFilled().setUnlocalizedName("jarFilled"));
        BOPCItems.dartBlower = registerItem(new ItemDartBlower().setUnlocalizedName("dartBlower"));
        BOPCItems.dart = registerItem(new ItemDart().setUnlocalizedName("dart"));
        BOPCItems.ancientStaff = registerItem(new ItemBOPAncientStaff().setUnlocalizedName("ancientStaff"));
        BOPCItems.enderporter = registerItem(new ItemEnderporter().setUnlocalizedName("enderporter"));
        BOPCItems.bopBucket = registerItem(new ItemBOPBucket().setUnlocalizedName("bopBucket"));
        BOPCItems.record_wanderer = registerItem(new ItemBOPRecord("wanderer").setUnlocalizedName("record_wanderer"));
        BOPCItems.record_corruption = registerItem(
            new ItemBOPRecord("corruption").setUnlocalizedName("record_corruption"));
        BOPCItems.biomeFinder = registerItem(new ItemBiomeFinder().setUnlocalizedName("biomeFinder"));
        BOPCItems.biomeEssence = registerItem(new ItemBOPBiomeEssence().setUnlocalizedName("biomeEssence"));
        BOPCItems.swordMud = registerItem(
            new ItemBOPSword(BOPItemHelper.toolMaterialMud, 0).setUnlocalizedName("swordMud"));
        BOPCItems.shovelMud = registerItem(
            new ItemBOPSpade(BOPItemHelper.toolMaterialMud, 0).setUnlocalizedName("shovelMud"));
        BOPCItems.pickaxeMud = registerItem(
            new ItemBOPPickaxe(BOPItemHelper.toolMaterialMud, 0).setUnlocalizedName("pickaxeMud"));
        BOPCItems.axeMud = registerItem(new ItemBOPAxe(BOPItemHelper.toolMaterialMud, 0).setUnlocalizedName("axeMud"));
        BOPCItems.hoeMud = registerItem(new ItemBOPHoe(BOPItemHelper.toolMaterialMud, 0).setUnlocalizedName("hoeMud"));
        BOPCItems.swordAmethyst = registerItem(
            new ItemBOPSword(BOPItemHelper.toolMaterialAmethyst, 1).setUnlocalizedName("swordAmethyst"));
        BOPCItems.shovelAmethyst = registerItem(
            new ItemBOPSpade(BOPItemHelper.toolMaterialAmethyst, 1).setUnlocalizedName("shovelAmethyst"));
        BOPCItems.pickaxeAmethyst = registerItem(
            new ItemBOPPickaxe(BOPItemHelper.toolMaterialAmethyst, 1).setUnlocalizedName("pickaxeAmethyst"));
        BOPCItems.axeAmethyst = registerItem(
            new ItemBOPAxe(BOPItemHelper.toolMaterialAmethyst, 1).setUnlocalizedName("axeAmethyst"));
        BOPCItems.hoeAmethyst = registerItem(
            new ItemBOPHoe(BOPItemHelper.toolMaterialAmethyst, 1).setUnlocalizedName("hoeAmethyst"));
        BOPCItems.scytheWood = registerItem(new ItemBOPScythe(ToolMaterial.WOOD, 0).setUnlocalizedName("scytheWood"));
        BOPCItems.scytheStone = registerItem(
            new ItemBOPScythe(ToolMaterial.STONE, 1).setUnlocalizedName("scytheStone"));
        BOPCItems.scytheIron = registerItem(new ItemBOPScythe(ToolMaterial.IRON, 2).setUnlocalizedName("scytheIron"));
        BOPCItems.scytheGold = registerItem(new ItemBOPScythe(ToolMaterial.GOLD, 3).setUnlocalizedName("scytheGold"));
        BOPCItems.scytheDiamond = registerItem(
            new ItemBOPScythe(ToolMaterial.EMERALD, 4).setUnlocalizedName("scytheDiamond"));
        BOPCItems.scytheMud = registerItem(
            new ItemBOPScythe(BOPItemHelper.toolMaterialMud, 5).setUnlocalizedName("scytheMud"));
        BOPCItems.scytheAmethyst = registerItem(
            new ItemBOPScythe(BOPItemHelper.toolMaterialAmethyst, 6).setUnlocalizedName("scytheAmethyst"));
        BOPCItems.helmetMud = registerItem(
            new ItemMuddyArmor(BOPItemHelper.armorMaterialMud, BiomesOPlenty.proxy.addArmor("mud"), 0)
                .setUnlocalizedName("helmetMud"));
        BOPCItems.chestplateMud = registerItem(
            new ItemMuddyArmor(BOPItemHelper.armorMaterialMud, BiomesOPlenty.proxy.addArmor("mud"), 1)
                .setUnlocalizedName("chestplateMud"));
        BOPCItems.leggingsMud = registerItem(
            new ItemMuddyArmor(BOPItemHelper.armorMaterialMud, BiomesOPlenty.proxy.addArmor("mud"), 2)
                .setUnlocalizedName("leggingsMud"));
        BOPCItems.bootsMud = registerItem(
            new ItemMuddyArmor(BOPItemHelper.armorMaterialMud, BiomesOPlenty.proxy.addArmor("mud"), 3)
                .setUnlocalizedName("bootsMud"));
        BOPCItems.helmetAmethyst = registerItem(
            new ItemAmethystArmor(BOPItemHelper.armorMaterialAmethyst, BiomesOPlenty.proxy.addArmor("amethyst"), 0)
                .setUnlocalizedName("helmetAmethyst"));
        BOPCItems.chestplateAmethyst = registerItem(
            new ItemAmethystArmor(BOPItemHelper.armorMaterialAmethyst, BiomesOPlenty.proxy.addArmor("amethyst"), 1)
                .setUnlocalizedName("chestplateAmethyst"));
        BOPCItems.leggingsAmethyst = registerItem(
            new ItemAmethystArmor(BOPItemHelper.armorMaterialAmethyst, BiomesOPlenty.proxy.addArmor("amethyst"), 2)
                .setUnlocalizedName("leggingsAmethyst"));
        BOPCItems.bootsAmethyst = registerItem(
            new ItemAmethystArmor(BOPItemHelper.armorMaterialAmethyst, BiomesOPlenty.proxy.addArmor("amethyst"), 3)
                .setUnlocalizedName("bootsAmethyst"));
        BOPCItems.flowerBand = registerItem(
            new ItemFlowerBand(BOPItemHelper.armorMaterialUnprotective, BiomesOPlenty.proxy.addArmor("flowerBand"), 0)
                .setUnlocalizedName("flowerBand"));
        BOPCItems.flippers = registerItem(
            new ItemFlippers(BOPItemHelper.armorMaterialUnprotective, BiomesOPlenty.proxy.addArmor("flippers"), 3)
                .setUnlocalizedName("flippers"));
        BOPCItems.wadingBoots = registerItem(
            new ItemWadingBoots(BOPItemHelper.armorMaterialUnprotective, BiomesOPlenty.proxy.addArmor("wadingBoots"), 3)
                .setUnlocalizedName("wadingBoots"));
    }

    private static void registerCraftingMaterials() {
        BOPItemHelper.toolMaterialMud.customCraftingMaterial = BOPCItems.mudball;
        BOPItemHelper.armorMaterialMud.customCraftingMaterial = BOPCItems.mudball;
    }

    public static Item registerItem(Item item) {
        GameRegistry.registerItem(
            item,
            item.getUnlocalizedName()
                .replace("item.", ""));
        return item;
    }
}
