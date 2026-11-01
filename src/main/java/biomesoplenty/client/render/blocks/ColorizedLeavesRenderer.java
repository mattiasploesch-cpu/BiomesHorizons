package biomesoplenty.client.render.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import biomesoplenty.api.content.BOPCBlocks;
import biomesoplenty.client.render.RenderUtils;
import biomesoplenty.common.blocks.BlockBOPColorizedLeaves;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class ColorizedLeavesRenderer implements ISimpleBlockRenderingHandler {

    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        float inset = 1.0E-4F;
        BlockBOPColorizedLeaves leavesBlock = (BlockBOPColorizedLeaves) block;
        renderer.setRenderBounds(inset, inset, inset, 1.0F - inset, 1.0F - inset, 1.0F - inset);
        RenderUtils.renderStandardInvBlock(renderer, block, metadata);
        if (block == BOPCBlocks.colorizedLeaves2 && metadata % 4 == 3) {
            GL11.glColor3f(1.0F, 1.0F, 1.0F);
            renderer.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
            RenderUtils.renderStandardInvBlock(renderer, Blocks.stone, 0, leavesBlock.floweringIcon);
        }
    }

    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId,
        RenderBlocks renderer) {
        BlockBOPColorizedLeaves leavesBlock = (BlockBOPColorizedLeaves) block;
        int metadata = world.getBlockMetadata(x, y, z);
        if (block == BOPCBlocks.colorizedLeaves2 && metadata % 4 == 3 && renderer.hasOverrideBlockTexture()) {
            renderer.setOverrideBlockTexture(renderer.overrideBlockTexture);
            renderer.renderStandardBlock(block, x, y, z);
            renderer.clearOverrideBlockTexture();
        } else {
            renderer.renderStandardBlock(block, x, y, z);
            if (block == BOPCBlocks.colorizedLeaves2 && metadata % 4 == 3) {
                renderer.setOverrideBlockTexture(leavesBlock.floweringIcon);
                renderer.renderStandardBlock(Blocks.stone, x, y, z);
                renderer.clearOverrideBlockTexture();
            }
        }

        return true;
    }

    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    public int getRenderId() {
        return RenderUtils.leavesModel;
    }
}
