package net.grian.spatium.array;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.function.Consumer;

public class Array3<T> extends AbstractArray3 implements Iterable<T> {

    private final T[][][] content;

    @SuppressWarnings("unchecked")
    public Array3(int x, int y, int z, Class<T> component) {
        super(x, y, z);
        content = (T[][][]) Array.newInstance(component, x, y, z);
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
        return content[x][y][z];
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
        content[x][y][z] = value;
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        for (int x = 0; x<sizeX; x++)
            for (int y = 0; y<sizeY; y++)
                for (int z = 0; z<sizeZ; z++)
                    action.accept(get(x, y, z));
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator3();
    }

    private final class ArrayIterator3 implements Iterator<T> {

        private final Incrementer3 incrementer = new Incrementer3(sizeX, sizeY, sizeZ);

        private ArrayIterator3() {}

        @Override
        public boolean hasNext() {
            return incrementer.canIncrement();
        }

        @Override
        public T next() {
            int[] pos = incrementer.current();
            T result = get(pos[0], pos[1], pos[2]);
            incrementer.increment();

            return result;
        }

    }

}
