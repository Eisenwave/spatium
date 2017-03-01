package net.grian.spatium.impl;

import net.grian.spatium.geo2.Triangle2;
import net.grian.spatium.geo2.Vector2;

public class Triangle2Impl implements Triangle2 {
    
    private double ax, ay, bx, by, cx, cy;
    
    public Triangle2Impl(double ax, double ay, double bx, double by, double cx, double cy) {
        this.ax = ax;
        this.ay = ay;
        this.bx = bx;
        this.by = by;
        this.cx = cx;
        this.cy = cy;
    }
    
    public Triangle2Impl(Vector2 a, Vector2 b, Vector2 c) {
        this(a.getX(), a.getY(), b.getX(), b.getY(), c.getX(), c.getY());
    }
    
    // GETTERS
    
    @Override
    public Vector2 getA() {
        return Vector2.fromXY(ax, ay);
    }
    
    @Override
    public Vector2 getB() {
        return Vector2.fromXY(bx, by);
    }
    
    @Override
    public Vector2 getC() {
        return Vector2.fromXY(cx, cy);
    }
    
    @Override
    public Vector2 getCenter() {
        return Vector2.fromXY(
            (ax + bx + cx) /3,
            (ay + by + cy) /3);
    }
    
    // SETTERS
    
    @Override
    public void setA(Vector2 point) {
        this.ax = point.getX();
        this.ay = point.getY();
    }
    
    @Override
    public void setB(Vector2 point) {
        this.bx = point.getX();
        this.by = point.getY();
    }
    
    @Override
    public void setC(Vector2 point) {
        this.cx = point.getX();
        this.cy = point.getY();
    }
    
    // TRANSFORMATIONS
    
    @Override
    public void translate(double x, double y) {
        ax += x; bx += x; cx += x;
        ay += y; by += y; cy += y;
    }
    
    @Override
    public void scale(double factor) {
        this.ax *= factor; this.ay *= factor;
        this.bx *= factor; this.by *= factor;
        this.cx *= factor; this.cy *= factor;
    }
    
    @Override
    public void scale(double x, double y) {
        this.ax *= x; this.ay *= y;
        this.bx *= x; this.by *= y;
        this.cx *= x; this.cy *= y;
    }
    
    @Override
    public void scaleCentric(double x, double y) {
        final double
            cenX = (ax + bx + cx) / 3,
            cenY = (ay + by + cy) / 3;
        
        ax = (ax - cenX) * x; bx = (bx - cenX) * x; cx = (cx = cenX) * x;
        ay = (ay - cenY) * y; by = (by - cenY) * y; cy = (cy = cenY) * y;
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName()+"["+getA()+","+getB()+","+getC()+"]";
    }
    
}
