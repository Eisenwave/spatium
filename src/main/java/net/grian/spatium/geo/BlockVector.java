package net.grian.spatium.geo;

import net.grian.spatium.SpatiumObject;
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
	
	/**
	 * Returns the x-coordinate of the block.
	 * 
	 * @return the x-coordinate of the block
	 */
	public abstract int getX();
	
	/**
	 * Returns the y-coordinate of the block.
	 * 
	 * @return the y-coordinate of the block
	 */
	public abstract int getY();
	
	/**
	 * Returns the z-coordinate of the block.
	 * 
	 * @return the z-coordinate of the block
	 */
	public abstract int getZ();
	
	// SETTERS

	/**
	 * Sets all block coordinates.
	 * 
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 * @param z the z-coordinate
	 * @return itself
	 */
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
	
	/**
	 * Returns whether these coordinates are equal to other coordinates.
	 * 
	 * @param block the block
	 * @return whether these and the coordinates of the block are equal
	 */
	public default boolean equals(BlockVector block) {
		return
				this.getX() == block.getX() &&
				this.getY() == block.getY() &&
				this.getZ() == block.getZ();
	}
	
	// MISC
	
	/**
	 * Returns a copy of these block coordinates.
	 * 
	 * @return a copy of these block coordinates.
	 */
	public abstract BlockVector clone();
	
	/**
	 * Converts these block coordinates into an array containing the x, y and z
	 * coordinates in order.
	 * 
	 * @return these coordinates in a new array
	 */
	public abstract int[] toArray();

}