package biomesoplenty.common.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import biomesoplenty.BiomesOPlenty;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBOPMisc extends Item {

    private static String[] items = new String[] { "mudbrick", "ash", "emptyhoneycomb", "fleshchunk", "crystalshard",
        "bluedye", "browndye", "greendye", "whitedye", "blackdye", "ghastlysoul", "pixiedust", "ichor", "pinecone" };
    @SideOnly(Side.CLIENT)
    private IIcon[] textures;

    public ItemBOPMisc() {
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
    }

    public void registerIcons(IIconRegister iconRegister) {
        this.textures = new IIcon[items.length];

        for (int i = 0; i < items.length; i++) {
            this.textures[i] = iconRegister.registerIcon("biomesoplenty:" + items[i]);
        }
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        int meta = itemStack.getItemDamage();
        if (meta < 0 || meta >= items.length) {
            meta = 0;
        }

        return super.getUnlocalizedName() + "." + items[meta];
    }

    public IIcon getIconFromDamage(int meta) {
        if (meta < 0 || meta >= this.textures.length) {
            meta = 0;
        }

        return this.textures[meta];
    }

    public int getItemStackLimit(ItemStack itemStack) {
        return itemStack.getItemDamage() == 10 ? 1 : 64;
    }

    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        for (int meta = 0; meta < items.length; meta++) {
            list.add(new ItemStack(item, 1, meta));
        }
    }
}
