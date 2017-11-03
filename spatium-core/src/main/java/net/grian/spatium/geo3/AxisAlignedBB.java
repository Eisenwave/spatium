package net.grian.spatium.geo3;

import net.grian.spatium.util.Spatium;
import net.grian.spatium.enums.Direction;
import net.grian.spatium.impl.AxisAlignedBBImpl;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

import static net.grian.spatium.util.PrimMath.*;
import static net.grian.spatium.enums.Direction.*;

/**
 * An axis aligned bounding box, or the cubical space between two points.
 */
public interface AxisAlignedBB extends Space, Serializable, Cloneable {

    /**
     * Creates a new bounding box between two points.
     *
     * @param x0 the x of the first point
     * @param y0 the y of the first point
     * @param z0 the z of the first point
     * @param x1 the x of the second point
     * @param y1 the y of the second point
     * @param z1 the z of the second point
     * @return a new bounding box
     */
    @NotNull
    static AxisAlignedBB fromPoints(double x0, double y0, double z0, double x1, double y1, double z1) {
        return AxisAlignedBBImpl.fromPoints(x0, y0, z0, x1, y1, z1);
    }
    
    @NotNull
    static AxisAlignedBB fromCenterDims(double x, double y, double z, double dx, double dy, double dz) {
        return AxisAlignedBBImpl.fromCenterDims(x, y, z, dx, dy, dz);
    }

    /**
     * Creates a new cubical axis aligned bounding box around a center and of a set size. Each side of the cube will
     * be twice as long as the specified size.
     *
     * @param center the center of the cube
     * @param size the half length of one side of the cube
     * @return a new axis aligned bounding box
     */
    @NotNull
    static AxisAlignedBB fromCenterDims(Vector3 center, double size) {
        return fromCenterDims(center.getX(), center.getY(), center.getZ(), size, size, size);
    }

    /**
     * Creates a new bounding box between two points.
     *
     * @param from the first point
     * @param to the second point
     * @return a new bounding box
     */
    @NotNull
    static AxisAlignedBB between(Vector3 from, Vector3 to) {
        return fromPoints(from.getX(), from.getY(), from.getZ(), to.getX(), to.getY(), to.getZ());
    }

    // GETTERS

    abstract double getMinX();

    abstract double getMinY();

    abstract double getMinZ();

    abstract double getMaxX();

    abstract double getMaxY();

    abstract double getMaxZ();

    default double getSizeX() {
        return getMaxX() - getMinX();
    }

    default double getSizeY() {
        return getMaxY() - getMinY();
    }

    default double getSizeZ() {
        return getMaxZ() - getMinZ();
    }

    @Override
    default Vector3 getCenter() {
        return Vector3.fromXYZ(
            (getMinX() + getMaxX()) * 0.5f,
            (getMinY() + getMaxY()) * 0.5f,
            (getMinZ() + getMaxZ()) * 0.5f);
    }

    default Vector3 getMin() {
        return Vector3.fromXYZ(getMinX(), getMinY(), getMinZ());
    }

    default Vector3 getMax() {
        return Vector3.fromXYZ(getMaxX(), getMaxY(), getMaxZ());
    }

    /**
     * Returns the dimensions of the bounding box in a new vector.
     *
     * @return the dimensions of the bounding box in a new vector
     */
    default Vector3 getDimensions() {
        return Vector3.fromXYZ(getSizeX(), getSizeY(), getSizeZ());
    }

    @Override
    default double getVolume() {
        return getSizeX() * getSizeY() * getSizeZ();
    }

    @Override
    default double getSurfaceArea() {
        double x = getSizeX(), y = getSizeY(), z = getSizeZ();
        return (x * y + x * z + y * z) * 2;
    }
    
    default Direction getClosestSide(Vector3 point) {
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
    
    //SETTERS
    
    default void setCenter(double x, double y, double z) {
        Vector3 center = getCenter();
        translate(x-center.getX(), y-center.getY(), z-center.getZ());
    }
    
    abstract void setDimensions(double x, double y, double z);

    // CHECKERS
    
    default boolean equals(AxisAlignedBB box) {
        return
            Spatium.equals(this.getMinX(), box.getMinX()) &&
            Spatium.equals(this.getMinY(), box.getMinY()) &&
            Spatium.equals(this.getMinZ(), box.getMinZ()) &&
            Spatium.equals(this.getMaxX(), box.getMaxX()) &&
            Spatium.equals(this.getMaxY(), box.getMaxY()) &&
            Spatium.equals(this.getMaxZ(), box.getMaxZ());
    }
    
    @Override
    default boolean contains(double x, double y, double z) {
        return
            x >= getMinX() && x <= getMaxX() &&
            y >= getMinY() && y <= getMaxY() &&
            z >= getMinZ() && z <= getMaxZ();
    }
    
    default boolean isCube() {
        final double d = getSizeX();
        return Spatium.equals(d, getSizeY()) && Spatium.equals(d, getSizeZ());
    }

    // TRANSFORMATIONS

    /**
     * Scales the bounding box around the origin.
     *
     * @param x the x-scale factor
     * @param y the y-scale factor
     * @param z the z-scale factor
     */
    abstract void scale(double x, double y, double z);
    
    default void scale(double factor) {
        scale(factor, factor, factor);
    }
    
    @Override
    default void scaleCentric(double factor) {
        setDimensions(getSizeX()*factor, getSizeY()*factor, getSizeZ()*factor);
    }
    
    /**
     * Expands the bounding box into positive direction. This will only affect the maximum point of the bounding box,
     * the minimum point stays untouched.
     *
     * @param x the x-growth
     * @param y the y-growth
     * @param z the z-growth
     */
    abstract void grow(double x, double y, double z);

    /**
     * Expands the bounding box into positive direction. This will only affect the maximum point of the bounding box,
     * the minimum point stays untouched.
     *
     * @param v the growth
     */
    default void grow(Vector3 v) {
        grow(v.getX(), v.getY(), v.getZ());
    }

    // MISC

    abstract AxisAlignedBB clone();

}
