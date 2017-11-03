package net.grian.spatium.util;

import net.grian.spatium.cache.CacheMath;
import org.jetbrains.annotations.Contract;

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
    
    /**
     * <p>
     *     Returns the inverted square root of x. (1 / \u221Ax)
     * </p>
     * <p>
     *     This method is not guaranteed to be faster than inverting {@link Math#sqrt(double)}.
     * </p>
     * <p>
     *     <b>Any performance boost is platform dependent! In fact, this method may perform worse than Java's sqrt.</b>
     * </p>
     *
     * @param x the number
     * @return the inverted sqrt of x
     */
    @Contract(pure = true)
    public static float invSqrt(float x) {
        float halfX = 0.5f * x;
        int i = Float.floatToIntBits(x);
        i = 0x5f3759df - (i >> 1);
        x = Float.intBitsToFloat(i);
        x *= (1.5f - halfX * x * x);
        return x;
    }
    
    /**
     * <p>
     *     Returns the inverted square root of x. (1 / \u221Ax)
     * </p>
     * <p>
     *     This method is not guaranteed to be faster than inverting {@link Math#sqrt(double)}.
     * </p>
     * <p>
     *     <b>Any performance boost is platform dependent! In fact, this method may perform worse than Java's sqrt.</b>
     * </p>
     *
     * @param x the number
     * @return the inverted sqrt of x
     */
    @Contract(pure = true)
    public static double invSqrt(double x) {
        double halfX = 0.5d * x;
        long i = Double.doubleToLongBits(x);
        i = 0x5fe6ec85e7de30daL - (i >> 1);
        x = Double.longBitsToDouble(i);
        x *= (1.5d - halfX * x * x);
        return x;
    }
    
    /**
     * <p>
     *     Returns the natural logarithm (<i>lat. logarithmus naturalis</i>) of a number <b>n</b>. (<code>ln(n)</code>)
     * </p>
     * <p>
     *     The <a href="https://en.wikipedia.org/wiki/Natural_logarithm">natural logarithm</a> is the logarithm to the
     *     base <i>e</i> (<a href="https://en.wikipedia.org/wiki/E_(mathematical_constant)">Euler's Number</a>).
     * </p>
     *
     * @param number the number
     * @return the natural logarithm
     */
    @Contract(pure = true)
    public static double ln(double number) {
        return Math.log(number);
    }
    
    /**
     * <p>
     *     Returns the binary logarithm of a number <b>n</b>.
     * </p>
     * <p>
     *     The <a href="https://en.wikipedia.org/wiki/Binary_logarithm">binary logarithm</a> is the logarithm to the
     *     base 2. (<code>log<sub>2</sub>(n)</code>)
     * </p>
     *
     * @param number the number
     * @return the binary logarithm
     */
    @Contract(pure = true)
    public static double log2(double number) {
        return Math.log(number) / CacheMath.LN_2;
    }
    
    /**
     * <p>
     *     Returns the binary logarithm of a number <b>n</b>.
     * </p>
     * <p>
     *     The <a href="https://en.wikipedia.org/wiki/Binary_logarithm">binary logarithm</a> is the logarithm to the
     *     base 2. (<code>log<sub>2</sub>(n)</code>)
     * </p>
     *
     * @param number the number
     * @return the binary logarithm
     */
    @Contract(pure = true)
    public static int log2(int number) {
        return (int) log2((double) number);
    }
    
    /**
     * Returns the smallest positive power of two which is greater or equal than the given number.
     * <blockquote>
     *     <code>greaterPow2(25) = 32</code>
     *     <code>greaterPow2(8) = 8</code>
     *     <code>greaterPow2(-2) = 1</code>
     * </blockquote>
     *
     * @param num the number
     * @return a greater power of two
     */
    @Contract(pure = true)
    public static int greaterPow2(int num) {
        if (num < 1) return 1;
        if (num > 0x40000000) return 0x40000000; // overflow protection
        
        int pow = 1;
        while (pow < num) pow *= 2;
        return pow;
    }
    
    /**
     * Returns the smallest positive power of two which is greater or equal than the given number.
     * <blockquote>
     *     <code>greaterPow2(25) = 32</code>
     *     <code>greaterPow2(8) = 8</code>
     *     <code>greaterPow2(-2) = 1</code>
     * </blockquote>
     *
     * @param num the number
     * @return a greater power of two
     */
    @Contract(pure = true)
    public static long greaterPow2(long num) {
        if (num < 1) return 1;
        if (num > (0x40000000_00000000L)) return 0x40000000_00000000L; // overflow protection
    
        long pow = 1;
        while (pow < num) pow *= 2;
        return pow;
    }
    
    /**
     * <p>
     *     Returns the decimal logarithm of a number <b>n</b>.
     * </p>
     * <p>
     *     The <a href="https://en.wikipedia.org/wiki/Common_logarithm">decimal logarithm</a> is the logarithm to the
     *     base 10. (<code>log<sub>10</sub>(n)</code>)
     * </p>
     *
     * @param number the number
     * @return the decimal logarithm
     */
    @Contract(pure = true)
    public static double log10(double number) {
        return Math.log10(number);
    }
    
    /**
     * Returns the logarithm to a given base <b>b</b> of a number <b>n</b>. (<code>log<sub>b</sub>(n)</code>)
     *
     * @param base the base
     * @param number the number
     * @return the logarithm of the number
     */
    @Contract(pure = true)
    public static double log(double base, double number) {
        return Math.log(number) / Math.log(base);
    }
    
    /**
     * Returns the n-th power of -1.
     *
     * @param exp the exponent
     * @return the n-th power of -1
     */
    @Contract(pure = true)
    public static int minusOnePow(int exp) {
        return exp%2 == 0 ? 1 : -1;
    }
    
    @Contract(pure = true)
    public static boolean isPower2(int num) {
        if (num < 0) return false;
        if (num == 0) return true;
        
        int mask = 1;
        for (int i = 0; i < Integer.SIZE; i++, mask <<= 1)
            if ((num & mask) == num)
                return true;
        return false;
    }
    
    /**
     * Returns the binary coefficient of n and k or <i>n choose k</i>.
     *
     * @param n n
     * @param k k
     * @return n choose k
     */
    @Contract(pure = true)
    public static int choose(int n, int k) {
        if (n < 0) throw new IllegalArgumentException("n must be positive");
        if (k < 0) throw new IllegalArgumentException("k must be positive");
        if (n == 0 || n == k) return 1;
        return internalChose(n, k);
    }
    
    @Contract(pure = true)
    private static int internalChose(int n, int k) {
        int res = 1;
        
        // Since C(n, k) = C(n, n-k)
        if (k > n - k)
            k = n - k;
        
        for (int i = 0; i < k; i++) {
            res *= (n - i);
            res /= (i + 1);
        }
        
        return res;
    }

}
