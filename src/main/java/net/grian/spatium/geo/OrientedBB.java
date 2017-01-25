package net.grian.spatium.geo;

import net.grian.spatium.impl.OrientedBBImpl;

/**
 * An oriented cuboid bounding box.
 */
public interface OrientedBB extends Space {

    /**
     * Constructs a new oriented bounding box.
     *
     * @param center the center of the bounding box
     * @param u the local x-axis (orthonormal basis vector)
     * @param v the local y-axis (orthonormal basis vector)
     * @param w the local z-axis (orthonormal basis vector)
     * @param du the half-size on the u-axis
     * @param dv the half-size on the v-axis
     * @param dw the half-size on the w-axis
     * @return a new oriented bounding box
     */
    public static OrientedBB construct(Vector center, Vector u, Vector v, Vector w, double du, double dv, double dw) {
        return new OrientedBBImpl(center, u, v, w, du, dv, dw);
    }

    //GETTERS

    /**
     * Returns the minimum point of the box after applying rotation.
     *
     * @return the minimum point of the box after applying rotation
     */
    public abstract Vector getMin();

    /**
     * Returns the maximum point of the box after applying rotation.
     *
     * @return the maximum point of the box after applying rotation
     */
    public abstract Vector getMax();

    /**
     * Returns the center of the bounding box
     *
     * @return the center of the bounding box
     */
    public abstract Vector getCenter();

    /**
     * Returns the first of 3 orthonormal basis vectors defining the orientation of the bounding box.
     *
     * @return the first of 3 orthonormal basis vectors
     */
    public abstract Vector getU();

    /**
     * Returns the second of 3 orthonormal basis vectors defining the orientation of the bounding box.
     *
     * @return the second of 3 orthonormal basis vectors
     */
    public abstract Vector getV();

    /**
     * Returns the third of 3 orthonormal basis vectors defining the orientation of the bounding box.
     *
     * @return the third of 3 orthonormal basis vectors
     */
    public abstract Vector getW();

    public abstract Slab getSlabU();

    public abstract Slab getSlabV();

    public abstract Slab getSlabW();

    public abstract double getSizeU();

    public abstract double getSizeV();

    public abstract double getSizeW();

    public default double[] getDimensions() {
        return new double[] {getSizeU(), getSizeV(), getSizeW()};
    }

    @Override
    public default double getVolume() {
        return getSizeU() * getSizeV() * getSizeW();
    }

    @Override
    public default double getSurfaceArea() {
        double x = getSizeU(), y = getSizeV(), z = getSizeW();
        return (x * y + x * z + y * z) * 2;
    }

}
