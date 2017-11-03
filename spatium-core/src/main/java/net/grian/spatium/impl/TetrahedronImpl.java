package net.grian.spatium.impl;

import net.grian.spatium.geo3.Tetrahedron;
import net.grian.spatium.geo3.Triangle3;
import net.grian.spatium.geo3.Vector3;

public class TetrahedronImpl implements Tetrahedron {
    
    private double
        ax, ay, az,
        bx, by, bz,
        cx, cy, cz,
        dx, dy, dz;
    
    public TetrahedronImpl(
        double ax, double ay, double az,
        double bx, double by, double bz,
        double cx, double cy, double cz,
        double dx, double dy, double dz) {
        this.ax = ax; this.ay = ay; this.az = az;
        this.bx = bx; this.by = by; this.bz = bz;
        this.cx = cx; this.cy = cy; this.cz = cz;
        this.dx = dx; this.dy = dy; this.dz = dz;
    }
    
    public TetrahedronImpl(Vector3 a, Vector3 b, Vector3 c, Vector3 d) {
        this.ax = a.getX(); this.ay = a.getY(); this.az = a.getZ();
        this.bx = b.getX(); this.by = b.getY(); this.bz = b.getZ();
        this.cx = c.getX(); this.cy = c.getY(); this.cz = c.getZ();
        this.dx = d.getX(); this.dy = d.getY(); this.dz = d.getZ();
    }
    
    public TetrahedronImpl(TetrahedronImpl copyOf) {
        this.ax = copyOf.ax; this.ay = copyOf.ay; this.az = copyOf.az;
        this.bx = copyOf.bx; this.by = copyOf.by; this.bz = copyOf.bz;
        this.cx = copyOf.cx; this.cy = copyOf.cy; this.cz = copyOf.cz;
        this.dx = copyOf.dx; this.dy = copyOf.dy; this.dz = copyOf.dz;
    }
    
    // GETTERS
    
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
    public Vector3 getD() {
        return Vector3.fromXYZ(dx, dy, dz);
    }
    
    @Override
    public Vector3 getCenter() {
        return Vector3.fromXYZ(
            (ax+bx+cx+dx) /4,
            (ay+by+cy+dy) /4,
            (az+bz+cz+dz) /4);
    }
    
    @Override
    public Triangle3 getTriangle(int index) {
        switch (index) {
            case 0: return Triangle3.fromPoints(ax, ay, az,    bx, by, bz,    cx, cy, cz);
            case 1: return Triangle3.fromPoints(bx, by, bz,    cx, cy, cz,    dx, dy, dz);
            case 2: return Triangle3.fromPoints(cx, cy, cz,    dx, dy, dz,    ax, ay, az);
            case 3: return Triangle3.fromPoints(dx, dy, dz,    ax, ay, az,    bx, by, bz);
            default: throw new IndexOutOfBoundsException(index+" must be in range(0,3)");
        }
    }
    
    // SETTERS
    
    @Override
    public void setA(Vector3 point) {
        this.ax = point.getX();
        this.ax = point.getY();
        this.az = point.getZ();
    }
    
    @Override
    public void setB(Vector3 point) {
        this.bx = point.getX();
        this.by = point.getY();
        this.bz = point.getZ();
    }
    
    @Override
    public void setC(Vector3 point) {
        this.cx = point.getX();
        this.cy = point.getY();
        this.cz = point.getZ();
    }
    
    @Override
    public void setD(Vector3 point) {
        this.dx = point.getX();
        this.dy = point.getY();
        this.dz = point.getZ();
    }
    
    // TRANSFORMATIONS
    
    @Override
    public void scale(double x, double y, double z) {
        this.ax *= x; this.ay *= y; this.az *= z;
        this.bx *= x; this.by *= y; this.bz *= z;
        this.cx *= x; this.cy *= y; this.cz *= z;
        this.dx *= x; this.dy *= y; this.dz *= z;
    }
    
    @Override
    public void translate(double x, double y, double z) {
        this.ax += x; this.ay += y; this.az += z;
        this.bx += x; this.by += y; this.bz += z;
        this.cx += x; this.cy += y; this.cz += z;
        this.dx += x; this.dy += y; this.dz += z;
    }
    
    // MISC
    
    @Override
    public String toString() {
        return Tetrahedron.class.getSimpleName() + "["+getA()+","+getB()+","+getC()+","+getD()+"]";
    }
    
    @Override
    public Tetrahedron clone() {
        return new TetrahedronImpl(this);
    }
    
}
