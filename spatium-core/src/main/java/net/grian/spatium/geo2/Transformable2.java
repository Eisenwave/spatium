package net.grian.spatium.geo2;

/**
 * <p>
 *     Interface to be implemented by all transformable 2D objects.
 * </p>
 * <p>
 *     This interface provides three kinds of transformations: <ul>
 *         <li>translation</li>
 *         <li>scaling around the origin</li>
 *         <lI>scaling around a given point</lI>
 *     </ul>
 * </p>
 * <p>
 *     All of the transformations this interface provide preserve shape and axis-alignment. Thus, it can be safely
 *     implemented by shapes such as circles or squares which would turn into ellipsoids or rectangles after
 *     deformation.
 * </p>
 */
public interface Transformable2 {
    
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
    
    /**
     * Translates this object by a given amount. This is equivalent to translating every point of this object's area
     * by the same amount on both the x and y-axis.
     *
     * @param t the translation
     *
     * @implNote The implementation has to guarantee that:<ul>
     *     <li>shape is being preserved</li>
     *     <li>the object area / size is being preserved</li>
     * </ul>
     */
    default void translate(Vector2 t) {
        translate(t.getX(), t.getY());
    }
    
    /**
     * Scales this object around the origin by given factor.
     *
     * @param factor the scaling factor
     *
     * @implNote The implementation has to guarantee that: <ul>
     *     <li><tt>scale(factor).getArea() "equals" getArea()*factor</tt></li>
     *     <li><tt>scale(factor).getCircumference() "equals" getCircumference()*factor</tt></li>
     * </ul>
     */
    abstract void scale(double factor);
    
    /**
     * Scales this object around a given point by given factor.
     *
     * @param factor the scaling factor
     *
     * @implNote The implementation has to guarantee that: <ul>
     *     <li><tt>scale(factor).getArea() = getArea()*factor</tt></li>
     *     <li><tt>scale(factor).getCircumference() = getCircumference()*factor</tt></li>
     * </ul>
     */
    default void scaleAround(double x, double y, double factor) {
        translate(-x, -y);
        scale(factor);
        translate(x, y);
    }
    
    /**
     * Scales this object around a given point by given factor.
     *
     * @param factor the scaling factor
     *
     * @implNote The implementation has to guarantee that: <ul>
     *     <li><tt>scale(factor).getArea() = getArea()*factor</tt></li>
     *     <li><tt>scale(factor).getCircumference() = getCircumference()*factor</tt></li>
     * </ul>
     */
    default void scaleAround(Vector2 center, double factor) {
        scaleAround(center.getX(), center.getY(), factor);
    }
    
    /**
     * Scales this object around its own center by given factor.
     *
     * @param factor the scaling factor
     *
     * @implNote The implementation has to guarantee that: <ul>
     *     <li><tt>scale(factor).getArea() = getArea()*factor</tt></li>
     *     <li><tt>scale(factor).getCircumference() = getCircumference()*factor</tt></li>
     *     <li>the center of the object stays unaffected by scaling</li>
     * </ul>
     */
    abstract void scaleCentric(double factor);
    
}
