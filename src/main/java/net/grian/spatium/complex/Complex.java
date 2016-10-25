package net.grian.spatium.complex;

import net.grian.spatium.Spatium;
import net.grian.spatium.SpatiumObject;
import net.grian.spatium.impl.EulerComplexImpl;
import net.grian.spatium.impl.KarthesianComplexImpl;

/**
 * A complex number which can either be provided in the form: {@code z = a + bi}
 * or the form: {@code z = r * e^(phi*i*pi)} depending on its implementation.
 */
public interface Complex extends SpatiumObject {
	
	/**
	 * Returns a new karthesian implementation of a complex number in the form:
	 * <blockquote>
	 *   {@code z = a + bi}
	 * </blockquote>
	 * 
	 * @param a the real part
	 * @param b the imaginary part
	 * @return a new complex number
	 */
	public static KarthesianComplex newKarthesian(float a, float b) {
		return new KarthesianComplexImpl(a, b);
	}
	
	/**
	 * Returns a new euler implementation of a complex number in the form:
	 * <blockquote>
	 *   {@code z = r * exp(phi * pi * i)}
	 * </blockquote>
	 * 
	 * @param r the radius
	 * @param phi the angle
	 * @return a new complex number
	 */
	public static EulerComplex newEuler(float r, float phi) {
		return new EulerComplexImpl(r, phi);
	}
	
	// GETTERS
	
	/**
	 * Returns the real part of this complex number.
	 * @return the real part
	 */
	public abstract float getReal();
	
	/**
	 * Returns the imaginary part of this complex number.
	 * @return the imaginary part
	 */
	public abstract float getImaginary();
	
	/**
	 * Returns the angle {@code phi} of this complex number.
	 * @return the angle
	 */
	public abstract float getAngle();
	
	/**
	 * Returns the radius of this complex number.
	 * @return the radius
	 */
	public abstract float getRadius();
	
	/**
	 * Returns the squared radius of this complex number.
	 * @return the squared radius
	 */
	public abstract float getRadiusSquared();
	
	// CHECKERS
	
	/**
	 * Returns whether this complex number is equal to another complex number.
	 */
	public default boolean equals(Complex z) {
		return
				Spatium.equals(this.getRadiusSquared(), z.getRadiusSquared()) &&
				Spatium.equals(this.getAngle(), z.getAngle());
	}
	
	// SETTERS
	
	/**
	 * Sets the real part of the complex number to a new value.
	 * 
	 * @param re the value
	 * @return itself
	 */
	public abstract Complex setReal(float re);
	
	/**
	 * Sets the imaginary part of the complex number to a new value.
	 * 
	 * @param im the value
	 * @return itself
	 */
	public abstract Complex setImaginary(float im);
	
	/**
	 * Sets the radius of the complex number to a new value.
	 * 
	 * @param r the radius
	 * @return itself
	 */
	public abstract Complex setRadius(float r);
	
	/**
	 * Sets the angle {@code phi} of the complex number to a new value.
	 * 
	 * @param phi the angle
	 * @return itself
	 */
	public abstract Complex setAngle(float phi);
	
	/**
	 * Inverts the imaginary part of the complex number.
	 * 
	 * @return itself
	 */
	public abstract Complex conjugate();
	
	/**
	 * Multiplies the complex number with itself.
	 * 
	 * @return itself
	 */
	public abstract Complex square();
	
	/**
	 * Multiplies the complex number with a number (scalar multiplication).
	 * 
	 * @param factor the factor
	 * @return itself
	 */
	public abstract Complex multiply(float factor);
	
	/**
	 * Multiplies the complex number with another complex number.
	 * 
	 * @param z the complex number
	 * @return itself
	 */
	public abstract Complex multiply(Complex z);
	
	/**
	 * Divides the complex number by a number (scalar multiplication).
	 * 
	 * @param divisor the divisor
	 * @return itself
	 */
	public abstract Complex divide(float divisor);
	
	/**
	 * Divides the complex number by another complex number.
	 * 
	 * @param z the complex number
	 * @return itself
	 */
	public abstract Complex divide(Complex z);
	
	// MISC
	
	/**
	 * Returns a new karthesian representation of this complex number. If this
	 * complex number is already represented in a the karthesian form, the
	 * returned object is equal to {@link #clone()}.
	 * 
	 * @return a new karthesian complex number
	 */
	public abstract KarthesianComplex toKarthesian();
	
	/**
	 * Returns a new euler representation of this complex number. If this
	 * complex number is already represented in a the euler form, the
	 * returned object is equal to {@link #clone()}.
	 * 
	 * @return a new euler complex number
	 */
	public abstract EulerComplex toEuler();
	
	/**
	 * Returns a new copy of this complex number. The representation of the
	 * complex number (karthesian or euler) will be the same as the original
	 * object's.
	 * 
	 * @return a copy of this complex number of equal representation
	 */
	public abstract Complex clone();

}
