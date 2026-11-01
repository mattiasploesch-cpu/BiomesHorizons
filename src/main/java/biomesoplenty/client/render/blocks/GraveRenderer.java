package biomesoplenty.client.render.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

import biomesoplenty.client.render.RenderUtils;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class GraveRenderer implements ISimpleBlockRenderingHandler {

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        if (modelId == RenderUtils.graveModel) {
            int meta = world.getBlockMetadata(x, y, z);
            float pixel = 0.0625F;
            if (meta == 0) {
                renderer.setRenderBounds(pixel * 5.0F, 0.0, pixel * 5.0F, pixel * 11.0F, pixel * 3.0F, pixel * 11.0F);
                renderer.renderStandardBlock(block, x, y, z);
                renderer.setRenderBounds(
                    pixel * 6.0F,
                    pixel * 3.0F,
                    pixel * 6.0F,
                    pixel * 10.0F,
                    pixel * 11.0F,
                    pixel * 10.0F);
                renderer.renderStandardBlock(block, x, y, z);
                renderer.setRenderBounds(0.0, pixel * 11.0F, pixel * 5.0F, pixel * 16.0F, pixel * 14.0F, pixel * 11.0F);
                renderer.renderStandardBlock(block, x, y, z);
            } else if (meta == 1) {
                renderer.setRenderBounds(
                    pixel * 13.0F,
                    pixel * -2.0F,
                    pixel * 5.0F,
                    pixel * 16.0F,
                    pixel * 8.0F,
                    pixel * 11.0F);
                renderer.renderStandardBlock(block, x, y, z);
                renderer.setRenderBounds(0.0, pixel * -2.0F, pixel * 5.0F, pixel * 3.0F, pixel * 8.0F, pixel * 11.0F);
                renderer.renderStandardBlock(block, x, y, z);
                renderer.setRenderBounds(
                    pixel * 6.0F,
                    pixel * -3.0F,
                    pixel * 7.0F,
                    pixel * 10.0F,
                    pixel * 14.0F,
                    pixel * 9.0F);
                renderer.renderStandardBlock(block, x, y, z);
                renderer.setRenderBounds(
                    pixel * -4.0F,
                    pixel * 1.0F,
                    pixel * 7.0F,
                    pixel * 6.0F,
                    pixel * 5.0F,
                    pixel * 9.0F);
                renderer.renderStandardBlock(block, x, y, z);
                renderer.setRenderBounds(
                    pixel * 10.0F,
                    pixel * 1.0F,
                    pixel * 7.0F,
                    pixel * 20.0F,
                    pixel * 5.0F,
                    pixel * 9.0F);
                renderer.renderStandardBlock(block, x, y, z);
                renderer.setRenderBounds(0.0, pixel * 8.0F, pixel * 5.0F, pixel * 16.0F, pixel * 11.0F, pixel * 11.0F);
                renderer.renderStandardBlock(block, x, y, z);
            } else if (meta == 2) {
                renderer.setRenderBounds(pixel * 5.0F, 0.0, pixel * 5.0F, pixel * 11.0F, pixel * 3.0F, pixel * 11.0F);
                renderer.renderStandardBlock(block, x, y, z);
                renderer.setRenderBounds(
                    pixel * 6.0F,
                    pixel * 3.0F,
                    pixel * 6.0F,
                    pixel * 10.0F,
                    pixel * 11.0F,
                    pixel * 10.0F);
                renderer.renderStandardBlock(block, x, y, z);
                renderer.setRenderBounds(pixel * 5.0F, pixel * 11.0F, 0.0, pixel * 11.0F, pixel * 14.0F, pixel * 16.0F);
                renderer.renderStandardBlock(block, x, y, z);
            } else if (meta == 3) {
                renderer.setRenderBounds(
                    pixel * 5.0F,
                    pixel * -2.0F,
                    pixel * 13.0F,
                    pixel * 11.0F,
                    pixel * 8.0F,
                    pixel * 16.0F);
                renderer.renderStandardBlock(block, x, y, z);
                renderer.setRenderBounds(pixel * 5.0F, pixel * -2.0F, 0.0, pixel * 11.0F, pixel * 8.0F, pixel * 3.0F);
                renderer.renderStandardBlock(block, x, y, z);
                renderer.setRenderBounds(
                    pixel * 7.0F,
                    pixel * -3.0F,
                    pixel * 6.0F,
                    pixel * 9.0F,
                    pixel * 14.0F,
                    pixel * 10.0F);
                renderer.renderStandardBlock(block, x, y, z);
                renderer.setRenderBounds(
                    pixel * 7.0F,
                    pixel * 1.0F,
                    pixel * -4.0F,
                    pixel * 9.0F,
                    pixel * 5.0F,
                    pixel * 6.0F);
                renderer.renderStandardBlock(block, x, y, z);
                renderer.setRenderBounds(
                    pixel * 7.0F,
                    pixel * 1.0F,
                    pixel * 10.0F,
                    pixel * 9.0F,
                    pixel * 5.0F,
                    pixel * 20.0F);
                renderer.renderStandardBlock(block, x, y, z);
                renderer.setRenderBounds(pixel * 5.0F, pixel * 8.0F, 0.0, pixel * 11.0F, pixel * 11.0F, pixel * 16.0F);
                renderer.renderStandardBlock(block, x, y, z);
            }
        }

        return true;
    }

    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {}

    public boolean shouldRender3DInInventory(int modelId) {
        return false;
    }

    public int getRenderId() {
        return RenderUtils.graveModel;
    }
}
