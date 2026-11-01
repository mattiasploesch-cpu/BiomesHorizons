package biomesoplenty.client.render.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import biomesoplenty.api.content.BOPCBlocks;
import biomesoplenty.client.render.RenderUtils;
import biomesoplenty.common.blocks.BlockBOPFoliage;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class FoliageRenderer implements ISimpleBlockRenderingHandler {

    private final int FLAXTOP = 6;

    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {}

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        int meta = world.getBlockMetadata(x, y, z);
        if (block == BOPCBlocks.foliage) {
            if (meta == 0) {
                return this.renderBlockAlgae(renderer, block, x, y, z);
            } else if (meta == 13) {
                return this.renderBlockClover(renderer, block, x, y, z);
            } else if (meta == 14) {
                return this.renderBlockLeafPile(renderer, block, x, y, z);
            } else {
                return meta == 15 ? this.renderBlockDeadLeafPile(renderer, block, x, y, z)
                    : this.renderCrossedSquares(block, x, y, z, renderer);
            }
        } else if (block == BOPCBlocks.lilyBop) {
            return this.renderBlockLily(renderer, block, meta, x, y, z);
        } else {
            return meta == 0 ? this.renderBlockAlgae(renderer, block, x, y, z)
                : this.renderCrossedSquares(block, x, y, z, renderer);
        }
    }

    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    public int getRenderId() {
        return RenderUtils.foliageModel;
    }

    public boolean renderBlockAlgae(RenderBlocks renderer, Block block, int x, int y, int z) {
        Tessellator tessellator = Tessellator.instance;
        IBlockAccess world = renderer.blockAccess;
        IIcon icon = renderer.getBlockIconFromSide(block, 1);
        if (renderer.hasOverrideBlockTexture()) {
            icon = renderer.overrideBlockTexture;
        }

        float cf = 1.0F;
        int cl = block.colorMultiplier(world, x, y, z);
        float c1 = (cl >> 16 & 0xFF) / 255.0F;
        float c2 = (cl >> 8 & 0xFF) / 255.0F;
        float c3 = (cl & 0xFF) / 255.0F;
        if (EntityRenderer.anaglyphEnable) {
            float f4 = (c1 * 30.0F + c2 * 59.0F + c3 * 11.0F) / 100.0F;
            float f5 = (c1 * 30.0F + c2 * 70.0F) / 100.0F;
            float f6 = (c1 * 30.0F + c3 * 70.0F) / 100.0F;
            c1 = f4;
            c2 = f5;
            c3 = f6;
        }

        tessellator.setColorOpaque_F(cf * c1, cf * c2, cf * c3);
        float f = 0.015625F;
        double d0 = icon.getMinU();
        double d1 = icon.getMinV();
        double d2 = icon.getMaxU();
        double d3 = icon.getMaxV();
        long l = x * 3129871 ^ z * 116129781L ^ y;
        l = l * l * 42317861L + l * 11L;
        int i1 = (int) (l >> 16 & 3L);
        tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        float f1 = x + 0.5F;
        float f2 = z + 0.5F;
        float f3 = (i1 & 1) * 0.5F * (1 - i1 / 2 % 2 * 2);
        float f4 = (i1 + 1 & 1) * 0.5F * (1 - (i1 + 1) / 2 % 2 * 2);
        tessellator.addVertexWithUV(f1 + f3 - f4, y + f, f2 + f3 + f4, d0, d1);
        tessellator.addVertexWithUV(f1 + f3 + f4, y + f, f2 - f3 + f4, d2, d1);
        tessellator.addVertexWithUV(f1 - f3 + f4, y + f, f2 - f3 - f4, d2, d3);
        tessellator.addVertexWithUV(f1 - f3 - f4, y + f, f2 + f3 - f4, d0, d3);
        tessellator.addVertexWithUV(f1 - f3 - f4, y + f, f2 + f3 - f4, d0, d3);
        tessellator.addVertexWithUV(f1 - f3 + f4, y + f, f2 - f3 - f4, d2, d3);
        tessellator.addVertexWithUV(f1 + f3 + f4, y + f, f2 - f3 + f4, d2, d1);
        tessellator.addVertexWithUV(f1 + f3 - f4, y + f, f2 + f3 + f4, d0, d1);
        return true;
    }

    public boolean renderBlockLily(RenderBlocks renderer, Block block, int meta, int x, int y, int z) {
        Tessellator tessellator = Tessellator.instance;
        IBlockAccess world = renderer.blockAccess;
        IIcon icon = renderer.getBlockIconFromSideAndMetadata(block, 1, meta);
        if (renderer.hasOverrideBlockTexture()) {
            icon = renderer.overrideBlockTexture;
        }

        float cf = 1.0F;
        int cl = block.colorMultiplier(world, x, y, z);
        float c1 = (cl >> 16 & 0xFF) / 255.0F;
        float c2 = (cl >> 8 & 0xFF) / 255.0F;
        float c3 = (cl & 0xFF) / 255.0F;
        if (EntityRenderer.anaglyphEnable) {
            float f4 = (c1 * 30.0F + c2 * 59.0F + c3 * 11.0F) / 100.0F;
            float f5 = (c1 * 30.0F + c2 * 70.0F) / 100.0F;
            float f6 = (c1 * 30.0F + c3 * 70.0F) / 100.0F;
            c1 = f4;
            c2 = f5;
            c3 = f6;
        }

        tessellator.setColorOpaque_F(cf * c1, cf * c2, cf * c3);
        float f = 0.015625F;
        double d0 = icon.getMinU();
        double d1 = icon.getMinV();
        double d2 = icon.getMaxU();
        double d3 = icon.getMaxV();
        long l = x * 3129871 ^ z * 116129781L ^ y;
        l = l * l * 42317861L + l * 11L;
        int i1 = (int) (l >> 16 & 3L);
        tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        float f1 = x + 0.5F;
        float f2 = z + 0.5F;
        float f3 = (i1 & 1) * 0.5F * (1 - i1 / 2 % 2 * 2);
        float f4 = (i1 + 1 & 1) * 0.5F * (1 - (i1 + 1) / 2 % 2 * 2);
        tessellator.addVertexWithUV(f1 + f3 - f4, y + f, f2 + f3 + f4, d0, d1);
        tessellator.addVertexWithUV(f1 + f3 + f4, y + f, f2 - f3 + f4, d2, d1);
        tessellator.addVertexWithUV(f1 - f3 + f4, y + f, f2 - f3 - f4, d2, d3);
        tessellator.addVertexWithUV(f1 - f3 - f4, y + f, f2 + f3 - f4, d0, d3);
        tessellator.addVertexWithUV(f1 - f3 - f4, y + f, f2 + f3 - f4, d0, d3);
        tessellator.addVertexWithUV(f1 - f3 + f4, y + f, f2 - f3 - f4, d2, d3);
        tessellator.addVertexWithUV(f1 + f3 + f4, y + f, f2 - f3 + f4, d2, d1);
        tessellator.addVertexWithUV(f1 + f3 - f4, y + f, f2 + f3 + f4, d0, d1);
        return true;
    }

    public boolean renderBlockClover(RenderBlocks renderer, Block block, int x, int y, int z) {
        Tessellator tessellator = Tessellator.instance;
        IBlockAccess world = renderer.blockAccess;
        IIcon icon = renderer.getBlockIconFromSideAndMetadata(block, 1, 13);
        if (renderer.hasOverrideBlockTexture()) {
            icon = renderer.overrideBlockTexture;
        }

        float cf = 1.0F;
        int cl = block.colorMultiplier(world, x, y, z);
        float c1 = (cl >> 16 & 0xFF) / 255.0F;
        float c2 = (cl >> 8 & 0xFF) / 255.0F;
        float c3 = (cl & 0xFF) / 255.0F;
        if (EntityRenderer.anaglyphEnable) {
            float f4 = (c1 * 30.0F + c2 * 59.0F + c3 * 11.0F) / 100.0F;
            float f5 = (c1 * 30.0F + c2 * 70.0F) / 100.0F;
            float f6 = (c1 * 30.0F + c3 * 70.0F) / 100.0F;
            c1 = f4;
            c2 = f5;
            c3 = f6;
        }

        tessellator.setColorOpaque_F(cf * c1, cf * c2, cf * c3);
        float f = 0.1F;
        double d0 = icon.getMinU();
        double d1 = icon.getMinV();
        double d2 = icon.getMaxU();
        double d3 = icon.getMaxV();
        long l = x * 3129871 ^ z * 116129781L ^ y;
        l = l * l * 42317861L + l * 11L;
        int i1 = (int) (l >> 16 & 3L);
        tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        float f1 = x + 0.5F;
        float f2 = z + 0.5F;
        float f3 = (i1 & 1) * 0.5F * (1 - i1 / 2 % 2 * 2);
        float f4 = (i1 + 1 & 1) * 0.5F * (1 - (i1 + 1) / 2 % 2 * 2);
        tessellator.addVertexWithUV(f1 + f3 - f4, y + f, f2 + f3 + f4, d0, d1);
        tessellator.addVertexWithUV(f1 + f3 + f4, y + f, f2 - f3 + f4, d2, d1);
        tessellator.addVertexWithUV(f1 - f3 + f4, y + f, f2 - f3 - f4, d2, d3);
        tessellator.addVertexWithUV(f1 - f3 - f4, y + f, f2 + f3 - f4, d0, d3);
        tessellator.addVertexWithUV(f1 - f3 - f4, y + f, f2 + f3 - f4, d0, d3);
        tessellator.addVertexWithUV(f1 - f3 + f4, y + f, f2 - f3 - f4, d2, d3);
        tessellator.addVertexWithUV(f1 + f3 + f4, y + f, f2 - f3 + f4, d2, d1);
        tessellator.addVertexWithUV(f1 + f3 - f4, y + f, f2 + f3 + f4, d0, d1);
        return true;
    }

    public boolean renderBlockLeafPile(RenderBlocks renderer, Block block, int x, int y, int z) {
        Tessellator tessellator = Tessellator.instance;
        IBlockAccess world = renderer.blockAccess;
        IIcon icon = renderer.getBlockIconFromSideAndMetadata(block, 1, 14);
        if (renderer.hasOverrideBlockTexture()) {
            icon = renderer.overrideBlockTexture;
        }

        float cf = 1.0F;
        int cl = block.colorMultiplier(world, x, y, z);
        float c1 = (cl >> 16 & 0xFF) / 255.0F;
        float c2 = (cl >> 8 & 0xFF) / 255.0F;
        float c3 = (cl & 0xFF) / 255.0F;
        if (EntityRenderer.anaglyphEnable) {
            float f4 = (c1 * 30.0F + c2 * 59.0F + c3 * 11.0F) / 100.0F;
            float f5 = (c1 * 30.0F + c2 * 70.0F) / 100.0F;
            float f6 = (c1 * 30.0F + c3 * 70.0F) / 100.0F;
            c1 = f4;
            c2 = f5;
            c3 = f6;
        }

        tessellator.setColorOpaque_F(cf * c1, cf * c2, cf * c3);
        float f = 0.05F;
        double d0 = icon.getMinU();
        double d1 = icon.getMinV();
        double d2 = icon.getMaxU();
        double d3 = icon.getMaxV();
        long l = x * 3129871 ^ z * 116129781L ^ y;
        l = l * l * 42317861L + l * 11L;
        int i1 = (int) (l >> 16 & 3L);
        tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        float f1 = x + 0.5F;
        float f2 = z + 0.5F;
        float f3 = (i1 & 1) * 0.5F * (1 - i1 / 2 % 2 * 2);
        float f4 = (i1 + 1 & 1) * 0.5F * (1 - (i1 + 1) / 2 % 2 * 2);
        tessellator.addVertexWithUV(f1 + f3 - f4, y + f, f2 + f3 + f4, d0, d1);
        tessellator.addVertexWithUV(f1 + f3 + f4, y + f, f2 - f3 + f4, d2, d1);
        tessellator.addVertexWithUV(f1 - f3 + f4, y + f, f2 - f3 - f4, d2, d3);
        tessellator.addVertexWithUV(f1 - f3 - f4, y + f, f2 + f3 - f4, d0, d3);
        tessellator.addVertexWithUV(f1 - f3 - f4, y + f, f2 + f3 - f4, d0, d3);
        tessellator.addVertexWithUV(f1 - f3 + f4, y + f, f2 - f3 - f4, d2, d3);
        tessellator.addVertexWithUV(f1 + f3 + f4, y + f, f2 - f3 + f4, d2, d1);
        tessellator.addVertexWithUV(f1 + f3 - f4, y + f, f2 + f3 + f4, d0, d1);
        return true;
    }

    public boolean renderBlockDeadLeafPile(RenderBlocks renderer, Block block, int x, int y, int z) {
        Tessellator tessellator = Tessellator.instance;
        IBlockAccess world = renderer.blockAccess;
        IIcon icon = renderer.getBlockIconFromSideAndMetadata(block, 1, 15);
        if (renderer.hasOverrideBlockTexture()) {
            icon = renderer.overrideBlockTexture;
        }

        float cf = 1.0F;
        int cl = block.colorMultiplier(world, x, y, z);
        float c1 = (cl >> 16 & 0xFF) / 255.0F;
        float c2 = (cl >> 8 & 0xFF) / 255.0F;
        float c3 = (cl & 0xFF) / 255.0F;
        if (EntityRenderer.anaglyphEnable) {
            float f4 = (c1 * 30.0F + c2 * 59.0F + c3 * 11.0F) / 100.0F;
            float f5 = (c1 * 30.0F + c2 * 70.0F) / 100.0F;
            float f6 = (c1 * 30.0F + c3 * 70.0F) / 100.0F;
            c1 = f4;
            c2 = f5;
            c3 = f6;
        }

        tessellator.setColorOpaque_F(cf * c1, cf * c2, cf * c3);
        float f = 0.05F;
        double d0 = icon.getMinU();
        double d1 = icon.getMinV();
        double d2 = icon.getMaxU();
        double d3 = icon.getMaxV();
        long l = x * 3129871 ^ z * 116129781L ^ y;
        l = l * l * 42317861L + l * 11L;
        int i1 = (int) (l >> 16 & 3L);
        tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
        float f1 = x + 0.5F;
        float f2 = z + 0.5F;
        float f3 = (i1 & 1) * 0.5F * (1 - i1 / 2 % 2 * 2);
        float f4 = (i1 + 1 & 1) * 0.5F * (1 - (i1 + 1) / 2 % 2 * 2);
        tessellator.addVertexWithUV(f1 + f3 - f4, y + f, f2 + f3 + f4, d0, d1);
        tessellator.addVertexWithUV(f1 + f3 + f4, y + f, f2 - f3 + f4, d2, d1);
        tessellator.addVertexWithUV(f1 - f3 + f4, y + f, f2 - f3 - f4, d2, d3);
        tessellator.addVertexWithUV(f1 - f3 - f4, y + f, f2 + f3 - f4, d0, d3);
        tessellator.addVertexWithUV(f1 - f3 - f4, y + f, f2 + f3 - f4, d0, d3);
        tessellator.addVertexWithUV(f1 - f3 + f4, y + f, f2 - f3 - f4, d2, d3);
        tessellator.addVertexWithUV(f1 + f3 + f4, y + f, f2 - f3 + f4, d2, d1);
        tessellator.addVertexWithUV(f1 + f3 - f4, y + f, f2 + f3 + f4, d0, d1);
        return true;
    }

    private boolean renderCrossedSquares(Block par1Block, int par2, int par3, int par4, RenderBlocks renderer) {
        Tessellator tessellator = Tessellator.instance;
        IBlockAccess world = renderer.blockAccess;
        tessellator.setBrightness(par1Block.getMixedBrightnessForBlock(world, par2, par3, par4));
        float f = 1.0F;
        int l = par1Block.colorMultiplier(world, par2, par3, par4);
        float f1 = (l >> 16 & 0xFF) / 255.0F;
        float f2 = (l >> 8 & 0xFF) / 255.0F;
        float f3 = (l & 0xFF) / 255.0F;
        if (EntityRenderer.anaglyphEnable) {
            float f4 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
            float f5 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
            float f6 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
            f1 = f4;
            f2 = f5;
            f3 = f6;
        }

        if ((world.getBlockMetadata(par2, par3, par4) == 8 || world.getBlockMetadata(par2, par3, par4) == 9)
            && world.getBlock(par2, par3, par4) == BOPCBlocks.foliage) {
            tessellator.setColorOpaque_F(f, f, f);
        } else {
            tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
        }

        double d0 = par2;
        double d1 = par3;
        double d2 = par4;
        if (par1Block == BOPCBlocks.foliage) {
            long i1;
            if (world.getBlockMetadata(par2, par3, par4) == 6) {
                i1 = par2 * 3129871 ^ par4 * 116129781L ^ par3 - 1;
            } else {
                i1 = par2 * 3129871 ^ par4 * 116129781L ^ par3;
            }

            i1 = i1 * i1 * 42317861L + i1 * 11L;
            d0 += ((float) (i1 >> 16 & 15L) / 15.0F - 0.5) * 0.5;
            d1 += ((float) (i1 >> 20 & 15L) / 15.0F - 1.0) * 0.2;
            d2 += ((float) (i1 >> 24 & 15L) / 15.0F - 0.5) * 0.5;
        }

        if (world.getBlockMetadata(par2, par3, par4) == 10 && world.getBlock(par2, par3, par4) == BOPCBlocks.flowers) {
            renderer.drawCrossedSquares(
                par1Block.getIcon(0, world.getBlockMetadata(par2, par3, par4)),
                d0,
                d1 - 1.0,
                d2,
                1.0F);
        } else if (world.getBlockMetadata(par2, par3, par4) == 6
            && world.getBlock(par2, par3, par4) == BOPCBlocks.foliage) {
                renderFlax(d0, d1, d2, 1.0F, f1, f2, f3, renderer);
            } else if (world.getBlockMetadata(par2, par3, par4) == 8
                && world.getBlock(par2, par3, par4) == BOPCBlocks.foliage) {
                    renderBerryBush(d0, d1, d2, 1.0F, f1, f2, f3, renderer);
                } else if (world.getBlockMetadata(par2, par3, par4) == 9
                    && world.getBlock(par2, par3, par4) == BOPCBlocks.foliage) {
                        renderShrub(d0, d1, d2, 1.0F, f1, f2, f3, renderer);
                    } else {
                        renderer.drawCrossedSquares(
                            par1Block.getIcon(0, world.getBlockMetadata(par2, par3, par4)),
                            d0,
                            d1,
                            d2,
                            1.0F);
                    }

        return true;
    }

    private static void renderBerryBush(double par1, double par2, double par3, float par4, float par5, float par6,
        float par7, RenderBlocks renderer) {
        Tessellator tessellator = Tessellator.instance;
        IIcon berryBush = renderer.getBlockIconFromSideAndMetadata(BOPCBlocks.foliage, 0, 8);
        IIcon berryBushBerry = ((BlockBOPFoliage) BOPCBlocks.foliage).berryBushBerry;
        tessellator.setColorOpaque_F(par4 * par5, par4 * par6, par4 * par7);
        renderer.drawCrossedSquares(berryBush, par1, par2, par3, par4);
        tessellator.setColorOpaque_F(par4, par4, par4);
        renderer.drawCrossedSquares(berryBushBerry, par1, par2, par3, par4);
    }

    private static void renderShrub(double par1, double par2, double par3, float par4, float par5, float par6,
        float par7, RenderBlocks renderer) {
        Tessellator tessellator = Tessellator.instance;
        IIcon shrubLeaf = renderer.getBlockIconFromSideAndMetadata(BOPCBlocks.foliage, 0, 9);
        IIcon shrubBranch = ((BlockBOPFoliage) BOPCBlocks.foliage).shrubBranch;
        tessellator.setColorOpaque_F(par4 * par5, par4 * par6, par4 * par7);
        renderer.drawCrossedSquares(shrubLeaf, par1, par2, par3, par4);
        tessellator.setColorOpaque_F(par4, par4, par4);
        renderer.drawCrossedSquares(shrubBranch, par1, par2, par3, par4);
    }

    private static void renderFlax(double par1, double par2, double par3, float par4, float par5, float par6,
        float par7, RenderBlocks renderer) {
        Tessellator tessellator = Tessellator.instance;
        IIcon flaxStem = renderer.getBlockIconFromSideAndMetadata(BOPCBlocks.foliage, 0, 6);
        IIcon flaxFlowers = ((BlockBOPFoliage) BOPCBlocks.foliage).flaxFlowers;
        tessellator.setColorOpaque_F(par4 * par5, par4 * par6, par4 * par7);
        renderer.drawCrossedSquares(flaxStem, par1, par2, par3, par4);
        tessellator.setColorOpaque_F(par4, par4, par4);
        renderer.drawCrossedSquares(flaxFlowers, par1, par2, par3, par4);
    }
}
