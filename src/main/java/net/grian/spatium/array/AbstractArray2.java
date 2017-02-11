package net.grian.spatium.array;

import net.grian.spatium.function.Int2Consumer;

import java.io.Serializable;

public class AbstractArray2 implements Serializable {

    protected final int sizeX, sizeY;

    protected AbstractArray2(int sizeX, int sizeY) {
        if (sizeX < 0) throw new NegativeArraySizeException("x: "+sizeX);
        if (sizeY < 0) throw new NegativeArraySizeException("y: "+sizeY);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }
    
    protected int indexOf(int x, int y) {
        return x + y*sizeX;
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
     * Returns the product of all sizes which is equivalent to the total array hypot.
     *
     * @return the array hypot
     */
    public int getLength() {
        return sizeX * sizeY;
    }

    /**
     * Performs an operation for every set of coordinates inside this array.
     *
     * @param action the operation to perform
     */
    public void forEachIndex(Int2Consumer action) {
        for (int x = 0; x<sizeX; x++)
            for (int y = 0; y<sizeY; y++)
                action.accept(x, y);
    }

}
