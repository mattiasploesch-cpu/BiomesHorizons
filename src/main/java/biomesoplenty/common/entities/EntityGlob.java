package biomesoplenty.common.entities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import biomesoplenty.BiomesOPlenty;
import biomesoplenty.api.content.BOPCItems;

public class EntityGlob extends EntityLiving implements IMob {

    private static final float[] spawnChances = new float[] { 1.0F, 0.75F, 0.5F, 0.25F, 0.0F, 0.25F, 0.5F, 0.75F };
    public float squishAmount;
    public float squishFactor;
    public float prevSquishFactor;
    private int globJumpDelay = 0;

    public EntityGlob(World par1World) {
        super(par1World);
        int i = 1 << this.rand.nextInt(3);
        this.yOffset = 0.0F;
        this.globJumpDelay = this.rand.nextInt(20) + 10;
        this.setGlobSize(i);
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte) 1));
    }

    protected void setGlobSize(int par1) {
        this.dataWatcher.updateObject(16, new Byte((byte) par1));
        this.setSize(0.6F * par1, 0.6F * par1);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
            .setBaseValue(par1 * par1);
        this.setHealth(this.getMaxHealth());
        this.experienceValue = par1;
    }

    public int getGlobSize() {
        return this.dataWatcher.getWatchableObjectByte(16);
    }

    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("Size", this.getGlobSize() - 1);
    }

    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setGlobSize(par1NBTTagCompound.getInteger("Size") + 1);
    }

    protected String getJumpSound() {
        return "mob.slime." + (this.getGlobSize() > 1 ? "big" : "small");
    }

    public void onUpdate() {
        if (!this.worldObj.isRemote && this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL
            && this.getGlobSize() > 0) {
            this.isDead = true;
        }

        this.squishFactor = this.squishFactor + (this.squishAmount - this.squishFactor) * 0.5F;
        this.prevSquishFactor = this.squishFactor;
        boolean flag = this.onGround;
        super.onUpdate();
        if (this.onGround && !flag) {
            int i = this.getGlobSize();

            for (int j = 0; j < i * 8; j++) {
                float f = this.rand.nextFloat() * (float) Math.PI * 2.0F;
                float f1 = this.rand.nextFloat() * 0.5F + 0.5F;
                float f2 = MathHelper.sin(f) * i * 0.5F * f1;
                float f3 = MathHelper.cos(f) * i * 0.5F * f1;
                BiomesOPlenty.proxy.spawnParticle("mud", this.posX + f2, this.boundingBox.minY, this.posZ + f3);
            }

            if (this.makesSoundOnLand()) {
                this.playSound(
                    this.getJumpSound(),
                    this.getSoundVolume(),
                    ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
            }

            this.squishAmount = -0.5F;
        } else if (!this.onGround && flag) {
            this.squishAmount = 1.0F;
        }

        this.alterSquishAmount();
        if (this.worldObj.isRemote) {
            int i = this.getGlobSize();
            this.setSize(0.6F * i, 0.6F * i);
        }
    }

    protected void updateEntityActionState() {
        this.despawnEntity();
        EntityPlayer entityplayer = this.worldObj.getClosestVulnerablePlayerToEntity(this, 16.0);
        if (entityplayer != null) {
            this.faceEntity(entityplayer, 10.0F, 20.0F);
        }

        if (this.onGround && this.globJumpDelay-- <= 0) {
            this.globJumpDelay = this.getJumpDelay();
            if (entityplayer != null) {
                this.globJumpDelay /= 3;
            }

            this.isJumping = true;
            if (this.makesSoundOnJump()) {
                this.playSound(
                    this.getJumpSound(),
                    this.getSoundVolume(),
                    ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
            }

            this.moveStrafing = 1.0F - this.rand.nextFloat() * 2.0F;
            this.moveForward = 1 * this.getGlobSize();
        } else {
            this.isJumping = false;
            if (this.onGround) {
                this.moveStrafing = this.moveForward = 0.0F;
            }
        }
    }

    protected void alterSquishAmount() {
        this.squishAmount *= 0.6F;
    }

    protected int getJumpDelay() {
        return this.rand.nextInt(20) + 10;
    }

    protected EntityGlob createInstance() {
        return new EntityGlob(this.worldObj);
    }

    public void setDead() {
        int i = this.getGlobSize();
        if (!this.worldObj.isRemote && i > 1 && this.getHealth() <= 0.0F) {
            int j = 2 + this.rand.nextInt(3);

            for (int k = 0; k < j; k++) {
                float f = (k % 2 - 0.5F) * i / 4.0F;
                float f1 = (k / 2 - 0.5F) * i / 4.0F;
                EntityGlob entityGlob = this.createInstance();
                entityGlob.setGlobSize(i / 2);
                entityGlob.setLocationAndAngles(
                    this.posX + f,
                    this.posY + 0.5,
                    this.posZ + f1,
                    this.rand.nextFloat() * 360.0F,
                    0.0F);
                this.worldObj.spawnEntityInWorld(entityGlob);
            }
        }

        super.setDead();
    }

    public void onCollideWithPlayer(EntityPlayer par1EntityPlayer) {
        if (this.canDamagePlayer()) {
            int i = this.getGlobSize();
            if (this.canEntityBeSeen(par1EntityPlayer)
                && this.getDistanceSqToEntity(par1EntityPlayer) < 0.6 * i * 0.6 * i
                && par1EntityPlayer.attackEntityFrom(DamageSource.causeMobDamage(this), this.getAttackStrength())) {
                this.playSound("mob.attack", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            }
        }
    }

    protected boolean canDamagePlayer() {
        return this.getGlobSize() > 1;
    }

    protected int getAttackStrength() {
        return this.getGlobSize();
    }

    protected String getHurtSound() {
        return "mob.slime." + (this.getGlobSize() > 1 ? "big" : "small");
    }

    protected String getDeathSound() {
        return "mob.slime." + (this.getGlobSize() > 1 ? "big" : "small");
    }

    protected void dropFewItems(boolean par1, int par2) {
        int var3 = this.rand.nextInt(3) + this.rand.nextInt(1 + par2);
        if (this.rand.nextInt(1000) == 0) {
            this.entityDropItem(new ItemStack(BOPCItems.record_corruption), 0.0F);
        }

        for (int var4 = 0; var4 < var3; var4++) {
            this.entityDropItem(new ItemStack(BOPCItems.mudball, 1, 1), 0.0F);
        }

        this.dropItem(Items.slime_ball, 1);
    }

    public boolean getCanSpawnHere() {
        Chunk chunk = this.worldObj
            .getChunkFromBlockCoords(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ));
        if (this.worldObj.getWorldInfo()
            .getTerrainType()
            .handleSlimeSpawnReduction(this.rand, this.worldObj)) {
            return false;
        }

        if (this.getGlobSize() == 1 || this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL) {
            BiomeGenBase biomegenbase = this.worldObj
                .getBiomeGenForCoords(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ));
            if (BiomeDictionary.isBiomeOfType(biomegenbase, Type.SWAMP) && this.posY > 50.0
                && this.posY < 70.0
                && this.rand.nextFloat() < 0.5F
                && this.rand.nextFloat() < this.worldObj.getCurrentMoonPhaseFactor()
                && this.worldObj.getBlockLightValue(
                    MathHelper.floor_double(this.posX),
                    MathHelper.floor_double(this.posY),
                    MathHelper.floor_double(this.posZ)) <= this.rand.nextInt(8)) {
                return super.getCanSpawnHere();
            }

            if (this.rand.nextInt(10) == 0 && chunk.getRandomWithSeed(987234911L)
                .nextInt(10) == 0 && this.posY < 40.0) {
                return super.getCanSpawnHere();
            }
        }

        return false;
    }

    protected float getSoundVolume() {
        return 0.4F * this.getGlobSize();
    }

    public int getVerticalFaceSpeed() {
        return 0;
    }

    protected boolean makesSoundOnJump() {
        return this.getGlobSize() > 0;
    }

    protected boolean makesSoundOnLand() {
        return this.getGlobSize() > 2;
    }
}
