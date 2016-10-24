package net.grian.spatium.api;

import net.grian.spatium.impl.Plane6f;

public interface Plane extends SpatiumObject {
	
	public static Plane create(float xc, float yc, float zc, float xn, float yn, float zn) {
		return new Plane6f(xc, yc, zc, xn, yn, zn);
	}
	
	public static Plane create(Vector center, Vector normal) {
		return new Plane6f(center, normal);
	}
	
	public static Plane create() {
		return new Plane6f();
	}
	
	// GETTERS
	
	public abstract Vector getCenter();
	
	public abstract Vector getNormal();
	
	// CHECKERS
	
	public abstract boolean contains(float x, float y, float z);
	
	public default boolean contains(Vector point) {
		return contains(point.getX(), point.getY(), point.getZ());
	}
	
	// SETTERS
	
	public abstract Plane setCenter(float x, float y, float z);
	
	public default Plane setCenter(Vector v) {
		return setCenter(v.getX(), v.getY(), v.getZ());
	}
	
	public abstract Plane setNormal(float x, float y, float z);
	
	public default Plane setNormal(Vector v) {
		return setNormal(v.getX(), v.getY(), v.getZ());
	}

}
