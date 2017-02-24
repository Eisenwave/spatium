package net.grian.spatium.geo3;

import net.grian.spatium.impl.BlockVectorImpl;

import java.io.Serializable;

import static net.grian.spatium.util.PrimMath.*;

public interface BlockVector extends Serializable, Cloneable {

    public static BlockVector create() {
        return new BlockVectorImpl();
    }

    public static BlockVector fromXYZ(int x, int y, int z) {
        return new BlockVectorImpl(x, y, z);
    }

    public static BlockVector fromXYZ(double x, double y, double z) {
        return fromXYZ((int) floor(x), (int) floor(y), (int) floor(z));
    }

    public static BlockVector fromVector(Vector3 vector) {
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
     */
    public abstract void set(int x, int y, int z);

    public abstract void setX(int x);

    public abstract void setY(int y);

    public abstract void setZ(int z);

    public abstract void add(int x, int y, int z);

    public default void add(BlockVector block) {
        add(block.getX(), block.getY(), block.getZ());
    }

    public abstract void subtract(int x, int y, int z);

    public default void subtract(BlockVector block) {
        subtract(block.getX(), block.getY(), block.getZ());
    }

    public abstract void multiply(int x, int y, int z);

    public default void multiply(int factor) {
        multiply(factor, factor, factor);
    }

    public abstract void multiply(double x, double y, double z);

    public default void multiply(double factor) {
        multiply(factor, factor, factor);
    }

    public abstract void divide(int x, int y, int z);

    public default void divide(int factor) {
        divide(factor, factor, factor);
    }

    public abstract void divide(double x, double y, double z);

    public default void divide(double divisor) {
        divide(divisor, divisor, divisor);
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
