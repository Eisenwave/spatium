package net.grian.spatium.coll;

import net.grian.spatium.geo.Ray;

@FunctionalInterface
public interface RayCaster {
	
	public abstract float rayCast(Ray ray, Collideable c);

}
