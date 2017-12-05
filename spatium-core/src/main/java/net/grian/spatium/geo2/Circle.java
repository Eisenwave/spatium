package net.grian.spatium.geo2;

import eisenwave.spatium.util.Spatium;
import net.grian.spatium.geo3.Vector3;
import net.grian.spatium.impl.CircleImpl;
import org.jetbrains.annotations.NotNull;

public interface Circle extends Area {
    
    //CONSTRUCTORS
    
    @NotNull
    static Circle fromCenterRadius(double x, double y, double r) {
        return new CircleImpl(x, y, r);
    }
    
    @NotNull
    static Circle fromCenterRadius(Vector2 center, double r) {
        return fromCenterRadius(center.getX(), center.getY(), r);
    }
    
    //GETTERS
    
    abstract double getX();
    
    abstract double getY();
    
    default Vector2 getCenter() {
        return Vector2.fromXY(getX(), getY());
    }
    
    abstract double getRadius();
    
    default double getDiameter() {
        return getRadius() * 2;
    }
    
    @Override
    default double getCircumference() {
        return Math.PI * getDiameter();
    }
    
    @Override
    default double getArea() {
        double r = getRadius();
        return Math.PI * r * r;
    }
    
    //CHECKERS
    
    default boolean equals(Circle circle) {
        return
            Spatium.equals(getX(), circle.getX()) &&
            Spatium.equals(getY(), circle.getY()) &&
            Spatium.equals(getRadius(), circle.getRadius());
    }
    
    default boolean contains(double x, double y) {
        final double dx = x-getX(), dy = y-getY(), r = getRadius();
        return dx*dx + dy*dy <= r*r;
    }
    
    default boolean contains(Vector2 point) {
        return contains(point.getX(), point.getY());
    }
    
    //SETTERS
    
    abstract void setX(double x);
    
    abstract void setY(double y);
    
    abstract void setCenter(double x, double y);
    
    default void setCenter(Vector3 center) {
        setCenter(center.getX(), center.getY());
    }
    
    abstract void setRadius(double r);
    
    default void setDiameter(double d) {
        setRadius(d / 2);
    }
    
    // TRANSFORMATIONS
    
    @Override
    default void scale(double factor) {
        setCenter(getX()*factor, getY()*factor);
        setRadius(getRadius() * factor);
    }
    
    @Override
    default void scaleCentric(double factor) {
        setRadius(getRadius() * factor);
    }
    
    @Override
    default void translate(double x, double y) {
        setCenter(getX()+x, getY()+y);
    }
    
}
