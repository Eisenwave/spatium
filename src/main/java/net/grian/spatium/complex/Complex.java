package net.grian.spatium.complex;

import net.grian.spatium.Spatium;
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
    public static Complex fromCartesian(double a, double b) {
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
    public static Complex fromEuler(double r, double phi) {
        return new ComplexImplEuler(r, phi);
    }

    // GETTERS

    /**
     * Returns the real part of this complex number.
     *
     * @return the real part
     */
    public abstract double getReal();

    /**
     * Returns the imaginary part of this complex number.
     *
     * @return the imaginary part
     */
    public abstract double getImaginary();

    /**
     * Returns the angle {@code phi} of this complex number.
     *
     * @return the angle
     */
    public abstract double getAngle();

    /**
     * Returns the radius of this complex number.
     *
     * @return the radius
     */
    public abstract double getRadius();

    /**
     * Returns the squared radius of this complex number.
     *
     * @return the squared radius
     */
    public abstract double getRadiusSquared();

    // CHECKERS

    /**
     * Returns whether this complex number is equal to another complex number.
     */
    public default boolean equals(Complex z) {
        return
                Spatium.equals(this.getRadiusSquared(), z.getRadiusSquared()) &&
                Spatium.equals(this.getAngle(), z.getAngle());
    }

    // SETTERS

    /**
     * Sets the real part of the complex number to a new value.
     *
     * @param re the value
     * @return itself
     */
    public abstract Complex setReal(double re);

    /**
     * Sets the imaginary part of the complex number to a new value.
     *
     * @param im the value
     * @return itself
     */
    public abstract Complex setImaginary(double im);

    /**
     * Sets the radius of the complex number to a new value.
     *
     * @param r the radius
     * @return itself
     */
    public abstract Complex setRadius(double r);

    /**
     * Sets the angle {@code phi} of the complex number to a new value.
     *
     * @param phi the angle
     * @return itself
     */
    public abstract Complex setAngle(double phi);

    /**
     * Inverts the imaginary part of the complex number.
     *
     * @return itself
     */
    public abstract Complex conjugate();

    /**
     * Multiplies the complex number with itself.
     *
     * @return itself
     */
    public abstract Complex square();

    /**
     * Multiplies the complex number with a number (scalar multiplication).
     *
     * @param factor the factor
     * @return itself
     */
    public abstract Complex multiply(double factor);

    /**
     * Multiplies the complex number with another complex number.
     *
     * @param z the complex number
     * @return itself
     */
    public abstract Complex multiply(Complex z);

    /**
     * Divides the complex number by a number (scalar multiplication).
     *
     * @param divisor the divisor
     * @return itself
     */
    public abstract Complex divide(double divisor);

    /**
     * Divides the complex number by another complex number.
     *
     * @param z the complex number
     * @return itself
     */
    public abstract Complex divide(Complex z);

    // MISC

    /**
     * Returns a new copy of this complex number. The representation of the
     * complex number (cartesian or euler) will be the same as the original
     * object's.
     *
     * @return a copy of this complex number of equal representation
     */
    public abstract Complex clone();

}
