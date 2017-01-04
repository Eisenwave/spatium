package net.grian.spatium.impl;

import net.grian.spatium.enums.Axis;
import net.grian.spatium.geo.AxisPlane;
import net.grian.spatium.geo.Plane;

public class AxisPlaneImpl implements AxisPlane {

    private Axis axis;
    private float depth;

    public AxisPlaneImpl(Axis axis, float depth) {
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
    public float getDepth() {
        return depth;
    }

    @Override
    public AxisPlane setAxis(Axis axis) {
        return null;
    }

    @Override
    public AxisPlane setDepth(float depth) {
        this.depth = depth;
        return this;
    }

    @Override
    public AxisPlane clone() {
        return new AxisPlaneImpl(this);
    }
}
