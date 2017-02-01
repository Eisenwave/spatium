package net.grian.spatium.geo3;

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
    double getVolume();
    
    /**
     * Returns the surface area of this space.
     *
     * @return the surface area of this space
     */
    double getSurfaceArea();
    
    boolean contains(double x, double y, double z);
    
    default boolean contains(Vector3 point) {
        return contains(point.getX(), point.getY(), point.getZ());
    }
    
}
