package net.grian.spatium.impl;

import net.grian.spatium.geo.Vector;

public class PolarVectorImpl implements Vector {

    private static final long serialVersionUID = -1987621226638181737L;

    private double radius, yaw, pitch;

    public PolarVectorImpl(double radius, double yaw, double pitch) {
        this.radius = radius;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    private PolarVectorImpl(PolarVectorImpl copyOf) {
        this(copyOf.radius, copyOf.yaw, copyOf.pitch);
    }

    @Override
    public double getX() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getY() {
        return Math.asin(Math.toRadians(pitch));
    }

    @Override
    public double getZ() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getYaw() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getPitch() {
        return pitch;
    }

    @Override
    public double getLength() {
        return radius;
    }

    @Override
    public double getLengthSquared() {
        return radius * radius;
    }

    @Override
    public double distanceTo(double x, double y, double z) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double angleTo(double x, double y, double z) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Vector midPoint(Vector v, double t) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double dot(double x, double y, double z) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Vector cross(double x, double y, double z) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector set(double x, double y, double z) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector setX(double x) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector setY(double y) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector setZ(double z) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector add(double x, double y, double z) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector subtract(double x, double y, double z) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector multiply(double x, double y, double z) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector multiply(double x) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector divide(double x, double y, double z) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector divide(double x) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector setLength(double length) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector setYaw(double yaw) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector setPitch(double pitch) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector setRadiusYawPitch(double radius, double yaw, double pitch) {
        this.radius = radius;
        this.yaw = yaw;
        this.pitch = pitch;
        return this;
    }

    @Override
    public double[] toArray() {
        return new double[] {radius, yaw, pitch};
    }

    @Override
    public Vector clone() {
        return new PolarVectorImpl(this);
    }

}
