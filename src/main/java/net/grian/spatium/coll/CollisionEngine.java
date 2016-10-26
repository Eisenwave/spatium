package net.grian.spatium.coll;

import net.grian.spatium.geo.Ray;

/**
 * A engine which performs collision detection operations such as testing for
 * collisions or casting rays.
 */
public interface CollisionEngine {
	
	public abstract boolean test(Collideable a, Collideable b);
	
	public abstract float rayCast(Ray ray, Collideable c);
	
	public abstract void defineTest(byte a, byte b, CollisionTester test);
	
	public abstract void defineRayCast(byte a, RayCaster test);

}
