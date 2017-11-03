package net.grian.spatium.enums;

public enum CardinalRotation {
    /** No rotation */
    R0(0),

    /** A 90 degree rotation. */
    R90(90),

    /** A 180 degree rotation. */
    R180(180),

    /** A 280 degree rotation. */
    R270(270);

    private final int degrees;
    private final double radians;

    CardinalRotation(int value) {
        this.degrees = value;
        this.radians = Math.toRadians(degrees);
    }

    public int degrees() {
        return degrees;
    }

    public double radians() {
        return radians;
    }

    public CardinalRotation plus90() {
        return values()[(ordinal()+1) % 4];
    }

    public CardinalRotation plus180() {
        return values()[(ordinal()+2) % 4];
    }

    public CardinalRotation plus270() {
        return minus90();
    }

    public CardinalRotation minus90() {
        return values()[(ordinal()-1) % 4];
    }

    public CardinalRotation minus180() {
        return values()[(ordinal()-2) % 4];
    }

    public CardinalRotation minus270() {
        return plus90();
    }

}
