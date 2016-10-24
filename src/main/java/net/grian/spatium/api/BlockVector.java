package net.grian.spatium.api;

import net.grian.spatium.impl.BlockVector3i;

public interface BlockVector extends SpatiumObject {
	
	public static BlockVector create() {
		return new BlockVector3i();
	}
	
	public static BlockVector fromXYZ(int x, int y, int z) {
		return new BlockVector3i(x, y, z);
	}
	
	public static BlockVector fromVector(Vector vector) {
		return new BlockVector3i(vector);
	}
	
	// GETTERS
	
	public abstract int getX();
	
	public abstract int getY();
	
	public abstract int getZ();
	
	// SETTERS
	
	public abstract BlockVector set(int x, int y, int z);
	
	public abstract BlockVector setX(int x);
	
	public abstract BlockVector setY(int y);
	
	public abstract BlockVector setZ(int z);
	
	public abstract BlockVector add(int x, int y, int z);
	
	public default BlockVector add(BlockVector block) {
		return add(block.getX(), block.getY(), block.getZ());
	}
	
	public abstract BlockVector subtract(int x, int y, int z);
	
	public default BlockVector subtract(BlockVector block) {
		return subtract(block.getX(), block.getY(), block.getZ());
	}
	
	public abstract BlockVector multiply(int x, int y, int z);
	
	public default BlockVector multiply(int factor) {
		return multiply(factor, factor, factor);
	}
	
	public abstract BlockVector multiply(float x, float y, float z);
	
	public default BlockVector multiply(float factor) {
		return multiply(factor, factor, factor);
	}
	
	public abstract BlockVector divide(int x, int y, int z);
	
	public default BlockVector divide(int factor) {
		return divide(factor, factor, factor);
	}
	
	public abstract BlockVector divide(float x, float y, float z);
	
	public default BlockVector divide(float divisor) {
		return divide(divisor, divisor, divisor);
	}
	
	// CHECKERS
	
	public default boolean equals(BlockVector vector) {
		return
				this.getX() == vector.getX() &&
				this.getY() == vector.getY() &&
				this.getZ() == vector.getZ();
	}
	
	// MISC
	
	public abstract BlockVector clone();
	
	public abstract int[] toArray();

}
