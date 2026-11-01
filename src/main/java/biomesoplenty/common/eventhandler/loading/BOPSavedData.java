package biomesoplenty.common.eventhandler.loading;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.WorldSavedData;

public class BOPSavedData extends WorldSavedData {

    public static final String DATA_IDENTIFIER = "biomesoplenty";
    public int lastLoadVersion = 0;

    public BOPSavedData(String identifier) {
        super(identifier);
    }

    public void readFromNBT(NBTTagCompound nbt) {
        this.lastLoadVersion = nbt.getInteger("LastLoadVersion");
    }

    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("LastLoadVersion", this.lastLoadVersion);
    }
}
