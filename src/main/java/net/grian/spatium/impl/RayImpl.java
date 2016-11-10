package net.grian.spatium.impl;

import net.grian.spatium.Spatium;
import net.grian.spatium.geo.Ray;
import net.grian.spatium.geo.Vector;

public class RayImpl implements Ray {

    private static final long serialVersionUID = -7636360491121122898L;

    private float xo, yo, zo, xd, yd, zd;

    public RayImpl(float xo, float yo, float zo, float xd, float yd, float zd) {
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
    public float getOriginX() {
        return xo;
    }

    @Override
    public float getOriginY() {
        return yo;
    }

    @Override
    public float getOriginZ() {
        return zo;
    }

    @Override
    public float getDirX() {
        return xd;
    }

    @Override
    public float getDirY() {
        return yd;
    }

    @Override
    public float getDirZ() {
        return zd;
    }

    @Override
    public float getLength() {
        return (float) Math.sqrt(xd*xd + yd*yd + zd*zd);
    }

    @Override
    public float getLengthSquared() {
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
    public Vector getPoint(float t) {
        return Vector.fromXYZ(
                xo + xd * t,
                yo + yd * t,
                zo + zd * t
        );
    }

    @Override
    public Vector closestPointTo(Vector point) {
        Vector dir = getDirection();
        float factor = dir.dot(point) / dir.dot(dir);

        return Vector.fromXYZ(
                this.xo + this.xd * factor,
                this.yo + this.yd * factor,
                this.zo + this.zd * factor);
    }

    // SETTERS

    @Override
    public Ray setOrigin(float x, float y, float z) {
        this.xo = x;
        this.yo = y;
        this.zo = z;
        return this;
    }

    @Override
    public Ray setDirection(float x, float y, float z) {
        this.xd = x;
        this.yd = y;
        this.zd = z;
        return this;
    }

    @Override
    public Ray setLength(float t) {
        float m = t / getLength();
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
    public boolean contains(float x, float y, float z) {
        float t = (x - this.xo) / this.xd;

        return
                Spatium.equals(t, (y - this.yo) / this.yd) &&
                Spatium.equals(t, (z - this.zo) / this.zd);
    }

    @Override
    public float containsAt(float x, float y, float z) {
        float t = (x - this.xo) / this.xd;

        if (
                Spatium.equals(t, (y - this.yo) / this.yd) &&
                Spatium.equals(t, (z - this.zo) / this.zd))
            return t;

        else
            return Float.NaN;
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
    public RayImpl clone() {
        return new RayImpl(this);
    }

}
