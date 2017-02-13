package net.grian.spatium.transform;

import net.grian.spatium.Spatium;
import net.grian.spatium.geo3.Vector3;
import net.grian.spatium.impl.QuaternionImpl;

import java.io.Serializable;

/**
 * <p>
 *     A Quaternion (4 dimensional number) which is used to represent rotations in 3D-Space around a vector.
 * </p>
 * <p>
 *     Quaternions are provided in the form:
 *     <br>{@code w + xi + yj + zk}
 * <p/>
 */
public interface Quaternion extends Serializable, Cloneable {

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
    static Quaternion fromXYZW(double x, double y, double z, double w) {
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
    static Quaternion fromXYZW(Vector3 vector, double w) {
        return new QuaternionImpl(vector, w);
    }

    /**
     * <p>
     *     Creates a new quaternion from a vector. The scale of the quaternion will be 0, the vector part of the
     *     quaternion will be equal to the vector's coordinates.
     * </p>
     * <p>
     *     This operation is equivalent to calling {@link #fromXYZW(Vector3, double)}. With the parameters {@code vector}
     *     and {@code 0}.
     * </p>
     * <br><u>Do not use this method unless you really know what you're doing.</u>
     *
     * @param vector the vector
     * @return a new quaternion
     */
    static Quaternion fromVector(Vector3 vector) {
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
    static Quaternion fromEulerRad(double x, double y, double z) {
        double
                halfYaw = x * 0.5f,
                halfPitch = y * 0.5f,
                halfRoll = z * 0.5f,
                sinYaw   = Math.sin(halfYaw),
                cosYaw   = Math.cos(halfYaw),
                sinPitch = Math.sin(halfPitch),
                cosPitch = Math.cos(halfPitch),
                sinRoll  = Math.sin(halfRoll),
                cosRoll  = Math.cos(halfRoll);

        return fromXYZW(
                cosYaw * cosPitch * cosRoll + sinYaw * sinPitch * sinRoll,
                cosYaw * cosPitch * sinRoll - sinYaw * sinPitch * cosRoll,
                cosYaw * sinPitch * cosRoll + sinYaw * cosPitch * sinRoll,
                sinYaw * cosPitch * cosRoll - cosYaw * sinPitch * sinRoll);
    }

    /**
     * <p>
     *     Returns a new Quaternion that is a counter-clockwise rotation around a given axis with angle theta.
     * </p>
     * <p>
     *     This rotation follows the <a href="https://en.wikipedia.org/wiki/Right-hand_rule">Right Hand Rule</a>.
     * </p>
     *
     * @param ax the x-coordinate of the axis
     * @param ay the y-coordinate of the axis
     * @param az the z-coordinate of the axis
     * @param theta the angle of rotation in radians
     * @return a new quaternion
     */
    static Quaternion fromRotation(double ax, double ay, double az, double theta) {
        double
                halfTheta = theta * 0.5,
                t = Math.sin(halfTheta),
                w = Math.cos(halfTheta);

        return fromXYZW(ax * t, ay * t, az * t, w);
    }
    
    /**
     * Returns a new Quaternion that is a rotation around a given axis with angle theta.
     *
     * @param axis the axis of rotation, a unit vector
     * @param theta the angle of rotation in radians
     * @return a new quaternion
     */
    static Quaternion fromRotation(Vector3 axis, double theta) {
        double
            halfTheta = theta * 0.5f,
            w = Math.cos(halfTheta);
        Vector3 xyz = axis.clone();
        xyz.multiply(Math.sin(halfTheta));
        
        return fromXYZW(xyz, w);
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
    static Quaternion product(Quaternion a, Quaternion b) {
        double
                lx = a.getX(), ly = a.getY(), lz = a.getZ(), lw = a.getW(),
                rx = b.getX(), ry = b.getY(), rz = b.getZ(), rw = b.getW();

        return Quaternion.fromXYZW(
                lw*rx + lx*rw + ly*rz - lz*ry,
                lw*ry - lx*rz + ly*rw + lz*rx,
                lw*rz + lx*ry - ly*rx + lz*rw,
                lw*rw - lx*rx - ly*ry - lz*rz);
    }
    
    /**
     * <p>
     *     Returns the product of a quaternion and a point.
     * </p>
     * <p>
     *     Geometrically, this operation is equivalent to applying the rotation <b>q</b> to the point.
     * </p>
     *
     * @param q the rotation
     * @param point the point
     * @return a new transformed point
     */
    static Vector3 product(Quaternion q, Vector3 point) {
        return product(
            product(q, Quaternion.fromVector(point)),
            Quaternion.inverse(q))
            .getVector();
    }

    /**
     * Returns a new Quaternion which is the conjugate of a given Quaternion. This can be used as a faster alternative
     * to {@link #clone()}.{@link #conjugate()}. Which first clones the object and then mutates it with conjugation.
     *
     * @param q the quaternion to conjugate
     * @return the complex conjugate of the quaternion
     */
    static Quaternion conjugate(Quaternion q) {
        return fromXYZW(-q.getX(), -q.getY(), -q.getZ(), q.getW());
    }

    /**
     * <p>
     *     Returns a new Quaternion which is the inverse of a given Quaternion.
     * </p>
     * <p>
     *     This can be used as a faster alternative to {@link #clone()}.{@link #invert()} which first clones the object
     *     and then mutates it with inversion.
     * </p>
     * <p>
     *     Geometrically, this operation is equivalent to inverting the rotation. Thus, when multiplying this
     *     quaternion with a vector, then multiplying the inverse with the vector, the rotation will be undone.
     * </p>
     *
     * @param q the quaternion to negate
     * @return the inverse of the quaternion
     */
    static Quaternion inverse(Quaternion q) {
        double t = 1 / q.getLengthSquared();
        return Quaternion.fromXYZW(
            q.getX() * -t,
            q.getY() * -t,
            q.getZ() * -t,
            q.getW() *  t);
    }

    /**
     * Returns the identity rotation <code>R<sub>0</sub></code>
     *
     * @return the identity rotation
     */
    static Quaternion identity() {
        return new QuaternionImpl(0, 0, 0, 1);
    }

    // GETTERS

    /**
     * Returns the x-coordinate of the quaternion vector.
     *
     * @return the x of the quaternion
     */
    abstract double getX();

    /**
     * Returns the y-coordinate of the quaternion vector.
     *
     * @return the y of the quaternion
     */
    abstract double getY();

    /**
     * Returns the z-coordinate of the quaternion vector.
     *
     * @return the z of the quaternion
     */
    abstract double getZ();

    /**
     * Returns the real part of the quaternion, also known as the scale.
     *
     * @return the real part of the quaternion
     */
    abstract double getW();

    /**
     * Returns the hypot of the quaternion.
     *
     * @return the hypot of the quaternion
     */
    abstract double getLength();

    /**
     * Returns the squared hypot of the quaternion.
     *
     * @return the squared hypot of the quaternion
     */
    abstract double getLengthSquared();

    /**
     * Returns the vector of this quaternion.
     *
     * @return the quaternion vector
     */
    default Vector3 getVector() {
        return Vector3.fromXYZ(getX(), getY(), getZ());
    }

    /**
     * Returns x, y, z, w using an index respectively.
     *
     * @param index the index
     * @return the quaternion's first to fourth number
     */
    default double get(int index) {
        switch (index) {
            case 0: return getX();
            case 1: return getY();
            case 2: return getZ();
            case 3: return getW();
            default: throw new IndexOutOfBoundsException("quaternion does not have "+index+"th number");
        }
    }

    /**
     * Returns the dot product of this and another quaternion.
     *
     * @param q the quaternion
     * @return the dot product
     */
    abstract double dot(Quaternion q);

    // CHECKERS

    default boolean equals(Quaternion q) {
        return
                Spatium.equals(this.getX(), q.getX()) &&
                Spatium.equals(this.getY(), q.getY()) &&
                Spatium.equals(this.getZ(), q.getZ()) &&
                Spatium.equals(this.getW(), q.getW());
    }

    // SETTERS
    
    /**
     * <p>
     *     Directly sets the x value of the quaternion.
     * </p>
     * <p>
     *     Do not use this method unless you really know what you are doing.
     * </p>
     *
     * @param x the x-value
     * @return itself
     */
    abstract Quaternion setX(double x);
    
    /**
     * <p>
     *     Directly sets the y value of the quaternion.
     * </p>
     * <p>
     *     Do not use this method unless you really know what you are doing.
     * </p>
     *
     * @param y the y-value
     * @return itself
     */
    abstract Quaternion setY(double y);
    
    /**
     * <p>
     *     Directly sets the z value of the quaternion.
     * </p>
     * <p>
     *     Do not use this method unless you really know what you are doing.
     * </p>
     *
     * @param z the x-value
     * @return itself
     */
    abstract Quaternion setZ(double z);
    
    /**
     * <p>
     *     Directly sets the w value of the quaternion.
     * </p>
     * <p>
     *     Do not use this method unless you really know what you are doing. In case you want to change the angle,
     *     use {@link #setAngle(double)} instead.
     * </p>
     *
     * @param w the w-value
     * @return itself
     */
    abstract Quaternion setW(double w);

    abstract Quaternion set(double x, double y, double z, double w);

    /**
     * Sets the angle of this quaternion.
     *
     * @param theta the angle in radians
     * @return itself
     */
    default Quaternion setAngle(double theta) {
        return setW(Math.cos(theta / 2));
    }

    /**
     * Conjugates the quaternion.
     * <blockquote>
     *     <code>(x + yi + zj + wk) -> (x - yi - zj - wk)</code>
     * </blockquote>
     *
     * @return itself
     */
    abstract Quaternion conjugate();

    /**
     * Inverts the quaternion.
     *
     * @return itself
     */
    abstract Quaternion invert();

    /**
     * <p>
     *     Adds a quaternion to this quaternion.
     * </p>
     * <p>
     *     Geometrically, this is <b>NOT</b> adding the rotation from this one, it is solely an algebraic addition of
     *     the x, y, z, w coordinates.
     * </p>
     *
     * @param q the quaternion
     * @return itself
     */
    abstract Quaternion add(Quaternion q);

    /**
     * Multiplies this quaternion with a factor (scalar multiplication).
     *
     * @param scale the scale
     * @return itself
     */
    abstract Quaternion multiply(double scale);

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
     * @deprecated use {@link #product(Quaternion, Quaternion)}
     * @param q the quaternion
     * @return itself
     */
    @Deprecated
    abstract Quaternion multiply(Quaternion q);

    /**
     * Divides this quaternion by a divisor (scalar multiplication).
     *
     * @param divisor the divisor
     * @return itself
     */
    abstract Quaternion divide(double divisor);

    /**
     * <p>
     *     Subtracts a quaternion from this quaternion.
     * </p>
     * <p>
     *     Geometrically, this is <b>NOT</b> subtracting the rotation from this one, it is solely an algebraic
     *     subtraction of the x, y, z, w coordinates.
     * </p>
     *
     * @param q the quaternion
     * @return itself
     */
    abstract Quaternion subtract(Quaternion q);

    /**
     * <p>
     *     Normalizes the quaternion, turning it into a unit quaternion. This means that the hypot of the quaternion is
     *     equal to 1.
     * </p>
     * <p>
     *     Hence, this operation is equivalent to {@code this.setLength(1)}
     * </p>
     *
     * @return itself
     * @see Vector3#normalize()
     */
    default Quaternion normalize() {
        return setLength(1);
    }

    /**
     * Sets the hypot of the quaternion, seen as a four-dimensional vector.
     *
     * @param length the new hypot
     * @return itself
     */
    default Quaternion setLength(double length) {
        return multiply(length / getLength());
    }

    // MISC
    
    abstract Quaternion clone();

}
