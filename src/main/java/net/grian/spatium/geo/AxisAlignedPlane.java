package net.grian.spatium.geo;

import net.grian.spatium.Spatium;
import net.grian.spatium.enums.Axis;
import net.grian.spatium.impl.PlaneImpl;

/**
 * An axis aligned plane.
 */
public interface AxisAlignedPlane {

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
    public abstract float getDepth();

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

    public default boolean equals(AxisAlignedPlane plane) {
        return this.getAxis() == plane.getAxis() && this.getDepth() == plane.getDepth();
    }

    /**
     * Returns whether this plane contains the point.
     *
     * @param point the point
     * @return whether this plane contains the point
     */
    public default boolean contains(Vector point) {
        switch (getAxis()) {
            case X: return Spatium.equals(getDepth(), point.getX());
            case Y: return Spatium.equals(getDepth(), point.getY());
            case Z: return Spatium.equals(getDepth(), point.getZ());
            default: throw new IllegalStateException("no axis");
        }
    }

    //SETTERS

    /**
     * Changes the axis on which this plane is positioned.
     *
     * @param axis the axis
     * @return itself
     */
    public abstract AxisAlignedPlane setAxis(Axis axis);

    /**
     * Changes the depth <b>d</b> of the plane.
     *
     * @param depth the depth
     * @return itself
     * @see #getDepth()
     */
    public abstract AxisAlignedPlane setDepth(float depth);

    //MISC

    /**
     * Converts this axis aligned plane into a regular one.
     *
     * @return a new plane
     */
    public default Plane toPlane() {
        switch (getAxis()) {
            case X: return new PlaneImpl(1, 0, 0, getDepth());
            case Y: return new PlaneImpl(0, 1, 0, getDepth());
            case Z: return new PlaneImpl(0, 0, 1, getDepth());
            default: throw new IllegalStateException("no axis");
        }
    }

    public abstract AxisAlignedPlane clone();

}
