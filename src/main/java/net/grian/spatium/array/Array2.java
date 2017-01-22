package net.grian.spatium.array;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.function.Consumer;

public class Array2<T> extends AbstractArray2 implements Iterable<T> {

    private final T[][] content;

    @SuppressWarnings("unchecked")
    public Array2(int x, int y, Class<T> component) {
        super(x, y);
        content = (T[][]) Array.newInstance(component, x, y);
    }

    /**
     * Returns an element at the given coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return the element at the coordinates
     */
    public T get(int x, int y) {
        return content[x][y];
    }

    /**
     * Sets an element at the given coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param value the new value
     */
    public void set(int x, int y, T value) {
        content[x][y] = value;
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        for (int x = 0; x<sizeX; x++)
            for (int y = 0; y<sizeY; y++)
                action.accept(get(x, y));
    }

    @Override
    public ArrayIterator2 iterator() {
        return new ArrayIterator2();
    }

    private final class ArrayIterator2 implements Iterator<T> {

        private final Incrementer2 incrementer = new Incrementer2(sizeX, sizeY);

        private ArrayIterator2() {}

        @Override
        public boolean hasNext() {
            return incrementer.canIncrement();
        }

        @Override
        public T next() {
            int[] pos = incrementer.current();
            T result = get(pos[0], pos[1]);
            incrementer.increment();

            return result;
        }

    }

}
