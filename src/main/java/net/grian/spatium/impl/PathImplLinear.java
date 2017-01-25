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
    public Vector getPoint(double t) {
        t %= 1;
        if (points.length == 1) return getOrigin();
        if (points.length == 2) return points[0].midPoint(points[1]);

        double[] lengths = new double[points.length-1];
        for (int i = 1; i<=points.length; i++)
            lengths[i] = points[i].distanceTo(points[i-1]);
        setTotalLength(lengths, 1);

        for (int i = 0; i<lengths.length; i++)
            if (t >= lengths[i]) return points[i].clone();

        return getEnd();
    }

    private static void setTotalLength(double[] numbers, double length) {
        double factor = length / sum(numbers);
        for (int i = 0; i<numbers.length; i++)
            numbers[i] *= factor;
    }

    private static double sum(double[] numbers) {
        int result = 0;
        for (double number : numbers)
            result += number;

        return result;
    }

    @Override
    public double getLength() {
        return Math.sqrt(getLengthSquared());
    }

    @Override
    public double getLengthSquared() {
        double result = 0;
        for (int i = 1; i<points.length; i++)
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

    @Override
    public Vector[] getControlPoints() {
        return PrimArrays.clone(points);
    }
}
