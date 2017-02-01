package net.grian.spatium.geo3;

import net.grian.spatium.SpatiumObject;
import net.grian.spatium.impl.PlaneImpl;

public interface Plane extends SpatiumObject {

    /**
     * Constructs a new plane from a point on the plane and two vectors.
     *
     * @param point the point
     * @param t the first vector
     * @param r the second vector
     * @return a new plane
     */
    static Plane fromPointVectors(Vector3 point, Vector3 t, Vector3 r) {
        return new PlaneImpl(point, t, r);
    }

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
    static Plane fromPointNormal(double xc, double yc, double zc, double xn, double yn, double zn) {
        return new PlaneImpl(xc, yc, zc, xn, yn, zn);
    }

    /**
     * Constructs a new plane from a center point and a normal vector.
     *
     * @param center the center
     * @param normal the normal vector
     * @return a new plane
     */
    static Plane fromPointNormal(Vector3 center, Vector3 normal) {
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
    static Plane fromGeneral(double a, double b, double c, double d) {
        return new PlaneImpl(a, b, c, d);
    }

    // GETTERS

    /**
     * Returns a point on the plane.
     *
     * @return the center of the plane
     */
    abstract Vector3 getPoint();

    /**
     * Returns the normal vector of the plane.
     *
     * @return the normal vector of the plane
     */
    abstract Vector3 getNormal();

    /**
     * Returns the depth <b>d</b> of the plane, as seen in the equation form:
     * <blockquote>
     *     <code>ax + by + cz = d</code>
     * </blockquote>
     *
     * @return the depth of the plane
     */
    abstract double getDepth();

    /**
     * <p>
     *     Returns the signed distance between a point and this plane.
     * </p>
     * <p>
     *     The sign is the result of a dot product between the plane normal and the x, y, z coordinates. Should the
     *     x, y, z coordinates lie on the positive side of the plane so will the distance be positive.
     * </p>
     * <p>
     *     Examples are:
     *     <ul>
     *         <li><code><b>(x = 0)</b> & <b>(2,0,0)</b> -> +2</code></li>
     *         <li><code><b>(x = 4)</b> & <b>(1,0,0)</b> -> -3</code></li>
     *     </ul>
     * </p>
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @param z the z-coordinate of the point
     * @return the distance between a point and this plane
     */
    default double signedDistanceTo(double x, double y, double z) {
        Vector3 n = getNormal();
        return (n.dot(x, y, z) - getDepth()) / n.getLength();
    }

    /**
     * <p>
     *     Returns the signed distance between a point and this plane.
     * </p>
     * <p>
     *     The sign is the result of a dot product between the plane normal and the x, y, z coordinates. Should the
     *     x, y, z coordinates lie on the positive side of the plane so will the distance be positive.
     * </p>
     * <p>
     *     Examples are:
     *     <ul>
     *         <li><code><b>(x = 0)</b> & <b>(2,0,0)</b> -> +2</code></li>
     *         <li><code><b>(x = 4)</b> & <b>(1,0,0)</b> -> -3</code></li>
     *     </ul>
     * </p>
     *
     * @param point the point
     * @return the distance between a point and this plane
     */
    default double signedDistanceTo(Vector3 point) {
        return signedDistanceTo(point.getX(), point.getY(), point.getZ());
    }

    // CHECKERS

    /**
     * Returns whether this plane is equal to another plane.
     *
     * @param plane the plane
     * @return whether this plane is equal to the plane
     */
    default boolean equals(Plane plane) {
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
    abstract boolean contains(double x, double y, double z);

    /**
     * Returns whether this plane contains a given point.
     *
     * @param point the point
     * @return whether this plane contains the point
     */
    default boolean contains(Vector3 point) {
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
    abstract Plane setCenter(double x, double y, double z);

    /**
     * Sets the center of this plane to a given point.
     *
     * @param point the point
     * @return itself
     */
    default Plane setCenter(Vector3 point) {
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
    abstract Plane setNormal(double x, double y, double z);

    /**
     * Sets the normal of this plane to a given vector.
     *
     * @param v the vector
     * @return itself
     */
    default Plane setNormal(Vector3 v) {
        return setNormal(v.getX(), v.getY(), v.getZ());
    }

    // MISC

    public abstract Plane clone();

}
