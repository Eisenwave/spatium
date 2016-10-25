package net.grian.spatium.coll;

@FunctionalInterface
public interface CollisionTester<A extends Collideable, B extends Collideable> {
	
	public boolean test(A a, B b);

}