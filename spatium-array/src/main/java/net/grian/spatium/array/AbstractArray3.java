package net.grian.spatium.array;

import java.io.Serializable;

public abstract class AbstractArray3 implements Serializable, Cloneable {

    protected final int sizeX, sizeY, sizeZ, length;
    private final int sizeXY;
    
    private static long product(int x, int y, int z) {
        return (long) x * y * z;
    }
    
    /**
     * Constructs an array using three dimensions.
     *
     * @param sizeX the x-size
     * @param sizeY the y-size
     * @param sizeZ the z-size
     */
    protected AbstractArray3(int sizeX, int sizeY, int sizeZ) {
        if (sizeX < 0) throw new NegativeArraySizeException("x: "+sizeX);
        if (sizeY < 0) throw new NegativeArraySizeException("y: "+sizeY);
        if (sizeZ < 0) throw new NegativeArraySizeException("z: "+sizeZ);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
        this.sizeXY = sizeX * sizeY;
        this.length = sizeXY * sizeZ;
        
        if (sizeXY < 0 || length != (long) sizeXY * sizeZ) // overflow
            throw new IllegalArgumentException("array length too large ("+product(sizeX, sizeY, sizeZ)+")");
    }
    
    protected AbstractArray3(AbstractArray3 copyOf) {
        this.sizeX = copyOf.sizeX;
        this.sizeY = copyOf.sizeY;
        this.sizeZ = copyOf.sizeZ;
        this.sizeXY = copyOf.sizeXY;
        this.length = copyOf.length;
    }
    
    /**
     * <p>
     *     Convenience method for obtaining the array index using an x, y, and z coordinate.
     * </p>
     * <p>
     *     This method asserts that the x-coordinate runs the fastest, then the y-coordinate, then the z-coordinate.
     * </p>
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return the index at the given coordinates
     */
    protected int indexOf(int x, int y, int z) {
        return z*sizeXY + y*sizeX + x;
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
        return length;
    }

}
