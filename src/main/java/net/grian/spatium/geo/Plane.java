package net.grian.spatium.geo;

import net.grian.spatium.Spatium;
import net.grian.spatium.SpatiumObject;
import net.grian.spatium.impl.Plane6f;

public interface Plane extends SpatiumObject {

    /**
     * Constructs a new plane from a center point and a normal vector.
     *
     * @param xc the x of the center
     * @param yc the y of the center
     * @param zc the z of the center
     * @param xn the x of the normal
     * @param yn the y of the normal
     * @param zn the z of the normal
     * @return a new plane
     */
    public static Plane create(float xc, float yc, float zc, float xn, float yn, float zn) {
        return new Plane6f(xc, yc, zc, xn, yn, zn);
    }

    /**
     * Constructs a new plane from a center point and a normal vector.
     *
     * @param center the center
     * @param normal the normal vector
     * @return a new plane
     */
    public static Plane create(Vector center, Vector normal) {
        return new Plane6f(center, normal);
    }

    /**
     * Constructs a new plane.
     *
     * @return a new plane
     */
    public static Plane create() {
        return new Plane6f();
    }

    // GETTERS

    /**
     * Returns the x-coordinate of the center of the plane.
     *
     * @return the x-coordinate of the center of the plane
     */
    public abstract float getCenterX();

    /**
     * Returns the y-coordinate of the center of the plane.
     *
     * @return the y-coordinate of the center of the plane
     */
    public abstract float getCenterY();

    /**
     * Returns the z-coordinate of the center of the plane.
     *
     * @return the z-coordinate of the center of the plane
     */
    public abstract float getCenterZ();

    /**
     * Returns the x-coordinate of the normal vector of the plane.
     *
     * @return the x-coordinate of the normal vector of the plane
     */
    public abstract float getNormalX();

    /**
     * Returns the y-coordinate of the normal vector of the plane.
     *
     * @return the y-coordinate of the normal vector of the plane
     */
    public abstract float getNormalY();

    /**
     * Returns the z-coordinate of the normal vector of the plane.
     *
     * @return the z-coordinate of the normal vector of the plane
     */
    public abstract float getNormalZ();

    /**
     * Returns the center of the plane.
     *
     * @return the center of the plane
     */
    public default Vector getCenter() {
        return Vector.fromXYZ(getCenterX(), getCenterY(), getCenterZ());
    }

    /**
     * Returns the normal vector of the plane.
     *
     * @return the normal vector of the plane
     */
    public default Vector getNormal() {
        return Vector.fromXYZ(getNormalX(), getNormalY(), getNormalZ());
    }

    // CHECKERS

    /**
     * Returns whether this plane is equal to another plane.
     *
     * @param plane the plane
     * @return whether this plane is equal to the plane
     */
    public default boolean equals(Plane plane) {
        return
                Spatium.equals(this.getCenterX(), plane.getCenterX()) &&
                Spatium.equals(this.getCenterY(), plane.getCenterY()) &&
                Spatium.equals(this.getCenterZ(), plane.getCenterZ()) &&
                Spatium.equals(this.getNormalX(), plane.getNormalX()) &&
                Spatium.equals(this.getNormalY(), plane.getNormalY()) &&
                Spatium.equals(this.getNormalZ(), plane.getNormalZ());
    }

    /**
     * Returns whether this plane contains a given point.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @param z the z-coordinate of the point
     * @return whether this plane contains the point
     */
    public abstract boolean contains(float x, float y, float z);

    /**
     * Returns whether this plane contains a given point.
     *
     * @param point the point
     * @return whether this plane contains the point
     */
    public default boolean contains(Vector point) {
        return contains(point.getX(), point.getY(), point.getZ());
    }

    // SETTERS

    /**
     * Sets the center of this plane to a given point.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @param z the z-coordinate of the point
     * @return itself
     */
    public abstract Plane setCenter(float x, float y, float z);

    /**
     * Sets the center of this plane to a given point.
     *
     * @param point the point
     * @return itself
     */
    public default Plane setCenter(Vector point) {
        return setCenter(point.getX(), point.getY(), point.getZ());
    }

    /**
     * Sets the normal of this plane to a given vector.
     *
     * @param x the x-coordinate of the vector
     * @param y the y-coordinate of the vector
     * @param z the z-coordinate of the vector
     * @return itself
     */
    public abstract Plane setNormal(float x, float y, float z);

    /**
     * Sets the normal of this plane to a given vector.
     *
     * @param v the vector
     * @return itself
     */
    public default Plane setNormal(Vector v) {
        return setNormal(v.getX(), v.getY(), v.getZ());
    }

    // MISC

    public abstract Plane clone();

}
