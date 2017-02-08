package net.grian.spatium.geo2;

/**
 * An enclosed area.
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
     * Scales this object by a factor around its own center by given factor
     *
     * @param factor the scaling factor
     * @return itself
     *
     * @implNote The implementation has to guarantee that: <ul>
     *     <li><tt>scale(factor).getArea() "equals" getArea()*factor</tt></li>
     *     <li><tt>scale(factor).getCircumference() "equals" getCircumference()*factor</tt></li>
     *     <li>the center of the object stays unaffected by scaling</li>
     * </ul>
     */
    abstract Area scale(double factor);
    
}
