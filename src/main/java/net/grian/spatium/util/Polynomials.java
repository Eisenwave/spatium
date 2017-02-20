package net.grian.spatium.util;

public class Polynomials {
    
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
