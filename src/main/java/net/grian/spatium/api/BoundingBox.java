package net.grian.spatium.api;

import net.grian.spatium.coll.Collideable;

public interface BoundingBox extends Collideable {
	
	public abstract Vector getCenter();
	
	public abstract Vector getMin();
	
	public abstract Vector getMax();

}
