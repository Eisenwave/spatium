package net.grian.spatium.impl;

import net.grian.spatium.enums.Direction;
import net.grian.spatium.geo.BlockVector;
import net.grian.spatium.geo.Vector;
import net.grian.spatium.util.PrimMath;

import java.util.Arrays;

public class BlockVectorImpl implements BlockVector {

    private static final long serialVersionUID = -9066407510526907382L;

    private int x,y,z;

    public BlockVectorImpl(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BlockVectorImpl(Vector v) {
        this.x = PrimMath.floor(v.getX());
        this.y = PrimMath.floor(v.getY());
        this.z = PrimMath.floor(v.getZ());
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

    // SETTERS

    @Override
    public BlockVector set(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    @Override
    public BlockVector setX(int x) {
        this.x = x;
        return this;
    }

    @Override
    public BlockVector setY(int y) {
        this.y = y;
        return this;
    }

    @Override
    public BlockVector setZ(int z) {
        this.z = z;
        return this;
    }

    @Override
    public BlockVectorImpl add(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    @Override
    public BlockVectorImpl subtract(int x, int y, int z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    @Override
    public BlockVectorImpl multiply(int x, int y, int z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    @Override
    public BlockVectorImpl multiply(float x, float y, float z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    @Override
    public BlockVectorImpl divide(int x, int y, int z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    @Override
    public BlockVectorImpl divide(float x, float y, float z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }

    public BlockVectorImpl getRelative(Direction direction, int offset) {
        switch(direction) {
        case POSITIVE_X: return add( offset, 0, 0);
        case NEGATIVE_X: return add(-offset, 0, 0);
        case POSITIVE_Y: return add(0,  offset, 0);
        case NEGATIVE_Y: return add(0, -offset, 0);
        case POSITIVE_Z: return add(0, 0,  offset);
        case NEGATIVE_Z: return add(0, 0, -offset);
        default: return null;
        }
    }

    // CHECKERS

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BlockVector && equals((BlockVector) obj);
    }

    // MISC

    @Override
    public BlockVectorImpl clone() {
        return new BlockVectorImpl(this);
    }

    public int[] toArray() {
        return new int[] {x, y, z};
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(toArray());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()+"["+x+","+y+","+z+"]";
    }

}
