package eisenwave.spatium.function;

import java.util.Objects;
import java.util.function.BiConsumer;

@FunctionalInterface
public interface Int2Consumer extends BiConsumer<Integer, Integer> {

    /**
     * Performs this operation on the given argument.
     *
     * @param x the input argument
     */
    void accept(int x, int y);

    /**
     * Performs this operation on the given argument.
     *
     * @param x the input argument
     */
    default void accept(Integer x, Integer y) {
        accept(x.intValue(), y.intValue());
    }

    default Int2Consumer andThen(Int2Consumer action) {
        Objects.requireNonNull(action);
        return (x, y) -> {this.accept(x, y); action.accept(x, y);};
    }

}
