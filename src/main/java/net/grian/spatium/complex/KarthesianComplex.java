package net.grian.spatium.complex;

import net.grian.spatium.Spatium;

/**
 * A complex number, represented in the form:
 * <blockquote>
 *   {@code z = a + bi}
 * </blockquote>
 */
public interface KarthesianComplex extends Complex {

    // CHECKERS

    public default boolean equals(Complex z) {
        return
                Spatium.equals(this.getReal(), z.getReal()) &&
                Spatium.equals(this.getImaginary(), z.getImaginary());
    }

    // SETTERS

    @Override
    public abstract KarthesianComplex setReal(float a);

    @Override
    public abstract KarthesianComplex setImaginary(float b);

    @Override
    public abstract KarthesianComplex setRadius(float r);

    @Override
    public abstract KarthesianComplex setAngle(float phi);

    @Override
    public abstract KarthesianComplex square();

    @Override
    public abstract KarthesianComplex multiply(float factor);

    @Override
    public abstract KarthesianComplex multiply(Complex z);

    @Override
    public abstract KarthesianComplex divide(float factor);

    @Override
    public abstract KarthesianComplex divide(Complex z);

    // MISC

    @Override
    public abstract KarthesianComplex clone();

    @Override
    public default KarthesianComplex toKarthesian() {
        return clone();
    }

}
