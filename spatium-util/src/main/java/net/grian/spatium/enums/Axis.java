package net.grian.spatium.enums;

import org.jetbrains.annotations.Contract;

/**
 * An Axis in 3D space.
 */
public enum Axis {
    /** The x-axis. */
    X(1, 0, 0),

    /** The y-axis. */
    Y(0, 1, 0),

    /** The z-axis. */
    Z(0, 0, 1);
    
    private final double x, y, z;

    Axis(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    @Contract(pure = true)
    public double x() {
        return x;
    }
    
    @Contract(pure = true)
    public double y() {
        return y;
    }
    
    @Contract(pure = true)
    public double z() {
        return z;
    }
    
}
