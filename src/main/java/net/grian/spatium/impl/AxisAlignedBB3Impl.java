package net.grian.spatium.impl;

import net.grian.spatium.geo3.AxisAlignedBB3;
import net.grian.spatium.geo3.Vector3;

public class AxisAlignedBB3Impl implements AxisAlignedBB3 {

    private static final long serialVersionUID = -1954142561482825366L;

    private double xmin, ymin, zmin, xmax, ymax, zmax;

    public AxisAlignedBB3Impl(double xa, double ya, double za, double xb, double yb, double zb) {
        this.xmin = Math.min(xa, xb);
        this.ymin = Math.min(ya, yb);
        this.zmin = Math.min(za, zb);
        this.xmax = Math.max(xa, xb);
        this.ymax = Math.max(ya, yb);
        this.zmax = Math.max(za, zb);
    }

    public AxisAlignedBB3Impl(AxisAlignedBB3Impl copyOf) {
        this.xmin = copyOf.xmin;
        this.ymin = copyOf.ymin;
        this.zmin = copyOf.zmin;
        this.xmax = copyOf.xmax;
        this.ymax = copyOf.ymax;
        this.zmax = copyOf.zmax;
    }

    // GETTERS

    @Override
    public double getMinX() {
        return xmin;
    }

    @Override
    public double getMinY() {
        return ymin;
    }

    @Override
    public double getMinZ() {
        return zmin;
    }

    @Override
    public double getMaxX() {
        return xmax;
    }

    @Override
    public double getMaxY() {
        return ymax;
    }

    @Override
    public double getMaxZ() {
        return zmax;
    }

    @Override
    public double getSizeX() {
        return xmax - xmin;
    }

    @Override
    public double getSizeY() {
        return ymax - ymin;
    }

    @Override
    public double getSizeZ() {
        return zmax - zmin;
    }

    @Override
    public Vector3 getMin() {
        return Vector3.fromXYZ(xmin, ymin, zmin);
    }

    @Override
    public Vector3 getMax() {
        return Vector3.fromXYZ(xmax, ymax, zmax);
    }

    @Override
    public Vector3 getDimensions() {
        return Vector3.fromXYZ(xmax - xmin, ymax - ymin, zmax - zmin);
    }

    @Override
    public double getVolume() {
        return (xmax - xmin) * (ymax - ymin) * (zmax - zmin);
    }

    @Override
    public double getSurfaceArea() {
        double a = xmax - xmin, b = ymax - ymin, c = zmax - zmin;
        return (a * b + a * c + b * c) * 2;
    }

    // CHECKERS

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AxisAlignedBB3 && equals((AxisAlignedBB3) obj);
    }

    // SETTERS

    @Override
    public void move(double x, double y, double z) {
        xmin += x; xmax += x;
        ymin += y; ymax += y;
        zmin += z; zmax += z;
    }

    @Override
    public void scale(double x, double y, double z) {
        xmin = x>0 ? this.xmin*x : this.xmax*x;
        ymin = y>0 ? this.ymin*y : this.ymax*y;
        zmin = z>0 ? this.zmin*z : this.zmax*z;
        xmax = x>0 ? this.xmax*x : this.xmin*x;
        ymax = y>0 ? this.ymax*y : this.ymin*y;
        zmax = z>0 ? this.zmax*z : this.zmin*z;
    }

    @Override
    public void grow(double x, double y, double z) {
        double
                dx = getSizeX(),
                dy = getSizeY(),
                dz = getSizeZ();

        if (-x < getSizeX()) {
            xmax += x;
        } else {
            xmax = xmin;
            xmin = xmin - dx - x;
        }

        if (-y < getSizeX()) {
            ymax += y;
        } else {
            ymax = ymin;
            ymin = ymin - dy - y;
        }

        if (-z < getSizeZ()) {
            zmax += z;
        } else {
            zmax = zmin;
            zmin = zmin - dz - z;
        }
    }

    @Override
    public Vector3 getCenter() {
        return Vector3.fromXYZ(
                (xmin + xmax) * 0.5f,
                (ymin + ymax) * 0.5f,
                (zmin + zmax) * 0.5f);
    }

    @Override
    public boolean contains(double x, double y, double z)  {
        return
                x >= xmin && x <= xmax &&
                y >= ymin && y <= ymax &&
                z >= zmin && z <= zmax;
    }

    // MISC


    @Override
    public String toString() {
        return AxisAlignedBB3Impl.class.getSimpleName()+"{min="+getMin()+",max="+getMax()+"}";
    }

    @Override
    public AxisAlignedBB3Impl clone() {
        return new AxisAlignedBB3Impl(this);
    }

}
