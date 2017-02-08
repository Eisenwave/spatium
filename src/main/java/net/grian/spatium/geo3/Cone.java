package net.grian.spatium.geo3;

import net.grian.spatium.Spatium;

import java.io.Serializable;

public interface Cone extends Space, Cloneable, Serializable {
    
    /**
     * Returns the tip of this cone, commonly known as the Apex.
     *
     * @return the apex of this cone
     */
    abstract Vector3 getApex();
    
    abstract Vector3 getDirection();
    
    /**
     * <p>
     *     Returns the aperture of this cone.
     * </p>
     * <p>
     *     The aperture of a right circular cone is the maximum angle between two generatrix lines. If the generatrix
     *     makes an angle θ to the axis, the aperture is 2θ.
     * </p>
     *
     * @return the aperture of this cone in radians
     */
    abstract double getAperture();
    
    /**
     * Returns the radius of the base of this cone.
     *
     * @return the radius of the base of this cone
     */
    abstract double getBaseRadius();
    
    /**
     * Returns the area of the base of this cone.
     *
     * @return the area of the base of this cone
     */
    default double getBaseArea() {
        double r = getBaseRadius();
        return Math.PI * r * r;
    }
    
    /**
     * Returns the height of this cone.
     *
     * @return the height of this cone
     */
    abstract double getHeight();
    
    /**
     * <p>
     *     Returns the lateral height of this cone.
     * </p>
     * <p>
     *     This is the length of a line segment from the apex of the cone along its side to its base.
     * </p>
     *
     * @return the lateral height of this cone
     */
    default double getLateralHeight() {
        return Spatium.hypot(getHeight(), getBaseRadius());
    }
    
    default double getLateralArea() {
        return Math.PI * getBaseRadius() * getLateralHeight();
    }
    
    @Override
    default double getVolume() {
        return (1.0/3.0) * getBaseArea() * getHeight();
    }
    
    @Override
    default double getSurfaceArea() {
        return getBaseArea() + getLateralArea();
    }
    
    //CHECKERS
    
    @Override
    default boolean contains(double x, double y, double z) {
        double height = getHeight();
        Vector3
            apex = getApex(),
            apexToPoint = Vector3.fromXYZ(x, y, z).subtract(apex),
            dir = getDirection().normalize();
        
        //distance (must be between 0 and height) of the point (projected onto cone axis) to the apex
        double coneDist = apexToPoint.dot(dir) / apexToPoint.getLength();
        if (coneDist < 0 || coneDist > height) return false;
        
        double
            //radius between 0 and base radius
            coneR = (coneDist / height) * getBaseRadius(),
            //note that apexToPoint and dir are being mutated here which is irrelevant in this situation though
            orthoDist = apexToPoint.subtract(dir.multiply(coneDist)).getLength();
    
        return orthoDist < coneR;
    }
    
    //SETTERS
    
    abstract Cone setHeight(double height);
    
    abstract Cone setBaseRadius(double radius);
    
    abstract Cone setApex(double x, double y, double z);
    
    default Cone setApex(Vector3 apex) {
        return setApex(apex.getX(), apex.getY(), apex.getZ());
    }
    
    abstract Cone setDirection(double x, double y, double z);
    
    default Cone setDirection(Vector3 dir) {
        return setDirection(dir.getX(), dir.getY(), dir.getZ());
    }
    
}
