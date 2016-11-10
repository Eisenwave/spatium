package net.grian.spatium.impl;

import net.grian.spatium.geo.AxisAlignedBB;
import net.grian.spatium.geo.Vector;

public class AxisAlignedBBImpl implements AxisAlignedBB {

    private static final long serialVersionUID = -1954142561482825366L;

    private float xmin, ymin, zmin, xmax, ymax, zmax;

    public AxisAlignedBBImpl(float xa, float ya, float za, float xb, float yb, float zb) {
        this.xmin = Math.min(xa, xb);
        this.ymin = Math.min(ya, yb);
        this.zmin = Math.min(za, zb);
        this.xmax = Math.max(xa, xb);
        this.ymax = Math.max(ya, yb);
        this.zmax = Math.max(za, zb);
    }

    public AxisAlignedBBImpl(AxisAlignedBBImpl copyOf) {
        this.xmin = copyOf.xmin;
        this.ymin = copyOf.ymin;
        this.zmin = copyOf.zmin;
        this.xmax = copyOf.xmax;
        this.ymax = copyOf.ymax;
        this.zmax = copyOf.zmax;
    }

    // GETTERS

    @Override
    public float getMinX() {
        return xmin;
    }

    @Override
    public float getMinY() {
        return ymin;
    }

    @Override
    public float getMinZ() {
        return zmin;
    }

    @Override
    public float getMaxX() {
        return xmax;
    }

    @Override
    public float getMaxY() {
        return ymax;
    }

    @Override
    public float getMaxZ() {
        return zmax;
    }

    @Override
    public float getSizeX() {
        return xmax - xmin;
    }

    @Override
    public float getSizeY() {
        return ymax - ymin;
    }

    @Override
    public float getSizeZ() {
        return zmax - zmin;
    }

    @Override
    public Vector getMin() {
        return Vector.fromXYZ(xmin, ymin, zmin);
    }

    @Override
    public Vector getMax() {
        return Vector.fromXYZ(xmax, ymax, zmax);
    }

    @Override
    public Vector getDimensions() {
        return Vector.fromXYZ(xmax - xmin, ymax - ymin, zmax - zmin);
    }

    @Override
    public float getVolume() {
        return (xmax - xmin) * (ymax - ymin) * (zmax - zmin);
    }

    // SETTERS

    @Override
    public AxisAlignedBB move(float x, float y, float z) {
        xmin += x; xmax += x;
        ymin += y; ymax += y;
        zmin += z; zmax += z;
        return this;
    }

    @Override
    public AxisAlignedBB scale(float x, float y, float z) {
        xmin = x>0 ? this.xmin*x : this.xmax*x;
        ymin = y>0 ? this.ymin*y : this.ymax*y;
        zmin = z>0 ? this.zmin*z : this.zmax*z;
        xmax = x>0 ? this.xmax*x : this.xmin*x;
        ymax = y>0 ? this.ymax*y : this.ymin*y;
        zmax = z>0 ? this.zmax*z : this.zmin*z;
        return this;
    }

    @Override
    public AxisAlignedBB grow(float x, float y, float z) {
        Vector center = getCenter();

        xmin = center.getX() - x;
        ymin = center.getY() - y;
        zmin = center.getZ() - z;
        xmax = center.getX() + x;
        ymax = center.getY() + y;
        zmax = center.getZ() + z;
        return this;
    }

    @Override
    public Vector getCenter() {
        return Vector.fromXYZ(
                (xmin + xmax) * 0.5f,
                (ymin + ymax) * 0.5f,
                (zmin + zmax) * 0.5f);
    }

    @Override
    public boolean contains(float x, float y, float z)  {
        return
                x >= xmin && x <= xmax &&
                y >= ymin && y <= ymax &&
                z >= zmin && z <= zmax;
    }

    // MISC

    @Override
    public AxisAlignedBBImpl clone() {
        return new AxisAlignedBBImpl(this);
    }

}
