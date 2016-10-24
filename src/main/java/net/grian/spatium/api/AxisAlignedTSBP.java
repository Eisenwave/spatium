package net.grian.spatium.api;

import net.grian.spatium.Spatium;
import net.grian.spatium.coll.Collideable;
import net.grian.spatium.enums.Axis;
import net.grian.spatium.enums.Direction;

public interface AxisAlignedTSBP extends Collideable {
	
	// GETTERS
	
	public abstract Direction getDirection();
	
	public default Axis getAxis() {
		return getDirection().axis();
	}
	
	public abstract float getMinX();
	
	public abstract float getMinY();
	
	public abstract float getMinZ();
	
	public abstract float getMaxX();
	
	public abstract float getMaxY();
	
	public abstract float getMaxZ();
	
	public default Vector getMin() {
		return Vector.fromXYZ(getMinX(), getMinY(), getMinZ());
	}
	
	public default Vector getMax() {
		return Vector.fromXYZ(getMaxX(), getMaxY(), getMaxZ());
	}
	
	public abstract float getMinWidth();
	
	public abstract float getMinHeight();
	
	public abstract float getMaxWidth();
	
	public abstract float getMaxHeight();
	
	/**
	 * Returns the depth of the plane in the direction it is in. For instance,
	 * a bounding plane located at {@code z = -5} in the direction
	 * {@link Direction#NEGATIVE_Z} would have a depth of {@code 5}
	 * @return the depth of the plane
	 */
	public abstract float getDepth();
	
	/**
	 * Returns the width of the plane.
	 * @return the width of the plane.
	 */
	public abstract float getWidth();
	
	/**
	 * Returns the surface area of one side of the plane.
	 * @return the area of the plane
	 */
	public default float getArea() {
		return getWidth() * getHeight();
	}
	
	/**
	 * Returns the height of the plane.
	 * @return the height of the plane.
	 */
	public abstract float getHeight();
	
	// CHECKERS
	
	public abstract boolean contains(float x, float y, float z);
	
	public default boolean contains(Vector point) {
		return contains(point.getX(), point.getY(), point.getZ());
	}
	
	public default boolean equals(AxisAlignedTSBP plane) {
		return
				getDirection() == plane.getDirection() &&
				Spatium.equals(getWidth(), plane.getWidth()) &&
				Spatium.equals(getHeight(), plane.getHeight()) &&
				Spatium.equals(getDepth(), plane.getDepth());
	}
	
	// SETTERS
	
	public abstract AxisAlignedTSBP move(float x, float y, float z);
	
	public default AxisAlignedTSBP move(Vector v) {
		return move(v.getX(), v.getY(), v.getZ());
	}
	
	public abstract AxisAlignedTSBP setDepth(float d);
	
	/**
	 * Inverts the direction and the depth of the plane.
	 * @return itself
	 * @see Direction#opposite()
	 */
	public abstract AxisAlignedTSBP mirror();
	
}
