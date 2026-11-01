package biomesoplenty.common.utils;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import cpw.mods.fml.relauncher.ReflectionHelper;

public class RandomForcedPositiveOwned extends Random {

    private final Random parent;

    public RandomForcedPositiveOwned(Random parent) {
        super(
            ((AtomicLong) ReflectionHelper.getPrivateValue(Random.class, parent, new String[] { "seed" })).longValue());
        this.parent = parent;
    }

    @Override
    public int nextInt() {
        return this.nextInt(1);
    }

    @Override
    public int nextInt(int n) {
        return n > 0 ? this.parent.nextInt(n) : 0;
    }
}
