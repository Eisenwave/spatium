package net.grian.spatium.impl;

import net.grian.spatium.complex.CartesianComplex;
import net.grian.spatium.complex.Complex;
import net.grian.spatium.complex.EulerComplex;

import java.util.Arrays;

public class ComplexImplCartesian implements CartesianComplex {

    private static final long serialVersionUID = 3260061277385578522L;

    private float a, b;

    public ComplexImplCartesian(float real, float imaginary) {
        this.a = real;
        this.b = imaginary;
    }

    public ComplexImplCartesian(ComplexImplCartesian copyOf) {
        this(copyOf.a, copyOf.b);
    }

    public ComplexImplCartesian(EulerComplex copyOf) {
        this(copyOf.getReal(), copyOf.getImaginary());
    }

    // GETTERS

    @Override
    public float getReal() {
        return a;
    }

    @Override
    public float getImaginary() {
        return b;
    }

    @Override
    public float getAngle() {
        return (float) Math.atan2(a, b);
    }

    @Override
    public float getRadius() {
        return (float) Math.hypot(a, b);
    }

    @Override
    public float getRadiusSquared() {
        return a*a + b*b;
    }

    // CHECKERS

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Complex && equals((Complex) obj);
    }

    // SETTERS

    @Override
    public CartesianComplex setReal(float a) {
        this.a = a;
        return this;
    }

    @Override
    public CartesianComplex setImaginary(float b) {
        this.b = b;
        return this;
    }

    @Override
    public CartesianComplex setRadius(float r) {
        float factor = r / getRadius();
        this.a *= factor;
        this.b *= factor;
        return this;
    }

    @Override
    public CartesianComplex setAngle(float phi) {
        phi += getAngle();
        float r = getRadius();

        this.a = (float) Math.acos(phi) * r;
        this.b = (float) Math.asin(phi) * r;
        return this;
    }

    @Override
    public Complex conjugate() {
        this.b = -b;
        return this;
    }

    @Override
    public CartesianComplex square() {
        this.a = a*a - b*b;
        this.b = 2 * a * b;
        return this;
    }

    @Override
    public CartesianComplex multiply(float factor) {
        this.a *= factor;
        this.b *= factor;
        return this;
    }

    @Override
    public CartesianComplex multiply(Complex z) {
        this.a = this.getReal() * z.getReal()      - this.getImaginary() * z.getImaginary();
        this.b = this.getReal() * z.getImaginary() + this.getImaginary() * z.getReal();
        return this;
    }

    @Override
    public CartesianComplex divide(float divisor) {
        this.a /= divisor;
        this.b /= divisor;
        return this;
    }

    @Override
    public CartesianComplex divide(Complex z) {
        float
        za = z.getReal(),
        zb = z.getImaginary(),
        denom = (float) Math.hypot(za, zb);

        this.a = (this.a * za + this.b * zb) / denom;
        this.b = (this.b * za - this.a * zb) / denom;
        return this;
    }

    // MISC

    @Override
    public String toString() {
        return "("+a+" + "+b+"i)";
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new float[] {a, b});
    }

    @Override
    public EulerComplex toEuler() {
        return new ComplexImplEuler(this);
    }

    @Override
    public CartesianComplex clone() {
        return new ComplexImplCartesian(this);
    }

}
