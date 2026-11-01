package biomesoplenty.common.entities.projectiles;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import biomesoplenty.BiomesOPlenty;
import biomesoplenty.ClientProxy;
import biomesoplenty.api.content.BOPCPotions;

public class EntityDart extends EntityArrow {

    boolean isClient = BiomesOPlenty.proxy instanceof ClientProxy;
    private int xTile = -1;
    private int yTile = -1;
    private int zTile = -1;
    private int ticksInAir = 0;
    private int damage = 2;
    private EntityDart.DartType type = EntityDart.DartType.NORMAL;

    public EntityDart(World world) {
        super(world);
    }

    public EntityDart(World world, EntityLivingBase shootingEntity, float par3) {
        super(world, shootingEntity, par3);
    }

    public EntityDart(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public void onUpdate() {
        super.onEntityUpdate();
        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0
                / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(this.motionY, f) * 180.0 / Math.PI);
        }

        Block block = this.worldObj.getBlock(this.xTile, this.yTile, this.zTile);
        if (block.getMaterial() != Material.air) {
            block.setBlockBoundsBasedOnState(this.worldObj, this.xTile, this.yTile, this.zTile);
            AxisAlignedBB axisalignedbb = block
                .getCollisionBoundingBoxFromPool(this.worldObj, this.xTile, this.yTile, this.zTile);
            if (axisalignedbb != null
                && axisalignedbb.isVecInside(Vec3.createVectorHelper(this.posX, this.posY, this.posZ))) {
                this.setDead();
            }
        }

        this.ticksInAir++;
        Vec3 vec3 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
        Vec3 vec31 = Vec3
            .createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        MovingObjectPosition movingobjectposition = this.worldObj.func_147447_a(vec3, vec31, false, true, false);
        vec3 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
        vec31 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        if (movingobjectposition != null) {
            vec31 = Vec3.createVectorHelper(
                movingobjectposition.hitVec.xCoord,
                movingobjectposition.hitVec.yCoord,
                movingobjectposition.hitVec.zCoord);
        }

        Entity entity = null;
        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(
            this,
            this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ)
                .expand(1.0, 1.0, 1.0));
        double d0 = 0.0;

        for (int l = 0; l < list.size(); l++) {
            Entity entity1 = (Entity) list.get(l);
            if (entity1.canBeCollidedWith() && (entity1 != this.shootingEntity || this.ticksInAir >= 5)) {
                float f1 = 0.3F;
                AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand(f1, f1, f1);
                MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec3, vec31);
                if (movingobjectposition1 != null) {
                    double d1 = vec3.distanceTo(movingobjectposition1.hitVec);
                    if (d1 < d0 || d0 == 0.0) {
                        entity = entity1;
                        d0 = d1;
                    }
                }
            }
        }

        if (entity != null) {
            movingobjectposition = new MovingObjectPosition(entity);
        }

        if (movingobjectposition != null && movingobjectposition.entityHit != null
            && movingobjectposition.entityHit instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer) movingobjectposition.entityHit;
            if (entityplayer.capabilities.disableDamage || this.shootingEntity instanceof EntityPlayer
                && !((EntityPlayer) this.shootingEntity).canAttackPlayer(entityplayer)) {
                movingobjectposition = null;
            }
        }

        if (movingobjectposition != null) {
            if (movingobjectposition.entityHit != null) {
                float f2 = MathHelper.sqrt_double(
                    this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                DamageSource damagesource = null;
                if (this.shootingEntity == null) {
                    damagesource = DamageSource.causeArrowDamage(this, this);
                } else {
                    damagesource = DamageSource.causeArrowDamage(this, this.shootingEntity);
                }

                if (this.type == EntityDart.DartType.POISON) {
                    this.damage = 1;
                    if (movingobjectposition.entityHit instanceof EntityLivingBase) {
                        ((EntityLivingBase) movingobjectposition.entityHit)
                            .addPotionEffect(new PotionEffect(BOPCPotions.paralysis.id, 100));
                    }
                }

                if (movingobjectposition.entityHit.attackEntityFrom(damagesource, this.damage)) {
                    if (movingobjectposition.entityHit instanceof EntityLivingBase && this.shootingEntity != null
                        && movingobjectposition.entityHit != this.shootingEntity
                        && movingobjectposition.entityHit instanceof EntityPlayer
                        && this.shootingEntity instanceof EntityPlayerMP) {
                        ((EntityPlayerMP) this.shootingEntity).playerNetServerHandler
                            .sendPacket(new S2BPacketChangeGameState(6, 0.0F));
                    }

                    this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                    this.setDead();
                } else {
                    this.motionX *= -0.1F;
                    this.motionY *= -0.1F;
                    this.motionZ *= -0.1F;
                    this.rotationYaw += 180.0F;
                    this.prevRotationYaw += 180.0F;
                    this.ticksInAir = 0;
                }
            } else {
                this.xTile = movingobjectposition.blockX;
                this.yTile = movingobjectposition.blockY;
                this.zTile = movingobjectposition.blockZ;
                this.motionX = (float) (movingobjectposition.hitVec.xCoord - this.posX);
                this.motionY = (float) (movingobjectposition.hitVec.yCoord - this.posY);
                this.motionZ = (float) (movingobjectposition.hitVec.zCoord - this.posZ);
                float f2 = MathHelper.sqrt_double(
                    this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                this.posX = this.posX - this.motionX / f2 * 0.05F;
                this.posY = this.posY - this.motionY / f2 * 0.05F;
                this.posZ = this.posZ - this.motionZ / f2 * 0.05F;

                for (int p = 0; p < 16; p++) {
                    if (this.isPoisonous()) {
                        BiomesOPlenty.proxy.spawnParticle("poisondart", this.posX, this.posY, this.posZ);
                    } else {
                        BiomesOPlenty.proxy.spawnParticle("dart", this.posX, this.posY, this.posZ);
                    }
                }

                this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                this.setDead();
            }
        }

        this.posX = this.posX + this.motionX;
        this.posY = this.posY + this.motionY;
        this.posZ = this.posZ + this.motionZ;
        float f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0 / Math.PI);
        this.rotationPitch = (float) (Math.atan2(this.motionY, f2) * 180.0 / Math.PI);

        while (this.rotationPitch - this.prevRotationPitch < -180.0F) {
            this.prevRotationPitch -= 360.0F;
        }

        while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
            this.prevRotationPitch += 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
            this.prevRotationYaw -= 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
            this.prevRotationYaw += 360.0F;
        }

        this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
        float f4 = 0.99F;
        float f1 = 0.05F;
        if (this.isInWater()) {
            for (int j1 = 0; j1 < 4; j1++) {
                float f3 = 0.25F;
                this.worldObj.spawnParticle(
                    "bubble",
                    this.posX - this.motionX * f3,
                    this.posY - this.motionY * f3,
                    this.posZ - this.motionZ * f3,
                    this.motionX,
                    this.motionY,
                    this.motionZ);
            }

            f4 = 0.8F;
        }

        this.motionX *= f4;
        this.motionY *= f4;
        this.motionZ *= f4;
        this.motionY -= f1;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.func_145775_I();
    }

    public void setDartType(EntityDart.DartType dartType) {
        this.type = dartType;
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);
        if (dartType == EntityDart.DartType.POISON) {
            this.dataWatcher.updateObject(16, (byte) (b0 | 1));
        } else {
            this.dataWatcher.updateObject(16, (byte) (b0 & -2));
        }
    }

    public boolean isPoisonous() {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);
        return (b0 & 1) != 0;
    }

    public enum DartType {
        NORMAL,
        POISON;
    }
}
