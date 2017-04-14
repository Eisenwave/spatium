package net.grian.spatium.impl;

import net.grian.spatium.Spatium;
import net.grian.spatium.geo2.Ray2;
import net.grian.spatium.geo2.Vector2;

public class Ray2Impl implements Ray2 {
    
    private double x, y, dx, dy;
    
    public Ray2Impl(double x, double y, double dx, double dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }
    
    public Ray2Impl(Ray2Impl cloneOf) {
        this(cloneOf.x, cloneOf.y, cloneOf.dx, cloneOf.dy);
    }
    
    //GETTERS
    
    @Override
    public double getOrgX() {
        return x;
    }
    
    @Override
    public double getOrgY() {
        return y;
    }
    
    @Override
    public double getDirX() {
        return dx;
    }
    
    @Override
    public double getDirY() {
        return dy;
    }
    
    @Override
    public double getLength() {
        return Spatium.hypot(dx, dy);
    }
    
    @Override
    public double getLengthSquared() {
        return dx*dx + dy*dy;
    }
    
    @Override
    public Vector2 getEnd() {
        return Vector2.fromXY(x+dx, y+dy);
    }
    
    @Override
    public Vector2 getPoint(double t) {
        return Vector2.fromXY(x + dx*t, y + dy*t);
    }
    
    //SETTERS
    
    @Override
    public void setOrigin(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public void setDirection(double x, double y) {
        this.dx = x;
        this.dy = y;
    }
    
    @Override
    public void setEnd(double x, double y) {
        Vector2 end = getEnd();
        this.dx = x - end.getX();
        this.dy = y - end.getY();
    }
    
    @Override
    public void setLength(double length) {
        double factor = length / getLength();
        this.dx *= factor;
        this.dy *= factor;
    }
    
    
    //TRANSFORMATIONS
    
    @Override
    public void translate(double x, double y) {
        this.x += x;
        this.y += y;
    }
    
    @Override
    public void scale(double x, double y) {
        this.x *= x;
        this.y *= y;
        this.dx *= x;
        this.dy *= y;
    }
    
    @Override
    public void scale(double factor) {
        this.x *= factor;
        this.y *= factor;
        this.dx *= factor;
        this.dy *= factor;
    }
    
    // MISC
    
    @Override
    public Ray2 clone() {
        return new Ray2Impl(this);
    }
    
    @Override
    public String toString() {
        return Ray2.class.getSimpleName()+
            "{org="+getOrigin()+
            ",dir="+getDirection()+"}";
    }
    
}
