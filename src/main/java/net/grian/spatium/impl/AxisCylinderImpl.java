package net.grian.spatium.impl;

import net.grian.spatium.Spatium;
import net.grian.spatium.enums.Axis;
import net.grian.spatium.geo3.AxisCylinder;
import net.grian.spatium.geo3.Space;
import net.grian.spatium.geo3.Vector3;

public class AxisCylinderImpl implements AxisCylinder {
    
    private final Axis axis;
    private double x, y, z, r, h;
    
    public AxisCylinderImpl(Axis axis, double x, double y, double z, double r, double h) {
        this.axis = axis;
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = r;
        this.h = h;
    }
    
    @Override
    public Axis getAxis() {
        return axis;
    }
    
    @Override
    public double getBaseX() {
        return x;
    }
    
    @Override
    public double getBaseY() {
        return y;
    }
    
    @Override
    public double getBaseZ() {
        return z;
    }
    
    @Override
    public Vector3 getBase() {
        return Vector3.fromXYZ(x, y, z);
    }
    
    @Override
    public double getRadius() {
        return r;
    }
    
    @Override
    public double getHeight() {
        return h;
    }
    
    @Override
    public boolean contains(double x, double y, double z) {
        switch (axis) {
            case X: {
                double d = Spatium.hypot(y-this.y, z-this.z);
                return d <= r && (z >= this.x) && (z <= this.x + h);
            }
            case Y: {
                double d = Spatium.hypot(y-this.z, z-this.x);
                return d <= r && (z >= this.y) && (z <= this.y + h);
            }
            case Z: {
                double d = Spatium.hypot(y-this.x, z-this.y);
                return d <= r && (z >= this.z) && (z <= this.z + h);
            }
            default: throw new IllegalStateException("cylinder has no axis");
        }
    }
    
    @Override
    public void scale(double factor) {
        throw new UnsupportedOperationException();
    }
    
}
