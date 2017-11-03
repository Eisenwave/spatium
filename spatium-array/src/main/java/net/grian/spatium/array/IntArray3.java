package net.grian.spatium.array;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class IntArray3 extends AbstractArray3 implements Iterable<Integer>, Cloneable {

    private final int[] data;

    @SuppressWarnings("unchecked")
    public IntArray3(int x, int y, int z) {
        super(x,y,z);
        this.data = new int[x * y * z];
    }
    
    public IntArray3(IntArray3 copyOf) {
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
    public int get(int x, int y, int z) {
        return data[indexOf(x, y, z)];
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
        data[indexOf(x, y, z)] = value;
    }

    // ITERATION
    
    @Override
    public void forEach(Consumer<? super Integer> action) {
        for (int i : data)
            action.accept(i);
    }

    public void forEachInt(IntConsumer action) {
        for (int i : data)
            action.accept(i);
    }

    @NotNull
    @Override
    public IntArrayIterator3 iterator() {
        return new IntArrayIterator3();
    }

    private final class IntArrayIterator3 implements Iterator<Integer> {

        private int index = 0;

        private IntArrayIterator3() {}

        @Override
        public boolean hasNext() {
            return index < data.length;
        }

        @Override
        public Integer next() {
            return data[index++];
        }

        public int nextInt() {
            return data[index++];
        }

    }

}
