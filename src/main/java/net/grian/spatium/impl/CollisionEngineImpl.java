package net.grian.spatium.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import net.grian.spatium.coll.*;
import net.grian.spatium.geo.Ray;

public class CollisionEngineImpl implements CollisionEngine {
	
	public CollisionEngineImpl() {}

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

}
