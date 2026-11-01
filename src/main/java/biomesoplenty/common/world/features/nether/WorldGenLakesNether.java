package biomesoplenty.common.world.features.nether;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import biomesoplenty.api.biome.BOPBiome;
import biomesoplenty.common.world.generation.WorldGeneratorBOP;

public class WorldGenLakesNether extends WorldGeneratorBOP {

    @Override
    public boolean generate(World world, Random rand, int x, int y, int z) {
        x -= 8;
        z -= 8;

        while (y > 5 && world.isAirBlock(x, y, z)) {
            y--;
        }

        if (y <= 4) {
            return false;
        }

        y -= 4;
        boolean[] aboolean = new boolean[2048];
        int l = rand.nextInt(4) + 4;

        for (int i1 = 0; i1 < l; i1++) {
            double d0 = rand.nextDouble() * 6.0 + 3.0;
            double d1 = rand.nextDouble() * 4.0 + 2.0;
            double d2 = rand.nextDouble() * 6.0 + 3.0;
            double d3 = rand.nextDouble() * (16.0 - d0 - 2.0) + 1.0 + d0 / 2.0;
            double d4 = rand.nextDouble() * (8.0 - d1 - 4.0) + 2.0 + d1 / 2.0;
            double d5 = rand.nextDouble() * (16.0 - d2 - 2.0) + 1.0 + d2 / 2.0;

            for (int k1 = 1; k1 < 15; k1++) {
                for (int l1 = 1; l1 < 15; l1++) {
                    for (int i2 = 1; i2 < 7; i2++) {
                        double d6 = (k1 - d3) / (d0 / 2.0);
                        double d7 = (i2 - d4) / (d1 / 2.0);
                        double d8 = (l1 - d5) / (d2 / 2.0);
                        double d9 = d6 * d6 + d7 * d7 + d8 * d8;
                        if (d9 < 1.0) {
                            aboolean[(k1 * 16 + l1) * 8 + i2] = true;
                        }
                    }
                }
            }
        }

        for (int var35 = 0; var35 < 16; var35++) {
            for (int j2 = 0; j2 < 16; j2++) {
                for (int j1 = 0; j1 < 8; j1++) {
                    boolean flag = !aboolean[(var35 * 16 + j2) * 8 + j1]
                        && (var35 < 15 && aboolean[((var35 + 1) * 16 + j2) * 8 + j1]
                            || var35 > 0 && aboolean[((var35 - 1) * 16 + j2) * 8 + j1]
                            || j2 < 15 && aboolean[(var35 * 16 + j2 + 1) * 8 + j1]
                            || j2 > 0 && aboolean[(var35 * 16 + (j2 - 1)) * 8 + j1]
                            || j1 < 7 && aboolean[(var35 * 16 + j2) * 8 + j1 + 1]
                            || j1 > 0 && aboolean[(var35 * 16 + j2) * 8 + (j1 - 1)]);
                    if (flag) {
                        Material material = world.getBlock(x + var35, y + j1, z + j2)
                            .getMaterial();
                        if (j1 >= 4 && material.isLiquid()) {
                            return false;
                        }

                        if (j1 < 4 && !material.isSolid() && world.getBlock(x + var35, y + j1, z + j2) != Blocks.lava) {
                            return false;
                        }
                    }
                }
            }
        }

        for (int var36 = 0; var36 < 16; var36++) {
            for (int j2 = 0; j2 < 16; j2++) {
                for (int j1 = 0; j1 < 8; j1++) {
                    if (aboolean[(var36 * 16 + j2) * 8 + j1]) {
                        world.setBlock(x + var36, y + j1, z + j2, j1 >= 4 ? Blocks.air : Blocks.lava, 0, 2);
                    }
                }
            }
        }

        for (int var37 = 0; var37 < 16; var37++) {
            for (int j2 = 0; j2 < 16; j2++) {
                for (int j1 = 0; j1 < 8; j1++) {
                    boolean flag = !aboolean[(var37 * 16 + j2) * 8 + j1]
                        && (var37 < 15 && aboolean[((var37 + 1) * 16 + j2) * 8 + j1]
                            || var37 > 0 && aboolean[((var37 - 1) * 16 + j2) * 8 + j1]
                            || j2 < 15 && aboolean[(var37 * 16 + j2 + 1) * 8 + j1]
                            || j2 > 0 && aboolean[(var37 * 16 + (j2 - 1)) * 8 + j1]
                            || j1 < 7 && aboolean[(var37 * 16 + j2) * 8 + j1 + 1]
                            || j1 > 0 && aboolean[(var37 * 16 + j2) * 8 + (j1 - 1)]);
                    if (flag && (j1 < 4 || rand.nextInt(2) != 0)
                        && world.getBlock(x + var37, y + j1, z + j2)
                            .getMaterial()
                            .isSolid()) {
                        world.setBlock(x + var37, y + j1, z + j2, Blocks.netherrack, 0, 2);
                    }
                }
            }
        }

        return true;
    }

    @Override
    public void setupGeneration(World world, Random random, BOPBiome biome, String featureName, int x, int z) {
        for (int i = 0; i < (Integer) biome.theBiomeDecorator.bopFeatures.getFeature(featureName); i++) {
            int randX = x + random.nextInt(16) + 8;
            int randY = random.nextInt(random.nextInt(random.nextInt(112) + 8) + 8);
            int randZ = z + random.nextInt(16) + 8;
            this.generate(world, random, randX, randY, randZ);
        }
    }
}
