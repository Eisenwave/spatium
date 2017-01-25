package net.grian.spatium.enums;

import net.grian.spatium.anno.MinecraftSpecific;

@SuppressWarnings("Duplicates")
public enum CardinalDirection {
    NORTH (180, Face.NORTH),
    EAST  (-90, Face.EAST),
    SOUTH (  0, Face.SOUTH),
    WEST  ( 90, Face.WEST);

    private final int angle;
    private final Face face;

    CardinalDirection(int angle, Face face) {
        this.angle = angle;
        this.face = face;
    }

    /**
     * Returns the angle of the cardinal direction in Minecraft space.
     * <ul>
     *   <li> {@link #NORTH} {@code -> 180}
     *   <li> {@link #EAST}  {@code -> -90}
     *   <li> {@link #SOUTH} {@code -> 0}
     *   <li> {@link #WEST}  {@code -> 90}
     * </ul>
     * @return the angle in degrees
     */
    @MinecraftSpecific
    public int angle() {
        return angle;
    }

    /**
     * Returns the {@link Face} corresponding to this cardinal direction.
     *
     * @return the corresponding face
     */
    public Face face() {
        return face;
    }

    /**
     * Gets the right hand cardinal direction of the current one.
     * <br>Example: <b>NORTH -> EAST
     */
    public CardinalDirection right() {
        switch (this) {
        case EAST: return SOUTH;
        case NORTH: return EAST;
        case SOUTH: return WEST;
        case WEST: return NORTH;
        default: throw new AssertionError(this);
        }
    }

    /**
     * Gets the left hand cardinal direction of the current one.
     * <br>Example: <b>NORTH -> WEST
     */
    public CardinalDirection left() {
        switch (this) {
        case EAST: return NORTH;
        case NORTH: return WEST;
        case SOUTH: return EAST;
        case WEST: return SOUTH;
        default: throw new AssertionError(this);
        }
    }

    public CardinalDirection rotate(CardinalRotation rotation) {
        switch (rotation) {
            case R0: return this;
            case R90: return left();
            case R180: return opposite();
            case R270: return right();
            default: throw new AssertionError(rotation);
        }
    }

    /**
     * Gets the opposite cardinal direction of this direction.
     * <br>Example: <b>NORTH -> SOUTH
     */
    public CardinalDirection opposite() {
        switch (this) {
        case EAST: return WEST;
        case NORTH: return SOUTH;
        case SOUTH: return NORTH;
        case WEST: return EAST;
        default: throw new AssertionError(this);
        }
    }

    public static CardinalDirection fromInitials(String initial) {
        switch(initial.toUpperCase()) {
        case "N": return NORTH;
        case "E": return EAST;
        case "S": return SOUTH;
        case "W": return WEST;
        default: throw new IllegalArgumentException("Initials must be N, E, S, W");
        }
    }

    public static CardinalDirection fromInitials(char initial) {
        switch(Character.toUpperCase(initial)) {
        case 'N': return NORTH;
        case 'E': return EAST;
        case 'S': return SOUTH;
        case 'W': return WEST;
        default: throw new IllegalArgumentException("Initials must be N, E, S, W");
        }
    }

}
