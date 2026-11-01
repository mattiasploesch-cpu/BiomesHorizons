package biomesoplenty.common.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import biomesoplenty.BiomesOPlenty;
import biomesoplenty.api.content.BOPCBiomes;
import biomesoplenty.api.content.BOPCItems;
import biomesoplenty.api.utils.BiomeUtils;

public class BlockBOPGeneric extends Block {

    private IIcon texture;
    private BlockBOPGeneric.BlockType type;

    public BlockBOPGeneric(Material material, BlockBOPGeneric.BlockType type) {
        super(material);
        this.setHarvestLevel("pickaxe", 3, 7);
        this.type = type;
        this.setCreativeTab(BiomesOPlenty.tabBiomesOPlenty);
        switch (type) {
            case ASH_STONE:
                this.setHardness(1.0F);
                this.setStepSound(Block.soundTypePiston);
                break;
            case CRAG_ROCK:
                this.setHardness(1.0F);
                this.setStepSound(Block.soundTypeStone);
                break;
            case DRIED_DIRT:
                this.setHardness(0.1F);
                this.setHarvestLevel("pickaxe", 0);
                this.setStepSound(Block.soundTypePiston);
                break;
            case HARD_DIRT:
                this.setHardness(0.9F);
                this.setStepSound(Block.soundTypePiston);
                break;
            case HARD_ICE:
                this.setHardness(0.75F);
                this.setStepSound(Block.soundTypePiston);
                break;
            case HARD_SAND:
                this.setHardness(0.7F);
                this.setHarvestLevel("shovel", 0);
                this.setStepSound(Block.soundTypeSand);
                break;
            case MUD_BRICK:
                this.setHardness(1.0F);
                this.setResistance(2.0F);
                this.setStepSound(Block.soundTypePiston);
                break;
            case BIOME_BLOCK:
                this.setHardness(0.6F);
                this.setStepSound(soundTypeGlass);
                break;
            case CRYSTAL:
                this.setHardness(0.15F);
                this.setResistance(5.0F);
                this.setLightLevel(1.0F);
                this.setStepSound(Block.soundTypeGlass);
        }
    }

    public void registerBlockIcons(IIconRegister iconRegister) {
        switch (this.type) {
            case ASH_STONE:
                this.texture = iconRegister.registerIcon("biomesoplenty:ashstone");
                break;
            case CRAG_ROCK:
                this.texture = iconRegister.registerIcon("biomesoplenty:cragrock");
                break;
            case DRIED_DIRT:
                this.texture = iconRegister.registerIcon("biomesoplenty:drieddirt");
                break;
            case HARD_DIRT:
                this.texture = iconRegister.registerIcon("biomesoplenty:harddirt");
                break;
            case HARD_ICE:
                this.texture = iconRegister.registerIcon("biomesoplenty:hardice");
                break;
            case HARD_SAND:
                this.texture = iconRegister.registerIcon("biomesoplenty:hardsand");
                break;
            case MUD_BRICK:
                this.texture = iconRegister.registerIcon("biomesoplenty:mudbrick");
                break;
            case BIOME_BLOCK:
                this.texture = iconRegister.registerIcon("biomesoplenty:biomeblock");
                break;
            case CRYSTAL:
                this.texture = iconRegister.registerIcon("biomesoplenty:crystal");
        }
    }

    public Item getItemDropped(int metadata, Random random, int fortune) {
        switch (this.type) {
            case CRYSTAL:
                return BOPCItems.misc;
            default:
                return super.getItemDropped(metadata, random, fortune);
        }
    }

    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int metadata, float chance, int fortune) {
        switch (this.type) {
            case BIOME_BLOCK:
                for (BiomeGenBase biome : BiomeGenBase.getBiomeGenArray()) {
                    if (biome != null && !BiomeUtils.areBiomesEqual(biome, BOPCBiomes.spectralGarden)
                        && !BiomeUtils.areBiomesEqual(biome, BOPCBiomes.boneyard)
                        && !BiomeUtils.areBiomesEqual(biome, BOPCBiomes.visceralHeap)
                        && !BiomeUtils.areBiomesEqual(biome, BOPCBiomes.polarChasm)
                        && !BiomeUtils.areBiomesEqual(biome, BOPCBiomes.undergarden)
                        && !BiomeUtils.areBiomesEqual(biome, BOPCBiomes.corruptedSands)
                        && !BiomeUtils.areBiomesEqual(biome, BOPCBiomes.phantasmagoricInferno)
                        && !BiomeUtils.areBiomesEqual(biome, BOPCBiomes.lushRiver)
                        && !BiomeUtils.areBiomesEqual(biome, BOPCBiomes.dryRiver)
                        && !BiomeUtils.areBiomesEqual(biome, BiomeGenBase.beach)
                        && !BiomeUtils.areBiomesEqual(biome, BiomeGenBase.coldBeach)
                        && !BiomeUtils.areBiomesEqual(biome, BiomeGenBase.stoneBeach)
                        && !BiomeUtils.areBiomesEqual(biome, BiomeGenBase.frozenOcean)
                        && !BiomeUtils.areBiomesEqual(biome, BiomeGenBase.frozenRiver)
                        && !BiomeUtils.areBiomesEqual(biome, BiomeGenBase.hell)
                        && !BiomeUtils.areBiomesEqual(biome, BiomeGenBase.river)
                        && !BiomeUtils.areBiomesEqual(biome, BiomeGenBase.sky)
                        && !BiomeUtils.areBiomesEqual(biome, BiomeGenBase.ocean)
                        && !BiomeUtils.areBiomesEqual(biome, BiomeGenBase.deepOcean)) {
                        ItemStack biomeEssence = new ItemStack(BOPCItems.biomeEssence);
                        biomeEssence.setTagCompound(new NBTTagCompound());
                        biomeEssence.getTagCompound()
                            .setInteger("biomeID", biome.biomeID);
                        if (world.rand.nextInt(75) == 0) {
                            this.dropBlockAsItem(world, x, y, z, biomeEssence);
                        }
                    }
                }
                break;
            default:
                super.dropBlockAsItemWithChance(world, x, y, z, metadata, chance, fortune);
        }
    }

    public int damageDropped(int meta) {
        switch (this.type) {
            case CRYSTAL:
                return 4;
            default:
                return meta;
        }
    }

    public int quantityDropped(int meta, int fortune, Random random) {
        switch (this.type) {
            case CRYSTAL:
                return 4;
            default:
                return 1;
        }
    }

    public IIcon getIcon(int side, int meta) {
        return this.texture;
    }

    public enum BlockType {
        ASH_STONE,
        HARD_SAND,
        HARD_DIRT,
        HARD_ICE,
        DRIED_DIRT,
        CRAG_ROCK,
        MUD_BRICK,
        BIOME_BLOCK,
        CRYSTAL;
    }
}
