package net.grian.spatium.enums;

import net.grian.spatium.geo3.Vector3;

/**
 * An Axis in 3D space.
 */
public enum Axis {
    /** The x-axis. */
    X(Vector3.fromXYZ(1, 0, 0)),

    /** The y-axis. */
    Y(Vector3.fromXYZ(0, 1, 0)),

    /** The z-axis. */
    Z(Vector3.fromXYZ(0, 0, 1));

    private final Vector3 vector;

    Axis(Vector3 vector) {
        this.vector = vector;
    }

    public Vector3 vector() {
        return vector.clone();
    }

}
