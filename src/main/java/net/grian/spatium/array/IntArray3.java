package net.grian.spatium.array;

import net.grian.spatium.function.IntConsumer;

import java.util.Iterator;
import java.util.function.Consumer;

public class IntArray3 extends AbstractArray3 implements Iterable<Integer> {

    private final int[] content;

    @SuppressWarnings("unchecked")
    public IntArray3(int x, int y, int z) {
        super(x,y,z);
        this.content = new int[x * y * z];
    }

    /**
     * Returns an element at the given coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return the element at the coordinates
     */
    public int get(int x, int y, int z) {
        return content[indexOf(x, y, z)];
    }

    /**
     * Sets an element at the given coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @param value the new value
     */
    public void set(int x, int y, int z, int value) {
        content[indexOf(x, y, z)] = value;
    }

    @Override
    public void forEach(Consumer<? super Integer> action) {
        for (int x = 0; x<sizeX; x++)
            for (int y = 0; y<sizeY; y++)
                for (int z = 0; z<sizeZ; z++)
                    action.accept(get(x, y, z));
    }

    public void forEachInt(IntConsumer action) {
        for (int x = 0; x<sizeX; x++)
            for (int y = 0; y<sizeY; y++)
                for (int z = 0; z<sizeZ; z++)
                    action.accept(get(x, y, z));
    }

    @Override
    public IntArrayIterator3 iterator() {
        return new IntArrayIterator3();
    }

    private final class IntArrayIterator3 implements Iterator<Integer> {

        private final Incrementer3 incrementer = new Incrementer3(sizeX, sizeY, sizeZ);

        private IntArrayIterator3() {}

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
            int result = get(pos[0], pos[1], pos[2]);
            incrementer.increment();

            return result;
        }

    }

}
