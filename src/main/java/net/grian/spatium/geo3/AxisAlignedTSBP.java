package net.grian.spatium.geo3;

import net.grian.spatium.Spatium;
import net.grian.spatium.enums.Axis;
import net.grian.spatium.enums.Direction;

import java.io.Serializable;

public interface AxisAlignedTSBP extends Serializable, Cloneable {

    // GETTERS

    Direction getDirection();

    default Axis getAxis() {
        return getDirection().axis();
    }

    double getMinX();

    double getMinY();

    double getMinZ();

    double getMaxX();

    double getMaxY();

    double getMaxZ();

    default Vector3 getMin() {
        return Vector3.fromXYZ(getMinX(), getMinY(), getMinZ());
    }

    default Vector3 getMax() {
        return Vector3.fromXYZ(getMaxX(), getMaxY(), getMaxZ());
    }

    default Vector3 getCenter() {
        return null;
    }

    double getMinWidth();

    double getMinHeight();

    double getMaxWidth();

    double getMaxHeight();

    /**
     * Returns the depth of the plane in the direction it is in. For instance, a bounding plane located at
     * {@code z = -5} in the direction {@link Direction#NEGATIVE_Z} would have a depth of {@code 5}.
     *
     * @return the depth of the plane
     */
    public abstract double getDepth();

    /**
     * Returns the width of the plane.
     * @return the width of the plane.
     */
    public abstract double getWidth();

    /**
     * Returns the surface area of one side of the plane.
     * @return the area of the plane
     */
    public default double getArea() {
        return getWidth() * getHeight();
    }

    /**
     * Returns the height of the plane.
     * @return the height of the plane.
     */
    public abstract double getHeight();

    // CHECKERS

    public abstract boolean contains(double x, double y, double z);

    public default boolean contains(Vector3 point) {
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

    public abstract AxisAlignedTSBP translate(double x, double y, double z);

    public default AxisAlignedTSBP translate(Vector3 v) {
        return translate(v.getX(), v.getY(), v.getZ());
    }

    public abstract AxisAlignedTSBP setDepth(double d);

    /**
     * Inverts the direction and the depth of the plane.
     * @return itself
     * @see Direction#opposite()
     */
    public abstract AxisAlignedTSBP mirror();

    // MISC

    public abstract AxisAlignedTSBP clone();

}
