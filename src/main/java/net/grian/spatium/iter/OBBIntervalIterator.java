package net.grian.spatium.iter;

import net.grian.spatium.array.Incrementer3;
import net.grian.spatium.geo3.OrientedBB;
import net.grian.spatium.geo3.Vector3;

import java.util.Iterator;

public class OBBIntervalIterator implements Iterator<Vector3> {
    
    private final Vector3 org, incrX, incrY, incrZ;
    private final Incrementer3 counter;
    
    /**
     * Constructs a new interval iterator.
     *
     * @param obb the oriented bounding box
     * @param ix the interval on the local x-axis
     * @param iy the interval on the local y-axis
     * @param iz the interval on the local z-axis
     */
    public OBBIntervalIterator(OrientedBB obb, double ix, double iy, double iz) {
        this.org = obb.getMin();
        this.incrX = obb.getAxisX().multiply(ix);
        this.incrY = obb.getAxisY().multiply(iy);
        this.incrZ = obb.getAxisZ().multiply(iz);
        this.counter = new Incrementer3(
            (int) (obb.getSizeX() / ix),
            (int) (obb.getSizeY() / iy),
            (int) (obb.getSizeZ() / iz));
    }
    
    /**
     * Constructs a new interval iterator.
     *
     * @param obb the oriented bounding box
     * @param interval the interval on the local axes
     */
    public OBBIntervalIterator(OrientedBB obb, double interval) {
        this(obb, interval, interval, interval);
    }
    
    @Override
    public boolean hasNext() {
        return counter.canIncrement();
    }
    
    @Override
    public Vector3 next() {
        int[] xyz = counter.current();
        Vector3 result = org.clone();
        scalarAdd(result, xyz[0], incrX);
        scalarAdd(result, xyz[1], incrY);
        scalarAdd(result, xyz[2], incrZ);
        counter.increment();
        return result;
    }
    
    private static void scalarAdd(Vector3 a, int r, Vector3 b) {
        a.add(b.getX()*r, b.getY()*r, b.getZ()*r);
    }
    
}
