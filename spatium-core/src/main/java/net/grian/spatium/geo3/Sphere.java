package net.grian.spatium.geo3;

import eisenwave.spatium.util.Spatium;
import eisenwave.spatium.cache.CacheMath;
import net.grian.spatium.impl.SphereImpl;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public interface Sphere extends Space, Serializable, Cloneable {

    /**
     * Constructs a new sphere.
     *
     * @return a new sphere
     */
    @NotNull
    static Sphere create() {
        return new SphereImpl();
    }

    /**
     * Constructs a new sphere from a center and a radius.
     *
     * @param center the center
     * @param radius the radius
     * @return a new sphere
     */
    @NotNull
    static Sphere fromCenterRadius(Vector3 center, double radius) {
        return new SphereImpl(center, radius);
    }

    /**
     * Constructs a new sphere from a center and a radius.
     *
     * @param x the center x
     * @param y the center y
     * @param z the center z
     * @param radius the radius
     * @return a new sphere
     */
    @NotNull
    static Sphere fromCenterRadius(double x, double y, double z, double radius) {
        return new SphereImpl(x, y, z, radius);
    }

    // GETTERS

    /**
     * Returns the x-coordinate of the center of this sphere.
     *
     * @return the x-coordinate of the center of this sphere
     */
    abstract double getX();

    /**
     * Returns the y-coordinate of the center of this sphere.
     *
     * @return the y-coordinate of the center of this sphere
     */
    abstract double getY();

    /**
     * Returns the z-coordinate of the center of this sphere.
     *
     * @return the z-coordinate of the center of this sphere
     */
    abstract double getZ();
    
    @Override
    default Vector3 getCenter() {
        return Vector3.fromXYZ(getX(), getY(), getZ());
    }

    /**
     * Returns the radius of this sphere.
     *
     * @return the radius of this sphere
     */
    abstract double getRadius();

    /**
     * Returns the squared radius of this sphere.
     *
     * @return the squared radius of this sphere
     */
    abstract double getRadiusSquared();

    /**
     * Returns the diameter of this sphere.
     *
     * @return the diameter of this sphere
     */
    default double getDiameter() {
        return getRadius() * 2;
    }

    @Override
    default double getVolume() {
        double r = getRadius();
        return CacheMath.FOUR_THIRDS_PI * r*r*r;
    }

    @Override
    default double getSurfaceArea() {
        double r = getRadius();
        return 4 * Math.PI * r*r;
    }
    
    default AxisAlignedBB getBoundaries() {
        return AxisAlignedBB.fromCenterDims(getCenter(), getRadius());
    }

    // CHECKERS

    /**
     * Returns whether this sphere contains a given point.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @param z the z-coordinate of the point
     * @return whether this sphere contains the point
     */
    default boolean contains(double x, double y, double z) {
        final double
            dx = x-getX(),
            dy = y-getY(),
            dz = z-getZ(),
            r = getRadius();
        return dx*dx + dy*dy + dz*dz <= r*r;
    }

    /**
     * Returns whether this sphere contains a given point.
     *
     * @param point the point
     * @return whether this sphere contains the point
     */
    default boolean contains(Vector3 point) {
        return contains(point.getX(), point.getY(), point.getZ());
    }

    /**
     * Returns whether this sphere is equal to another sphere.
     *
     * @param sphere the sphere
     * @return whether this is equal to the sphere
     */
    default boolean equals(Sphere sphere) {
        return
            Spatium.equals(this.getX(), sphere.getX()) &&
            Spatium.equals(this.getY(), sphere.getY()) &&
            Spatium.equals(this.getZ(), sphere.getZ()) &&
            Spatium.equals(this.getRadius(), sphere.getRadius());
                
    }

    // SETTERS
    
    /**
     * Sets the x of the sphere center.
     *
     * @param x the x-coordinate
     */
    abstract void setX(double x);
    
    /**
     * Sets the y of the sphere center.
     *
     * @param y the y-coordinate
     */
    abstract void setY(double y);
    
    /**
     * Sets the z of the sphere center.
     *
     * @param z the z-coordinate
     */
    abstract void setZ(double z);
    
    /**
     * Sets the coordinates of the sphere center.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     */
    abstract void setCenter(double x, double y, double z);
    
    /**
     * Sets the coordinates of the sphere center.
     *
     * @param center the new center coordinates
     */
    default void setCenter(Vector3 center) {
        setCenter(center.getX(), center.getY(), center.getZ());
    }
    
    /**
     * Sets the radius of the sphere.
     *
     * @param r new the radius
     */
    abstract void setRadius(double r);
    
    /**
     * Sets the diameter of the sphere.
     *
     * @param d new the diameter
     */
    default void setDiameter(double d) {
        setRadius(d * 0.5f);
    }
    
    // TRANSFORMATIONS
    
    @Override
    default void translate(double x, double y, double z) {
        setCenter(getCenter().add(x, y, z));
    }

    /**
     * Scales this sphere by a factor. This is equivalent to multiplying the
     * radius of the sphere with the factor.
     *
     * @param factor the factor
     */
    @Override
    default void scaleCentric(double factor) {
        setRadius(getRadius() * factor);
    }

    // MISC

    /**
     * Returns a circular path on the sphere. This circle is the intersection of a plane represented by a given
     * normal vector.
     *
     * @param normal the normal vector of the plane
     * @return a new circular path on the plane
     */
    default Path3 getCircle(Vector3 normal) {
        return Path3.circle(this, normal);
    }

    abstract Sphere clone();

}
