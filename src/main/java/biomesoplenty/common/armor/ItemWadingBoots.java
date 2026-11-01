package biomesoplenty.common.armor;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;

import biomesoplenty.BiomesOPlenty;
import biomesoplenty.api.content.BOPCItems;

public class ItemWadingBoots extends ItemArmor {

    public int textureID = 0;

    public ItemWadingBoots(ArmorMaterial armorMaterial, int renderIndex, int armorType) {
        super(armorMaterial, renderIndex, armorType);
        this.textureID = armorType;
        this.setMaxDamage(0);
        this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
    }

    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return stack.getItem() == BOPCItems.wadingBoots ? "biomesoplenty:textures/armor/wadingboots.png" : null;
    }

    public void registerIcons(IIconRegister iconRegister) {
        if (this.textureID == 3) {
            this.itemIcon = iconRegister.registerIcon("biomesoplenty:wadingboots");
        }
    }
}
