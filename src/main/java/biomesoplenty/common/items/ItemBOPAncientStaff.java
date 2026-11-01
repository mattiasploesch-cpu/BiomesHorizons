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

public class ItemBOPAncientStaff extends Item {

    private static String[] parts = new String[] { "ancientstaff", "staffhandle", "staffpole", "stafftopper",
        "ancientstaffbroken" };
    @SideOnly(Side.CLIENT)
    private IIcon[] textures;

    public ItemBOPAncientStaff() {
        this.maxStackSize = 1;
        this.setHasSubtypes(true);
        this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
    }

    public boolean isFull3D() {
        return true;
    }

    public boolean hasEffect(ItemStack itemStack, int pass) {
        return itemStack.getItemDamage() == 0;
    }

    public void registerIcons(IIconRegister iconRegister) {
        this.textures = new IIcon[parts.length];

        for (int i = 0; i < parts.length; i++) {
            this.textures[i] = iconRegister.registerIcon("biomesoplenty:" + parts[i]);
        }
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        int meta = itemStack.getItemDamage();
        if (meta < 0 || meta >= parts.length) {
            meta = 0;
        }

        return super.getUnlocalizedName() + "." + parts[meta];
    }

    public IIcon getIconFromDamage(int meta) {
        if (meta < 0 || meta >= this.textures.length) {
            meta = 0;
        }

        return this.textures[meta];
    }

    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        for (int meta = 0; meta < parts.length; meta++) {
            list.add(new ItemStack(item, 1, meta));
        }
    }
}
