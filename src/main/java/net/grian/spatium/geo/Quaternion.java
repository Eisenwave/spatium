package net.grian.spatium.geo;

import net.grian.spatium.Spatium;
import net.grian.spatium.impl.QuaternionImpl;
import net.grian.spatium.matrix.MatrixIndexOutOfBoundsException;

/**
 * A Quaternion (4 dimensional number) which is used to represent rotations in 3D-Space around a vector.
 * <p>
 *     Quaternions are provided in the form:
 *     <br>{@code w + xi + yj + zk}
 * <p/>
 */
public interface Quaternion {

    /**
     * Creates a new quaternion from vector coordinates and a scale.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @param w the scale
     * @return a new quaternion
     */
    public static Quaternion create(float x, float y, float z, float w) {
        return new QuaternionImpl(x, y, z, w);
    }

    /**
     * Creates a new quaternion from vector coordinates and a scale.
     *
     * @param vector the vector
     * @param w the scale
     * @return a new quaternion
     */
    public static Quaternion create(Vector vector, float w) {
        return new QuaternionImpl(vector, w);
    }

    /**
     * Returns a new quaternion that is equal to a given euler rotation in radians around the x, y and z axes.
     *
     * @param x the rotation around the x-axis in radians
     * @param y the rotation around the y-axis in radians
     * @param z the rotation around the z-axis in radians
     * @return a new quaternion
     */
    public static Quaternion fromEulerRad(float x, float y, float z) {
        float
                halfYaw = x * 0.5f,
                halfPitch = y * 0.5f,
                halfRoll = z * 0.5f,
                sinYaw = (float)Math.sin(halfYaw),
                cosYaw = (float)Math.cos(halfYaw),
                sinPitch = (float)Math.sin(halfPitch),
                cosPitch = (float)Math.cos(halfPitch),
                sinRoll = (float)Math.sin(halfRoll),
                cosRoll = (float)Math.cos(halfRoll);

        return Quaternion.create(
                cosYaw * cosPitch * cosRoll + sinYaw * sinPitch * sinRoll,
                cosYaw * cosPitch * sinRoll - sinYaw * sinPitch * cosRoll,
                cosYaw * sinPitch * cosRoll + sinYaw * cosPitch * sinRoll,
                sinYaw * cosPitch * cosRoll - cosYaw * sinPitch * sinRoll
        );
    }

    /**
     * Returns a new quaternion that is equal to a given euler rotation in degrees around the x, y and z axes.
     *
     * @param x the rotation around the x-axis in degrees
     * @param y the rotation around the y-axis in degrees
     * @param z the rotation around the z-axis in degrees
     * @return a new quaternion
     */
    public static Quaternion fromEulerDeg(float x, float y, float z) {
        return fromEulerRad(x*Spatium.DEG_TO_RAD, y*Spatium.DEG_TO_RAD, z*Spatium.DEG_TO_RAD);
    }

    /**
     * Returns the product of two quaternions <code>c = a * b</code>.
     * <p>
     *     This operation is equivalent to first performing the rotation a and then the rotation b as each pair of
     *     rotations can be described by another rotation.
     * </p>
     *
     * @param a the first quaternion (rotation)
     * @param b the second quaternion (rotation)
     * @return a new quaternion (rotation)
     */
    public static Quaternion product(Quaternion a, Quaternion b) {
        float
                lx = a.getX(), ly = a.getY(), lz = a.getZ(), lw = a.getW(),
                rx = b.getX(), ry = b.getY(), rz = b.getZ(), rw = b.getW();

        return Quaternion.create(
                lw*rx + lx*rw + ly*rz - lz*ry,
                lw*ry - lx*rz + ly*rw + lz*rx,
                lw*rz + lx*ry - ly*rx + lz*rw,
                lw*rw - lx*rx - ly*ry - lz*rz
                );
    }

    /**
     * Returns the product of a quaternion (rotation) and a point.
     * <p>
     *     This operation is equivalent to applying the rotation q to the point.
     * </p>
     *
     * @param q the quaternion (rotation)
     * @param point the point to be rotated
     * @return a new rotated point
     */
    public static Vector product(Quaternion q, Vector point) {
        float
                px = point.getX(), py = point.getY(), pz = point.getZ(),
                qx = q.getX(), qy = q.getY(), qz = q.getZ(), w = q.getW(),
                x2 = qx * 2,   y2 = qy * 2,   z2 = qz * 2,
                xx = qx * x2,  yy = qy * y2,  zz = qz * z2,
                xy = qx * y2,  xz = qx * z2,  yz = qy * z2,
                wx = w * x2,   wy = w * y2,   wz = w * z2;

        return Vector.fromXYZ(
                px * (1 - (yy + zz)) + py * (xy - wz)       + pz * (xz + wy),
                px * (xy + wz)       + py * (1 - (xx + zz)) + pz * (yz - wx),
                px * (xz - wy)       + py * (yz + wx)       + pz * (1 - (xx + yy))
        );
    }

    /**
     * Returns the identity rotation <code>R<sub>0</sub></code>
     *
     * @return the identity rotation
     */
    public static Quaternion identity() {
        return new QuaternionImpl(0, 0, 0, 1);
    }

    // GETTERS

    /**
     * Returns the x-coordinate of the quaternion vector.
     *
     * @return the x of the quaternion
     */
    public abstract float getX();

    /**
     * Returns the y-coordinate of the quaternion vector.
     *
     * @return the y of the quaternion
     */
    public abstract float getY();

    /**
     * Returns the z-coordinate of the quaternion vector.
     *
     * @return the z of the quaternion
     */
    public abstract float getZ();

    /**
     * Returns the real part of the quaternion, also known as the scale.
     *
     * @return the real part of the quaternion
     */
    public abstract float getW();

    /**
     * Returns the length of the quaternion.
     *
     * @return the length of the quaternion
     */
    public abstract float getLength();

    /**
     * Returns the squared length of the quaternion.
     *
     * @return the squared length of the quaternion
     */
    public abstract float getLengthSquared();

    /**
     * Returns the vector of this quaternion.
     *
     * @return the quaternion vector
     */
    public default Vector getVector() {
        return Vector.fromXYZ(getX(), getY(), getZ());
    }

    /**
     * Returns x, y, z, w using an index respectively.
     *
     * @param index the index
     * @return the quaternion's first to fourth number
     */
    public default float get(int index) {
        switch (index) {
            case 0: return getX();
            case 1: return getY();
            case 2: return getZ();
            case 3: return getW();
            default: throw new MatrixIndexOutOfBoundsException("quaternion does not have "+index+"th number");
        }
    }

    /**
     * Returns the dot product of this and another quaternion.
     *
     * @param q the quaternion
     * @return the dot product
     */
    public abstract float dot(Quaternion q);

    // CHECKERS

    public default boolean equals(Quaternion q) {
        return
                Spatium.equals(this.getX(), q.getX()) &&
                Spatium.equals(this.getY(), q.getY()) &&
                Spatium.equals(this.getZ(), q.getZ()) &&
                Spatium.equals(this.getW(), q.getW());
    }

    // SETTERS

    public abstract Quaternion setX(float x);

    public abstract Quaternion setY(float y);

    public abstract Quaternion setZ(float z);

    public abstract Quaternion setW(float w);

    public abstract Quaternion set(float x, float y, float z, float w);

    /**
     * Conjugates the quaternion.
     * <blockquote>
     *     <code>x + yi + zj + wk -> x - yi - zj - wk</code>
     * </blockquote>
     *
     * @return itself
     */
    public abstract Quaternion conjugate();

    /**
     * Inverts the quaternion.
     *
     * @return itself
     */
    public abstract Quaternion invert();

    /**
     * Adds a quaternion to this quaternion.
     *
     * @param q the quaternion
     * @return itself
     */
    public abstract Quaternion add(Quaternion q);

    /**
     * Multiplies this quaternion with a factor (scalar multiplication).
     *
     * @param scale the scale
     * @return itself
     */
    public abstract Quaternion multiply(float scale);

    /**
     * <p>
     *     Multiplies a quaternion with this quaternion. This will mutate the current quaternion and is equivalent to
     *     "appending a rotation".
     * </p>
     * <p>
     *     The product of two quaternions {@code a} and {@code b} is equivalent to the rotation {@code a} followed by
     *     the rotation {@code b}.
     * </p>
     *
     * @param q the quaternion
     * @return itself
     */
    public abstract Quaternion multiply(Quaternion q);

    /**
     * Divides this quaternion by a divisor (scalar multiplication).
     *
     * @param divisor the divisor
     * @return itself
     */
    public abstract Quaternion divide(float divisor);

    /**
     * Subtracts a quaternion from this quaternion.
     *
     * @param q the quaternion
     * @return itself
     */
    public abstract Quaternion subtract(Quaternion q);

    /**
     * Divides this quaternion through a quaternion.
     *
     * @param q the quaternion
     * @return itself
     */
    public abstract Quaternion divide(Quaternion q);

    /**
     * Normalizes the quaternion, seen as a four-dimensional vector.
     * This is equivalent to settings its length to 1.
     *
     * @return itself
     */
    public default Quaternion normalize() {
        return setLength(1);
    }

    /**
     * Sets the length of the quaternion, seen as a four-dimensional vector.
     *
     * @param length the new length
     * @return itself
     */
    public default Quaternion setLength(float length) {
        return divide(length / getLength());
    }

    // MISC

    public abstract Quaternion clone();

}
