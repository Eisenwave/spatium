package net.grian.spatium.array;

import net.grian.spatium.util.PrimMath;

import java.util.Iterator;

public class BooleanArray2 extends AbstractArray2 implements Iterable<Boolean> {

    private final byte[][] content;

    public BooleanArray2(int x, int y) {
        super(x, y);
        final int lengthY = PrimMath.floorAsInt((float) y/Byte.SIZE);
        this.content = new byte[x][lengthY];
    }

    public boolean get(int x, int y) {
        return (content[x][y/Byte.SIZE] >> (y%Byte.SIZE)) == 1;
    }

    public void set(int x, int y, boolean value) {
        if (value) enable(x, y);
        else disable(x, y);
    }

    public void enable(int x, int y) {
        content[x][y/Byte.SIZE] |= (1 << (y%Byte.SIZE));
    }

    public void disable(int x, int y) {
        content[x][y/Byte.SIZE] &= ~(1 << (y%Byte.SIZE));
    }

    @Override
    public BooleanArrayIterator2 iterator() {
        return new BooleanArrayIterator2();
    }

    private final class BooleanArrayIterator2 implements Iterator<Boolean> {

        private final Incrementer2 incrementer = new Incrementer2(sizeX, sizeY);

        private BooleanArrayIterator2() {}

        @Override
        public boolean hasNext() {
            return incrementer.canIncrement();
        }

        @Override
        public Boolean next() {
            return nextBoolean();
        }

        public boolean nextBoolean() {
            int[] pos = incrementer.current();
            boolean result = get(pos[0], pos[1]);
            incrementer.increment();

            return result;
        }

    }

}
