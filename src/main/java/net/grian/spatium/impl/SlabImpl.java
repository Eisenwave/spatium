package net.grian.spatium.impl;

import net.grian.spatium.geo.Plane;
import net.grian.spatium.geo.Slab;
import net.grian.spatium.geo.Vector;

public class SlabImpl implements Slab {

    private float x, y, z, dmin, dmax;

    public SlabImpl(float x, float y, float z, float dmin, float dmax) {
        if (dmin > dmax) throw new IllegalArgumentException("dmin > dmax");
        this.x = x;
        this.y = y;
        this.z = z;
        this.dmin = dmin;
        this.dmax = dmax;
    }

    public SlabImpl(Vector normal, float dmin, float dmax) {
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
        float p = dmin / (x + y + z);
        return Vector.fromXYZ(p, p, p);
    }

    @Override
    public Vector getMaxPoint() {
        float p = dmax / (x + y + z);
        return Vector.fromXYZ(p, p, p);
    }

    @Override
    public float getMinDepth() {
        return dmin;
    }

    @Override
    public float getMaxDepth() {
        return dmax;
    }

    @Override
    public Slab setNormal(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    @Override
    public Slab setMinDepth(float depth) {
        if (depth > dmax) throw new IllegalArgumentException("depth > max depth");
        this.dmin = depth;
        return this;
    }

    @Override
    public Slab setMaxDepth(float depth) {
        if (depth < dmin) throw new IllegalArgumentException("depth < min depth");
        return this;
    }

    @Override
    public Slab push(float depth) {
        dmin += depth;
        dmax += depth;
        return this;
    }

    @Override
    public Slab pull(float depth) {
        dmin -= depth;
        dmax -= depth;
        return this;
    }
}
