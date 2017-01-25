package net.grian.spatium.complex;

import net.grian.spatium.Spatium;

/**
 * A complex number, represented in the form:
 * <blockquote>
 *   <code>z = r * e<sup>phi*pi*i</sup></code>
 * </blockquote>
 */
public interface EulerComplex extends Complex {

    // CHECKERS

    public default boolean equals(Complex z) {
        return
                Spatium.equals(this.getRadius(), z.getRadius()) &&
                Spatium.equals(this.getAngle(), z.getAngle());
    }

    // SETTERS

    @Override
    public abstract EulerComplex setReal(double a);

    @Override
    public abstract EulerComplex setImaginary(double b);

    @Override
    public abstract EulerComplex setRadius(double r);

    @Override
    public abstract EulerComplex setAngle(double phi);

    @Override
    public abstract EulerComplex square();

    @Override
    public abstract EulerComplex multiply(double factor);

    @Override
    public abstract EulerComplex multiply(Complex z);

    @Override
    public abstract EulerComplex divide(double factor);

    @Override
    public abstract EulerComplex divide(Complex z);

    // MISC

    @Override
    public abstract EulerComplex clone();

    @Override
    public default EulerComplex toEuler() {
        return clone();
    }

}
