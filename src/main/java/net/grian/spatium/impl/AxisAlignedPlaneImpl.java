package net.grian.spatium.impl;

import net.grian.spatium.enums.Axis;
import net.grian.spatium.geo.AxisAlignedPlane;

public class AxisAlignedPlaneImpl implements AxisAlignedPlane {

    private Axis axis;
    private float depth;

    public AxisAlignedPlaneImpl(Axis axis, float depth) {
        this.axis = axis;
        this.depth = depth;
    }

    public AxisAlignedPlaneImpl(Axis axis) {
        this(axis, 0);
    }

    public AxisAlignedPlaneImpl(AxisAlignedPlaneImpl copyOf) {
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
    public AxisAlignedPlane setAxis(Axis axis) {
        return null;
    }

    @Override
    public AxisAlignedPlane setDepth(float depth) {
        this.depth = depth;
        return this;
    }

    @Override
    public AxisAlignedPlane clone() {
        return new AxisAlignedPlaneImpl(this);
    }
}
