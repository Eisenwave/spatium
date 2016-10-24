package net.grian.spatium.util;

import java.io.Serializable;
import java.util.Arrays;

public class Complex implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 3260061277385578522L;
	
	public final static Complex ZERO = new Complex(0, 0);
	
	public static Complex ofArith(double real, double imaginary) {
		return new Complex(real, imaginary);
	}
	
	public static Complex ofPolar(double r, double phi) {
		return new Complex(
				r * Math.acos(phi),
				r * Math.asin(phi)
				);
	}
	
	private final double a, b;
	
	public Complex(double real, double imaginary) {
		this.a = real;
		this.b = imaginary;
	}
	
	public Complex(Complex cloneOf) {
		this(cloneOf.a, cloneOf.b);
	}
	
	public double getReal() {
		return a;
	}
	
	public double getImaginary() {
		return b;
	}
	
	public double getAngle() {
		return Math.atan2(a, b);
	}
	
	public double getRadius() {
		return Math.hypot(a, b);
	}
	
	public Complex conjugate() {
		return new Complex(this.a, -this.b);
	}
	
	public Complex add(Complex z) {
		return new Complex(this.a + z.a, this.b + z.b);
	}
	
	public Complex subtract(Complex z) {
		return new Complex(this.a - z.a, this.b - z.b);
	}
	
	public Complex square() {
		return new Complex(
				a * a - b * b,
				2 * a * b);
	}
	
	public Complex multiply(double n) {
		return new Complex(a * n, b * n);
	}
	
	public Complex multiply(Complex z) {
		return new Complex(
				this.a * z.a - this.b * z.b,
				this.a * z.b + this.b * z.a
				);
	}
	
	public Complex divide(double n) {
		return new Complex(a / n, b / n);
	}
	
	public Complex divide(Complex z) {
		double denom = Math.hypot(z.a, z.b);
		
		return new Complex(
				(this.a * z.a + this.b * z.b) / denom,
				(this.b * z.a - this.a * z.b) / denom);
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Complex && equals((Complex) obj);
	}
	
	public boolean equals(Complex z) {
		return this.a == z.a && this.b == z.b;
	}
	
	@Override
	public String toString() {
		return "("+a+" + "+b+"i)";
	}
	
	@Override
	public Complex clone() {
		return new Complex(this);
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(new double[] {a, b});
	}

}
