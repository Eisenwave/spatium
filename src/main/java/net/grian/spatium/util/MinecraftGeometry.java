package net.grian.spatium.util;

import net.grian.spatium.api.Vector;
import net.grian.spatium.enums.CardinalDirection;
import net.grian.spatium.enums.Direction;
import net.grian.spatium.enums.Face;

public final class MinecraftGeometry {
	
	private MinecraftGeometry() {}
	
	public static Face faceOfDirection(Direction dir) {
		switch (dir) {
		case POSITIVE_X: return Face.EAST;
		case POSITIVE_Y: return Face.UP;
		case POSITIVE_Z: return Face.SOUTH;
		case NEGATIVE_X: return Face.WEST;
		case NEGATIVE_Y: return Face.DOWN;
		case NEGATIVE_Z: return Face.NORTH;
		default: assert false : dir; return null;
		}
	}
	
	/**
	 * @deprecated Use {@link Face#direction()}
	 * @param face the face
	 * @return the direction of the face
	 */
	@Deprecated
	public static Direction directionOfFace(Face face) {
		switch (face) {
		case EAST:  return Direction.POSITIVE_X;
		case UP:    return Direction.POSITIVE_Y;
		case SOUTH: return Direction.POSITIVE_Z;
		case WEST:  return Direction.NEGATIVE_X;
		case DOWN:  return Direction.NEGATIVE_Y;
		case NORTH: return Direction.NEGATIVE_Z;
		default: assert false : face; return null;
		}
	}
	
	public static float getPositiveAngle(CardinalDirection dir) {
		switch(dir) {
		case EAST: return 270;
		case NORTH: return 180;
		case WEST: return 90;
		case SOUTH: return 0;
		default: assert false : dir; return 0;
		}
	}
	
	public static float getMinecraftAngle(CardinalDirection dir) {
		switch(dir) {
		case EAST: return -90;
		case NORTH: return 180;
		case WEST: return 90;
		case SOUTH: return 0;
		default: assert false : dir; return 0;
		}
	}
	
	/**
	 * 'Adjusts' the yaw in degrees so it can be used for further calculations.
	 * 
	 * @param yaw in degrees
	 * @return The adjusted yaw in radians.
	 */
	public static float adjustYawDeg(float yaw) {
		return (float) Math.toRadians(yaw);
	}
	
	/**
	 * 'Adjusts' the pitch in degrees so it can be used for further calculations.
	 * 
	 * @param pitch in degrees
	 * @return The adjusted pitch in radians.
	 */
	public static float adjustPitchDeg(float pitch) {
		return (float) Math.toRadians(-pitch);
	}
	
	/**
	 * 'Adjusts' the yaw in radians so it can be used for further calculations.
	 * 
	 * @param yaw in radians
	 * @return The adjusted yaw in radians.
	 */
	public static float adjustYawRad(float yaw) {
		return yaw;
	}
	
	/**
	 * 'Adjusts' the pitch in radians so it can be used for further calculations.
	 * 
	 * @param pitch in radians
	 * @return The adjusted pitch in radians.
	 */
	public static float adjustPitchRad(float pitch) {
		return -pitch;
	}
	
	/**
	 * Calculates the directional vector of the specified yaw and pitch.
	 * 
	 * @param yaw in degrees
	 * @param pitch in degrees
	 * 
	 * @return A new (not normalized) vector.
	 */
	public static Vector vectorFromYawPitchDeg(float yaw, float pitch) {
		return vectorFromYawPitchRad(
				(float) Math.toRadians(yaw),
				(float) Math.toRadians(pitch));
	}
	
	public static Vector vectorFromYawPitchDeg(float yaw, float pitch, float length) {
		return vectorFromYawPitchDeg(yaw, pitch).setLength(length);
	}
	
	/**
	 * Calculates the directional vector of the specified yaw and pitch.
	 * 
	 * @param yaw in radians
	 * @param pitch in radians
	 * 
	 * @return A new (not normalized) vector.
	 */
	public static Vector vectorFromYawPitchRad(float yaw, float pitch) {
		float
		adjYawRadians = adjustYawRad(yaw),
		adjPitchRadians = adjustPitchRad(pitch),
		x = (float) Math.sin(-adjYawRadians),
		y = (float) Math.tan(adjPitchRadians),
		z = (float) Math.cos(adjYawRadians);

		return Vector.fromXYZ(x, y, z);
	}
	
	public static Vector vectorFromYawPitchRad(float yaw, float pitch, float length) {
		return vectorFromYawPitchRad(yaw, pitch).setLength(length);
	}

}
