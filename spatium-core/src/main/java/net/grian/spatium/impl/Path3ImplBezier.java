package net.grian.spatium.impl;

import net.grian.spatium.curve.Curves;
import net.grian.spatium.geo3.Path3;
import net.grian.spatium.geo3.Vector3;

import java.util.Arrays;

public class Path3ImplBezier implements Path3 {

    private final Vector3[] points;

    public Path3ImplBezier(Vector3... points) {
        if (points.length == 0) throw new IllegalArgumentException("no points given");
        this.points = Arrays.copyOf(points, points.length);
    }

    @Override
    public Vector3 getPoint(double t) {
        return Curves.bezier(t % 1, points);
    }

    @Override
    public double getLength() {
        double result = 0;
        for (int i = 1; i<points.length; i++)
            result += points[i].distanceTo(points[i-1]);

        return result;
    }

    @Override
    public Vector3[] getControlPoints() {
        return Arrays.copyOf(points, points.length);
    }

    @Override
    public double getLengthSquared() {
        double l = getLength();
        return l*l;
    }
}
