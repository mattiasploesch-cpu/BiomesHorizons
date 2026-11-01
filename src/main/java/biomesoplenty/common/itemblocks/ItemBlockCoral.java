package biomesoplenty.common.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import biomesoplenty.api.content.BOPCBlocks;
import biomesoplenty.common.blocks.BlockBOPCoral;

public class ItemBlockCoral extends ItemBlock {

    private IIcon[] textures;

    public ItemBlockCoral(Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    public int getMetadata(int meta) {
        return meta < 8 ? meta + 8 : meta;
    }

    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ, int metadata) {
        if (this.field_150939_a == BOPCBlocks.coral1 && metadata > 8 && metadata < 12) {
            metadata = world.getBlock(x, y - 1, z) == this.field_150939_a ? 10 : 11;
        }

        return super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
    }

    public void registerIcons(IIconRegister iconRegister) {
        this.textures = new IIcon[1];
        this.textures[0] = iconRegister.registerIcon("biomesoplenty:item_kelp");
    }

    public IIcon getIconFromDamage(int meta) {
        return this.field_150939_a.getIcon(0, meta);
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        BlockBOPCoral block = (BlockBOPCoral) this.field_150939_a;
        return super.getUnlocalizedName() + "." + block.getCoralType(itemStack.getItemDamage());
    }
}
