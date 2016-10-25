package net.grian.spatium.complex;

import net.grian.spatium.Spatium;

/**
 * A complex number, represented in the form:
 * <blockquote>
 *   {@code z = r * exp(phi * pi * i)}
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
	public abstract EulerComplex setReal(float a);
	
	@Override
	public abstract EulerComplex setImaginary(float b);
	
	@Override
	public abstract EulerComplex setRadius(float r);
	
	@Override
	public abstract EulerComplex setAngle(float phi);
	
	@Override
	public abstract EulerComplex square();
	
	@Override
	public abstract EulerComplex multiply(float factor);
	
	@Override
	public abstract EulerComplex multiply(Complex z);
	
	@Override
	public abstract EulerComplex divide(float factor);
	
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
