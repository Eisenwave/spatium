package net.grian.spatium.coll;

import net.grian.spatium.api.Ray;

@FunctionalInterface
public interface RayCaster<T extends Collideable> {
	
	public abstract float rayCast(Ray ray, T c);

}
