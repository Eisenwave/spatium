package eisenwave.spatium.array;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;

public class Array2<T> extends AbstractArray2 implements Iterable<T> {

    private final T[] data;

    @SuppressWarnings("unchecked")
    public Array2(int x, int y, Class<T> component) {
        super(x, y);
        
        this.data = (T[]) Array.newInstance(component, this.length);
    }
    
    @SuppressWarnings("unchecked")
    public Array2(Array2<T> copyOf) {
        super(copyOf);
    
        this.data = Arrays.copyOf(copyOf.data, copyOf.data.length);
    }

    /**
     * Returns an element at the given coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return the element at the coordinates
     */
    public T get(int x, int y) {
        return data[indexOf(x,y)];
    }

    /**
     * Sets an element at the given coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param value the new value
     */
    public void set(int x, int y, T value) {
        data[indexOf(x,y)] = value;
    }
    
    /**
     * Fills the array with a given value.
     *
     * @param value the value
     */
    public void fill(T value) {
        for (int i = 0; i < data.length; i++)
            data[i] = value;
    }
    
    // ITERATION
    
    @Override
    public void forEach(Consumer<? super T> action) {
        for (T obj : data)
            action.accept(obj);
    }
    
    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator<>(data);
    }

    // MISC
    
    @Override
    public Array2<T> clone() {
        return new Array2<>(this);
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(this.data);
    }

}
