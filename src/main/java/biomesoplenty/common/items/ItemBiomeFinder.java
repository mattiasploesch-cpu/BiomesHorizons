package biomesoplenty.common.items;

import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;

import biomesoplenty.BiomesOPlenty;
import biomesoplenty.common.network.BOPPacketHandler;
import biomesoplenty.common.network.message.MessageBiomePosition;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBiomeFinder extends Item {

    public IIcon[] biomeRadarIcons = new IIcon[32];
    private double[] currentAngles = new double[BiomeGenBase.getBiomeGenArray().length];
    private double[] angleDeltas = new double[BiomeGenBase.getBiomeGenArray().length];

    public ItemBiomeFinder() {
        this.setMaxStackSize(1);
        this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
    }

    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (!itemStack.hasTagCompound()) {
            itemStack.setTagCompound(new NBTTagCompound());
        }

        int biomeIDToFind = itemStack.getTagCompound()
            .getInteger("biomeIDToFind");
        if (!world.isRemote && !itemStack.getTagCompound()
            .getBoolean("foundBiome")) {
            BiomeGenBase biomeToFind = BiomeGenBase.getBiomeGenArray()[biomeIDToFind];
            if (biomeToFind != null) {
                int radius = 256;
                WorldChunkManager chunkManager = world.getWorldChunkManager();
                ChunkPosition finalFoundPosition1 = null;
                int playerX = MathHelper.floor_double(player.posX);
                int playerZ = MathHelper.floor_double(player.posZ);

                for (int x = 10; x >= -10; x--) {
                    for (int z = -10; z <= 10; z++) {
                        ChunkPosition foundPosition = chunkManager.findBiomePosition(
                            playerX + x * 512,
                            playerZ + z * 512,
                            radius,
                            Arrays.asList(biomeToFind),
                            world.rand);
                        if (foundPosition != null
                            && world.getBiomeGenForCoords(foundPosition.chunkPosX, foundPosition.chunkPosZ)
                                == biomeToFind) {
                            finalFoundPosition1 = foundPosition;
                            break;
                        }
                    }

                    if (finalFoundPosition1 != null) {
                        break;
                    }
                }

                ChunkPosition finalFoundPosition2 = null;

                for (int x = -10; x <= 10; x++) {
                    for (int z = 10; z >= -10; z--) {
                        ChunkPosition foundPosition = chunkManager.findBiomePosition(
                            playerX + x * 512,
                            playerZ + z * 512,
                            radius,
                            Arrays.asList(biomeToFind),
                            world.rand);
                        if (foundPosition != null
                            && world.getBiomeGenForCoords(foundPosition.chunkPosX, foundPosition.chunkPosZ)
                                == biomeToFind) {
                            finalFoundPosition2 = foundPosition;
                            break;
                        }
                    }

                    if (finalFoundPosition2 != null) {
                        break;
                    }
                }

                ChunkPosition biomePosition = null;
                if (finalFoundPosition1 != null && finalFoundPosition2 != null) {
                    int f1X = finalFoundPosition1.chunkPosX;
                    int f1Z = finalFoundPosition1.chunkPosZ;
                    int f2X = finalFoundPosition2.chunkPosX;
                    int f2Z = finalFoundPosition2.chunkPosZ;
                    if (Math.sqrt(f1X * f1X + f1Z * f1Z) > Math.sqrt(f2X * f2X + f2Z * f2Z)) {
                        biomePosition = finalFoundPosition2;
                    } else {
                        biomePosition = finalFoundPosition1;
                    }
                } else if (finalFoundPosition1 == null) {
                    biomePosition = finalFoundPosition2;
                } else if (finalFoundPosition2 == null) {
                    biomePosition = finalFoundPosition1;
                }

                if (biomePosition != null) {
                    NBTTagCompound biomeCompound = new NBTTagCompound();
                    biomeCompound.setInteger("x", biomePosition.chunkPosX);
                    biomeCompound.setInteger("z", biomePosition.chunkPosZ);
                    itemStack.getTagCompound()
                        .setTag("biomePosition", biomeCompound);
                    itemStack.getTagCompound()
                        .setBoolean("foundBiome", true);
                    BOPPacketHandler.instance.sendTo(
                        new MessageBiomePosition(biomePosition.chunkPosX, biomePosition.chunkPosZ, true),
                        (EntityPlayerMP) player);
                }
            }
        }

        return itemStack;
    }

    public void registerIcons(IIconRegister iconRegister) {
        for (int i = 0; i < 32; i++) {
            this.biomeRadarIcons[i] = iconRegister.registerIcon("biomesoplenty:biomeradar/" + i);
        }

        this.itemIcon = iconRegister.registerIcon("biomesoplenty:biomefinder");
    }

    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack itemStack, int renderPass) {
        Minecraft minecraft = Minecraft.getMinecraft();
        World world = minecraft.theWorld;
        EntityPlayer player = minecraft.thePlayer;
        NBTTagCompound stackCompound = itemStack != null ? itemStack.getTagCompound() : null;
        if (world != null && player != null
            && stackCompound != null
            && stackCompound.hasKey("biomeIDToFind")
            && stackCompound.hasKey("foundBiome")) {
            int biomeID = stackCompound.getInteger("biomeIDToFind");
            boolean foundBiome = stackCompound.getBoolean("foundBiome");
            NBTTagCompound biomePositionCompound = stackCompound.getCompoundTag("biomePosition");
            if (foundBiome) {
                return this.biomeRadarIcons[this.getIconIndexFacingBiome(
                    world,
                    player,
                    biomePositionCompound.getInteger("x"),
                    biomePositionCompound.getInteger("z"),
                    biomeID)];
            }
        }

        return this.itemIcon;
    }

    public void addInformation(ItemStack itemStack, EntityPlayer player, List infoList, boolean advancedItemTooltips) {
        if (itemStack.hasTagCompound() && itemStack.getTagCompound()
            .hasKey("biomeIDToFind")) {
            BiomeGenBase biome = BiomeGenBase.getBiomeGenArray()[itemStack.getTagCompound()
                .getInteger("biomeIDToFind")];
            if (biome != null) {
                infoList.add(
                    "" + EnumChatFormatting.BOLD
                        + StatCollector.translateToLocal("biomeRadar.target")
                        + " "
                        + EnumChatFormatting.RESET
                        + biome.biomeName);
                if (itemStack.getTagCompound()
                    .hasKey("foundBiome")) {
                    NBTTagCompound stackCompound = itemStack != null ? itemStack.getTagCompound() : null;
                    NBTTagCompound biomePositionCompound = stackCompound.getCompoundTag("biomePosition");
                    boolean foundBiome = itemStack.getTagCompound()
                        .getBoolean("foundBiome");
                    if (foundBiome) {
                        infoList.add(
                            "" + EnumChatFormatting.DARK_GREEN
                                + EnumChatFormatting.ITALIC
                                + StatCollector.translateToLocal("biomeRadar.foundBiome"));
                        infoList.add(
                            "" + EnumChatFormatting.GRAY
                                + "X: "
                                + EnumChatFormatting.GREEN
                                + biomePositionCompound.getInteger("x")
                                + EnumChatFormatting.GRAY
                                + "  Z: "
                                + EnumChatFormatting.GREEN
                                + biomePositionCompound.getInteger("z"));
                    } else {
                        infoList.add(
                            "" + EnumChatFormatting.DARK_GRAY
                                + EnumChatFormatting.ITALIC
                                + StatCollector.translateToLocal("biomeRadar.scanBiome"));
                    }
                }
            }
        }
    }

    public int getIconIndexFacingBiome(World world, EntityPlayer player, int biomePosX, int biomePosZ, int biomeID) {
        double d3 = 0.0;
        if (world != null) {
            double d4 = biomePosX - player.posX;
            double d5 = biomePosZ - player.posZ;
            player.rotationYaw = (float) (player.rotationYaw % 360.0);
            d3 = -((player.rotationYaw - 90.0) * Math.PI / 180.0 - Math.atan2(d5, d4));
        }

        double d6 = d3 - this.currentAngles[biomeID];

        while (d6 < -Math.PI) {
            d6 += Math.PI * 2;
        }

        while (d6 >= Math.PI) {
            d6 -= Math.PI * 2;
        }

        if (d6 < -1.0) {
            d6 = -1.0;
        }

        if (d6 > 1.0) {
            d6 = 1.0;
        }

        this.angleDeltas[biomeID] = this.angleDeltas[biomeID] + d6 * 0.1;
        this.angleDeltas[biomeID] = this.angleDeltas[biomeID] * 0.8;
        this.currentAngles[biomeID] = this.currentAngles[biomeID] + this.angleDeltas[biomeID];
        int i = (int) ((this.currentAngles[biomeID] / (Math.PI * 2) + 1.0) * 32.0) % 32;

        while (i < 0) {
            i = (i + 32) % 32;
        }

        return i;
    }
}
