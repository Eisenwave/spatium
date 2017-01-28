package net.grian.spatium.enums;

/**
 * <p>
 *     A face or side of a cube.
 * </p>
 * <p>
 *     This enum is specific to Minecraft space and for strictly mathematical uses, its use is not recommended.
 *     Use {@link Direction} instead.
 * </p>
 */
public enum Face {
    UP    (Direction.POSITIVE_X),
    DOWN  (Direction.NEGATIVE_Y),
    NORTH (Direction.NEGATIVE_Z),
    SOUTH (Direction.POSITIVE_Z),
    EAST  (Direction.POSITIVE_X),
    WEST  (Direction.NEGATIVE_X);

    private final Direction direction;
    private Face opposite;
    static {//necessary since constructors would lead to forward references
        UP.opposite = DOWN;
        DOWN.opposite = UP;
        NORTH.opposite = SOUTH;
        SOUTH.opposite = NORTH;
        EAST.opposite = WEST;
        WEST.opposite = EAST;
    }

    Face(Direction direction) {
        this.direction = direction;
    }

    /**
     * Returns the direction corresponding to the face. In Minecraft space,
     * this is the direction from the center of the side of a cube with this
     * face.
     *
     * <br><br>For example, {@link Face#UP} corresponds to
     * {@link Direction#POSITIVE_Y}.
     *
     * @return the direction corresponding to the face
     */
    public Direction direction() {
        return direction;
    }

    /**
     * Returns the axis of this direction.
     *
     * @return the axis of this direction
     */
    public Axis axis() {
        return direction.axis();
    }

    /**
     * Returns the opposite face.
     *
     * @return the opposite face
     */
    public Face opposite() {
        return opposite;
    }

}
