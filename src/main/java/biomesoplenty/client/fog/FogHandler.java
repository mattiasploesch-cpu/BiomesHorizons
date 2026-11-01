package biomesoplenty.client.fog;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.client.event.EntityViewRenderEvent.RenderFogEvent;
import net.minecraftforge.common.ForgeModContainer;

import org.lwjgl.opengl.GL11;

import biomesoplenty.common.configuration.BOPConfigurationMisc;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class FogHandler {

    private static double fogX;
    private static double fogZ;
    private static boolean fogInit;
    private static float fogFarPlaneDistance;

    @SubscribeEvent
    public void onGetFogColour(FogColors event) {
        if (BOPConfigurationMisc.fogColors) {
            if (event.entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) event.entity;
                World world = player.worldObj;
                int x = MathHelper.floor_double(player.posX);
                int y = MathHelper.floor_double(player.posY);
                int z = MathHelper.floor_double(player.posZ);
                Block blockAtEyes = ActiveRenderInfo
                    .getBlockAtEntityViewpoint(world, event.entity, (float) event.renderPartialTicks);
                if (blockAtEyes.getMaterial() == Material.lava) {
                    return;
                }

                Vec3 mixedColor;
                if (blockAtEyes.getMaterial() == Material.water) {
                    mixedColor = getFogBlendColorWater(world, player, x, y, z, event.renderPartialTicks);
                } else {
                    mixedColor = getFogBlendColour(
                        world,
                        player,
                        x,
                        y,
                        z,
                        event.red,
                        event.green,
                        event.blue,
                        event.renderPartialTicks);
                }

                event.red = (float) mixedColor.xCoord;
                event.green = (float) mixedColor.yCoord;
                event.blue = (float) mixedColor.zCoord;
            }
        }
    }

    @SubscribeEvent
    public void onRenderFog(RenderFogEvent event) {
        Entity entity = event.entity;
        World world = entity.worldObj;
        int playerX = MathHelper.floor_double(entity.posX);
        int playerY = MathHelper.floor_double(entity.posY);
        int playerZ = MathHelper.floor_double(entity.posZ);
        if (playerX == fogX && playerZ == fogZ && fogInit) {
            renderFog(event.fogMode, fogFarPlaneDistance, 0.75F);
        } else {
            fogInit = true;
            int distance = 20;
            float fpDistanceBiomeFog = 0.0F;
            float weightBiomeFog = 0.0F;

            for (int x = -distance; x <= distance; x++) {
                for (int z = -distance; z <= distance; z++) {
                    BiomeGenBase biome = world.getBiomeGenForCoords(playerX + x, playerZ + z);
                    if (biome instanceof IBiomeFog) {
                        float distancePart = ((IBiomeFog) biome).getFogDensity(playerX + x, playerY, playerZ + z);
                        float weightPart = 1.0F;
                        if (x == -distance) {
                            double xDiff = 1.0 - (entity.posX - playerX);
                            distancePart = (float) (distancePart * xDiff);
                            weightPart = (float) (weightPart * xDiff);
                        } else if (x == distance) {
                            double xDiff = entity.posX - playerX;
                            distancePart = (float) (distancePart * xDiff);
                            weightPart = (float) (weightPart * xDiff);
                        }

                        if (z == -distance) {
                            double zDiff = 1.0 - (entity.posZ - playerZ);
                            distancePart = (float) (distancePart * zDiff);
                            weightPart = (float) (weightPart * zDiff);
                        } else if (z == distance) {
                            double zDiff = entity.posZ - playerZ;
                            distancePart = (float) (distancePart * zDiff);
                            weightPart = (float) (weightPart * zDiff);
                        }

                        fpDistanceBiomeFog += distancePart;
                        weightBiomeFog += weightPart;
                    }
                }
            }

            float weightMixed = distance * 2 * distance * 2;
            float weightDefault = weightMixed - weightBiomeFog;
            float fpDistanceBiomeFogAvg = weightBiomeFog == 0.0F ? 0.0F : fpDistanceBiomeFog / weightBiomeFog;
            float farPlaneDistance = (fpDistanceBiomeFog * 240.0F + event.farPlaneDistance * weightDefault)
                / weightMixed;
            float farPlaneDistanceScaleBiome = 0.1F * (1.0F - fpDistanceBiomeFogAvg) + 0.75F * fpDistanceBiomeFogAvg;
            float farPlaneDistanceScale = (farPlaneDistanceScaleBiome * weightBiomeFog + 0.75F * weightDefault)
                / weightMixed;
            fogX = entity.posX;
            fogZ = entity.posZ;
            fogFarPlaneDistance = Math.min(farPlaneDistance, event.farPlaneDistance);
            renderFog(event.fogMode, fogFarPlaneDistance, farPlaneDistanceScale);
        }
    }

    private static void renderFog(int fogMode, float farPlaneDistance, float farPlaneDistanceScale) {
        if (fogMode < 0) {
            GL11.glFogf(2915, 0.0F);
            GL11.glFogf(2916, farPlaneDistance);
        } else {
            GL11.glFogf(2915, farPlaneDistance * farPlaneDistanceScale);
            GL11.glFogf(2916, farPlaneDistance);
        }
    }

    private static Vec3 postProcessColor(World world, EntityLivingBase player, float r, float g, float b,
        double renderPartialTicks) {
        double darkScale = (player.lastTickPosY + (player.posY - player.lastTickPosY) * renderPartialTicks)
            * world.provider.getVoidFogYFactor();
        if (player.isPotionActive(Potion.blindness)) {
            int duration = player.getActivePotionEffect(Potion.blindness)
                .getDuration();
            darkScale *= duration < 20 ? 1.0F - duration / 20.0F : 0.0;
        }

        if (darkScale < 1.0) {
            darkScale = darkScale < 0.0 ? 0.0 : darkScale * darkScale;
            r = (float) (r * darkScale);
            g = (float) (g * darkScale);
            b = (float) (b * darkScale);
        }

        if (player.isPotionActive(Potion.nightVision)) {
            int duration = player.getActivePotionEffect(Potion.nightVision)
                .getDuration();
            float brightness = duration > 200 ? 1.0F
                : 0.7F + MathHelper.sin((float) ((duration - renderPartialTicks) * Math.PI * 0.2F)) * 0.3F;
            float scale = 1.0F / r;
            scale = Math.min(scale, 1.0F / g);
            scale = Math.min(scale, 1.0F / b);
            r = r * (1.0F - brightness) + r * scale * brightness;
            g = g * (1.0F - brightness) + g * scale * brightness;
            b = b * (1.0F - brightness) + b * scale * brightness;
        }

        if (Minecraft.getMinecraft().gameSettings.anaglyph) {
            float aR = (r * 30.0F + g * 59.0F + b * 11.0F) / 100.0F;
            float aG = (r * 30.0F + g * 70.0F) / 100.0F;
            float aB = (r * 30.0F + b * 70.0F) / 100.0F;
            r = aR;
            g = aG;
            b = aB;
        }

        return Vec3.createVectorHelper(r, g, b);
    }

    private static Vec3 getFogBlendColorWater(World world, EntityLivingBase playerEntity, int playerX, int playerY,
        int playerZ, double renderPartialTicks) {
        int distance = 2;
        float rBiomeFog = 0.0F;
        float gBiomeFog = 0.0F;
        float bBiomeFog = 0.0F;

        for (int x = -distance; x <= distance; x++) {
            for (int z = -distance; z <= distance; z++) {
                BiomeGenBase biome = world.getBiomeGenForCoords(playerX + x, playerZ + z);
                int waterColorMult = biome.waterColorMultiplier;
                float rPart = (waterColorMult & 0xFF0000) >> 16;
                float gPart = (waterColorMult & 0xFF00) >> 8;
                float bPart = waterColorMult & 0xFF;
                if (x == -distance) {
                    double xDiff = 1.0 - (playerEntity.posX - playerX);
                    rPart = (float) (rPart * xDiff);
                    gPart = (float) (gPart * xDiff);
                    bPart = (float) (bPart * xDiff);
                } else if (x == distance) {
                    double xDiff = playerEntity.posX - playerX;
                    rPart = (float) (rPart * xDiff);
                    gPart = (float) (gPart * xDiff);
                    bPart = (float) (bPart * xDiff);
                }

                if (z == -distance) {
                    double zDiff = 1.0 - (playerEntity.posZ - playerZ);
                    rPart = (float) (rPart * zDiff);
                    gPart = (float) (gPart * zDiff);
                    bPart = (float) (bPart * zDiff);
                } else if (z == distance) {
                    double zDiff = playerEntity.posZ - playerZ;
                    rPart = (float) (rPart * zDiff);
                    gPart = (float) (gPart * zDiff);
                    bPart = (float) (bPart * zDiff);
                }

                rBiomeFog += rPart;
                gBiomeFog += gPart;
                bBiomeFog += bPart;
            }
        }

        rBiomeFog /= 255.0F;
        gBiomeFog /= 255.0F;
        bBiomeFog /= 255.0F;
        float weight = distance * 2 * distance * 2;
        float respirationLevel = EnchantmentHelper.getRespiration(playerEntity) * 0.2F;
        float rMixed = (rBiomeFog * 0.02F + respirationLevel) / weight;
        float gMixed = (gBiomeFog * 0.02F + respirationLevel) / weight;
        float bMixed = (bBiomeFog * 0.2F + respirationLevel) / weight;
        return postProcessColor(world, playerEntity, rMixed, gMixed, bMixed, renderPartialTicks);
    }

    private static Vec3 getFogBlendColour(World world, EntityLivingBase playerEntity, int playerX, int playerY,
        int playerZ, float defR, float defG, float defB, double renderPartialTicks) {
        GameSettings settings = Minecraft.getMinecraft().gameSettings;
        int[] ranges = ForgeModContainer.blendRanges;
        int distance = 6;
        if (settings.fancyGraphics && settings.renderDistanceChunks >= 0
            && settings.renderDistanceChunks < ranges.length) {
            distance = ranges[settings.renderDistanceChunks];
        }

        float rBiomeFog = 0.0F;
        float gBiomeFog = 0.0F;
        float bBiomeFog = 0.0F;
        float weightBiomeFog = 0.0F;

        for (int x = -distance; x <= distance; x++) {
            for (int z = -distance; z <= distance; z++) {
                BiomeGenBase biome = world.getBiomeGenForCoords(playerX + x, playerZ + z);
                if (biome instanceof IBiomeFog) {
                    IBiomeFog biomeFog = (IBiomeFog) biome;
                    int fogColour = biomeFog.getFogColour(playerX + x, playerY, playerZ + z);
                    float rPart = (fogColour & 0xFF0000) >> 16;
                    float gPart = (fogColour & 0xFF00) >> 8;
                    float bPart = fogColour & 0xFF;
                    float weightPart = 1.0F;
                    if (x == -distance) {
                        double xDiff = 1.0 - (playerEntity.posX - playerX);
                        rPart = (float) (rPart * xDiff);
                        gPart = (float) (gPart * xDiff);
                        bPart = (float) (bPart * xDiff);
                        weightPart = (float) (weightPart * xDiff);
                    } else if (x == distance) {
                        double xDiff = playerEntity.posX - playerX;
                        rPart = (float) (rPart * xDiff);
                        gPart = (float) (gPart * xDiff);
                        bPart = (float) (bPart * xDiff);
                        weightPart = (float) (weightPart * xDiff);
                    }

                    if (z == -distance) {
                        double zDiff = 1.0 - (playerEntity.posZ - playerZ);
                        rPart = (float) (rPart * zDiff);
                        gPart = (float) (gPart * zDiff);
                        bPart = (float) (bPart * zDiff);
                        weightPart = (float) (weightPart * zDiff);
                    } else if (z == distance) {
                        double zDiff = playerEntity.posZ - playerZ;
                        rPart = (float) (rPart * zDiff);
                        gPart = (float) (gPart * zDiff);
                        bPart = (float) (bPart * zDiff);
                        weightPart = (float) (weightPart * zDiff);
                    }

                    rBiomeFog += rPart;
                    gBiomeFog += gPart;
                    bBiomeFog += bPart;
                    weightBiomeFog += weightPart;
                }
            }
        }

        if (weightBiomeFog != 0.0F && distance != 0) {
            rBiomeFog /= 255.0F;
            gBiomeFog /= 255.0F;
            bBiomeFog /= 255.0F;
            float celestialAngle = world.getCelestialAngle((float) renderPartialTicks);
            float baseScale = MathHelper
                .clamp_float(MathHelper.cos(celestialAngle * (float) Math.PI * 2.0F) * 2.0F + 0.5F, 0.0F, 1.0F);
            float rScale = baseScale * 0.94F + 0.06F;
            float gScale = baseScale * 0.94F + 0.06F;
            float bScale = baseScale * 0.91F + 0.09F;
            float rainStrength = world.getRainStrength((float) renderPartialTicks);
            if (rainStrength > 0.0F) {
                rScale *= 1.0F - rainStrength * 0.5F;
                gScale *= 1.0F - rainStrength * 0.5F;
                bScale *= 1.0F - rainStrength * 0.4F;
            }

            float thunderStrength = world.getWeightedThunderStrength((float) renderPartialTicks);
            if (thunderStrength > 0.0F) {
                rScale *= 1.0F - thunderStrength * 0.5F;
                gScale *= 1.0F - thunderStrength * 0.5F;
                bScale *= 1.0F - thunderStrength * 0.5F;
            }

            rBiomeFog *= rScale / weightBiomeFog;
            gBiomeFog *= gScale / weightBiomeFog;
            bBiomeFog *= bScale / weightBiomeFog;
            Vec3 processedColor = postProcessColor(
                world,
                playerEntity,
                rBiomeFog,
                gBiomeFog,
                bBiomeFog,
                renderPartialTicks);
            rBiomeFog = (float) processedColor.xCoord;
            gBiomeFog = (float) processedColor.yCoord;
            bBiomeFog = (float) processedColor.zCoord;
            float weightMixed = distance * 2 * distance * 2;
            float weightDefault = weightMixed - weightBiomeFog;
            processedColor.xCoord = (rBiomeFog * weightBiomeFog + defR * weightDefault) / weightMixed;
            processedColor.yCoord = (gBiomeFog * weightBiomeFog + defG * weightDefault) / weightMixed;
            processedColor.zCoord = (bBiomeFog * weightBiomeFog + defB * weightDefault) / weightMixed;
            return processedColor;
        } else {
            return Vec3.createVectorHelper(defR, defG, defB);
        }
    }
}
