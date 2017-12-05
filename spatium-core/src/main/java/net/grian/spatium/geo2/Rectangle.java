package net.grian.spatium.geo2;

import eisenwave.spatium.util.Spatium;
import net.grian.spatium.impl.RectangleImpl;
import org.jetbrains.annotations.NotNull;

public interface Rectangle extends Area, Polygon2 {
    
    @NotNull
    static Rectangle fromCenterDims(double x, double y, double dx, double dy) {
        return new RectangleImpl(x, y, dx, dy);
    }
    
    @NotNull
    static Rectangle fromPoints(double x0, double y0, double x1, double y1) {
        return fromCenterDims(
            (x0 + x1) / 2,
            (y0 + y1) / 2,
            Math.abs(x1-x0) / 2,
            Math.abs(y1-y0) / 2);
    }
    
    @NotNull
    static Rectangle between(Vector2 a, Vector2 b) {
        return fromCenterDims(a.getX(), a.getY(), b.getX(), b.getY());
    }
    
    // GETTERS
    
    abstract double getMinX();
    
    abstract double getMinY();
    
    abstract double getMaxX();
    
    abstract double getMaxY();
    
    default Vector2 getMin() {
        return Vector2.fromXY(getMinX(), getMinY());
    }
    
    default Vector2 getMax() {
        return Vector2.fromXY(getMinX(), getMinY());
    }
    
    default double getWidth() {
        return getMaxX() - getMinX();
    }
    
    default double getHeight() {
        return getMaxY() - getMinY();
    }
    
    // AREA IMPL
    
    @Override
    default Vector2 getCenter() {
        return Vector2.fromXY(getMinX()+getMaxX(), getMinY()+getMaxY()).divide(2);
    }
    
    @Override
    default double getArea() {
        return getWidth() * getHeight();
    }
    
    @Override
    default double getCircumference() {
        return (getWidth() + getHeight()) * 2;
    }
    
    // POLYGON IMPL
    
    @Override
    default int getVertexCount() {
        return 4;
    }
    
    @Override
    default Vector2 getVertex(int index) {
        switch (index) {
            case 0: return Vector2.fromXY(getMinX(), getMinY());
            case 1: return Vector2.fromXY(getMinX(), getMaxY());
            case 2: return Vector2.fromXY(getMaxX(), getMaxY());
            case 3: return Vector2.fromXY(getMaxX(), getMinY());
            default: throw new IndexOutOfBoundsException("index must be in range 0-3");
        }
    }
    
    @Override
    default Ray2 getEdge(int index) {
        Vector2 org = getVertex(index);
        double dx = 0, dy = 0;
        
        switch (index) {
            case 0: dy = getHeight(); break;
            case 1: dx = getWidth(); break;
            case 2: dy = -getHeight(); break;
            case 3: dx = -getWidth(); break;
            default: throw new IndexOutOfBoundsException("index must be in range 0-3");
        }
        
        return Ray2.fromOD(org.getX(), org.getY(), dx, dy);
    }
    
    @Override
    default Rectangle getBoundaries() {
        return clone();
    }
    
    //CHECKERS
    
    default boolean contains(double x, double y) {
        return
            x >= getMinX() && x <= getMaxX() &&
            y >= getMinY() && y <= getMaxY();
    }
    
    default boolean isSquare() {
        return Spatium.equals(getWidth(), getHeight());
    }
    
    default boolean equals(Rectangle rectangle) {
        return
            Spatium.equals(getMinX(), rectangle.getMinX()) &&
            Spatium.equals(getMinY(), rectangle.getMinY()) &&
            Spatium.equals(getMaxX(), rectangle.getMaxX()) &&
            Spatium.equals(getMaxY(), rectangle.getMaxY());
    }
    
    // SETTERS
    
    abstract void setDimensions(double x, double y);
    
    // TRANSFORMATIONS
    
    default void scale(double x, double y) {
        setDimensions(getWidth()*x, getHeight()*y);
    }
    
    @Override
    default void scale(double factor) {
        scale(factor, factor);
    }
    
    // MISC
    
    abstract Rectangle clone();
    
}
