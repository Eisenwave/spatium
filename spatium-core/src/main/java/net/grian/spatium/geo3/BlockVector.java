package net.grian.spatium.geo3;

import net.grian.spatium.impl.BlockVectorImpl;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

import static net.grian.spatium.util.PrimMath.*;

public interface BlockVector extends Serializable, Cloneable {
    
    @NotNull
    static BlockVector create() {
        return new BlockVectorImpl();
    }
    
    @NotNull
    static BlockVector fromXYZ(int x, int y, int z) {
        return new BlockVectorImpl(x, y, z);
    }
    
    @NotNull
    static BlockVector fromXYZ(double x, double y, double z) {
        return fromXYZ((int) floor(x), (int) floor(y), (int) floor(z));
    }
    
    @NotNull
    static BlockVector fromVector(Vector3 vector) {
        return new BlockVectorImpl(vector);
    }

    // GETTERS

    /**
     * Returns the x-coordinate of the block.
     *
     * @return the x-coordinate of the block
     */
    abstract int getX();

    /**
     * Returns the y-coordinate of the block.
     *
     * @return the y-coordinate of the block
     */
    abstract int getY();

    /**
     * Returns the z-coordinate of the block.
     *
     * @return the z-coordinate of the block
     */
    abstract int getZ();
    
    default int dot(int x, int y, int z) {
        return getX()*x + getY()*y + getZ()*z;
    }
    
    default int dot(BlockVector v) {
        return dot(v.getX(), v.getY(), v.getZ());
    }
    
    // PREDICATES
    
    /**
     * Returns whether these coordinates are equal to other coordinates.
     *
     * @param block the block
     * @return whether these and the coordinates of the block are equal
     */
    default boolean equals(BlockVector block) {
        return
            this.getX() == block.getX() &&
            this.getY() == block.getY() &&
            this.getZ() == block.getZ();
    }
    
    default boolean isZero() {
        return getX()==0 && getY()==0 && getZ()==0;
    }

    // MUTATORS

    /**
     * Sets all block coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     */
    abstract void set(int x, int y, int z);

    abstract void setX(int x);

    abstract void setY(int y);

    abstract void setZ(int z);
    
    // OPERATIONS

    abstract BlockVector add(int x, int y, int z);

    default BlockVector add(BlockVector block) {
        return add(block.getX(), block.getY(), block.getZ());
    }

    abstract BlockVector subtract(int x, int y, int z);

    default BlockVector subtract(BlockVector block) {
        return subtract(block.getX(), block.getY(), block.getZ());
    }

    abstract BlockVector multiply(int x, int y, int z);

    default BlockVector multiply(int factor) {
        return multiply(factor, factor, factor);
    }

    abstract BlockVector multiply(double x, double y, double z);

    default BlockVector multiply(double factor) {
        return multiply(factor, factor, factor);
    }

    abstract BlockVector divide(int x, int y, int z);

    default BlockVector divide(int factor) {
        return divide(factor, factor, factor);
    }

    abstract BlockVector divide(double x, double y, double z);

    default BlockVector divide(double divisor) {
        return divide(divisor, divisor, divisor);
    }
    
    default BlockVector negate() {
        return multiply(-1);
    }

    // MISC
    
    default Vector3 toVector() {
        return Vector3.fromBlock(this);
    }

    abstract BlockVector clone();

    /**
     * Converts these block coordinates into an array containing the x, y and z
     * coordinates in order.
     *
     * @return these coordinates in a new array
     */
    default int[] toArray() {
        return new int[] {getX(), getY(), getZ()};
    }

}
