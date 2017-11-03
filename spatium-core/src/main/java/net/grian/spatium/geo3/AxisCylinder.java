package net.grian.spatium.geo3;

import net.grian.spatium.util.Spatium;
import net.grian.spatium.enums.Axis;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public interface AxisCylinder extends Space, Cloneable, Serializable {
    
    //GETTERS
    
    /**
     * Returns the axis that this cylinder's base/bottom and top are aligned to.
     *
     * @return the cylinder axis
     */
    @NotNull
    abstract Axis getAxis();
    
    /**
     * Returns the x-coordinate of the center of the cylinder bottom.
     *
     * @return the cylinder bottom x
     */
    abstract double getBaseX();
    
    /**
     * Returns the y-coordinate of the center of the cylinder bottom.
     *
     * @return the cylinder bottom y
     */
    abstract double getBaseY();
    
    /**
     * Returns the z-coordinate of the center of the cylinder bottom.
     *
     * @return the cylinder bottom z
     */
    abstract double getBaseZ();
    
    default double getTopX() {
        return getBaseX() + getHeight();
    }
    
    default double getTopY() {
        return getBaseY() + getHeight();
    }
    
    default double getTopZ() {
        return getBaseZ() + getHeight();
    }
    
    /**
     * Returns the center of the cylinder bottom/base.
     *
     * @return the center of the cylinder bottom/base
     */
    default Vector3 getBase() {
        return Vector3.fromXYZ(getBaseX(), getBaseY(), getBaseZ());
    }
    
    /**
     * Returns the center of the cylinder top.
     *
     * @return the center of the cylinder top
     */
    default Vector3 getTop() {
        return Vector3.fromXYZ(getTopX(), getTopY(), getTopZ());
    }
    
    @Override
    default Vector3 getCenter() {
        return getBase().add(getTop()).divide(2);
    }
    
    /**
     * Returns the radius of this cylinder.
     *
     * @return the radius of this cylinder
     */
    abstract double getRadius();
    
    /**
     * Returns the height of this cylinder.
     *
     * @return the height of this cylinder
     */
    abstract double getHeight();
    
    @Override
    default double getVolume() {
        double r = getRadius();
        return Math.PI * r * r * getHeight();
    }
    
    @Override
    default double getSurfaceArea() {
        /*
        double
            r = getRadius(),
            disk = 2 * (Math.PI * r * r),
            side = 2 * Math.PI * r * getHeight();
        return disk + side;
        */
        double
            r = getRadius(),
            twoPiR = 2 * Math.PI * r;
        
        return twoPiR*r + twoPiR*getHeight();
    }
    
    /**
     * <p>
     *     Returns a slab that entirely contains this cylinder.
     * </p>
     * <p>
     *     This slab is a pair of parallel planes which's to planes are the planes in which the cylinder disks lie.
     * </p>
     *
     * @return the cylinder slab
     */
    default AxisSlab3 getSlab() {
        Axis axis = getAxis();
        
        switch (axis) {
            case X: return AxisSlab3.fromPoints(axis, getBaseX(), getTopX());
            case Y: return AxisSlab3.fromPoints(axis, getBaseX(), getTopX());
            case Z: return AxisSlab3.fromPoints(axis, getBaseX(), getTopX());
            default: throw new IllegalStateException("cylinder has no axis");
        }
    }
    
    //CHECKERS
    
    @Override
    default boolean contains(double x, double y, double z) {
        final double bx = getBaseX(), by = getBaseY(), bz = getBaseZ();
        switch (getAxis()) {
            case X: {
                double d = Spatium.hypot(y-by, z-bz);
                return d <= getRadius() && (z >= bx) && (z <= bx + getHeight());
            }
            case Y: {
                double d = Spatium.hypot(y-bz, z-bx);
                return d <= getRadius() && (z >= by) && (z <= by + getHeight());
            }
            case Z: {
                double d = Spatium.hypot(y-bx, z-by);
                return d <= getRadius() && (z >= bz) && (z <= bz + getHeight());
            }
            default: throw new IllegalStateException("cylinder has no axis");
        }
    }
    
}
