package net.grian.spatium.array;

import net.grian.spatium.function.TriIntConsumer;

public abstract class AbstractArray3 {

    protected final int sizeX, sizeY, sizeZ;

    protected AbstractArray3(int sizeX, int sizeY, int sizeZ) {
        if (sizeX < 0) throw new NegativeArraySizeException("x: "+sizeX);
        if (sizeY < 0) throw new NegativeArraySizeException("y: "+sizeY);
        if (sizeZ < 0) throw new NegativeArraySizeException("z: "+sizeZ);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
    }

    /**
     * Returns the size of the array in its first dimension.
     *
     * @return the 1st dimension size of the array
     */
    public int getSizeX() {
        return sizeX;
    }

    /**
     * Returns the size of the array in its second dimension.
     *
     * @return the 2nd dimension size of the array
     */
    public int getSizeY() {
        return sizeY;
    }

    /**
     * Returns the size of the array in its third dimension.
     *
     * @return the 3rd dimension size of the array
     */
    public int getSizeZ() {
        return sizeZ;
    }

    /**
     * Returns the product of all sizes which is equivalent to the total array length.
     *
     * @return the array length
     */
    public int getLength() {
        return sizeX * sizeY * sizeZ;
    }

    /**
     * Performs an operation for every set of coordinates inside this array.
     *
     * @param action the operation to perform
     */
    public void forEachIndex(TriIntConsumer action) {
        for (int x = 0; x<sizeX; x++)
            for (int y = 0; y<sizeY; y++)
                for (int z = 0; z<sizeZ; z++)
                    action.accept(x, y, z);
    }

}
