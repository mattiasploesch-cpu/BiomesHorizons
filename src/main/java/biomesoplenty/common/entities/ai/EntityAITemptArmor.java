package biomesoplenty.common.entities.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EntityAITemptArmor extends EntityAIBase {

    private EntityCreature temptedEntity;
    private float speedTowardsTarget;
    private double targetX;
    private double targetY;
    private double targetZ;
    private double field_75278_f;
    private double field_75279_g;
    private EntityPlayer temptingPlayer;
    private boolean isRunning;
    private Item armorItem;
    private int armorMeta;
    private boolean scaredByPlayerMovement;
    private boolean field_75286_m;

    public EntityAITemptArmor(EntityCreature entityCreature, float speedTowardsTarget, Item armorItem, int armorMeta,
        boolean scaredByPlayerMovement) {
        this.temptedEntity = entityCreature;
        this.speedTowardsTarget = speedTowardsTarget;
        this.armorItem = armorItem;
        this.armorMeta = armorMeta;
        this.scaredByPlayerMovement = scaredByPlayerMovement;
        this.setMutexBits(3);
    }

    public boolean shouldExecute() {
        this.temptingPlayer = this.temptedEntity.worldObj.getClosestPlayerToEntity(this.temptedEntity, 10.0);
        if (this.temptingPlayer == null) {
            return false;
        }

        ItemStack itemstack = this.temptingPlayer.inventory.armorInventory[3];
        return itemstack == null ? false
            : itemstack.getItem() == this.armorItem && itemstack.getItemDamage() >= this.armorMeta;
    }

    public boolean continueExecuting() {
        if (this.scaredByPlayerMovement) {
            if (this.temptedEntity.getDistanceSqToEntity(this.temptingPlayer) < 36.0) {
                if (this.temptingPlayer.getDistanceSq(this.targetX, this.targetY, this.targetZ)
                    > 0.010000000000000002) {
                    return false;
                }

                if (Math.abs(this.temptingPlayer.rotationPitch - this.field_75278_f) > 5.0
                    || Math.abs(this.temptingPlayer.rotationYaw - this.field_75279_g) > 5.0) {
                    return false;
                }
            } else {
                this.targetX = this.temptingPlayer.posX;
                this.targetY = this.temptingPlayer.posY;
                this.targetZ = this.temptingPlayer.posZ;
            }

            this.field_75278_f = this.temptingPlayer.rotationPitch;
            this.field_75279_g = this.temptingPlayer.rotationYaw;
        }

        return this.shouldExecute();
    }

    public void startExecuting() {
        this.targetX = this.temptingPlayer.posX;
        this.targetY = this.temptingPlayer.posY;
        this.targetZ = this.temptingPlayer.posZ;
        this.isRunning = true;
        this.field_75286_m = this.temptedEntity.getNavigator()
            .getAvoidsWater();
        this.temptedEntity.getNavigator()
            .setAvoidsWater(false);
    }

    public void resetTask() {
        this.temptingPlayer = null;
        this.temptedEntity.getNavigator()
            .clearPathEntity();
        this.isRunning = false;
        this.temptedEntity.getNavigator()
            .setAvoidsWater(this.field_75286_m);
    }

    public void updateTask() {
        this.temptedEntity.getLookHelper()
            .setLookPositionWithEntity(this.temptingPlayer, 30.0F, this.temptedEntity.getVerticalFaceSpeed());
        if (this.temptedEntity.getDistanceSqToEntity(this.temptingPlayer) < 6.25) {
            this.temptedEntity.getNavigator()
                .clearPathEntity();
        } else {
            this.temptedEntity.getNavigator()
                .tryMoveToEntityLiving(this.temptingPlayer, this.speedTowardsTarget);
        }
    }

    public boolean isRunning() {
        return this.isRunning;
    }
}
