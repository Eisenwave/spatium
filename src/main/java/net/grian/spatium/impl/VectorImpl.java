package net.grian.spatium.impl;

import net.grian.spatium.Spatium;
import net.grian.spatium.geo.BlockVector;
import net.grian.spatium.geo.Vector;
import net.grian.spatium.matrix.Matrix;
import net.grian.spatium.matrix.MatrixDimensionsException;

import java.util.Arrays;

public class VectorImpl implements Vector {

    private static final long serialVersionUID = 6745733279751595544L;

    private double x, y, z;

    public VectorImpl(double x, double y, double z) {
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
    public double angleTo(Vector vector) {
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
    public Vector cross(double x, double y, double z) {
        return new VectorImpl(
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
    public Vector midPoint(Vector v, double t) {
        return new VectorImpl(
                this.getX() + (v.getX() - this.getX()) * t,
                this.getX() + (v.getY() - this.getY()) * t,
                this.getX() + (v.getZ() - this.getZ()) * t);
    }

    // SETTERS

    @Override
    public Vector set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    @Override
    public Vector setX(double x) {
        this.x = x;
        return this;
    }

    @Override
    public Vector setY(double y) {
        this.y = y;
        return this;
    }

    @Override
    public Vector setZ(double z) {
        this.z = z;
        return this;
    }

    @Override
    public Vector transform(Matrix m) {
        if (m.getRows() != 3 || m.getColumns() != 3)
            throw new MatrixDimensionsException("matrix must be a 3x3 matrix");

        return this.set(
                m.get(0,0)*x + m.get(0,1)*y + m.get(0,2)*z,
                m.get(1,0)*x + m.get(1,1)*y + m.get(1,2)*z,
                m.get(2,0)*x + m.get(2,1)*y + m.get(2,2)*z);
    }

    @Override
    public Vector setYaw(double yaw) {
        double
        yaw2 = Math.toRadians(yaw),
        r = getLength();

        this.x = Math.sin(-yaw2) * r;
        this.z = Math.cos( yaw2) * r;
        return this;
    }

    @Override
    public Vector setPitch(double pitch) {
        double
        pitch2 = -Math.toRadians(pitch),
        r = getLength();

        this.y = Math.tan(pitch2) * r;
        return this;
    }

    @Override
    public Vector setRadiusYawPitch(double radius, double yaw, double pitch) {
        double
        r = radius * getLength(),
        y = Math.toRadians(yaw),
        p = -Math.toRadians(pitch);

        this.x = Math.sin(-y) * r;
        this.y = Math.tan( p) * r;
        this.z = Math.cos( y) * r;
        return this;
    }

    @Override
    public Vector add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    @Override
    public Vector subtract(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    @Override
    public Vector multiply(double x, double y, double z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    @Override
    public Vector multiply(double factor) {
        this.x *= factor;
        this.y *= factor;
        this.z *= factor;
        return this;
    }

    @Override
    public Vector invert() {
        this.x = -x;
        this.y = -y;
        this.z = -z;
        return this;
    }

    @Override
    public Vector divide(double x, double y, double z) {
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }

    @Override
    public Vector divide(double divisor) {
        this.x /= divisor;
        this.y /= divisor;
        this.z /= divisor;
        return this;
    }

    @Override
    public Vector setLength(double length) {
        double n = length / getLength();
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
        return getClass().getSimpleName()+"["+x+","+y+","+z+"]";
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
    public Vector clone() {
        return new VectorImpl(this);
    }

}
