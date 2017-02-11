package net.grian.spatium.array;

import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class IntArray2 extends AbstractArray2 implements Iterable<Integer> {

    private final int[] content;

    public IntArray2(int x, int y) {
        super(x, y);
        content = new int[x * y];
    }

    /**
     * Returns an element at the given coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return the element at the coordinates
     */
    public int get(int x, int y) {
        return content[indexOf(x, y)];
    }

    /**
     * Sets an element at the given coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param value the new value
     */
    public void set(int x, int y, int value) {
        content[indexOf(x, y)] = value;
    }

    @Override
    public void forEach(Consumer<? super Integer> action) {
        for (int x = 0; x<sizeX; x++)
            for (int y = 0; y<sizeY; y++)
                action.accept(get(x, y));
    }

    public void forEachInt(IntConsumer action) {
        for (int x = 0; x<sizeX; x++)
            for (int y = 0; y<sizeY; y++)
                action.accept(get(x, y));
    }

    @Override
    public IntArrayIterator2 iterator() {
        return new IntArrayIterator2();
    }

    public final class IntArrayIterator2 implements Iterator<Integer> {

        private final Incrementer2 incrementer = new Incrementer2(sizeX, sizeY);

        private IntArrayIterator2() {}

        @Override
        public boolean hasNext() {
            return incrementer.canIncrement();
        }

        @Override
        public Integer next() {
            return nextInt();
        }

        public int nextInt() {
            int[] pos = incrementer.current();
            int result = get(pos[0], pos[1]);
            incrementer.increment();

            return result;
        }

    }

}
