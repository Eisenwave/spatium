package net.grian.spatium.geo2;

/**
 * A generic enclosed area.
 */
public interface Area extends Transformable2 {
    
    /**
     * Returns the area of this object.
     *
     * @return the area of this object
     */
    abstract double getArea();
    
    /**
     * Returns the circumference of this object.
     *
     * @return the circumference of this object
     */
    abstract double getCircumference();
    
    abstract boolean contains(double x, double y);
    
    default boolean contains(Vector2 point) {
        return contains(point.getX(), point.getY());
    }
    
    /**
     * <p>
     *     Returns the center of this area.
     * </p>
     * <p>
     *     The center is defined as the average of all points this area consists of.
     * </p>
     *
     * @return the center of this area
     */
    abstract Vector2 getCenter();
    
    @Override
    default void scaleCentric(double factor) {
        scaleAround(getCenter(), factor);
    }
    
}
