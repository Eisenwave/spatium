package net.grian.spatium.geo3;

import net.grian.spatium.impl.Ray3Impl;
import net.grian.spatium.iter.BlockIntervalIterator;
import net.grian.spatium.iter.IntervalIterator;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Iterator;

/**
 * A ray, defined by an origin and a direction.
 */
public interface Ray3 extends Path3, Serializable, Cloneable {

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
    @NotNull
    static Ray3 fromOD(double xo, double yo, double zo, double xd, double yd, double zd) {
        return new Ray3Impl(xo, yo, zo, xd, yd, zd);
    }

    /**
     * Creates a new ray from an origin point and a direction vector.
     *
     * @param origin the origin
     * @param dir the direction
     * @return a new ray
     */
    @NotNull
    static Ray3 fromOD(Vector3 origin, Vector3 dir) {
        return new Ray3Impl(origin, dir);
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
    @NotNull
    static Ray3 between(Vector3 from, Vector3 to) {
        return fromOD(from, Vector3.between(from, to));
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
    @NotNull
    static Ray3 between(double ox, double oy, double oz, double tx, double ty, double tz) {
        return fromOD(ox, oy, oz, tx-ox, ty-oy, tz-oz);
    }

    // GETTERS

    /**
     * Returns the x-coordinate of the origin of this ray.
     *
     * @return the x-coordinate of the origin of this ray
     */
    abstract double getOrgX();

    /**
     * Returns the y-coordinate of the origin of this ray.
     *
     * @return the y-coordinate of the origin of this ray
     */
    abstract double getOrgY();

    /**
     * Returns the z-coordinate of the origin of this ray.
     *
     * @return the z-coordinate of the origin of this ray
     */
    abstract double getOrgZ();

    /**
     * Returns the x-coordinate of the direction of this ray.
     *
     * @return the x-coordinate of the direction of this ray
     */
    abstract double getDirX();

    /**
     * Returns the y-coordinate of the direction of this ray.
     *
     * @return the y-coordinate of the direction of this ray
     */
    abstract double getDirY();

    /**
     * Returns the z-coordinate of the direction of this ray.
     *
     * @return the z-coordinate of the direction of this ray
     */
    abstract double getDirZ();

    /**
     * Returns the length of this ray.
     *
     * @return the length of this ray
     */
    abstract double getLength();

    /**
     * Returns the squared length of this ray.
     *
     * @return the squared length of this ray
     */
    abstract double getLengthSquared();
    
    /**
     * Returns the origin of this ray in a new vector.
     *
     * @return the origin of this ray in a new vector
     */
    @Override
    default Vector3 getOrigin() {
        return Vector3.fromXYZ(getOrgX(), getOrgY(), getOrgZ());
    }
    
    /**
     * Returns the direction of this ray in a new vector.
     *
     * @return the direction of this ray in a new vector
     */
    default Vector3 getDirection() {
        return Vector3.fromXYZ(getDirX(), getDirY(), getDirZ());
    }
    
    default AxisAlignedBB getBoundaries() {
        return AxisAlignedBB.between(getOrigin(), getEnd());
    }

    // PATH IMPL
    
    /**
     * Returns the end of this ray in a new vector. This is equal to the sum of the origin and direction of the vector.
     *
     * @return the end of this ray in a new vector
     */
    @Override
    default Vector3 getEnd() {
        return Vector3.fromXYZ(getOrgX() + getDirX(), getOrgY() + getDirY(), getOrgZ() + getDirZ());
    }

    @Override
    default Vector3[] getControlPoints() {
        return new Vector3[] {getOrigin(), getEnd()};
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
    default boolean contains(double x, double y, double z) {
        return Vector3.between(getOrgX(), getOrgY(), getOrgZ(), x, y, z).isMultipleOf(getDirection());
    }

    /**
     * Returns whether the ray is equal to the given point at any point.
     *
     * @param point the point
     * @return whether the ray is equal to the given point at any point
     */
    default boolean contains(Vector3 point) {
        return contains(point.getX(), point.getY(), point.getZ());
    }

    /**
     * Returns whether this ray is equal to another ray. This condition is true
     * if both origin and direction are equal.
     *
     * @param ray the ray
     * @return whether this ray is equal to the ray
     */
    default boolean equals(Ray3 ray) {
        return
            getOrigin().equals(ray.getOrigin()) &&
            getDirection().isMultipleOf(ray.getDirection());
    }

    // SETTERS

    /**
     * Sets the origin of the ray to a given point.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @param z the z-coordinate of the point
     */
    abstract void setOrigin(double x, double y, double z);

    /**
     * Sets the origin of the ray to a given point.
     *
     * @param point the point
     */
    default void setOrigin(Vector3 point) {
        setOrigin(point.getX(), point.getY(), point.getZ());
    }

    /**
     * Sets the direction of this ray to a given vector.
     *
     * @param x the x-coordinate of the vector
     * @param y the y-coordinate of the vector
     * @param z the z-coordinate of the vector
     */
    abstract void setDirection(double x, double y, double z);
    
    /**
     * Sets the direction of this ray to a given vector.
     *
     * @param v the vector
     */
    default void setDirection(Vector3 v) {
        setDirection(v.getX(), v.getY(), v.getZ());
    }

    /**
     * Sets the length of this ray to a given length.
     *
     * @param t the new hypot
     */
    default void setLength(double t) {
        scaleDirection(t / getLength());
    }

    default void normalize() {
        setLength(1);
    }
    
    //TRANSFORMATIONS
    
    default void scaleOrigin(double x, double y, double z) {
        setDirection(getOrgX()*x, getOrgY()*y, getOrgZ()*z);
    }
    
    default void scaleDirection(double x, double y, double z) {
        setDirection(getDirX()*x, getDirY()*y, getDirZ()*z);
    }
    
    default void scaleOrigin(double factor) {
        scaleOrigin(factor, factor, factor);
    }
    
    default void scaleDirection(double factor) {
        scaleDirection(factor, factor, factor);
    }
    
    default void scale(double x, double y, double z) {
        scaleOrigin(x, y, z);
        scaleDirection(x, y, z);
    }
    
    default void scale(double factor) {
        scale(factor, factor, factor);
    }
    
    /**
     * Translates the ray origin by a given amount on each axis.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     */
    abstract void translate(double x, double y, double z);

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
    default Iterator<Vector3> intervalIterator(double interval) {
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
    default Iterator<BlockVector> blockIntervalIterator() {
        return new BlockIntervalIterator(this);
    }

    /**
     * Returns a given amount of equally distributed points on this ray.
     *
     * @param amount the amount
     * @return an array containing points on this ray
     */
    @Override
    default Vector3[] getPoints(int amount) {
        if (amount < 0) throw new IllegalArgumentException("amount < 0");
        if (amount == 0) return new Vector3[0];
        if (amount == 1) return new Vector3[] {getOrigin()};
        if (amount == 2) return new Vector3[] {getOrigin(), getEnd()};

        int t = amount - 1, i = 0;
        Vector3[] result = new Vector3[amount];

        Iterator<Vector3> iter = intervalIterator(getLengthSquared() / t*t);
        while (iter.hasNext())
            result[i++] = iter.next();

        return result;
    }

    abstract Ray3 clone();

}
