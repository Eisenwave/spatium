package net.grian.spatium.array;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;

/**
 * <p>
 *     An array of bytes, acting as an array of boolean values.
 * </p>
 * <p>
 *     The array will have have a length of <code>presumed length / {@link Byte#SIZE}</code>.
 * </p>
 * <p>
 *     Due to the overhead caused by binary operations, read and write access is slightly slower than an actual array
 *     of primitive <code>boolean</code> values.
 * </p>
 */
public class BooleanArray extends AbstractArray implements Iterable<Boolean>, Cloneable {
    
    private final byte[] data;
    
    protected final int padding;
    
    public BooleanArray(int length) {
        super(length);
        
        int dataLen = (int) Math.ceil((float) length/Byte.SIZE);
        this.padding = (Byte.SIZE - (length%Byte.SIZE)) % Byte.SIZE;
        this.data = new byte[dataLen];
    }
    
    public BooleanArray(int length, byte[] data) {
        super(length);
        
        int dataLen = (int) Math.ceil((float) length/Byte.SIZE);
    
        if (data.length < dataLen)
            throw new IllegalArgumentException("data must be at least "+dataLen+" long");
        
        this.padding = (Byte.SIZE - (length%Byte.SIZE)) % Byte.SIZE;
        this.data = new byte[dataLen];
    }
    
    public BooleanArray(BooleanArray copyOf) {
        super(copyOf.length);
        
        this.data = Arrays.copyOf(copyOf.data, 0);
        this.padding = copyOf.padding;
    }
    
    public BooleanArray(boolean[] copyOf) {
        this(copyOf.length);
        
        for (int i = 0; i < copyOf.length; i++)
            if (copyOf[i]) enable(i);
    }
    
    /**
     * <p>
     *     Returns the amount of unused bits in the byte array which backs this object.
     * </p>
     * <p>
     *     If the length of this array is a multiple of 8, the padding will be zero.
     * </p>
     *
     * @return the amount of unused bits
     */
    public int getPadding() {
        return padding;
    }
    
    public int getDataLength() {
        return data.length;
    }
    
    public boolean get(int index) {
        return ((data[index/Byte.SIZE] >> (index%Byte.SIZE)) & 1) != 0;
    }
    
    public void set(int index, boolean value) {
        if (value) enable(index);
        else disable(index);
    }
    
    /**
     * Sets the value at a given index to {@code true}.
     *
     * @param index the index
     */
    public void enable(int index) {
        data[index/Byte.SIZE] |= (1 << (index%Byte.SIZE));
    }
    
    /**
     * Sets the value at a given index to {@code false}.
     *
     * @param index the index
     */
    public void disable(int index) {
        data[index/Byte.SIZE] &= ~(1 << (index%Byte.SIZE));
    }
    
    // MISC
    
    @Override
    public BooleanArray clone() {
        return new BooleanArray(this);
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof BooleanArray && equals((BooleanArray) obj);
    }
    
    public boolean equals(BooleanArray array) {
        return Arrays.equals(this.data, array.data);
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName()+
            "{len="+length+
            ",datLen="+data.length+
            ",padding="+padding+"}";
    }
    
    // ITERATION
    
    @NotNull
    @Override
    public BooleanArrayIterator iterator() {
        return new BooleanArrayIterator();
    }
    
    public final class BooleanArrayIterator implements Iterator<Boolean> {
        
        private int index = 0;
        
        private BooleanArrayIterator() {}
        
        @Override
        public boolean hasNext() {
            return index < length;
        }
        
        @Override
        public Boolean next() {
            return get(index++);
        }
        
        public boolean nextBoolean() {
            return get(index++);
        }
        
    }
    
}
