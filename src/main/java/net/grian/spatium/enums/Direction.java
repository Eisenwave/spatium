package net.grian.spatium.enums;

import net.grian.spatium.anno.MinecraftSpecific;
import net.grian.spatium.geo.Vector;

import static net.grian.spatium.enums.Axis.*;
import static net.grian.spatium.enums.Direction.AxisDirection.*;
import static net.grian.spatium.enums.Face.*;

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
    NEGATIVE_X(Vector.fromXYZ(-1,  0,  0), NEGATIVE, X, WEST),
    POSITIVE_X(Vector.fromXYZ( 1,  0,  0), POSITIVE, X, EAST),
    NEGATIVE_Y(Vector.fromXYZ( 0, -1,  0), NEGATIVE, Y, DOWN),
    POSITIVE_Y(Vector.fromXYZ( 0,  1,  0), POSITIVE, Y, UP),
    NEGATIVE_Z(Vector.fromXYZ( 0,  0, -1), NEGATIVE, Z, NORTH),
    POSITIVE_Z(Vector.fromXYZ( 0,  0,  1), POSITIVE, Z, SOUTH);

    private final Axis axis;
    private final AxisDirection direction;
    private final Face face;
    private final Vector vector;

    Direction(Vector vector, AxisDirection direction, Axis axis, Face face) {
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
    public Vector vector() {
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
