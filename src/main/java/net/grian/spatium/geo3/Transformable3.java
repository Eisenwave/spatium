package net.grian.spatium.geo3;

public interface Transformable3 {
    
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
    abstract void translate(double x, double y, double z);
    
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
    default void translate(Vector3 t) {
        translate(t.getX(), t.getY(), t.getZ());
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
    default void scaleAround(double x, double y, double z, double factor) {
        translate(-x, -y, -z);
        scale(factor);
        translate(x, y, z);
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
    default void scaleAround(Vector3 center, double factor) {
        scaleAround(center.getX(), center.getY(), center.getZ(), factor);
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
