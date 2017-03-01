package net.grian.spatium.geo2;

import net.grian.spatium.Spatium;
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
    
    default double dot(Vector2 v) {
        return getX()*v.getX() + getY()*v.getY();
    }
    
    default double distanceTo(double x, double y) {
        return Spatium.hypot(x-getX(), y-getY());
    }
    
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
    
    default Vector2 setLength(double length) {
        return multiply(length / getLength());
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
    
    // MISC
    
    abstract Vector2 clone();
    
}
