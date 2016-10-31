package net.grian.spatium;

import net.grian.spatium.geo.Vector;

public class Spatium {
	
	/**
     * <p>
     *     The maximum relevant precision when using methods such as {@link #equals(Object)} between two objects in this
     *     library.
     * </p>
     * <p>
     *     Any numeric difference between coordinates, angles etc. that is smaller than {@code #EPSILON} will be
     *     ignored and seen as the result of floating point imprecision of mathematical operations.
     * </p>
     * <p>
     *     For example, {@code 1E-10} and {@code 2E-10} are equal.
     * </p>
     *
     * @see #equals(float, float)
	 */
	public final static float EPSILON = 1E-5f;

    private final static float
            DEG_TO_RAD = (float) (Math.PI / 180),
			RAD_TO_DEG = (float) (180 / Math.PI);

	/**
	 * Checks whether the difference between two values {@code a, b} is smaller than the maximum precision
     * {@link #EPSILON}. This method is used for <i>fuzzy</i> {@link #equals(Object)} in classes such as {@link Vector}.
	 *
	 * @param a the first value
	 * @param b the second value
	 * @return whether the values are roughly the same
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

    /**
     * Converts radians into degrees.
     *
     * @param radians the angle in radians
     * @return the angle in degrees
     */
	public static float degrees(float radians) {
		return RAD_TO_DEG * radians;
	}

    /**
     * Converts degrees into radians.
     *
     * @param degrees the angle in degrees
     * @return the angle in radians
     */
	public static float radians(float degrees) {
		return DEG_TO_RAD * degrees;
	}
	
}
