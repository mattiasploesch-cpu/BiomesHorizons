package biomesoplenty.client.particles;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

public class EntityDandelionFX extends EntityFX {

    private static final ResourceLocation texture = new ResourceLocation(
        "biomesoplenty:textures/particles/dandelion.png");
    private static final ResourceLocation particles = new ResourceLocation("textures/particle/particles.png");

    public EntityDandelionFX(World par1World, double par2, double par4, double par6, float par8) {
        super(par1World, par2, par4, par6, 0.0, 0.0, 0.0);
        this.motionX *= 0.20000000149011612;
        this.motionY *= 0.1F;
        this.motionZ *= 0.20000000149011612;
        float f4 = (float) Math.random() * 0.4F + 0.6F;
        this.particleScale *= 0.25F;
        this.particleScale *= par8;
        this.particleMaxAge = (int) (8.0 / (Math.random() * 0.8 + 0.2));
        this.particleMaxAge = (int) (this.particleMaxAge * par8);
        this.noClip = false;
        this.setSize(0.01F, 0.01F);
    }

    public void renderParticle(Tessellator tessellator, float par2, float par3, float par4, float par5, float par6,
        float par7) {
        tessellator.draw();
        GL11.glPushMatrix();
        GL11.glDepthMask(false);
        GL11.glEnable(3042);
        FMLClientHandler.instance()
            .getClient().renderEngine.bindTexture(texture);
        float sizeFactor = 0.1F * this.particleScale;
        float var13 = (float) (this.prevPosX + (this.posX - this.prevPosX) * par2 - EntityFX.interpPosX);
        float var14 = (float) (this.prevPosY + (this.posY - this.prevPosY) * par2 - EntityFX.interpPosY);
        float var15 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * par2 - EntityFX.interpPosZ);
        tessellator.startDrawingQuads();
        tessellator.setBrightness(240);
        tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, 1.0F);
        tessellator.addVertexWithUV(
            var13 - par3 * sizeFactor - par6 * sizeFactor,
            var14 - par4 * sizeFactor,
            var15 - par5 * sizeFactor - par7 * sizeFactor,
            0.0,
            1.0);
        tessellator.addVertexWithUV(
            var13 - par3 * sizeFactor + par6 * sizeFactor,
            var14 + par4 * sizeFactor,
            var15 - par5 * sizeFactor + par7 * sizeFactor,
            1.0,
            1.0);
        tessellator.addVertexWithUV(
            var13 + par3 * sizeFactor + par6 * sizeFactor,
            var14 + par4 * sizeFactor,
            var15 + par5 * sizeFactor + par7 * sizeFactor,
            1.0,
            0.0);
        tessellator.addVertexWithUV(
            var13 + par3 * sizeFactor - par6 * sizeFactor,
            var14 - par4 * sizeFactor,
            var15 + par5 * sizeFactor - par7 * sizeFactor,
            0.0,
            0.0);
        tessellator.draw();
        GL11.glDisable(3042);
        GL11.glDepthMask(true);
        GL11.glPopMatrix();
        FMLClientHandler.instance()
            .getClient().renderEngine.bindTexture(particles);
        tessellator.startDrawingQuads();
    }

    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }

        this.motionY += 0.004;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        if (this.posY == this.prevPosY) {
            this.motionX *= 1.1;
            this.motionZ *= 1.1;
        }

        this.motionX *= 0.96F;
        this.motionY *= 0.96F;
        this.motionZ *= 0.96F;
        if (this.onGround) {
            this.motionX *= 0.7F;
            this.motionZ *= 0.7F;
        }
    }
}
