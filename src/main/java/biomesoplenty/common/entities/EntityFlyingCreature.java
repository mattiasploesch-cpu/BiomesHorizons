package biomesoplenty.common.entities;

import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFlyingCreature extends EntityAmbientCreature {

    public EntityFlyingCreature(World par1World) {
        super(par1World);
    }

    protected void fall(float par1) {}

    protected void updateFallState(double par1, boolean par3) {}

    public void moveEntityWithHeading(float par1, float par2) {
        if (this.isInWater()) {
            this.moveFlying(par1, par2, 0.02F);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.8F;
            this.motionY *= 0.8F;
            this.motionZ *= 0.8F;
        } else if (this.handleLavaMovement()) {
            this.moveFlying(par1, par2, 0.02F);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.5;
            this.motionY *= 0.5;
            this.motionZ *= 0.5;
        } else {
            float f2 = 0.91F;
            if (this.onGround) {
                f2 = 0.54600006F;
                Block block = this.worldObj.getBlock(
                    MathHelper.floor_double(this.posX),
                    MathHelper.floor_double(this.boundingBox.minY) - 1,
                    MathHelper.floor_double(this.posZ));
                f2 = block.slipperiness * 0.91F;
            }

            float f3 = 0.16277136F / (f2 * f2 * f2);
            this.moveFlying(par1, par2, this.onGround ? 0.1F * f3 : 0.02F);
            f2 = 0.91F;
            if (this.onGround) {
                f2 = 0.54600006F;
                Block block = this.worldObj.getBlock(
                    MathHelper.floor_double(this.posX),
                    MathHelper.floor_double(this.boundingBox.minY) - 1,
                    MathHelper.floor_double(this.posZ));
                f2 = block.slipperiness * 0.91F;
            }

            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= f2;
            this.motionY *= f2;
            this.motionZ *= f2;
        }

        this.prevLimbSwingAmount = this.limbSwingAmount;
        double d0 = this.posX - this.prevPosX;
        double d1 = this.posZ - this.prevPosZ;
        float f4 = MathHelper.sqrt_double(d0 * d0 + d1 * d1) * 4.0F;
        if (f4 > 1.0F) {
            f4 = 1.0F;
        }

        this.limbSwingAmount = this.limbSwingAmount + (f4 - this.limbSwingAmount) * 0.4F;
        this.limbSwing = this.limbSwing + this.limbSwingAmount;
    }

    public boolean isOnLadder() {
        return false;
    }
}
