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
    public static Slab create(double a, double b, double c, double dmin, double dmax) {
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
    public static Slab create(Vector normal, double dmin, double dmax) {
        return new SlabImpl(normal.getX(), normal.getY(), normal.getZ(), dmin, dmax);
    }

    /**
     * Constructs a slab from two points and a normal vector.
     *
     * @param a a point on the first plane
     * @param b a point on the second plane
     * @param normal the normal
     * @return a new slab
     */
    public static Slab fromPointsNormal(Vector a, Vector b, Vector normal) {
        double da = normal.dot(a), db = normal.dot(b);

        return new SlabImpl(normal, Math.min(da, db), Math.max(da, db));
    }

    //GETTERS

    /**
     * Returns the normal of this slab, which is the normal of the two parallel planes defined by this slab.
     *
     * @return the slab normal
     */
    public abstract Vector getNormal();

    /**
     * Returns the minimum plane.
     *
     * @return the minimum plane
     */
    public abstract Plane getMin();

    /**
     * Returns the maximum plane.
     *
     * @return the maximum plane
     */
    public abstract Plane getMax();

    /**
     * Returns some point on the minimum plane.
     *
     * @return some point on the minimum plane
     */
    public abstract Vector getMinPoint();

    /**
     * Returns some point on the maximum plane.
     *
     * @return some point on the maximum plane
     */
    public abstract Vector getMaxPoint();

    public abstract double getMinDepth();

    public abstract double getMaxDepth();

    /**
     * Returns the thickness of the slab. This is the distance between the two planes which enclose the slab.
     *
     * @return the thickness of the slab
     */
    public default double getThickness() {
        return (getMaxDepth() - getMinDepth()) / getNormal().getLength();
    }

    //CHECKERS

    public default boolean equals(Slab slab) {
        return
                this.getNormal().isMultipleOf(slab.getNormal()) &&
                Spatium.equals(this.getMinDepth(), slab.getMinDepth()) &&
                Spatium.equals(this.getMaxDepth(), slab.getMaxDepth());
    }

    public default boolean contains(double x, double y, double z) {
        Vector n = getNormal();
        double invLength = 1 / n.getLength();
        double dot = n.dot(x, y, z);

        return
                (dot - getMinDepth()) * invLength >= 0 &&  //signed distance to min plane positive or 0
                (dot - getMaxDepth()) * invLength <= 0;    //signed distance to max plane negative or 0
    }

    public default boolean contains(Vector point) {
        return contains(point.getX(), point.getY(), point.getZ());
    }

    //SETTERS

    /**
     * Sets the normal of the slab to specified coordinates.
     *
     * @param x the x-coordinate of the normal
     * @param y the y-coordinate of the normal
     * @param z the z-coordinate of the normal
     * @return itself
     */
    public abstract Slab setNormal(double x, double y, double z);

    /**
     * Sets the normal of the slab to a specified vector.
     *
     * @param normal the normal vector
     * @return itself
     */
    public default Slab setNormal(Vector normal) {
        return setNormal(normal.getX(), normal.getY(), normal.getZ());
    }

    public abstract Slab setMinDepth(double depth);

    public abstract Slab setMaxDepth(double depth);

    /**
     * Moves the slab into the direction of its normal vector.
     *
     * @param depth the additional depth
     * @return itself
     */
    public abstract Slab push(double depth);

    /**
     * Moves the slab into the opposite direction of its normal vector.
     *
     * @param depth the additional depth
     * @return itself
     */
    public abstract Slab pull(double depth);

}
