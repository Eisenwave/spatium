package net.grian.spatium.impl;

import net.grian.spatium.geo.Vector;

public class PolarVectorImpl implements Vector {

    private static final long serialVersionUID = -1987621226638181737L;

    private float radius, yaw, pitch;

    public PolarVectorImpl(float radius, float yaw, float pitch) {
        this.radius = radius;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    private PolarVectorImpl(PolarVectorImpl copyOf) {
        this(copyOf.radius, copyOf.yaw, copyOf.pitch);
    }

    @Override
    public float getX() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public float getY() {
        return (float) Math.asin(Math.toRadians(pitch));
    }

    @Override
    public float getZ() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public float getYaw() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public float getPitch() {
        return pitch;
    }

    @Override
    public float getLength() {
        return radius;
    }

    @Override
    public float getLengthSquared() {
        return radius * radius;
    }

    @Override
    public float distanceTo(float x, float y, float z) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public float angleTo(float x, float y, float z) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Vector midPoint(Vector v, float t) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public float dot(float x, float y, float z) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Vector cross(float x, float y, float z) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector set(float x, float y, float z) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector setX(float x) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector setY(float y) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector setZ(float z) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector add(float x, float y, float z) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector subtract(float x, float y, float z) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector multiply(float x, float y, float z) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector multiply(float x) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector divide(float x, float y, float z) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector divide(float x) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector setLength(float length) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector setYaw(float yaw) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector setPitch(float pitch) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector setRadiusYawPitch(float radius, float yaw, float pitch) {
        this.radius = radius;
        this.yaw = yaw;
        this.pitch = pitch;
        return this;
    }

    @Override
    public float[] toArray() {
        return new float[] {radius, yaw, pitch};
    }

    @Override
    public Vector clone() {
        return new PolarVectorImpl(this);
    }

}
