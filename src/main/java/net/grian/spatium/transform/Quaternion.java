package net.grian.spatium.transform;

import net.grian.spatium.Spatium;
import net.grian.spatium.SpatiumObject;
import net.grian.spatium.geo.Vector;
import net.grian.spatium.impl.QuaternionImpl;
import net.grian.spatium.matrix.MatrixIndexOutOfBoundsException;

/**
 * <p>
 *     A Quaternion (4 dimensional number) which is used to represent rotations in 3D-Space around a vector.
 * </p>
 * <p>
 *     Quaternions are provided in the form:
 *     <br>{@code w + xi + yj + zk}
 * <p/>
 */
public interface Quaternion extends SpatiumObject, Transformation {

    /**
     * Creates a new quaternion from vector coordinates and a scale.
     * <br><u>Do not use this method unless you really know what you're doing.</u>
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @param w the scale
     * @return a new quaternion
     */
    public static Quaternion fromXYZW(float x, float y, float z, float w) {
        return new QuaternionImpl(x, y, z, w);
    }

    /**
     * Creates a new quaternion from vector coordinates and a scale.
     * <br><u>Do not use this method unless you really know what you're doing.</u>
     *
     * @param vector the vector
     * @param w the scale
     * @return a new quaternion
     */
    public static Quaternion fromXYZW(Vector vector, float w) {
        return new QuaternionImpl(vector, w);
    }

    /**
     * <p>
     *     Creates a new quaternion from a vector. The scale of the quaternion will be 0, the vector part of the
     *     quaternion will be equal to the vector's coordinates.
     * </p>
     * <p>
     *     This operation is equivalent to calling {@link #fromXYZW(Vector, float)}. With the parameters {@code vector}
     *     and {@code 0}.
     * </p>
     * <br><u>Do not use this method unless you really know what you're doing.</u>
     *
     * @param vector the vector
     * @return a new quaternion
     */
    public static Quaternion fromVector(Vector vector) {
        return fromXYZW(vector, 0);
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
                sinYaw   = (float) Math.sin(halfYaw),
                cosYaw   = (float) Math.cos(halfYaw),
                sinPitch = (float) Math.sin(halfPitch),
                cosPitch = (float) Math.cos(halfPitch),
                sinRoll  = (float) Math.sin(halfRoll),
                cosRoll  = (float) Math.cos(halfRoll);

        return fromXYZW(
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
        return fromEulerRad(Spatium.radians(x), Spatium.radians(y), Spatium.radians(z));
    }

    /**
     * Returns a new quaternion from yaw, pitch and roll.
     *
     * @param yaw the yaw
     * @param pitch the pitch
     * @param roll the roll
     * @return a new quaternion
     */
    public static Quaternion fromYawPitchRoll(float yaw, float pitch, float roll) {
        return fromEulerDeg(-pitch, -yaw, -roll);
    }

    /**
     * Returns a new Quaternion that is a rotation around a given axis with angle theta.
     *
     * @param axis the axis of rotation, a unit vector
     * @param theta the angle of rotation in radians
     * @return a new quaternion
     */
    public static Quaternion fromRotation(Vector axis, float theta) {
        float halfTheta = theta * 0.5f;
        return fromXYZW(
                axis.clone().multiply((float) Math.sin(halfTheta)),
                (float) Math.cos(halfTheta));
    }

    /**
     * Returns a new Quaternion that is a rotation around a given axis with angle theta.
     *
     * @param ax the x-coordinate of the axis
     * @param ay the y-coordinate of the axis
     * @param az the z-coordinate of the axis
     * @param theta the angle of rotation in radians
     * @return a new quaternion
     */
    public static Quaternion fromRotation(float ax, float ay, float az, float theta) {
        float
                halfTheta = theta * 0.5f,
                t = (float) Math.sin(halfTheta),
                w = (float) Math.cos(halfTheta);

        return fromXYZW(ax * t, ay * t, az * t, w);
    }

    /**
     * <p>
     *     Returns the product of two quaternions <code>c = a * b</code>. (See
     *     <a href = "https://en.wikipedia.org/wiki/Quaternion#Hamilton_product">Hamilton Product<a/> for reference)
     * </p>
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

        return Quaternion.fromXYZW(
                lw*rx + lx*rw + ly*rz - lz*ry,
                lw*ry - lx*rz + ly*rw + lz*rx,
                lw*rz + lx*ry - ly*rx + lz*rw,
                lw*rw - lx*rx - ly*ry - lz*rz
                );
    }

    /**
     * Returns a new Quaternion which is the conjugate of a given Quaternion. This can be used as a faster alternative
     * to {@link #clone()}.{@link #conjugate()}. Which first clones the object and then mutates it with conjugation.
     *
     * @param q the quaternion to conjugate
     * @return the complex conjugate of the quaternion
     */
    public static Quaternion conjugate(Quaternion q) {
        return fromXYZW(-q.getX(), -q.getY(), -q.getZ(), q.getW());
    }

    /**
     * Returns a new Quaternion which is the inverse of a given Quaternion. This can be used as a faster alternative
     * to {@link #clone()}.{@link #invert()}. Which first clones the object and then mutates it with inversion.
     *
     * @param q the quaternion to invert
     * @return the inverse of the quaternion
     */
    public static Quaternion inverse(Quaternion q) {
        float t = 1 / q.getLengthSquared();
        return Quaternion.fromXYZW(
            q.getX() * -t,
            q.getY() * -t,
            q.getZ() * -t,
            q.getW() *  t
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
     * Changes the angle of this quaternion.
     *
     * @param theta the angle in radians
     * @return itself
     */
    public default Quaternion setAngle(float theta) {
        return setW( (float) Math.cos(theta / 2) );
    }

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
     * Adds the rotation q to this quaternion. This operation is equivalent to multiplying q (right hand side) with this
     * quaternion (left hand side).
     *
     * @param q the rotation to add
     * @return itself
     * @see #multiply(Quaternion)
     */
    public default Quaternion addRotation(Quaternion q) {
        return multiply(q);
    }

    /**
     * Subtracts the rotation q from this quaternion. This operation is equivalent to multiplying q (right hand side)
     * with this quaternion inverted (left hand side).
     *
     * @param q the rotation to subtract
     * @return itself
     * @see #invert()
     * @see #multiply(Quaternion)
     */
    public default Quaternion subtractRotation(Quaternion q) {
        return invert().multiply(q);
    }

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
     * <p>
     *     Normalizes the quaternion, turning it into a unit quaternion. This means that the length of the quaternion is
     *     equal to 1.
     * </p>
     * <p>
     *     Hence, this operation is equivalent to {@code this.setLength(1)}
     * </p>
     *
     * @return itself
     * @see Vector#normalize()
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
        return multiply(length / getLength());
    }

    // MISC


    @Override
    public default Vector apply(Vector v) {
        return Transformations.rotate(v, this);
    }

    public abstract Quaternion clone();

}
