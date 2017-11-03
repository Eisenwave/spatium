package net.grian.spatium.curve;

import net.grian.spatium.geo3.Vector3;

public class Curves {

    public static Vector3 bezier(double t, Vector3 a, Vector3 b) {
        return a.midPoint(b, t);
    }

    public static Vector3 bezier(double t, Vector3 a, Vector3 b, Vector3 c) {
        return bezier(t, bezier(t, a, b), bezier(t, b, c));
    }

    public static Vector3 bezier(double t, Vector3... points) {
        if (points.length == 0) throw new IllegalArgumentException();
        if (points.length == 1) return points[0].clone();
        if (points.length == 2) return bezier(t, points[0], points[1]);
        if (points.length == 3) return bezier(t, points[0], points[1], points[2]);

        Vector3[] next = new Vector3[points.length - 1];
        for (int i = 0; i<next.length;)
            next[i] = bezier(t, points[i], points[i++]);

        return bezier(t, next);
    }

}
