package biomesoplenty.common.entities;

import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import biomesoplenty.BiomesOPlenty;
import biomesoplenty.api.content.BOPCItems;

public class EntityPixie extends EntityFlyingCreature {

    public int courseChangeCooldown;
    public double waypointX;
    public double waypointY;
    public double waypointZ;

    public EntityPixie(World world) {
        super(world);
        this.setSize(1.0F, 1.0F);
    }

    protected void updateEntityActionState() {
        double d0 = this.waypointX - this.posX;
        double d1 = this.waypointY - this.posY;
        double d2 = this.waypointZ - this.posZ;
        double d3 = d0 * d0 + d1 * d1 + d2 * d2;
        if (d3 < 1.0 || d3 > 3600.0) {
            this.waypointX = this.posX + (this.rand.nextFloat() * 4.0F - 2.0F) * 2.0F;
            this.waypointY = this.posY + (this.rand.nextFloat() * 4.0F - 2.0F) * 2.0F;
            this.waypointZ = this.posZ + (this.rand.nextFloat() * 4.0F - 2.0F) * 2.0F;
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
            this.entityDropItem(new ItemStack(BOPCItems.misc, 1, 11), 0.0F);
        }
    }

    public void onEntityUpdate() {
        super.onEntityUpdate();
        if (this.worldObj.isRemote) {
            for (int i = 0; i < 7; i++) {
                if (this.rand.nextInt(2) == 0) {
                    BiomesOPlenty.proxy.spawnParticle(
                        "pixietrail",
                        this.posX + this.rand.nextDouble() * this.width,
                        this.posY + this.rand.nextDouble() * this.height - this.yOffset,
                        this.posZ + this.rand.nextDouble() * this.width);
                }
            }
        }
    }

    protected boolean isValidLightLevel() {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.boundingBox.minY);
        int k = MathHelper.floor_double(this.posZ);
        if (this.worldObj.getSavedLightValue(EnumSkyBlock.Sky, i, j, k) > this.rand.nextInt(32)) {
            return false;
        }

        int l = this.worldObj.getBlockLightValue(i, j, k);
        if (this.worldObj.isThundering()) {
            int i1 = this.worldObj.skylightSubtracted;
            this.worldObj.skylightSubtracted = 10;
            l = this.worldObj.getBlockLightValue(i, j, k);
            this.worldObj.skylightSubtracted = i1;
        }

        return l <= this.rand.nextInt(8);
    }

    public boolean getCanSpawnHere() {
        return this.isValidLightLevel() && super.getCanSpawnHere();
    }

    protected String getLivingSound() {
        return "biomesoplenty:mob.pixie.say";
    }

    protected String getHurtSound() {
        return "biomesoplenty:mob.pixie.hurt";
    }

    protected String getDeathSound() {
        return "biomesoplenty:mob.pixie.hurt";
    }
}
