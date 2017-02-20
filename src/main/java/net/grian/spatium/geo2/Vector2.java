package net.grian.spatium.geo2;

import net.grian.spatium.Spatium;
import net.grian.spatium.impl.Vector2Impl;

public interface Vector2 {
    
    static Vector2 zero() {
        return new Vector2Impl(0, 0);
    }
    
    static Vector2 fromXY(double x, double y) {
        return new Vector2Impl(x, y);
    }
    
    static Vector2 fromRadiusAngle(double angle) {
        return fromXY(Math.cos(angle), Math.sin(angle));
    }
    
    static Vector2 between(Vector2 from, Vector2 to) {
        return fromXY(
            to.getX() - from.getX(),
            to.getY() - from.getY());
    }
    
    abstract double getX();
    
    abstract double getY();
    
    default double getLength() {
        return Spatium.hypot(getX(), getY());
    }
    
    /**
     * Returns a vector of equal length perpendicular to this vector, so that:
     * <ul>
     *     <li>|v'| = |v|</tt></li>
     *     <li><tt>v * v' = 0</tt></li>
     * </ul>
     *
     * @return a perpendicular vector of equal length
     */
    default Vector2 getInverse() {
        return fromXY(getY(), -getX());
    }
    
    //SETTERS
    
    abstract Vector2 setX(double x);
    
    abstract Vector2 setY(double y);
    
    abstract Vector2 set(double x, double y);
    
    default Vector2 setLength(double length) {
        return multiply(length / getLength());
    }
    
    //OPERATIONS
    
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
    
}
