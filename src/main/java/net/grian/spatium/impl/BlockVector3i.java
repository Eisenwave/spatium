package net.grian.spatium.impl;

import java.util.Arrays;

import net.grian.spatium.enums.Direction;
import net.grian.spatium.geo.BlockVector;
import net.grian.spatium.geo.Vector;
import net.grian.spatium.util.PrimMath;

public class BlockVector3i implements BlockVector {
	
	private static final long serialVersionUID = -9066407510526907382L;
	
	private int x,y,z;
	
	public BlockVector3i(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public BlockVector3i(Vector v) {
		this.x = PrimMath.floorAsInt(v.getX());
		this.y = PrimMath.floorAsInt(v.getY());
		this.z = PrimMath.floorAsInt(v.getZ());
	}
	
	public BlockVector3i() {
		this(0,0,0);
	}
	
	public BlockVector3i(BlockVector3i coords) {
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
	public BlockVector3i add(int x, int y, int z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}
	
	@Override
	public BlockVector3i subtract(int x, int y, int z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}
	
	@Override
	public BlockVector3i multiply(int x, int y, int z) {
		this.x *= x;
		this.y *= y;
		this.z *= z;
		return this;
	}
	
	@Override
	public BlockVector3i multiply(float x, float y, float z) {
		this.x *= x;
		this.y *= y;
		this.z *= z;
		return this;
	}
	
	@Override
	public BlockVector3i divide(int x, int y, int z) {
		this.x *= x;
		this.y *= y;
		this.z *= z;
		return this;
	}
	
	@Override
	public BlockVector3i divide(float x, float y, float z) {
		this.x /= x;
		this.y /= y;
		this.z /= z;
		return this;
	}
	
	public BlockVector3i getRelative(Direction direction, int offset) {
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
	public BlockVector3i clone() {
		return new BlockVector3i(this);
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
		return this.getClass().getName()+"["+x+","+y+","+z+"]";
	}

}
