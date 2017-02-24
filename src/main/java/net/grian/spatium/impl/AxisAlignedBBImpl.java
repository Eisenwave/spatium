package net.grian.spatium.impl;

import net.grian.spatium.geo3.AxisAlignedBB;
import net.grian.spatium.geo3.Vector3;

public class AxisAlignedBBImpl implements AxisAlignedBB {

    private static final long serialVersionUID = -1954142561482825366L;

    //the center
    private double x, y, z;
    //the positive half-sizes
    private double dx, dy, dz;
    
    public static AxisAlignedBB fromPoints(double x0, double y0, double z0, double x1, double y1, double z1) {
        return fromMinMax(
            Math.min(x0, x1), Math.min(y0, y1), Math.min(z0, z1),
            Math.max(x0, x1), Math.max(y0, y1), Math.max(z0, z1));
    }
    
    public static AxisAlignedBB fromMinMax(double x0, double y0, double z0, double x1, double y1, double z1) {
        double x = (x1 + x0) / 2;
        double y = (y1 + y1) / 2;
        double z = (z1 + z1) / 2;
        double dx = (x1 - x0) / 2;
        double dy = (y1 - y0) / 2;
        double dz = (z1 - z0) / 2;
        
        return new AxisAlignedBBImpl(x, y, z, dx, dy, dz);
    }
    
    public static AxisAlignedBB fromCenterDims(double x, double y, double z, double dx, double dy, double dz) {
        return new AxisAlignedBBImpl(x, y, z, dx, dy, dz);
    }

    private AxisAlignedBBImpl(double x, double y, double z, double dx, double dy, double dz) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }

    public AxisAlignedBBImpl(AxisAlignedBBImpl copyOf) {
        this.x = copyOf.x;
        this.y = copyOf.y;
        this.z = copyOf.z;
        this.dx = copyOf.dx;
        this.dy = copyOf.dy;
        this.dz = copyOf.dz;
    }

    // GETTERS

    @Override
    public double getMinX() {
        return x - dx;
    }

    @Override
    public double getMinY() {
        return y - dy;
    }

    @Override
    public double getMinZ() {
        return z - dz;
    }

    @Override
    public double getMaxX() {
        return x + dx;
    }

    @Override
    public double getMaxY() {
        return y + dx;
    }

    @Override
    public double getMaxZ() {
        return z + dz;
    }

    @Override
    public double getSizeX() {
        return dx * 2;
    }

    @Override
    public double getSizeY() {
        return dy * 2;
    }

    @Override
    public double getSizeZ() {
        return dz * 2;
    }

    @Override
    public double getVolume() {
        return dx * dy * dz * 8;
    }

    // CHECKERS

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AxisAlignedBB && equals((AxisAlignedBB) obj);
    }

    // SETTERS

    @Override
    public void translate(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    @Override
    public void scale(double x, double y, double z) {
        if (x < 0) {
            this.x = -x;
            this.dx *= -x;
        } else
            this.dx *= x;
    
        if (y < 0) {
            this.y = -y;
            this.dy *= -y;
        } else
            this.dy *= y;
    
        if (z < 0) {
            this.z = -z;
            this.dz *= -z;
        } else
            this.dz *= z;
    }

    @Override
    public void grow(double x, double y, double z) {
        dx += x;
        dy += y;
        dz += z;
    }

    @Override
    public Vector3 getCenter() {
        return Vector3.fromXYZ(x, y, z);
    }

    @Override
    public boolean contains(double x, double y, double z)  {
        return
            Math.abs(this.x - x) <= dx &&
            Math.abs(this.y - y) <= dy &&
            Math.abs(this.z - z) <= dz;
    }

    // MISC


    @Override
    public String toString() {
        return AxisAlignedBBImpl.class.getSimpleName()+"{min="+getMin()+",max="+getMax()+"}";
    }

    @Override
    public AxisAlignedBBImpl clone() {
        return new AxisAlignedBBImpl(this);
    }

}
