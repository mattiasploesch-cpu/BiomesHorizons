package biomesoplenty.common.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import biomesoplenty.BiomesOPlenty;
import biomesoplenty.api.content.BOPCBlocks;
import biomesoplenty.client.render.RenderUtils;
import biomesoplenty.common.blocks.templates.BOPBlockWorldDecor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBOPFlower extends BOPBlockWorldDecor {

    private static final String[] plants = new String[] { "clover", "swampflower", "deadbloom", "glowflower",
        "hydrangea", "cosmos", "daffodil", "wildflower", "violet", "anemone", "lilyflower", "enderlotus", "bromeliad",
        "eyebulbbottom", "eyebulbtop", "dandelion" };
    private IIcon[] textures;
    private static final int EYEBULBTOP = 14;
    private static final int EYEBULBBOTTOM = 13;

    public BlockBOPFlower() {
        super(Material.plants);
        this.setHardness(0.0F);
        this.setStepSound(Block.soundTypeGrass);
        this.setTickRandomly(true);
        float var4 = 0.2F;
        this.setBlockBounds(0.5F - var4, 0.0F, 0.5F - var4, 0.5F + var4, var4 * 3.0F, 0.5F + var4);
        this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
    }

    public void registerBlockIcons(IIconRegister iconRegister) {
        this.textures = new IIcon[plants.length];

        for (int i = 0; i < plants.length; i++) {
            this.textures[i] = iconRegister.registerIcon("biomesoplenty:" + plants[i]);
        }
    }

    public IIcon getIcon(int side, int meta) {
        if (meta < 0 || meta >= this.textures.length) {
            meta = 0;
        }

        return this.textures[meta];
    }

    public int getRenderType() {
        return RenderUtils.foliageModel;
    }

    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta == 3) {
            return 9;
        } else {
            return meta == 11 ? 5 : 0;
        }
    }

    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        switch (meta) {
            case 0:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.015625F, 1.0F);
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 7:
            case 8:
            case 12:
            case 13:
            case 14:
            default:
                this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 0.8F, 0.9F);
                break;
            case 5:
                this.setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 0.8F, 0.7F);
                break;
            case 6:
                this.setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 0.6F, 0.7F);
                break;
            case 9:
                this.setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 0.5F, 0.7F);
                break;
            case 10:
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
                break;
            case 11:
                this.setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 0.4F, 0.7F);
                break;
            case 15:
                this.setBlockBounds(0.3F, 0.0F, 0.3F, 0.7F, 0.6F, 0.7F);
        }
    }

    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        int meta = world.getBlockMetadata(x, y, z);
        if (!world.isRemote && meta == 2 && entity instanceof EntityLivingBase) {
            if (entity instanceof EntityPlayer) {
                InventoryPlayer inventory = ((EntityPlayer) entity).inventory;
                if (inventory.armorInventory[0] == null || inventory.armorInventory[0].getItem() != Items.leather_boots
                    || inventory.armorInventory[1] == null
                    || inventory.armorInventory[1].getItem() != Items.leather_leggings) {
                    ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.wither.id, 200));
                }
            } else {
                ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.wither.id, 200));
            }
        }
    }

    public void randomDisplayTick(World world, int x, int y, int z, Random random) {
        super.randomDisplayTick(world, x, y, z, random);
        int meta = world.getBlockMetadata(x, y, z);
        if (meta == 2) {
            if (random.nextInt(4) != 0) {
                world.spawnParticle(
                    "townaura",
                    x + random.nextFloat(),
                    y + random.nextFloat(),
                    z + random.nextFloat(),
                    0.0,
                    0.0,
                    0.0);
            }

            if (random.nextInt(4) == 0) {
                world.spawnParticle("smoke", x + random.nextFloat(), y, z + random.nextFloat(), 0.0, 0.0, 0.0);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item block, CreativeTabs creativeTabs, List list) {
        for (int i = 0; i < plants.length; i++) {
            if (i != 14) {
                list.add(new ItemStack(block, 1, i));
            }
        }
    }

    @Override
    public boolean isValidPosition(World world, int x, int y, int z, int metadata) {
        Block block = world.getBlock(x, y - 1, z);
        switch (metadata) {
            case 6:
                return block == Blocks.grass || block == Blocks.dirt
                    || block == Blocks.farmland
                    || block == BOPCBlocks.longGrass
                    || block == BOPCBlocks.overgrownNetherrack
                    || block.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, this);
            case 7:
            case 8:
            case 9:
            default:
                return block == Blocks.grass || block == Blocks.dirt
                    || block == Blocks.farmland
                    || block == BOPCBlocks.longGrass
                    || block == BOPCBlocks.overgrownNetherrack
                    || block.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, this);
            case 10:
                return block == Blocks.waterlily;
            case 11:
                return block == BOPCBlocks.bopGrass;
            case 12:
                return block == BOPCBlocks.hardDirt || block == Blocks.hardened_clay || block == Blocks.sand;
            case 13:
                return block == Blocks.netherrack || block == BOPCBlocks.overgrownNetherrack
                    || block == BOPCBlocks.flesh;
            case 14:
                return block == this;
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
        super.onNeighborBlockChange(world, x, y, z, neighborBlock);
        if (world.getBlockMetadata(x, y, z) == 14 && world.getBlock(x, y - 1, z) == this
            && world.getBlockMetadata(x, y - 1, z) != 13) {
            world.setBlockToAir(x, y, z);
        }

        if (world.getBlockMetadata(x, y, z) == 13 && world.getBlock(x, y + 1, z) != this) {
            world.setBlockToAir(x, y, z);
        }
    }

    public int getDamageValue(World world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        if (meta == 14) {
            meta = 13;
        }

        return meta;
    }

    public int damageDropped(int meta) {
        return meta == 14 ? 13 : meta;
    }

    public int quantityDropped(int meta, int fortune, Random random) {
        return meta == 13 ? 0 : 1;
    }

    public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta) {
        super.harvestBlock(world, player, x, y, z, meta);
        ItemStack equippedItem = player.getCurrentEquippedItem();
        if (equippedItem != null) {
            if (equippedItem.getItem() != Items.shears && meta == 2 && !world.isRemote) {
                player.addPotionEffect(new PotionEffect(Potion.wither.id, 300));
            }
        } else if (meta == 2 && !world.isRemote) {
            player.addPotionEffect(new PotionEffect(Potion.wither.id, 300));
        }
    }

    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z) == 10;
    }
}
