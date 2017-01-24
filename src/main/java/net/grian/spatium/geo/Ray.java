package net.grian.spatium.geo;

import net.grian.spatium.Spatium;
import net.grian.spatium.impl.RayImpl;
import net.grian.spatium.iter.BlockIntervalIterator;
import net.grian.spatium.iter.IntervalIterator;

import java.util.Iterator;

/**
 * A ray, defined by an origin and a direction.
 */
public interface Ray extends Path {

    /**
     * Creates a new ray from 3 coordinates for the origin point and 3 coordinates for the direction vector.
     *
     * @param xo the x-coordinate of the origin
     * @param yo the y-coordinate of the origin
     * @param zo the z-coordinate of the origin
     * @param xd the x-coordinate of the direction
     * @param yd the y-coordinate of the direction
     * @param zd the z-coordinate of the direction
     * @return a new ray
     */
    public static Ray fromOD(float xo, float yo, float zo, float xd, float yd, float zd) {
        return new RayImpl(xo, yo, zo, xd, yd, zd);
    }

    /**
     * Creates a new ray from an origin point and a direction vector.
     *
     * @param origin the origin
     * @param dir the direction
     * @return a new ray
     */
    public static Ray fromOD(Vector origin, Vector dir) {
        return new RayImpl(origin, dir);
    }

    /**
     * Creates a new ray between two points.
     * The origin will be a copy of the point {@code from}.
     * The directional vector will be a new vector from {@code from} to {@code to}.
     *
     * @param from the first point
     * @param to the second point
     * @return a new ray
     */
    public static Ray between(Vector from, Vector to) {
        return fromOD(from, Vector.between(from, to));
    }

    /**
     * Creates a new ray between two points.
     *
     * @param ox the origin x
     * @param oy the origin x
     * @param oz the origin x
     * @param tx the target x
     * @param ty the target x
     * @param tz the target x
     * @return a new ray
     */
    public static Ray between(float ox, float oy, float oz, float tx, float ty, float tz) {
        return fromOD(ox, oy, oz, tx-ox, ty-oy, tz-oz);
    }

    // GETTERS

    /**
     * Returns the x-coordinate of the origin of this ray.
     *
     * @return the x-coordinate of the origin of this ray
     */
    public abstract float getOriginX();

    /**
     * Returns the y-coordinate of the origin of this ray.
     *
     * @return the y-coordinate of the origin of this ray
     */
    public abstract float getOriginY();

    /**
     * Returns the z-coordinate of the origin of this ray.
     *
     * @return the z-coordinate of the origin of this ray
     */
    public abstract float getOriginZ();

    /**
     * Returns the x-coordinate of the direction of this ray.
     *
     * @return the x-coordinate of the direction of this ray
     */
    public abstract float getDirX();

    /**
     * Returns the y-coordinate of the direction of this ray.
     *
     * @return the y-coordinate of the direction of this ray
     */
    public abstract float getDirY();

    /**
     * Returns the z-coordinate of the direction of this ray.
     *
     * @return the z-coordinate of the direction of this ray
     */
    public abstract float getDirZ();

    /**
     * Returns the hypot of this ray.
     *
     * @return the hypot of this ray
     */
    public abstract float getLength();

    /**
     * Returns the squared hypot of this ray.
     *
     * @return the squared hypot of this ray
     */
    public abstract float getLengthSquared();

    /**
     * Returns the origin of this ray in a new vector.
     *
     * @return the origin of this ray in a new vector
     */
    public default Vector getOrigin() {
        return Vector.fromXYZ(getOriginX(), getOriginY(), getOriginZ());
    }

    /**
     * Returns the direction of this ray in a new vector.
     *
     * @return the direction of this ray in a new vector
     */
    public default Vector getDirection() {
        return Vector.fromXYZ(getDirX(), getDirY(), getDirZ());
    }

    /**
     * Returns the end of this ray in a new vector. This is equal to the sum of
     * the origin and direction of the vector.
     *
     * @return the end of this ray in a new vector
     */
    public default Vector getEnd() {
        return Vector.fromXYZ(getOriginX() + getDirX(), getOriginY() + getDirY(), getOriginZ() + getDirZ());
    }

    /**
     * Returns the closest point on this ray to another ray. Note that this
     * point can be further away from the origin of the ray than the hypot
     * of the ray, the ray is treated as if it were infinitely long.
     *
     * @param point the point
     * @return the closest point on this ray to another point
     */
    public abstract Vector closestPointTo(Vector point);

    @Override
    public default Vector[] getControlPoints() {
        return new Vector[] {getOrigin(), getEnd()};
    }

    // CHECKERS

    /**
     * Returns whether the ray is equal to the given point at any point.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @param z the z-coordinate of the point
     * @return whether the ray is equal to the given point at any point
     */
    public abstract boolean contains(float x, float y, float z);

    /**
     * Returns whether the ray is equal to the given point at any point.
     *
     * @param point the point
     * @return whether the ray is equal to the given point at any point
     */
    public default boolean contains(Vector point) {
        return contains(point.getX(), point.getY(), point.getZ());
    }

    /**
     * Tests at which point of the ray the ray collides with another point.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @param z the z-coordinate of the point
     * @return the ray multiplier or {@link Float#NaN} if there is no collision
     */
    public abstract float containsAt(float x, float y, float z);

    /**
     * Tests at which point of the ray the ray collides with another point.
     *
     * @param point the point
     * @return the ray multiplier or {@link Float#NaN} if there is no collision
     */
    public default float containsAt(Vector point) {
        return containsAt(point.getX(), point.getY(), point.getZ());
    }

    /**
     * Returns whether this ray is equal to another ray. This condition is true
     * if both origin and direction are equal.
     *
     * @param ray the ray
     * @return whether this ray is equal to the ray
     */
    public default boolean equals(Ray ray) {
        return
                Spatium.equals(this.getOriginX(), ray.getOriginX()) &&
                Spatium.equals(this.getOriginY(), ray.getOriginY()) &&
                Spatium.equals(this.getOriginZ(), ray.getOriginZ()) &&
                Spatium.equals(this.getDirX(), ray.getDirX()) &&
                Spatium.equals(this.getDirY(), ray.getDirY()) &&
                Spatium.equals(this.getDirZ(), ray.getDirZ());
    }

    // SETTERS

    /**
     * Sets the origin of the ray to a given point.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @param z the z-coordinate of the point
     * @return itself
     */
    public abstract Ray setOrigin(float x, float y, float z);

    /**
     * Sets the origin of the ray to a given point.
     *
     * @param point the point
     * @return itself
     */
    public default Ray setOrigin(Vector point) {
        return setOrigin(point.getX(), point.getY(), point.getZ());
    }

    /**
     * Sets the direction of the ray to a given vector.
     *
     * @param x the x-coordinate of the vector
     * @param y the y-coordinate of the vector
     * @param z the z-coordinate of the vector
     * @return itself
     */
    public abstract Ray setDirection(float x, float y, float z);

    /**
     * Sets the hypot of the ray to a given hypot.
     *
     * @param t the new hypot
     * @return itself
     */
    public abstract Ray setLength(float t);

    // MISC

    /**
     * <p>
     *     Returns a new interval iterator for this ray. This iterator will return all points in a given interval that
     *     are on a ray.
     * </p>
     * <p>
     *     For example, a ray with hypot 1 will produce an iterator that iterates over precisely 3 points if the
     *     interval is 0.5 (or 0.4).
     * </p>
     * <p>
     *     To get an iterator that iterates over a specified amount of points {@code x}, make use of
     *     <blockquote>
     *         {@code intervalIterator( getLength() / (x - 1) )}
     *     </blockquote>
     * </p>
     *
     * @param interval the interval of iteration
     * @return a new interval iterator
     */
    public default Iterator<Vector> intervalIterator(float interval) {
        return new IntervalIterator(this, interval);
    }

    /**
     * <p>
     *     Returns a new interval iterator for this ray. This iterator will return all points in a given interval that
     *     are on a ray.
     * </p>
     * <p>
     *     For example, a ray with hypot 1 will produce an iterator that iterates over precisely 3 points if the
     *     interval is 0.5 (or 0.4).
     * </p>
     * <p>
     *     To get an iterator that iterates over a specified amount of points {@code x}, make use of
     *     <blockquote>
     *         {@code intervalIterator( getLength() / (x - 1) )}
     *     </blockquote>
     * </p>
     *
     * @return a new interval iterator
     */
    public default Iterator<BlockVector> blockIntervalIterator() {
        return new BlockIntervalIterator(this);
    }

    /**
     * Returns a given amount of equally distributed points on this ray.
     *
     * @param amount the amount
     * @return an array containing points on this ray
     */
    public default Vector[] getPoints(int amount) {
        if (amount < 0) throw new IllegalArgumentException("amount < 0");
        if (amount == 0) return new Vector[0];
        if (amount == 1) return new Vector[] {getOrigin()};
        if (amount == 2) return new Vector[] {getOrigin(), getEnd()};

        int t = amount - 1, i = 0;
        Vector[] result = new Vector[amount];

        Iterator<Vector> iter = intervalIterator(getLengthSquared() / t*t);
        while (iter.hasNext())
            result[i++] = iter.next();

        return result;
    }

    public abstract Ray clone();



}
