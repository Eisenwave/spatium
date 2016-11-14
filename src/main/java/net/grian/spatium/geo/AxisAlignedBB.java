package net.grian.spatium.geo;

import net.grian.spatium.Spatium;
import net.grian.spatium.impl.AxisAlignedBBImpl;

/**
 * An axis aligned bounding box, or the cubical space between two points.
 */
public interface AxisAlignedBB extends Space {

    /**
     * Creates a new bounding box between two points.
     *
     * @param xa the x of the first point
     * @param ya the y of the first point
     * @param za the z of the first point
     * @param xb the x of the second point
     * @param yb the y of the second point
     * @param zb the z of the second point
     * @return a new bounding box
     */
    public static AxisAlignedBB fromPoints(float xa, float ya, float za, float xb, float yb, float zb) {
        return new AxisAlignedBBImpl(xa, ya, za, xb, yb, zb);
    }

    /**
     * Creates a new cubical axis aligned bounding box around a center and of a set size. Each side of the cube will
     * be twice as long as the specified size.
     *
     * @param center the center of the cube
     * @param size the size of the cube
     * @return a new axis aligned bounding box
     */
    public static AxisAlignedBB createCube(Vector center, float size) {
        return new AxisAlignedBBImpl(
                center.getX() - size,
                center.getY() - size,
                center.getZ() - size,
                center.getX() + size,
                center.getY() + size,
                center.getZ() + size
                );
    }

    /**
     * Creates a new bounding box between two points.
     *
     * @param from the first point
     * @param to the second point
     * @return a new bounding box
     */
    public static AxisAlignedBB between(Vector from, Vector to) {
        return fromPoints(from.getX(), from.getY(), from.getZ(), to.getX(), to.getY(), to.getZ());
    }

    // GETTERS

    public abstract float getMinX();

    public abstract float getMinY();

    public abstract float getMinZ();

    public abstract float getMaxX();

    public abstract float getMaxY();

    public abstract float getMaxZ();

    public default float getSizeX() {
        return getMaxX() - getMinX();
    }

    public default float getSizeY() {
        return getMaxY() - getMinY();
    }

    public default float getSizeZ() {
        return getMaxZ() - getMinZ();
    }

    public default Vector getCenter() {
        return Vector.fromXYZ(
                (getMinX() + getMaxX()) * 0.5f,
                (getMinY() + getMaxY()) * 0.5f,
                (getMinZ() + getMaxZ()) * 0.5f);
    }

    public default Vector getMin() {
        return Vector.fromXYZ(getMinX(), getMinY(), getMinZ());
    }

    public default Vector getMax() {
        return Vector.fromXYZ(getMaxX(), getMaxY(), getMaxZ());
    }

    /**
     * Returns the dimensions of the bounding box in a new vector.
     *
     * @return the dimensions of the bounding box in a new vector
     */
    public default Vector getDimensions() {
        return Vector.fromXYZ(getSizeX(), getSizeY(), getSizeZ());
    }

    @Override
    public default float getVolume() {
        return getSizeX() * getSizeY() * getSizeZ();
    }

    @Override
    public default float getSurfaceArea() {
        float x = getSizeX(), y = getSizeY(), z = getSizeZ();
        return (x * y + x * z + y * z) * 2;
    }

    // CHECKERS

    public default boolean equals(AxisAlignedBB box) {
        return
                Spatium.equals(this.getMinX(), box.getMinX()) &&
                Spatium.equals(this.getMinY(), box.getMinY()) &&
                Spatium.equals(this.getMinZ(), box.getMinZ()) &&
                Spatium.equals(this.getMaxX(), box.getMaxX()) &&
                Spatium.equals(this.getMaxY(), box.getMaxY()) &&
                Spatium.equals(this.getMaxZ(), box.getMaxZ());
    }

    /**
     * Returns whether this bounding box contains a point of given coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return whether this bounding box contains a point of given coordinates
     */
    public default boolean contains(float x, float y, float z) {
        return
                x >= getMinX() && x <= getMaxX() &&
                y >= getMinY() && y <= getMaxY() &&
                z >= getMinZ() && z <= getMaxZ();
    }

    /**
     * Returns whether this bounding box contains a point of given coordinates.
     *
     * @param point the point
     * @return whether this bounding box contains a point of given coordinates
     */
    public default boolean contains(Vector point) {
        return contains(point.getX(), point.getY(), point.getZ());
    }

    // SETTERS

    /**
     * Moves the bounding box by a given amount.
     *
     * @param x the amount of x-movement
     * @param y the amount of y-movement
     * @param z the amount of z-movement
     * @return itself
     */
    public abstract AxisAlignedBB move(float x, float y, float z);

    /**
     * Moves the bounding box by a given amount.
     *
     * @param v the movement
     * @return itself
     */
    public default AxisAlignedBB move(Vector v) {
        return move(v.getX(), v.getY(), v.getZ());
    }

    /**
     * Scales the bounding box around the origin.
     *
     * @param x the x-scale factor
     * @param y the y-scale factor
     * @param z the z-scale factor
     * @return itself
     */
    public abstract AxisAlignedBB scale(float x, float y, float z);

    /**
     * Scales the bounding box around the origin.
     *
     * @param v the scale factors
     * @return itself
     */
    public default AxisAlignedBB scale(Vector v) {
        return scale(v.getX(), v.getY(), v.getZ());
    }

    /**
     * Scales the bounding box around the origin.
     *
     * @param factor the scale factor
     * @return itself
     */
    public default AxisAlignedBB scale(float factor) {
        return scale(factor, factor, factor);
    }

    /**
     * Expands the bounding box into positive direction. This will only affect the maximum point of the bounding box,
     * the minimum point stays untouched.
     *
     * @param x the x-growth
     * @param y the y-growth
     * @param z the z-growth
     * @return itself
     */
    public abstract AxisAlignedBB grow(float x, float y, float z);

    /**
     * Expands the bounding box into positive direction. This will only affect the maximum point of the bounding box,
     * the minimum point stays untouched.
     *
     * @param v the growth
     * @return itself
     */
    public default AxisAlignedBB grow(Vector v) {
        return grow(v.getX(), v.getY(), v.getZ());
    }

    // MISC

    public abstract AxisAlignedBB clone();

}
