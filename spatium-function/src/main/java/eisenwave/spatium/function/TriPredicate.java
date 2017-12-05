package eisenwave.spatium.function;

public interface TriPredicate<A,B,C> {
    
    abstract boolean test(A a, B b, C c);
    
}
