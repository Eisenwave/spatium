package net.grian.spatium.array;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;

/**
 * <p>
 *     An array of nibbles (half-bytes).
 * </p>
 * <p>
 *     This array is called {@link LowNibbleArray} because even indices are mapped onto the low nibble (0x0F) and
 *     odd indices are mapped onto the high nibble (0xF0) of a byte. Hence, the array starts with a low nibble.
 * </p>
 */
public class LowNibbleArray extends AbstractArray implements Iterable<Byte>, Cloneable {
    
    private final byte[] data;
    
    public LowNibbleArray(int length) {
        super(length);
        
        int halfLen = length >> 1,
            extraLen = (length & 1) == 0? 0 : 1;
        
        // make data one byte longer if length is uneven (f.e. 2 bytes for 3 nibbles)
        this.data = new byte[halfLen + extraLen];
    }
    
    public LowNibbleArray(int length, byte[] data) {
        super(length);
        
        int halfLen = length >> 1,
            extraLen = (length & 1) == 0? 0 : 1,
            dataLen = halfLen + extraLen;
        
        if (data.length < dataLen)
            throw new IllegalArgumentException("data must be at least "+dataLen+" long");
        
        this.data = data;
    }
    
    public LowNibbleArray(LowNibbleArray copyOf) {
        super(copyOf);
        this.data = Arrays.copyOf(copyOf.data, copyOf.data.length);
    }
    
    public byte get(int index) {
        byte b = this.data[index >> 1];
        return (byte) ((index & 1) == 0?
            b >> 4 & 0x0F : // even -> high nibble
            b & 0x0F);      // uneven -> low nibble
    }
    
    public void set(int index, byte nibble) {
        this.data[index >> 1] |= (index & 1) == 0?
            nibble << 4 & 0xF0 : // even -> high nibble
            nibble & 0x0F;       // uneven -> low nibble
    }
    
    public int getDataLength() {
        return data.length;
    }
    
    // ITERATION
    
    @NotNull
    @Override
    public LowNibbleArrayIterator iterator() {
        return new LowNibbleArrayIterator();
    }
    
    private final class LowNibbleArrayIterator implements Iterator<Byte> {
        
        private int index = 0;
        
        private LowNibbleArrayIterator() {}
        
        @Override
        public boolean hasNext() {
            return index < data.length;
        }
        
        @Override
        public Byte next() {
            return get(index);
        }
        
        public byte nextInt() {
            return get(index);
        }
        
    }
    
    // MISC
    
    @Override
    public LowNibbleArray clone() {
        return new LowNibbleArray(this);
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof LowNibbleArray && equals((LowNibbleArray) obj);
    }
    
    public boolean equals(LowNibbleArray array) {
        if (this.length != array.length) return false;
        
        for (int i = 0; i < length; i++)
            if (this.get(i) != array.get(i))
                return false;
        
        return true;
    }
    
}
