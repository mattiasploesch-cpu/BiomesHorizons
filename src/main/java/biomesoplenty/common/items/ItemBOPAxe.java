package biomesoplenty.common.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

import biomesoplenty.BiomesOPlenty;
import biomesoplenty.api.content.BOPCItems;

public class ItemBOPAxe extends ItemAxe {

    public int textureID = 0;

    public ItemBOPAxe(ToolMaterial toolMaterial, int texture) {
        super(toolMaterial);
        this.textureID = texture;
        this.setHarvestLevel("axe", 4);
        this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
    }

    public boolean getIsRepairable(ItemStack itemToRepair, ItemStack itemToRepairWith) {
        return this.textureID == 1 && itemToRepairWith.getItem() == BOPCItems.misc
            && itemToRepairWith.getItemDamage() == 2;
    }

    public void registerIcons(IIconRegister iconRegister) {
        if (this.textureID == 0) {
            this.itemIcon = iconRegister.registerIcon("biomesoplenty:mudaxe");
        } else if (this.textureID == 1) {
            this.itemIcon = iconRegister.registerIcon("biomesoplenty:amethystaxe");
        } else {
            this.itemIcon = iconRegister.registerIcon("biomesoplenty:mudball");
        }
    }
}
