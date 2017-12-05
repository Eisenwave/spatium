package eisenwave.spatium.function;

public interface TriConsumer<A,B,C> {
    
    abstract void accept(A a, B b, C c);
    
}
