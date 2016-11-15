package net.grian.spatium.geo;

import net.grian.spatium.Spatium;
import net.grian.spatium.impl.SlabImpl;

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

    /**
     * Constructs a new slab from two plane equations (general form).
     * <blockquote>
     *         <code>ax + by + cz = d<sub>min</sub></code>
     *     <br><code>ax + by + cz = d<sub>max</sub></code>
     * </blockquote>
     *
     * @param a the x-coordinate of the normal vector
     * @param b the y-coordinate of the normal vector
     * @param c the z-coordinate of the normal vector
     * @param dmin the minimum d value
     * @param dmax the maximum d value
     * @return a new slab
     */
    public static Slab create(float a, float b, float c, float dmin, float dmax) {
        return new SlabImpl(a, b, c, dmin, dmax);
    }

    /**
     * Constructs a new slab from two plane equations (general form).
     * <blockquote>
     *         <code>ax + by + cz = d<sub>min</sub></code>
     *     <br><code>ax + by + cz = d<sub>max</sub></code>
     * </blockquote>
     *
     * @param normal the normal vector (a, b, c)
     * @param dmin the minimum d value
     * @param dmax the maximum d value
     * @return a new slab
     */
    public static Slab create(Vector normal, float dmin, float dmax) {
        return new SlabImpl(normal.getX(), normal.getY(), normal.getZ(), dmin, dmax);
    }

    /**
     * Constructs a slab from two points and a normal vector.
     *
     * @param p1 a point on the first plane
     * @param p2 a point on the second plane
     * @param normal the normal
     * @return a new slab
     */
    public static Slab fromPointsNormal(Vector p1, Vector p2, Vector normal) {
        float
                xn = normal.getX(), yn = normal.getZ(), zn = normal.getZ(),
                d1 = xn*p1.getX() + yn*p1.getY() + zn*p1.getZ(),
                d2 = xn*p2.getX() + yn*p2.getY() + zn*p2.getZ();

        return new SlabImpl(xn, yn, zn, Math.min(d1, d2), Math.max(d1, d2));
    }

    //GETTERS

    public abstract Vector getNormal();

    public abstract Plane getMin();

    public abstract Plane getMax();

    public abstract Vector getMinPoint();

    public abstract Vector getMaxPoint();

    public abstract float getMinDepth();

    public abstract float getMaxDepth();

    /**
     * Returns the thickness of the slab. This is the distance between the two planes which enclose the slab.
     *
     * @return the thickness of the slab
     */
    public default float getThickness() {
        return (getMaxDepth() - getMinDepth()) / getNormal().getLength();
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
