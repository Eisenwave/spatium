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
    public abstract CartesianComplex setReal(float a);

    @Override
    public abstract CartesianComplex setImaginary(float b);

    @Override
    public abstract CartesianComplex setRadius(float r);

    @Override
    public abstract CartesianComplex setAngle(float phi);

    @Override
    public abstract CartesianComplex square();

    @Override
    public abstract CartesianComplex multiply(float factor);

    @Override
    public abstract CartesianComplex multiply(Complex z);

    @Override
    public abstract CartesianComplex divide(float factor);

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
