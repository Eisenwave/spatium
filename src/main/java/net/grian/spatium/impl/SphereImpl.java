package net.grian.spatium.impl;

import net.grian.spatium.geo3.Sphere;
import net.grian.spatium.geo3.Vector3;

public class SphereImpl implements Sphere {

    private static final long serialVersionUID = 9087738324681226586L;

    private double x, y, z, r;

    public SphereImpl(double x, double y, double z, double r) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.r = r;
    }

    public SphereImpl() {
        this(0, 0, 0, 0);
    }

    public SphereImpl(SphereImpl sphere) {
        this(sphere.x, sphere.y, sphere.z, sphere.r);
    }

    public SphereImpl(Vector3 center, double r) {
        this(center.getX(), center.getY(), center.getZ(), r);
    }

    // GETTERS

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getZ() {
        return z;
    }

    @Override
    public Vector3 getCenter() {
        return Vector3.fromXYZ(x, y, z);
    }

    @Override
    public double getRadius() {
        return r;
    }

    @Override
    public double getRadiusSquared() {
        return r*r;
    }

    private final static double four_thirds_pi = (double) ((4d / 3d) * Math.PI);

    @Override
    public double getVolume() {
        double r = getRadius();
        return four_thirds_pi * r*r*r;
    }

    private final static double four_pi = (double) (4 * Math.PI);

    @Override
    public double getSurfaceArea() {
        double r = getRadius();
        return four_pi * r*r;
    }

    // CHECKERS

    @Override
    public boolean contains(double x, double y, double z) {
        double
        xd = this.x - x,
        yd = this.y - y,
        zd = this.z - z;

        return Math.sqrt(xd*xd + yd*yd + zd*zd) <= r;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Sphere && equals((Sphere) obj);
    }

    // SETTERS

    @Override
    public void setCenter(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void scale(double factor) {
        if (factor < 0) throw new IllegalArgumentException("factor < 0");
        this.r *= factor;
    }

    @Override
    public void translate(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    @Override
    public void setRadius(double radius) {
        if (radius < 0) throw new IllegalArgumentException("radius < 0");
        this.r = radius;
    }

    // MISC


    @Override
    public String toString() {
        return getClass().getSimpleName()+"{center="+getCenter()+",r="+getRadius()+"}";
    }

    @Override
    public Sphere clone() {
        return new SphereImpl(this);
    }

}
