package net.grian.spatium.array;

import net.grian.spatium.util.Incrementer2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public class BooleanArray2 extends AbstractArray2 implements Iterable<Boolean>, Cloneable {

    private final byte[] data;
    
    protected final int stride, padding;

    public BooleanArray2(int x, int y) {
        super(x, y);
        this.stride = (int) Math.ceil((float) x/Byte.SIZE);
        this.padding = Byte.SIZE - (x%Byte.SIZE);
        this.data = new byte[stride * y];
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
    
    /**
     * Constructs a new array by copying WBMP byte data.
     *
     * @param x the x-dimensions
     * @param y the y-dimensions
     * @param wbmp the wbmp data
     * @param offset the offset in the wbmp array
     */
    public BooleanArray2(int x, int y, byte[] wbmp, int offset) {
        this(x, y);
        System.arraycopy(data, 0, wbmp, offset, data.length);
    }
    
    @Override
    protected int indexOf(int x, int y) {
        return x / Byte.SIZE + (y * stride);
    }
    
    public boolean get(int x, int y) {
        return (data[indexOf(x,y)] & (0b10000000 >>> (x%Byte.SIZE))) != 0;
    }

    public void set(int x, int y, boolean value) {
        if (value) enable(x, y);
        else disable(x, y);
    }

    public void enable(int x, int y) {
         data[indexOf(x,y)] |= 0b10000000 >>> (x%Byte.SIZE);
    }

    public void disable(int x, int y) {
        data[indexOf(x,y)] &= ~(0b10000000 >>> (x%Byte.SIZE));
    }
    
    public int getStride() {
        return stride;
    }
    
    public int getPadding() {
        return padding;
    }
    
    // MISC
    
    @Override
    public BooleanArray2 clone()  {
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
    public byte[] toWBMP(@Nullable byte[] target, int offset) {
        if (target == null) target = new byte[this.data.length];
        System.arraycopy(this.data, 0, target, offset, this.data.length);
        
        return target;
    }
    
    // ITERATION
    
    @NotNull
    @Override
    public BooleanArrayIterator2 iterator() {
        return new BooleanArrayIterator2();
    }

    private final class BooleanArrayIterator2 implements Iterator<Boolean> {

        private final Incrementer2 incrementer = new Incrementer2(sizeX, sizeY);

        private BooleanArrayIterator2() {}

        @Override
        public boolean hasNext() {
            return incrementer.canIncrement();
        }

        @Override
        public Boolean next() {
            return nextBoolean();
        }

        public boolean nextBoolean() {
            int[] pos = incrementer.getAndIncrement();
            return get(pos[0], pos[1]);
        }

    }
    
    // UTIL
    
    @NotNull
    public static String toString(byte[] bytes, ByteStringer stringer) {
        Objects.requireNonNull(stringer);
        if (bytes.length==0) return "[]";
        
        StringBuilder builder = new StringBuilder("[");
        for (int i = 1; i < bytes.length; i++) {
            builder
                .append(stringer.accept(bytes[i-1]))
                .append(", ");
        }
        return builder
            .append(Integer.toHexString(bytes[bytes.length-1]&0xFF))
            .append("]")
            .toString();
    }
    
    @NotNull
    public static String toString(byte[] bytes, int radix, boolean upperCase) {
        if (radix < 2) throw new IllegalArgumentException("radix < 2");
        
        ByteStringer stringer = upperCase?
            b -> Integer.toString(b&0xFF, radix).toUpperCase() :
            b -> Integer.toString(b&0xFF, radix);
        
        return toString(bytes, stringer);
    }
    
    @NotNull
    public static String toHexString(byte[] bytes) {
        return toString(bytes, b -> Integer.toHexString(b&0xFF));
    }
    
    @NotNull
    public static String toBinaryString(byte[] bytes) {
        return toString(bytes, b -> Integer.toBinaryString(b&0xFF));
    }
    
    @FunctionalInterface
    public static interface ByteStringer {
        String accept(byte b);
    }

}
