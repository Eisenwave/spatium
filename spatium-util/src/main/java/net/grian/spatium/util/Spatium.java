package net.grian.spatium.util;

import org.jetbrains.annotations.Contract;

public final class Spatium {

    private Spatium() {}

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
     *     For example, {@code 1E-11} and {@code 2E-11} are equal.
     * </p>
     *
     * @see #equals(float, float)
     */
    public final static double
        EPSILON = 1E-10,
        HALF_EPSILON = EPSILON / 2;
    
    public final static double
        DEG_TO_RAD = Math.PI / 180,
        RAD_TO_DEG = 180 / Math.PI;

    /**
     * Checks whether the difference between two numbers {@code a,b} is smaller than the maximum precision
     * {@link #EPSILON}.
     * <p>
     *     This handles the following (special) cases: <ul>
     *         <li>a \u2248 b &lt;=&gt; abs(a-b) < {@link Spatium#EPSILON}</li>
     *         <li>+\u221E = +\u221E</li>
     *         <li>-\u221E != +\u221E</li>
     *         <li>-\u221E = -\u221E</li>
     *         <li>NaN = NaN</li>
     *         <li>NaN != +-\u221E</li>
     *     </ul>
     * </p>
     *
     * @param a the first number
     * @param b the second number
     * @return whether the numbers are roughly the same
     */
    @Contract(pure = true)
    public static boolean equals(float a, float b) {
        return
            Double.compare(a, b) == 0 || //handles cases of infinity, NaN
            Math.abs(a - b) < EPSILON;
    }

    /**
     * Checks whether the difference between three numbers {@code a,b,c} is smaller than the maximum precision
     * {@link #EPSILON}. This method is used for <i>fuzzy</i> {@link #equals(Object)} in classes such as vectors.
     *
     * @param a the first number
     * @param b the second number
     * @return whether the numbers are roughly the same
     */
    @Contract(pure = true)
    public static boolean equals(float a, float b, float c) {
        return equals(a, b) && equals(b, c);
    }

    /**
     * <p>
     *     Checks whether the difference between two numbers {@code a,b} is smaller than the maximum precision
     *     {@link #EPSILON}.
     * </p>
     * <p>
     *     This handles the following (special) cases: <ul>
     *         <li>a \u2248 b &lt;=&gt; abs(a-b) < {@link Spatium#EPSILON}</li>
     *         <li>+\u221E = +\u221E</li>
     *         <li>-\u221E != +\u221E</li>
     *         <li>-\u221E = -\u221E</li>
     *         <li>NaN = NaN</li>
     *         <li>NaN != +-\u221E</li>
     *     </ul>
     * </p>
     *
     * @param a the first number
     * @param b the second number
     * @return whether the numbers are roughly the same
     */
    @Contract(pure = true)
    public static boolean equals(double a, double b) {
        return
            Double.compare(a, b) == 0 || //handles cases of infinity, NaN
            Math.abs(a - b) < EPSILON;
    }

    /**
     * Checks whether the difference between three numbers {@code a,b,c} is smaller than the maximum precision
     * {@link #EPSILON}. This method is used for <i>fuzzy</i> {@link #equals(Object)} in classes such as vectors.
     *
     * @param a the first number
     * @param b the second number
     * @return whether the numbers are roughly the same
     */
    @Contract(pure = true)
    public static boolean equals(double a, double b, double c) {
        return equals(a, b) && equals(b, c);
    }

    /**
     * Checks whether the difference between several numbers is smaller than the maximum precision
     * {@link #EPSILON}. This method is used for <i>fuzzy</i> {@link #equals(Object)} in classes such as vectors.
     *
     * @param numbers the numbers
     * @return whether the numbers are roughly the same
     */
    @Contract(pure = true)
    public static boolean equals(double... numbers) {
        if (numbers.length == 0) return false;
        if (numbers.length == 1) return true;
        if (numbers.length == 2) return equals(numbers[0], numbers[1]);
        if (numbers.length == 3) return equals(numbers[0], numbers[1], numbers[2]);

        double first = numbers[0];
        for (int i = 1; i<numbers.length; i++)
            if (!Spatium.equals(first, numbers[i])) return false;

        return true;
    }
    
    /**
     * <p>
     *     Returns whether a numbers is approximately 0.
     * </p>
     * <p>
     *     This method abides the same rules as {@link #equals(double...)}, thus the difference between the number
     *     and 0 must be smaller than {@link #EPSILON}.
     * </p>
     *
     * @param number the number
     * @return whether the number is approximately 0
     */
    @Contract(pure = true)
    public static boolean isZero(double number) {
        return number > -HALF_EPSILON && number < HALF_EPSILON;
    }
    
    /**
     * <p>
     *     Returns whether a numbers is approximately 0.
     * </p>
     * <p>
     *     This method abides the same rules as {@link #equals(double...)}, thus the difference between the number
     *     and 0 must be smaller than {@link #EPSILON}.
     * </p>
     *
     * @param number the number
     * @return whether the number is approximately 0
     */
    @Contract(pure = true)
    public static boolean isZero(float number) {
        return number > -HALF_EPSILON && number < HALF_EPSILON;
    }

    //LENGTH

    public static float hypot(float x, float y) {
        return (float) Math.sqrt(x*x + y*y);
    }

    public static float hypot(float x, float y, float z) {
        return (float) Math.sqrt(x*x + y*y + z*z);
    }

    public static float hypot(float... coords) {
        if (coords.length == 0) return 0;
        if (coords.length == 1) return coords[0];
        if (coords.length == 2) return hypot(coords[0], coords[1]);
        if (coords.length == 3) return hypot(coords[0], coords[1], coords[2]);

        float lengthSquared = 0;
        for (float x : coords)
            lengthSquared += x * x;

        return (float) Math.sqrt(lengthSquared);
    }

    public static double hypot(double x, double y) {
        return Math.sqrt(x*x + y*y);
    }

    public static double hypot(double x, double y, double z) {
        return Math.sqrt(x*x + y*y + z*z);
    }

    public static double hypot(double... coords) {
        if (coords.length == 0) return 0;
        if (coords.length == 1) return coords[0];
        if (coords.length == 2) return hypot(coords[0], coords[1]);
        if (coords.length == 3) return hypot(coords[0], coords[1], coords[2]);

        double lengthSquared = 0;
        for (double x : coords)
            lengthSquared += x * x;

        return Math.sqrt(lengthSquared);
    }

    /**
     * Converts radians into degrees.
     *
     * @param radians the angle in radians
     * @return the angle in degrees
     */
    @Contract(pure = true)
    public static double degrees(double radians) {
        return RAD_TO_DEG * radians;
    }

    /**
     * Converts degrees into radians.
     *
     * @param degrees the angle in degrees
     * @return the angle in radians
     */
    @Contract(pure = true)
    public static double radians(double degrees) {
        return DEG_TO_RAD * degrees;
    }

}
