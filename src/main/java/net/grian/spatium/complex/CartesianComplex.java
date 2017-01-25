package net.grian.spatium.complex;

import net.grian.spatium.Spatium;

/**
 * A complex number, represented in the form:
 * <blockquote>
 *   {@code z = a + bi}
 * </blockquote>
 */
public interface CartesianComplex extends Complex {

    // CHECKERS

    public default boolean equals(Complex z) {
        return
                Spatium.equals(this.getReal(), z.getReal()) &&
                Spatium.equals(this.getImaginary(), z.getImaginary());
    }

    // SETTERS

    @Override
    public abstract CartesianComplex setReal(double a);

    @Override
    public abstract CartesianComplex setImaginary(double b);

    @Override
    public abstract CartesianComplex setRadius(double r);

    @Override
    public abstract CartesianComplex setAngle(double phi);

    @Override
    public abstract CartesianComplex square();

    @Override
    public abstract CartesianComplex multiply(double factor);

    @Override
    public abstract CartesianComplex multiply(Complex z);

    @Override
    public abstract CartesianComplex divide(double factor);

    @Override
    public abstract CartesianComplex divide(Complex z);

    // MISC

    @Override
    public abstract CartesianComplex clone();

    @Override
    public default CartesianComplex toCartesian() {
        return clone();
    }

}
