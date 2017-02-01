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
    public Slab3 setNormal(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    @Override
    public Slab3 setMinDepth(double depth) {
        if (depth > dmax) throw new IllegalArgumentException("depth > max depth");
        this.dmin = depth;
        return this;
    }

    @Override
    public Slab3 setMaxDepth(double depth) {
        if (depth < dmin) throw new IllegalArgumentException("depth < min depth");
        return this;
    }

    @Override
    public Slab3 push(double depth) {
        dmin += depth;
        dmax += depth;
        return this;
    }

    @Override
    public Slab3 pull(double depth) {
        dmin -= depth;
        dmax -= depth;
        return this;
    }

    @Override
    public String toString() {
        return Slab3Impl.class.getSimpleName()+
                "{n="+getNormal()+
                ",dmin="+getMinDepth()+
                ",dmax="+getMaxDepth()+"}";
    }
}
