package net.grian.spatium.voxel;

import net.grian.spatium.geo3.BlockVector;

public interface BitArray3 {

    /**
     * Returns the dimension on the x-axis.
     *
     * @return the dimension on the x-axis
     */
    int getSizeX();

    /**
     * Returns the dimension on the y-axis.
     *
     * @return the dimension on the y-axis
     */
    int getSizeY();

    /**
     * Returns the dimension on the z-axis.
     *
     * @return the dimension on the z-axis
     */
    int getSizeZ();

    /**
     * Returns the volume. This is equivalent to the product of all dimensions.
     *
     * @return the volume
     */
    default int getVolume() {
        return getSizeX() * getSizeY() * getSizeZ();
    }

    /**
     * Returns the dimensions on x, y and z axis.
     *
     * @return the dimensions
     */
    default BlockVector getDimensions() {
        return BlockVector.fromXYZ(getSizeX(), getSizeY(), getSizeZ());
    }

    /**
     * Checks whether the field contains an element at the given position.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return whether there is an element
     */
    boolean contains(int x, int y, int z);

    /**
     * Checks whether the field contains an element at the given position.
     *
     * @param pos the position
     * @return whether there is an element
     */
    default boolean contains(BlockVector pos) {
        return contains(pos.getX(), pos.getY(), pos.getZ());
    }

    /**
     * Returns the total amount of elements in this bitmap.
     *
     * @return the total amount of elements in this bitmap
     */
    default int size() {
        final int limX = getSizeX(), limY = getSizeY(), limZ = getSizeZ();
        int count = 0;

        for (int x = 0; x<limX; x++)
            for (int y = 0; y<limY; y++)
                for (int z = 0; z<limZ; z++)
                    if (contains(x, y, z)) count++;

        return count;
    }

}
