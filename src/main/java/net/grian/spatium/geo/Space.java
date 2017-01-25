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

}
