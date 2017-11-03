package net.grian.spatium.array;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class IntArray2 extends AbstractArray2 implements Iterable<Integer>, Cloneable {

    private final int[] data;

    public IntArray2(int x, int y) {
        super(x, y);
        data = new int[x * y];
    }
    
    public IntArray2(IntArray2 copyOf) {
        super(copyOf.sizeX, copyOf.sizeY);
        data = Arrays.copyOf(copyOf.data, copyOf.data.length);
    }

    /**
     * Returns an element at the given coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return the element at the coordinates
     */
    public int get(int x, int y) {
        return data[indexOf(x, y)];
    }

    /**
     * Sets an element at the given coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param value the new value
     */
    public void set(int x, int y, int value) {
        data[indexOf(x, y)] = value;
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
    public IntArray2 clone() {
        return new IntArray2(this);
    }

    @NotNull
    @Override
    public IntArrayIterator2 iterator() {
        return new IntArrayIterator2();
    }

    public final class IntArrayIterator2 implements Iterator<Integer> {
        
        private int index = 0;

        private IntArrayIterator2() {}

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
