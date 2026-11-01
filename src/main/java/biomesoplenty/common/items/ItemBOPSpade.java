package biomesoplenty.common.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;

import biomesoplenty.BiomesOPlenty;
import biomesoplenty.api.content.BOPCItems;

public class ItemBOPSpade extends ItemSpade {

    public int textureID = 0;

    public ItemBOPSpade(ToolMaterial toolMaterial, int texture) {
        super(toolMaterial);
        this.textureID = texture;
        this.setHarvestLevel("shovel", 4);
        this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
    }

    public boolean getIsRepairable(ItemStack itemToRepair, ItemStack itemToRepairWith) {
        return this.textureID == 1 && itemToRepairWith.getItem() == BOPCItems.misc
            && itemToRepairWith.getItemDamage() == 2;
    }

    public void registerIcons(IIconRegister iconRegister) {
        if (this.textureID == 0) {
            this.itemIcon = iconRegister.registerIcon("biomesoplenty:mudshovel");
        } else if (this.textureID == 1) {
            this.itemIcon = iconRegister.registerIcon("biomesoplenty:amethystshovel");
        } else {
            this.itemIcon = iconRegister.registerIcon("biomesoplenty:mudball");
        }
    }
}
