package net.grian.spatium.enums;

import net.grian.spatium.anno.MinecraftSpecific;

/**
 * One of six directions, or movements into negative or positive directions
 * of one of three axes in 3D space.
 * 
 * <br><br>This enum is independent from Minecraft space and thus highly
 * recommended for strictly mathematical purpouses. 
 * 
 * @see Face
 */
public enum Direction {
    POSITIVE_X(Axis.X, AxisDirection.POSITIVE, Face.EAST),
    POSITIVE_Y(Axis.Y, AxisDirection.POSITIVE, Face.UP),
    POSITIVE_Z(Axis.Z, AxisDirection.POSITIVE, Face.SOUTH),
    NEGATIVE_X(Axis.X, AxisDirection.NEGATIVE, Face.WEST),
    NEGATIVE_Y(Axis.Y, AxisDirection.NEGATIVE, Face.DOWN),
    NEGATIVE_Z(Axis.Z, AxisDirection.NEGATIVE, Face.NORTH);

    private final Axis axis;
    private final AxisDirection direction;
    private final Face face;

    Direction(Axis axis, AxisDirection direction, Face face) {
        this.axis = axis;
        this.direction = direction;
        this.face = face;
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
        if (direction == AxisDirection.POSITIVE) switch (axis) {
        case X: return Direction.POSITIVE_X;
        case Y: return Direction.POSITIVE_Y;
        case Z: return Direction.POSITIVE_Z;
        }
        else switch (axis) {
        case X: return Direction.NEGATIVE_X;
        case Y: return Direction.NEGATIVE_Y;
        case Z: return Direction.NEGATIVE_Z;
        }
        assert false: "axis = "+axis+", direction = "+direction;
        return null;
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
