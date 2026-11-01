package biomesoplenty.client.render.entities;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import biomesoplenty.client.models.entities.ModelPixie;
import biomesoplenty.common.entities.EntityPixie;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderPixie extends RenderLiving {

    public RenderPixie() {
        super(new ModelPixie(), 0.25F);
        this.setRenderPassModel(new ModelPixie());
        this.shadowSize = 0.0F;
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return new ResourceLocation("biomesoplenty:textures/mobs/pixie.png");
    }

    protected int setPixieBrightness(EntityPixie par1EntityPixie, int par2, float par3) {
        if (par2 != 0) {
            return -1;
        }

        this.bindTexture(new ResourceLocation("biomesoplenty:textures/mobs/pixie.png"));
        float f1 = 1.0F;
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glBlendFunc(1, 1);
        if (par1EntityPixie.isInvisible()) {
            GL11.glDepthMask(false);
        } else {
            GL11.glDepthMask(true);
        }

        char c0 = '\uf0f0';
        int j = c0 % 65536;
        int k = c0 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, f1);
        return 1;
    }

    protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3) {
        return this.setPixieBrightness((EntityPixie) par1EntityLivingBase, par2, par3);
    }
}
