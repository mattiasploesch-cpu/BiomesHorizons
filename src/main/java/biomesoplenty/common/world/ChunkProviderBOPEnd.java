package biomesoplenty.common.world;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;

import biomesoplenty.common.utils.PerlinNoise;

public class ChunkProviderBOPEnd implements IChunkProvider {

    private Random rand;
    private World worldObj;
    private PerlinNoise perlin;
    private BiomeGenBase[] biomesForGeneration;

    public ChunkProviderBOPEnd(World world, long l) {
        this.worldObj = world;
        this.rand = new Random(l);
        this.perlin = new PerlinNoise(l);
    }

    public Chunk provideChunk(int cx, int cy) {
        BiomeGenBase biome = BiomeGenBase.sky;
        this.rand.setSeed(cx * 341873128712L + cy * 132897987541L);
        Block[] blocks = new Block[65536];
        byte[] metadata = new byte[65536];
        this.biomesForGeneration = this.worldObj.getWorldChunkManager()
            .loadBlockGeneratorData(this.biomesForGeneration, cx * 16, cy * 16, 16, 16);
        this.generateTerrain(cx, cy, blocks, metadata, this.biomesForGeneration);
        this.replaceBlocksForBiome(cx, cy, blocks, metadata, this.biomesForGeneration);
        Chunk chunk = new Chunk(this.worldObj, blocks, metadata, cx, cy);
        byte[] abyte1 = chunk.getBiomeArray();

        for (int k = 0; k < abyte1.length; k++) {
            abyte1[k] = (byte) this.biomesForGeneration[k].biomeID;
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    public void generateTerrain(int cx, int cy, Block[] blocks, byte[] metadata, BiomeGenBase[] biome) {
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                float spawnDis = (20000 - ((cx * 16 + j) * (cx * 16 + j) + (cy * 16 + i) * (cy * 16 + i))) / 13000.0F;
                float noiseC = this.perlin.noise2((cx * 16 + j) / 80.0F, (cy * 16 + i) / 80.0F) * 6.0F - 1.2F;
                noiseC = spawnDis > noiseC ? spawnDis : noiseC;
                float noiseA = 64.0F + this.perlin.noise2((cx * 16 + j) / 40.0F, (cy * 16 + i) / 40.0F) * 6.0F
                    + this.perlin.noise2((cx * 16 + j) / 12.0F, (cy * 16 + i) / 12.0F) * 3.0F
                    + noiseC * 3.0F;
                float noiseB = 12.0F + this.perlin.noise2((1000.0F + cx * 16 + j) / 40.0F, (cy * 16 + i) / 40.0F) * 6.0F
                    + this.perlin.noise2((cx * 16 + j) / 12.0F, (cy * 16 + i) / 12.0F) * 3.0F
                    - noiseC * 3.0F;

                for (int k = 0; k < 256; k++) {
                    int p = (j * 16 + i) * 256 + k;
                    if (k > 72) {
                        blocks[p] = Blocks.air;
                    } else if (k < noiseA && k > noiseB) {
                        float noise3 = this.perlin.noise3((cx * 16 + j) / 30.0F, (cy * 16 + i) / 30.0F, k / 20.0F)
                            + noiseC;
                        if (noise3 > 0.2F) {
                            blocks[p] = Blocks.end_stone;
                        } else {
                            blocks[p] = Blocks.air;
                        }
                    } else {
                        blocks[p] = Blocks.air;
                    }
                }
            }
        }
    }

    public void replaceBlocksForBiome(int cx, int cy, Block[] blocks, byte[] metadata, BiomeGenBase[] biomes) {
        int depth = -1;

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                BiomeGenBase biome = biomes[i * 16 + j];

                for (int k = 255; k > -1; k--) {
                    Block b = blocks[(j * 16 + i) * 256 + k];
                    if (b == Blocks.air) {
                        depth = -1;
                    } else if (b == Blocks.end_stone) {
                        if (++depth == 0) {
                            blocks[(j * 16 + i) * 256 + k] = biome.topBlock;
                        } else if (depth < 4) {
                            blocks[(j * 16 + i) * 256 + k] = biome.fillerBlock;
                        }
                    }
                }
            }
        }
    }

    public Chunk loadChunk(int par1, int par2) {
        return this.provideChunk(par1, par2);
    }

    private double[] func_4061_a(double[] ad, int i, int j, int k, int l, int i1, int j1) {
        return null;
    }

    public boolean chunkExists(int par1, int par2) {
        return true;
    }

    public void populate(IChunkProvider ichunkprovider, int i, int j) {
        BlockFalling.fallInstantly = true;
        int x = i * 16;
        int y = j * 16;
        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(x + 16, y + 16);
        this.rand.setSeed(this.worldObj.getSeed());
        long i1 = this.rand.nextLong() / 2L * 2L + 1L;
        long j1 = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed(i * i1 + j * j1 ^ this.worldObj.getSeed());
        biomegenbase.decorate(this.worldObj, this.rand, x, y);
        SpawnerAnimals.performWorldGenSpawning(this.worldObj, biomegenbase, x + 8, y + 8, 16, 16, this.rand);
        BlockFalling.fallInstantly = false;
    }

    public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate) {
        return true;
    }

    public boolean unloadQueuedChunks() {
        return false;
    }

    public boolean unload100OldestChunks() {
        return false;
    }

    public boolean canSave() {
        return true;
    }

    public String makeString() {
        return "RandomLevelSource";
    }

    public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4) {
        BiomeGenBase var5 = this.worldObj.getBiomeGenForCoords(par2, par4);
        return var5 == null ? null : var5.getSpawnableList(par1EnumCreatureType);
    }

    public ChunkPosition func_147416_a(World par1World, String par2Str, int par3, int par4, int par5) {
        return null;
    }

    public int getLoadedChunkCount() {
        return 0;
    }

    public void saveExtraData() {}

    public void recreateStructures(int par1, int par2) {}
}
