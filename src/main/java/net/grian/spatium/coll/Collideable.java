package net.grian.spatium.coll;

import net.grian.spatium.SpatiumObject;

public interface Collideable extends SpatiumObject {
	
	public final static byte
	VECTOR = 0,
	AABB = 1,
	AATSBP = 2,
	SPHERE = 3,
	PLANE = 4,
	RAY = 5;
	
	/**
	 * Returns the ID of this object which makes it recognizable to a
	 * {@link CollisionEngine}. Using this ID, unique collisions between this
	 * and other collideable objects can be defined.
	 * 
	 * @return the collision id
	 */
	public byte getCollisionId();

}
