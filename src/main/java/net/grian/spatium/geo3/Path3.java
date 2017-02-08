package net.grian.spatium.geo3;

import net.grian.spatium.impl.Path3ImplBezier;
import net.grian.spatium.impl.Path3ImplCircle;
import net.grian.spatium.impl.Path3ImplLinear;
import net.grian.spatium.impl.Ray3Impl;

import java.io.Serializable;

public interface Path3 extends Serializable, Cloneable {

    /**
     * Creates a new circular path on a sphere with equal diameter as the sphere. This circular path is the
     * intersection of the sphere and a plane represented by the given normal vector which intersects the origin of
     * the sphere.
     *
     * @param sphere the sphere
     * @param normal the normal vector
     * @return a new path
     */
    public static Path3 circle(Sphere sphere, Vector3 normal) {
        return new Path3ImplCircle(sphere, normal);
    }

    /**
     * Creates a new circular path on a sphere with equal diameter as the sphere. This circular path is the
     * intersection of the sphere and a plane represented by the given normal vector which intersects the origin of
     * the sphere.
     *
     * @param center the center of the sphere
     * @param radius the radius of the sphere
     * @param normal the normal vector
     * @return a new path
     */
    public static Path3 circle(Vector3 center, double radius, Vector3 normal) {
        return new Path3ImplCircle(center, radius, normal);
    }

    /**
     * Creates a new linear path between two points.
     *
     * @param from the origin
     * @param to the end
     * @return a new path
     */
    public static Path3 line(Vector3 from, Vector3 to) {
        return new Ray3Impl(from, to);
    }

    /**
     * Creates a new linear path between a series of points.
     *
     * @param points the points
     * @return a new path
     */
    public static Path3 linear(Vector3... points) {
        return new Path3ImplLinear(points);
    }

    /**
     * Creates a new <a href="https://en.wikipedia.org/wiki/B%C3%A9zier_curve">bezier curve</a> between an array of
     * given points.
     *
     * @param points the points
     * @return a new path
     */
    public static Path3 bezier(Vector3... points) {
        return new Path3ImplBezier(points);
    }

    /**
     * <p>
     *     Returns the point at a given path multiplier {@code t} between 0 and 1.
     * </p>
     * There is two different cases for method:
     * <ul>
     *     <li>should the path be finite, t is being wrapped around 0 and 1. For example: {@code -3.5 -> 0.5}</li>
     *     <li>should the path be infinite, t can take on any value. Example: infinite line</li>
     * </ul>
     *
     * @param t the path multiplier in range from 0 to 1
     * @return a new point on this path
     */
    public abstract Vector3 getPoint(double t);

    /**
     * Returns the length of this path.
     *
     * @return the length of this path
     */
    public abstract double getLength();

    /**
     * Returns the squared hypot of this path.
     *
     * @return the squared hypot of this path
     */
    public abstract double getLengthSquared();

    /**
     * Returns whether this path is closed. This is equivalent to checking whether the origin of the path is the
     * end of the path.
     *
     * @return whether this path is closed
     */
    public default boolean isClosed() {
        return getOrigin().equals(getEnd());
    }

    /**
     * Returns the origin of this path.
     *
     * @return the origin of this path
     */
    public default Vector3 getOrigin() {
        return getPoint(0);
    }

    /**
     * Returns the middle point of this path.
     *
     * @return the middle point of this path
     */
    public default Vector3 getMidPoint() {
        return getPoint(0.5f);
    }

    /**
     * Returns the end of this path.
     *
     * @return the end of this path
     */
    public default Vector3 getEnd() {
        return getPoint(1);
    }

    /**
     * <p>
     *     Returns all control points of this path. These are points relevant to the shape of the curve.
     * </p>
     * Examples of control points:
     * <ul>
     *     <li>control points of a bezier curve</li>
     *     <li>points that a linear path consists of</li>
     *     <li>the origin and end of a ray</li>
     *     <li>the center of a circle</li>
     * </ul>
     * <p>
     *     The meaning of control points changes depending on the path that is implementing this interface, they don't
     *     have to be on the path, just relevant to the construction of it.
     * </p>
     * <p>
     *     It is important to note that the array is always a new object, although <u>its content's mutation can mutate
     *     the path.</u>
     * </p>
     *
     * @return a new non-empty array of points relevant to the construction of the curve
     */
    public abstract Vector3[] getControlPoints();

    /**
     * Returns an even amount of equally distributed points on this path.
     *
     * @param amount the amount of points
     * @return a new array containing the points
     */
    public default Vector3[] getPoints(int amount) {
        if (amount < 0) throw new IllegalArgumentException("negative amount");
        if (amount == 1) return new Vector3[] {getMidPoint()};
        if (amount == 2) return new Vector3[] {getOrigin(), getEnd()};
        if (amount == 3) return new Vector3[] {getOrigin(), getMidPoint(), getEnd()};

        final double t = amount - 1, interval = getLengthSquared() / t * t;
        Vector3[] result = new Vector3[amount];
        for (int i = 0; i<result.length; i++)
            result[i] = getPoint(i * interval);

        return result;
    }

}
