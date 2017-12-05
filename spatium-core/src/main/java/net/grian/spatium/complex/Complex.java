package net.grian.spatium.complex;

import eisenwave.spatium.util.Spatium;
import net.grian.spatium.impl.ComplexImplCartesian;
import net.grian.spatium.impl.ComplexImplEuler;

import java.io.Serializable;

/**
 * A complex number which can either be provided in the form: {@code z = a + bi}
 * or the form: {@code z = r * e^(phi*i*pi)} depending on its implementation.
 */
public interface Complex extends Serializable, Cloneable {

    /**
     * Returns a new cartesian implementation of a complex number in the form:
     * <blockquote>
     *   {@code z = a + bi}
     * </blockquote>
     *
     * @param a the real part
     * @param b the imaginary part
     * @return a new complex number
     */
    static Complex fromCartesian(double a, double b) {
        return new ComplexImplCartesian(a, b);
    }

    /**
     * Returns a new euler implementation of a complex number in the form:
     * <blockquote>
     *   {@code z = r * exp(phi * pi * i)}
     * </blockquote>
     *
     * @param r the radius
     * @param phi the angle
     * @return a new complex number
     */
    static Complex fromEuler(double r, double phi) {
        return new ComplexImplEuler(r, phi);
    }

    // GETTERS

    /**
     * Returns the real part of this complex number.
     *
     * @return the real part
     */
    abstract double getReal();

    /**
     * Returns the imaginary part of this complex number.
     *
     * @return the imaginary part
     */
    abstract double getImaginary();

    /**
     * Returns the angle {@code phi} of this complex number.
     *
     * @return the angle
     */
    abstract double getAngle();

    /**
     * Returns the radius of this complex number.
     *
     * @return the radius
     */
    abstract double getModulo();

    /**
     * Returns the squared radius of this complex number.
     *
     * @return the squared radius
     */
    abstract double getModuloSquared();

    // CHECKERS

    /**
     * Returns whether this complex number is equal to another complex number.
     */
    default boolean equals(Complex z) {
        return
            Spatium.equals(this.getModuloSquared(), z.getModuloSquared()) &&
            Spatium.equals(this.getAngle(), z.getAngle());
    }
    
    default boolean isReal() {
        return Spatium.isZero(getImaginary());
    }
    
    default boolean isFinite() {
        return Double.isFinite(getReal()) && Double.isFinite(getImaginary());
    }
    
    default boolean isZero() {
        return Spatium.isZero(getReal()) && Spatium.isZero(getImaginary());
    }

    // SETTERS

    /**
     * Sets the real part of the complex number to a new value.
     *
     * @param re the value
     * @return itself
     */
    abstract Complex setReal(double re);

    /**
     * Sets the imaginary part of the complex number to a new value.
     *
     * @param im the value
     * @return itself
     */
    abstract Complex setImaginary(double im);

    /**
     * Sets the radius of the complex number to a new value.
     *
     * @param r the radius
     * @return itself
     */
    abstract Complex setModulo(double r);

    /**
     * Sets the angle {@code phi} of the complex number to a new value.
     *
     * @param phi the angle
     * @return itself
     */
    abstract Complex setAngle(double phi);
    
    // OPERATIONS

    /**
     * Inverts the imaginary part of the complex number.
     *
     * @return itself
     */
    abstract Complex conjugate();

    /**
     * Multiplies the complex number with itself.
     *
     * @return itself
     */
    abstract Complex square();

    /**
     * Multiplies the complex number with a number (scalar multiplication).
     *
     * @param factor the factor
     * @return itself
     */
    abstract Complex multiply(double factor);

    /**
     * Multiplies the complex number with another complex number.
     *
     * @param z the complex number
     * @return itself
     */
    abstract Complex multiply(Complex z);

    /**
     * Divides the complex number by a number (scalar multiplication).
     *
     * @param divisor the divisor
     * @return itself
     */
    abstract Complex divide(double divisor);

    /**
     * Divides the complex number by another complex number.
     *
     * @param z the complex number
     * @return itself
     */
    abstract Complex divide(Complex z);

    // MISC
    
    abstract Complex clone();

}
