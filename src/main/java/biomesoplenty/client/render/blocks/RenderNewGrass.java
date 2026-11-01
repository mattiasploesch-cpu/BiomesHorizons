package biomesoplenty.client.render.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;

import biomesoplenty.client.render.RenderUtils;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderNewGrass implements ISimpleBlockRenderingHandler {

    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        float inset = 1.0E-4F;
        renderer.setRenderBounds(inset, inset, inset, 1.0F - inset, 1.0F - inset, 1.0F - inset);
        RenderUtils.renderStandardInvBlock(renderer, Blocks.grass, 0);
        renderer.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
        RenderUtils.renderStandardInvBlock(renderer, block, metadata);
    }

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        if (renderer.hasOverrideBlockTexture()) {
            renderer.renderBlockUsingTexture(Blocks.grass, x, y, z, renderer.overrideBlockTexture);
        } else {
            renderer.renderStandardBlock(Blocks.grass, x, y, z);
            renderer.renderStandardBlock(block, x, y, z);
        }

        return true;
    }

    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    public int getRenderId() {
        return RenderUtils.newGrassModel;
    }
}
