package biomesoplenty.common.world.features.trees;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.util.ForgeDirection;

public class WorldGenBogTree2 extends WorldGenAbstractTree {

    private final int minTreeHeight;
    private final int randomTreeHeight;
    private final Block wood;
    private final Block leaves;
    private final int woodMeta;
    private final int leavesMeta;

    public WorldGenBogTree2(Block wood, Block leaves, int woodMeta, int leavesMeta, boolean doBlockNotify,
        int minTreeHeight, int randomTreeHeight) {
        super(doBlockNotify);
        this.wood = wood;
        this.leaves = leaves;
        this.woodMeta = woodMeta;
        this.leavesMeta = leavesMeta;
        this.minTreeHeight = minTreeHeight;
        this.randomTreeHeight = randomTreeHeight;
    }

    public boolean generate(World world, Random random, int x, int y, int z) {
        int l = random.nextInt(this.randomTreeHeight) + this.minTreeHeight;

        while (world.getBlock(x, y - 1, z)
            .getMaterial() == Material.water) {
            y--;
        }

        boolean flag = true;
        if (y >= 1 && y + l + 1 <= 256) {
            for (int i1 = y; i1 <= y + 1 + l; i1++) {
                byte b0 = 1;
                if (i1 == y) {
                    b0 = 0;
                }

                if (i1 >= y + 1 + l - 2) {
                    b0 = 3;
                }

                for (int j1 = x - b0; j1 <= x + b0 && flag; j1++) {
                    for (int k1 = z - b0; k1 <= z + b0 && flag; k1++) {
                        if (i1 >= 0 && i1 < 256) {
                            Block block = world.getBlock(j1, i1, k1);
                            if (!block.isAir(world, j1, i1, k1) && !block.isLeaves(world, j1, i1, k1)) {
                                if (block != Blocks.water && block != Blocks.flowing_water) {
                                    flag = false;
                                } else if (i1 > y) {
                                    flag = false;
                                }
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

            Block block1 = world.getBlock(x, y - 1, z);
            boolean isSoil = block1
                .canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, (BlockSapling) Blocks.sapling);
            if (isSoil && y < 256 - l - 1) {
                block1.onPlantGrow(world, x, y - 1, z, x, y, z);

                for (int k2 = y - 3 + l; k2 <= y + l; k2++) {
                    int j1 = k2 - (y + l);
                    int k1 = 2 - j1 / 2;

                    for (int l2 = x - k1; l2 <= x + k1; l2++) {
                        int l1 = l2 - x;

                        for (int i2 = z - k1; i2 <= z + k1; i2++) {
                            int j2 = i2 - z;
                            if ((Math.abs(l1) != k1 || Math.abs(j2) != k1 || random.nextInt(2) != 0 && j1 != 0)
                                && world.getBlock(l2, k2, i2)
                                    .canBeReplacedByLeaves(world, l2, k2, i2)) {
                                this.setBlockAndNotifyAdequately(world, l2, k2, i2, this.leaves, this.leavesMeta);
                            }
                        }
                    }
                }

                for (int var26 = 0; var26 < l; var26++) {
                    Block block2 = world.getBlock(x, y + var26, z);
                    if (block2.isAir(world, x, y + var26, z) || block2.isLeaves(world, x, y + var26, z)
                        || block2 == Blocks.flowing_water
                        || block2 == Blocks.water) {
                        this.setBlockAndNotifyAdequately(world, x, y + var26, z, this.wood, this.woodMeta);
                        this.setBlockAndNotifyAdequately(world, x - 1, y, z, this.wood, this.woodMeta + 4);
                        this.setBlockAndNotifyAdequately(world, x + 1, y, z, this.wood, this.woodMeta + 4);
                        this.setBlockAndNotifyAdequately(world, x, y, z - 1, this.wood, this.woodMeta + 8);
                        this.setBlockAndNotifyAdequately(world, x, y, z + 1, this.wood, this.woodMeta + 8);
                        this.setBlockAndNotifyAdequately(world, x - 1, y + (l - 4), z, this.wood, this.woodMeta + 4);
                        this.setBlockAndNotifyAdequately(world, x + 1, y + (l - 4), z, this.wood, this.woodMeta + 4);
                        this.setBlockAndNotifyAdequately(world, x, y + (l - 4), z - 1, this.wood, this.woodMeta + 8);
                        this.setBlockAndNotifyAdequately(world, x, y + (l - 4), z + 1, this.wood, this.woodMeta + 8);
                        this.setBlockAndNotifyAdequately(world, x - 2, y + (l - 3), z, this.wood, this.woodMeta);
                        this.setBlockAndNotifyAdequately(world, x + 2, y + (l - 3), z, this.wood, this.woodMeta);
                        this.setBlockAndNotifyAdequately(world, x, y + (l - 3), z - 2, this.wood, this.woodMeta);
                        this.setBlockAndNotifyAdequately(world, x, y + (l - 3), z + 2, this.wood, this.woodMeta);
                    }
                }

                for (int var27 = y - 3 + l; var27 <= y + l; var27++) {
                    int j1 = var27 - (y + l);
                    int k1 = 2 - j1 / 2;

                    for (int l2 = x - k1; l2 <= x + k1; l2++) {
                        for (int l1 = z - k1; l1 <= z + k1; l1++) {
                            if (world.getBlock(l2, var27, l1)
                                .isLeaves(world, l2, var27, l1)) {
                                if (random.nextInt(4) == 0 && world.getBlock(l2 - 1, var27, l1)
                                    .isAir(world, l2 - 1, var27, l1)) {
                                    this.generateVines(world, l2 - 1, var27, l1, 8);
                                }

                                if (random.nextInt(4) == 0 && world.getBlock(l2 + 1, var27, l1)
                                    .isAir(world, l2 + 1, var27, l1)) {
                                    this.generateVines(world, l2 + 1, var27, l1, 2);
                                }

                                if (random.nextInt(4) == 0 && world.getBlock(l2, var27, l1 - 1)
                                    .isAir(world, l2, var27, l1 - 1)) {
                                    this.generateVines(world, l2, var27, l1 - 1, 1);
                                }

                                if (random.nextInt(4) == 0 && world.getBlock(l2, var27, l1 + 1)
                                    .isAir(world, l2, var27, l1 + 1)) {
                                    this.generateVines(world, l2, var27, l1 + 1, 4);
                                }
                            }
                        }
                    }
                }

                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void generateVines(World par1World, int par2, int par3, int par4, int par5) {
        this.setBlockAndNotifyAdequately(par1World, par2, par3, par4, Blocks.vine, par5);
        int i1 = 4;

        while (true) {
            par3--;
            if (!par1World.getBlock(par2, par3, par4)
                .isAir(par1World, par2, par3, par4) || i1 <= 0) {
                return;
            }

            this.setBlockAndNotifyAdequately(par1World, par2, par3, par4, Blocks.vine, par5);
            i1--;
        }
    }
}
