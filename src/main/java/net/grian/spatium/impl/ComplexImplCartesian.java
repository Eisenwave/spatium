package net.grian.spatium.impl;

import net.grian.spatium.complex.CartesianComplex;
import net.grian.spatium.complex.Complex;
import net.grian.spatium.complex.EulerComplex;

import java.util.Arrays;

public class ComplexImplCartesian implements CartesianComplex {

    private static final long serialVersionUID = 3260061277385578522L;

    private double a, b;

    public ComplexImplCartesian(double real, double imaginary) {
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
    public double getReal() {
        return a;
    }

    @Override
    public double getImaginary() {
        return b;
    }

    @Override
    public double getAngle() {
        return Math.atan2(a, b);
    }

    @Override
    public double getRadius() {
        return Math.hypot(a, b);
    }

    @Override
    public double getRadiusSquared() {
        return a*a + b*b;
    }

    // CHECKERS

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Complex && equals((Complex) obj);
    }

    // SETTERS

    @Override
    public CartesianComplex setReal(double a) {
        this.a = a;
        return this;
    }

    @Override
    public CartesianComplex setImaginary(double b) {
        this.b = b;
        return this;
    }

    @Override
    public CartesianComplex setRadius(double r) {
        double factor = r / getRadius();
        this.a *= factor;
        this.b *= factor;
        return this;
    }

    @Override
    public CartesianComplex setAngle(double phi) {
        phi += getAngle();
        double r = getRadius();

        this.a = Math.acos(phi) * r;
        this.b = Math.asin(phi) * r;
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
    public CartesianComplex multiply(double factor) {
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
    public CartesianComplex divide(double divisor) {
        this.a /= divisor;
        this.b /= divisor;
        return this;
    }

    @Override
    public CartesianComplex divide(Complex z) {
        double
        za = z.getReal(),
        zb = z.getImaginary(),
        denom = Math.hypot(za, zb);

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
        return Arrays.hashCode(new double[] {a, b});
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
