package net.grian.spatium.impl;

import net.grian.spatium.Spatium;
import net.grian.spatium.geo3.Cone;
import net.grian.spatium.geo3.Vector3;

public class ConeImpl implements Cone {
    
    private double x, y, z, dx, dy, dz, r;
    
    public ConeImpl(double x, double y, double z, double dx, double dy, double dz, double r) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
        this.r = r;
    }
    
    @Override
    public Vector3 getApex() {
        return Vector3.fromXYZ(x, y, z);
    }
    
    @Override
    public Vector3 getDirection() {
        return Vector3.fromXYZ(dx, dy, dz);
    }
    
    @Override
    public double getAperture() {
        return 2 * Math.atan(r / getHeight());
    }
    
    @Override
    public double getBaseRadius() {
        return r;
    }
    
    @Override
    public double getHeight() {
        return Spatium.hypot(dx, dy, dz);
    }
    
    @Override
    public void scale(double factor) {
        dx *= factor;
        dy *= factor;
        dz *= factor;
    }
    
    @Override
    public void translate(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }
    
    @Override
    public void setHeight(double height) {
        scale(height / getHeight());
    }
    
    @Override
    public void setBaseRadius(double radius) {
        this.r = radius;
    }
    
    @Override
    public void setApex(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    @Override
    public void setDirection(double x, double y, double z) {
        this.dx = x;
        this.dy = y;
        this.dz = z;
    }
    
}
