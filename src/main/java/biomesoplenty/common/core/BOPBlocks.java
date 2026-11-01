package biomesoplenty.common.core;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;

import biomesoplenty.api.content.BOPCBlocks;
import biomesoplenty.common.blocks.BlockAsh;
import biomesoplenty.common.blocks.BlockBOPAppleLeaves;
import biomesoplenty.common.blocks.BlockBOPColorizedLeaves;
import biomesoplenty.common.blocks.BlockBOPColorizedSapling;
import biomesoplenty.common.blocks.BlockBOPCoral;
import biomesoplenty.common.blocks.BlockBOPFlower;
import biomesoplenty.common.blocks.BlockBOPFlower2;
import biomesoplenty.common.blocks.BlockBOPFoliage;
import biomesoplenty.common.blocks.BlockBOPFruit;
import biomesoplenty.common.blocks.BlockBOPGems;
import biomesoplenty.common.blocks.BlockBOPGeneric;
import biomesoplenty.common.blocks.BlockBOPGrass;
import biomesoplenty.common.blocks.BlockBOPLeaves;
import biomesoplenty.common.blocks.BlockBOPLilypad;
import biomesoplenty.common.blocks.BlockBOPLog;
import biomesoplenty.common.blocks.BlockBOPMushroom;
import biomesoplenty.common.blocks.BlockBOPNewDirt;
import biomesoplenty.common.blocks.BlockBOPNewFarmland;
import biomesoplenty.common.blocks.BlockBOPNewGrass;
import biomesoplenty.common.blocks.BlockBOPPersimmonLeaves;
import biomesoplenty.common.blocks.BlockBOPPetals;
import biomesoplenty.common.blocks.BlockBOPPlank;
import biomesoplenty.common.blocks.BlockBOPPlant;
import biomesoplenty.common.blocks.BlockBOPRocks;
import biomesoplenty.common.blocks.BlockBOPSapling;
import biomesoplenty.common.blocks.BlockBOPSlab;
import biomesoplenty.common.blocks.BlockBOPStairs;
import biomesoplenty.common.blocks.BlockBamboo;
import biomesoplenty.common.blocks.BlockBones;
import biomesoplenty.common.blocks.BlockFlesh;
import biomesoplenty.common.blocks.BlockFlowerVine;
import biomesoplenty.common.blocks.BlockGrave;
import biomesoplenty.common.blocks.BlockHive;
import biomesoplenty.common.blocks.BlockHoney;
import biomesoplenty.common.blocks.BlockIvy;
import biomesoplenty.common.blocks.BlockLongGrass;
import biomesoplenty.common.blocks.BlockMoss;
import biomesoplenty.common.blocks.BlockMud;
import biomesoplenty.common.blocks.BlockOriginGrass;
import biomesoplenty.common.blocks.BlockOvergrownNetherrack;
import biomesoplenty.common.blocks.BlockStoneFormations;
import biomesoplenty.common.blocks.BlockTreeMoss;
import biomesoplenty.common.blocks.BlockTurnip;
import biomesoplenty.common.blocks.BlockWillow;
import biomesoplenty.common.blocks.BlockWisteria;
import biomesoplenty.common.fluids.blocks.BlockBloodFluid;
import biomesoplenty.common.fluids.blocks.BlockHoneyFluid;
import biomesoplenty.common.fluids.blocks.BlockPoisonFluid;
import biomesoplenty.common.itemblocks.ItemBlockAppleLeaves;
import biomesoplenty.common.itemblocks.ItemBlockBOPLilypad;
import biomesoplenty.common.itemblocks.ItemBlockBOPMetadata;
import biomesoplenty.common.itemblocks.ItemBlockBamboo;
import biomesoplenty.common.itemblocks.ItemBlockBones;
import biomesoplenty.common.itemblocks.ItemBlockColorizedLeaves;
import biomesoplenty.common.itemblocks.ItemBlockColorizedSapling;
import biomesoplenty.common.itemblocks.ItemBlockCoral;
import biomesoplenty.common.itemblocks.ItemBlockFlower;
import biomesoplenty.common.itemblocks.ItemBlockFlower2;
import biomesoplenty.common.itemblocks.ItemBlockFoliage;
import biomesoplenty.common.itemblocks.ItemBlockFruit;
import biomesoplenty.common.itemblocks.ItemBlockGems;
import biomesoplenty.common.itemblocks.ItemBlockGrass;
import biomesoplenty.common.itemblocks.ItemBlockGrave;
import biomesoplenty.common.itemblocks.ItemBlockHive;
import biomesoplenty.common.itemblocks.ItemBlockIvy;
import biomesoplenty.common.itemblocks.ItemBlockLeaves;
import biomesoplenty.common.itemblocks.ItemBlockLog;
import biomesoplenty.common.itemblocks.ItemBlockMoss;
import biomesoplenty.common.itemblocks.ItemBlockMushroom;
import biomesoplenty.common.itemblocks.ItemBlockPersimmonLeaves;
import biomesoplenty.common.itemblocks.ItemBlockPetals;
import biomesoplenty.common.itemblocks.ItemBlockPlank;
import biomesoplenty.common.itemblocks.ItemBlockPlant;
import biomesoplenty.common.itemblocks.ItemBlockRocks;
import biomesoplenty.common.itemblocks.ItemBlockSapling;
import biomesoplenty.common.itemblocks.ItemBlockSlab;
import biomesoplenty.common.itemblocks.ItemBlockWillow;
import cpw.mods.fml.common.registry.GameRegistry;

public class BOPBlocks {

    public static void init() {
        registerBlocks();
        setFireInfo();
    }

    private static void registerBlocks() {
        BOPCBlocks.mud = registerMetadataBlock(new BlockMud().setBlockName("mud"));
        BOPCBlocks.driedDirt = registerBlock(
            new BlockBOPGeneric(Material.rock, BlockBOPGeneric.BlockType.DRIED_DIRT).setBlockName("driedDirt"));
        BOPCBlocks.rocks = registerBlock(new BlockBOPRocks().setBlockName("rocks"), ItemBlockRocks.class);
        BOPCBlocks.ash = registerBlock(new BlockAsh().setBlockName("ash"));
        BOPCBlocks.flesh = registerBlock(new BlockFlesh().setBlockName("flesh"));
        BOPCBlocks.plants = registerBlock(new BlockBOPPlant().setBlockName("plants"), ItemBlockPlant.class);
        BOPCBlocks.flowers = registerBlock(new BlockBOPFlower().setBlockName("flowers"), ItemBlockFlower.class);
        BOPCBlocks.flowers2 = registerBlock(new BlockBOPFlower2().setBlockName("flowers2"), ItemBlockFlower2.class);
        BOPCBlocks.stoneFormations = registerMetadataBlock(new BlockStoneFormations().setBlockName("stoneFormations"));
        BOPCBlocks.mushrooms = registerBlock(new BlockBOPMushroom().setBlockName("mushrooms"), ItemBlockMushroom.class);
        BOPCBlocks.willow = registerBlock(new BlockWillow().setBlockName("willow"), ItemBlockWillow.class);
        BOPCBlocks.ivy = registerBlock(new BlockIvy().setBlockName("ivy"), ItemBlockIvy.class);
        BOPCBlocks.treeMoss = registerBlock(new BlockTreeMoss().setBlockName("treeMoss"));
        BOPCBlocks.flowerVine = registerBlock(new BlockFlowerVine().setBlockName("flowerVine"));
        BOPCBlocks.wisteria = registerBlock(new BlockWisteria().setBlockName("wisteria"));
        BOPCBlocks.lilyBop = registerBlock(new BlockBOPLilypad().setBlockName("lilyBop"), ItemBlockBOPLilypad.class);
        BOPCBlocks.foliage = registerBlock(new BlockBOPFoliage().setBlockName("foliage"), ItemBlockFoliage.class);
        BOPCBlocks.fruitBop = registerBlock(new BlockBOPFruit().setBlockName("fruitBop"), ItemBlockFruit.class);
        BOPCBlocks.turnip = registerBlock(new BlockTurnip().setBlockName("turnip"));
        BOPCBlocks.coral1 = registerBlock(
            new BlockBOPCoral(BlockBOPCoral.CoralCategory.CAT1).setBlockName("coral1"),
            ItemBlockCoral.class);
        BOPCBlocks.coral2 = registerBlock(
            new BlockBOPCoral(BlockBOPCoral.CoralCategory.CAT2).setBlockName("coral2"),
            ItemBlockCoral.class);
        BOPCBlocks.ashStone = registerBlock(
            new BlockBOPGeneric(Material.rock, BlockBOPGeneric.BlockType.ASH_STONE).setBlockName("ashStone"));
        BOPCBlocks.hardIce = registerBlock(
            new BlockBOPGeneric(Material.rock, BlockBOPGeneric.BlockType.HARD_ICE).setBlockName("hardIce"));
        BOPCBlocks.appleLeaves = registerBlock(
            new BlockBOPAppleLeaves().setBlockName("appleLeaves"),
            ItemBlockAppleLeaves.class);
        BOPCBlocks.persimmonLeaves = registerBlock(
            new BlockBOPPersimmonLeaves().setBlockName("persimmonLeaves"),
            ItemBlockPersimmonLeaves.class);
        BOPCBlocks.moss = registerBlock(new BlockMoss().setBlockName("moss"), ItemBlockMoss.class);
        BOPCBlocks.bamboo = registerBlock(new BlockBamboo().setBlockName("bamboo"), ItemBlockBamboo.class);
        BOPCBlocks.mudBricks = registerBlock(
            new BlockBOPGeneric(Material.rock, BlockBOPGeneric.BlockType.MUD_BRICK).setBlockName("mudBricks"));
        BOPCBlocks.originGrass = registerBlock(new BlockOriginGrass().setBlockName("originGrass"));
        BOPCBlocks.longGrass = registerBlock(new BlockLongGrass().setBlockName("longGrass"));
        BOPCBlocks.overgrownNetherrack = registerBlock(
            new BlockOvergrownNetherrack().setBlockName("overgrownNetherrack"));
        BOPCBlocks.bopGrass = registerBlock(new BlockBOPGrass().setBlockName("bopGrass"), ItemBlockGrass.class);
        BOPCBlocks.newBopGrass = registerMetadataBlock(new BlockBOPNewGrass().setBlockName("newBopGrass"));
        BOPCBlocks.newBopDirt = registerMetadataBlock(new BlockBOPNewDirt().setBlockName("newBopDirt"));
        BOPCBlocks.newBopFarmland = registerMetadataBlock(new BlockBOPNewFarmland().setBlockName("newBopFarmland"));
        BOPCBlocks.logs1 = registerBlock(
            new BlockBOPLog(BlockBOPLog.LogCategory.CAT1).setBlockName("logs1"),
            ItemBlockLog.class);
        BOPCBlocks.logs2 = registerBlock(
            new BlockBOPLog(BlockBOPLog.LogCategory.CAT2).setBlockName("logs2"),
            ItemBlockLog.class);
        BOPCBlocks.logs3 = registerBlock(
            new BlockBOPLog(BlockBOPLog.LogCategory.CAT3).setBlockName("logs3"),
            ItemBlockLog.class);
        BOPCBlocks.logs4 = registerBlock(
            new BlockBOPLog(BlockBOPLog.LogCategory.CAT4).setBlockName("logs4"),
            ItemBlockLog.class);
        BOPCBlocks.leaves1 = registerBlock(
            new BlockBOPLeaves(BlockBOPLeaves.LeafCategory.CAT1).setBlockName("leaves1"),
            ItemBlockLeaves.class);
        BOPCBlocks.leaves2 = registerBlock(
            new BlockBOPLeaves(BlockBOPLeaves.LeafCategory.CAT2).setBlockName("leaves2"),
            ItemBlockLeaves.class);
        BOPCBlocks.leaves3 = registerBlock(
            new BlockBOPLeaves(BlockBOPLeaves.LeafCategory.CAT3).setBlockName("leaves3"),
            ItemBlockLeaves.class);
        BOPCBlocks.leaves4 = registerBlock(
            new BlockBOPLeaves(BlockBOPLeaves.LeafCategory.CAT4).setBlockName("leaves4"),
            ItemBlockLeaves.class);
        BOPCBlocks.petals = registerBlock(new BlockBOPPetals().setBlockName("petals"), ItemBlockPetals.class);
        BOPCBlocks.saplings = registerBlock(new BlockBOPSapling().setBlockName("saplings"), ItemBlockSapling.class);
        BOPCBlocks.colorizedSaplings = registerBlock(
            new BlockBOPColorizedSapling().setBlockName("colorizedSaplings"),
            ItemBlockColorizedSapling.class);
        BOPCBlocks.hardSand = registerBlock(
            new BlockBOPGeneric(Material.sand, BlockBOPGeneric.BlockType.HARD_SAND).setBlockName("hardSand"));
        BOPCBlocks.hardDirt = registerBlock(
            new BlockBOPGeneric(Material.rock, BlockBOPGeneric.BlockType.HARD_DIRT).setBlockName("hardDirt"));
        BOPCBlocks.biomeBlock = registerBlock(
            new BlockBOPGeneric(Material.glass, BlockBOPGeneric.BlockType.BIOME_BLOCK).setBlockName("biomeBlock"));
        BOPCBlocks.crystal = registerBlock(
            new BlockBOPGeneric(Material.glass, BlockBOPGeneric.BlockType.CRYSTAL).setBlockName("crystal"));
        BOPCBlocks.gemOre = registerBlock(new BlockBOPGems().setBlockName("gemOre"), ItemBlockGems.class);
        BOPCBlocks.cragRock = registerBlock(
            new BlockBOPGeneric(Material.rock, BlockBOPGeneric.BlockType.CRAG_ROCK).setBlockName("cragRock"));
        BOPCBlocks.hive = registerBlock(new BlockHive().setBlockName("hive"), ItemBlockHive.class);
        BOPCBlocks.honeyBlock = registerBlock(new BlockHoney().setBlockName("honeyBlock"));
        BOPCBlocks.bones = registerBlock(new BlockBones().setBlockName("bones"), ItemBlockBones.class);
        BOPCBlocks.grave = registerBlock(new BlockGrave().setBlockName("grave"), ItemBlockGrave.class);
        BOPCBlocks.planks = registerBlock(new BlockBOPPlank().setBlockName("planks"), ItemBlockPlank.class);
        BlockBOPSlab singleSlab = (BlockBOPSlab) new BlockBOPSlab(false, Material.wood, BlockBOPSlab.SlabCategory.WOOD1)
            .setBlockName("woodenSingleSlab1");
        BlockBOPSlab doubleSlab = (BlockBOPSlab) new BlockBOPSlab(true, Material.wood, BlockBOPSlab.SlabCategory.WOOD1)
            .setBlockName("woodenDoubleSlab1");
        BOPCBlocks.woodenSingleSlab1 = registerBlock(singleSlab, ItemBlockSlab.class, singleSlab, doubleSlab);
        BOPCBlocks.woodenDoubleSlab1 = registerBlock(doubleSlab, ItemBlockSlab.class, singleSlab, doubleSlab);
        singleSlab = (BlockBOPSlab) new BlockBOPSlab(false, Material.wood, BlockBOPSlab.SlabCategory.WOOD2)
            .setBlockName("woodenSingleSlab2");
        doubleSlab = (BlockBOPSlab) new BlockBOPSlab(true, Material.wood, BlockBOPSlab.SlabCategory.WOOD2)
            .setBlockName("woodenDoubleSlab2");
        BOPCBlocks.woodenSingleSlab2 = registerBlock(singleSlab, ItemBlockSlab.class, singleSlab, doubleSlab);
        BOPCBlocks.woodenDoubleSlab2 = registerBlock(doubleSlab, ItemBlockSlab.class, singleSlab, doubleSlab);
        singleSlab = (BlockBOPSlab) new BlockBOPSlab(false, Material.rock, BlockBOPSlab.SlabCategory.STONE)
            .setBlockName("stoneSingleSlab");
        doubleSlab = (BlockBOPSlab) new BlockBOPSlab(true, Material.rock, BlockBOPSlab.SlabCategory.STONE)
            .setBlockName("stoneDoubleSlab");
        BOPCBlocks.stoneSingleSlab = registerBlock(singleSlab, ItemBlockSlab.class, singleSlab, doubleSlab);
        BOPCBlocks.stoneDoubleSlab = registerBlock(doubleSlab, ItemBlockSlab.class, singleSlab, doubleSlab);
        BOPCBlocks.sacredoakStairs = registerBlock(
            new BlockBOPStairs(BOPCBlocks.planks, BlockBOPStairs.Category.SACREDOAK).setBlockName("sacredoakStairs"));
        BOPCBlocks.cherryStairs = registerBlock(
            new BlockBOPStairs(BOPCBlocks.planks, BlockBOPStairs.Category.CHERRY).setBlockName("cherryStairs"));
        BOPCBlocks.darkStairs = registerBlock(
            new BlockBOPStairs(BOPCBlocks.planks, BlockBOPStairs.Category.DARK).setBlockName("darkStairs"));
        BOPCBlocks.firStairs = registerBlock(
            new BlockBOPStairs(BOPCBlocks.planks, BlockBOPStairs.Category.FIR).setBlockName("firStairs"));
        BOPCBlocks.etherealStairs = registerBlock(
            new BlockBOPStairs(BOPCBlocks.planks, BlockBOPStairs.Category.ETHEREAL).setBlockName("etherealStairs"));
        BOPCBlocks.magicStairs = registerBlock(
            new BlockBOPStairs(BOPCBlocks.planks, BlockBOPStairs.Category.MAGIC).setBlockName("magicStairs"));
        BOPCBlocks.mangroveStairs = registerBlock(
            new BlockBOPStairs(BOPCBlocks.planks, BlockBOPStairs.Category.MANGROVE).setBlockName("mangroveStairs"));
        BOPCBlocks.palmStairs = registerBlock(
            new BlockBOPStairs(BOPCBlocks.planks, BlockBOPStairs.Category.PALM).setBlockName("palmStairs"));
        BOPCBlocks.redwoodStairs = registerBlock(
            new BlockBOPStairs(BOPCBlocks.planks, BlockBOPStairs.Category.REDWOOD).setBlockName("redwoodStairs"));
        BOPCBlocks.willowStairs = registerBlock(
            new BlockBOPStairs(BOPCBlocks.planks, BlockBOPStairs.Category.WILLOW).setBlockName("willowStairs"));
        BOPCBlocks.pineStairs = registerBlock(
            new BlockBOPStairs(BOPCBlocks.planks, BlockBOPStairs.Category.PINE).setBlockName("pineStairs"));
        BOPCBlocks.hellBarkStairs = registerBlock(
            new BlockBOPStairs(BOPCBlocks.planks, BlockBOPStairs.Category.HELL_BARK).setBlockName("hellBarkStairs"));
        BOPCBlocks.jacarandaStairs = registerBlock(
            new BlockBOPStairs(BOPCBlocks.planks, BlockBOPStairs.Category.JACARANDA).setBlockName("jacarandaStairs"));
        BOPCBlocks.mahoganyStairs = registerBlock(
            new BlockBOPStairs(BOPCBlocks.planks, BlockBOPStairs.Category.MAHOGANY).setBlockName("mahoganyStairs"));
        BOPCBlocks.mudBricksStairs = registerBlock(
            new BlockBOPStairs(BOPCBlocks.mudBricks, BlockBOPStairs.Category.MUD_BRICKS)
                .setBlockName("mudBricksStairs"));
        BOPCBlocks.colorizedLeaves1 = registerBlock(
            new BlockBOPColorizedLeaves(BlockBOPColorizedLeaves.ColourizedLeafCategory.CAT1)
                .setBlockName("colorizedLeaves1"),
            ItemBlockColorizedLeaves.class);
        BOPCBlocks.colorizedLeaves2 = registerBlock(
            new BlockBOPColorizedLeaves(BlockBOPColorizedLeaves.ColourizedLeafCategory.CAT2)
                .setBlockName("colorizedLeaves2"),
            ItemBlockColorizedLeaves.class);
        BOPCBlocks.poison = registerBlock(new BlockPoisonFluid().setBlockName("poison"));
        BOPCBlocks.blood = registerBlock(new BlockBloodFluid().setBlockName("hell_blood"));
        BOPCBlocks.honey = registerBlock(new BlockHoneyFluid().setBlockName("honey"));
    }

    private static void setFireInfo() {
        Blocks.fire.setFireInfo(BOPCBlocks.bamboo, 5, 5);
        Blocks.fire.setFireInfo(BOPCBlocks.appleLeaves, 30, 60);
        Blocks.fire.setFireInfo(BOPCBlocks.colorizedLeaves1, 30, 60);
        Blocks.fire.setFireInfo(BOPCBlocks.colorizedLeaves2, 30, 60);
        Blocks.fire.setFireInfo(BOPCBlocks.leaves1, 30, 60);
        Blocks.fire.setFireInfo(BOPCBlocks.leaves2, 30, 60);
        Blocks.fire.setFireInfo(BOPCBlocks.leaves3, 30, 60);
        Blocks.fire.setFireInfo(BOPCBlocks.leaves4, 30, 60);
        Blocks.fire.setFireInfo(BOPCBlocks.logs1, 5, 5);
        Blocks.fire.setFireInfo(BOPCBlocks.logs2, 5, 5);
        Blocks.fire.setFireInfo(BOPCBlocks.logs3, 5, 5);
        Blocks.fire.setFireInfo(BOPCBlocks.logs4, 5, 5);
        Blocks.fire.setFireInfo(BOPCBlocks.foliage, 60, 100);
        Blocks.fire.setFireInfo(BOPCBlocks.persimmonLeaves, 30, 60);
        Blocks.fire.setFireInfo(BOPCBlocks.petals, 30, 60);
        Blocks.fire.setFireInfo(BOPCBlocks.planks, 5, 20);
        Blocks.fire.setFireInfo(BOPCBlocks.plants, 60, 100);
        Blocks.fire.setFireInfo(BOPCBlocks.woodenSingleSlab1, 5, 20);
        Blocks.fire.setFireInfo(BOPCBlocks.woodenDoubleSlab1, 5, 20);
        Blocks.fire.setFireInfo(BOPCBlocks.woodenSingleSlab2, 5, 20);
        Blocks.fire.setFireInfo(BOPCBlocks.woodenDoubleSlab2, 5, 20);
        Blocks.fire.setFireInfo(BOPCBlocks.sacredoakStairs, 5, 20);
        Blocks.fire.setFireInfo(BOPCBlocks.cherryStairs, 5, 20);
        Blocks.fire.setFireInfo(BOPCBlocks.darkStairs, 5, 20);
        Blocks.fire.setFireInfo(BOPCBlocks.firStairs, 5, 20);
        Blocks.fire.setFireInfo(BOPCBlocks.etherealStairs, 5, 20);
        Blocks.fire.setFireInfo(BOPCBlocks.magicStairs, 5, 20);
        Blocks.fire.setFireInfo(BOPCBlocks.mangroveStairs, 5, 20);
        Blocks.fire.setFireInfo(BOPCBlocks.palmStairs, 5, 20);
        Blocks.fire.setFireInfo(BOPCBlocks.redwoodStairs, 5, 20);
        Blocks.fire.setFireInfo(BOPCBlocks.willowStairs, 5, 20);
        Blocks.fire.setFireInfo(BOPCBlocks.pineStairs, 5, 20);
        Blocks.fire.setFireInfo(BOPCBlocks.jacarandaStairs, 5, 20);
        Blocks.fire.setFireInfo(BOPCBlocks.mahoganyStairs, 5, 20);
        Blocks.fire.setFireInfo(BOPCBlocks.moss, 15, 100);
        Blocks.fire.setFireInfo(BOPCBlocks.treeMoss, 15, 100);
        Blocks.fire.setFireInfo(BOPCBlocks.willow, 15, 100);
        Blocks.fire.setFireInfo(BOPCBlocks.flowerVine, 15, 100);
        Blocks.fire.setFireInfo(BOPCBlocks.wisteria, 15, 100);
    }

    public static Block registerBlock(Block block) {
        GameRegistry.registerBlock(
            block,
            block.getUnlocalizedName()
                .replace("tile.", ""));
        return block;
    }

    public static Block registerMetadataBlock(Block block) {
        registerBlock(block, ItemBlockBOPMetadata.class);
        return block;
    }

    public static Block registerBlock(Block block, Class<? extends ItemBlock> itemBlockClass) {
        GameRegistry.registerBlock(
            block,
            itemBlockClass,
            block.getUnlocalizedName()
                .replace("tile.", ""));
        return block;
    }

    public static Block registerBlock(Block block, Class<? extends ItemBlock> itemBlockClass,
        Object... constructorArgs) {
        GameRegistry.registerBlock(
            block,
            itemBlockClass,
            block.getUnlocalizedName()
                .replace("tile.", ""),
            constructorArgs);
        return block;
    }
}
