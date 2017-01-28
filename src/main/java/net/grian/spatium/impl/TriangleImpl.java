package net.grian.spatium.impl;

import net.grian.spatium.Spatium;
import net.grian.spatium.coll.Distances;
import net.grian.spatium.geo.Triangle;
import net.grian.spatium.geo.Vector;

public class TriangleImpl implements Triangle {

    private double ax, ay, az, bx, by, bz, cx, cy, cz;

    public TriangleImpl(double ax, double ay, double az, double bx, double by, double bz, double cx, double cy, double cz) {
        this.ax = ax;
        this.ay = ay;
        this.az = az;
        this.bx = bx;
        this.by = by;
        this.bz = bz;
        this.cx = cx;
        this.cy = cy;
        this.cz = cz;
    }

    public TriangleImpl(Vector a, Vector b, Vector c) {
        this(a.getX(), a.getY(), a.getZ(), b.getX(), b.getY(), b.getZ(), c.getX(), c.getY(), c.getZ());
    }

    public TriangleImpl() {
        this(0,0,0,0,0,0,0,0,0);
    }

    //GETTERS

    @Override
    public Vector getA() {
        return Vector.fromXYZ(ax, ay, az);
    }

    @Override
    public Vector getB() {
        return Vector.fromXYZ(bx, by, bz);
    }

    @Override
    public Vector getC() {
        return Vector.fromXYZ(cx, cy, cz);
    }

    @Override
    public double getLengthA() {
        return Spatium.hypot(bx-ax, by-ay, bz-az);
    }

    @Override
    public double getLengthB() {
        return Spatium.hypot(cx-bx, cy-by, cz-bz);
    }

    @Override
    public double getLengthC() {
        return Spatium.hypot(ax-cx, ay-cy, az-cz);
    }

    @Override
    public Vector getNormal() {
        Vector ab = Vector.fromXYZ(bx - ax, by - ay, bz - az);
        Vector ac = Vector.fromXYZ(cx - ax, cy - ay, cz - az);
        return ab.cross(ac);
    }

    @Override
    public Vector getCenter() {
        return Vector.fromXYZ(
                (ax + bx + cx) / 3,
                (ay + by + cy) / 3,
                (az + bz + cz) / 3);
    }

    //SETTERS

    @Override
    public Triangle setA(Vector point) {
        this.ax = point.getX();
        this.ay = point.getY();
        this.az = point.getZ();
        return this;
    }

    @Override
    public Triangle setB(Vector point) {
        this.bx = point.getX();
        this.by = point.getY();
        this.bz = point.getZ();
        return this;
    }

    @Override
    public Triangle setC(Vector point) {
        this.cx = point.getX();
        this.cy = point.getY();
        this.cz = point.getZ();
        return this;
    }

    @Override
    public Triangle move(double x, double y, double z) {
        ax += x; ay += y; az += z;
        bx += x; by += y; az += z;
        cx += x; cy += y; cz += z;
        return this;
    }

    @Override
    public Triangle scale(double x, double y, double z) {
        ax *= x; ay *= y; az *= z;
        bx *= x; by *= y; az *= z;
        cx *= x; cy *= y; cz *= z;
        return this;
    }
}
