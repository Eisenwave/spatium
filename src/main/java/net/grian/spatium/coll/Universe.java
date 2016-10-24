package net.grian.spatium.coll;

import java.util.List;
import java.util.Set;

import net.grian.spatium.api.Ray;
import net.grian.spatium.impl.UniverseImpl;

public interface Universe<T> {
	
	/**
	 * Creates a new universe containing a certain type of handles.
	 * @param handleType the type of handles
	 * @return a new universe
	 * @see #getHandleType()
	 */
	public static <T> Universe<T> create(Class<T> handleType) {
		return new UniverseImpl<T>(handleType);
	}
	
	/**
	 * Returns the type of objects contained in this universe. For example,
	 * entities, blocks, etc.
	 * 
	 * @return the type of objects in the universe
	 */
	public abstract Class<T> getHandleType();
	
	/**
	 * Returns all {@link Collideable}s in this universe, aka. all obstructions
	 * when casting a ray.
	 * @return all collideables in the universe
	 */
	public abstract Set<Collideable> getContent();
	
	/**
	 * Casts a ray into the universe and returns a new list containing all
	 * the collisions of the ray.
	 * @param ray the ray
	 * @return all collisions of the ray
	 */
	public abstract List<RayCollision<T>> castRay(Ray ray);

}
