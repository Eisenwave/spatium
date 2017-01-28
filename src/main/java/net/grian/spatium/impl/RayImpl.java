package net.grian.spatium.impl;

import net.grian.spatium.Spatium;
import net.grian.spatium.geo.Ray;
import net.grian.spatium.geo.Vector;

public class RayImpl implements Ray {

    private static final long serialVersionUID = -7636360491121122898L;

    private double xo, yo, zo, xd, yd, zd;

    public RayImpl(double xo, double yo, double zo, double xd, double yd, double zd) {
        this.xo = xo;
        this.yo = yo;
        this.zo = zo;
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
    }

    public RayImpl(Vector origin, Vector dir) {
        this.xo = origin.getX();
        this.yo = origin.getY();
        this.zo = origin.getZ();
        this.xd = dir.getX();
        this.yd = dir.getY();
        this.zd = dir.getZ();
    }

    public RayImpl(RayImpl ray) {
        this(ray.xo, ray.yo, ray.zo, ray.xd, ray.yd, ray.zd);
    }

    // GETTERS

    @Override
    public double getOriginX() {
        return xo;
    }

    @Override
    public double getOriginY() {
        return yo;
    }

    @Override
    public double getOriginZ() {
        return zo;
    }

    @Override
    public double getDirX() {
        return xd;
    }

    @Override
    public double getDirY() {
        return yd;
    }

    @Override
    public double getDirZ() {
        return zd;
    }

    @Override
    public double getLength() {
        return Math.sqrt(xd*xd + yd*yd + zd*zd);
    }

    @Override
    public double getLengthSquared() {
        return xd*xd + yd*yd + zd*zd;
    }

    @Override
    public Vector getOrigin() {
        return Vector.fromXYZ(xo, yo, zo);
    }

    @Override
    public Vector getDirection() {
        return Vector.fromXYZ(xd, yd, zd);
    }

    @Override
    public Vector getEnd() {
        return Vector.fromXYZ(xo + xd, yo + yd, zo + zd);
    }

    @Override
    public Vector getPoint(double t) {
        return Vector.fromXYZ(
                xo + xd * t,
                yo + yd * t,
                zo + zd * t
        );
    }

    // SETTERS

    @Override
    public Ray setOrigin(double x, double y, double z) {
        this.xo = x;
        this.yo = y;
        this.zo = z;
        return this;
    }

    @Override
    public Ray setDirection(double x, double y, double z) {
        this.xd = x;
        this.yd = y;
        this.zd = z;
        return this;
    }

    @Override
    public Ray setLength(double t) {
        double m = t / getLength();
        this.xd *= m;
        this.yd *= m;
        this.zd *= m;
        return this;
    }

    // CHECKERS

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Ray && equals((Ray) obj);
    }

    @Override
    public boolean contains(double x, double y, double z) {
        double t = (x - this.xo) / this.xd;

        return
                Spatium.equals(t, (y - this.yo) / this.yd) &&
                Spatium.equals(t, (z - this.zo) / this.zd);
    }

    @Override
    public double containsAt(double x, double y, double z) {
        double t = (x - this.xo) / this.xd;

        if (
                Spatium.equals(t, (y - this.yo) / this.yd) &&
                Spatium.equals(t, (z - this.zo) / this.zd))
            return t;

        else
            return Double.NaN;
    }

    @Override
    public boolean equals(Ray ray) {
        return
                Spatium.equals(xo, ray.getOriginX()) &&
                Spatium.equals(yo, ray.getOriginY()) &&
                Spatium.equals(zo, ray.getOriginZ()) &&
                Spatium.equals(xd, ray.getDirX()) &&
                Spatium.equals(yd, ray.getDirY()) &&
                Spatium.equals(zd, ray.getDirZ());
    }

    // MISC


    @Override
    public String toString() {
        return getClass().getSimpleName()+"{o="+getOrigin()+",d="+getDirection()+"}";
    }

    @Override
    public RayImpl clone() {
        return new RayImpl(this);
    }

}
