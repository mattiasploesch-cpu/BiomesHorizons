package biomesoplenty.common.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;

import biomesoplenty.BiomesOPlenty;
import biomesoplenty.api.content.BOPCItems;

public class ItemBOPHoe extends ItemHoe {

    public int textureID = 0;

    public ItemBOPHoe(ToolMaterial par2, int texture) {
        super(par2);
        this.textureID = texture;
        this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
    }

    public boolean getIsRepairable(ItemStack itemToRepair, ItemStack itemToRepairWith) {
        return this.textureID == 1 && itemToRepairWith.getItem() == BOPCItems.misc
            && itemToRepairWith.getItemDamage() == 2;
    }

    public void registerIcons(IIconRegister iconRegister) {
        if (this.textureID == 0) {
            this.itemIcon = iconRegister.registerIcon("biomesoplenty:mudhoe");
        } else if (this.textureID == 1) {
            this.itemIcon = iconRegister.registerIcon("biomesoplenty:amethysthoe");
        } else {
            this.itemIcon = iconRegister.registerIcon("biomesoplenty:mudball");
        }
    }
}
