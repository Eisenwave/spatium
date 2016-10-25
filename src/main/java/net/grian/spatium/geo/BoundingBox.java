package net.grian.spatium.geo;

import net.grian.spatium.coll.Collideable;

public interface BoundingBox extends Collideable {
	
	public abstract Vector getCenter();
	
	public abstract Vector getMin();
	
	public abstract Vector getMax();

}
