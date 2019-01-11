package eisenwave.spatium.array;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;

public class Array3<T> extends AbstractArray3 implements Iterable<T> {

    private final T[] data;

    @SuppressWarnings("unchecked")
    public Array3(int x, int y, int z, Class<T> component) {
        super(x, y, z);
        
        this.data = (T[]) Array.newInstance(component, this.length);
    }
    
    public Array3(Array3<T> copyOf) {
        super(copyOf);
        
        this.data = Arrays.copyOf(copyOf.data, copyOf.data.length);
    }

    /**
     * Returns an element at the given coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return the element at the coordinates
     */
    public T get(int x, int y, int z) {
        return data[indexOf(x,y,z)];
    }

    /**
     * Sets an element at the given coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @param value the new value
     */
    public void set(int x, int y, int z, T value) {
        data[indexOf(x,y,z)] = value;
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
    public Array3<T> clone() {
        return new Array3<>(this);
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(this.data);
    }
    
    @Override
    public String toString() {
        return Arrays.toString(this.data);
    }
    
}
