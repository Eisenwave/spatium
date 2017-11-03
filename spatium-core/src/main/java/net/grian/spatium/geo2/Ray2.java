package net.grian.spatium.geo2;

import net.grian.spatium.impl.Ray2Impl;
import org.jetbrains.annotations.NotNull;

public interface Ray2 extends Transformable2 {
    
    // CONSTRUCTORS
    
    @NotNull
    static Ray2 fromOD(double x, double y, double dx, double dy) {
        return new Ray2Impl(x, y, dx, dy);
    }
    
    @NotNull
    static Ray2 fromOD(Vector2 origin, Vector2 direction) {
        return fromOD(origin.getX(), origin.getY(), direction.getX(), direction.getY());
    }
    
    @NotNull
    static Ray2 between(double x0, double y0, double x1, double y1) {
        return fromOD(x0, y0, x1-x0, y1-y0);
    }
    
    @NotNull
    static Ray2 between(Vector2 a, Vector2 b) {
        return between(a.getX(), a.getY(), b.getX(), b.getY());
    }
    
    // GETTERS
    
    abstract double getOrgX();
    
    abstract double getOrgY();
    
    abstract double getDirX();
    
    abstract double getDirY();
    
    default Vector2 getOrigin() {
        return Vector2.fromXY(getOrgX(), getOrgY());
    }
    
    default Vector2 getEnd() {
        return getOrigin().add(getDirection());
    }
    
    default Vector2 getDirection() {
        return Vector2.fromXY(getDirX(), getDirY());
    }
    
    default double getLength() {
        return getDirection().getLength();
    }
    
    default double getLengthSquared() {
        return getDirection().getLengthSquared();
    }
    
    default Vector2 getPoint(double t) {
        return getOrigin().add(getDirection().multiply(t));
    }
    
    // CHECKERS
    
    default boolean equals(Ray2 ray) {
        return
            contains(ray.getOrigin()) &&
            getDirection().isMultipleOf(ray.getDirection());
    }
    
    default boolean contains(double x, double y) {
        return Vector2.between(getOrgX(), getOrgY(), x, y).isMultipleOf(getDirX(), getDirY());
        
        /* this code is no longer in use since it does not account for dirX or dirY being 0
        return Spatium.equals(
            (x - getOrgX()) / getDirX(),
            (y - getOrgY()) / getDirY());
        */
    }
    
    default boolean contains(Vector2 point) {
        return contains(point.getX(), point.getY());
    }
    
    // SETTERS
    
    abstract void setOrigin(double x, double y);
    
    abstract void setEnd(double x, double y);
    
    abstract void setDirection(double x, double y);
    
    default void setOrigin(Vector2 v) {
        setOrigin(v.getX(), v.getY());
    }
    
    default void setEnd(Vector2 v) {
        setEnd(v.getX(), v.getY());
    }
    
    default void setDirection(Vector2 v) {
        setDirection(v.getX(), v.getY());
    }
    
    default void setLength(double length) {
        scaleDirection(length / getLength());
    }
    
    default void normalize() {
        setLength(1);
    }
    
    // TRANSFORMATIONS
    
    default void scaleDirection(double x, double y) {
        setDirection(getDirX()*x, getDirY()*y);
    }
    
    default void scaleOrigin(double x, double y) {
        setOrigin(getOrgX()*x, getOrgY()*y);
    }
    
    default void scaleDirection(double factor) {
        scaleDirection(factor, factor);
    }
    
    default void scaleOrigin(double factor) {
        scaleOrigin(factor, factor);
    }
    
    default void scale(double x, double y) {
        scaleOrigin(x, y);
        scaleDirection(x, y);
    }
    
    @Override
    default void scale(double factor) {
        scale(factor, factor);
    }
    
    @Override
    default void scaleCentric(double factor) {
        setOrigin(getOrigin().multiply(factor));
        setEnd(getEnd().multiply(factor));
    }
    
    // MISC
    
    abstract Ray2 clone();
    
}
