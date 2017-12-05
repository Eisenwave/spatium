package net.grian.spatium.impl;

import eisenwave.spatium.enums.Direction;
import net.grian.spatium.geo3.BlockVector;
import net.grian.spatium.geo3.Vector3;
import eisenwave.spatium.util.PrimMath;

import java.util.Arrays;

public class BlockVectorImpl implements BlockVector {

    private static final long serialVersionUID = -9066407510526907382L;

    private int x,y,z;

    public BlockVectorImpl(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BlockVectorImpl(Vector3 v) {
        this.x = (int) PrimMath.floor(v.getX());
        this.y = (int) PrimMath.floor(v.getY());
        this.z = (int) PrimMath.floor(v.getZ());
    }

    public BlockVectorImpl() {
        this(0,0,0);
    }

    public BlockVectorImpl(BlockVectorImpl coords) {
        this(coords.x, coords.y, coords.z);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getZ() {
        return z;
    }
    
    public BlockVectorImpl getRelative(Direction direction, int offset) {
        BlockVectorImpl clone = clone();
        switch(direction) {
            case POSITIVE_X: clone.add( offset, 0, 0); break;
            case NEGATIVE_X: clone.add(-offset, 0, 0); break;
            case POSITIVE_Y: clone.add(0,  offset, 0); break;
            case NEGATIVE_Y: clone.add(0, -offset, 0); break;
            case POSITIVE_Z: clone.add(0, 0,  offset); break;
            case NEGATIVE_Z: clone.add(0, 0, -offset); break;
        }
        return clone;
    }
    
    // CHECKERS
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof BlockVector && equals((BlockVector) obj);
    }

    // SETTERS

    @Override
    public void set(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void setZ(int z) {
        this.z = z;
    }

    // OPERATIONS
    
    @Override
    public BlockVector add(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    @Override
    public BlockVector subtract(int x, int y, int z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    @Override
    public BlockVector multiply(int x, int y, int z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    @Override
    public BlockVector multiply(double x, double y, double z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    @Override
    public BlockVector divide(int x, int y, int z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    @Override
    public BlockVector divide(double x, double y, double z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }
    
    @Override
    public BlockVector negate() {
        this.x = -x;
        this.y = -y;
        this.z = -z;
        return this;
    }
    
    // MISC

    @Override
    public BlockVectorImpl clone() {
        return new BlockVectorImpl(this);
    }

    @Override
    public int[] toArray() {
        return new int[] {x, y, z};
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(toArray());
    }

    @Override
    public String toString() {
        return "("+x+","+y+","+z+")";
    }

}
