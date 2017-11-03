package net.grian.spatium.impl;

import net.grian.spatium.polynom.Polynomial;

import java.util.Arrays;

public class PolynomialImpl implements Polynomial {
    
    private final int degree;
    
    /** Array in which the exponent is equal to the index */
    private final double[] handle;
    
    public PolynomialImpl(int degree) {
        this.degree = degree;
        this.handle = new double[degree+1];
    }
    
    public PolynomialImpl(double[] content) {
        this.degree = content.length-1;
        this.handle = new double[content.length];
        
        for (int i = 0; i < content.length; i++)
            handle[i] = content[degree - i];
    }
    
    public PolynomialImpl(PolynomialImpl copyOf) {
        this.degree = copyOf.degree;
        this.handle = Arrays.copyOf(copyOf.handle, 0);
    }
    
    @Override
    public int getDegree() {
        return degree;
    }
    
    @Override
    public double get(int exp) {
        if (exp > degree) return 0;
        return handle[exp];
    }
    
    @Override
    public void set(int exp, double cof) {
        handle[exp] = cof;
    }
    
    @Override
    public void multiply(double factor) {
        for (int i = 0; i<handle.length; i++)
            handle[i] *= factor;
    }
    
    @Override
    public String toString() {
        if (degree == 0) return toString0();
        if (degree == 1) return toString1();
        
        StringBuilder builder = new StringBuilder();
        builder
            .append(handle[degree])
            .append("x^")
            .append(degree);
        
        for (int i = degree-1; i > 1; i--)
            builder
                .append(" + ")
                .append(handle[i])
                .append("x^")
                .append(i);
        
        builder
            .append(" + ")
            .append(toString1());
        
        return builder.toString();
    }
    
    private String toString0() {
        return String.valueOf(handle[0]);
    }
    
    private String toString1() {
        return handle[1] + "x + " + handle[0];
    }
    
}
