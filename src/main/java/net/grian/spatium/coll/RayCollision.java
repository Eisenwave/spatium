package net.grian.spatium.coll;

public class RayCollision<T> implements Comparable<RayCollision<T>> {
	
	private final Collideable c;
	private final T handle;
	private final float t;
	
	public RayCollision(Collideable c, T handle, float t) {
		this.c = c;
		this.handle = handle;
		this.t = t;
	}
	
	public Collideable getCollideable() {
		return c;
	}
	
	public T getHandle() {
		return handle;
	}

	public float getRayMultiplier() {
		return t;
	}

	@Override
	public int compareTo(RayCollision<T> collision) {
		return (int) Math.signum(t - collision.t);
	}

}
