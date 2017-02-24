package net.grian.spatium.geo2;

import net.grian.spatium.Spatium;

public interface Rectangle extends Area {
    
    //GETTERS
    
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
    
    @Override
    default Vector2 getCenter() {
        return Vector2.fromXY(getMinX()+getMaxX(), getMinY()+getMaxY()).divide(2);
    }
    
    default double getWidth() {
        return getMaxX() - getMinX();
    }
    
    default double getHeight() {
        return getMaxY() - getMinY();
    }
    
    @Override
    default double getArea() {
        return getWidth() * getHeight();
    }
    
    @Override
    default double getCircumference() {
        return (getWidth() + getHeight()) * 2;
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
    
    //SETTERS
    
    
    
}
