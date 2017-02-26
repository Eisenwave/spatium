package net.grian.spatium.impl;

import net.grian.spatium.geo3.Vector3;
import net.grian.spatium.matrix.Matrix;

public class PolarVector3Impl implements Vector3 {

    private static final long serialVersionUID = -1987621226638181737L;

    private double radius, yaw, pitch;

    public PolarVector3Impl(double radius, double yaw, double pitch) {
        this.radius = radius;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    private PolarVector3Impl(PolarVector3Impl copyOf) {
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
    public Vector3 midPoint(Vector3 v, double t) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public double dot(double x, double y, double z) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Vector3 cross(double x, double y, double z) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector3 set(double x, double y, double z) {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public Vector3 setX(double x) {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public Vector3 setY(double y) {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public Vector3 setZ(double z) {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public Vector3 transform(Matrix m) {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public Vector3 add(double x, double y, double z) {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public Vector3 subtract(double x, double y, double z) {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public Vector3 multiply(double x, double y, double z) {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public Vector3 divide(double x, double y, double z) {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public Vector3 setLength(double length) {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public Vector3 setYaw(double yaw) {
        this.yaw = yaw;
        return this;
    }

    @Override
    public Vector3 setPitch(double pitch) {
        this.pitch = pitch;
        return this;
    }

    @Override
    public Vector3 setLengthYawPitch(double radius, double yaw, double pitch) {
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
    public Vector3 clone() {
        return new PolarVector3Impl(this);
    }

}
