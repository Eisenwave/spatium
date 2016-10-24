package net.grian.spatium.impl;

import net.grian.spatium.Spatium;
import net.grian.spatium.api.Plane;
import net.grian.spatium.api.Vector;

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
	
	public Plane6f(Vector center, Vector normal) {
		this(
				center.getX(), center.getY(), center.getZ(),
				normal.getX(), normal.getY(), normal.getZ());
	}
	
	public Plane6f() {
		this(0, 0, 0, 0, 0, 0);
	}

	@Override
	public Vector getCenter() {
		return Vector.fromXYZ(xc, yc, zc);
	}

	@Override
	public Vector getNormal() {
		return Vector.fromXYZ(xn, yn, zn);
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
	
	

}
