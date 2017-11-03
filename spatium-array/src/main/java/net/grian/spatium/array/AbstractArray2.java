package net.grian.spatium.array;

import java.io.Serializable;

public class AbstractArray2 implements Serializable, Cloneable {

    protected final int sizeX, sizeY, length;

    protected AbstractArray2(int sizeX, int sizeY) {
        if (sizeX < 0) throw new NegativeArraySizeException("x: "+sizeX);
        if (sizeY < 0) throw new NegativeArraySizeException("y: "+sizeY);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.length = sizeX * sizeY;
    
        if (length < 0)
            throw new IllegalArgumentException("array length too large ("+((long) sizeX * sizeY)+")");
    }
    
    protected AbstractArray2(AbstractArray2 copyOf) {
        this.sizeX = copyOf.sizeX;
        this.sizeY = copyOf.sizeY;
        this.length = copyOf.length;
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
     * Returns the product of all sizes which is equivalent to the total array length.
     *
     * @return the array length
     */
    public int getLength() {
        return length;
    }

}
