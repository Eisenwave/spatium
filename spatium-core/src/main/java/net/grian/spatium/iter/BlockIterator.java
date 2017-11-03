package net.grian.spatium.iter;

import net.grian.spatium.geo3.BlockSelection;
import net.grian.spatium.geo3.BlockVector;
import net.grian.spatium.util.Incrementer3;

import java.util.Iterator;

public class BlockIterator implements Iterator<BlockVector> {

    private final Incrementer3 increment;

    public BlockIterator(BlockSelection blocks) {
        this.increment = new Incrementer3(blocks.getSizeX(), blocks.getSizeY(), blocks.getSizeZ());
    }

    @Override
    public boolean hasNext() {
        return increment.canIncrement();
    }

    @Override
    public BlockVector next() {
        int[] block = increment.getAndIncrement();
        return BlockVector.fromXYZ(block[0], block[1], block[2]);
    }
}
