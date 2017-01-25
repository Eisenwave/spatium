package net.grian.spatium.impl;

import net.grian.spatium.enums.Axis;
import net.grian.spatium.geo.AxisPlane;

public class AxisPlaneImpl implements AxisPlane {

    private Axis axis;
    private double depth;

    public AxisPlaneImpl(Axis axis, double depth) {
        this.axis = axis;
        this.depth = depth;
    }

    public AxisPlaneImpl(Axis axis) {
        this(axis, 0);
    }

    public AxisPlaneImpl(AxisPlaneImpl copyOf) {
        this.axis = copyOf.axis;
        this.depth = copyOf.depth;
    }

    @Override
    public Axis getAxis() {
        return axis;
    }

    @Override
    public double getDepth() {
        return depth;
    }

    @Override
    public AxisPlane setAxis(Axis axis) {
        return null;
    }

    @Override
    public AxisPlane setDepth(double depth) {
        this.depth = depth;
        return this;
    }

    @Override
    public AxisPlane clone() {
        return new AxisPlaneImpl(this);
    }
}
