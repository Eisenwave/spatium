package net.grian.spatium.impl;

import net.grian.spatium.cache.CacheMath;
import net.grian.spatium.geo.Path;
import net.grian.spatium.geo.Sphere;
import net.grian.spatium.geo.Vector;
import net.grian.spatium.transform.Quaternion;
import net.grian.spatium.transform.Transformations;

public class PathImplCircle implements Path {

    private final double cx, cy, cz, r, nx, ny, nz;

    public PathImplCircle(double ox, double oy, double oz, double r, double nx, double ny, double nz) {
        this.cx = ox;
        this.cy = oy;
        this.cz = oz;
        this.r = r;
        this.nx = nx;
        this.ny = ny;
        this.nz = nz;
    }

    public PathImplCircle(Sphere sphere, Vector normal) {
        this(
                sphere.getX(), sphere.getY(), sphere.getZ(), sphere.getRadius(),
                normal.getX(), normal.getY(), normal.getZ());
    }

    public PathImplCircle(double ox, double oy, double oz, double r, Vector normal) {
        this(ox, oy, oz, r, normal.getX(), normal.getY(), normal.getZ());
    }

    public PathImplCircle(Sphere sphere, double nx, double ny, double nz) {
        this(sphere.getX(), sphere.getY(), sphere.getZ(), sphere.getRadius(), nx, ny, nz);
    }

    public PathImplCircle(Vector center, double radius, Vector normal) {
        this(center.getX(), center.getY(), center.getZ(), radius, normal);
    }

    @Override
    public Vector getPoint(double t) {
        Vector p = getRelativeOrigin();
        Quaternion q = toRotation(t * CacheMath.TAU);
        Transformations.rotate(p, q);
        //System.out.println("t="+t+" ("+ Spatium.degrees(t*CacheMath.TAU)+") -> "+p);
        return p.add(cx, cy, cz);
    }

    @Override
    public Vector getOrigin() {
        return getCenter().add(getRelativeOrigin());
    }

    public Vector getCenter() {
        return Vector.fromXYZ(cx, cy, cz);
    }

    public Vector getRelativeOrigin() {
        return Vector.fromXYZ(nx, 0, -1/nz).setLength(r);
    }

    @Override
    public Vector getEnd() {
        return getOrigin();
    }

    @Override
    public double getLength() {
        return r;
    }

    @Override
    public double getLengthSquared() {
        return r * r;
    }

    @Override
    public Vector[] getControlPoints() {
        return new Vector[] {Vector.fromXYZ(cx, cy, cz)};
    }

    /**
     * Is a tautology since a circle is always a closed path.
     *
     * @return true
     */
    @Override
    public boolean isClosed() {
        return true;
    }

    /**
     * Converts this circle into a rotation.
     *
     * @param theta the angle in radians
     * @return a new rotation
     */
    public Quaternion toRotation(double theta) {
        return Quaternion.fromRotation(nx, ny, nz, theta);
    }

}
