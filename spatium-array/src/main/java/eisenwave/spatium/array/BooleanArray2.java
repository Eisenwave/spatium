package eisenwave.spatium.array;

import org.jetbrains.annotations.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class BooleanArray2 extends AbstractArray2 implements Iterable<Boolean>, Cloneable {
    
    private final static long HIGHEST_BIT = 0x8000_0000_0000_0000L;
    private final static int ENTRY_SIZE = Long.SIZE;
    
    private final long[] data;
    
    protected final int stride, padding;
    
    public BooleanArray2(int x, int y) {
        super(x, y);
        this.stride = (int) Math.ceil((float) x / ENTRY_SIZE);
        this.padding = ENTRY_SIZE - (x % ENTRY_SIZE);
        this.data = new long[stride * y];
    }
    
    /**
     * Copy constructor.
     *
     * @param copyOf the copy
     */
    public BooleanArray2(BooleanArray2 copyOf) {
        super(copyOf.sizeX, copyOf.sizeY);
        this.stride = copyOf.stride;
        this.padding = copyOf.padding;
        this.data = Arrays.copyOf(copyOf.data, copyOf.data.length);
    }
    
    // GETTERS
    
    /**
     * Returns the stride of the array.
     * This is the width of each row in bytes of the backing array.
     *
     * @return the stride of the array
     */
    public int getStride() {
        return stride;
    }
    
    /**
     * Returns the padding of the array.
     * This is the amount of bits which represent no data in the backing array.
     *
     * @return the padding of the array
     */
    public int getPadding() {
        return padding;
    }
    
    @Override
    protected int indexOf(int x, int y) {
        return x / ENTRY_SIZE + (y * stride);
    }
    
    public boolean get(int x, int y) {
        return (data[indexOf(x, y)] & (HIGHEST_BIT >>> (x % ENTRY_SIZE))) != 0;
    }
    
    public boolean[] getRow(int y) {
        final int offY = y * stride;
        final boolean[] result = new boolean[sizeX];
        
        //System.err.println("getRow(" + y + ") " + offY + " " + sizeX + "x" + sizeY + " " + stride);
        for (int i = 0; i < stride; i++) {
            int lim = (i == stride - 1)? (ENTRY_SIZE - padding) : ENTRY_SIZE;
            long entry = data[offY + i];
            
            for (int b = 0; b < lim; b++) {
                result[i * ENTRY_SIZE + b] = (entry & (HIGHEST_BIT >>> b)) != 0;
            }
        }
        
        return result;
    }
    
    // SINGLE SETTERS
    
    public void set(int x, int y, boolean value) {
        if (value) enable(x, y);
        else disable(x, y);
    }
    
    public void flip(int x, int y) {
        set(x, y, !get(x, y));
    }
    
    public void enable(int x, int y) {
        data[indexOf(x, y)] |= HIGHEST_BIT >>> (x % ENTRY_SIZE);
    }
    
    public void disable(int x, int y) {
        data[indexOf(x, y)] &= ~(HIGHEST_BIT >>> (x % ENTRY_SIZE));
    }
    
    // OPERATIONS
    
    public void not() {
        for (int i = 0; i < data.length; i++)
            data[i] = ~data[i];
    }
    
    public void xor(BooleanArray2 array) {
        if (this.getSizeX() != array.getSizeX() || this.getSizeY() != array.getSizeY())
            throw new IllegalArgumentException("Can only operate with an array of equal dimensions");
        
        for (int i = 0; i < data.length; i++)
            data[i] ^= array.data[i];
    }
    
    public void and(BooleanArray2 array) {
        if (this.getSizeX() != array.getSizeX() || this.getSizeY() != array.getSizeY())
            throw new IllegalArgumentException("Can only operate with an array of equal dimensions");
        
        for (int i = 0; i < data.length; i++)
            data[i] &= array.data[i];
    }
    
    public void or(BooleanArray2 array) {
        if (this.getSizeX() != array.getSizeX() || this.getSizeY() != array.getSizeY())
            throw new IllegalArgumentException("Can only operate with an array of equal dimensions");
        
        for (int i = 0; i < data.length; i++)
            data[i] |= array.data[i];
    }
    
    // MISC
    
    @Override
    public BooleanArray2 clone() {
        return new BooleanArray2(this);
    }
    
    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }
    
    @Override
    public boolean equals(Object obj) {
        return obj instanceof BooleanArray2 && equals((BooleanArray2) obj);
    }
    
    public boolean equals(BooleanArray2 array) {
        return Arrays.equals(this.data, array.data);
    }
    
    @Override
    public String toString() {
        return toBinaryString(data);
    }
    
    /**
     * Converts the array into the WBMP byte array format.
     *
     * @return the target array or a freshly allocated array
     */
    @Deprecated
    public byte[] toWBMP(@Nullable byte[] target, int offset) {
        throw new UnsupportedOperationException();
        /* if (target == null) target = new byte[this.data.length];
        System.arraycopy(this.data, 0, target, offset, this.data.length);
        
        return target; */
    }
    
    // ITERATION
    
    @NotNull
    @Override
    public BooleanArrayIterator2 iterator() {
        return new BooleanArrayIterator2();
    }
    
    private final class BooleanArrayIterator2 implements Iterator<Boolean> {
        
        private int x = 0, y = 0;
        private boolean hasNext = true;
        
        private BooleanArrayIterator2() {}
        
        @Override
        public boolean hasNext() {
            return hasNext;
        }
        
        @Override
        public Boolean next() {
            return nextBoolean();
        }
        
        public boolean nextBoolean() {
            if (!hasNext)
                throw new NoSuchElementException(x + ", " + y);
            boolean result = get(x, y);
            if (++x >= sizeX) {
                x = 0;
                if (++y >= sizeY)
                    hasNext = false;
            }
            return result;
        }
        
    }
    
    // UTIL
    
    @NotNull
    public static String toString(long[] bytes, Stringer stringer) {
        Objects.requireNonNull(stringer);
        if (bytes.length == 0) return "[]";
        
        StringBuilder builder = new StringBuilder("[");
        for (int i = 1; i < bytes.length; i++) {
            builder
                .append(stringer.accept(bytes[i - 1]))
                .append(", ");
        }
        return builder
            .append(stringer.accept(bytes[bytes.length - 1]))
            .append("]")
            .toString();
    }
    
    @NotNull
    public static String toString(long[] bytes, int radix, boolean upperCase) {
        if (radix < 2) throw new IllegalArgumentException("radix < 2");
    
        Stringer stringer = upperCase?
            b -> Long.toString(b, radix).toUpperCase() :
            b -> Long.toString(b, radix);
        
        return toString(bytes, stringer);
    }
    
    @NotNull
    public static String toHexString(long[] bytes) {
        return toString(bytes, Long::toHexString);
    }
    
    @NotNull
    public static String toBinaryString(long[] bytes) {
        return toString(bytes, Long::toBinaryString);
    }
    
    @FunctionalInterface
    public static interface Stringer {
    
        String accept(long b);
    }
    
}
