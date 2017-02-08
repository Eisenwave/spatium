package net.grian.spatium.geo3;

import net.grian.spatium.Spatium;
import net.grian.spatium.enums.Direction;
import net.grian.spatium.impl.AxisAlignedBB3Impl;

import java.io.Serializable;

import static net.grian.spatium.util.PrimMath.*;
import static net.grian.spatium.enums.Direction.*;

/**
 * An axis aligned bounding box, or the cubical space between two points.
 */
public interface AxisAlignedBB3 extends Space, Serializable, Cloneable {

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
    public static AxisAlignedBB3 fromPoints(double xa, double ya, double za, double xb, double yb, double zb) {
        return new AxisAlignedBB3Impl(xa, ya, za, xb, yb, zb);
    }
    
    public static AxisAlignedBB3 createCuboid(Vector3 center, double x, double y, double z) {
        return new AxisAlignedBB3Impl(
            center.getX() - x,
            center.getY() - y,
            center.getZ() - z,
            center.getX() + x,
            center.getY() + y,
            center.getZ() + z);
    }

    /**
     * Creates a new cubical axis aligned bounding box around a center and of a set size. Each side of the cube will
     * be twice as long as the specified size.
     *
     * @param center the center of the cube
     * @param size   the size of the cube
     * @return a new axis aligned bounding box
     */
    public static AxisAlignedBB3 createCube(Vector3 center, double size) {
        return createCuboid(center, size, size, size);
    }

    /**
     * Creates a new bounding box between two points.
     *
     * @param from the first point
     * @param to   the second point
     * @return a new bounding box
     */
    public static AxisAlignedBB3 between(Vector3 from, Vector3 to) {
        return fromPoints(from.getX(), from.getY(), from.getZ(), to.getX(), to.getY(), to.getZ());
    }

    // GETTERS

    public abstract double getMinX();

    public abstract double getMinY();

    public abstract double getMinZ();

    public abstract double getMaxX();

    public abstract double getMaxY();

    public abstract double getMaxZ();

    public default double getSizeX() {
        return getMaxX() - getMinX();
    }

    public default double getSizeY() {
        return getMaxY() - getMinY();
    }

    public default double getSizeZ() {
        return getMaxZ() - getMinZ();
    }

    public default Vector3 getCenter() {
        return Vector3.fromXYZ(
            (getMinX() + getMaxX()) * 0.5f,
            (getMinY() + getMaxY()) * 0.5f,
            (getMinZ() + getMaxZ()) * 0.5f);
    }

    public default Vector3 getMin() {
        return Vector3.fromXYZ(getMinX(), getMinY(), getMinZ());
    }

    public default Vector3 getMax() {
        return Vector3.fromXYZ(getMaxX(), getMaxY(), getMaxZ());
    }

    /**
     * Returns the dimensions of the bounding box in a new vector.
     *
     * @return the dimensions of the bounding box in a new vector
     */
    public default Vector3 getDimensions() {
        return Vector3.fromXYZ(getSizeX(), getSizeY(), getSizeZ());
    }

    @Override
    public default double getVolume() {
        return getSizeX() * getSizeY() * getSizeZ();
    }

    @Override
    public default double getSurfaceArea() {
        double x = getSizeX(), y = getSizeY(), z = getSizeZ();
        return (x * y + x * z + y * z) * 2;
    }

    public default Direction getClosestSide(Vector3 point) {
        Vector3 center = getCenter();
        final double
            x = point.getX() - center.getX(),
            y = point.getY() - center.getY(),
            z = point.getZ() - center.getZ();

        if (abs(x) < abs(y)) {
            if (abs(x) < abs(z))
                return x >= 0 ? POSITIVE_X : NEGATIVE_X;
            else
                return z >= 0 ? POSITIVE_Z : NEGATIVE_Z;
        } else {
            if (abs(y) < abs(z))
                return y >= 0 ? POSITIVE_Y : NEGATIVE_Y;
            else
                return z >= 0 ? POSITIVE_Z : NEGATIVE_Z;
        }
    }

    // CHECKERS
    
    public default boolean equals(AxisAlignedBB3 box) {
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
    public default boolean contains(double x, double y, double z) {
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
    public default boolean contains(Vector3 point) {
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
    public abstract AxisAlignedBB3 move(double x, double y, double z);

    /**
     * Moves the bounding box by a given amount.
     *
     * @param v the movement
     * @return itself
     */
    public default AxisAlignedBB3 move(Vector3 v) {
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
    public abstract AxisAlignedBB3 scale(double x, double y, double z);

    /**
     * Scales the bounding box around the origin.
     *
     * @param v the scale factors
     * @return itself
     */
    public default AxisAlignedBB3 scale(Vector3 v) {
        return scale(v.getX(), v.getY(), v.getZ());
    }

    /**
     * Scales the bounding box around the origin.
     *
     * @param factor the scale factor
     * @return itself
     */
    @Override
    public default AxisAlignedBB3 scale(double factor) {
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
    public abstract AxisAlignedBB3 grow(double x, double y, double z);

    /**
     * Expands the bounding box into positive direction. This will only affect the maximum point of the bounding box,
     * the minimum point stays untouched.
     *
     * @param v the growth
     * @return itself
     */
    public default AxisAlignedBB3 grow(Vector3 v) {
        return grow(v.getX(), v.getY(), v.getZ());
    }

    // MISC

    public abstract AxisAlignedBB3 clone();

}
