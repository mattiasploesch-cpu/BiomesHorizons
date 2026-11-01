package biomesoplenty.client.particles;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

public class EntitySteamFX extends EntityFX {

    private static final ResourceLocation texture = new ResourceLocation("biomesoplenty:textures/particles/steam.png");
    private static final ResourceLocation particles = new ResourceLocation("textures/particle/particles.png");
    float steamParticleScale;

    public EntitySteamFX(World par1World, double par2, double par4, double par6, double par8, double par10,
        double par12) {
        this(par1World, par2, par4, par6, par8, par10, par12, 1.0F);
    }

    public EntitySteamFX(World par1World, double par2, double par4, double par6, double par8, double par10,
        double par12, float par14) {
        super(par1World, par2, par4, par6, 0.0, 0.0, 0.0);
        this.motionX *= 0.1F;
        this.motionY *= 0.1F;
        this.motionZ *= 0.1F;
        this.motionX += par8;
        this.motionY += par10;
        this.motionZ += par12;
        this.particleRed = this.particleGreen = this.particleBlue = (float) (Math.random() * 0.3F);
        this.particleScale *= 0.75F;
        this.particleScale *= par14;
        this.steamParticleScale = this.particleScale;
        this.particleMaxAge = (int) (8.0 / (Math.random() * 0.8 + 0.2));
        this.particleMaxAge = (int) (this.particleMaxAge * par14);
        this.noClip = false;
    }

    public void renderParticle(Tessellator tessellator, float par2, float par3, float par4, float par5, float par6,
        float par7) {
        float f = (this.particleAge + par2) / this.particleMaxAge * 32.0F;
        if (f < 0.0F) {
            f = 0.0F;
        }

        if (f > 1.0F) {
            f = 1.0F;
        }

        this.particleScale *= f;
        tessellator.draw();
        GL11.glPushMatrix();
        GL11.glDepthMask(false);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 1);
        FMLClientHandler.instance()
            .getClient().renderEngine.bindTexture(texture);
        float f6 = this.particleTextureIndexX / 16.0F;
        float f7 = f6 + 0.0624375F;
        float f8 = this.particleTextureIndexY / 16.0F;
        float f9 = f8 + 0.0624375F;
        float f10 = 0.1F * this.particleScale;
        if (this.particleIcon != null) {
            f6 = this.particleIcon.getMinU();
            f7 = this.particleIcon.getMaxU();
            f8 = this.particleIcon.getMinV();
            f9 = this.particleIcon.getMaxV();
        }

        float f11 = (float) (this.prevPosX + (this.posX - this.prevPosX) * par2 - interpPosX);
        float f12 = (float) (this.prevPosY + (this.posY - this.prevPosY) * par2 - interpPosY);
        float f13 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * par2 - interpPosZ);
        float f14 = 1.0F;
        tessellator.startDrawingQuads();
        tessellator.setBrightness(10);
        tessellator.setColorRGBA_F(
            this.particleRed * f14,
            this.particleGreen * f14,
            this.particleBlue * f14,
            this.particleAlpha);
        tessellator
            .addVertexWithUV(f11 - par3 * f10 - par6 * f10, f12 - par4 * f10, f13 - par5 * f10 - par7 * f10, f7, f9);
        tessellator
            .addVertexWithUV(f11 - par3 * f10 + par6 * f10, f12 + par4 * f10, f13 - par5 * f10 + par7 * f10, f7, f8);
        tessellator
            .addVertexWithUV(f11 + par3 * f10 + par6 * f10, f12 + par4 * f10, f13 + par5 * f10 + par7 * f10, f6, f8);
        tessellator
            .addVertexWithUV(f11 + par3 * f10 - par6 * f10, f12 - par4 * f10, f13 + par5 * f10 - par7 * f10, f6, f9);
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

        this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
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
