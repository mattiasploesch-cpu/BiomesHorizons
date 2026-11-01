package biomesoplenty.common.blocks;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import biomesoplenty.BiomesOPlenty;

public class BlockBOPStairs extends BlockStairs {

    private static final String[] woodTypes = new String[] { "sacredoak", "cherry", "dark", "fir", "ethereal", "magic",
        "mangrove", "palm", "redwood", "willow", "pine", "hell_bark", "jacaranda", "mahogany" };
    private static final String[] stoneTypes = new String[] { "mudbrick" };
    private IIcon[] textures;
    private final BlockBOPStairs.Category category;

    public BlockBOPStairs(Block model, BlockBOPStairs.Category cat) {
        super(model, 0);
        this.category = cat;
        if (this.isWoodCategory(this.category.toString())) {
            this.setHardness(2.0F);
            this.setStepSound(Block.soundTypeWood);
        } else {
            this.setHardness(3.0F);
            this.setStepSound(Block.soundTypePiston);
        }

        this.useNeighborBrightness = true;
        this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
    }

    public void registerBlockIcons(IIconRegister iconRegister) {
        if (this.isStoneCategory(this.category.toString())) {
            this.textures = new IIcon[stoneTypes.length];

            for (int i = 0; i < stoneTypes.length; i++) {
                this.textures[i] = iconRegister.registerIcon("biomesoplenty:" + stoneTypes[i]);
            }
        } else {
            this.textures = new IIcon[woodTypes.length];

            for (int i = 0; i < woodTypes.length; i++) {
                this.textures[i] = iconRegister.registerIcon("biomesoplenty:plank_" + woodTypes[i]);
            }
        }
    }

    public boolean isWoodCategory(String block) {
        String type = BlockBOPStairs.Category.valueOf(block).type;
        return type == "wood";
    }

    public boolean isStoneCategory(String block) {
        String type = BlockBOPStairs.Category.valueOf(block).type;
        return type == "stone";
    }

    public static int getWoodCategoryAmount() {
        int woodCatNo = 0;

        for (BlockBOPStairs.Category cat : BlockBOPStairs.Category.values()) {
            if (cat.values.contains("wood")) {
                woodCatNo++;
            }
        }

        return woodCatNo;
    }

    public static int getStoneCategoryAmount() {
        int woodCatNo = 0;

        for (BlockBOPStairs.Category cat : BlockBOPStairs.Category.values()) {
            if (cat.values.contains("stone")) {
                woodCatNo++;
            }
        }

        return woodCatNo;
    }

    public IIcon getIcon(int side, int meta) {
        int adjCat = this.category.ordinal();
        if (this.isStoneCategory(this.category.toString())) {
            adjCat -= getWoodCategoryAmount();
        }

        return this.textures[adjCat];
    }

    public enum Category {

        SACREDOAK("wood"),
        CHERRY("wood"),
        DARK("wood"),
        FIR("wood"),
        ETHEREAL("wood"),
        MAGIC("wood"),
        MANGROVE("wood"),
        PALM("wood"),
        REDWOOD("wood"),
        WILLOW("wood"),
        PINE("wood"),
        HELL_BARK("wood"),
        JACARANDA("wood"),
        MAHOGANY("wood"),
        MUD_BRICKS("stone");

        private final List<String> values;
        private String type;

        Category(String type) {
            this.type = type;
            this.values = Arrays.asList(type);
        }
    }
}
