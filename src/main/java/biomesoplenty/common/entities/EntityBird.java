package biomesoplenty.common.entities;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityBird extends EntityFlyingCreature {

    public int courseChangeCooldown;
    public double waypointX;
    public double waypointY;
    public double waypointZ;

    public EntityBird(World world) {
        super(world);
        this.setSize(1.0F, 1.0F);
    }

    protected void updateEntityActionState() {
        double d0 = this.waypointX - this.posX;
        double d1 = this.waypointY - this.posY;
        double d2 = this.waypointZ - this.posZ;
        double d3 = d0 * d0 + d1 * d1 + d2 * d2;
        if (d3 < 1.0 || d3 > 3600.0) {
            this.waypointX = this.posX + (this.rand.nextFloat() * 8.0F - 4.0F) * 6.0F;
            this.waypointY = this.posY + (this.rand.nextFloat() * 2.0F - 1.0F) * 6.0F;
            this.waypointZ = this.posZ + (this.rand.nextFloat() * 8.0F - 4.0F) * 6.0F;
        }

        if (this.courseChangeCooldown-- <= 0) {
            this.courseChangeCooldown = this.courseChangeCooldown + this.rand.nextInt(2) + 2;
            d3 = MathHelper.sqrt_double(d3);
            if (this.isCourseTraversable(this.waypointX, this.waypointY, this.waypointZ, d3)) {
                this.motionX += d0 / d3 * 0.1;
                this.motionY += d1 / d3 * 0.1;
                this.motionZ += d2 / d3 * 0.1;
            } else {
                this.waypointX = this.posX;
                this.waypointY = this.posY;
                this.waypointZ = this.posZ;
            }
        }

        this.renderYawOffset = this.rotationYaw = -((float) Math.atan2(this.motionX, this.motionZ)) * 180.0F
            / (float) Math.PI;
    }

    private boolean isCourseTraversable(double par1, double par3, double par5, double par7) {
        double d4 = (this.waypointX - this.posX) / par7;
        double d5 = (this.waypointY - this.posY) / par7;
        double d6 = (this.waypointZ - this.posZ) / par7;
        AxisAlignedBB axisalignedbb = this.boundingBox.copy();

        for (int i = 1; i < par7; i++) {
            axisalignedbb.offset(d4, d5, d6);
            if (!this.worldObj.getCollidingBoundingBoxes(this, axisalignedbb)
                .isEmpty()) {
                return false;
            }
        }

        return true;
    }

    protected void dropFewItems(boolean par1, int par2) {
        int var3 = this.rand.nextInt(3) + this.rand.nextInt(1 + par2);

        for (int var4 = 0; var4 < var3; var4++) {
            this.entityDropItem(new ItemStack(Items.feather, 1), 0.0F);
        }
    }

    protected String getLivingSound() {
        return "biomesoplenty:mob.bird.say";
    }

    protected String getHurtSound() {
        return "biomesoplenty:mob.bird.hurt";
    }

    protected String getDeathSound() {
        return "biomesoplenty:mob.bird.hurt";
    }
}
