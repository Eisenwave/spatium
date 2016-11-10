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
        return Curves.bezier(t, points);
    }

    @Override
    public float getLength() {
        //TODO implement this
        return 0;
    }

    @Override
    public float getLengthSquared() {
        //TODO implement this
        return 0;
    }
}
