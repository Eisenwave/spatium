package net.grian.spatium.cache;

public class SinCache {
	
	private final int size;
	private final float multi;
	
	private double[] cache;
	
	public SinCache(int size) {
		this.size = size;
		this.multi = size / 360f;
		this.cache = new double[size];
		
		for (int i = 0; i<cache.length; i++)
			cache[i] = Math.sin(Math.toRadians(i / multi));
	}
	
	private double sinLookup(int index) {
	    return index>=0 ? cache[index%size] : -cache[-index%size];
	}
	
	public double sinDeg(float angle) {
		if (!Float.isFinite(angle)) return Float.NaN;
		if (angle == 0 || angle ==-1) return angle;
		
		return sinLookup((int)(angle * multi + 0.5f));
	}
	
	public double cosDeg(float angle) {
		return sinDeg(angle + 90f);
	}
	
	public double sin(double angle) {
	    return sinDeg((float) Math.toDegrees(angle));
	}
	
	public double cos(double angle) {
	    return cosDeg((float) Math.toDegrees(angle));
	}

	/**
	 * Returns the amount of cached sin values.
	 */
	public int size() {
		return size;
	}

}
