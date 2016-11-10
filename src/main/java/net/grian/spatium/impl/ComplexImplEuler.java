package net.grian.spatium.impl;

import net.grian.spatium.complex.CartesianComplex;
import net.grian.spatium.complex.Complex;
import net.grian.spatium.complex.EulerComplex;

public class ComplexImplEuler implements EulerComplex {

    private static final long serialVersionUID = -3477899621141300107L;

    private float r, phi;

    public ComplexImplEuler(float r, float phi) {
        this.r = r;
        this.phi = phi;
    }

    public ComplexImplEuler(ComplexImplEuler copyOf) {
        this.r = copyOf.r;
        this.phi = copyOf.phi;
    }

    public ComplexImplEuler(CartesianComplex copyOf) {
        this.r = copyOf.getRadius();
        this.phi = copyOf.getAngle();
    }

    // GETTERS

    @Override
    public float getReal() {
        return (float) (r * Math.acos(phi));
    }

    @Override
    public float getImaginary() {
        return (float) (r * Math.asin(phi));
    }

    @Override
    public float getRadius() {
        return r;
    }

    @Override
    public float getRadiusSquared() {
        return r * r;
    }

    @Override
    public float getAngle() {
        return phi;
    }

    // CHECKERS

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Complex && equals((Complex) obj);
    }

    // SETTERS

    @Override
    public EulerComplex setReal(float a) {
        CartesianComplex z = toCartesian().setReal(a);
        this.r = z.getRadius();
        this.phi = z.getAngle();
        return this;
    }

    @Override
    public EulerComplex setImaginary(float b) {
        CartesianComplex z = toCartesian().setImaginary(b);
        this.r = z.getRadius();
        this.phi = z.getAngle();
        return this;
    }

    @Override
    public EulerComplex setRadius(float r) {
        this.r = r;
        return this;
    }

    @Override
    public EulerComplex setAngle(float phi) {
        this.phi = phi;
        return this;
    }

    @Override
    public EulerComplex conjugate() {
        this.phi = -phi;
        return this;
    }

    @Override
    public EulerComplex square() {
        r *= r;
        phi += phi;
        return this;
    }

    @Override
    public EulerComplex multiply(float factor) {
        this.r *= factor;
        return this;
    }

    @Override
    public EulerComplex multiply(Complex z) {
        this.r *= z.getRadius();
        this.phi += z.getAngle();
        return this;
    }

    @Override
    public EulerComplex divide(float divisor) {
        this.r /= divisor;
        return this;
    }

    @Override
    public EulerComplex divide(Complex z) {
        this.r /= z.getRadius();
        this.phi -= z.getAngle();
        return this;
    }

    // MISC

    @Override
    public String toString() {
        return "("+r+" * exp("+phi+"* i * pi))";
    }

    @Override
    public CartesianComplex toCartesian() {
        return new ComplexImplCartesian(this);
    }

    @Override
    public EulerComplex clone() {
        return new ComplexImplEuler(this);
    }

}