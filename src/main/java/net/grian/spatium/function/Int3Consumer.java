package net.grian.spatium.function;

import net.grian.spatium.geo3.BlockVector;

import java.util.Objects;
import java.util.function.Consumer;

@FunctionalInterface
public interface Int3Consumer extends Consumer<BlockVector> {

    void accept(int x, int y, int z);

    /**
     * Performs this operation on the given argument.
     *
     * @param x the input argument
     */
    default void accept(Integer x, Integer y, Integer z) {
        accept(x.intValue(), y.intValue(), z.intValue());
    }

    default void accept(BlockVector v) {
        accept(v.getX(), v.getY(), v.getZ());
    }
    
    default Int3Consumer andThen(Int3Consumer action) {
        Objects.requireNonNull(action);
        return (x, y, z) -> {this.accept(x, y, z); action.accept(x, y, z);};
    }

}
