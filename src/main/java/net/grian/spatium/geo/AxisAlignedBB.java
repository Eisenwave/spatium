package net.grian.spatium.geo;

import net.grian.spatium.impl.AxisAlignedBB6f;

/**
 * An axis aligned bounding box, or the cubical space between two points.
 */
public interface AxisAlignedBB extends BoundingBox {
	
	/**
	 * Creates a new bounding box between two points.
	 * 
	 * @param xa the x of the first point
	 * @param ya the y of the first point
	 * @param za the z of the first point
	 * @param xb the x of the second point
	 * @param yb the y of the second point
	 * @param zb the z of the second point
	 * @return a new bounding box
	 */
    public static AxisAlignedBB fromPoints(float xa, float ya, float za, float xb, float yb, float zb) {
		return new AxisAlignedBB6f(xa, ya, za, xb, yb, zb);
	}

	/**
     * Creates a new cubical axis aligned bounding box around a center and of a set size. Each side of the cube will
     * be twice as long as the specified size.
     *
	 * @param center the center of the cube
	 * @param size the size of the cube
	 * @return a new axis aligned bounding box
	 */
	public static AxisAlignedBB createCube(Vector center, float size) {
		return new AxisAlignedBB6f(
				center.getX() - size,
				center.getY() - size,
				center.getZ() - size,
				center.getX() + size,
				center.getY() + size,
				center.getZ() + size
				);
	}
	
	/**
	 * Creates a new bounding box between two points.
	 * 
	 * @param from the first point
	 * @param to the second point
	 * @return a new bounding box
	 */
	public static AxisAlignedBB between(Vector from, Vector to) {
		return fromPoints(from.getX(), from.getY(), from.getZ(), to.getX(), to.getY(), to.getZ());
	}
	
	// GETTERS

	public float getMinX();

	public float getMinY();

	public float getMinZ();

	public float getMaxX();

	public float getMaxY();

	public float getMaxZ();

	public float getSizeX();

	public float getSizeY();

	public float getSizeZ();
	
	/**
	 * Returns the dimensions of the bounding box in a new vector.
	 * 
	 * @return the dimensions of the bounding box in a new vector
	 */
	public default Vector getDimensions() {
		return Vector.fromXYZ(getSizeX(), getSizeY(), getSizeZ());
	}
	
	/**
	 * Returns the volume of the bounding box.
	 * 
	 * @return the volume of the bounding box
	 */
	public default float getVolume() {
		return getSizeX() * getSizeY() * getSizeZ();
	}
	
	// CHECKERS
	
	/**
	 * Returns whether this bounding box contains a point of given coordinates.
	 * 
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 * @param z the z-coordinate
	 * @return whether this bounding box contains a point of given coordinates
	 */
	public boolean contains(float x, float y, float z);
	
	/**
	 * Returns whether this bounding box contains a point of given coordinates.
	 * 
	 * @param point the point
	 * @return whether this bounding box contains a point of given coordinates
	 */
	public default boolean contains(Vector point) {
		return contains(point.getX(), point.getY(), point.getZ());
	}
	
	// SETTERS
	
	AxisAlignedBB move(float x, float y, float z);

	public default AxisAlignedBB move(Vector v) {
		return move(v.getX(), v.getY(), v.getZ());
	}

	public AxisAlignedBB scale(float x, float y, float z);

	public default AxisAlignedBB scale(Vector v) {
		return scale(v.getX(), v.getY(), v.getZ());
	}

	public AxisAlignedBB grow(float x, float y, float z);

	public default AxisAlignedBB grow(Vector v) {
		return grow(v.getX(), v.getY(), v.getZ());
	}
	
	// MISC
	
	AxisAlignedBB clone();

}