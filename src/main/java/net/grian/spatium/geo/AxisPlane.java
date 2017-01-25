package net.grian.spatium.geo;

import net.grian.spatium.Spatium;
import net.grian.spatium.enums.Axis;
import net.grian.spatium.impl.AxisPlaneImpl;

/**
 * An axis aligned plane.
 */
public interface AxisPlane extends Plane {

    /**
     * Creates a new plane, as seen in the general form:
     * <blockquote>
     *     <code>ax + by + cz = d</code>
     * </blockquote>
     * With <code>d</code> being the specified depth.
     *
     * @return a new axis plane
     */
    public static AxisPlane create(Axis axis, double depth) {
        return new AxisPlaneImpl(axis, depth);
    }

    //GETTERS

    /**
     * Returns the axis that this plane is positioned on.
     *
     * @return the axis of this plane
     */
    public abstract Axis getAxis();

    /**
     * Returns the depth <b>d</b> of the plane, as seen in the general form:
     * <blockquote>
     *     <code>ax + by + cz = d</code>
     * </blockquote>
     * <p>
     *     For example, an axis aligned plane on the x-axis with <code><b>d</b></code> = 9 will contain the point
     *     <code>(9, 0, 0)</code> since it fulfills the condition:
     *     <br><code>x = 9</code>.
     * </p>
     *
     * @return the depth of the plane
     */
    public abstract double getDepth();

    /**
     * Returns a point on the plane.
     *
     * @return a point on the plane
     */
    public default Vector getPoint() {
        switch (getAxis()) {
            case X: return Vector.fromXYZ(getDepth(), 0, 0);
            case Y: return Vector.fromXYZ(0, getDepth(), 0);
            case Z: return Vector.fromXYZ(0, 0, getDepth());
            default: throw new IllegalStateException("no axis");
        }
    }

    /**
     * Returns the normal of the plane. This will be a normalized vector pointing straight into the direction of the
     * axis that the plane is positioned on.
     *
     * @return the normal vector of the plane
     */
    public default Vector getNormal() {
        switch (getAxis()) {
            case X: return Vector.fromXYZ(1, 0, 0);
            case Y: return Vector.fromXYZ(0, 1, 0);
            case Z: return Vector.fromXYZ(0, 0, 1);
            default: throw new IllegalStateException("no axis");
        }
    }

    //CHECKERS

    public default boolean equals(AxisPlane plane) {
        return this.getAxis() == plane.getAxis() && this.getDepth() == plane.getDepth();
    }

    /**
     * Returns whether this plane contains the point.
     *
     * @param x the x-coordinate of the point
     * @param y the y-coordinate of the point
     * @param z the z-coordinate of the point
     * @return whether this plane contains the point
     */
    @Override
    public default boolean contains(double x, double y, double z) {
        switch (getAxis()) {
            case X: return Spatium.equals(getDepth(), x);
            case Y: return Spatium.equals(getDepth(), y);
            case Z: return Spatium.equals(getDepth(), z);
            default: throw new IllegalStateException("no axis");
        }
    }

    /**
     * Returns whether this plane contains the point.
     *
     * @param point the point
     * @return whether this plane contains the point
     */
    @Override
    public default boolean contains(Vector point) {
        return contains(point.getX(), point.getY(), point.getZ());
    }

    //SETTERS

    /**
     * Changes the axis on which this plane is positioned.
     *
     * @param axis the axis
     * @return itself
     */
    public abstract AxisPlane setAxis(Axis axis);

    /**
     * Changes the depth <b>d</b> of the plane.
     *
     * @param depth the depth
     * @return itself
     * @see #getDepth()
     */
    public abstract AxisPlane setDepth(double depth);

    @Override
    public default Plane setNormal(double x, double y, double z) {
        throw new UnsupportedOperationException();
    }

    @Override
    public default Plane setCenter(double x, double y, double z) {
        switch (getAxis()) {
            case X: return setDepth(x);
            case Y: return setDepth(y);
            case Z: return setDepth(z);
            default: throw new IllegalStateException("no axis");
        }
    }

    //MISC

    /**
     * Converts this axis aligned plane into a regular one.
     *
     * @return a new plane
     */
    public default Plane toPlane() {
        switch (getAxis()) {
            case X: return Plane.fromGeneral(1, 0, 0, getDepth());
            case Y: return Plane.fromGeneral(0, 1, 0, getDepth());
            case Z: return Plane.fromGeneral(0, 0, 1, getDepth());
            default: throw new IllegalStateException("no axis");
        }
    }

    public abstract AxisPlane clone();

}
