package biomesoplenty.common.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;

import biomesoplenty.BiomesOPlenty;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBOPBiomeEssence extends Item {

    public ItemBOPBiomeEssence() {
        this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
    }

    public boolean hasContainerItem(ItemStack itemStack) {
        return true;
    }

    public ItemStack getContainerItem(ItemStack itemStack) {
        return itemStack;
    }

    public void registerIcons(IIconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon("biomesoplenty:biomeessence");
    }

    public void addInformation(ItemStack itemStack, EntityPlayer player, List infoList, boolean advancedItemTooltips) {
        if (itemStack.hasTagCompound() && itemStack.getTagCompound()
            .hasKey("biomeID")) {
            BiomeGenBase biome = BiomeGenBase.getBiomeGenArray()[itemStack.getTagCompound()
                .getInteger("biomeID")];
            if (biome != null) {
                infoList.add(biome.biomeName);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemStack, int par2) {
        if (itemStack.hasTagCompound() && itemStack.getTagCompound()
            .hasKey("biomeID")) {
            BiomeGenBase biome = BiomeGenBase.getBiomeGenArray()[itemStack.getTagCompound()
                .getInteger("biomeID")];
            if (biome != null) {
                return biome.color;
            }
        }

        return 16777215;
    }

    public boolean hasEffect(ItemStack itemStack, int pass) {
        return true;
    }
}
