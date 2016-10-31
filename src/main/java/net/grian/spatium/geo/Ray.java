package net.grian.spatium.geo;

import net.grian.spatium.Spatium;
import net.grian.spatium.SpatiumObject;
import net.grian.spatium.impl.Ray6f;

/**
 * A ray, defined by an origin and a direction.
 */
public interface Ray extends SpatiumObject {

    /**
     * Creates a new ray from 3 coordinates for the origin point and 3 coordinates for the direction vector.
     *
     * @param xo the x-coordinate of the origin
     * @param yo the y-coordinate of the origin
     * @param zo the z-coordinate of the origin
     * @param xd the x-coordinate of the direction
     * @param yd the y-coordinate of the direction
     * @param zd the z-coordinate of the direction
     * @return a new ray
     */
	public static Ray fromOriginAndDirection(float xo, float yo, float zo, float xd, float yd, float zd) {
		return new Ray6f(xo, yo, zo, xd, yd, zd);
	}

    /**
     * Creates a new ray from an origin point and a direction vector.
     *
     * @param origin the origin
     * @param dir the direction
     * @return a new ray
     */
	public static Ray fromOriginAndDirection(Vector origin, Vector dir) {
		return new Ray6f(origin, dir);
	}

    /**
     * Creates a new ray between two points.
     * The origin will be a copy of the point {@code from}.
     * The directional vector will be a new vector from {@code from} to {@code to}.
     *
     * @param from the first point
     * @param to the second point
     * @return a new ray
     */
	public static Ray between(Vector from, Vector to) {
		return new Ray6f(from, Vector.between(from, to));
	}
	
	// GETTERS
	
	/**
	 * Returns the x-coordinate of the origin of this ray.
	 * 
	 * @return the x-coordinate of the origin of this ray
	 */
	public abstract float getOriginX();
	
	/**
	 * Returns the y-coordinate of the origin of this ray.
	 * 
	 * @return the y-coordinate of the origin of this ray
	 */
	public abstract float getOriginY();
	
	/**
	 * Returns the z-coordinate of the origin of this ray.
	 * 
	 * @return the z-coordinate of the origin of this ray
	 */
	public abstract float getOriginZ();
	
	/**
	 * Returns the x-coordinate of the direction of this ray.
	 * 
	 * @return the x-coordinate of the direction of this ray
	 */
	public abstract float getDirX();
	
	/**
	 * Returns the y-coordinate of the direction of this ray.
	 * 
	 * @return the y-coordinate of the direction of this ray
	 */
	public abstract float getDirY();
	
	/**
	 * Returns the z-coordinate of the direction of this ray.
	 * 
	 * @return the z-coordinate of the direction of this ray
	 */
	public abstract float getDirZ();
	
	/**
	 * Returns the length of this ray.
	 * 
	 * @return the length of this ray
	 */
	public abstract float getLength();
	
	/**
	 * Returns the origin of this ray in a new vector.
	 * 
	 * @return the origin of this ray in a new vector
	 */
	public default Vector getOrigin() {
		return Vector.fromXYZ(getOriginX(), getOriginY(), getOriginZ());
	}
	
	/**
	 * Returns the direction of this ray in a new vector.
	 * 
	 * @return the direction of this ray in a new vector
	 */
	public default Vector getDirection() {
        return Vector.fromXYZ(getDirX(), getDirY(), getDirZ());
    }
	
	/**
	 * Returns the end of this ray in a new vector. This is equal to the sum of
	 * the origin and direction of the vector.
	 * 
	 * @return the end of this ray in a new vector
	 */
	public default Vector getEnd() {
        return Vector.fromXYZ(getOriginX() + getDirX(), getOriginY() + getDirY(), getOriginZ() + getDirZ());
    }
	
	public abstract Vector mostPositivePoint();
	
	public abstract Vector mostNegativePoint();
	
	/**
	 * Returns the closest point on this ray to another ray. Note that this
	 * point can be further away from the origin of the ray than the length
	 * of the ray, the ray is treated as if it were infinitely long.
	 * 
	 * @param point the point
	 * @return the closest point on this ray to another point
	 */
	public abstract Vector closestPointTo(Vector point);
	
	// CHECKERS
	
	/**
	 * Returns whether the ray is equal to the given point at any point.
	 * 
	 * @param x the x-coordinate of the point
	 * @param y the y-coordinate of the point
	 * @param z the z-coordinate of the point
	 * @return whether the ray is equal to the given point at any point
	 */
	public abstract boolean contains(float x, float y, float z);
	
	/**
	 * Returns whether the ray is equal to the given point at any point.
	 * 
	 * @param point the point
	 * @return whether the ray is equal to the given point at any point
	 */
	public default boolean contains(Vector point) {
		return contains(point.getX(), point.getY(), point.getZ());
	}
	
	/**
	 * Tests at which point of the ray the ray collides with another point.
	 * 
	 * @param x the x-coordinate of the point
	 * @param y the y-coordinate of the point
	 * @param z the z-coordinate of the point
	 * @return the ray multiplier or {@link Float#NaN} if there is no collision
	 */
	public abstract float containsAt(float x, float y, float z);
	
	/**
	 * Tests at which point of the ray the ray collides with another point.
	 * 
	 * @param point the point
	 * @return the ray multiplier or {@link Float#NaN} if there is no collision
	 */
	public default float containsAt(Vector point) {
		return containsAt(point.getX(), point.getY(), point.getZ());
	}
	
	/**
	 * Returns whether this ray is equal to another ray. This condition is true
	 * if both origin and direction are equal.
	 * 
	 * @param ray the ray
	 * @return whether this ray is equal to the ray
	 */
	public default boolean equals(Ray ray) {
		return
				Spatium.equals(this.getOriginX(), ray.getOriginX()) &&
				Spatium.equals(this.getOriginY(), ray.getOriginY()) &&
				Spatium.equals(this.getOriginZ(), ray.getOriginZ()) &&
				Spatium.equals(this.getDirX(), ray.getDirX()) &&
				Spatium.equals(this.getDirY(), ray.getDirY()) &&
				Spatium.equals(this.getDirZ(), ray.getDirZ());
	}
	
	// SETTERS
	
	/**
	 * Sets the origin of the ray to a given point.
	 * 
	 * @param x the x-coordinate of the point
	 * @param y the y-coordinate of the point
	 * @param z the z-coordinate of the point
	 * @return itself
	 */
	public abstract Ray setOrigin(float x, float y, float z);

    /**
     * Sets the origin of the ray to a given point.
     *
     * @param point the point
     * @return itself
     */
    public default Ray setOrigin(Vector point) {
        return setOrigin(point.getX(), point.getY(), point.getZ());
    }
	
	/**
	 * Sets the direction of the ray to a given vector.
	 * 
	 * @param x the x-coordinate of the vector
	 * @param y the y-coordinate of the vector
	 * @param z the z-coordinate of the vector
	 * @return itself
	 */
	public abstract Ray setDirection(float x, float y, float z);
	
	/**
	 * Sets the length of the ray to a given length.
	 * 
	 * @param t the new length
	 * @return itself
	 */
	public abstract Ray setLength(float t);
	
	// MISC
	
	public abstract Ray clone();

}
