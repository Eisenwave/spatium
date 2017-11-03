package net.grian.spatium.geo2;

import net.grian.spatium.util.Spatium;
import net.grian.spatium.geo3.Vectors;
import net.grian.spatium.impl.Vector2Impl;
import org.jetbrains.annotations.NotNull;

public interface Vector2 {
    
    @NotNull
    static Vector2 zero() {
        return new Vector2Impl(0, 0);
    }
    
    @NotNull
    static Vector2 fromXY(double x, double y) {
        return new Vector2Impl(x, y);
    }
    
    @NotNull
    static Vector2 fromRadiusAngle(double angle) {
        return fromXY(Math.cos(angle), Math.sin(angle));
    }
    
    @NotNull
    static Vector2 between(double x0, double y0, double x1, double y1) {
        return fromXY(x1-x0, y1-y0);
    }
    
    @NotNull
    static Vector2 between(Vector2 from, Vector2 to) {
        return fromXY(
            to.getX() - from.getX(),
            to.getY() - from.getY());
    }
    
    //GETTERS
    
    abstract double getX();
    
    abstract double getY();
    
    default double getLength() {
        return Math.sqrt(dot(this));
    }
    
    default double getLengthSquared() {
        return dot(this);
    }
    
    /**
     * Returns the (clockwise) angle to the x-axis of this vector.
     *
     * @return the angle
     */
    default double getAngle() {
        return Math.atan2(getY(), getX());
    }
    
    default double dot(Vector2 v) {
        return getX()*v.getX() + getY()*v.getY();
    }
    
    /**
     * Returns the distance of this vector to a given point.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @return the distance to the point
     */
    default double distanceTo(double x, double y) {
        return Spatium.hypot(x-getX(), y-getY());
    }
    
    /**
     * Returns the distance of this vector to a given point.
     *
     * @param point the point
     * @return the distance to the point
     */
    default double distanceTo(Vector2 point) {
        return distanceTo(point.getX(), point.getY());
    }
    
    /**
     * Returns a vector of equal length perpendicular to this vector, so that:
     * <ul>
     *     <li><code>|v'| = |v|</tt></code></li>
     *     <li><code>v' * v = 0 &lt;=&gt; v' perpendicular to v</code> </li>
     * </ul>
     *
     * @return a perpendicular vector of equal length
     */
    default Vector2 getInverse() {
        return fromXY(getY(), -getX());
    }
    
    //CHECKERS
    
    default boolean isMultipleOf(double x, double y) {
        return Vectors.multiples(getX(), getY(), x, y);
    }
    
    default boolean isMultipleOf(Vector2 v) {
        return Vectors.multiples(this, v);
    }
    
    default boolean equals(Vector2 v) {
        return
            Spatium.equals(getX(), v.getX()) &&
            Spatium.equals(getY(), v.getY());
    }
    
    default boolean isFinite() {
        return Double.isFinite(getX()) && Double.isFinite(getY());
    }
    
    default boolean isZero() {
        return Spatium.isZero(getX()) && Spatium.isZero(getY());
    }
    
    //SETTERS
    
    abstract Vector2 setX(double x);
    
    abstract Vector2 setY(double y);
    
    abstract Vector2 set(double x, double y);
    
    /**
     * Sets the length of this vector.
     *
     * @param length the length
     * @return itself
     */
    default Vector2 setLength(double length) {
        return multiply(length / getLength());
    }
    
    /**
     * Sets the (clockwise) angle to the x-axis of this vector.
     *
     * @param angle the angle in radians
     * @return itself
     */
    default Vector2 setAngle(double angle) {
        final double r = getLength();
        return set(
            Math.cos(angle) * r,
            Math.sin(angle) * r);
    }
    
    // OPERATIONS
    
    abstract Vector2 add(double x, double y);
    
    default Vector2 add(Vector2 v) {
        return add(v.getX(), v.getY());
    }
    
    abstract Vector2 subtract(double x, double y);
    
    default Vector2 subtract(Vector2 v) {
        return subtract(v.getX(), v.getY());
    }
    
    abstract Vector2 multiply(double x, double y);
    
    default Vector2 multiply(double factor) {
        return multiply(factor, factor);
    }
    
    abstract Vector2 divide(double x, double y);
    
    default Vector2 divide(double divisor) {
        return divide(divisor, divisor);
    }
    
    /**
     * Negates this vector, so that <tt>v' = -v</tt>
     *
     * @return itself
     */
    default Vector2 negate() {
        return multiply(-1);
    }
    
    /**
     * Normalizes this vector, so that <tt>|v| = 1</tt>
     *
     * @return itself
     */
    default Vector2 normalize() {
        return setLength(1);
    }
    
    // TRANSFORMATIONS
    
    /**
     * Rotates the vector counter clockwise by a given angle.
     *
     * @param angle the angle in radians
     * @return itself
     */
    default Vector2 rotCountClock(double angle) {
        final double
            x = getX(), y = getY(),
            sin = Math.sin(angle),
            cos = Math.cos(angle);
        return set(
            x*cos - y*sin,
            x*sin + cos*y);
    }
    
    /**
     * Rotates the vector clockwise by a given angle.
     *
     * @param angle the angle in radians
     * @return itself
     */
    default Vector2 rotClock(double angle) {
        return rotCountClock(-angle);
    }
    
    // MISC
    
    abstract Vector2 clone();
    
    default double[] toArray() {
        return new double[] {getX(), getY()};
    }
    
}
