package net.grian.spatium.geo;


/**
 * An oriented cuboid bounding box.
 */
public interface OrientedBB extends Space {

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

    public abstract float getSizeU();

    public abstract float getSizeV();

    public abstract float getSizeW();

    public default float[] getDimensions() {
        return new float[] {getSizeU(), getSizeV(), getSizeW()};
    }

    @Override
    public default float getVolume() {
        return getSizeU() * getSizeV() * getSizeW();
    }

    @Override
    public default float getSurfaceArea() {
        float x = getSizeU(), y = getSizeV(), z = getSizeW();
        return (x * y + x * z + y * z) * 2;
    }

}
