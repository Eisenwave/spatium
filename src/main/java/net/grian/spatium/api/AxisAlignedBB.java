package net.grian.spatium.api;

import net.grian.spatium.impl.AxisAlignedBB6f;

/**
 * An axis aligned bounding box, or the cubical space between two points.
 */
public interface AxisAlignedBB extends BoundingBox {
	
	public static AxisAlignedBB fromPoints(float xa, float ya, float za, float xb, float yb, float zb) {
		return new AxisAlignedBB6f(xa, ya, za, xb, yb, zb);
	}
	
	public static AxisAlignedBB cube(Vector center, float size) {
		return new AxisAlignedBB6f(
				center.getX() - size,
				center.getY() - size,
				center.getZ() - size,
				center.getX() + size,
				center.getY() + size,
				center.getZ() + size
				);
	}
	
	public static AxisAlignedBB between(Vector from, Vector to) {
		return fromPoints(from.getX(), from.getY(), from.getZ(), to.getX(), to.getY(), to.getZ());
	}
	
	// GETTERS
	
	public abstract float getMinX();
	
	public abstract float getMinY();
	
	public abstract float getMinZ();
	
	public abstract float getMaxX();
	
	public abstract float getMaxY();
	
	public abstract float getMaxZ();
	
	public abstract Vector getDimensions();
	
	public abstract float getVolume();
	
	// CHECKERS
	
	public abstract boolean contains(float x, float y, float z);
	
	public default boolean contains(Vector point) {
		return contains(point.getX(), point.getY(), point.getZ());
	}
	
	// SETTERS
	
	public abstract AxisAlignedBB move(float x, float y, float z);
	
	public default AxisAlignedBB move(Vector v) {
		return move(v.getX(), v.getY(), v.getZ());
	}
	
	public abstract AxisAlignedBB scale(float x, float y, float z);
	
	public default AxisAlignedBB scale(Vector v) {
		return scale(v.getX(), v.getY(), v.getZ());
	}
	
	public abstract AxisAlignedBB grow(float x, float y, float z);
	
	public default AxisAlignedBB grow(Vector v) {
		return grow(v.getX(), v.getY(), v.getZ());
	}
	
	// MISC
	
	public abstract AxisAlignedBB clone();

}
