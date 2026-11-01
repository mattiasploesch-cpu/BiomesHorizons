package biomesoplenty.common.network.message;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import biomesoplenty.api.content.BOPCItems;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;

public class MessageBiomePosition implements IMessage, IMessageHandler<MessageBiomePosition, IMessage> {

    private int x;
    private int z;
    private boolean foundBiome;

    public MessageBiomePosition() {}

    public MessageBiomePosition(int x, int z, boolean foundBiome) {
        this.x = x;
        this.z = z;
        this.foundBiome = foundBiome;
    }

    public void fromBytes(ByteBuf buf) {
        this.x = buf.readInt();
        this.z = buf.readInt();
        this.foundBiome = buf.readBoolean();
    }

    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.x);
        buf.writeInt(this.z);
        buf.writeBoolean(this.foundBiome);
    }

    public IMessage onMessage(MessageBiomePosition message, MessageContext ctx) {
        if (ctx.side == Side.SERVER) {
            EntityPlayer player = ctx.getServerHandler().playerEntity;
            NBTTagCompound biomeCompound = new NBTTagCompound();
            biomeCompound.setInteger("x", this.x);
            biomeCompound.setInteger("z", this.z);
            ItemStack currentItem = player.getCurrentEquippedItem();
            if (currentItem.getItem() == BOPCItems.biomeFinder) {
                if (!currentItem.hasTagCompound()) {
                    currentItem.setTagCompound(new NBTTagCompound());
                }

                currentItem.getTagCompound()
                    .setBoolean("foundBiome", this.foundBiome);
                currentItem.getTagCompound()
                    .setTag("biomePosition", biomeCompound);
            }
        }

        return null;
    }
}
