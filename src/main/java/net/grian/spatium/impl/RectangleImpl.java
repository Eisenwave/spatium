package net.grian.spatium.impl;

import net.grian.spatium.geo2.Rectangle;
import net.grian.spatium.geo2.Vector2;

public class RectangleImpl implements Rectangle {
    
    private double x, y, dx, dy;
    
    public RectangleImpl(double x, double y, double dx, double dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }
    
    @Override
    public double getMinX() {
        return x - dx;
    }
    
    @Override
    public double getMinY() {
        return y - dy;
    }
    
    @Override
    public double getMaxX() {
        return x + dx;
    }
    
    @Override
    public double getMaxY() {
        return y + dy;
    }
    
    @Override
    public double getWidth() {
        return dx * 2;
    }
    
    @Override
    public double getHeight() {
        return dy * 2;
    }
    
    @Override
    public Vector2 getCenter() {
        return Vector2.fromXY(x, y);
    }
    
    // SETTERS
    
    @Override
    public void setDimensions(double x, double y) {
        this.dx = Math.abs(x);
        this.dy = Math.abs(y);
    }
    
    // TRANSFORMATIONS
    
    @Override
    public void scale(double x, double y) {
        this.x *= x;
        this.y *= y;
        this.dx *= Math.abs(x);
        this.dy *= Math.abs(y);
    }
    
    @Override
    public void scaleCentric(double factor) {
        factor = Math.abs(factor);
        dx *= factor;
        dy *= factor;
    }
    
    @Override
    public void translate(double x, double y) {
        this.x += x;
        this.y += y;
    }
    
}
