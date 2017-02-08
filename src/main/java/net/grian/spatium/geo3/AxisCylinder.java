package net.grian.spatium.geo3;

import net.grian.spatium.Spatium;
import net.grian.spatium.enums.Axis;

import java.io.Serializable;

public interface AxisCylinder extends Space, Cloneable, Serializable {
    
    abstract Axis getAxis();
    
    abstract double getBaseX();
    
    abstract double getBaseY();
    
    abstract double getBaseZ();
    
    default Vector3 getBase() {
        return Vector3.fromXYZ(getBaseX(), getBaseY(), getBaseZ());
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
