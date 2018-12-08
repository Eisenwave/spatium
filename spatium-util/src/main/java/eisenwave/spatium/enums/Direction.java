package eisenwave.spatium.enums;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static eisenwave.spatium.enums.Axis.*;
import static eisenwave.spatium.enums.Direction.AxisDirection.*;

/**
 * <p>
 * One of six directions, or movements into negative or positive directions of one of three axes in 3D space.
 * </p>
 * <p>
 * This enum is universal (not specific to Minecraft geometry) and thus highly recommended for strictly
 * for geometric operations, unlike its Minecraft counterpart ({@link Face}).
 * </p>
 *
 * @see Face
 */
public enum Direction {
    NEGATIVE_X(-1, 0, 0, NEGATIVE, X, "-X"),
    POSITIVE_X(1, 0, 0, POSITIVE, X, "+X"),
    NEGATIVE_Y(0, -1, 0, NEGATIVE, Y, "-Y"),
    POSITIVE_Y(0, 1, 0, POSITIVE, Y, "+Y"),
    NEGATIVE_Z(0, 0, -1, NEGATIVE, Z, "-Z"),
    POSITIVE_Z(0, 0, 1, POSITIVE, Z, "+Z");
    
    private Axis axis;
    private AxisDirection direction;
    private final int x, y, z;
    private final String name;
    
    Direction(int x, int y, int z, AxisDirection direction, Axis axis, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.axis = axis;
        this.direction = direction;
        this.name = name;
    }
    
    @Contract(pure = true)
    public int x() {
        return x;
    }
    
    @Contract(pure = true)
    public int y() {
        return y;
    }
    
    @Contract(pure = true)
    public int z() {
        return z;
    }
    
    /**
     * Returns the opposite of this direction. This does not influence the
     * {@link Axis} of this direction, however the {@link AxisDirection} is
     * inverted.
     *
     * @return the opposite direction
     */
    @NotNull
    @Contract(pure = true)
    public Direction opposite() {
        return direction.opposite().withAxis(axis);
    }
    
    /**
     * Returns the axis that this direction is on.
     *
     * @return the axis that this direction is on
     */
    @NotNull
    @Contract(pure = true)
    public Axis axis() {
        return axis;
    }
    
    /**
     * Returns the axis that this direction is on.
     *
     * @return the axis that this direction is on
     */
    @NotNull
    @Contract(pure = true)
    public Face face() {
        return Face.values()[ordinal()];
    }
    
    /**
     * Returns the {@link AxisDirection} that this direction is pointing
     * towards.
     *
     * @return the direction on the axis of this direction
     */
    @NotNull
    @Contract(pure = true)
    public AxisDirection direction() {
        return direction;
    }
    
    @NotNull
    @Contract(pure = true)
    public static Direction valueOf(Axis axis, AxisDirection direction) {
        return values()[axis.ordinal() * 2 + direction.ordinal()];
    }
    
    @NotNull
    public static Direction[] valuesOf(Axis axis) {
        return new Direction[] {
            valueOf(axis, AxisDirection.NEGATIVE),
            valueOf(axis, AxisDirection.POSITIVE)
        };
    }
    
    /**
     * The direction on an axis (positive or negative).
     */
    public static enum AxisDirection {
        NEGATIVE(),
        POSITIVE();
    
        private AxisDirection() {}
        
        @Contract(pure = true)
        public AxisDirection opposite() {
            return this == POSITIVE? NEGATIVE : POSITIVE;
        }
        
        @Contract(pure = true)
        public Direction withAxis(Axis axis) {
            return Direction.valueOf(axis, this);
        }
    
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    /*
    int sum(int a, int b, boolean even) {
        int sum = 0;
        for (int i = a; i <= b; i++)
            if ((i%2 == 0) == even)
                a += i;
        return sum;
    }
    
    int sum2(int a, int b, boolean even) {
        int sum = 0;
        int start = (a%2==0) == even? a : a+1;
        for (int i = start; i <= b; i += 2)
            if ((i%2 == 0) == even)
                a += i;
        return sum;
    }
    */
    
}
