package net.grian.spatium.impl;

import net.grian.spatium.geo3.Plane;
import net.grian.spatium.geo3.Slab3;
import net.grian.spatium.geo3.Vector3;

public class Slab3Impl implements Slab3 {

    private double x, y, z, dmin, dmax;

    public Slab3Impl(double x, double y, double z, double dmin, double dmax) {
        if (dmin > dmax) throw new IllegalArgumentException("dmin > dmax");
        this.x = x;
        this.y = y;
        this.z = z;
        this.dmin = dmin;
        this.dmax = dmax;
    }

    public Slab3Impl(Vector3 normal, double dmin, double dmax) {
        this(normal.getX(), normal.getY(), normal.getZ(), dmin, dmax);
    }

    @Override
    public Vector3 getNormal() {
        return Vector3.fromXYZ(x, y, z);
    }

    @Override
    public Plane getMin() {
        return Plane.fromGeneral(x, y, z, dmin);
    }

    @Override
    public Plane getMax() {
        return Plane.fromGeneral(x, y, z, dmax);
    }

    @Override
    public Vector3 getMinPoint() {
        double p = dmin / (x + y + z);
        return Vector3.fromXYZ(p, p, p);
    }

    @Override
    public Vector3 getMaxPoint() {
        double p = dmax / (x + y + z);
        return Vector3.fromXYZ(p, p, p);
    }

    @Override
    public double getMinDepth() {
        return dmin;
    }

    @Override
    public double getMaxDepth() {
        return dmax;
    }

    @Override
    public void setNormal(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void setMinDepth(double depth) {
        if (depth > dmax) throw new IllegalArgumentException("depth > max depth");
        this.dmin = depth;
    }

    @Override
    public void setMaxDepth(double depth) {
        if (depth < dmin) throw new IllegalArgumentException("depth < min depth");
        this.dmax = depth;
    }

    @Override
    public void push(double depth) {
        dmin += depth;
        dmax += depth;
    }

    @Override
    public void pull(double depth) {
        dmin -= depth;
        dmax -= depth;
    }

    @Override
    public String toString() {
        return Slab3Impl.class.getSimpleName()+
                "{n="+getNormal()+
                ",dmin="+getMinDepth()+
                ",dmax="+getMaxDepth()+"}";
    }
}
