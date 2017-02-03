package net.grian.spatium.impl;

import net.grian.spatium.geo2.Vector2;

public class Vector2Impl implements Vector2 {
    
    private double x, y;
    
    public Vector2Impl(double x, double y) {
        this.x = x;
        this.y = y;
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
    public Vector2 setX(double x) {
        this.x = x;
        return this;
    }
    
    @Override
    public Vector2 setY(double y) {
        this.y = y;
        return this;
    }
    
    @Override
    public Vector2 set(double x, double y) {
        this.x = x;
        this.y = y;
        return this;
    }
    
    @Override
    public Vector2 add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }
    
    @Override
    public Vector2 subtract(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }
    
    @Override
    public Vector2 multiply(double x, double y) {
        this.x *= x;
        this.y *= y;
        return this;
    }
    
    @Override
    public Vector2 divide(double x, double y) {
        this.x /= x;
        this.y /= y;
        return this;
    }
    
    @Override
    public String toString() {
        return "("+x+","+y+")";
    }
    
}