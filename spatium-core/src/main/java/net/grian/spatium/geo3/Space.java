package net.grian.spatium.geo3;

/**
 * An enclosed space, such as a bounding box or sphere.
 */
public interface Space extends Transformable3 {
    
    /**
     * Returns the volume of this space.
     *
     * @return the volume of this space
     */
    abstract double getVolume();
    
    /**
     * Returns the surface area of this space.
     *
     * @return the surface area of this space
     */
    abstract double getSurfaceArea();
    
    /**
     * Checks whether this space contains a point with given coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return whether this space contains the point
     */
    abstract boolean contains(double x, double y, double z);
    
    /**
     * Checks whether this space contains a point with given coordinates.
     *
     * @param point the point
     * @return whether this space contains the point
     */
    default boolean contains(Vector3 point) {
        return contains(point.getX(), point.getY(), point.getZ());
    }
    
    /**
     * <p>
     *     Returns the center of this space.
     * </p>
     * <p>
     *     The center is defined as the average of all points this space consists of. (center of mass)
     * </p>
     * <p>
     *     The center is allowed to lie outside the space. (for instance in the case of a donut)
     * </p>
     *
     * @return the center of this area
     */
    abstract Vector3 getCenter();
    
    @Override
    default void scaleCentric(double factor) {
        scaleAround(getCenter(), factor);
    }
    
}
