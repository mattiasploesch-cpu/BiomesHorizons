package biomesoplenty.common.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import biomesoplenty.api.content.BOPCItems;
import biomesoplenty.api.content.BOPCPotions;

public class EntityPhantom extends EntityMob {

    public EntityPhantom(World world) {
        super(world);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
            .setBaseValue(20.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
            .setBaseValue(0.45);
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.onGround && this.motionY < 0.0) {
            this.motionY *= 0.6;
        }

        this.fallDistance = 0.0F;
    }

    protected void fall(float par1) {}

    public void onEntityUpdate() {
        super.onEntityUpdate();

        for (int i = 0; i < 9; i++) {
            this.worldObj.spawnParticle(
                "mobSpell",
                this.posX + this.rand.nextDouble() * this.width,
                this.posY + this.rand.nextDouble() * this.height - this.yOffset,
                this.posZ + this.rand.nextDouble() * this.width,
                0.0,
                0.0,
                0.0);
        }
    }

    public boolean attackEntityAsMob(Entity par1Entity) {
        if (super.attackEntityAsMob(par1Entity)) {
            if (par1Entity instanceof EntityLivingBase && this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL
                && this.worldObj.difficultySetting != EnumDifficulty.EASY) {
                ((EntityLivingBase) par1Entity).addPotionEffect(new PotionEffect(BOPCPotions.possession.id, 30, 0));
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        for (int i = 0; i < 9; i++) {
            this.worldObj.spawnParticle(
                "mobSpell",
                this.posX + this.rand.nextDouble() * this.width,
                this.posY + this.rand.nextDouble() * this.height - this.yOffset,
                this.posZ + this.rand.nextDouble() * this.width,
                66.0,
                0.0,
                0.0);
        }

        return super.attackEntityFrom(par1DamageSource, par2);
    }

    protected void dropFewItems(boolean par1, int par2) {
        super.dropFewItems(par1, par2);
        if (par1 && (this.rand.nextInt(3) == 0 || this.rand.nextInt(1 + par2) > 0)) {
            this.entityDropItem(new ItemStack(BOPCItems.misc, 1, 10), 1.0F);
        }
    }

    public boolean getCanSpawnHere() {
        return this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL
            && this.worldObj.checkNoEntityCollision(this.boundingBox)
            && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox)
                .isEmpty()
            && !this.worldObj.isAnyLiquid(this.boundingBox);
    }

    protected String getLivingSound() {
        return "biomesoplenty:mob.phantom.say";
    }

    protected String getHurtSound() {
        return "biomesoplenty:mob.phantom.hurt";
    }

    protected String getDeathSound() {
        return "biomesoplenty:mob.phantom.death";
    }
}
