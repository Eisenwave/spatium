package net.grian.spatium.geo3;

import net.grian.spatium.Spatium;
import net.grian.spatium.cache.CacheMath;
import net.grian.spatium.impl.SphereImpl;

import java.io.Serializable;

public interface Sphere extends Space, Serializable, Cloneable {

    /**
     * Constructs a new sphere.
     *
     * @return a new sphere
     */
    public static Sphere create() {
        return new SphereImpl();
    }

    /**
     * Constructs a new sphere from a center and a radius.
     *
     * @param center the center
     * @param radius the radius
     * @return a new sphere
     */
    public static Sphere fromCenterRadius(Vector3 center, double radius) {
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
    public static Sphere fromCenterRadius(double x, double y, double z, double radius) {
        return new SphereImpl(x, y, z, radius);
    }

    // GETTERS

    /**
     * Returns the x-coordinate of the center of this sphere.
     *
     * @return the x-coordinate of the center of this sphere
     */
    public abstract double getX();

    /**
     * Returns the y-coordinate of the center of this sphere.
     *
     * @return the y-coordinate of the center of this sphere
     */
    public abstract double getY();

    /**
     * Returns the z-coordinate of the center of this sphere.
     *
     * @return the z-coordinate of the center of this sphere
     */
    public abstract double getZ();

    /**
     * Returns the center of this sphere.
     *
     * @return the center of this sphere
     */
    public default Vector3 getCenter() {
        return Vector3.fromXYZ(getX(), getY(), getZ());
    }

    /**
     * Returns the radius of this sphere.
     *
     * @return the radius of this sphere
     */
    public abstract double getRadius();

    /**
     * Returns the squared radius of this sphere.
     *
     * @return the squared radius of this sphere
     */
    public abstract double getRadiusSquared();

    /**
     * Returns the diameter of this sphere.
     *
     * @return the diameter of this sphere
     */
    public default double getDiameter() {
        return getRadius() * 2;
    }

    @Override
    public default double getVolume() {
        double r = getRadius();
        return CacheMath.FOUR_THIRDS_PI * r*r*r;
    }

    @Override
    public default double getSurfaceArea() {
        double r = getRadius();
        return 4 * Math.PI * r*r;
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
    public default boolean contains(double x, double y, double z) {
        return Spatium.hypot(x-getX(), y-getY(), z-getZ()) <= getRadius();
    }

    /**
     * Returns whether this sphere contains a given point.
     *
     * @param point the point
     * @return whether this sphere contains the point
     */
    public default boolean contains(Vector3 point) {
        return contains(point.getX(), point.getY(), point.getZ());
    }

    /**
     * Returns whether this sphere is equal to another sphere.
     *
     * @param sphere the sphere
     * @return whether this is equal to the sphere
     */
    public default boolean equals(Sphere sphere) {
        return
            Spatium.equals(this.getX(), sphere.getX()) &&
            Spatium.equals(this.getY(), sphere.getY()) &&
            Spatium.equals(this.getZ(), sphere.getZ()) &&
            Spatium.equals(this.getRadius(), sphere.getRadius());
                
    }

    // SETTERS

    public abstract void setCenter(double x, double y, double z);

    public default void setCenter(Vector3 center) {
        setCenter(center.getX(), center.getY(), center.getZ());
    }
    
    @Override
    public abstract void translate(double x, double y, double z);
    
    public default void translate(Vector3 v) {
        translate(v.getX(), v.getY(), v.getZ());
    }

    /**
     * Scales this sphere by a factor. This is equivalent to multiplying the
     * radius of the sphere with the factor.
     *
     * @param factor the factor
     */
    @Override
    public default void scaleCentric(double factor) {
        setRadius(getRadius() * factor);
    }

    /**
     * Sets the radius of the sphere.
     *
     * @param r new the radius
     */
    public abstract void setRadius(double r);

    /**
     * Sets the diameter of the sphere.
     *
     * @param d new the diameter
     */
    public default void setDiameter(double d) {
        setRadius(d * 0.5f);
    }

    // MISC

    /**
     * Returns a circular path on the sphere. This circle is the intersection of a plane represented by a given
     * normal vector.
     *
     * @param normal the normal vector of the plane
     * @return a new circular path on the plane
     */
    public default Path3 getCircle(Vector3 normal) {
        return Path3.circle(this, normal);
    }

    public abstract Sphere clone();

}
