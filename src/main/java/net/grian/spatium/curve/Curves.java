package net.grian.spatium.curve;

import net.grian.spatium.api.Vector;

public class Curves {
	
	public Vector bezier(float t, Vector a, Vector b) {
		return a.midPoint(b, t);
	}
	
	public Vector bezier(float t, Vector a, Vector b, Vector c) {
		return bezier(t, bezier(t, a, b), bezier(t, b, c));
	}
	
	public Vector bezier(float t, Vector... points) {
		if (points.length == 0) throw new IllegalArgumentException();
		if (points.length == 1) return points[0].clone();
		if (points.length == 2) return bezier(t, points[0], points[1]);
		if (points.length == 3) return bezier(t, points[0], points[1], points[2]);
		
		Vector[] next = new Vector[points.length - 1];
		for (int i = 0; i<next.length;)
			next[i] = bezier(t, points[i], points[i++]);
		
		return bezier(t, next);
	}

}
