package net.grian.spatium.util;

import net.grian.spatium.cache.CacheMath;

/**
 * <p>
 *     Utility class that provides faster or more convenient alternatives to {@link Math}.
 * </p>
 * <p>
 *     This is not to be confused with {@link CacheMath} which uses lookup tables, not faster algorithms to return
 *     results more efficiently.
 * </p>
 *
 */
public final class FastMath {
    
    private FastMath() {}

    public static float invSqrt(float x) {
        float halfX = 0.5f * x;
        int i = Float.floatToIntBits(x);
        i = 0x5f3759df - (i >> 1);
        x = Float.intBitsToFloat(i);
        x *= (1.5f - halfX * x * x);
        return x;
    }

    public static double invSqrt(double x) {
        double halfX = 0.5d * x;
        long i = Double.doubleToLongBits(x);
        i = 0x5fe6ec85e7de30daL - (i >> 1);
        x = Double.longBitsToDouble(i);
        x *= (1.5d - halfX * x * x);
        return x;
    }
    
    /**
     * Returns the logarithm (base 2) of a number.
     *
     * @param number the number
     * @return the log (base 2) of the number
     */
    public static double log2(double number) {
        return Math.log(number) / CacheMath.LN_2;
    }
    
    /**
     * Returns the logarithm to a given base of a number.
     *
     * @param base the base
     * @param number the number
     * @return the logarithm of the number
     */
    public static double log(double base, double number) {
        return Math.log(number) / Math.log(base);
    }

}
