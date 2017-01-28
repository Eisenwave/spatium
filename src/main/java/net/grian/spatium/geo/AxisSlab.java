package net.grian.spatium.geo;

import net.grian.spatium.enums.Axis;
import net.grian.spatium.impl.AxisSlabImpl;

public interface AxisSlab extends Slab {

    public static AxisSlab create(Axis axis, double min, double max) {
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

    //CHECKERS

    @Override
    public default boolean contains(double x, double y, double z) {
        switch (getAxis()) {
            case X: return x > getMinDepth() && x < getMaxDepth();
            case Y: return y > getMinDepth() && y < getMaxDepth();
            case Z: return z > getMinDepth() && z < getMaxDepth();
            default: throw new IllegalStateException("unknown axis ("+getAxis()+")");
        }
    }

    //SETTERS

    @Override
    public default Slab setNormal(double x, double y, double z) {
        throw new UnsupportedOperationException();
    }

    /**
     * Moves the slab positively on its axis.
     *
     * @param depth the additional depth
     * @return itself
     */
    @Override
    public abstract AxisSlab push(double depth);

    /**
     * Moves the slab negatively on its axis.
     *
     * @param depth the additional depth
     * @return itself
     */
    @Override
    public abstract AxisSlab pull(double depth);

}
