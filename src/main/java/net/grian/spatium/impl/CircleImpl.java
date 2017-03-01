package net.grian.spatium.impl;

import net.grian.spatium.geo2.Circle;

public class CircleImpl implements Circle {
    
    private double x, y, r;
    
    public CircleImpl(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }
    
    public CircleImpl(CircleImpl copyOf) {
        this.x = copyOf.x;
        this.y = copyOf.y;
        this.r = copyOf.r;
    }
    
    @Override
    public double getX() {
        return x;
    }
    
    @Override
    public double getY() {
        return y;
    }
    
    @Override
    public double getRadius() {
        return r;
    }
    
    @Override
    public void setX(double x) {
        this.x = x;
    }
    
    @Override
    public void setY(double y) {
        this.y = y;
    }
    
    @Override
    public void setCenter(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public void setRadius(double r) {
        this.r = r;
    }
    
    //TRANSFORMATIONS
    
    @Override
    public void scale(double factor) {
        this.x *= factor;
        this.y *= factor;
        this.r *= Math.abs(factor);
    }
    
    @Override
    public void scaleCentric(double factor) {
        this.r *= factor;
    }
    
    @Override
    public void translate(double x, double y) {
        this.x += x;
        this.y += y;
    }
    
    // MISC
    
    @Override
    public Object clone() {
        return new CircleImpl(this);
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName()+
            "{x="+getX()+
            ",y="+getY()+
            ",r="+getRadius()+"}";
    }
    
}
