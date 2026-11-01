package biomesoplenty.common.world.features.trees;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.util.ForgeDirection;

import biomesoplenty.api.content.BOPCBlocks;

public class WorldGenRedwoodTree extends WorldGenAbstractTree {

    private final Block wood;
    private final Block leaves;
    private final int woodMeta;
    private final int leavesMeta;
    private final int minTreeHeight;
    private final int randomTreeHeight;

    public WorldGenRedwoodTree(Block wood, Block leaves, int woodMeta, int leavesMeta, boolean doBlockNotify,
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
        if (y >= 1 && y < 254) {
            for (int i1 = y; i1 <= y + 1 + l; i1++) {
                byte b0 = 7;
                if (i1 == y) {
                    b0 = 7;
                }

                if (i1 >= y + 1 + l - 2) {
                    b0 = 8;
                }

                for (int j1 = x - b0; j1 <= x + b0; j1++) {
                    for (int k1 = z - b0; k1 <= z + b0; k1++) {
                        if (i1 < 0 || i1 >= 256) {
                            return false;
                        }

                        Block block = world.getBlock(j1, i1, k1);
                        if (!this.isReplaceable(world, j1, i1, k1)) {
                            return false;
                        }
                    }
                }
            }

            for (int ix = -3; ix <= 3; ix++) {
                for (int iz = -3; iz <= 3; iz++) {
                    Block block2 = world.getBlock(x + ix, y - 1, z + iz);
                    if (!block2.canSustainPlant(
                        world,
                        x + ix,
                        y - 1,
                        z + iz,
                        ForgeDirection.UP,
                        (BlockSapling) Blocks.sapling)) {
                        return false;
                    }
                }
            }

            for (int ix = -3; ix <= 3; ix++) {
                for (int iy = 0; iy <= l; iy++) {
                    for (int iz = -3; iz <= 3; iz++) {
                        Block block2 = world.getBlock(x + ix, y + iy, z + iz);
                        if (!block2.isAir(world, x + ix, y + iy, z + iz)) {
                            return false;
                        }
                    }
                }
            }

            for (int ix = -3; ix <= 3; ix++) {
                for (int iz = -3; iz <= 3; iz++) {
                    if ((ix != -3 && ix != 3 || iz >= -1 && iz <= 1) && (iz != -3 && iz != 3 || ix >= -1 && ix <= 1)) {
                        Block block2 = world.getBlock(x + ix, y - 1, z + iz);
                        block2.onPlantGrow(world, x + ix, y - 1, z + iz, x + ix, y - 1, z + iz);
                    }
                }
            }

            byte b0 = 9;
            byte b1 = 0;

            for (int k1 = y - b0 + l; k1 <= y + l; k1++) {
                int i3 = k1 - (y + l);
                int l1 = b1 + 1 - i3 / 4;

                for (int i2 = x - l1; i2 <= x + l1; i2++) {
                    int j2 = i2 - x;

                    for (int k2 = z - l1; k2 <= z + l1; k2++) {
                        int l2 = k2 - z;
                        if (Math.abs(j2) != l1 || Math.abs(l2) != l1 || random.nextInt(2) != 0 && i3 != 0) {
                            Block block1 = world.getBlock(i2, k1, k2);
                            if (block1.isAir(world, i2, k1, k2) || block1.isLeaves(world, i2, k1, k2)) {
                                this.setBlockAndNotifyAdequately(world, i2, k1 + 6, k2, this.leaves, this.leavesMeta);
                                this.setBlockAndNotifyAdequately(world, i2, k1 + 10, k2, this.leaves, this.leavesMeta);
                            }
                        }
                    }
                }
            }

            for (int var21 = 0; var21 < l; var21++) {
                Block block = world.getBlock(x, y + var21, z);
                if (block.isAir(world, x, y + var21, z) || block.isLeaves(world, x, y + var21, z)) {
                    this.setBlockAndNotifyAdequately(world, x, y + l, z, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x, y + l + 1, z, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x, y + l + 2, z, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x, y + l + 3, z, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x, y + l, z, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x, y + l, z, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x, y + var21, z, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x - 1, y + var21, z, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x + 1, y + var21, z, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x, y + var21, z - 1, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x, y + var21, z + 1, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(
                        world,
                        x - 1,
                        y + MathHelper.floor_double(var21 * 0.75),
                        z - 1,
                        this.wood,
                        this.woodMeta);
                    this.setBlockAndNotifyAdequately(
                        world,
                        x + 1,
                        y + MathHelper.floor_double(var21 * 0.75),
                        z - 1,
                        this.wood,
                        this.woodMeta);
                    this.setBlockAndNotifyAdequately(
                        world,
                        x - 1,
                        y + MathHelper.floor_double(var21 * 0.75),
                        z + 1,
                        this.wood,
                        this.woodMeta);
                    this.setBlockAndNotifyAdequately(
                        world,
                        x + 1,
                        y + MathHelper.floor_double(var21 * 0.75),
                        z + 1,
                        this.wood,
                        this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x - 2, y + var21 / 2, z, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x - 2, y + var21 / 2, z - 1, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x - 2, y + var21 / 2, z + 1, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x + 2, y + var21 / 2, z, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x + 2, y + var21 / 2, z - 1, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x + 2, y + var21 / 2, z + 1, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x, y + var21 / 2, z - 2, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x - 1, y + var21 / 2, z - 2, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x + 1, y + var21 / 2, z - 2, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x, y + var21 / 2, z + 2, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x - 1, y + var21 / 2, z + 2, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x + 1, y + var21 / 2, z + 2, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x - 2, y + var21 / 4, z - 2, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x + 2, y + var21 / 4, z + 2, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x + 2, y + var21 / 4, z - 2, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x - 2, y + var21 / 4, z + 2, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x - 3, y + var21 / 4, z, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x + 3, y + var21 / 4, z, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x, y + var21 / 4, z - 3, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x, y + var21 / 4, z + 3, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x - 3, y + var21 / 6, z - 1, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x - 3, y + var21 / 6, z + 1, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x + 3, y + var21 / 6, z - 1, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x + 3, y + var21 / 6, z + 1, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x - 1, y + var21 / 6, z - 3, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x + 1, y + var21 / 6, z - 3, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x - 1, y + var21 / 6, z + 3, this.wood, this.woodMeta);
                    this.setBlockAndNotifyAdequately(world, x + 1, y + var21 / 6, z + 3, this.wood, this.woodMeta);
                }
            }

            for (int i = 0; i < 90; i++) {
                int randX = x - random.nextInt(8) + random.nextInt(8);
                int randY = y + random.nextInt(64);
                int randZ = z - random.nextInt(8) + random.nextInt(8);
                new WorldGenBOPShrub(BOPCBlocks.logs3, BOPCBlocks.colorizedLeaves1, 0, 3, BOPCBlocks.logs3)
                    .generate(world, random, randX, randY, randZ);
            }

            return true;
        } else {
            return false;
        }
    }
}
