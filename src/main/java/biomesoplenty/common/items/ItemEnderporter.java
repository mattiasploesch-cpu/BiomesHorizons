package biomesoplenty.common.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import biomesoplenty.BiomesOPlenty;

public class ItemEnderporter extends Item {

    public ItemEnderporter() {
        this.maxStackSize = 1;
        this.setMaxDamage(9);
        this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
    }

    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("biomesoplenty:enderporter");
    }

    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (player.ridingEntity != null) {
            return itemStack;
        }

        if (player.worldObj.provider.isSurfaceWorld()) {
            itemStack.damageItem(1, player);
            player.addPotionEffect(new PotionEffect(Potion.blindness.id, 100, 999));
            player.addPotionEffect(new PotionEffect(Potion.resistance.id, 200, 999));
            player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 100, 999));
            player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 200, 3));
            player.setPosition(world.getSpawnPoint().posX, 256.0, world.getSpawnPoint().posZ);
            world.playSoundAtEntity(player, "random.levelup", 1.0F, 5.0F);
        } else if (!player.worldObj.isRemote) {
            ChatComponentText chatComponent = new ChatComponentText(
                "§5" + StatCollector.translateToLocal("enderporter.prevent"));
            chatComponent.getChatStyle()
                .setColor(EnumChatFormatting.DARK_PURPLE);
            player.addChatMessage(chatComponent);
        }

        return itemStack;
    }
}
