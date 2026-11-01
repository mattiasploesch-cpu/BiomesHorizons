package biomesoplenty.common.network.message;

import java.util.Random;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class MessageSpawnParticle implements IMessage, IMessageHandler<MessageSpawnParticle, IMessage> {

    private Random random = new Random();
    public String particleName;
    public float posX;
    public float posY;
    public float posZ;
    public float offsetX;
    public float offsetY;
    public float offsetZ;
    public float velocity;
    public int amount;

    public MessageSpawnParticle() {}

    public MessageSpawnParticle(String particleName, float posX, float posY, float posZ, float offsetX, float offsetY,
        float offsetZ, float velocity, int amount) {
        this.particleName = particleName;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
        this.velocity = velocity;
        this.amount = amount;
    }

    public void fromBytes(ByteBuf buf) {
        this.particleName = ByteBufUtils.readUTF8String(buf);
        this.posX = buf.readFloat();
        this.posY = buf.readFloat();
        this.posZ = buf.readFloat();
        this.offsetX = buf.readFloat();
        this.offsetY = buf.readFloat();
        this.offsetZ = buf.readFloat();
        this.velocity = buf.readFloat();
        this.amount = buf.readInt();
    }

    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.particleName);
        buf.writeFloat(this.posX);
        buf.writeFloat(this.posY);
        buf.writeFloat(this.posZ);
        buf.writeFloat(this.offsetX);
        buf.writeFloat(this.offsetY);
        buf.writeFloat(this.offsetZ);
        buf.writeFloat(this.velocity);
        buf.writeInt(this.amount);
    }

    public IMessage onMessage(MessageSpawnParticle message, MessageContext ctx) {
        if (this.amount == 0) {
            double d0 = this.velocity * this.offsetX;
            double d2 = this.velocity * this.offsetY;
            double var7 = this.velocity * this.offsetZ;
        } else {
            for (int i = 0; i < this.amount; i++) {
                double offsetX = this.random.nextGaussian() * this.offsetX;
                double offsetY = this.random.nextGaussian() * this.offsetY;
                double offsetZ = this.random.nextGaussian() * this.offsetZ;
                double d6 = this.random.nextGaussian() * this.velocity;
                double d7 = this.random.nextGaussian() * this.velocity;
                double var14 = this.random.nextGaussian() * this.velocity;
            }
        }

        return null;
    }
}
