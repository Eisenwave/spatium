package net.grian.spatium.iter;

import net.grian.spatium.geo.BlockSelection;
import net.grian.spatium.geo.BlockVector;
import net.grian.spatium.util.ArrayMath;

import java.util.Iterator;

public class BlockIterator implements Iterator<BlockVector> {

    private int i = 0, x, y, z;
    private final int limit;

    public BlockIterator(BlockSelection blocks) {
        this.x = blocks.getSizeX();
        this.y = blocks.getSizeY();
        this.z = blocks.getSizeZ();
        this.limit = blocks.getBlockCount();
    }

    @Override
    public boolean hasNext() {
        return i < limit;
    }

    @Override
    public BlockVector next() {
        int[] block = ArrayMath.indexToCoords3D(i++, x, y, z);
        return BlockVector.fromXYZ(block[0], block[1], block[2]);
    }
}
