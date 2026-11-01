package biomesoplenty.common.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;

import biomesoplenty.BiomesOPlenty;
import biomesoplenty.api.content.BOPCItems;

public class ItemBOPPickaxe extends ItemPickaxe {

    public int textureID = 0;

    public ItemBOPPickaxe(ToolMaterial toolMaterial, int texture) {
        super(toolMaterial);
        this.textureID = texture;
        this.setHarvestLevel("pickaxe", toolMaterial.getHarvestLevel());
        this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
    }

    public boolean getIsRepairable(ItemStack itemToRepair, ItemStack itemToRepairWith) {
        return this.textureID == 1 && itemToRepairWith.getItem() == BOPCItems.misc
            && itemToRepairWith.getItemDamage() == 2;
    }

    public void registerIcons(IIconRegister iconRegister) {
        if (this.textureID == 0) {
            this.itemIcon = iconRegister.registerIcon("biomesoplenty:mudpickaxe");
        } else if (this.textureID == 1) {
            this.itemIcon = iconRegister.registerIcon("biomesoplenty:amethystpickaxe");
        } else {
            this.itemIcon = iconRegister.registerIcon("biomesoplenty:mudball");
        }
    }
}
