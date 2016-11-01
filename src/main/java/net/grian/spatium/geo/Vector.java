package net.grian.spatium.geo;

import net.grian.spatium.MinecraftSpecific;
import net.grian.spatium.Spatium;
import net.grian.spatium.SpatiumObject;
import net.grian.spatium.impl.Vector3f;
import net.grian.spatium.util.MinecraftGeometry;

/**
 * A three-dimensional vector. Can be either karthesian or polar depending on
 * the implementation.
 */
public interface Vector extends SpatiumObject {

    /**
     * Creates a new Vector.
     *
     * @return a new Vector
     */
    public static Vector create() {
        return new Vector3f();
    }

    /**
     * Creates a new Vector from 3 three coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return a new Vector
     */
    public static Vector fromXYZ(float x, float y, float z) {
        return new Vector3f(x, y, z);
    }

    /**
     * Creates a new Vector from block coordinates.
     *
     * @param block the block coordinates
     * @return a new Vector
     */
    public static Vector fromBlock(BlockVector block) {
        return new Vector3f(block);
    }

    /**
     * Creates a new Vector from a radius (length), a yaw and a pitch.
     *
     * @param radius the radius
     * @param yaw the yaw
     * @param pitch the pitch
     * @return a new Vector
     */
    public static Vector fromRadiusYawPitch(float radius, float yaw, float pitch) {
        return MinecraftGeometry.vectorFromYawPitchDeg(yaw, pitch).setLength(radius);
    }

    /**
     * Creates a new non-normalized Vector from a yaw and a pitch.
     *
     * @param yaw the yaw
     * @param pitch the pitch
     * @return a new Vector
     */
    public static Vector fromYawPitch(float yaw, float pitch) {
        return MinecraftGeometry.vectorFromYawPitchDeg(yaw, pitch);
    }

    /**
     * Creates a new Vector between two points. The vector will be pointing
     * from {@code from} to {@code to}.
     *
     * @param from the first point
     * @param to the second point
     * @return a new Vector between these points
     */
    public static Vector between(Vector from, Vector to) {
        return new Vector3f(
                to.getX() - from.getX(),
                to.getY() - from.getY(),
                to.getZ() - from.getZ());
    }

    /**
     * Returns the sum of all the vectors's coordinates.
     *
     * @param vectors the vectors
     * @return the sum of vectors
     * @throws IllegalArgumentException if the array is empty
     */
    public static Vector sum(Vector... vectors) {
        if (vectors.length == 0) throw new IllegalArgumentException("no vectors given");
        if (vectors.length == 1) return vectors[0].clone();

        float x = 0, y = 0, z = 0;
        for (Vector v : vectors) {
            x += v.getX();
            y += v.getY();
            z += v.getZ();
        }

        return Vector.fromXYZ(x, y, z);
    }

    /**
     * Returns the average of all the vector's coordinates.
     *
     * @param vectors the vectors
     * @return average vector
     * @throws IllegalArgumentException if the array is empty
     */
    public static Vector average(Vector... vectors) {
        return sum(vectors).divide(vectors.length);
    }

    // GETTERS

    /**
     * Returns the x of the vector.
     * @return the x of the vector
     */
    public abstract float getX();

    /**
     * Returns the y of the vector.
     *
     * @return the y of the vector
     */
    public abstract float getY();

    /**
     * Returns the z of the vector.
     *
     * @return the z of the vector
     */
    public abstract float getZ();

    /**
     * Returns the yaw of the vector in degrees.
     *
     * @return the yaw of the vector
     */
    @MinecraftSpecific
    public abstract float getYaw();

    /**
     * Returns the pitch of the vector.
     *
     * @return the pitch of the vector
     */
    @MinecraftSpecific
    public abstract float getPitch();

    /**
     * Returns the length of the vector.
     *
     * @return the length of the vector
     */
    public abstract float getLength();

    /**
     * Returns the squared length of the vector.
     *
     * @return the squared length of the vector
     */
    public abstract float getLengthSquared();

    /**
     * Returns the distance between this vector and a point.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return the distance to the point
     */
    public abstract float distanceTo(float x, float y, float z);

    /**
     * Returns the distance between this vector and a point.
     *
     * @param point the point
     * @return the distance to the point
     */
    public default float distanceTo(Vector point) {
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
    public abstract float angleTo(float x, float y, float z);

    /**
     * Returns the signed angle between this vector and another vector.
     *
     * @param v the vector
     * @return the signed angle to another vector
     */
    public default float angleTo(Vector v) {
        return angleTo(v.getX(), v.getY(), v.getZ());
    }

    /**
     * Returns the signed angle of this vector to a plane. This is equivalent to the signed angle of the plane's
     * normal vector.
     *
     * @param plane the plane
     * @return the signed angle to the plane
     */
    public default float angleTo(Plane plane) {
        return angleTo(plane.getNormalX(), plane.getNormalY(), plane.getNormalZ());
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
    public default Vector midPoint(Vector point, float t) {
        if (t < 0 || t > 1) throw new IllegalArgumentException("weight out of range ("+t+")");
        float k = 1-t;
        return Vector.fromXYZ(
                (this.getX() * k) + (point.getX() * t),
                (this.getX() * k) + (point.getY() * t),
                (this.getX() * k) + (point.getZ() * t));
    }

    /**
     * Returns the middle-point between this point and another point.
     *
     * @param point the point
     * @return the middle-point to another point
     * @see #midPoint(Vector, float)
     */
    public default Vector midPoint(Vector point) {
        return midPoint(point, 0.5f);
    }

    /**
     * Returns the dot-product of this and another vector.
     *
     * @param x the x coordinate of the vector
     * @param y the y coordinate of the vector
     * @param z the z coordinate of the vector
     * @return the dot-product of this and another vector
     */
    public abstract float dot(float x, float y, float z);

    /**
     * Returns the dot-product of this and another vector.
     *
     * @param v the vector
     * @return the dot-product of this and another vector
     */
    public default float dot(Vector v) {
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
    public abstract Vector cross(float x, float y, float z);

    /**
     * Returns the cross-product of this and another vector.
     *
     * @param v the vector
     * @return the cross-product of this and another vector
     */
    public default Vector cross(Vector v) {
        return cross(v.getX(), v.getY(), v.getZ());
    }

    // CHECKERS

    /**
     * Returns whether this vector equals another vector.
     *
     * <br><br>The margin for floating point inaccuracies is
     * {@link Spatium#EPSILON}
     *
     * @param v the vector
     * @return whether this vector equals another vector
     */
    public default boolean equals(Vector v) {
        return
                Spatium.equals(getX(), v.getX()) &&
                Spatium.equals(getY(), v.getY()) &&
                Spatium.equals(getZ(), v.getZ());
    }

    // SETTERS

    /**
     * Sets the coordinates of this vector to new coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return itself
     */
    public abstract Vector set(float x, float y, float z);

    /**
     * Sets the coordinates of this vector to the coordinates of another vector.
     *
     * @param v the vector
     * @return itself
     */
    public default Vector set(Vector v) {
        return set(v.getX(), v.getY(), v.getZ());
    }

    /**
     * Sets the x of the vector.
     *
     * @param x the x of the vector
     * @return itself
     */
    public abstract Vector setX(float x);

    /**
     * Sets the y of the vector.
     *
     * @param y the y of the vector
     * @return itself
     */
    public abstract Vector setY(float y);

    /**
     * Sets the z of the vector.
     *
     * @param z the z of the vector
     * @return itself
     */
    public abstract Vector setZ(float z);

    // OPERATIONS

    /**
     * Adds another vector to this vector.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return itself
     */
    public abstract Vector add(float x, float y, float z);

    /**
     * Adds another vector to this vector.
     *
     * @param v the vector
     * @return itself
     */
    public default Vector add(Vector v) {
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
    public abstract Vector subtract(float x, float y, float z);

    /**
     * Subtracts another vector from this vector.
     *
     * @param v the vector to subtract
     * @return itself
     */
    public default Vector subtract(Vector v) {
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
    public abstract Vector multiply(float x, float y, float z);

    /**
     * Scales this vector by a factor.
     * @param factor the factor
     * @return itself
     */
    public default Vector multiply(float factor) {
        return multiply(factor, factor, factor);
    }

    /**
     * Divides the coordinates of this vector.
     *
     * @param x the x divisor
     * @param y the y divisor
     * @param z the z divisor
     * @return itself
     */
    public abstract Vector divide(float x, float y, float z);

    /**
     * Divides this vector by a divisor.
     *
     * @param divisor the divisor
     * @return itself
     */
    public default Vector divide(float divisor) {
        return divide(divisor, divisor, divisor);
    }

    /**
     * Divides the vector through its own length / Sets the length of the
     * vector to 1.
     * @return itself
     * @see #getLength()
     */
    public default Vector normalize() {
        return setLength(1);
    }

    /**
     * Sets the length of the vector.
     *
     * @param length the length of the vector
     * @return itself
     * @see #normalize()
     */
    public abstract Vector setLength(float length);

    /**
     * Sets the yaw of this vector.
     *
     * @param yaw the yaw
     * @return itself
     */
    @MinecraftSpecific
    public abstract Vector setYaw(float yaw);

    /**
     * Sets the pitch of this vector.
     *
     * @param pitch the pitch
     * @return itself
     */
    @MinecraftSpecific
    public abstract Vector setPitch(float pitch);

    /**
     * Sets the radius (length), yaw and pitch of this vector.
     *
     * @param radius the radius
     * @param yaw the yaw
     * @param pitch the pitch
     * @return itself
     */
    @MinecraftSpecific
    public abstract Vector setRadiusYawPitch(float radius, float yaw, float pitch);

    /**
     * Sets the yaw and pitch of this vector but keeps the radius (length).
     *
     * @param yaw the yaw
     * @param pitch the pitch
     * @return itself
     */
    @MinecraftSpecific
    public default Vector setYawPitch(float yaw, float pitch) {
        return setRadiusYawPitch(getLength(), yaw, pitch);
    }

    // MISC

    /**
     * Converts this vector into a new array of length 3 which contains the
     * x, y, and z coordinates in order.
     *
     * @return the coordinates of this vector in a new array
     */
    public default float[] toArray() {
        return new float[] {getX(), getY(), getZ()};
    }

    /**
     * Converts this vector to block coordinates. This means getting the coordinates of the block
     * in space that this vector is inside.
     *
     * @return new block coordinates
     */
    public default BlockVector toBlockVector() {
        return BlockVector.fromVector(this);
    }

    public abstract Vector clone();

}
