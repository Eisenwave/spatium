package net.grian.spatium;

import net.grian.spatium.geo.Vector;

public class Spatium {
	
	/**
	 * The maximum precision when using methods such as {@link #equals(Object)}
	 * between to objects in this library.
	 */
	public final static float EPSILON = 0.00001f;

	public final static float
			DEG_TO_RAD = (float) (Math.PI / 180),
			RAD_TO_DEG = (float) (180 / Math.PI);

	/**
	 * Checks whether the difference betweent two values {@code a, b} is
	 * smaller than the maximum precision {@link #EPSILON}. This method is used
	 * for fuzzy {@link #equals(Object)} in classes such as {@link Vector}.
	 * @param a the first value
	 * @param b the second value
	 * @return whether the values are roghly the same
	 */
	public static boolean equals(float a, float b) {
		return Math.abs(a - b) < EPSILON;
	}
	
	public static boolean equals(float a, float b, float c) {
		return equals(a, b) && equals(b, c);
	}
	
	public static float length(float x, float y) {
		return (float) Math.sqrt(x*x + y*y);
	}
	
	public static float length(float x, float y, float z) {
		return (float) Math.sqrt(x*x + y*y + z*z);
	}

	public static float degrees(float radians) {
		return (float) (RAD_TO_DEG * radians);
	}

	public static float radians(float degrees) {
		return DEG_TO_RAD * degrees;//
	}
	
}
