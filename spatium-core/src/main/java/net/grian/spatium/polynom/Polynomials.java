package net.grian.spatium.polynom;

import net.grian.spatium.util.PrimMath;
import org.jetbrains.annotations.Contract;

public final class Polynomials {
    
    private Polynomials() {}
    
    public static Polynomial sum(Polynomial a, Polynomial b) {
        final int degree = PrimMath.max(a.getDegree(), b.getDegree());
        Polynomial sum = Polynomial.create(degree);
        
        for (int i = 0; i<=degree; i++)
            sum.set(i, a.get(i)+b.get(i));
        
        return sum;
    }
    
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
    @Contract(pure = true)
    public static double[] solve(double a, double b, double c) {
        if (a == 0) return new double[] {solve(b, c)};
        return solvePQ(b / a, c / a);
    }
    
    /**
     * Solves a polynomial of the form <tt>x^2 + px + q</tt>.
     *
     * @return the two solutions
     */
    @Contract(pure = true)
    public static double[] solvePQ(double p, double q) {
        double phalf = p / 2;
        double plusMinus = Math.sqrt(phalf*phalf - q);
        
        double x1 = -phalf - plusMinus;
        double x2 = -phalf + plusMinus;
        return new double[] {x1, x2};
    }
    
    /*view-source:https://www.easycalculation.com/algebra/cubic-equation.php
     * Solves a cubical polynomial of the form <code>ax^3 + bx^2 + cx + d</code>
     *
     * @return the solutions
     *
    public static double[] solve(double a, double b, double c, double d) {
        //p = -b/(3a),   q = p^3 + (bc-3ad)/(6a^2),   r = c/(3a)
        //x   =   {q + [q2 + (r-p2)3]1/2}1/3   +   {q - [q2 + (r-p2)3]1/2}1/3   +   p
        if (a != 1) {
            b /= a;
            c /= a;
            d /= a;
        }
        
        final double
            q = (3*c - b*b) / 9,
            q3 = q*q*q,
            r = (-27*d + b*(9*c - 2*b*b)) / 54,
            disc = q3 + r*r, // the discriminant delta = q^3 + r^2
            factor = 2 * Math.sqrt(Math.abs(q));
        
        double term1;
        if (disc < 0) {
            term1 = b/3;
        } else {
            double
                plusMinus = Math.sqrt(disc),
                s = r + plusMinus,
                t = r - plusMinus;
            term1 = CacheMath.SQRT_3 * ((s-t)/2);
        }
        
        double
            f1 = Math.cos(Math.abs(q3) / 3);
        return new double[] {
            -term1 + factor * f1,
            -term1 + factor * Math.cos(q3 + (2 * Math.PI) /3),
            -term1 + factor * Math.cos(q3 + (4 * Math.PI) /3)
        };
    }
    */
    
    /**
     * Solves a polynomial of the form <tt>ax + b</tt>
     *
     * @return the two solutions
     */
    @Contract(pure = true)
    public static double solve(double a, double b) {
        return -b / a;
    }
    
}
