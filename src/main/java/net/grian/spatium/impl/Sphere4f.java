package net.grian.spatium.impl;

import net.grian.spatium.api.Sphere;
import net.grian.spatium.api.Vector;

public class Sphere4f implements Sphere {
	
	private static final long serialVersionUID = 9087738324681226586L;
	
	private float x, y, z, r;
	
	public Sphere4f(float x, float y, float z, float r) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.r = r;
	}
	
	public Sphere4f() {
		this(0, 0, 0, 0);
	}
	
	public Sphere4f(Sphere4f sphere) {
		this(sphere.x, sphere.y, sphere.z, sphere.r);
	}
	
	public Sphere4f(Vector center, float r) {
		this(center.getX(), center.getY(), center.getZ(), r);
	}
	
	// GETTERS
	
	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public float getZ() {
		return z;
	}
	
	@Override
	public Vector getCenter() {
		return Vector.fromXYZ(x, y, z);
	}
	
	@Override
	public float getRadius() {
		return r;
	}
	
	@Override
	public float getRadiusSquared() {
		return r*r;
	}
	
	// CHECKERS

	@Override
	public boolean contains(float x, float y, float z) {
		float
		xd = this.x - x,
		yd = this.y - y,
		zd = this.z - z;
		
		return Math.sqrt(xd*xd + yd*yd + zd*zd) <= r;
	}
		
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Sphere && equals((Sphere) obj);
	}
	
	// SETTERS
	
	@Override
	public Sphere setCenter(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	@Override
	public Sphere scale(float factor) {
		if (factor < 0) throw new IllegalArgumentException("factor < 0");
		this.r *= factor;
		return this;
	}

	@Override
	public Sphere move(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	@Override
	public Sphere setRadius(float radius) {
		if (radius < 0) throw new IllegalArgumentException("radius < 0");
		this.r = radius;
		return this;
	}
	
	// MISC
	
	@Override
	public Sphere clone() {
		return new Sphere4f(this);
	}

}
