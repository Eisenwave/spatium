package eisenwave.spatium.util;

import java.util.Random;

@SuppressWarnings("Duplicates")
public final class NumberUtil {
    
    private final static int
        HIGHEST_BIT_32 = 0b10000000_00000000_00000000_00000000;
    
    /**
     * Returns {@code (a ^ x) % m}.
     * <p>
     * This function implements the
     * <a href="http://www.thefullwiki.org/Square-and-multiply_algorithm">Square & Multiply</a> algorithm.
     *
     * @param a the base
     * @param x the exponent
     * @param m the modulus
     * @return (a ^ x) % m
     */
    public static int powMod(int a, int x, int m) {
        if (x == 0) return 1;
        else if (x == 1) return a % m;
        
        long result = 1;
        a %= m;
        
        boolean start = false;
        for (int i = 0; i < 32; i++) {
            if (((x << i) & HIGHEST_BIT_32) != 0) {
                if (!start) {
                    start = true;
                    result = a;
                }
                else {
                    result *= result;
                    result %= m;
                    result *= a;
                    result %= m;
                }
            }
            else if (start) {
                result *= result;
                result %= m;
            }
        }
        
        return (int) result;
    }
    
    /**
     * Returns whether the given number {@code p} passes the Fermat test.
     *
     * @param p the number
     * @param random the random to be used to generate a base
     * @return whether the number is a fermat prime
     */
    public static boolean isFermatPrime(int p, Random random) {
        int a = random.nextInt() % (p - 1) + 1;
        return powMod(a, p - 1, p) == 1;
    }
    
    /**
     * Returns whether the given number {@code p} passes the Fermat test.
     *
     * @param p the number
     * @param random the random to be used to generate a base
     * @param tests the amount of tests to be made
     * @return whether the number is a fermat prime
     */
    public static boolean isFermatPrime(int p, Random random, int tests) {
        for (int i = 0; i < tests; i++) {
            if (!isFermatPrime(p, random))
                return false;
        }
        return true;
    }
    
}
