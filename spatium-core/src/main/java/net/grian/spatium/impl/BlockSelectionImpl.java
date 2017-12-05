package net.grian.spatium.impl;

import net.grian.spatium.geo3.BlockSelection;
import net.grian.spatium.geo3.BlockVector;
import net.grian.spatium.geo3.Vector3;
import net.grian.spatium.iter.BlockIterator;
import eisenwave.spatium.util.PrimMath;

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
    
    @Override
    public Vector3 getCenter() {
        return Vector3.fromXYZ(
            (xmax-xmin) / 2D,
            (ymax-ymin) / 2D,
            (zmax-zmin) / 2D);
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
    public void setDimensions(int x, int y, int z) {
        //TODO implement
    }
    
    // TRANSFORMATIONS

    @Override
    public void translate(int x, int y, int z) {
        xmin += x;
        ymin += y;
        zmin += z;
        xmax += x;
        ymax += y;
        zmax += z;
    }

    @Override
    public void scale(double x, double y, double z) {
        if (x >= 0) {
            xmin *= x;
            xmax *= x;
        } else {
            xmin = (int) PrimMath.floor(xmax * x);
            xmax *= x;
        }
        if (y >= 0) {
            ymin *= y;
            ymax *= y;
        } else {
            ymin = (int) PrimMath.floor(ymax * y);
            ymax *= y;
        }
        if (z >= 0) {
            zmin *= z;
            zmax *= z;
        } else {
            zmin = (int) PrimMath.floor(zmax * z);
            zmax *= z;
        }
    }
    
    @Override
    public void grow(int x, int y, int z) {
        //TODO implement this
    }
    
    //MISC

    @Override
    public String toString() {
        return getClass().getSimpleName()+"{min="+getMin()+",max="+getMax()+"}";
    }

    @Override
    public BlockSelection clone() {
        return new BlockSelectionImpl(this);
    }

    @Override
    public Iterator<BlockVector> iterator() {
        return new BlockIterator(this);
    }

}
