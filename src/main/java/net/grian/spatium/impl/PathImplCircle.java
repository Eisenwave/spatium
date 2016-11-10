package net.grian.spatium.impl;

import net.grian.spatium.geo.Path;
import net.grian.spatium.geo.Sphere;
import net.grian.spatium.geo.Vector;
import net.grian.spatium.transform.Quaternion;
import net.grian.spatium.transform.Transformations;

public class PathImplCircle implements Path {

    private final float ox, oy, oz, r, nx, ny, nz;

    public PathImplCircle(float ox, float oy, float oz, float r, float nx, float ny, float nz) {
        this.ox = ox;
        this.oy = oy;
        this.oz = oz;
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

    public PathImplCircle(float ox, float oy, float oz, float r, Vector normal) {
        this(ox, oy, oz, r, normal.getX(), normal.getY(), normal.getZ());
    }

    public PathImplCircle(Sphere sphere, float nx, float ny, float nz) {
        this(sphere.getX(), sphere.getY(), sphere.getZ(), sphere.getRadius(), nx, ny, nz);
    }

    @Override
    public Vector getPoint(float t) {
        Vector p = getOrigin();
        Quaternion q = toRotation((float) (t * 0.5 * Math.PI));

        return Transformations.rotate(p, q);
    }

    @Override
    public Vector getOrigin() {
        return Vector.fromXYZ(
                ox + nx * r,
                oy + ny * r,
                oz + nz * r);
    }

    @Override
    public Vector getEnd() {
        return getOrigin();
    }

    @Override
    public float getLength() {
        return r;
    }

    @Override
    public float getLengthSquared() {
        return r * r;
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
    public Quaternion toRotation(float theta) {
        return Quaternion.fromRotation(nx, ny, nz, theta);
    }

}
