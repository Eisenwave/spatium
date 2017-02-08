package net.grian.spatium.impl;

import net.grian.spatium.complex.Complex;

public class ComplexImplEuler implements Complex {

    private static final long serialVersionUID = -3477899621141300107L;

    private double r, phi;

    public ComplexImplEuler(double r, double phi) {
        this.r = r;
        this.phi = phi;
    }

    public ComplexImplEuler(ComplexImplEuler copyOf) {
        this.r = copyOf.r;
        this.phi = copyOf.phi;
    }

    public ComplexImplEuler(ComplexImplCartesian copyOf) {
        this.r = copyOf.getRadius();
        this.phi = copyOf.getAngle();
    }

    // GETTERS

    @Override
    public double getReal() {
        return r * Math.acos(phi);
    }

    @Override
    public double getImaginary() {
        return r * Math.asin(phi);
    }

    @Override
    public double getRadius() {
        return r;
    }

    @Override
    public double getRadiusSquared() {
        return r * r;
    }

    @Override
    public double getAngle() {
        return phi;
    }

    // CHECKERS

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Complex && equals((Complex) obj);
    }

    // SETTERS

    @Override
    public Complex setReal(double a) {
        ComplexImplCartesian z = toCartesian();
        z.setReal(a);
        
        this.r = z.getRadius();
        this.phi = z.getAngle();
        return this;
    }

    @Override
    public Complex setImaginary(double b) {
        ComplexImplCartesian z = toCartesian();
        z.setImaginary(b);
        
        this.r = z.getRadius();
        this.phi = z.getAngle();
        return this;
    }

    @Override
    public Complex setRadius(double r) {
        this.r = r;
        return this;
    }

    @Override
    public Complex setAngle(double phi) {
        this.phi = phi;
        return this;
    }

    @Override
    public Complex conjugate() {
        this.phi = -phi;
        return this;
    }

    @Override
    public Complex square() {
        r *= r;
        phi += phi;
        return this;
    }

    @Override
    public Complex multiply(double factor) {
        this.r *= factor;
        return this;
    }

    @Override
    public Complex multiply(Complex z) {
        this.r *= z.getRadius();
        this.phi += z.getAngle();
        return this;
    }

    @Override
    public Complex divide(double divisor) {
        this.r /= divisor;
        return this;
    }

    @Override
    public Complex divide(Complex z) {
        this.r /= z.getRadius();
        this.phi -= z.getAngle();
        return this;
    }

    // MISC

    @Override
    public String toString() {
        return "("+r+" * exp("+phi+"* i * pi))";
    }
    
    private ComplexImplCartesian toCartesian() {
        return new ComplexImplCartesian(this);
    }

    @Override
    public Complex clone() {
        return new ComplexImplEuler(this);
    }

}
