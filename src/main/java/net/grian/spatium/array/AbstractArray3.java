package net.grian.spatium.array;

import net.grian.spatium.function.Int3Consumer;

import java.io.Serializable;

public abstract class AbstractArray3 implements Serializable {

    protected final int sizeX, sizeY, sizeZ;

    protected AbstractArray3(int sizeX, int sizeY, int sizeZ) {
        if (sizeX < 0) throw new NegativeArraySizeException("x: "+sizeX);
        if (sizeY < 0) throw new NegativeArraySizeException("y: "+sizeY);
        if (sizeZ < 0) throw new NegativeArraySizeException("z: "+sizeZ);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
    }
    
    protected int indexOf(int x, int y, int z) {
        return z*sizeX*sizeY + y*sizeX + x;
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
     * Returns the product of all sizes which is equivalent to the total array hypot.
     *
     * @return the array hypot
     */
    public int getLength() {
        return sizeX * sizeY * sizeZ;
    }

    /**
     * Performs an operation for every set of coordinates inside this array.
     *
     * @param action the operation to perform
     */
    public void forEachIndex(Int3Consumer action) {
        for (int x = 0; x<sizeX; x++)
            for (int y = 0; y<sizeY; y++)
                for (int z = 0; z<sizeZ; z++)
                    action.accept(x, y, z);
    }

}
