package net.grian.spatium.function;

import java.util.Objects;
import java.util.function.Consumer;

@FunctionalInterface
public interface IntConsumer extends Consumer<Integer> {

    /**
     * Performs this operation on the given argument.
     *
     * @param x the input argument
     */
    void accept(int x);

    /**
     * Performs this operation on the given argument.
     *
     * @param x the input argument
     */
    default void accept(Integer x) {
        accept(x.intValue());
    }

    default IntConsumer andThen(IntConsumer action) {
        Objects.requireNonNull(action);
        return (x) -> {this.accept(x); action.accept(x);};
    }

}
