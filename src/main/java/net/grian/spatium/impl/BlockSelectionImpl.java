package net.grian.spatium.impl;

import net.grian.spatium.geo.BlockSelection;
import net.grian.spatium.geo.BlockVector;
import net.grian.spatium.util.ArrayMath;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class BlockSelectionImpl implements BlockSelection {

    private int xmin, ymin, zmin, xmax, ymax, zmax;

    public BlockSelectionImpl(int xa, int ya, int za, int xb, int yb, int zb) {
        this.xmin = Math.min(xa, xb);
        this.ymin = Math.min(ya, yb);
        this.zmin = Math.min(za, zb);
        this.xmax = Math.max(xa, xb);
        this.ymax = Math.max(ya, yb);
        this.zmax = Math.max(za, zb);
    }

    private BlockSelectionImpl(BlockSelectionImpl copyOf) {
        this.xmin = copyOf.xmin;
        this.ymin = copyOf.ymin;
        this.zmin = copyOf.zmin;
        this.xmax = copyOf.xmax;
        this.ymax = copyOf.ymax;
        this.zmax = copyOf.zmax;
    }

    // GETTERS

    @Override
    public int getMinX() {
        return xmin;
    }

    @Override
    public int getMinY() {
        return ymin;
    }

    @Override
    public int getMinZ() {
        return zmin;
    }

    @Override
    public int getMaxX() {
        return xmax;
    }

    @Override
    public int getMaxY() {
        return ymax;
    }

    @Override
    public int getMaxZ() {
        return zmax;
    }

    @Override
    public int getSizeX() {
        return xmax - xmin + 1;
    }

    @Override
    public int getSizeY() {
        return ymax - ymin + 1;
    }

    @Override
    public int getSizeZ() {
        return zmax - zmin + 1;
    }

    // CHECKERS


    @Override
    public boolean equals(Object obj) {
        return obj instanceof BlockSelection && equals((BlockSelection) obj);
    }

    @Override
    public boolean contains(int x, int y, int z) {
        return
                x >= xmin && x <= xmax &&
                y >= ymin && y <= ymax &&
                z >= zmin && z <= zmax;
    }

    // SETTERS

    @Override
    public BlockSelection move(int x, int y, int z) {
        xmin += x;
        ymin += y;
        zmin += z;
        xmax += x;
        ymax += y;
        zmax += z;
        concurrentModification = true;
        return this;
    }

    @Override
    public BlockSelection scale(int x, int y, int z) {
        concurrentModification = true;
        //TODO implement this
        return this;
    }

    @Override
    public BlockSelection grow(int x, int y, int z) {
        concurrentModification = true;
        //TODO implement this
        return this;
    }

    @Override
    public BlockSelection clone() {
        return new BlockSelectionImpl(this);
    }

    @Override
    public Iterator<BlockVector> iterator() {
        return new BlockIterator();
    }

    protected boolean concurrentModification = false;

    private class BlockIterator implements Iterator<BlockVector> {

        private boolean first = true;
        private int index = 0, x = getSizeX(), y = getSizeY(), z = getSizeZ();
        private final int limit = getVolume();

        @Override
        public boolean hasNext() {
            return index < limit;
        }

        @Override
        public BlockVector next() {
            if (first) {
                concurrentModification = false;
                first = false;
            }
            if (concurrentModification) //block modified throughout iteration
                throw new ConcurrentModificationException();
            int[] block = ArrayMath.indexToCoords3D(index, x, y, z);
            return BlockVector.fromXYZ(block[0], block[1], block[2]);
        }
    }

}