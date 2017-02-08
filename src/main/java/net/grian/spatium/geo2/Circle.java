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
    
    abstract Circle setX(double x);
    
    abstract Circle setY(double y);
    
    abstract Circle setZ(double z);
    
    abstract Circle setCenter(double x, double y, double z);
    
    default Circle setCenter(Vector3 center) {
        return setCenter(center.getX(), center.getY(), center.getZ());
    }
    
    abstract Circle setRadius(double r);
    
    default Circle setDiameter(double d) {
        return setRadius(d / 2);
    }
    
    @Override
    default Circle scale(double factor) {
        return setRadius(getRadius() * factor);
    }
    
}
