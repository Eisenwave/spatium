package net.grian.spatium.api;

import net.grian.spatium.coll.Collideable;
import net.grian.spatium.impl.Ray6f;

public interface Ray extends SpatiumObject, Collideable {
	
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
	
	public abstract float getOriginX();
	
	public abstract float getOriginY();
	
	public abstract float getOriginZ();
	
	public abstract float getDirX();
	
	public abstract float getDirY();
	
	public abstract float getDirZ();
	
	public abstract float getLength();
	
	public abstract Vector getOrigin();
	
	public abstract Vector getDirection();
	
	public abstract Vector getEnd();
	
	public abstract Vector mostPositivePoint();
	
	public abstract Vector mostNegativePoint();
	
	/**
	 * Returns the closest point on this ray to another ray. Note that this
	 * point can be further away from the origin of the ray than the length
	 * of the ray, the ray is treated as if it were infinitely long.
	 * @param point the point
	 * @return the closest point on this ray to another point
	 */
	public abstract Vector closestPointTo(Vector point);
	
	// CHECKERS
	
	public abstract boolean contains(float x, float y, float z);
	
	public default boolean contains(Vector point) {
		return contains(point.getX(), point.getY(), point.getZ());
	}
	
	/**
	 * Tests at which point of the ray the ray collides with another point.
	 * @param x the x-coordinate of the point
	 * @param y the y-coordinate of the point
	 * @param z the z-coordinate of the point
	 * @return the ray multiplier or {@link Float#NaN} if there is no collision
	 */
	public abstract float containsAt(float x, float y, float z);
	
	public default float containsAt(Vector point) {
		return containsAt(point.getX(), point.getY(), point.getZ());
	}
	
	public default boolean equals(Ray ray) {
		return
				getOrigin().equals(ray.getOrigin()) &&
				getDirection().equals(ray.getDirection());
	}
	
	// SETTERS
	
	public abstract Ray setOrigin(float x, float y, float z);
	
	public abstract Ray setDirection(float x, float y, float z);
	
	public abstract Ray setLength(float t);
	
	// MISC
	
	public abstract Ray clone();

}
