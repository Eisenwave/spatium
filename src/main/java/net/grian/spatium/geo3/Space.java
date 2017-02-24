package net.grian.spatium.geo3;

/**
 * An enclosed space, such as a bounding box or sphere.
 */
public interface Space {
    
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
     * Scales this space by a factor around its own center by given factor
     *
     * @param factor the scaling factor
     *
     * @implNote The implementation has to guarantee that: <ul>
     *     <li><tt>scale(factor).getVolume() "equals" getVolume()*factor</tt></li>
     *     <li><tt>scale(factor).getSurfaceArea() "equals" getSurfaceArea()*factor</tt></li>
     *     <li>the center of the space stays unaffected by scaling</li>
     * </ul>
     */
    abstract void scale(double factor);
    
    /**
     * Translates this object by a given amount. This is equivalent to translating every point of this object's volume
     * by the same amount on all axes.
     *
     * @param x the translation on the x-axis
     * @param y the translation on the y-axis
     * @param z the translation on the z-axis
     *
     * @implNote The implementation has to guarantee that:<ul>
     *     <li>shape is being preserved</li>
     *     <li>the object volume / size is being preserved</li>
     * </ul>
     */
    abstract void translate(double x, double y, double z);
    
    /**
     * Translates this object by a given amount. This is equivalent to translating every point of this object's volume
     * by the same amount on all axes.
     *
     * @param v the translation on all axes
     *
     * @implNote The implementation has to guarantee that:<ul>
     *     <li>shape is being preserved</li>
     *     <li>the object volume / size is being preserved</li>
     * </ul>
     */
    default void translate(Vector3 v) {
        translate(v.getX(), v.getY(), v.getZ());
    }
    
}
