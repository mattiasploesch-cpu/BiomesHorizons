package biomesoplenty.common.world.features;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import biomesoplenty.api.biome.BOPBiome;
import biomesoplenty.common.world.generation.WorldGeneratorBOP;

public class WorldGenBOPBigMushroom extends WorldGeneratorBOP {

    private List soilBlocks;
    private int mushroomType = -1;

    public WorldGenBOPBigMushroom(Block... soilBlocks) {
        super(false);
        this.soilBlocks = Arrays.asList(soilBlocks);
    }

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        int mushroomType = random.nextInt(2);
        if (this.mushroomType >= 0) {
            mushroomType = this.mushroomType;
        }

        int height = random.nextInt(3) + 4;
        boolean flag = true;
        if (y >= 1 && y + height + 1 < 256) {
            for (int yItr = y; yItr <= y + 1 + height; yItr++) {
                byte radius = 3;
                if (yItr <= y + 3) {
                    radius = 0;
                }

                for (int k1 = x - radius; k1 <= x + radius && flag; k1++) {
                    for (int l1 = z - radius; l1 <= z + radius && flag; l1++) {
                        if (yItr >= 0 && yItr < 256) {
                            Block block = world.getBlock(k1, yItr, l1);
                            if (!block.isAir(world, k1, yItr, l1) && !block.isLeaves(world, k1, yItr, l1)) {
                                flag = false;
                            }
                        } else {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag) {
                return false;
            }

            Block soilBlock = world.getBlock(x, y - 1, z);
            if (!this.soilBlocks.contains(soilBlock)) {
                return false;
            }

            int topY = y + height;
            if (mushroomType == 1) {
                topY = y + height - 3;
            }

            for (int k1 = topY; k1 <= y + height; k1++) {
                int l1 = 1;
                if (k1 < y + height) {
                    l1++;
                }

                if (mushroomType == 0) {
                    l1 = 3;
                }

                for (int l2 = x - l1; l2 <= x + l1; l2++) {
                    for (int i2 = z - l1; i2 <= z + l1; i2++) {
                        int j2 = 5;
                        if (l2 == x - l1) {
                            j2--;
                        }

                        if (l2 == x + l1) {
                            j2++;
                        }

                        if (i2 == z - l1) {
                            j2 -= 3;
                        }

                        if (i2 == z + l1) {
                            j2 += 3;
                        }

                        if (mushroomType == 0 || k1 < y + height) {
                            if ((l2 == x - l1 || l2 == x + l1) && (i2 == z - l1 || i2 == z + l1)) {
                                continue;
                            }

                            if (l2 == x - (l1 - 1) && i2 == z - l1) {
                                j2 = 1;
                            }

                            if (l2 == x - l1 && i2 == z - (l1 - 1)) {
                                j2 = 1;
                            }

                            if (l2 == x + (l1 - 1) && i2 == z - l1) {
                                j2 = 3;
                            }

                            if (l2 == x + l1 && i2 == z - (l1 - 1)) {
                                j2 = 3;
                            }

                            if (l2 == x - (l1 - 1) && i2 == z + l1) {
                                j2 = 7;
                            }

                            if (l2 == x - l1 && i2 == z + (l1 - 1)) {
                                j2 = 7;
                            }

                            if (l2 == x + (l1 - 1) && i2 == z + l1) {
                                j2 = 9;
                            }

                            if (l2 == x + l1 && i2 == z + (l1 - 1)) {
                                j2 = 9;
                            }
                        }

                        if (j2 == 5 && k1 < y + height) {
                            j2 = 0;
                        }

                        if ((j2 != 0 || y >= y + height - 1) && world.getBlock(l2, k1, i2)
                            .canBeReplacedByLeaves(world, l2, k1, i2)) {
                            this.setBlockAndNotifyAdequately(
                                world,
                                l2,
                                k1,
                                i2,
                                Block.getBlockById(Block.getIdFromBlock(Blocks.brown_mushroom_block) + mushroomType),
                                j2);
                        }
                    }
                }
            }

            for (int var17 = 0; var17 < height; var17++) {
                Block block2 = world.getBlock(x, y + var17, z);
                if (block2.canBeReplacedByLeaves(world, x, y + var17, z)) {
                    this.setBlockAndNotifyAdequately(
                        world,
                        x,
                        y + var17,
                        z,
                        Block.getBlockById(Block.getIdFromBlock(Blocks.brown_mushroom_block) + mushroomType),
                        10);
                }
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setupGeneration(World world, Random random, BOPBiome biome, String featureName, int x, int z) {
        for (int i = 0; i < (Integer) biome.theBiomeDecorator.bopFeatures.getFeature(featureName); i++) {
            int randX = x + random.nextInt(16) + 8;
            int randZ = z + random.nextInt(16) + 8;
            int randY = random.nextInt(256);
            this.generate(world, random, randX, randY, randZ);
        }
    }
}
