package net.grian.spatium.array;

import net.grian.spatium.geo3.BlockVector;
import net.grian.spatium.util.PrimMath;
import net.grian.spatium.voxel.BitArray3;

import java.util.Iterator;

public class BooleanArray3 extends AbstractArray3 implements BitArray3, Iterable<Boolean>, Cloneable {

    private final byte[][][] content;

    public BooleanArray3(int x, int y, int z) {
        super(x, y, z);
        final int lengthY = PrimMath.ceil((float) y/Byte.SIZE);
        this.content = new byte[x][lengthY][z];
    }

    public BooleanArray3(BitArray3 copyOf) {
        this(copyOf.getSizeX(), copyOf.getSizeY(), copyOf.getSizeZ());

        for (int x = 0; x<sizeX; x++) for (int y = 0; y<sizeY; y++) for (int z = 0; z<sizeZ; z++)
            if (copyOf.contains(x, y, z)) enable(x, y, z);
    }

    @Override
    public boolean contains(int x, int y, int z) {
        return get(x, y, z);
    }

    public boolean get(int x, int y, int z) {
        return ((content[x][y/Byte.SIZE][z] >> (y%Byte.SIZE)) & 1) == 1;
    }

    public void set(int x, int y, int z, boolean value) {
        if (value) enable(x, y, z);
        else disable(x, y, z);
    }

    public void enable(int x, int y, int z) {
        content[x][y/Byte.SIZE][z] |= (1 << (y%Byte.SIZE));
    }

    public void enable(BlockVector pos) {
        enable(pos.getX(), pos.getY(), pos.getZ());
    }

    public void disable(int x, int y, int z) {
        content[x][y/Byte.SIZE][z] &= ~(1 << (y%Byte.SIZE));
    }

    public void disable(BlockVector pos) {
        disable(pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public BooleanArrayIterator3 iterator() {
        return new BooleanArrayIterator3();
    }

    @Override
    public BooleanArray3 clone() {
        return new BooleanArray3(this);
    }

    private final class BooleanArrayIterator3 implements Iterator<Boolean> {

        private final Incrementer3 incrementer = new Incrementer3(sizeX, sizeY, sizeZ);

        private BooleanArrayIterator3() {}

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
            boolean result = get(pos[0], pos[1], pos[2]);
            incrementer.increment();

            return result;
        }

    }

}
