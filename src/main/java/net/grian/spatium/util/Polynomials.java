package net.grian.spatium.util;

import org.jetbrains.annotations.Contract;

public class Polynomials {
    
    /**
     * Returns the sum of two polynomials.
     *
     * @param a the first polynomial
     * @param b the second polynomial
     * @return the sum of the polynomials
     */
    @Contract(pure = true)
    public static double[] sum(double[] a, double[] b) {
        int l = Math.max(a.length, b.length);
        double[] sum = new double[l];
        
        for (int i = 0; i < l; i++) {
            if (i < a.length) sum[i] += a[i];
            if (i < b.length) sum[i] += b[i];
        }
        
        return sum;
    }
    
    /**
     * Solves a polynomial of the form <tt>ax^2 + bx + c</tt>.
     *
     * @return the two solutions
     */
    public static double[] solve(double a, double b, double c) {
        if (a == 0) return new double[] {solve(b, c)};
        return solvePQ(b / a, c / a);
    }
    
    /**
     * Solves a polynomial of the form <tt>x^2 + px + q</tt>.
     *
     * @return the two solutions
     */
    public static double[] solvePQ(double p, double q) {
        double phalf = p / 2;
        double plusMinus = Math.sqrt(phalf*phalf - q);
        
        double x1 = -phalf - plusMinus;
        double x2 = -phalf + plusMinus;
        return new double[] {x1, x2};
    }
    
    /**
     * Solves a polynomial of the form <tt>ax + b</tt>
     *
     * @return the two solutions
     */
    public static double solve(double a, double b) {
        return -b / a;
    }
    
}
