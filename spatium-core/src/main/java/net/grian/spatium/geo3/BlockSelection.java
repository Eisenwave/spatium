package net.grian.spatium.geo3;

import net.grian.spatium.impl.BlockSelectionImpl;
import net.grian.spatium.util.PrimMath;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.function.Consumer;

import static net.grian.spatium.util.PrimMath.*;

public interface BlockSelection extends Space, Iterable<BlockVector>, Serializable, Cloneable {

    /**
     * Creates a new bounding box between two points.
     *
     * @param xa the x of the first point
     * @param ya the y of the first point
     * @param za the z of the first point
     * @param xb the x of the second point
     * @param yb the y of the second point
     * @param zb the z of the second point
     * @return a new block selection
     */
    @NotNull
    public static BlockSelection fromPoints(int xa, int ya, int za, int xb, int yb, int zb) {
        return new BlockSelectionImpl(xa, ya, za, xb, yb, zb);
    }

    /**
     * Creates a new cubical axis aligned bounding box around a center and of a set size. Each side of the cube will
     * be twice as long as the specified size.
     *
     * @param center the center of the cube
     * @param size the size of the cube
     * @return a new block selection
     */
    @NotNull
    public static BlockSelection createCube(BlockVector center, int size) {
        return new BlockSelectionImpl(
            center.getX() - size,
            center.getY() - size,
            center.getZ() - size,
            center.getX() + size,
            center.getY() + size,
            center.getZ() + size);
    }

    /**
     * Creates a new bounding box between two points.
     *
     * @param from the first point
     * @param to the second point
     * @return a new block selection
     */
    @NotNull
    public static BlockSelection between(BlockVector from, BlockVector to) {
        return fromPoints(from.getX(), from.getY(), from.getZ(), to.getX(), to.getY(), to.getZ());
    }

    // GETTERS

    /**
     * Returns the minimum x-coordinate of this selection.
     *
     * @return the minimum x-coordinate of this selection
     */
    public int getMinX();

    /**
     * Returns the minimum y-coordinate of this selection.
     *
     * @return the minimum y-coordinate of this selection
     */
    public int getMinY();

    /**
     * Returns the minimum z-coordinate of this selection.
     *
     * @return the minimum z-coordinate of this selection
     */
    public int getMinZ();

    /**
     * Returns the maximum x-coordinate of this selection.
     *
     * @return the maximum x-coordinate of this selection
     */
    public int getMaxX();

    /**
     * Returns the maximum y-coordinate of this selection.
     *
     * @return the maximum y-coordinate of this selection
     */
    public int getMaxY();

    /**
     * Returns the maximum z-coordinate of this selection.
     *
     * @return the maximum z-coordinate of this selection
     */
    public int getMaxZ();

    public default BlockVector getMin() {
        return BlockVector.fromXYZ(getMinX(), getMinY(), getMinZ());
    }

    public default BlockVector getMax() {
        return BlockVector.fromXYZ(getMaxX(), getMaxY(), getMaxZ());
    }

    /**
     * Returns the size of the block selection on the x-axis. This will be at least one block.
     *
     * @return the size of the block selection on the x-axis
     */
    public default int getSizeX() {
        return getMaxX() - getMinX() + 1;
    }

    /**
     * Returns the size of the block selection on the y-axis. This will be at least one block.
     *
     * @return the size of the block selection on the y-axis
     */
    public default int getSizeY() {
        return getMaxY() - getMinY() + 1;
    }

    /**
     * Returns the size of the block selection on the z-axis. This will be at least one block.
     *
     * @return the size of the block selection on the z-axis
     */
    public default int getSizeZ() {
        return getMaxZ() - getMinZ() + 1;
    }

    /**
     * Returns the dimensions of the bounding box in a new vector.
     *
     * @return the dimensions of the bounding box in a new vector
     */
    public default BlockVector getDimensions() {
        return BlockVector.fromXYZ(getSizeX(), getSizeY(), getSizeZ());
    }

    /**
     * Returns the volume of the bounding box in blocks.
     *
     * @return the volume of the bounding box in blocks
     */
    public default int getBlockCount() {
        return getSizeX() * getSizeY() * getSizeZ();
    }

    @Override
    public default double getVolume() {
        return getBlockCount();
    }

    @Override
    public default double getSurfaceArea() {
        int x = getSizeX(), y = getSizeY(), z = getSizeZ();
        return (x * y + x * z + y * z) * 2;
    }

    // CHECKERS

    public default boolean equals(BlockSelection blocks) {
        return
            this.getMinX() == blocks.getMinX() &&
            this.getMinY() == blocks.getMinY() &&
            this.getMinZ() == blocks.getMinZ() &&
            this.getMaxX() == blocks.getMaxX() &&
            this.getMaxY() == blocks.getMaxY() &&
            this.getMaxZ() == blocks.getMaxZ();
    }

    /**
     * Returns whether this block selection contains a block of given coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return whether this block selection contains the block
     */
    public default boolean contains(int x, int y, int z) {
        return
            x >= getMinX() && x <= getMaxX() &&
            y >= getMinY() && y <= getMaxY() &&
            z >= getMinZ() && z <= getMaxZ();
    }

    /**
     * Returns whether this block selection is equal to or contains another selection.
     *
     * @param box the other selection
     * @return whether this block selection contains another
     */
    public default boolean contains(BlockSelection box) {
        return
            box.getMinX() >= this.getMinX() && box.getMaxX() <= this.getMaxX() &&
            box.getMinY() >= this.getMinY() && box.getMaxY() <= this.getMaxY() &&
            box.getMinZ() >= this.getMinZ() && box.getMaxZ() <= this.getMaxZ();
    }

    /**
     * Returns whether this block selection contains a block of given coordinates.
     *
     * @param block the block
     * @return whether this block selection contains the block
     */
    public default boolean contains(BlockVector block) {
        return contains(block.getX(), block.getY(), block.getZ());
    }

    /**
     * Returns whether this bounding box contains a point of given coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return whether this bounding box contains a point of given coordinates
     */
    public default boolean contains(double x, double y, double z) {
        return contains((int) floor(x), (int) floor(y), (int) floor(z));
    }

    /**
     * Returns whether this bounding box contains a point of given coordinates.
     *
     * @param point the point
     * @return whether this bounding box contains a point of given coordinates
     */
    public default boolean contains(Vector3 point) {
        return contains(point.getX(), point.getY(), point.getZ());
    }

    // SETTERS
    
    abstract void setDimensions(int x, int y, int z);
    
    // TRANSFORMATIONS

    /**
     * Moves the block selection.
     *
     * @param x the displacement in blocks on the x-axis
     * @param y the displacement in blocks on the y-axis
     * @param z the displacement in blocks on the z-axis
     */
    public abstract void translate(int x, int y, int z);
    
    @Override
    default void translate(double x, double y, double z) {
        translate((int) PrimMath.floor(x), (int) PrimMath.floor(x), (int) PrimMath.floor(x));
    }

    /**
     * Moves the block selection.
     *
     * @param v a block vector representing the movement in blocks on x, y, z axes.
     */
    public default void translate(BlockVector v) {
        translate(v.getX(), v.getY(), v.getZ());
    }
    
    public abstract void scale(double x, double y, double z);
    
    public default void scale(BlockVector v) {
        scale(v.getX(), v.getY(), v.getZ());
    }
    
    @Override
    default void scale(double factor) {
        scale(factor, factor,factor);
    }
    
    default void scaleCentric(double x, double y, double z) {
        setDimensions(
            (int) PrimMath.floor(getSizeX()*x),
            (int) PrimMath.floor(getSizeY()*y),
            (int) PrimMath.floor(getSizeZ()*z));
    }
    
    @Override
    default void scaleCentric(double factor) {
        scaleCentric(factor, factor, factor);
    }
    
    public abstract void grow(int x, int y, int z);

    public default void grow(BlockVector v) {
        grow(v.getX(), v.getY(), v.getZ());
    }

    // MISC

    /**
     * Performs an operation for every block in this selection.
     *
     * @param action the operation to perform
     */
    @Override
    public default void forEach(Consumer<? super BlockVector> action) {
        final int limX = getMaxX(), limY = getMaxY(), limZ = getMaxZ();
        
        for (int x = getMinX(); x < limX; x++)
            for (int y = getMinY(); y < limY; y++)
                for (int z = getMinZ(); z < limZ; z++)
                    BlockVector.fromXYZ(x, y, z);
    }

    /**
     * Performs an operation for every block in this selection.
     *
     * @param action the operation to perform
     */
    /* public default void forEach(Int3Consumer action) {
        final int limX = getMaxX(), limY = getMaxY(), limZ = getMaxZ();

        for (int x = getMinX(); x < limX; x++)
            for (int y = getMinY(); y < limY; y++)
                for (int z = getMinZ(); z < limZ; z++)
                    action.accept(x, y, z);
    } */

    /**
     * Converts this block selection into an axis aligned bounding box which will surround the selection.
     *
     * @return a new axis aligned bounding box
     */
    public default AxisAlignedBB toBoundingBox() {
        return AxisAlignedBB.fromPoints(getMinX(), getMinY(), getMinZ(), getMaxX()+1, getMaxY()+1, getMaxZ()+1);
    }

    public abstract BlockSelection clone();

}
