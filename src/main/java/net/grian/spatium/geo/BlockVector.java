package net.grian.spatium.geo;

import net.grian.spatium.SpatiumObject;
import net.grian.spatium.impl.BlockVectorImpl;

import static net.grian.spatium.util.PrimMath.*;

public interface BlockVector extends SpatiumObject {

    public static BlockVector create() {
        return new BlockVectorImpl();
    }

    public static BlockVector fromXYZ(int x, int y, int z) {
        return new BlockVectorImpl(x, y, z);
    }

    public static BlockVector fromXYZ(double x, double y, double z) {
        return fromXYZ((int) floor(x), (int) floor(y), (int) floor(z));
    }

    public static BlockVector fromVector(Vector vector) {
        return new BlockVectorImpl(vector);
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

    public abstract BlockVector multiply(double x, double y, double z);

    public default BlockVector multiply(double factor) {
        return multiply(factor, factor, factor);
    }

    public abstract BlockVector divide(int x, int y, int z);

    public default BlockVector divide(int factor) {
        return divide(factor, factor, factor);
    }

    public abstract BlockVector divide(double x, double y, double z);

    public default BlockVector divide(double divisor) {
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

    public abstract BlockVector clone();

    /**
     * Converts these block coordinates into an array containing the x, y and z
     * coordinates in order.
     *
     * @return these coordinates in a new array
     */
    public default int[] toArray() {
        return new int[] {getX(), getY(), getZ()};
    }

}
