package net.grian.spatium.iter;

import net.grian.spatium.array.Incrementer3;
import net.grian.spatium.geo.BlockSelection;
import net.grian.spatium.geo.BlockVector;

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
        int[] block = increment.current();
        increment.increment();
        return BlockVector.fromXYZ(block[0], block[1], block[2]);
    }
}
