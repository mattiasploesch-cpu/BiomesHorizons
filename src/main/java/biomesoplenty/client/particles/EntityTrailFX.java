package biomesoplenty.client.particles;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityTrailFX extends EntityFX {

    private ResourceLocation trailResource;
    private int particleIndex;
    private double startY;

    public EntityTrailFX(World world, double x, double y, double z, String trailName) {
        super(world, x, y, z);
        this.trailResource = new ResourceLocation("biomesoplenty:textures/particles/" + trailName + ".png");
        this.motionX = this.motionY = this.motionZ = 0.0;
        this.particleMaxAge = 550;
        this.particleIndex = this.rand.nextInt(4);
        this.startY = y;
    }

    public void renderParticle(Tessellator renderer, float partialTicks, float rotX, float rotXZ, float rotZ,
        float rotYZ, float rotXY) {
        FMLClientHandler.instance()
            .getClient().renderEngine.bindTexture(this.trailResource);
        float minU = this.particleIndex % 2 * 0.5F;
        float maxU = minU + 0.5F;
        float minV = this.particleIndex / 2 * 0.5F;
        float maxV = minV + 0.5F;
        float alpha = 1.0F - Math.min(1.0F, 2.0F * this.particleAge / this.particleMaxAge);
        float width = 0.15F;
        float x = (float) (this.prevPosX + (this.posX - this.prevPosX) - interpPosX);
        float y = (float) (this.prevPosY + (this.posY - this.prevPosY) - interpPosY);
        float z = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) - interpPosZ);
        renderer.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, alpha);
        renderer.addVertexWithUV(x - width, y, z + width, minU, maxV);
        renderer.addVertexWithUV(x + width, y, z + width, minU, minV);
        renderer.addVertexWithUV(x + width, y, z - width, maxU, minV);
        renderer.addVertexWithUV(x - width, y, z - width, maxU, maxV);
    }

    public int getFXLayer() {
        return 2;
    }

    public void onUpdate() {
        super.onUpdate();
        this.posY = this.startY - 0.01 * ((float) this.particleAge / this.particleMaxAge);
        this.prevPosY = this.posY;
    }
}
