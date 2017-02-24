package net.grian.spatium.geo2;

/**
 * A generic enclosed area.
 */
public interface Area {
    
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
    
    /**
     * Scales this object around its own center by given factor.
     *
     * @param factor the scaling factor
     *
     * @implNote The implementation has to guarantee that: <ul>
     *     <li><tt>scale(factor).getArea() "equals" getArea()*factor</tt></li>
     *     <li><tt>scale(factor).getCircumference() "equals" getCircumference()*factor</tt></li>
     *     <li>the center of the object stays unaffected by scaling</li>
     * </ul>
     */
    abstract void scale(double factor);
    
    /**
     * Translates this object by a given amount. This is equivalent to translating every point of this object's area
     * by the same amount on both the x and y-axis.
     *
     * @param x the translation on the x-axis
     * @param y the translation on the y-axis
     *
     * @implNote The implementation has to guarantee that:<ul>
     *     <li>shape is being preserved</li>
     *     <li>the object area / size is being preserved</li>
     * </ul>
     */
    abstract void translate(double x, double y);
    
}
