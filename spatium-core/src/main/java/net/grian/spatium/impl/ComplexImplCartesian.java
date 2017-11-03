package net.grian.spatium.impl;

import net.grian.spatium.complex.Complex;

import java.util.Arrays;

public class ComplexImplCartesian implements Complex {

    private static final long serialVersionUID = 3260061277385578522L;

    private double a, b;

    public ComplexImplCartesian(double real, double imaginary) {
        this.a = real;
        this.b = imaginary;
    }

    public ComplexImplCartesian(ComplexImplCartesian copyOf) {
        this(copyOf.a, copyOf.b);
    }

    public ComplexImplCartesian(Complex copyOf) {
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
    public double getModulo() {
        return Math.hypot(a, b);
    }

    @Override
    public double getModuloSquared() {
        return a*a + b*b;
    }

    // CHECKERS

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Complex && equals((Complex) obj);
    }

    // SETTERS

    @Override
    public Complex setReal(double a) {
        this.a = a;
        return this;
    }

    @Override
    public Complex setImaginary(double b) {
        this.b = b;
        return this;
    }

    @Override
    public Complex setModulo(double r) {
        double factor = r / getModulo();
        this.a *= factor;
        this.b *= factor;
        return this;
    }

    @Override
    public Complex setAngle(double phi) {
        phi += getAngle();
        double r = getModulo();

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
    public Complex square() {
        this.a = a*a - b*b;
        this.b = 2 * a * b;
        return this;
    }

    @Override
    public Complex multiply(double factor) {
        this.a *= factor;
        this.b *= factor;
        return this;
    }

    @Override
    public Complex multiply(Complex z) {
        final double
            a = this.getReal(), b = this.getImaginary(),
            c = z.getReal(), d = z.getImaginary();
        
        this.a = a*c - b*d;
        this.b = a*d + b*c;
        return this;
    }

    @Override
    public Complex divide(double divisor) {
        this.a /= divisor;
        this.b /= divisor;
        return this;
    }

    @Override
    public Complex divide(Complex z) {
        final double
            a = this.getReal(), b = this.getImaginary(),
            c = z.getReal(), d = z.getImaginary(),
            denom = c*c + d*d;
    
        this.a = (a*c + b*d) / denom;
        this.b = (b*c - a*d) / denom;
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
    
    public ComplexImplEuler toEuler() {
        return new ComplexImplEuler(this);
    }

    @Override
    public Complex clone() {
        return new ComplexImplCartesian(this);
    }

}
