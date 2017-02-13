package net.grian.spatium.geo3;

import net.grian.spatium.impl.OrientedBB3Impl;
import net.grian.spatium.matrix.Matrix;

import java.io.Serializable;

/**
 * An oriented cuboid bounding box.
 */
public interface OrientedBB3 extends Space, Serializable, Cloneable {

    /**
     * Constructs a new oriented bounding box from its center and dimensions.
     *
     * @param center the center of the bounding box
     * @param u the local x-axis (orthonormal basis vector)
     * @param v the local y-axis (orthonormal basis vector)
     * @param w the local z-axis (orthonormal basis vector)
     * @param dx the half-size on the x-axis
     * @param dy the half-size on the y-axis
     * @param dz the half-size on the z-axis
     * @return a new oriented bounding box
     */
    public static OrientedBB3 fromCD(Vector3 center, Vector3 u, Vector3 v, Vector3 w, double dx, double dy, double dz) {
        return new OrientedBB3Impl(center, dx, dy, dz, u, v, w);
    }

    /**
     * Constructs a new oriented bounding box from its center and dimensions.
     *
     * @param x the center x
     * @param y the center y
     * @param z the center z
     * @param dx the half-size on the x-axis
     * @param dy the half-size on the y-axis
     * @param dz the half-size on the z-axis
     * @return a new oriented bounding box
     */
    public static OrientedBB3 fromCD(double x, double y, double z, double dx, double dy, double dz) {
        return new OrientedBB3Impl(x, y, z, dx, dy, dz);
    }

    /**
     * Constructs a new oriented bounding box from its center and dimensions.
     *
     * @param center the center
     * @param dx the half-size on the x-axis
     * @param dy the half-size on the y-axis
     * @param dz the half-size on the z-axis
     * @return a new oriented bounding box
     */
    public static OrientedBB3 fromCD(Vector3 center, double dx, double dy, double dz) {
        return fromCD(center.getX(), center.getY(), center.getZ(), dx, dy, dz);
    }

    /**
     * Constructs a new oriented bounding box from an axis aligned bounding box.
     *
     * @param box the bounding box
     * @return a new oriented bounding box
     */
    public static OrientedBB3 fromAABB(AxisAlignedBB3 box) {
        return fromCD(box.getCenter(), box.getSizeX()/2, box.getSizeY()/2, box.getSizeZ()/2);
    }

    //GETTERS

    /**
     * Returns the minimum point of the box after applying rotation.
     *
     * @return the minimum point of the box after applying rotation
     */
    public abstract Vector3 getMin();

    /**
     * Returns the maximum point of the box after applying rotation.
     *
     * @return the maximum point of the box after applying rotation
     */
    public abstract Vector3 getMax();

    /**
     * Returns the center of the bounding box
     *
     * @return the center of the bounding box
     */
    public abstract Vector3 getCenter();

    public abstract Matrix getTransform();

    public abstract Slab3 getSlabX();

    public abstract Slab3 getSlabY();

    public abstract Slab3 getSlabZ();

    /**
     * Returns the size of the bounding box on the (local) x-axis.
     *
     * @return the size on the x-axis
     */
    public abstract double getSizeX();

    /**
     * Returns the size of the bounding box on the (local) y-axis.
     *
     * @return the size on the y-axis
     */
    public abstract double getSizeY();

    /**
     * Returns the size of the bounding box on the (local) z-axis.
     *
     * @return the size on the z-axis
     */
    public abstract double getSizeZ();

    /**
     * Returns the (local) x, y, z dimensions of the bounding box.
     *
     * @return the dimensions of the box
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

    //SETTERS

    public abstract void move(double x, double y, double z);

    public default void move(Vector3 v) {
        move(v.getX(), v.getY(), v.getZ());
    }

    public abstract void transform(Matrix transform);

    public default void rotateX(double angle) {
        transform(Matrix.fromRotX(angle));
    }

    public default void rotateY(double angle) {
        transform(Matrix.fromRotY(angle));
    }

    public default void rotateZ(double angle) {
        transform(Matrix.fromRotZ(angle));
    }
    
    @Override
    public abstract void scale(double factor);
    
    //MISC
    
    abstract OrientedBB3 clone();
    
    /**
     * Returns the bounding box as a AABB without its transformations.
     *
     * @return the untransformed aabb of this obb
     */
    public default AxisAlignedBB3 toAABB() {
        return AxisAlignedBB3.fromCenterDims(getCenter(), getSizeX()/2, getSizeY()/2, getSizeZ()/2);
    }
    
}
