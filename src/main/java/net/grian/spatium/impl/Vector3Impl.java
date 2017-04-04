package net.grian.spatium.impl;

import net.grian.spatium.Spatium;
import net.grian.spatium.cache.CacheMath;
import net.grian.spatium.geo3.BlockVector;
import net.grian.spatium.geo3.Vector3;
import net.grian.spatium.matrix.Matrix;
import net.grian.spatium.matrix.MatrixDimensionsException;

import java.util.Arrays;

public class Vector3Impl implements Vector3 {

    private static final long serialVersionUID = 6745733279751595544L;

    private double x, y, z;

    public Vector3Impl(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3Impl(BlockVector block) {
        this(block.getX(), block.getY(), block.getZ());
    }

    public Vector3Impl() {
        this(0, 0, 0);
    }

    public Vector3Impl(Vector3Impl copyOf) {
        this(copyOf.x, copyOf.y, copyOf.z);
    }

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
    public double getYaw() {
        return Spatium.degrees( getYawRad() );
    }

    @Override
    public double getPitch() {
        return Spatium.degrees( getPitchRad() );
    }
    
    @SuppressWarnings("SuspiciousNameCombination")
    public double getYawRad() {
        return -Math.atan2(x, z);
    }
    
    @SuppressWarnings("SuspiciousNameCombination")
    public double getPitchRad() {
        /* singularity is not being handled explicitly anymore since Math.atan handles it automatically
        final double dot = x*x + z*z;
        if (Spatium.isZero(dot))
            return y >= 0? -CacheMath.HALF_PI : CacheMath.HALF_PI; */
        return -Math.atan( y / Spatium.hypot(x, z) );
    }

    @Override
    public double angleTo(Vector3 vector) {
        return Math.acos( dot(vector) / this.getLength() * vector.getLength() );
    }

    @Override
    public double getLength() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    @Override
    public double getLengthSquared() {
        return x*x + y*y + z*z;
    }

    /**
     * Returns the lenght of this vector using
     * {@link Math#hypot(double, double)}, preventing a possible overflow.
     * Use {@link #getLength()} for performance reasons instead when there is
     * no such possibility.
     * @return the hypot of the vector
     */
    public double getLengthSafe() {
        return Math.hypot(Math.hypot(x, y), z);
    }

    @Override
    public double dot(double x, double y, double z) {
        return this.x*x + this.y*y + this.z*z;
    }

    @Override
    public Vector3 cross(double x, double y, double z) {
        return new Vector3Impl(
            this.y * z - this.z * y,
            this.z * x - this.x * z,
            this.x * y - this.y * x);
    }

    @Override
    public double distanceTo(double x, double y, double z) {
        double
            dx = this.x - x,
            dy = this.y - y,
            dz = this.z - z;

        return Math.sqrt(dx*dx + dy*dy + dz*dz);
    }

    @Override
    public double angleTo(double x, double y, double z) {
        //v1 * v2 = cos(theta) * |v1| * |v2|
        //theta = acos( v1*v2 / (|v1| * |v2|) )
        return Math.acos( dot(x, y, z) / (this.getLength() * Math.sqrt(x*x + y*y + z*z)) );
    }

    @Override
    public Vector3 midPoint(Vector3 v, double t) {
        return new Vector3Impl(
            this.getX() + (v.getX() - this.getX()) * t,
            this.getX() + (v.getY() - this.getY()) * t,
            this.getX() + (v.getZ() - this.getZ()) * t);
    }
    
    @Override
    public Vector3 midPoint(Vector3 point) {
        return new Vector3Impl(
            (x+point.getX()) / 2,
            (y+point.getY()) / 2,
            (z+point.getZ()) / 2);
    }
    
    // SETTERS

    @Override
    public Vector3 set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    @Override
    public Vector3 setX(double x) {
        this.x = x;
        return this;
    }

    @Override
    public Vector3 setY(double y) {
        this.y = y;
        return this;
    }

    @Override
    public Vector3 setZ(double z) {
        this.z = z;
        return this;
    }

    @Override
    public Vector3 transform(Matrix m) {
        if (m.getRows() != 3 || m.getColumns() != 3)
            throw new MatrixDimensionsException("matrix must be a 3x3 matrix");

        return set(
                m.get(0,0)*x + m.get(0,1)*y + m.get(0,2)*z,
                m.get(1,0)*x + m.get(1,1)*y + m.get(1,2)*z,
                m.get(2,0)*x + m.get(2,1)*y + m.get(2,2)*z);
    }

    @Override
    public Vector3 setYaw(double yaw) {
        final double
            yaw2 = Spatium.radians(yaw),
            xz = Spatium.hypot(x, z);
        this.x = Math.sin(-yaw2) * xz;
        this.z = Math.cos( yaw2) * xz;
        return this;
    }

    @Override
    public Vector3 setPitch(double pitch) {
        final double
            r = getLength(),
            pitch2 = Spatium.radians(pitch),
            xz = r * Math.cos(pitch2),
            factor = xz / Spatium.hypot(x, z);
        this.x *= factor;
        this.z *= factor;
        this.y = -Math.tan(pitch2) * xz;
        return this;
        //return setRYP(getLength(), getYawRad(), Spatium.radians(pitch));
    }

    @Override
    public Vector3 setLengthYawPitch(double r, double yaw, double pitch) {
        return setRYP(r, Spatium.radians(yaw), Spatium.radians(pitch));
    }
    
    /**
     * Internal method for adjusting yaw and pitch.
     *
     * @param r the radius
     * @param yaw the yaw
     * @param pitch the pitch
     */
    private Vector3 setRYP(double r, double yaw, double pitch) {
        if (Spatium.equals(Math.abs(pitch), CacheMath.HALF_PI)) {
            this.x = 0;
            this.z = 0;
            this.y = pitch>=0? -r : r;
        } else {
            r *= Math.cos(pitch);
            this.x = Math.sin(-yaw) * r;
            this.z = Math.cos( yaw) * r;
            this.y = -Math.tan(pitch) * Spatium.hypot(x, z);
        }
        return this;
    }

    @Override
    public Vector3 add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    @Override
    public Vector3 subtract(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    @Override
    public Vector3 multiply(double x, double y, double z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    @Override
    public Vector3 multiply(double factor) {
        this.x *= factor;
        this.y *= factor;
        this.z *= factor;
        return this;
    }

    @Override
    public Vector3 negate() {
        this.x = -x;
        this.y = -y;
        this.z = -z;
        return this;
    }
    
    @Override
    public Vector3 normalize() {
        final double length = getLength();
        this.x /= length;
        this.y /= length;
        this.z /= length;
        return this;
    }
    
    @Override
    public Vector3 divide(double x, double y, double z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }

    @Override
    public Vector3 divide(double divisor) {
        this.x /= divisor;
        this.y /= divisor;
        this.z /= divisor;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Vector3 && equals((Vector3) obj);
    }

    // MISC
    
    @Override
    public String toString() {
        /*
        String x = Strings.valueOf(this.x, 8);
        String y = Strings.valueOf(this.y, 8);
        String z = Strings.valueOf(this.z, 8);
        */
        
        return "("+x+","+y+","+z+")";
    }

    @Override
    public double[] toArray() {
        return new double[] {x, y, z};
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(toArray());
    }

    @Override
    public Vector3 clone() {
        return new Vector3Impl(this);
    }

}
