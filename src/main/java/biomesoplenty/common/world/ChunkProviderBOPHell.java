package biomesoplenty.common.world;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCavesHell;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.feature.WorldGenFire;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenGlowStone1;
import net.minecraft.world.gen.feature.WorldGenGlowStone2;
import net.minecraft.world.gen.feature.WorldGenHellLava;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.structure.MapGenNetherBridge;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Post;
import net.minecraftforge.event.terraingen.InitMapGenEvent.EventType;
import net.minecraftforge.event.terraingen.PopulateChunkEvent.Pre;
import net.minecraftforge.event.terraingen.TerrainGen;

public class ChunkProviderBOPHell implements IChunkProvider {

    private Random hellRNG;
    private NoiseGeneratorOctaves netherNoiseGen1;
    private NoiseGeneratorOctaves netherNoiseGen2;
    private NoiseGeneratorOctaves netherNoiseGen3;
    private NoiseGeneratorOctaves slowsandGravelNoiseGen;
    private NoiseGeneratorOctaves netherrackExculsivityNoiseGen;
    public NoiseGeneratorOctaves netherNoiseGen6;
    public NoiseGeneratorOctaves netherNoiseGen7;
    private World worldObj;
    private double[] noiseField;
    public MapGenNetherBridge genNetherBridge = new MapGenNetherBridge();
    private double[] slowsandNoise = new double[256];
    private double[] gravelNoise = new double[256];
    private BiomeGenBase[] biomesForGeneration;
    private double[] netherrackExclusivityNoise = new double[256];
    private MapGenBase netherCaveGenerator = new MapGenCavesHell();
    double[] noiseData1;
    double[] noiseData2;
    double[] noiseData3;
    double[] noiseData4;
    double[] noiseData5;

    public ChunkProviderBOPHell(World par1World, long par2) {
        this.genNetherBridge = (MapGenNetherBridge) TerrainGen
            .getModdedMapGen(this.genNetherBridge, EventType.NETHER_BRIDGE);
        this.netherCaveGenerator = TerrainGen.getModdedMapGen(this.netherCaveGenerator, EventType.NETHER_CAVE);
        this.worldObj = par1World;
        this.hellRNG = new Random(par2);
        this.netherNoiseGen1 = new NoiseGeneratorOctaves(this.hellRNG, 16);
        this.netherNoiseGen2 = new NoiseGeneratorOctaves(this.hellRNG, 16);
        this.netherNoiseGen3 = new NoiseGeneratorOctaves(this.hellRNG, 8);
        this.slowsandGravelNoiseGen = new NoiseGeneratorOctaves(this.hellRNG, 4);
        this.netherrackExculsivityNoiseGen = new NoiseGeneratorOctaves(this.hellRNG, 4);
        this.netherNoiseGen6 = new NoiseGeneratorOctaves(this.hellRNG, 10);
        this.netherNoiseGen7 = new NoiseGeneratorOctaves(this.hellRNG, 16);
    }

    public void generateNetherTerrain(int par1, int par2, Block[] blocks) {
        byte b0 = 4;
        byte b1 = 32;
        int k = b0 + 1;
        byte b2 = 17;
        int l = b0 + 1;
        this.noiseField = this.initializeNoiseField(this.noiseField, par1 * b0, 0, par2 * b0, k, b2, l);

        for (int i1 = 0; i1 < b0; i1++) {
            for (int j1 = 0; j1 < b0; j1++) {
                for (int k1 = 0; k1 < 16; k1++) {
                    double d0 = 0.125;
                    double d1 = this.noiseField[((i1 + 0) * l + j1 + 0) * b2 + k1 + 0];
                    double d2 = this.noiseField[((i1 + 0) * l + j1 + 1) * b2 + k1 + 0];
                    double d3 = this.noiseField[((i1 + 1) * l + j1 + 0) * b2 + k1 + 0];
                    double d4 = this.noiseField[((i1 + 1) * l + j1 + 1) * b2 + k1 + 0];
                    double d5 = (this.noiseField[((i1 + 0) * l + j1 + 0) * b2 + k1 + 1] - d1) * d0;
                    double d6 = (this.noiseField[((i1 + 0) * l + j1 + 1) * b2 + k1 + 1] - d2) * d0;
                    double d7 = (this.noiseField[((i1 + 1) * l + j1 + 0) * b2 + k1 + 1] - d3) * d0;
                    double d8 = (this.noiseField[((i1 + 1) * l + j1 + 1) * b2 + k1 + 1] - d4) * d0;

                    for (int l1 = 0; l1 < 8; l1++) {
                        double d9 = 0.25;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int i2 = 0; i2 < 4; i2++) {
                            int j2 = i2 + i1 * 4 << 11 | 0 + j1 * 4 << 7 | k1 * 8 + l1;
                            short short1 = 128;
                            double d14 = 0.25;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;

                            for (int k2 = 0; k2 < 4; k2++) {
                                Block l2 = Blocks.air;
                                if (k1 * 8 + l1 < b1) {
                                    l2 = Blocks.lava;
                                }

                                if (d15 > 0.0) {
                                    l2 = Blocks.netherrack;
                                }

                                blocks[j2] = l2;
                                j2 += short1;
                                d15 += d16;
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }

    public void replaceBlocksForBiome(int par1, int par2, Block[] blocks, BiomeGenBase[] par4ArrayOfBiomeGenBase) {
        byte b0 = 32;
        double d0 = 0.03125;
        this.slowsandNoise = this.slowsandGravelNoiseGen
            .generateNoiseOctaves(this.slowsandNoise, par1 * 16, par2 * 16, 0, 16, 16, 1, d0, d0, 1.0);
        this.gravelNoise = this.slowsandGravelNoiseGen
            .generateNoiseOctaves(this.gravelNoise, par1 * 16, 109, par2 * 16, 16, 1, 16, d0, 1.0, d0);
        this.netherrackExclusivityNoise = this.netherrackExculsivityNoiseGen.generateNoiseOctaves(
            this.netherrackExclusivityNoise,
            par1 * 16,
            par2 * 16,
            0,
            16,
            16,
            1,
            d0 * 2.0,
            d0 * 2.0,
            d0 * 2.0);

        for (int k = 0; k < 16; k++) {
            for (int l = 0; l < 16; l++) {
                BiomeGenBase biomegenbase = par4ArrayOfBiomeGenBase[l + k * 16];
                boolean flag = this.slowsandNoise[k + l * 16] + this.hellRNG.nextDouble() * 0.2 > 0.0;
                boolean flag1 = this.gravelNoise[k + l * 16] + this.hellRNG.nextDouble() * 0.2 > 0.0;
                int i1 = (int) (this.netherrackExclusivityNoise[k + l * 16] / 3.0 + 3.0
                    + this.hellRNG.nextDouble() * 0.25);
                int j1 = -1;
                Block b1 = biomegenbase.topBlock;
                Block b2 = biomegenbase.fillerBlock;

                for (int k1 = 127; k1 >= 0; k1--) {
                    int l1 = (l * 16 + k) * 128 + k1;
                    if (k1 < 127 - this.hellRNG.nextInt(5) && k1 > 0 + this.hellRNG.nextInt(5)) {
                        Block b3 = blocks[l1];
                        if (b3 == Blocks.air) {
                            j1 = -1;
                        } else if (b3 == Blocks.netherrack) {
                            if (j1 == -1) {
                                if (i1 <= 0) {
                                    b1 = Blocks.air;
                                    b2 = Blocks.netherrack;
                                } else if (k1 >= b0 - 4 && k1 <= b0 + 1) {
                                    b1 = Blocks.netherrack;
                                    b2 = Blocks.netherrack;
                                    if (flag1) {
                                        b1 = Blocks.gravel;
                                    }

                                    if (flag1) {
                                        b2 = Blocks.netherrack;
                                    }

                                    if (flag) {
                                        b1 = Blocks.soul_sand;
                                    }

                                    if (flag) {
                                        b2 = Blocks.soul_sand;
                                    }
                                }

                                if (k1 < b0 && b1 == Blocks.air) {
                                    b1 = Blocks.lava;
                                }

                                j1 = i1;
                                if (k1 >= b0 - 1) {
                                    blocks[l1] = b1;
                                } else {
                                    blocks[l1] = b2;
                                }
                            } else if (j1 > 0) {
                                j1--;
                                blocks[l1] = b2;
                            }
                        }
                    } else {
                        blocks[l1] = Blocks.bedrock;
                    }
                }
            }
        }
    }

    public Chunk loadChunk(int par1, int par2) {
        return this.provideChunk(par1, par2);
    }

    public Chunk provideChunk(int par1, int par2) {
        this.hellRNG.setSeed(par1 * 341873128712L + par2 * 132897987541L);
        byte[] abyte = new byte[32768];
        Block[] blocks = new Block[32768];
        this.generateNetherTerrain(par1, par2, blocks);
        this.biomesForGeneration = this.worldObj.getWorldChunkManager()
            .loadBlockGeneratorData(this.biomesForGeneration, par1 * 16, par2 * 16, 16, 16);
        this.replaceBlocksForBiome(par1, par2, blocks, this.biomesForGeneration);
        this.netherCaveGenerator.func_151539_a(this, this.worldObj, par1, par2, blocks);
        this.genNetherBridge.func_151539_a(this, this.worldObj, par1, par2, blocks);
        Chunk chunk = new Chunk(this.worldObj, blocks, par1, par2);
        BiomeGenBase[] abiomegenbase = this.worldObj.getWorldChunkManager()
            .loadBlockGeneratorData((BiomeGenBase[]) null, par1 * 16, par2 * 16, 16, 16);
        byte[] abyte1 = chunk.getBiomeArray();

        for (int k = 0; k < abyte1.length; k++) {
            abyte1[k] = (byte) abiomegenbase[k].biomeID;
        }

        chunk.resetRelightChecks();
        return chunk;
    }

    private double[] initializeNoiseField(double[] par1ArrayOfDouble, int par2, int par3, int par4, int par5, int par6,
        int par7) {
        if (par1ArrayOfDouble == null) {
            par1ArrayOfDouble = new double[par5 * par6 * par7];
        }

        double d0 = 684.412;
        double d1 = 2053.236;
        this.noiseData4 = this.netherNoiseGen6
            .generateNoiseOctaves(this.noiseData4, par2, par3, par4, par5, 1, par7, 1.0, 0.0, 1.0);
        this.noiseData5 = this.netherNoiseGen7
            .generateNoiseOctaves(this.noiseData5, par2, par3, par4, par5, 1, par7, 100.0, 0.0, 100.0);
        this.noiseData1 = this.netherNoiseGen3
            .generateNoiseOctaves(this.noiseData1, par2, par3, par4, par5, par6, par7, d0 / 80.0, d1 / 60.0, d0 / 80.0);
        this.noiseData2 = this.netherNoiseGen1
            .generateNoiseOctaves(this.noiseData2, par2, par3, par4, par5, par6, par7, d0, d1, d0);
        this.noiseData3 = this.netherNoiseGen2
            .generateNoiseOctaves(this.noiseData3, par2, par3, par4, par5, par6, par7, d0, d1, d0);
        int k1 = 0;
        int l1 = 0;
        double[] adouble1 = new double[par6];

        for (int i2 = 0; i2 < par6; i2++) {
            adouble1[i2] = Math.cos(i2 * Math.PI * 6.0 / par6) * 2.0;
            double d2 = i2;
            if (i2 > par6 / 2) {
                d2 = par6 - 1 - i2;
            }

            if (d2 < 4.0) {
                d2 = 4.0 - d2;
                adouble1[i2] -= d2 * d2 * d2 * 10.0;
            }
        }

        for (int var36 = 0; var36 < par5; var36++) {
            for (int j2 = 0; j2 < par7; j2++) {
                double d3 = (this.noiseData4[l1] + 256.0) / 512.0;
                if (d3 > 1.0) {
                    d3 = 1.0;
                }

                double d4 = 0.0;
                double d5 = this.noiseData5[l1] / 8000.0;
                if (d5 < 0.0) {
                    d5 = -d5;
                }

                d5 = d5 * 3.0 - 3.0;
                if (d5 < 0.0) {
                    d5 /= 2.0;
                    if (d5 < -1.0) {
                        d5 = -1.0;
                    }

                    d5 /= 1.4;
                    d5 /= 2.0;
                    d3 = 0.0;
                } else {
                    if (d5 > 1.0) {
                        d5 = 1.0;
                    }

                    d5 /= 6.0;
                }

                d3 += 0.5;
                d5 = d5 * par6 / 16.0;
                l1++;

                for (int k2 = 0; k2 < par6; k2++) {
                    double d6 = 0.0;
                    double d7 = adouble1[k2];
                    double d8 = this.noiseData2[k1] / 512.0;
                    double d9 = this.noiseData3[k1] / 512.0;
                    double d10 = (this.noiseData1[k1] / 10.0 + 1.0) / 2.0;
                    if (d10 < 0.0) {
                        d6 = d8;
                    } else if (d10 > 1.0) {
                        d6 = d9;
                    } else {
                        d6 = d8 + (d9 - d8) * d10;
                    }

                    d6 -= d7;
                    if (k2 > par6 - 4) {
                        double d11 = (k2 - (par6 - 4)) / 3.0F;
                        d6 = d6 * (1.0 - d11) + -10.0 * d11;
                    }

                    if (k2 < d4) {
                        double d11 = (d4 - k2) / 4.0;
                        if (d11 < 0.0) {
                            d11 = 0.0;
                        }

                        if (d11 > 1.0) {
                            d11 = 1.0;
                        }

                        d6 = d6 * (1.0 - d11) + -10.0 * d11;
                    }

                    par1ArrayOfDouble[k1] = d6;
                    k1++;
                }
            }
        }

        return par1ArrayOfDouble;
    }

    public boolean chunkExists(int par1, int par2) {
        return true;
    }

    public void populate(IChunkProvider par1IChunkProvider, int par2, int par3) {
        BlockFalling.fallInstantly = true;
        MinecraftForge.EVENT_BUS.post(new Pre(par1IChunkProvider, this.worldObj, this.hellRNG, par2, par3, false));
        int k = par2 * 16;
        int l = par3 * 16;
        BiomeGenBase var6 = this.worldObj.getBiomeGenForCoords(k + 16, l + 16);
        this.hellRNG.setSeed(this.worldObj.getSeed());
        long rand1 = this.hellRNG.nextLong() / 2L * 2L + 1L;
        long rand2 = this.hellRNG.nextLong() / 2L * 2L + 1L;
        this.hellRNG.setSeed(par2 * rand1 + par3 * rand2 ^ this.worldObj.getSeed());
        this.genNetherBridge.generateStructuresInChunk(this.worldObj, this.hellRNG, par2, par3);
        boolean doGen = TerrainGen.populate(
            par1IChunkProvider,
            this.worldObj,
            this.hellRNG,
            par2,
            par3,
            false,
            net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.NETHER_LAVA);

        for (int i1 = 0; doGen && i1 < 8; i1++) {
            int j1 = k + this.hellRNG.nextInt(16) + 8;
            int k1 = this.hellRNG.nextInt(120) + 4;
            int l1 = l + this.hellRNG.nextInt(16) + 8;
            new WorldGenHellLava(Blocks.flowing_lava, false).generate(this.worldObj, this.hellRNG, j1, k1, l1);
        }

        int var19 = this.hellRNG.nextInt(this.hellRNG.nextInt(10) + 1) + 1;
        doGen = TerrainGen.populate(
            par1IChunkProvider,
            this.worldObj,
            this.hellRNG,
            par2,
            par3,
            false,
            net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.FIRE);

        for (int j1 = 0; doGen && j1 < var19; j1++) {
            int k1 = k + this.hellRNG.nextInt(16) + 8;
            int l1 = this.hellRNG.nextInt(120) + 4;
            int i2 = l + this.hellRNG.nextInt(16) + 8;
            new WorldGenFire().generate(this.worldObj, this.hellRNG, k1, l1, i2);
        }

        var19 = this.hellRNG.nextInt(this.hellRNG.nextInt(10) + 1);
        doGen = TerrainGen.populate(
            par1IChunkProvider,
            this.worldObj,
            this.hellRNG,
            par2,
            par3,
            false,
            net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.GLOWSTONE);

        for (int var22 = 0; doGen && var22 < var19; var22++) {
            int k1 = k + this.hellRNG.nextInt(16) + 8;
            int l1 = this.hellRNG.nextInt(120) + 4;
            int i2 = l + this.hellRNG.nextInt(16) + 8;
            new WorldGenGlowStone1().generate(this.worldObj, this.hellRNG, k1, l1, i2);
        }

        for (int var23 = 0; doGen && var23 < 10; var23++) {
            int k1 = k + this.hellRNG.nextInt(16) + 8;
            int l1 = this.hellRNG.nextInt(128);
            int i2 = l + this.hellRNG.nextInt(16) + 8;
            new WorldGenGlowStone2().generate(this.worldObj, this.hellRNG, k1, l1, i2);
        }

        MinecraftForge.EVENT_BUS
            .post(new net.minecraftforge.event.terraingen.DecorateBiomeEvent.Pre(this.worldObj, this.hellRNG, k, l));
        doGen = TerrainGen.decorate(
            this.worldObj,
            this.hellRNG,
            k,
            l,
            net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.SHROOM);
        if (doGen && this.hellRNG.nextInt(1) == 0) {
            int var24 = k + this.hellRNG.nextInt(16) + 8;
            int k1 = this.hellRNG.nextInt(128);
            int l1 = l + this.hellRNG.nextInt(16) + 8;
            new WorldGenFlowers(Blocks.brown_mushroom).generate(this.worldObj, this.hellRNG, var24, k1, l1);
        }

        if (doGen && this.hellRNG.nextInt(1) == 0) {
            int var25 = k + this.hellRNG.nextInt(16) + 8;
            int k1 = this.hellRNG.nextInt(128);
            int l1 = l + this.hellRNG.nextInt(16) + 8;
            new WorldGenFlowers(Blocks.red_mushroom).generate(this.worldObj, this.hellRNG, var25, k1, l1);
        }

        WorldGenMinable worldgenminable = new WorldGenMinable(Blocks.quartz_ore, 13, Blocks.netherrack);
        doGen = TerrainGen.generateOre(
            this.worldObj,
            this.hellRNG,
            worldgenminable,
            k,
            l,
            net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.QUARTZ);

        for (int k1 = 0; k1 < 16 && doGen; k1++) {
            int l1 = k + this.hellRNG.nextInt(16);
            int i2 = this.hellRNG.nextInt(108) + 10;
            int j2 = l + this.hellRNG.nextInt(16);
            worldgenminable.generate(this.worldObj, this.hellRNG, l1, i2, j2);
        }

        for (int var32 = 0; var32 < 16; var32++) {
            int l1 = k + this.hellRNG.nextInt(16);
            int i2 = this.hellRNG.nextInt(108) + 10;
            int j2 = l + this.hellRNG.nextInt(16);
            new WorldGenHellLava(Blocks.flowing_lava, true).generate(this.worldObj, this.hellRNG, l1, i2, j2);
        }

        var6.decorate(this.worldObj, this.hellRNG, k, l);
        MinecraftForge.EVENT_BUS.post(new Post(this.worldObj, this.hellRNG, k, l));
        MinecraftForge.EVENT_BUS.post(
            new net.minecraftforge.event.terraingen.PopulateChunkEvent.Post(
                par1IChunkProvider,
                this.worldObj,
                this.hellRNG,
                par2,
                par3,
                false));
        BlockFalling.fallInstantly = false;
    }

    public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate) {
        return true;
    }

    public boolean unloadQueuedChunks() {
        return false;
    }

    public boolean canSave() {
        return true;
    }

    public String makeString() {
        return "HellRandomLevelSource";
    }

    public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4) {
        if (par1EnumCreatureType == EnumCreatureType.monster && this.genNetherBridge.hasStructureAt(par2, par3, par4)) {
            return this.genNetherBridge.getSpawnList();
        }

        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(par2, par4);
        return biomegenbase == null ? null : biomegenbase.getSpawnableList(par1EnumCreatureType);
    }

    public int getLoadedChunkCount() {
        return 0;
    }

    public void recreateStructures(int par1, int par2) {
        this.genNetherBridge.func_151539_a(this, this.worldObj, par1, par2, (Block[]) null);
    }

    public void saveExtraData() {}

    public ChunkPosition func_147416_a(World par1World, String par2Str, int par3, int par4, int par5) {
        return null;
    }
}
