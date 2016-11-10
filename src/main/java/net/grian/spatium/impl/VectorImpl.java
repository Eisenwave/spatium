package net.grian.spatium.impl;

import net.grian.spatium.Spatium;
import net.grian.spatium.geo.BlockVector;
import net.grian.spatium.geo.Vector;

import java.util.Arrays;

public class VectorImpl implements Vector {

    private static final long serialVersionUID = 6745733279751595544L;

    private float x, y, z;

    public VectorImpl(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public VectorImpl(BlockVector block) {
        this(block.getX(), block.getY(), block.getZ());
    }

    public VectorImpl() {
        this(0, 0, 0);
    }

    public VectorImpl(VectorImpl copyOf) {
        this(copyOf.x, copyOf.y, copyOf.z);
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getZ() {
        return z;
    }

    @Override
    public float getYaw() {
        return Spatium.degrees( (float)-Math.atan2(x, z) );
    }

    @Override
    public float getPitch() {
        return Spatium.degrees( (float) Math.atan(y / Math.sqrt(x*x + z*z)) );
    }

    @Override
    public float angleTo(Vector vector) {
        return (float) Math.acos( dot(vector) / this.getLength() * vector.getLength() );
    }

    @Override
    public float getLength() {
        return (float) Math.sqrt(x*x + y*y + z*z);
    }

    @Override
    public float getLengthSquared() {
        return x*x + y*y + z*z;
    }

    /**
     * Returns the lenght of this vector using
     * {@link Math#hypot(double, double)}, preventing a possible overflow.
     * Use {@link #getLength()} for performance reasons instead when there is
     * no such possibility.
     * @return the length of the vector
     */
    public float getLengthSafe() {
        return (float) Math.hypot(Math.hypot(x, y), z);
    }

    @Override
    public float dot(float x, float y, float z) {
        return this.x * x + this.y * y + this.z + z;
    }

    @Override
    public Vector cross(float x, float y, float z) {
        return new VectorImpl(
                (this.y * z - this.z * y),
                (this.z * x - this.x * z),
                (this.x * y - this.y * x)
                );
    }

    @Override
    public float distanceTo(float x, float y, float z) {
        float
        dx = this.x - x,
        dy = this.y - y,
        dz = this.z - z;

        return (float) Math.sqrt(dx*dx + dy*dy + dz*dz);
    }

    @Override
    public float angleTo(float x, float y, float z) {
        return (float) Math.acos( dot(x, y, z) / (this.getLength() * Math.sqrt(x*x + y*y + z*z)));
    }

    @Override
    public Vector midPoint(Vector v, float t) {
        return new VectorImpl(
                this.getX() + (v.getX() - this.getX()) * t,
                this.getX() + (v.getY() - this.getY()) * t,
                this.getX() + (v.getZ() - this.getZ()) * t);
    }

    // SETTERS

    @Override
    public Vector set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    @Override
    public Vector setX(float x) {
        this.x = x;
        return this;
    }

    @Override
    public Vector setY(float y) {
        this.y = y;
        return this;
    }

    @Override
    public Vector setZ(float z) {
        this.z = z;
        return this;
    }

    @Override
    public Vector setYaw(float yaw) {
        double
        yaw2 = Math.toRadians(yaw),
        r = getLength();

        this.x = (float) (Math.sin(-yaw2) * r);
        this.z = (float) (Math.cos( yaw2) * r);
        return this;
    }

    @Override
    public Vector setPitch(float pitch) {
        double
        pitch2 = -Math.toRadians(pitch),
        r = getLength();

        this.y = (float) (Math.tan(pitch2) * r);
        return this;
    }

    @Override
    public Vector setRadiusYawPitch(float radius, float yaw, float pitch) {
        double
        r = radius * getLength(),
        y = Math.toRadians(yaw),
        p = -Math.toRadians(pitch);

        this.x = (float) (Math.sin(-y) * r);
        this.y = (float) (Math.tan( p) * r);
        this.z = (float) (Math.cos( y) * r);
        return this;
    }

    @Override
    public Vector add(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    @Override
    public Vector subtract(float x, float y, float z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    @Override
    public Vector multiply(float x, float y, float z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    @Override
    public Vector multiply(float x) {
        return multiply(x, x, x);
    }

    @Override
    public Vector divide(float x, float y, float z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }

    @Override
    public Vector divide(float x) {
        return divide(x, x, x);
    }

    @Override
    public Vector setLength(float length) {
        float n = length / getLength();
        x *= n;
        y *= n;
        z *= n;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Vector && equals((Vector) obj);
    }

    // MISC


    @Override
    public String toString() {
        return "("+x+", "+y+", "+z+")";
    }

    @Override
    public float[] toArray() {
        return new float[] {x, y, z};
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(toArray());
    }

    @Override
    public Vector clone() {
        return new VectorImpl(this);
    }

}
