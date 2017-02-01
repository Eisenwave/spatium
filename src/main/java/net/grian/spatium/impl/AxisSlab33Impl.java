package net.grian.spatium.impl;

import net.grian.spatium.enums.Axis;
import net.grian.spatium.geo3.*;

public class AxisSlab33Impl implements AxisSlab3 {

    private Axis axis;

    private double min, max;

    public AxisSlab33Impl(Axis axis, double min, double max) {
        this.axis = axis;
        this.min = min;
        this.max = max;
    }

    @Override
    public Axis getAxis() {
        return axis;
    }

    @Override
    public Vector3 getNormal() {
        switch (axis) {
            case X: return Vector3.fromXYZ(1, 0, 0);
            case Y: return Vector3.fromXYZ(0, 1, 0);
            case Z: return Vector3.fromXYZ(0, 0, 1);
            default: throw new AssertionError(axis);
        }
    }

    @Override
    public AxisPlane getMin() {
        return AxisPlane.create(axis, min);
    }

    @Override
    public AxisPlane getMax() {
        return AxisPlane.create(axis, max);
    }

    @Override
    public Vector3 getMinPoint() {
        return null;
    }

    @Override
    public Vector3 getMaxPoint() {
        return null;
    }

    @Override
    public double getMinDepth() {
        return min;
    }

    @Override
    public double getMaxDepth() {
        return max;
    }

    @Override
    public Slab3 setMinDepth(double depth) {
        if (depth > max) {
            min = max;
            max = depth;
        }
        else
            min = depth;
        return this;
    }

    @Override
    public Slab3 setMaxDepth(double depth) {
        if (depth < min) {
            max = min;
            min = depth;
        }
        else
            max = depth;
        return this;
    }

    @Override
    public AxisSlab3 push(double depth) {
        min += depth;
        max += depth;
        return this;
    }

    @Override
    public AxisSlab3 pull(double depth) {
        min += depth;
        max += depth;
        return this;
    }

}
