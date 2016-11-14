package net.grian.spatium.geo;

import net.grian.spatium.SpatiumObject;
import net.grian.spatium.impl.PlaneImpl;

public interface Plane extends SpatiumObject {

    /**
     * Constructs a new plane from a center point and a normal vector. (point-normal form)
     *
     * @param xc the x of the center
     * @param yc the y of the center
     * @param zc the z of the center
     * @param xn the x of the normal
     * @param yn the y of the normal
     * @param zn the z of the normal
     * @return a new plane
     */
    public static Plane fromPointNormal(float xc, float yc, float zc, float xn, float yn, float zn) {
        return new PlaneImpl(xc, yc, zc, xn, yn, zn);
    }

    /**
     * Constructs a new plane from a center point and a normal vector.
     *
     * @param center the center
     * @param normal the normal vector
     * @return a new plane
     */
    public static Plane fromPointNormal(Vector center, Vector normal) {
        return new PlaneImpl(center, normal);
    }

    /**
     * Constructs a new plane using the general form (equation form):
     * <blockquote>
     *     <code>ax + by + cz = d</code>
     * </blockquote>
     *
     * @param a the x-coefficient
     * @param b the y-coefficient
     * @param c the z-coefficient
     * @param d the depth
     * @return a new plane
     */
    public static Plane fromGeneral(float a, float b, float c, float d) {
        return new PlaneImpl(a, b, c, d);
    }

    // GETTERS

    /**
     * Returns a point on the plane.
     *
     * @return the center of the plane
     */
    public abstract Vector getPoint();

    /**
     * Returns the normal vector of the plane.
     *
     * @return the normal vector of the plane
     */
    public abstract Vector getNormal();

    /**
     * Returns the depth <b>d</b> of the plane, as seen in the equation form:
     * <blockquote>
     *     <code>ax + by + cz = d</code>
     * </blockquote>
     *
     * @return the depth of the plane
     */
    public abstract float getDepth();

    /**
     * Returns the signed distance between a point and this plane.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @param z the z-coordinate of the point
     * @return the distance between a point and this plane
     */
    public default float signedDistanceTo(float x, float y, float z) {
        Vector n = getNormal();
        return (n.dot(x, y, z) - getDepth()) / n.getLength();
    }

    /**
     * Returns the signed distance between a point and this plane.
     *
     * @param point the point
     * @return the distance between a point and this plane
     */
    public default float signedDistanceTo(Vector point) {
        return signedDistanceTo(point.getX(), point.getY(), point.getZ());
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
                getPoint().equals(plane.getPoint()) &&
                getNormal().equals(plane.getNormal());
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
