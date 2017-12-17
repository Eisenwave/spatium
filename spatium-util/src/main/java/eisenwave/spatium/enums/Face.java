package eisenwave.spatium.enums;

import org.jetbrains.annotations.Nullable;

/**
 * <p>
 * A face or side of a cube.
 * </p>
 * <p>
 * This enum is specific to Minecraft space and for strictly mathematical uses, its use is not recommended.
 * Use {@link Direction} instead.
 * </p>
 */
public enum Face {
    WEST(Direction.NEGATIVE_X),
    EAST(Direction.POSITIVE_X),
    DOWN(Direction.NEGATIVE_Y),
    UP(Direction.POSITIVE_Y),
    NORTH(Direction.NEGATIVE_Z),
    SOUTH(Direction.POSITIVE_Z);
    
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
    
    @Nullable
    public static Face fromString(String str) {
        switch (str.toLowerCase()) {
            case "negative_z":
            case "nz":
            case "north":
            case "n": return Face.NORTH;
            
            case "positive_x":
            case "px":
            case "x":
            case "east":
            case "e": return Face.EAST;
            
            case "positive_z":
            case "pz":
            case "z":
            case "south":
            case "s": return Face.SOUTH;
            
            case "negative_x":
            case "nx":
            case "west":
            case "w": return Face.WEST;
            
            case "positive_y":
            case "py":
            case "y":
            case "top":
            case "t":
            case "up":
            case "u": return Face.UP;
            
            case "negative_y":
            case "ny":
            case "bottom":
            case "b":
            case "down":
            case "d": return Face.DOWN;
            
            default: return null;
        }
    }
    
    Face(Direction direction) {
        this.direction = direction;
    }
    
    /**
     * Returns the direction corresponding to the face. In Minecraft space,
     * this is the direction from the center of the side of a cube with this
     * face.
     * <p>
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
