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

public class ItemGems extends Item {

    private static String[] gems = new String[] { "amethyst", "ruby", "peridot", "topaz", "tanzanite", "malachite",
        "sapphire", "amber" };
    @SideOnly(Side.CLIENT)
    private IIcon[] textures;

    public ItemGems() {
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
    }

    public void registerIcons(IIconRegister iconRegister) {
        this.textures = new IIcon[gems.length];

        for (int i = 0; i < gems.length; i++) {
            this.textures[i] = iconRegister.registerIcon("biomesoplenty:" + gems[i]);
        }
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        int meta = itemStack.getItemDamage();
        if (meta < 0 || meta >= gems.length) {
            meta = 0;
        }

        return super.getUnlocalizedName() + "." + gems[meta];
    }

    public IIcon getIconFromDamage(int meta) {
        if (meta < 0 || meta >= this.textures.length) {
            meta = 0;
        }

        return this.textures[meta];
    }

    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        for (int meta = 0; meta < gems.length; meta++) {
            list.add(new ItemStack(item, 1, meta));
        }
    }
}
