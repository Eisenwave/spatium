package net.grian.spatium.geo;

import net.grian.spatium.SpatiumObject;

/**
 * An enclosed space, such as a bounding box or sphere.
 */
public interface Space extends SpatiumObject {

    /**
     * Returns the volume of this space.
     *
     * @return the volume of this space
     */
    public abstract double getVolume();

    /**
     * Returns the surface area of this space.
     *
     * @return the surface area of this space
     */
    public abstract double getSurfaceArea();

    public abstract boolean contains(double x, double y, double z);

    public default boolean contains(Vector point) {
        return contains(point.getX(), point.getY(), point.getZ());
    }

}
