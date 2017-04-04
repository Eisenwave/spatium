package net.grian.spatium.impl;

import net.grian.spatium.Spatium;
import net.grian.spatium.transform.Quaternion;
import net.grian.spatium.geo3.Vector3;
import net.grian.spatium.util.Strings;


public class QuaternionImpl implements Quaternion {

    private double x, y, z, w;

    public QuaternionImpl(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public QuaternionImpl(Vector3 vector, double scale) {
        this.x = vector.getX();
        this.y = vector.getY();
        this.z = vector.getZ();
        this.w = scale;
    }

    private QuaternionImpl(QuaternionImpl copyOf) {
        this(copyOf.x, copyOf.y, copyOf.z, copyOf.w);
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
    public double getW() {
        return w;
    }

    @Override
    public double getLength() {
        return Math.sqrt(dot(x, y, z, w));
    }

    @Override
    public double getLengthSquared() {
        return dot(x, y, z, w);
    }

    @Override
    public double dot(double x, double y, double z, double w) {
        return this.x*x + this.y*y + this.z*z + this.w*w;
    }

    @Override
    public Quaternion conjugate() {
        this.x = -x;
        this.y = -y;
        this.z = -z;
        return this;
    }

    @Override
    public Quaternion invert() {
        double t = 1 / getLengthSquared();
        if (t != 0) {
            this.x *= -t;
            this.y *= -t;
            this.z *= -t;
            this.w *=  t;
        }
        return this;
    }

    @Override
    public double get(int index) {
        switch (index) {
            case 0: return x;
            case 1: return y;
            case 2: return z;
            case 3: return w;
            default: throw new IndexOutOfBoundsException("quaternion does not have index "+index);
        }
    }

    // CHECKERS

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Quaternion && equals((Quaternion) obj);
    }

    // SETTERS


    @Override
    public Quaternion setX(double x) {
        this.x = x;
        return this;
    }

    @Override
    public Quaternion setY(double y) {
        this.y = y;
        return this;
    }

    @Override
    public Quaternion setZ(double z) {
        this.z = z;
        return this;
    }

    @Override
    public Quaternion setW(double w) {
        this.w = w;
        return this;
    }

    @Override
    public Quaternion set(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    //OPERATIONS
    
    @Override
    public Quaternion add(Quaternion q) {
        this.x += q.getX();
        this.y += q.getY();
        this.z += q.getZ();
        this.w += q.getW();
        return this;
    }

    @Override
    public Quaternion subtract(Quaternion q) {
        this.x -= q.getX();
        this.y -= q.getY();
        this.z -= q.getZ();
        this.w -= q.getW();
        return this;
    }

    @Override
    public Quaternion multiply(double scale) {
        this.x *= scale;
        this.y *= scale;
        this.z *= scale;
        this.w *= scale;
        return this;
    }
    
    @Override
    public Quaternion multiply(Quaternion q) {
        double
            lx = x,        ly = y,        lz = z,        lw = w,
            rx = q.getX(), ry = q.getY(), rz = q.getZ(), rw = q.getW();

        this.w = lw*rw - lx*rx - ly*ry - lz*rz;
        this.x = lw*rx + lx*rw + ly*rz - lz*ry;
        this.y = lw*ry - lx*rz + ly*rw + lz*rx;
        this.z = lw*rz + lx*ry - ly*rx + lz*rw;
        return this;
    }

    @Override
    public Quaternion divide(double divisor) {
        this.x /= divisor;
        this.y /= divisor;
        this.z /= divisor;
        this.w /= divisor;
        return this;
    }

    // MISC

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public Quaternion clone() {
        return new QuaternionImpl(this);
    }

    @Override
    public String toString() {
        String
            w = Strings.valueOf(this.w, 6),
            x = Strings.valueOf(this.x, 6),
            y = Strings.valueOf(this.y, 6),
            z = Strings.valueOf(this.z, 6);
        
        return "("+w+" + "+x+"i + "+y+"j + "+z+"k)";
    }
}
