package net.grian.spatium.geo3;

import net.grian.spatium.impl.OrientedBBImpl;
import net.grian.spatium.matrix.Matrix;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * An oriented cuboid bounding box.
 */
public interface OrientedBB extends Space, Serializable, Cloneable {

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
    @NotNull
    static OrientedBB fromCD(Vector3 center, Vector3 u, Vector3 v, Vector3 w, double dx, double dy, double dz) {
        return new OrientedBBImpl(center, dx, dy, dz, u, v, w);
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
    @NotNull
    static OrientedBB fromCD(double x, double y, double z, double dx, double dy, double dz) {
        return new OrientedBBImpl(x, y, z, dx, dy, dz);
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
    @NotNull
    static OrientedBB fromCD(Vector3 center, double dx, double dy, double dz) {
        return fromCD(center.getX(), center.getY(), center.getZ(), dx, dy, dz);
    }

    /**
     * Constructs a new oriented bounding box from an axis aligned bounding box.
     *
     * @param box the bounding box
     * @return a new oriented bounding box
     */
    @NotNull
    static OrientedBB fromAABB(AxisAlignedBB box) {
        return fromCD(box.getCenter(), box.getSizeX()/2, box.getSizeY()/2, box.getSizeZ()/2);
    }

    //GETTERS

    /**
     * Returns the local minimum of this bounding box.
     *
     * @return the minimum point of the box after applying rotation
     */
    abstract Vector3 getMin();

    /**
     * Returns the local maximum of this bounding box.
     *
     * @return the maximum point of the box after applying rotation
     */
    abstract Vector3 getMax();
    
    @Override
    abstract Vector3 getCenter();
    
    /**
     * Returns the OBB's normalized local x-axis in world space.
     *
     * @return the local x-axis
     */
    abstract Vector3 getAxisX();
    
    /**
     * Returns the OBB's normalized local y-axis in world space.
     *
     * @return the local y-axis
     */
    abstract Vector3 getAxisY();
    
    /**
     * Returns the OBB's normalized local z-axis in world space.
     *
     * @return the local z-axis
     */
    abstract Vector3 getAxisZ();
    
    abstract Slab3 getSlabX();
    
    abstract Slab3 getSlabY();
    
    abstract Slab3 getSlabZ();
    
    /**
     * Returns the OBB's orthonormal transformation matrix from world space into local space.
     *
     * @return the OBB's transformation matrix
     */
    abstract Matrix getTransform();

    /**
     * Returns the size of the bounding box on the (local) x-axis.
     *
     * @return the size on the x-axis
     */
    abstract double getSizeX();

    /**
     * Returns the size of the bounding box on the (local) y-axis.
     *
     * @return the size on the y-axis
     */
    abstract double getSizeY();

    /**
     * Returns the size of the bounding box on the (local) z-axis.
     *
     * @return the size on the z-axis
     */
    abstract double getSizeZ();

    /**
     * Returns the (local) x, y, z dimensions of the bounding box.
     *
     * @return the dimensions of the box
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
    
    abstract AxisAlignedBB getBoundaries();
    
    // CHECKERS
    
    @Override
    default boolean contains(double x, double y, double z) {
        Matrix objectToWorld = getTransform().getInverse();
        Vector3 point = Vector3.fromXYZ(x, y, z).transform(objectToWorld);
        return toAABB().contains(point);
    }
    
    // SETTERS
    
    abstract void setCenter(double x, double y, double z);
    
    default void setCenter(Vector3 center) {
        setCenter(center.getX(), center.getY(), center.getZ());
    }
    
    abstract void setDimensions(double x, double y, double z);

    // TRANSFORMATIONS

    @Override
    default void translate(double x, double y, double z) {
        setCenter(getCenter().add(x, y, z));
    }
    
    @Override
    default void scale(double factor) {
        Vector3 d = getDimensions();
        setDimensions(d.getX()*factor, d.getY()*factor, d.getZ()*factor);
    }
    
    abstract void transform(Matrix transform);

    default void rotateX(double angle) {
        transform(Matrix.fromRotX(angle));
    }

    default void rotateY(double angle) {
        transform(Matrix.fromRotY(angle));
    }

    default void rotateZ(double angle) {
        transform(Matrix.fromRotZ(angle));
    }
    
    @Override
    abstract void scaleCentric(double factor);
    
    //MISC
    
    abstract OrientedBB clone();
    
    /**
     * Returns the bounding box as a AABB without its transformations.
     *
     * @return the untransformed aabb of this obb
     */
    default AxisAlignedBB toAABB() {
        Vector3 center = getCenter();
        return AxisAlignedBB.fromCenterDims(
            center.getX(), center.getY(), center.getZ(),
            getSizeX()/2, getSizeY()/2, getSizeZ()/2);
    }
    
}
