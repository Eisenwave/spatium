package net.grian.spatium.impl;

import net.grian.spatium.Spatium;
import net.grian.spatium.geo.Plane;
import net.grian.spatium.geo.Vector;

public class PlaneImpl implements Plane {

    private static final long serialVersionUID = 196722359952800225L;

    private double x, y, z, xn, yn, zn;

    public PlaneImpl(double x, double y, double z, double xn, double yn, double zn) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.xn = xn;
        this.yn = yn;
        this.zn = zn;
    }

    public PlaneImpl(double a, double b, double c, double d) {
        double p = d / (a + b + c);
        this.x = p;
        this.y = p;
        this.z = p;
        this.xn = a;
        this.yn = b;
        this.zn = c;
    }

    public PlaneImpl(PlaneImpl copyOf) {
        this.x = copyOf.x;
        this.y = copyOf.y;
        this.z = copyOf.z;
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
        return Vector.fromXYZ(x, y, z);
    }

    @Override
    public Vector getNormal() {
        return Vector.fromXYZ(xn, yn, zn);
    }

    @Override
    public double getDepth() {
        //normal * point
        return xn*x + yn*y + zn*z;
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
        return Spatium.equals((x-x)*xn + (y-y)*yn + (z-z)*zn, 0) ;
    }

    // GETTERS

    @Override
    public Plane setCenter(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
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
        return "("+xn+", "+yn+", "+zn+") * ((x, y, z) - ("+x+", "+y+", "+z+"))";
    }
}
