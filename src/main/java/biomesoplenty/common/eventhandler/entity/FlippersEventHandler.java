package biomesoplenty.common.eventhandler.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import biomesoplenty.api.content.BOPCItems;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class FlippersEventHandler {

    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent event) {
        if (event.entity instanceof EntityLiving) {
            EntityLiving entity = (EntityLiving) event.entity;
            ItemStack itemstack = entity.getEquipmentInSlot(1);
            if (entity.isInWater() && itemstack != null && itemstack.getItem() == BOPCItems.flippers) {
                entity.motionX *= 1.125;
                entity.motionY *= 1.1;
                entity.motionZ *= 1.125;
            }
        }

        if (event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;
            InventoryPlayer inventory = player.inventory;
            if (player.isInWater() && !player.capabilities.isFlying
                && inventory.armorInventory[0] != null
                && inventory.armorInventory[0].getItem() == BOPCItems.flippers) {
                player.motionX *= 1.125;
                player.motionY *= 1.1;
                player.motionZ *= 1.125;
            }
        }
    }
}
