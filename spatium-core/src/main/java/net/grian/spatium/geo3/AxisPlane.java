package net.grian.spatium.geo3;

import eisenwave.spatium.util.Spatium;
import eisenwave.spatium.enums.Axis;
import net.grian.spatium.impl.AxisPlaneImpl;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * An axis aligned plane.
 */
public interface AxisPlane extends Plane, Serializable, Cloneable {

    /**
     * Creates a new plane, as seen in the general form:
     * <blockquote>
     *     <code>ax + by + cz = d</code>
     * </blockquote>
     * With <code>d</code> being the specified depth.
     *
     * @return a new axis plane
     */
    @NotNull
    static AxisPlane create(Axis axis, double depth) {
        return new AxisPlaneImpl(axis, depth);
    }

    //GETTERS

    /**
     * Returns the axis that this plane is positioned on.
     *
     * @return the axis of this plane
     */
    @NotNull
    abstract Axis getAxis();

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
    @Override
    abstract double getDepth();

    @Override
    default Vector3 getPoint() {
        return getNormal().multiply(getDepth());
    }

    /**
     * Returns the normal of the plane. This will be a normalized vector pointing straight into the direction of the
     * axis that the plane is positioned on.
     *
     * @return the normal vector of the plane
     */
    @Override
    default Vector3 getNormal() {
        return Vector3.fromAxis(getAxis());
    }

    //CHECKERS

    default boolean equals(AxisPlane plane) {
        return this.getAxis() == plane.getAxis() && Spatium.equals(this.getDepth(), plane.getDepth());
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
    default boolean contains(double x, double y, double z) {
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
    default boolean contains(Vector3 point) {
        return contains(point.getX(), point.getY(), point.getZ());
    }

    //SETTERS

    /**
     * Changes the axis on which this plane is positioned.
     *
     * @param axis the axis
     * @return itself
     */
    abstract AxisPlane setAxis(Axis axis);

    /**
     * Changes the depth <b>d</b> of the plane.
     *
     * @param depth the depth
     * @return itself
     * @see #getDepth()
     */
    abstract AxisPlane setDepth(double depth);

    @Override
    default void setNormal(double x, double y, double z) {
        throw new UnsupportedOperationException();
    }

    @Override
    default void setCenter(double x, double y, double z) {
        switch (getAxis()) {
            case X: setDepth(x); break;
            case Y: setDepth(y); break;
            case Z: setDepth(z); break;
            default: throw new IllegalStateException("no axis");
        }
    }

    //MISC

    /**
     * Converts this axis aligned plane into a regular one.
     *
     * @return a new plane
     */
    default Plane toPlane() {
        switch (getAxis()) {
            case X: return Plane.fromGeneral(1, 0, 0, getDepth());
            case Y: return Plane.fromGeneral(0, 1, 0, getDepth());
            case Z: return Plane.fromGeneral(0, 0, 1, getDepth());
            default: throw new IllegalStateException("no axis");
        }
    }

    abstract AxisPlane clone();

}
