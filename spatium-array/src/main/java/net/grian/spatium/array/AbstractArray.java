package net.grian.spatium.array;

import java.io.Serializable;

public class AbstractArray implements Serializable, Cloneable {
    
    protected final int length;
    
    protected AbstractArray(int length) {
        if (length < 0) throw new NegativeArraySizeException(Integer.toString(length));
        this.length = length;
    }
    
    protected AbstractArray(AbstractArray copyOf) {
        this.length = copyOf.length;
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
