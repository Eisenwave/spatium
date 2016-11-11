package net.grian.spatium.transform;

import net.grian.spatium.geo.Vector;

import java.util.function.Function;

@FunctionalInterface
public interface Transformation extends Function<Vector, Vector> {

    /**
     * Applies the transformation to a point.
     *
     * @param v the point
     * @return a new transformed point
     */
    public Vector apply(Vector v);

}
