package net.grian.spatium.api;

import net.grian.spatium.Spatium;
import net.grian.spatium.coll.Collideable;
import net.grian.spatium.impl.Sphere4f;

public interface Sphere extends Collideable {
	
	public static Sphere create() {
		return new Sphere4f();
	}
	
	public static Sphere fromCenterAndRadius(Vector center, float radius) {
		return new Sphere4f(center, radius);
	}
	
	// GETTERS
	
	public abstract Vector getCenter();
	
	public abstract float getRadius();
	
	public abstract float getRadiusSquared();
	
	public default float getDiameter() {
		return getRadius() * 2;
	}
	
	// CHECKERS
	
	public abstract boolean contains(float x, float y, float z);
	
	public default boolean contains(Vector point) {
		return contains(point.getX(), point.getY(), point.getZ());
	}
	
	public default boolean equals(Sphere sphere) {
		return
				getCenter().equals(sphere.getCenter()) &&
				Spatium.equals(getRadius(), sphere.getRadius());
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
	
	public abstract Sphere scale(float factor);
	
	public abstract Sphere setRadius(float r);
	
	public default Sphere setDiameter(float d) {
		return setRadius(d * 0.5f);
	}
	
	// MISC
	
	public abstract Sphere clone();

}
