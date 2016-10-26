package net.grian.spatium.coll;

@FunctionalInterface
public interface CollisionTester {
	
	public boolean test(Collideable a, Collideable b);

}