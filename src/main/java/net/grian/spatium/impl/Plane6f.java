package net.grian.spatium.impl;

import net.grian.spatium.Spatium;
import net.grian.spatium.geo.Plane;
import net.grian.spatium.geo.Vector;

public class Plane6f implements Plane {
	
	private static final long serialVersionUID = 196722359952800225L;
	
	private float xc, yc, zc, xn, yn, zn;
	
	public Plane6f(float xc, float yc, float zc, float xn, float yn, float zn) {
		this.xc = xc;
		this.yc = yc;
		this.zc = zc;
		this.xn = xn;
		this.yn = yn;
		this.zn = zn;
	}
	
	public Plane6f(Plane6f copyOf) {
		this.xc = copyOf.xc;
		this.yc = copyOf.yc;
		this.zc = copyOf.zc;
		this.xn = copyOf.xn;
		this.yn = copyOf.yn;
		this.zn = copyOf.zn;
	}
	
	public Plane6f(Vector center, Vector normal) {
		this(
				center.getX(), center.getY(), center.getZ(),
				normal.getX(), normal.getY(), normal.getZ());
	}
	
	public Plane6f() {
		this(0, 0, 0, 0, 0, 0);
	}
	
	// GETTERS
	
	@Override
	public float getCenterX() {
		return xc;
	}

	@Override
	public float getCenterY() {
		return yc;
	}

	@Override
	public float getCenterZ() {
		return zc;
	}

	@Override
	public float getNormalX() {
		return xn;
	}

	@Override
	public float getNormalY() {
		return yn;
	}

	@Override
	public float getNormalZ() {
		return zn;
	}

	@Override
	public Vector getCenter() {
		return Vector.fromXYZ(xc, yc, zc);
	}

	@Override
	public Vector getNormal() {
		return Vector.fromXYZ(xn, yn, zn);
	}
	
	// CHECKERS

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Plane && equals((Plane) obj);
	}
	
	@Override
	public boolean contains(float x, float y, float z) {
		float
		dx = x - xc,
		dy = y - yc,
		dz = z - zc;
		
		// dot product of normal vector and dist between center and point
		return Spatium.equals(xn*dx + y*dy + z*dz, 0) ;
	}
	
	// GETTERS

	@Override
	public Plane setCenter(float x, float y, float z) {
		this.xc = x;
		this.yc = y;
		this.zc = z;
		return this;
	}

	@Override
	public Plane setNormal(float x, float y, float z) {
		this.xn = x;
		this.yn = y;
		this.zn = z;
		return this;
	}
	
	// MISC
	
	@Override
	public Plane clone() {
		return new Plane6f(this);
	}

}
