package biomesoplenty.common.armor;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;

import biomesoplenty.BiomesOPlenty;
import biomesoplenty.api.content.BOPCItems;

public class ItemMuddyArmor extends ItemArmor {

    public int textureID = 0;

    public ItemMuddyArmor(ArmorMaterial armorMaterial, int renderIndex, int armorType) {
        super(armorMaterial, renderIndex, armorType);
        this.textureID = armorType;
        this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
    }

    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        if (stack.getItem() == BOPCItems.helmetMud || stack.getItem() == BOPCItems.chestplateMud
            || stack.getItem() == BOPCItems.bootsMud) {
            return "biomesoplenty:textures/armor/mud_1.png";
        } else {
            return stack.getItem() == BOPCItems.leggingsMud ? "biomesoplenty:textures/armor/mud_2.png" : null;
        }
    }

    public void registerIcons(IIconRegister iconRegister) {
        if (this.textureID == 0) {
            this.itemIcon = iconRegister.registerIcon("biomesoplenty:mudhelmet");
        } else if (this.textureID == 1) {
            this.itemIcon = iconRegister.registerIcon("biomesoplenty:mudchestplate");
        } else if (this.textureID == 2) {
            this.itemIcon = iconRegister.registerIcon("biomesoplenty:mudleggings");
        } else if (this.textureID == 3) {
            this.itemIcon = iconRegister.registerIcon("biomesoplenty:mudboots");
        } else {
            this.itemIcon = iconRegister.registerIcon("biomesoplenty:mudball");
        }
    }
}
