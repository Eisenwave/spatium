package eisenwave.spatium.array;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

public class ArrayIterator<T> implements Iterator<T> {
    
    protected final T[] array;
    protected int limit;
    protected int index;
    
    public ArrayIterator(T[] array, int index) {
        if (index < 0) throw new ArrayIndexOutOfBoundsException(Integer.toString(index));
        
        this.array = Objects.requireNonNull(array);
        this.limit = array.length;
        this.index = index - 1;
    }
    
    public ArrayIterator(@NotNull T[] array) {
        this(array, 0);
    }
    
    @Override
    public boolean hasNext() {
        //System.out.println((index+1)+" "+limit);
        return index+1 < limit;
    }
    
    @Override
    public T next() {
        return array[++index];
    }
    
    @Override
    public void remove() {
        array[index] = null;
    }
    
    @Override
    public void forEachRemaining(Consumer<? super T> action) {
        for (int i = index; i < limit; i++)
            action.accept(array[i]);
    }
    
}
