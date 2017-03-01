package net.grian.spatium.enums;

import net.grian.spatium.anno.MinecraftSpecific;
import net.grian.spatium.geo3.Vector3;

import static net.grian.spatium.enums.Axis.*;
import static net.grian.spatium.enums.Direction.AxisDirection.*;

/**
 * <p>
 *     One of six directions, or movements into negative or positive directions of one of three axes in 3D space.
 * </p>
 * <p>
 *     This enum is universal (not specific to Minecraft geometry) and thus highly recommended for strictly
 *     for geometric operations, unlike its Minecraft counterpart ({@link Face}).
 * </p>
 * 
 * @see Face
 */
public enum Direction {
    NEGATIVE_X(Vector3.fromXYZ(-1,  0,  0), NEGATIVE, X, Face.WEST),
    POSITIVE_X(Vector3.fromXYZ( 1,  0,  0), POSITIVE, X, Face.EAST),
    NEGATIVE_Y(Vector3.fromXYZ( 0, -1,  0), NEGATIVE, Y, Face.DOWN),
    POSITIVE_Y(Vector3.fromXYZ( 0,  1,  0), POSITIVE, Y, Face.UP),
    NEGATIVE_Z(Vector3.fromXYZ( 0,  0, -1), NEGATIVE, Z, Face.NORTH),
    POSITIVE_Z(Vector3.fromXYZ( 0,  0,  1), POSITIVE, Z, Face.SOUTH);

    private final Axis axis;
    private final AxisDirection direction;
    private final Face face;
    private final Vector3 vector;

    Direction(Vector3 vector, AxisDirection direction, Axis axis, Face face) {
        this.vector = vector;
        this.axis = axis;
        this.direction = direction;
        this.face = face;
    }

    /**
     * Returns a vector pointing straight into this direction.
     *
     * @return a vector pointing straight into this direction
     */
    public Vector3 vector() {
        return vector.clone();
    }

    /**
     * Returns the opposite of this direction. This does not influence the
     * {@link Axis} of this direction, however the {@link AxisDirection} is
     * inverted.
     *
     * @return the opposite direction
     */
    public Direction opposite() {
        return valueOf(axis, direction.opposite());
    }

    /**
     * Returns the axis that this direction is on.
     *
     * @return the axis that this direction is on
     */
    public Axis axis() {
        return axis;
    }

    /**
     * Returns the axis that this direction is on.
     *
     * @return the axis that this direction is on
     */
    @MinecraftSpecific
    public Face face() {
        return face;
    }

    /**
     * Returns the {@link AxisDirection} that this direction is pointing
     * towards.
     * @return the direction on the axis of this direction
     */
    public AxisDirection direction() {
        return direction;
    }

    public static Direction valueOf(Axis axis, AxisDirection direction) {
        if (direction == POSITIVE) switch (axis) {
        case X: return POSITIVE_X;
        case Y: return POSITIVE_Y;
        case Z: return POSITIVE_Z;
        }
        else switch (axis) {
        case X: return NEGATIVE_X;
        case Y: return NEGATIVE_Y;
        case Z: return NEGATIVE_Z;
        }

        throw new IllegalArgumentException(axis+", "+direction);
    }

    public static enum AxisDirection {
        POSITIVE,
        NEGATIVE;

        public AxisDirection opposite() {
            if (this==POSITIVE) return NEGATIVE;
            else return POSITIVE;
        }

    }

}
