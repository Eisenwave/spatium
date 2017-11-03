package net.grian.spatium.impl;

import net.grian.spatium.cache.CacheMath;
import net.grian.spatium.geo3.Path3;
import net.grian.spatium.geo3.Sphere;
import net.grian.spatium.geo3.Vector3;
import net.grian.spatium.transform.Quaternion;

public class Path3ImplCircle implements Path3 {

    private final double cx, cy, cz, r, nx, ny, nz;

    public Path3ImplCircle(double ox, double oy, double oz, double r, double nx, double ny, double nz) {
        this.cx = ox;
        this.cy = oy;
        this.cz = oz;
        this.r = r;
        this.nx = nx;
        this.ny = ny;
        this.nz = nz;
    }

    public Path3ImplCircle(Sphere sphere, Vector3 normal) {
        this(
                sphere.getX(), sphere.getY(), sphere.getZ(), sphere.getRadius(),
                normal.getX(), normal.getY(), normal.getZ());
    }

    public Path3ImplCircle(double ox, double oy, double oz, double r, Vector3 normal) {
        this(ox, oy, oz, r, normal.getX(), normal.getY(), normal.getZ());
    }

    public Path3ImplCircle(Sphere sphere, double nx, double ny, double nz) {
        this(sphere.getX(), sphere.getY(), sphere.getZ(), sphere.getRadius(), nx, ny, nz);
    }

    public Path3ImplCircle(Vector3 center, double radius, Vector3 normal) {
        this(center.getX(), center.getY(), center.getZ(), radius, normal);
    }

    @Override
    public Vector3 getPoint(double t) {
        Quaternion rotation = toRotation(t * CacheMath.TAU);
        return Quaternion.product(rotation, getRelativeOrigin()).add(cx, cy, cz);
    }

    @Override
    public Vector3 getOrigin() {
        return getCenter().add(getRelativeOrigin());
    }

    public Vector3 getCenter() {
        return Vector3.fromXYZ(cx, cy, cz);
    }

    public Vector3 getRelativeOrigin() {
        return Vector3.fromXYZ(nx, 0, -1/nz).setLength(r);
    }

    @Override
    public Vector3 getEnd() {
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
    public Vector3[] getControlPoints() {
        return new Vector3[] {Vector3.fromXYZ(cx, cy, cz)};
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
