package net.grian.spatium.impl;

import net.grian.spatium.Spatium;
import net.grian.spatium.geo3.Triangle3;
import net.grian.spatium.geo3.Vector3;
import net.grian.spatium.transform.Transformation;

public class Triangle3Impl implements Triangle3 {

    private double ax, ay, az, bx, by, bz, cx, cy, cz;

    public Triangle3Impl(double ax, double ay, double az, double bx, double by, double bz, double cx, double cy, double cz) {
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

    public Triangle3Impl(Vector3 a, Vector3 b, Vector3 c) {
        this(a.getX(), a.getY(), a.getZ(), b.getX(), b.getY(), b.getZ(), c.getX(), c.getY(), c.getZ());
    }

    public Triangle3Impl() {
        this(0,0,0,0,0,0,0,0,0);
    }

    //GETTERS

    @Override
    public Vector3 getA() {
        return Vector3.fromXYZ(ax, ay, az);
    }

    @Override
    public Vector3 getB() {
        return Vector3.fromXYZ(bx, by, bz);
    }

    @Override
    public Vector3 getC() {
        return Vector3.fromXYZ(cx, cy, cz);
    }

    @Override
    public double getLengthAB() {
        return Spatium.hypot(bx-ax, by-ay, bz-az);
    }

    @Override
    public double getLengthBC() {
        return Spatium.hypot(cx-bx, cy-by, cz-bz);
    }

    @Override
    public double getLengthCA() {
        return Spatium.hypot(ax-cx, ay-cy, az-cz);
    }

    @Override
    public Vector3 getNormal() {
        Vector3 ab = Vector3.fromXYZ(bx - ax, by - ay, bz - az);
        Vector3 ac = Vector3.fromXYZ(cx - ax, cy - ay, cz - az);
        return ab.cross(ac);
    }

    @Override
    public Vector3 getCenter() {
        return Vector3.fromXYZ(
                (ax + bx + cx) / 3,
                (ay + by + cy) / 3,
                (az + bz + cz) / 3);
    }

    //SETTERS

    @Override
    public Triangle3 setA(Vector3 point) {
        this.ax = point.getX();
        this.ay = point.getY();
        this.az = point.getZ();
        return this;
    }

    @Override
    public Triangle3 setB(Vector3 point) {
        this.bx = point.getX();
        this.by = point.getY();
        this.bz = point.getZ();
        return this;
    }

    @Override
    public Triangle3 setC(Vector3 point) {
        this.cx = point.getX();
        this.cy = point.getY();
        this.cz = point.getZ();
        return this;
    }

    @Override
    public Triangle3 move(double x, double y, double z) {
        ax += x; ay += y; az += z;
        bx += x; by += y; az += z;
        cx += x; cy += y; cz += z;
        return this;
    }

    @Override
    public Triangle3 scale(double x, double y, double z) {
        ax *= x; ay *= y; az *= z;
        bx *= x; by *= y; az *= z;
        cx *= x; cy *= y; cz *= z;
        return this;
    }
    
    @Override
    public Triangle3 transform(Transformation t) {
        {
            Vector3 vertex = getA();
            t.transform(vertex);
            this.ax = vertex.getX();
            this.ay = vertex.getY();
            this.az = vertex.getZ();
        }
        {
            Vector3 vertex = getB();
            t.transform(vertex);
            this.bx = vertex.getX();
            this.by = vertex.getY();
            this.bz = vertex.getZ();
        }
        {
            Vector3 vertex = getC();
            t.transform(vertex);
            this.cx = vertex.getX();
            this.cy = vertex.getY();
            this.cz = vertex.getZ();
        }
        return this;
    }
    
    //MISC
    
    
    @Override
    public String toString() {
        return getClass().getSimpleName()+"["+getA()+","+getB()+","+getC()+"]";
    }
    
}
