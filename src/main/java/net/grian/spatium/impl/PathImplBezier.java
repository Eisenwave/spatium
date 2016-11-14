package net.grian.spatium.impl;

import net.grian.spatium.curve.Curves;
import net.grian.spatium.geo.Path;
import net.grian.spatium.geo.Vector;
import net.grian.spatium.util.PrimArrays;

public class PathImplBezier implements Path {

    private final Vector[] points;

    public PathImplBezier(Vector... points) {
        if (points.length == 0) throw new IllegalArgumentException("no points given");
        this.points = PrimArrays.clone(points);
    }

    @Override
    public Vector getPoint(float t) {
        return Curves.bezier(t % 1, points);
    }

    @Override
    public float getLength() {
        float result = 0;
        for (int i = 1; i<points.length; i++)
            result += points[i].distanceTo(points[i-1]);

        return result;
    }

    @Override
    public Vector[] getControlPoints() {
        return PrimArrays.clone(points);
    }

    @Override
    public float getLengthSquared() {
        float l = getLength();
        return l*l;
    }
}
