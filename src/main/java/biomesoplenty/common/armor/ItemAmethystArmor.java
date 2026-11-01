package biomesoplenty.common.armor;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;

import biomesoplenty.BiomesOPlenty;
import biomesoplenty.api.content.BOPCItems;

public class ItemAmethystArmor extends ItemArmor {

    public int textureID = 0;

    public ItemAmethystArmor(ArmorMaterial armorMaterial, int renderIndex, int armorType) {
        super(armorMaterial, renderIndex, armorType);
        this.textureID = armorType;
        this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
    }

    public boolean getIsRepairable(ItemStack itemToRepair, ItemStack itemToRepairWith) {
        return itemToRepairWith.getItem() == BOPCItems.misc && itemToRepairWith.getItemDamage() == 2;
    }

    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        if (stack.getItem() == BOPCItems.helmetAmethyst || stack.getItem() == BOPCItems.chestplateAmethyst
            || stack.getItem() == BOPCItems.bootsAmethyst) {
            return "biomesoplenty:textures/armor/amethyst_1.png";
        } else {
            return stack.getItem() == BOPCItems.leggingsAmethyst ? "biomesoplenty:textures/armor/amethyst_2.png" : null;
        }
    }

    public void registerIcons(IIconRegister iconRegister) {
        if (this.textureID == 0) {
            this.itemIcon = iconRegister.registerIcon("biomesoplenty:amethysthelmet");
        } else if (this.textureID == 1) {
            this.itemIcon = iconRegister.registerIcon("biomesoplenty:amethystchestplate");
        } else if (this.textureID == 2) {
            this.itemIcon = iconRegister.registerIcon("biomesoplenty:amethystleggings");
        } else if (this.textureID == 3) {
            this.itemIcon = iconRegister.registerIcon("biomesoplenty:amethystboots");
        } else {
            this.itemIcon = iconRegister.registerIcon("biomesoplenty:mudball");
        }
    }
}
