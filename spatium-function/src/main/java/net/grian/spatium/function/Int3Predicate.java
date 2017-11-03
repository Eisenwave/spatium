package net.grian.spatium.function;

@FunctionalInterface
public interface Int3Predicate extends TriPredicate<Integer, Integer, Integer> {

    abstract boolean test(int x, int y, int z);
    
    default boolean test(Integer x, Integer y, Integer z) {
        return test(x.intValue(), y.intValue(), z.intValue());
    }

}
