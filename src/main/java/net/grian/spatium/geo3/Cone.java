package net.grian.spatium.geo3;

import net.grian.spatium.Spatium;
import net.grian.spatium.impl.ConeImpl;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public interface Cone extends Space, Cloneable, Serializable {
    
    @NotNull
    static Cone fromApexDirRadius(double x, double y, double z, double dx, double dy, double dz, double r) {
        return new ConeImpl(x, y, z, dx, dy, dz, r);
    }
    
    @NotNull
    static Cone fromApexDirRadius(Vector3 apex, Vector3 dir, double r) {
        return fromApexDirRadius(apex.getX(), apex.getY(), apex.getZ(), dir.getX(), dir.getY(), dir.getZ(), r);
    }
    
    @NotNull
    static Cone fromApexDirAperture(Vector3 apex, Vector3 dir, double aperture) {
        double r = dir.getLength() * Math.atan(aperture / 2);
        return fromApexDirRadius(apex.getX(), apex.getY(), apex.getZ(), dir.getX(), dir.getY(), dir.getZ(), r);
    }
    
    /**
     * Returns the tip of this cone, commonly known as the Apex.
     *
     * @return the apex of this cone
     */
    abstract Vector3 getApex();
    
    /**
     * <p>
     *     Returns a vector between the cone's apex and the cone's center of base.
     * </p>
     * <p>
     *     Note that the axis is NOT guaranteed to be normalized and its length is NOT equal to the cone height.
     * </p>
     *
     * @return the cone direction
     */
    abstract Vector3 getAxis();
    
    default Vector3 getCenter() {
        final Vector3
            apex = getApex(),
            axis = getAxis().setLength(getHeight()),
            baseCen = apex.clone().add(axis),
            offset = Vectors.ortho(axis).setLength(getBaseRadius());
        
        return Vectors.average(
            apex,
            baseCen.clone().add(offset),
            baseCen.add(offset.negate()));
    }
    
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
    
    default Vector3 getBaseCenter() {
        return getApex().add(getAxis().setLength(getHeight()));
    }
    
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
        return getBaseArea() * getHeight() / 3;
    }
    
    @Override
    default double getSurfaceArea() {
        return getBaseArea() + getLateralArea();
    }
    
    //CHECKERS
    
    @Override
    default boolean contains(double x, double y, double z) {
        double height = getHeight();
        Vector3 apex = getApex();
        
        Vector3 apexToPoint = Vector3.fromXYZ(x, y, z).subtract(apex);
        Vector3 axis = getAxis().normalize();
        
        //distance (must be between 0 and height) of the point (projected onto cone axis) to the apex
        double coneDist = apexToPoint.dot(axis);
        if (coneDist < 0 || coneDist > height) return false;
        
        double
            //note that apexToPoint and axis are being mutated here, although this is fine since they are not used after
            //radius between at coneDist (between 0 and base radius)
            maxRadius = (coneDist / height) * getBaseRadius(),
            localRadius = apexToPoint.subtract(axis.multiply(coneDist)).getLength();
        
        return localRadius <= maxRadius;
    }
    
    //SETTERS
    
    abstract void setHeight(double height);
    
    abstract void setBaseRadius(double radius);
    
    abstract void setApex(double x, double y, double z);
    
    default void setApex(Vector3 apex) {
        setApex(apex.getX(), apex.getY(), apex.getZ());
    }
    
    abstract void setDirection(double x, double y, double z);
    
    default void setDirection(Vector3 dir) {
        setDirection(dir.getX(), dir.getY(), dir.getZ());
    }
    
    // TRANSFORMATIONS
    
    @Override
    default void translate(double x, double y, double z) {
        setApex(getApex().add(x, y, z));
    }
    
    @Override
    default void scale(double factor) {
        setApex(getApex().multiply(factor));
        setHeight(getHeight() * factor);
    }
    
    // MISC
    
    abstract Cone clone();
    
}
