package net.grian.spatium.impl;

import net.grian.spatium.geo2.Ray2;
import net.grian.spatium.geo2.Rectangle;
import net.grian.spatium.geo2.Triangle2;
import net.grian.spatium.geo2.Vector2;
import eisenwave.spatium.util.PrimMath;

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
    
    public Triangle2Impl(Triangle2Impl copyOf) {
        this.ax = copyOf.ax;
        this.ay = copyOf.ay;
        this.bx = copyOf.bx;
        this.by = copyOf.by;
        this.cx = copyOf.cx;
        this.cy = copyOf.cy;
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
    
    @Override
    public Ray2 getEdge(int index) {
        switch (index) {
            case 0: return Ray2.between(ax, ay, bx, by);
            case 1: return Ray2.between(bx, by, cx, cy);
            case 2: return Ray2.between(cx, cy, ax, ay);
            default: throw new IndexOutOfBoundsException(index+" must be in range 0-2");
        }
    }
    
    @Override
    public Rectangle getBoundaries() {
        return Rectangle.fromPoints(
            PrimMath.min(ax, bx, cx),
            PrimMath.min(ay, by, cy),
            PrimMath.max(ax, bx, cx),
            PrimMath.max(ay, by, cy));
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
    
    // MISC
    
    
    @Override
    public Triangle2 clone() {
        return new Triangle2Impl(this);
    }
    
    @Override
    public String toString() {
        return Triangle2.class.getSimpleName() + "["+getA()+","+getB()+","+getC()+"]";
    }
    
}
