package net.grian.spatium.enums;

import net.grian.spatium.geo.Vector;

/**
 * An Axis in 3D space.
 */
public enum Axis {
    /** The x-axis. */
    X(Vector.fromXYZ(1, 0, 0)),

    /** The y-axis. */
    Y(Vector.fromXYZ(0, 1, 0)),

    /** The z-axis. */
    Z(Vector.fromXYZ(0, 0, 1));

    private final Vector vector;

    Axis(Vector vector) {
        this.vector = vector;
    }

    public Vector vector() {
        return vector.clone();
    }

}
