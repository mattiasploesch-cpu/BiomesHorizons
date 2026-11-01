package biomesoplenty.common.itemblocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import biomesoplenty.BiomesOPlenty;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockFlower extends ItemBlock {

    private static final String[] plants = new String[] { "clover", "swampflower", "deadbloom", "glowflower",
        "hydrangea", "cosmos", "daffodil", "wildflower", "violet", "anemone", "lilyflower", "enderlotus", "bromeliad",
        "eyebulbbottom", "eyebulbtop", "dandelion" };
    @SideOnly(Side.CLIENT)
    private IIcon[] textures;
    private static final int EYEBULBTOP = 14;

    public ItemBlockFlower(Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    public int getMetadata(int meta) {
        return meta & 15;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.textures = new IIcon[1];
        this.textures[0] = iconRegister.registerIcon("biomesoplenty:eyebulbtop");
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        int meta = itemStack.getItemDamage();
        if (meta < 0 || meta >= plants.length) {
            meta = 0;
        }

        return super.getUnlocalizedName() + "." + plants[meta];
    }

    public IIcon getIconFromDamage(int meta) {
        return meta == 13 ? this.textures[0] : this.field_150939_a.getIcon(0, meta);
    }

    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }

    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return par1ItemStack.getItemDamage() == 15 ? EnumAction.block : super.getItemUseAction(par1ItemStack);
    }

    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 20;
    }

    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        if (par1ItemStack.getItemDamage() == 15) {
            par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        }

        return par1ItemStack;
    }

    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        Vec3 vec = player.getLook(0.5F);
        Random rnd = player.getRNG();

        for (int p = 0; p < 4; p++) {
            float pos = (rnd.nextFloat() - 0.5F) / 8.0F;
            BiomesOPlenty.proxy.spawnParticle(
                "dandelion",
                player.posX + vec.xCoord + pos,
                player.posY + vec.yCoord + player.getEyeHeight() + pos,
                player.posZ + vec.zCoord + pos);
        }

        if (count < 10 && !player.capabilities.isCreativeMode) {
            player.stopUsingItem();
            stack.stackSize--;
        }
    }

    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ, int metadata) {
        return metadata == 13
            && !this.placeBlockAt(stack, player, world, x, y + 1, z, side, hitX, hitY + 1.0F, hitZ, 14) ? false
                : super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
    }
}
