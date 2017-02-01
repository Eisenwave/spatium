package net.grian.spatium.function;

import net.grian.spatium.geo3.BlockVector;

import java.util.function.Predicate;

@FunctionalInterface
public interface Int3Predicate extends Predicate<BlockVector> {

    boolean test(int x, int y, int z);
    
    default boolean test(BlockVector v) {
        return test(v.getX(), v.getY(), v.getZ());
    }

}
