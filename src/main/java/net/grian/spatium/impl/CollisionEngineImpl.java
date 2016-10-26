package net.grian.spatium.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import net.grian.spatium.coll.*;
import net.grian.spatium.geo.Ray;

public class CollisionEngineImpl implements CollisionEngine {
	
	private final Map<Integer, CollisionTester> tests = new HashMap<>();
	private final Map<Integer, RayCaster> rayCasts = new HashMap<>();
	
	public CollisionEngineImpl() {}

	@Override
	public boolean test(Collideable a, Collideable b) {
		return true;
		/*
		final int
		idA = a.getCollisionId(),
		idB = b.getCollisionId(),
		key = idA==idB? (idA << 16 | idB) : (Math.max(idA, idB) << 16 | Math.min(idA, idB));
		
		if (!tests.containsKey(key)) return false;
		
		CollisionTester tester = tests.get(a,b);
		*/
	}

	@Override
	public float rayCast(Ray ray, Collideable c) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	public void defineTest(byte a, byte b, CollisionTester<A, B> tester) {
		Objects.requireNonNull(tester);
		
		tests.put(new Class<?>[] {a, b}, tester);
		tests.put(new Class<?>[] {b, a}, tester);
	}

	public <T extends Collideable> void defineRayCast(
			Class<T> clazz, RayCaster<T> caster) {
		Objects.requireNonNull(clazz);
		Objects.requireNonNull(caster);
		
		rayCasts.put(clazz, caster);
	}
	*/

	@Override
	public void defineTest(byte a, byte b, CollisionTester test) {

	}

	@Override
	public void defineRayCast(byte a, RayCaster test) {

	}
}
