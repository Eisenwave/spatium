package net.grian.spatium.impl;

import net.grian.spatium.geo.Plane;
import net.grian.spatium.geo.Slab;
import net.grian.spatium.geo.Vector;

public class SlabImpl implements Slab {

    private double x, y, z, dmin, dmax;

    public SlabImpl(double x, double y, double z, double dmin, double dmax) {
        if (dmin > dmax) throw new IllegalArgumentException("dmin > dmax");
        this.x = x;
        this.y = y;
        this.z = z;
        this.dmin = dmin;
        this.dmax = dmax;
    }

    public SlabImpl(Vector normal, double dmin, double dmax) {
        this(normal.getX(), normal.getY(), normal.getZ(), dmin, dmax);
    }

    @Override
    public Vector getNormal() {
        return Vector.fromXYZ(x, y, z);
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
    public Vector getMinPoint() {
        double p = dmin / (x + y + z);
        return Vector.fromXYZ(p, p, p);
    }

    @Override
    public Vector getMaxPoint() {
        double p = dmax / (x + y + z);
        return Vector.fromXYZ(p, p, p);
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
    public Slab setNormal(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    @Override
    public Slab setMinDepth(double depth) {
        if (depth > dmax) throw new IllegalArgumentException("depth > max depth");
        this.dmin = depth;
        return this;
    }

    @Override
    public Slab setMaxDepth(double depth) {
        if (depth < dmin) throw new IllegalArgumentException("depth < min depth");
        return this;
    }

    @Override
    public Slab push(double depth) {
        dmin += depth;
        dmax += depth;
        return this;
    }

    @Override
    public Slab pull(double depth) {
        dmin -= depth;
        dmax -= depth;
        return this;
    }
}
