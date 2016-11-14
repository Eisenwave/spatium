package net.grian.spatium.geo;

import net.grian.spatium.Spatium;

/**
 * <p>
 *     A pair of parallel planes in <code>R<sup>3</sup></code>, provided in the form:
 * </p>
 * <blockquote>
 *         <code>ax + by + cz = d<sub>min</sub></code>
 *     <br><code>ax + by + cz = d<sub>max</sub></code>
 * </blockquote>
 * With the normal vector being <code>(a, b, c)</code>.
 * <p>
 *     A slab is an infinite but enclosed space with infinite volume and surface area.
 * </p>
 */
public interface Slab {

    //GETTERS

    public abstract Vector getNormal();

    public abstract Plane getMin();

    public abstract Plane getMax();

    public abstract Vector getMinPoint();

    public abstract Vector getMaxPoint();

    public abstract float getMinDepth();

    public abstract float getMaxDepth();

    public default float getThickness() {
        return getMaxDepth() - getMinDepth();
    }

    //CHECKERS

    public default boolean equals(Slab slab) {
        return
                this.getNormal().isMultipleOf(slab.getNormal()) &&
                Spatium.equals(this.getMinDepth(), slab.getMinDepth()) &&
                Spatium.equals(this.getMaxDepth(), slab.getMaxDepth());
    }

    public default boolean contains(float x, float y, float z) {
        Vector n = getNormal();
        float invLength = 1 / n.getLength();
        float dot = n.dot(x, y, z);

        return
                (dot - getMinDepth()) * invLength >= 0 &&  //signed distance to min plane positive or 0
                (dot - getMaxDepth()) * invLength <= 0;    //signed distance to max plane negative or 0
    }

    public default boolean contains(Vector point) {
        return contains(point.getX(), point.getY(), point.getZ());
    }

    //SETTERS

    public abstract Slab setNormal(float x, float y, float z);

    public default Slab setNormal(Vector normal) {
        return setNormal(normal.getX(), normal.getY(), normal.getZ());
    }

    public abstract Slab setMinDepth(float depth);

    public abstract Slab setMaxDepth(float depth);

    /**
     * Moves the slab into the direction of its normal vector.
     *
     * @param depth the additional depth
     * @return itself
     */
    public abstract Slab push(float depth);

    /**
     * Moves the slab into the opposite direction of its normal vector.
     *
     * @param depth the additional depth
     * @return itself
     */
    public abstract Slab pull(float depth);

}
