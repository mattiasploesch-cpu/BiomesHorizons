package biomesoplenty.common.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockFruit extends ItemBlock {

    private static final String[] plants = new String[] { "apple_block", "persimmon_block", "peach_block", "pear_block",
        "pinecone_block" };
    @SideOnly(Side.CLIENT)
    private IIcon[] textures;

    public ItemBlockFruit(Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    public int getMetadata(int meta) {
        return meta & 15;
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        int meta = itemStack.getItemDamage();
        if (meta < 0 || meta >= plants.length) {
            meta = 0;
        }

        return super.getUnlocalizedName() + "." + plants[meta];
    }

    public IIcon getIconFromDamage(int meta) {
        return this.field_150939_a.getIcon(0, meta);
    }

    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (itemStack.getItemDamage() != 14) {
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
                && world.isAirBlock(i, j + 1, k)
                && world.getBlock(i, j - 1, k)
                    .getMaterial() != Material.water) {
                world.setBlock(i, j + 1, k, this.field_150939_a, 14, 2);
                if (!player.capabilities.isCreativeMode) {
                    itemStack.stackSize--;
                }
            }
        }

        return itemStack;
    }
}
