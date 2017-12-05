package eisenwave.spatium.function;

import java.util.Objects;

@FunctionalInterface
public interface Int3Consumer extends TriConsumer<Integer, Integer, Integer> {

    void accept(int x, int y, int z);

    /**
     * Performs this operation on the given argument.
     *
     * @param x the input argument
     */
    default void accept(Integer x, Integer y, Integer z) {
        accept(x.intValue(), y.intValue(), z.intValue());
    }
    
    default Int3Consumer andThen(Int3Consumer action) {
        Objects.requireNonNull(action);
        return (x, y, z) -> {this.accept(x, y, z); action.accept(x, y, z);};
    }

}
