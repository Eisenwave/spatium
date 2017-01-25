package net.grian.spatium.impl;

import net.grian.spatium.Spatium;
import net.grian.spatium.geo.Plane;
import net.grian.spatium.geo.Vector;

public class PlaneImpl implements Plane {

    private static final long serialVersionUID = 196722359952800225L;

    private double xc, yc, zc, xn, yn, zn;

    public PlaneImpl(double xc, double yc, double zc, double xn, double yn, double zn) {
        this.xc = xc;
        this.yc = yc;
        this.zc = zc;
        this.xn = xn;
        this.yn = yn;
        this.zn = zn;
    }

    public PlaneImpl(double a, double b, double c, double d) {
        double p = d / (a + b + c);
        this.xc = p;
        this.yc = p;
        this.zc = p;
        this.xn = a;
        this.yn = b;
        this.zn = c;
    }

    public PlaneImpl(PlaneImpl copyOf) {
        this.xc = copyOf.xc;
        this.yc = copyOf.yc;
        this.zc = copyOf.zc;
        this.xn = copyOf.xn;
        this.yn = copyOf.yn;
        this.zn = copyOf.zn;
    }

    public PlaneImpl(Vector center, Vector normal) {
        this(
                center.getX(), center.getY(), center.getZ(),
                normal.getX(), normal.getY(), normal.getZ());
    }

    public PlaneImpl(Vector point, Vector t, Vector r) {
        this(point, t.cross(r));
    }

    public PlaneImpl() {
        this(0, 0, 0, 0, 0, 0);
    }

    // GETTERS

    @Override
    public Vector getPoint() {
        return Vector.fromXYZ(xc, yc, zc);
    }

    @Override
    public Vector getNormal() {
        return Vector.fromXYZ(xn, yn, zn);
    }

    @Override
    public double getDepth() {
        return xn*xc + yn*yc + zn*zc;
    }

    // CHECKERS

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Plane && equals((Plane) obj);
    }

    @Override
    public boolean contains(double x, double y, double z) {
        //if the dot product of a vector from the center of the plane to the point and the plane normal are
        //orthogonal, the point lies in the plane
        return Spatium.equals((x-xc)*xn + (y-yc)*yn + (z-zc)*zn, 0) ;
    }

    // GETTERS

    @Override
    public Plane setCenter(double x, double y, double z) {
        this.xc = x;
        this.yc = y;
        this.zc = z;
        return this;
    }

    @Override
    public Plane setNormal(double x, double y, double z) {
        this.xn = x;
        this.yn = y;
        this.zn = z;
        return this;
    }

    // MISC

    @Override
    public Plane clone() {
        return new PlaneImpl(this);
    }

    @Override
    public String toString() {
        return "("+xn+", "+yn+", "+zn+") * ((x, y, z) - ("+xc+", "+yc+", "+zc+"))";
    }
}
