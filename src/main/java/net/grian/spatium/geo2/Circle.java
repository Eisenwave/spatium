package net.grian.spatium.geo2;

import net.grian.spatium.geo3.Vector3;

public interface Circle extends Area {
    
    abstract double getX();
    
    abstract double getY();
    
    abstract double getZ();
    
    default Vector3 getCenter() {
        return Vector3.fromXYZ(getX(), getY(), getZ());
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
    
    //SETTERS
    
    abstract void setX(double x);
    
    abstract void setY(double y);
    
    abstract void setZ(double z);
    
    abstract void setCenter(double x, double y, double z);
    
    default void setCenter(Vector3 center) {
        setCenter(center.getX(), center.getY(), center.getZ());
    }
    
    abstract void setRadius(double r);
    
    default void setDiameter(double d) {
        setRadius(d / 2);
    }
    
    @Override
    default void scale(double factor) {
        setRadius(getRadius() * factor);
    }
    
}
