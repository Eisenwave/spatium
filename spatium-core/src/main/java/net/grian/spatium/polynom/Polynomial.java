package net.grian.spatium.polynom;

import net.grian.spatium.util.Spatium;
import net.grian.spatium.impl.PolynomialImpl;

/**
 * A resizable wrapper class for polynomials.
 */
public interface Polynomial {
    
    // CONSTRUCTORS
    
    static Polynomial create(int degree) {
        return new PolynomialImpl(degree);
    }
    
    static Polynomial create(double... coefficients) {
        return new PolynomialImpl(coefficients);
    }
    
    // GETTERS
    
    /**
     * Returns the degree of the polynomial.
     *
     * @throws IndexOutOfBoundsException if the exponent is invalid
     */
    abstract int getDegree();
    
    /**
     * <p>
     *     Returns the coefficient for a variable with a given exponent.
     * </p>
     * <p>
     *     For instance, <code>get(3)</code> returns the coefficient of <code>x<sup>3</sup></code>.
     * </p>
     *
     * @param exp the exponent
     * @return the coefficient
     * @throws IndexOutOfBoundsException if the exponent is negative
     */
    abstract double get(int exp);
    
    //CHECKERS
    
    /**
     * Returns whether all coefficients of the polynomial are 0.
     *
     * @return whether all coefficients are 0
     */
    default boolean isZero() {
        int degree = getDegree();
        for (int exp = 0; exp<=degree; exp++)
            if (!Spatium.isZero(get(exp))) return false;
        
        return true;
    }
    
    //SETTERS
    
    /**
     * <p>
     *     Sets the coefficient of variable with a given exponent to a given value.
     * </p>
     * <p>
     *     For instance, <code>set(3, 1)</code> sets the coefficient of <code>x<sup>3</sup></code> to 1.
     * </p>
     *
     * @param exp the exponent
     * @param cof the new coefficient
     * @throws IndexOutOfBoundsException if the exponent is negative or > degree
     */
    abstract void set(int exp, double cof);
    
    //OPERATIONS
    
    /**
     * Multiplies each coefficient with a given factor.
     *
     * @param factor the factor
     */
    abstract void multiply(double factor);
    
}
