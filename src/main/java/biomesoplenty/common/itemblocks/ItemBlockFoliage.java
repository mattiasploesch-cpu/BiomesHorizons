package biomesoplenty.common.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

import biomesoplenty.api.content.BOPCBlocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockFoliage extends ItemColored {

    private static final String[] foliageTypes = new String[] { "duckweed", "shortgrass", "mediumgrass", "flaxbottom",
        "bush", "sprout", "flaxtop", "poisonivy", "berrybush", "shrub", "wheatgrass", "dampgrass", "koru",
        "cloverpatch", "leafpile", "deadleafpile" };
    @SideOnly(Side.CLIENT)
    private IIcon[] textures;
    private static final int FLAXTOP = 6;

    public ItemBlockFoliage(Block block) {
        super(block, true);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.textures = new IIcon[foliageTypes.length];

        for (int i = 0; i < foliageTypes.length; i++) {
            this.textures[i] = iconRegister.registerIcon("biomesoplenty:" + foliageTypes[i]);
        }

        this.textures[3] = iconRegister.registerIcon("biomesoplenty:item_flax");
        this.textures[8] = iconRegister.registerIcon("biomesoplenty:item_berrybush");
        this.textures[9] = iconRegister.registerIcon("biomesoplenty:item_shrub");
        this.textures[15] = iconRegister.registerIcon("biomesoplenty:deadleafpile");
    }

    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemStack, int par2) {
        return itemStack.getItemDamage() != 3 && itemStack.getItemDamage() != 8
            && itemStack.getItemDamage() != 9
            && itemStack.getItemDamage() != 15 ? BOPCBlocks.foliage.getRenderColor(itemStack.getItemDamage())
                : 16777215;
    }

    public int getMetadata(int meta) {
        return meta & 15;
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        int meta = itemStack.getItemDamage();
        if (meta < 0 || meta >= foliageTypes.length) {
            meta = 0;
        }

        return super.getUnlocalizedName() + "." + foliageTypes[meta];
    }

    public IIcon getIconFromDamage(int meta) {
        if (meta == 6) {
            meta = 3;
        }

        return this.textures[meta];
    }

    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (itemStack.getItemDamage() != 0) {
            return super.onItemRightClick(itemStack, world, player);
        }

        MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);
        if (movingobjectposition == null) {
            return itemStack;
        }

        if (movingobjectposition.typeOfHit == MovingObjectType.BLOCK) {
            int i = movingobjectposition.blockX;
            int j = movingobjectposition.blockY;
            int k = movingobjectposition.blockZ;
            if (!world.canMineBlock(player, i, j, k)) {
                return itemStack;
            }

            if (!player.canPlayerEdit(i, j, k, movingobjectposition.sideHit, itemStack)) {
                return itemStack;
            }

            if (world.getBlock(i, j, k)
                .getMaterial() == Material.water && world.getBlockMetadata(i, j, k) == 0
                && world.isAirBlock(i, j + 1, k)) {
                world.setBlock(i, j + 1, k, this.field_150939_a, 0, 2);
                if (!player.capabilities.isCreativeMode) {
                    itemStack.stackSize--;
                }
            }
        }

        return itemStack;
    }

    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ, int metadata) {
        return metadata == 3 && !this.placeBlockAt(stack, player, world, x, y + 1, z, side, hitX, hitY + 1.0F, hitZ, 6)
            ? false
            : super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
    }
}
