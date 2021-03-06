package net.grian.spatium.geo3;

import eisenwave.spatium.enums.Axis;
import eisenwave.spatium.enums.Direction;
import net.grian.spatium.impl.Vector3Impl;
import net.grian.spatium.matrix.Matrix;
import eisenwave.spatium.util.Spatium;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * A three-dimensional vector. Can be either karthesian or polar depending on
 * the implementation.
 */
public interface Vector3 extends Serializable, Cloneable {

    /**
     * Creates a new Vector3.
     *
     * @return a new Vector3
     */
    @NotNull
    static Vector3 zero() {
        return new Vector3Impl();
    }

    /**
     * Creates a new Vector3 from 3 three coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return a new Vector3
     */
    @NotNull
    static Vector3 fromXYZ(double x, double y, double z) {
        return new Vector3Impl(x, y, z);
    }

    /**
     * Creates a new Vector3 from block coordinates.
     *
     * @param block the block coordinates
     * @return a new Vector3
     */
    @NotNull
    static Vector3 fromBlock(BlockVector block) {
        return new Vector3Impl(block);
    }
    
    @NotNull
    static Vector3 fromAxis(Axis axis) {
        switch (axis) {
            case X: return Vector3.fromXYZ(1, 0, 0);
            case Y: return Vector3.fromXYZ(0, 1, 0);
            case Z: return Vector3.fromXYZ(0, 0, 1);
            default: throw new AssertionError();
        }
    }
    
    @NotNull
    static Vector3 fromDir(Direction direction) {
        switch (direction) {
            case POSITIVE_X: return Vector3.fromXYZ( 1,  0,  0);
            case POSITIVE_Y: return Vector3.fromXYZ( 0,  1,  0);
            case POSITIVE_Z: return Vector3.fromXYZ( 0,  0,  1);
            case NEGATIVE_X: return Vector3.fromXYZ(-1,  0,  0);
            case NEGATIVE_Y: return Vector3.fromXYZ( 0, -1,  0);
            case NEGATIVE_Z: return Vector3.fromXYZ( 0,  0, -1);
            default: throw new AssertionError();
        }
    }

    /**
     * Creates a new Vector3 from a radius (hypot), a yaw and a pitch.
     *
     * @param radius the radius
     * @param yaw the yaw
     * @param pitch the pitch
     * @return a new Vector3
     */
    @NotNull
    static Vector3 fromRadiusYawPitch(double radius, double yaw, double pitch) {
        return Vectors.rypToXYZ(radius, Spatium.radians(yaw), Spatium.radians(pitch));
    }

    /**
     * Creates a new Vector3 between two points. The vector will be pointing from {@code from} to {@code to}.
     *
     * @param from the first point
     * @param to the second point
     * @return a new Vector3 between these points
     */
    @NotNull
    static Vector3 between(Vector3 from, Vector3 to) {
        return new Vector3Impl(
            to.getX() - from.getX(),
            to.getY() - from.getY(),
            to.getZ() - from.getZ());
    }
    
    /**
     * Creates a new Vector3 between two points. The vector will be pointing from {@code from} to {@code to}.
     *
     * @return a new Vector3 between these points
     */
    @NotNull
    static Vector3 between(double fx, double fy, double fz, double tx, double ty, double tz) {
        return new Vector3Impl(tx-fx, ty-fy, tz-fz);
    }

    // GETTERS

    /**
     * Returns the x of the vector.
     * @return the x of the vector
     */
    abstract double getX();

    /**
     * Returns the y of the vector.
     *
     * @return the y of the vector
     */
    abstract double getY();

    /**
     * Returns the z of the vector.
     *
     * @return the z of the vector
     */
    abstract double getZ();

    /**
     * Returns the yaw of the vector in degrees.
     *
     * @return the yaw of the vector
     */
    abstract double getYaw();

    /**
     * Returns the pitch of the vector in degrees.
     *
     * @return the pitch of the vector
     */
    abstract double getPitch();

    /**
     * Returns the length of the vector.
     *
     * @return the length of the vector
     */
    default double getLength() {
        return Math.sqrt(getLengthSquared());
    }

    /**
     * Returns the squared length of the vector.
     *
     * @return the squared length of the vector
     */
    default double getLengthSquared() {
        return dot(this);
    }

    /**
     * Returns the distance between this vector and a point.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return the distance to the point
     */
    default double distanceTo(double x, double y, double z) {
        return Vector3.between(getX(), getY(), getZ(), x, y, z).getLength();
    }

    /**
     * Returns the distance between this vector and a point.
     *
     * @param point the point
     * @return the distance to the point
     */
    default double distanceTo(Vector3 point) {
        return distanceTo(point.getX(), point.getY(), point.getZ());
    }

    /**
     * Returns the signed angle between this vector and another vector.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return the signed angle to another vector
     */
    default double angleTo(double x, double y, double z) {
        return Math.acos(dot(x, y, z) / (getLength() * Spatium.hypot(x, y, z)));
    }

    /**
     * Returns the signed angle between this vector and another vector.
     *
     * @param v the vector
     * @return the signed angle to another vector
     */
    default double angleTo(Vector3 v) {
        return Math.acos(dot(v) / (getLength() * v.getLength()));
    }

    /**
     * Returns the signed angle of this vector to a plane. This is equivalent to the signed angle of the plane's
     * normal vector.
     *
     * @param plane the plane
     * @return the signed angle to the plane
     */
    default double angleTo(Plane plane) {
        return angleTo(plane.getNormal());
    }

    /**
     * <p>
     *     Returns a new point between this point and another with a weight t. The weight specifies how close the point
     *     is to this point.
     * </p>
     * <p>
     *     At 0, the result will be a clone of this point. At 1, the result will be a clone of the other point.
     * </p>
     *
     * @param point the other point
     * @param t the weight ({@code 0 <= t <= 1})
     * @return a point between this and another point
     * @throws IllegalArgumentException if t is smaller than 0 or larger than 1
     */
    default Vector3 midPoint(Vector3 point, double t) {
        if (t < 0 || t > 1) throw new IllegalArgumentException("weight out of range ("+t+")");
        double k = 1-t;
        return Vector3.fromXYZ(
            (this.getX() * k) + (point.getX() * t),
            (this.getY() * k) + (point.getY() * t),
            (this.getZ() * k) + (point.getZ() * t));
    }

    /**
     * Returns the middle-point between this point and another point.
     *
     * @param point the point
     * @return the middle-point to another point
     * @see #midPoint(Vector3, double)
     */
    default Vector3 midPoint(Vector3 point) {
        return midPoint(point, 0.5);
    }

    /**
     * Returns the dot-product of this and another vector.
     *
     * @param x the x coordinate of the vector
     * @param y the y coordinate of the vector
     * @param z the z coordinate of the vector
     * @return the dot-product of this and another vector
     */
    default double dot(double x, double y, double z) {
        return getX()*x + getY()*y + getZ()*z;
    }

    /**
     * Returns the dot-product of this and another vector.
     *
     * @param v the vector
     * @return the dot-product of this and another vector
     */
    default double dot(Vector3 v) {
        return dot(v.getX(), v.getY(), v.getZ());
    }

    /**
     * Returns the cross-product of this and another vector.
     *
     * @param x the x coordinate of the vector
     * @param y the y coordinate of the vector
     * @param z the z coordinate of the vector
     * @return the cross-product of this and another vector
     */
    default Vector3 cross(double x, double y, double z) {
        return fromXYZ(
            getY() * z - getZ() * y,
            getZ() * x - getX() * z,
            getX() * y - getY() * x);
    }

    /**
     * Returns the cross-product of this and another vector.
     *
     * @param v the vector
     * @return the cross-product of this and another vector
     */
    default Vector3 cross(Vector3 v) {
        return cross(v.getX(), v.getY(), v.getZ());
    }

    // CHECKERS

    /**
     * Returns whether this vector equals another vector with a margin for floating point inaccuracies of
     * {@link Spatium#EPSILON}.
     *
     * @param v the vector
     * @return whether this vector equals another vector
     * @see Spatium#equals(double, double)
     */
    default boolean equals(Vector3 v) {
        return
            Spatium.equals(getX(), v.getX()) &&
            Spatium.equals(getY(), v.getY()) &&
            Spatium.equals(getZ(), v.getZ());
    }
    
    /**
     * Returns whether this vector is a multiple of the vector <b>v</b>, meaning that:
     * <blockquote>
     *     <code>r * this = v</code>
     * </blockquote>
     * <p>
     *     This is equivalent to whether this vector is parallel to another vector.
     * </p>
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return whether this vector is a multiple of v
     */
    default boolean isMultipleOf(double x, double y, double z) {
        return Vectors.multiples(getX(), getY(), getZ(), x, y, z);
    }

    /**
     * Returns whether this vector is a multiple of the vector <b>v</b>, meaning that:
     * <blockquote>
     *     <code>r * this = v</code>
     * </blockquote>
     * <p>
     *     This is equivalent to whether this vector is parallel to another vector.
     * </p>
     *
     * @param v the vector
     * @return whether this vector is a multiple of v
     */
    default boolean isMultipleOf(Vector3 v) {
        return isMultipleOf(v.getX(), v.getY(), v.getZ());
    }
    
    /**
     * Returns whether this vector is orthogonal (perpendicular) to another vector.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return whether this vector is orthogonal to the vector
     */
    default boolean isOrthogonalTo(double x, double y, double z) {
        return Spatium.isZero( dot(x, y, z) );
    }
    
    /**
     * Returns whether this vector is orthogonal (perpendicular) to another vector.
     *
     * @param v the vector
     * @return whether this vector is orthogonal to the vector
     */
    default boolean isOrthogonalTo(Vector3 v) {
        return Spatium.isZero( dot(v) );
    }
    
    /**
     * Returns whether all coordinates of this vector are zero.
     *
     * @return whether this vector is zero
     * @see Spatium#isZero(double)
     */
    default boolean isZero() {
        return
            Spatium.isZero(getX()) &&
            Spatium.isZero(getY()) &&
            Spatium.isZero(getZ());
    }
    
    /**
     * Returns whether all coordinates of this vector are finite.
     *
     * @return whether this vector is finite
     * @see Double#isFinite(double)
     */
    default boolean isFinite() {
        return
            Double.isFinite(getX()) &&
            Double.isFinite(getY()) &&
            Double.isFinite(getZ());
    }
    
    /**
     * Returns whether the vectors is a unit vector.
     *
     * @return whether this vector is a unit vector
     */
    default boolean isUnit() {
        return Spatium.equals(1, getLengthSquared());
    }

    // SETTERS

    /**
     * Sets the coordinates of this vector to new coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     */
    abstract Vector3 set(double x, double y, double z);

    /**
     * Sets the coordinates of this vector to the coordinates of another vector.
     *
     * @param v the vector
     */
    default Vector3 set(Vector3 v) {
        return set(v.getX(), v.getY(), v.getZ());
    }

    /**
     * Sets the x of the vector.
     *
     * @param x the x of the vector
     */
    abstract Vector3 setX(double x);

    /**
     * Sets the y of the vector.
     *
     * @param y the y of the vector
     */
    abstract Vector3 setY(double y);

    /**
     * Sets the z of the vector.
     *
     * @param z the z of the vector
     */
    abstract Vector3 setZ(double z);
    
    /**
     * Sets the length of the vector.
     *
     * @param length the length of the vector
     * @see #normalize()
     */
    default Vector3 setLength(double length) {
        return multiply(length / getLength());
    }
    
    /**
     * Sets the yaw of this vector.
     *
     * @param yaw the yaw in degrees
     */
    abstract Vector3 setYaw(double yaw);
    
    /**
     * Sets the pitch of this vector.
     *
     * @param pitch the pitch in degrees
     */
    abstract Vector3 setPitch(double pitch);
    
    /**
     * Sets the radius (hypot), yaw and pitch of this vector.
     *
     * @param radius the radius
     * @param yaw the yaw
     * @param pitch the pitch
     */
    abstract Vector3 setLengthYawPitch(double radius, double yaw, double pitch);
    
    /**
     * Sets the yaw and pitch of this vector but keeps the radius (hypot).
     *
     * @param yaw the yaw
     * @param pitch the pitch
     */
    default Vector3 setYawPitch(double yaw, double pitch) {
        return setLengthYawPitch(getLength(), yaw, pitch);
    }

    // OPERATIONS

    /**
     * Adds another vector to this vector.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     */
    abstract Vector3 add(double x, double y, double z);

    /**
     * Adds another vector to this vector.
     *
     * @param v the vector
     * @return itself
     */
    default Vector3 add(Vector3 v) {
        return add(v.getX(), v.getY(), v.getZ());
    }

    /**
     * Subtracts another vector from this vector.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return itself
     */
    abstract Vector3 subtract(double x, double y, double z);

    /**
     * Subtracts another vector from this vector.
     *
     * @param v the vector to subtract
     * @return itself
     */
    default Vector3 subtract(Vector3 v) {
        return subtract(v.getX(), v.getY(), v.getZ());
    }

    /**
     * Multiplies the coordinates of this vector.
     *
     * @param x the x factor
     * @param y the y factor
     * @param z the z factor
     * @return itself
     */
    abstract Vector3 multiply(double x, double y, double z);

    /**
     * Scales this vector by a factor.
     * @param factor the factor
     */
    default Vector3 multiply(double factor) {
        return multiply(factor, factor, factor);
    }
    
    /**
     * Divides the coordinates of this vector.
     *
     * @param x the x divisor
     * @param y the y divisor
     * @param z the z divisor
     */
    abstract Vector3 divide(double x, double y, double z);

    /**
     * Divides this vector by a divisor.
     *
     * @param divisor the divisor
     */
    default Vector3 divide(double divisor) {
        return divide(divisor, divisor, divisor);
    }
    
    /**
     * Negates the vector, making it point into the exact opposite direction while preserving length.
     */
    default Vector3 negate() {
        return multiply(-1);
    }

    /**
     * Divides the vector through its own length / Sets the length of the vector to 1.
     *
     * @see #getLength()
     * @see #setLength(double)
     */
    default Vector3 normalize() {
        return setLength(1);
    }

    //TRANSFORMATIONS
    
    /**
     * Transforms this vector using a 3x3 matrix.
     *
     * @param m the transformation matrix
     */
    abstract Vector3 transform(Matrix m);
    
    /**
     * Rotates this vector around the x-axis.
     *
     * @param angle the angle in radians
     * @see Matrix
     * @apiNote
     *     <p>
     *         The default implementation is performing the rotation using rotation matrices.
     *     </p>
     *     <p>
     *         Any implementation should replicate the behavior of {@link Matrix#fromRotX(double)} passed as a
     *         parameter of {@link #transform(Matrix)} to ensure consistency.
     *     </p>
     */
    default Vector3 rotateX(double angle) {
        final Matrix rot = Matrix.fromRotX(angle);
        double x = getX(), y = getY(), z = getZ();
        return set(
            x,
            y*rot.get(1, 1) + z*rot.get(1, 2),
            z*rot.get(2, 1) + z*rot.get(2, 2));
    }
    
    /**
     * Rotates this vector around the y-axis.
     *
     * @param angle the angle in radians
     * @see Matrix
     * @apiNote
     *     <p>
     *         The default implementation is performing the rotation using rotation matrices.
     *     </p>
     *     <p>
     *         Any implementation should replicate the behavior of {@link Matrix#fromRotY(double)} passed as a
     *         parameter of {@link #transform(Matrix)} to ensure consistency.
     *     </p>
     */
    default Vector3 rotateY(double angle) {
        final Matrix rot = Matrix.fromRotY(angle);
        double x = getX(), y = getY(), z = getZ();
        return set(
            x*rot.get(0, 0) + z*rot.get(0, 2),
            y,
            x*rot.get(2, 0) + z*rot.get(2, 2));
    }
    
    /**
     * Rotates this vector around the z-axis.
     *
     * @param angle the angle in radians
     * @see Matrix
     * @apiNote
     *     <p>
     *         The default implementation is performing the rotation using rotation matrices.
     *     </p>
     *     <p>
     *         Any implementation should replicate the behavior of {@link Matrix#fromRotZ(double)} passed as a
     *         parameter of {@link #transform(Matrix)} to ensure consistency.
     *     </p>
     */
    default Vector3 rotateZ(double angle) {
        final Matrix rot = Matrix.fromRotY(angle);
        double x = getX(), y = getY(), z = getZ();
        return set(
            x*rot.get(0, 0) + y*rot.get(0, 1),
            x*rot.get(1, 0) + y*rot.get(1, 1),
            z);
    }

    // MISC

    /**
     * Converts this vector into a new array of hypot 3 which contains the
     * x, y, and z coordinates in order.
     *
     * @return the coordinates of this vector in a new array
     */
    default double[] toArray() {
        return new double[] {getX(), getY(), getZ()};
    }

    /**
     * Converts this vector to block coordinates. This means getting the coordinates of the block
     * in space that this vector is inside.
     *
     * @return new block coordinates
     */
    default BlockVector toBlockVector() {
        return BlockVector.fromVector(this);
    }

    abstract Vector3 clone();

}
