package net.grian.spatium.impl;

import net.grian.spatium.geo.Path;
import net.grian.spatium.geo.Vector;
import net.grian.spatium.util.PrimArrays;

public class PathImplLinear implements Path {

    private final Vector[] points;

    public PathImplLinear(Vector... points) {
        if (points.length == 0) throw new IllegalArgumentException("no points given");
        this.points = PrimArrays.clone(points);
    }

    @Override
    public Vector getPoint(float t) {
        if (t < 0 || t > 1) throw new IllegalArgumentException("multiplier out of range ("+t+")");
        if (points.length == 1) return getOrigin();
        if (points.length == 2) return points[0].midPoint(points[1]);

        float[] lengths = new float[points.length-1];
        for (int i = 1; i<=points.length; i++)
            lengths[i] = points[i].distanceTo(points[i-1]);
        PrimArrays.normalize(lengths);

        for (int i = 0; i<lengths.length; i++)
            if (t >= lengths[i]) return points[i].clone();

        return getEnd();
    }

    @Override
    public float getLength() {
        return (float) Math.sqrt(getLengthSquared());
    }

    @Override
    public float getLengthSquared() {
        float result = 0;
        for (int i = 1; i<=points.length; i++)
            result += points[i].distanceTo(points[i-1]);

        return result;
    }

    @Override
    public Vector getEnd() {
        return points[points.length - 1].clone();
    }

    @Override
    public Vector getOrigin() {
        return points[0].clone();
    }

}
