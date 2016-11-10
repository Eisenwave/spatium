package net.grian.spatium.geo;

import net.grian.spatium.SpatiumObject;
import net.grian.spatium.impl.PathImplBezier;
import net.grian.spatium.impl.PathImplCircle;
import net.grian.spatium.impl.PathImplLinear;
import net.grian.spatium.impl.RayImpl;

public interface Path extends SpatiumObject {

    /**
     * Creates a new circular path on a sphere with equal diameter as the sphere. This circular path is the
     * intersection of the sphere and a plane represented by the given normal vector which intersects the origin of
     * the sphere.
     *
     * @param sphere the sphere
     * @param normal the normal vector
     * @return a new path
     */
    public static Path circle(Sphere sphere, Vector normal) {
        return new PathImplCircle(sphere, normal);
    }

    /**
     * Creates a new linear path between two points.
     *
     * @param from the origin
     * @param to the end
     * @return a new path
     */
    public static Path line(Vector from, Vector to) {
        return new RayImpl(from, to);
    }

    /**
     * Creates a new linear path between a series of points.
     *
     * @param points the points
     * @return a new path
     */
    public static Path linear(Vector... points) {
        return new PathImplLinear(points);
    }

    /**
     * Creates a new path representing a bezier curve between an array of given points.
     *
     * @param points the points
     * @return a new path
     */
    public static Path bezier(Vector... points) {
        return new PathImplBezier(points);
    }

    /**
     * Returns the point at a given path multiplier {@code t} between 0 and 1.
     *
     * @param t the path multiplier in range from 0 to 1
     * @return a new point on this path
     */
    public abstract Vector getPoint(float t);

    /**
     * Returns the length of this path.
     *
     * @return the length of this path
     */
    public abstract float getLength();

    /**
     * Returns the squared length of this path.
     *
     * @return the squared length of this path
     */
    public abstract float getLengthSquared();

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
    public default Vector getOrigin() {
        return getPoint(0);
    }

    /**
     * Returns the middle point of this path.
     *
     * @return the middle point of this path
     */
    public default Vector getMidPoint() {
        return getPoint(0.5f);
    }

    /**
     * Returns the end of this path.
     *
     * @return the end of this path
     */
    public default Vector getEnd() {
        return getPoint(1);
    }

    /**
     * Returns an even amount of equally distributed points on this path.
     *
     * @param amount the amount of points
     * @return a new array containing the points
     */
    public default Vector[] getPoints(int amount) {
        if (amount < 0) throw new IllegalArgumentException("negative amount");
        if (amount == 1) return new Vector[] {getMidPoint()};
        if (amount == 2) return new Vector[] {getOrigin(), getEnd()};
        if (amount == 3) return new Vector[] {getOrigin(), getMidPoint(), getEnd()};

        final float t = amount - 1, interval = getLengthSquared() / t * t;
        Vector[] result = new Vector[amount];
        for (int i = 0; i<result.length; i++)
            result[i] = getPoint(i * interval);

        return result;
    }

}
