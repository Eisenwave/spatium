package net.grian.spatium.api;

import net.grian.spatium.Spatium;
import net.grian.spatium.coll.Collideable;
import net.grian.spatium.impl.Ray6f;

public interface Ray extends Collideable {
	
	public static Ray fromOriginAndDirection(float xo, float yo, float zo, float xd, float yd, float zd) {
		return new Ray6f(xo, yo, zo, xd, yd, zd);
	}
	
	public static Ray fromOriginAndDirection(Vector origin, Vector dir) {
		return new Ray6f(origin, dir);
	}
	
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
	public abstract Vector getOrigin();
	
	/**
	 * Returns the direction of this ray in a new vector.
	 * 
	 * @return the direction of this ray in a new vector
	 */
	public abstract Vector getDirection();
	
	/**
	 * Returns the end of this ray in a new vector. This is equal to the sum of
	 * the origin and direction of the vector.
	 * 
	 * @return the end of this ray in a new vector
	 */
	public abstract Vector getEnd();
	
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
