package biomesoplenty.common.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import biomesoplenty.BiomesOPlenty;
import biomesoplenty.api.content.BOPCItems;
import biomesoplenty.common.entities.projectiles.EntityDart;

public class ItemDartBlower extends Item {

    public ItemDartBlower() {
        this.maxStackSize = 1;
        this.setMaxDamage(63);
        this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
    }

    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("biomesoplenty:dartblower");
    }

    public boolean isFull3D() {
        return true;
    }

    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer par3EntityPlayer) {
        boolean flag = par3EntityPlayer.capabilities.isCreativeMode;
        if (flag || par3EntityPlayer.inventory.hasItem(BOPCItems.dart)) {
            EntityDart entityDart = new EntityDart(world, par3EntityPlayer, 1.0F);
            itemStack.damageItem(1, par3EntityPlayer);
            world.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F, 1.75F);
            int slot = -1;
            if (par3EntityPlayer.inventory.hasItemStack(new ItemStack(BOPCItems.dart, 1, 1))) {
                entityDart.setDartType(EntityDart.DartType.POISON);

                for (int k = 0; k < par3EntityPlayer.inventory.mainInventory.length; k++) {
                    if (par3EntityPlayer.inventory.mainInventory[k] != null
                        && par3EntityPlayer.inventory.mainInventory[k].getItem() == BOPCItems.dart
                        && par3EntityPlayer.inventory.mainInventory[k].getItemDamage() == 1) {
                        slot = k;
                        break;
                    }
                }
            } else if (par3EntityPlayer.inventory.hasItemStack(new ItemStack(BOPCItems.dart, 1, 0))) {
                entityDart.setDartType(EntityDart.DartType.NORMAL);

                for (int k = 0; k < par3EntityPlayer.inventory.mainInventory.length; k++) {
                    if (par3EntityPlayer.inventory.mainInventory[k] != null
                        && par3EntityPlayer.inventory.mainInventory[k].getItem() == BOPCItems.dart
                        && par3EntityPlayer.inventory.mainInventory[k].getItemDamage() == 0) {
                        slot = k;
                        break;
                    }
                }
            }

            if (!world.isRemote) {
                world.spawnEntityInWorld(entityDart);
            }

            if (!flag && slot >= 0) {
                par3EntityPlayer.inventory.decrStackSize(slot, 1);
            }
        }

        return itemStack;
    }
}
