package net.grian.spatium.geo;

import net.grian.spatium.Spatium;
import net.grian.spatium.coll.Collideable;
import net.grian.spatium.impl.Sphere4f;

public interface Sphere extends Collideable {
	
	/**
	 * Constructs a new sphere.
	 * 
	 * @return a new sphere
	 */
	public static Sphere create() {
		return new Sphere4f();
	}
	
	/**
	 * Constructs a new sphere from a center and a radius.
	 * 
	 * @param center the center
	 * @param radius the radius
	 * @return a new sphere
	 */
	public static Sphere fromCenterAndRadius(Vector center, float radius) {
		return new Sphere4f(center, radius);
	}
	
	// GETTERS
	
	/**
	 * Returns the x-coordinate of the center of this sphere.
	 * 
	 * @return the x-coordinate of the center of this sphere
	 */
	public abstract float getX();
	
	/**
	 * Returns the y-coordinate of the center of this sphere.
	 * 
	 * @return the y-coordinate of the center of this sphere
	 */
	public abstract float getY();
	
	/**
	 * Returns the z-coordinate of the center of this sphere.
	 * 
	 * @return the z-coordinate of the center of this sphere
	 */
	public abstract float getZ();
	
	/**
	 * Returns the center of this sphere.
	 * 
	 * @return the center of this sphere
	 */
	public abstract Vector getCenter();
	
	/**
	 * Returns the radius of this sphere.
	 * 
	 * @return the radius of this sphere
	 */
	public abstract float getRadius();
	
	/**
	 * Returns the squared radius of this sphere.
	 * 
	 * @return the squared radius of this sphere
	 */
	public abstract float getRadiusSquared();
	
	/**
	 * Returns the diameter of this sphere.
	 * 
	 * @return the diameter of this sphere
	 */
	public default float getDiameter() {
		return getRadius() * 2;
	}
	
	// CHECKERS
	
	/**
	 * Returns whether this sphere contains a given point.
	 * 
	 * @param x the x-coordinate of the point
	 * @param y the y-coordinate of the point
	 * @param z the z-coordinate of the point
	 * @return whether this sphere contains the point
	 */
	public abstract boolean contains(float x, float y, float z);
	
	/**
	 * Returns whether this sphere contains a given point.
	 * 
	 * @param point the point
	 * @return whether this sphere contains the point
	 */
	public default boolean contains(Vector point) {
		return contains(point.getX(), point.getY(), point.getZ());
	}
	
	/**
	 * Returns whether this sphere is equal to another sphere.
	 * 
	 * @param sphere the sphere
	 * @return whether this is equal to the sphere
	 */
	public default boolean equals(Sphere sphere) {
		return
				Spatium.equals(this.getX(), sphere.getX()) &&
				Spatium.equals(this.getY(), sphere.getY()) &&
				Spatium.equals(this.getZ(), sphere.getZ()) &&
				Spatium.equals(this.getRadius(), sphere.getRadius());
	}
	
	// SETTERS
	
	public abstract Sphere setCenter(float x, float y, float z);
	
	public default Sphere setCenter(Vector center) {
		return setCenter(center.getX(), center.getY(), center.getZ());
	}
	
	public abstract Sphere move(float x, float y, float z);
	
	public default Sphere move(Vector center) {
		return move(center.getX(), center.getY(), center.getZ());
	}
	
	/**
	 * Scales the sphere by a factor. This is equivalent to multiplying the
	 * radius of the sphere with the factor.
	 * 
	 * @param factor the factor
	 * @return itself
	 */
	public abstract Sphere scale(float factor);
	
	/**
	 * Sets the radius of the sphere.
	 * 
	 * @param r new the radius
	 * @return itself
	 */
	public abstract Sphere setRadius(float r);
	
	/**
	 * Sets the diameter of the sphere.
	 * 
	 * @param d new the diameter
	 * @return itself
	 */
	public default Sphere setDiameter(float d) {
		return setRadius(d * 0.5f);
	}
	
	// MISC
	
	public abstract Sphere clone();
	
	@Override
	public default byte getCollisionId() {
		return SPHERE;
	}

}