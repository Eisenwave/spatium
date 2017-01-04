package net.grian.spatium.geo;

import net.grian.spatium.enums.Axis;
import net.grian.spatium.impl.AxisSlabImpl;

public interface AxisSlab extends Slab {

    public static AxisSlab create(Axis axis, float min, float max) {
        return new AxisSlabImpl(axis, min, max);
    }

    /**
     * Returns the axis to which this slab is aligned.
     *
     * @return this slab's axis
     */
    public abstract Axis getAxis();

    @Override
    public abstract AxisPlane getMin();

    @Override
    public abstract AxisPlane getMax();

    @Override
    public default Slab setNormal(float x, float y, float z) {
        throw new UnsupportedOperationException();
    }

    /**
     * Moves the slab positively on its axis.
     *
     * @param depth the additional depth
     * @return itself
     */
    @Override
    public abstract AxisSlab push(float depth);

    /**
     * Moves the slab negatively on its axis.
     *
     * @param depth the additional depth
     * @return itself
     */
    @Override
    public abstract AxisSlab pull(float depth);

}
