package biomesoplenty.common.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import biomesoplenty.BiomesOPlenty;
import biomesoplenty.api.BOPItemHelper;
import biomesoplenty.api.content.BOPCBlocks;
import biomesoplenty.api.content.BOPCItems;

public class ItemBOPScythe extends Item {

    public int textureID = 0;
    protected ToolMaterial toolMaterial;

    public ItemBOPScythe(ToolMaterial toolMaterial, int texture) {
        this.toolMaterial = toolMaterial;
        this.maxStackSize = 1;
        this.setMaxDamage(toolMaterial.getMaxUses());
        this.textureID = texture;
        this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
    }

    public boolean onBlockDestroyed(ItemStack itemstack, World world, Block block, int x, int y, int z,
        EntityLivingBase entity) {
        int radius = 3;
        int height = 4;
        if (this.toolMaterial != ToolMaterial.IRON && this.toolMaterial != ToolMaterial.GOLD) {
            if (this.toolMaterial == ToolMaterial.EMERALD) {
                if (block != null && block.isLeaves(world, x, y, z)) {
                    height = 5;
                }

                radius = 5;
            } else if (this.toolMaterial == BOPItemHelper.toolMaterialAmethyst) {
                if (block != null && block.isLeaves(world, x, y, z)) {
                    height = 6;
                }

                radius = 6;
            } else if (block != null && block.isLeaves(world, x, y, z)) {
                height = 0;
            }
        } else {
            if (block != null && block.isLeaves(world, x, y, z)) {
                height = 4;
            }

            radius = 4;
        }

        if (block != null) {
            if (block.isLeaves(world, x, y, z)) {
                if (height > 0) {
                    this.trimLeaves(itemstack, entity, world, x, y, z, height, radius);
                    return true;
                }

                itemstack.damageItem(1, entity);
            } else {
                this.trimCutCorner(itemstack, entity, world, x, y, z, height, radius);
                if (world.rand.nextInt(3) == 0) {
                    this.trim(itemstack, entity, world, x, y, z, height, radius - 1);
                    return true;
                }
            }
        }

        return false;
    }

    public void trim(ItemStack stack, EntityLivingBase entity, World world, int x, int y, int z, int height,
        int radius) {
        for (int aX = -radius; aX <= radius; aX++) {
            for (int aY = 0; aY <= radius; aY++) {
                for (int aZ = -radius; aZ <= radius; aZ++) {
                    Block block = world.getBlock(x + aX, y + aY, z + aZ);
                    int meta = world.getBlockMetadata(x + aX, y + aY, z + aZ);
                    if (block != null && block != Blocks.air) {
                        if (block == BOPCBlocks.foliage && (meta == 1 || meta == 2 || meta == 6)) {
                            if (meta == 1) {
                                block.dropBlockAsItem(world, x + aX, y + aY, z + aZ, meta, 0);
                                world.setBlockToAir(x + aX, y + aY, z + aZ);
                            } else if (meta == 2) {
                                block.dropBlockAsItem(world, x + aX, y + aY, z + aZ, meta, 0);
                                world.setBlock(x + aX, y + aY, z + aZ, BOPCBlocks.foliage, 1, 2);
                            } else if (meta == 6) {
                                block.dropBlockAsItem(world, x + aX, y + aY, z + aZ, meta, 0);
                                world.setBlockToAir(x + aX, y + aY, z + aZ);
                            }
                        } else if (block == Blocks.tallgrass) {
                            block.dropBlockAsItem(world, x + aX, y + aY, z + aZ, meta, 0);
                            world.setBlock(x + aX, y + aY, z + aZ, BOPCBlocks.foliage, 2, 2);
                        } else if (block != Blocks.waterlily && block instanceof BlockFlower) {
                            block.dropBlockAsItem(world, x + aX, y + aY, z + aZ, meta, 0);
                            world.setBlockToAir(x + aX, y + aY, z + aZ);
                        }
                    }
                }
            }
        }
    }

    public void trimLeaves(ItemStack stack, EntityLivingBase entity, World world, int x, int y, int z, int height,
        int radius) {
        for (int aX = -radius; aX <= radius; aX++) {
            for (int aY = -radius; aY <= radius; aY++) {
                for (int aZ = -radius; aZ <= radius; aZ++) {
                    Block block = world.getBlock(x + aX, y + aY, z + aZ);
                    int meta = world.getBlockMetadata(x + aX, y + aY, z + aZ);
                    if (block != null && block.isLeaves(world, x + aX, y + aY, z + aZ)) {
                        if (this.toolMaterial != ToolMaterial.IRON && this.toolMaterial != ToolMaterial.GOLD) {
                            if (this.toolMaterial == ToolMaterial.EMERALD) {
                                if (world.rand.nextInt(27) == 0) {
                                    stack.damageItem(1, entity);
                                }
                            } else if (this.toolMaterial == BOPItemHelper.toolMaterialAmethyst) {
                                if (world.rand.nextInt(28) == 0) {
                                    stack.damageItem(1, entity);
                                }
                            } else {
                                if (world.rand.nextInt(24) != 0) {
                                    return;
                                }

                                stack.damageItem(1, entity);
                            }
                        } else if (world.rand.nextInt(26) == 0) {
                            stack.damageItem(1, entity);
                        }

                        block.dropBlockAsItem(world, x + aX, y + aY, z + aZ, meta, 0);
                        world.setBlockToAir(x + aX, y + aY, z + aZ);
                    }
                }
            }
        }
    }

    public void trimCutCorner(ItemStack stack, EntityLivingBase entity, World world, int x, int y, int z, int height,
        int radius) {
        for (int aX = -radius; aX <= radius; aX++) {
            for (int aY = 0; aY <= radius; aY++) {
                for (int aZ = -radius; aZ <= radius; aZ++) {
                    if (aX + aZ < radius * 2 && -aX + aZ < radius * 2
                        && aX + -aZ < radius * 2
                        && -aX + -aZ < radius * 2) {
                        Block block = world.getBlock(x + aX, y + aY, z + aZ);
                        int meta = world.getBlockMetadata(x + aX, y + aY, z + aZ);
                        if (block != null && block != Blocks.air) {
                            if (this.toolMaterial != ToolMaterial.IRON && this.toolMaterial != ToolMaterial.GOLD) {
                                if (this.toolMaterial == ToolMaterial.EMERALD) {
                                    if (world.rand.nextInt(37) == 0) {
                                        stack.damageItem(1, entity);
                                    }
                                } else if (this.toolMaterial == BOPItemHelper.toolMaterialAmethyst) {
                                    if (world.rand.nextInt(38) == 0) {
                                        stack.damageItem(1, entity);
                                    }
                                } else if (world.rand.nextInt(34) == 0) {
                                    stack.damageItem(1, entity);
                                }
                            } else if (world.rand.nextInt(36) == 0) {
                                stack.damageItem(1, entity);
                            }

                            if (block == BOPCBlocks.foliage && (meta == 1 || meta == 2 || meta == 6)) {
                                if (meta == 1) {
                                    block.dropBlockAsItem(world, x + aX, y + aY, z + aZ, meta, 0);
                                    world.setBlockToAir(x + aX, y + aY, z + aZ);
                                } else if (meta == 2) {
                                    block.dropBlockAsItem(world, x + aX, y + aY, z + aZ, meta, 0);
                                    world.setBlock(x + aX, y + aY, z + aZ, BOPCBlocks.foliage, 1, 2);
                                } else if (meta == 6) {
                                    block.dropBlockAsItem(world, x + aX, y + aY, z + aZ, meta, 0);
                                    world.setBlockToAir(x + aX, y + aY, z + aZ);
                                }
                            } else if (block == Blocks.tallgrass) {
                                block.dropBlockAsItem(world, x + aX, y + aY, z + aZ, meta, 0);
                                world.setBlock(x + aX, y + aY, z + aZ, BOPCBlocks.foliage, 2, 2);
                            } else if (block != Blocks.waterlily && block instanceof BlockFlower) {
                                block.dropBlockAsItem(world, x + aX, y + aY, z + aZ, meta, 0);
                                world.setBlockToAir(x + aX, y + aY, z + aZ);
                            }
                        }
                    }
                }
            }
        }
    }

    public float func_150893_a(ItemStack item, Block block) {
        return Items.shears.func_150893_a(item, block);
    }

    public int getItemEnchantability() {
        return this.toolMaterial.getEnchantability();
    }

    public boolean getIsRepairable(ItemStack itemToRepair, ItemStack itemToRepairWith) {
        if (this.textureID == 0 && itemToRepairWith.getItem() == Item.getItemFromBlock(Blocks.planks)) {
            return true;
        } else if (this.textureID == 1 && itemToRepairWith.getItem() == Item.getItemFromBlock(Blocks.cobblestone)) {
            return true;
        } else if (this.textureID == 2 && itemToRepairWith.getItem() == Items.iron_ingot) {
            return true;
        } else if (this.textureID == 3 && itemToRepairWith.getItem() == Items.gold_ingot) {
            return true;
        } else {
            return this.textureID == 4 && itemToRepairWith.getItem() == Items.diamond ? true
                : this.textureID == 6 && itemToRepairWith.getItem() == BOPCItems.misc
                    && itemToRepairWith.getItemDamage() == 2;
        }
    }

    public boolean isFull3D() {
        return true;
    }

    public void registerIcons(IIconRegister iconRegister) {
        if (this.textureID == 0) {
            this.itemIcon = iconRegister.registerIcon("biomesoplenty:woodscythe");
        } else if (this.textureID == 1) {
            this.itemIcon = iconRegister.registerIcon("biomesoplenty:stonescythe");
        } else if (this.textureID == 2) {
            this.itemIcon = iconRegister.registerIcon("biomesoplenty:ironscythe");
        } else if (this.textureID == 3) {
            this.itemIcon = iconRegister.registerIcon("biomesoplenty:goldscythe");
        } else if (this.textureID == 4) {
            this.itemIcon = iconRegister.registerIcon("biomesoplenty:diamondscythe");
        } else if (this.textureID == 5) {
            this.itemIcon = iconRegister.registerIcon("biomesoplenty:mudscythe");
        } else if (this.textureID == 6) {
            this.itemIcon = iconRegister.registerIcon("biomesoplenty:amethystscythe");
        } else {
            this.itemIcon = iconRegister.registerIcon("biomesoplenty:mudball");
        }
    }
}
