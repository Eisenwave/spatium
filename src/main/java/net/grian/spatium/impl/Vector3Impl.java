package net.grian.spatium.impl;

import net.grian.spatium.Spatium;
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

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    public double getYaw() {
        return Spatium.degrees(-Math.atan2(x, z));
    }

    @Override
    public double getPitch() {
        return Spatium.degrees(Math.atan(y / Math.sqrt(x*x + z*z)));
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
                (this.y * z - this.z * y),
                (this.z * x - this.x * z),
                (this.x * y - this.y * x));
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
        return Math.acos( dot(x, y, z) / (this.getLength() * Math.sqrt(x*x + y*y + z*z)));
    }

    @Override
    public Vector3 midPoint(Vector3 v, double t) {
        return new Vector3Impl(
                this.getX() + (v.getX() - this.getX()) * t,
                this.getX() + (v.getY() - this.getY()) * t,
                this.getX() + (v.getZ() - this.getZ()) * t);
    }

    // SETTERS

    @Override
    public void set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public void setZ(double z) {
        this.z = z;
    }

    @Override
    public void transform(Matrix m) {
        if (m.getRows() != 3 || m.getColumns() != 3)
            throw new MatrixDimensionsException("matrix must be a 3x3 matrix");

        set(
                m.get(0,0)*x + m.get(0,1)*y + m.get(0,2)*z,
                m.get(1,0)*x + m.get(1,1)*y + m.get(1,2)*z,
                m.get(2,0)*x + m.get(2,1)*y + m.get(2,2)*z);
    }

    @Override
    public void setYaw(double yaw) {
        double
        yaw2 = Math.toRadians(yaw),
        r = getLength();

        this.x = Math.sin(-yaw2) * r;
        this.z = Math.cos( yaw2) * r;
    }

    @Override
    public void setPitch(double pitch) {
        double
        pitch2 = -Math.toRadians(pitch),
        r = getLength();

        this.y = Math.tan(pitch2) * r;
    }

    @Override
    public void setRadiusYawPitch(double radius, double yaw, double pitch) {
        double
        r = radius * getLength(),
        y = Math.toRadians(yaw),
        p = -Math.toRadians(pitch);

        this.x = Math.sin(-y) * r;
        this.y = Math.tan( p) * r;
        this.z = Math.cos( y) * r;
    }

    @Override
    public void add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    @Override
    public void subtract(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
    }

    @Override
    public void multiply(double x, double y, double z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
    }

    @Override
    public void multiply(double factor) {
        this.x *= factor;
        this.y *= factor;
        this.z *= factor;
    }

    @Override
    public void negate() {
        this.x = -x;
        this.y = -y;
        this.z = -z;
    }

    @Override
    public void divide(double x, double y, double z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
    }

    @Override
    public void divide(double divisor) {
        this.x /= divisor;
        this.y /= divisor;
        this.z /= divisor;
    }

    @Override
    public void setLength(double length) {
        double n = length / getLength();
        x *= n;
        y *= n;
        z *= n;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Vector3 && equals((Vector3) obj);
    }

    // MISC
    
    @Override
    public String toString() {
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
