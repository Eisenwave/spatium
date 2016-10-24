package net.grian.spatium.enums;

/**
 * A face or side of a cube.
 * 
 * <br><br>This enum is specific to Minecraft space and for strictly
 * mathematical uses, its use is not recommended. Use {@link Direction} instead.
 */
public enum Face {
	UP    (Direction.POSITIVE_Y),
	DOWN  (Direction.NEGATIVE_Y),
	NORTH (Direction.NEGATIVE_Z),
	SOUTH (Direction.POSITIVE_Z),
	EAST  (Direction.POSITIVE_X),
	WEST  (Direction.NEGATIVE_X);

	private final Direction direction;
	
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
	
	public Axis axis() {
		return direction.axis();
	}
	
	public Face opposite() {
		switch (this) {
		case UP: return DOWN;
		case DOWN: return UP;
		case NORTH: return SOUTH;
		case SOUTH: return NORTH;
		case EAST: return WEST;
		case WEST: return EAST;
		default: return null;
		}
	}
}
