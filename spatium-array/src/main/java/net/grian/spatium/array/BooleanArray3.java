package net.grian.spatium.array;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class BooleanArray3 extends AbstractArray3 implements Iterable<Boolean>, Cloneable {

    private final BooleanArray data;

    public BooleanArray3(int x, int y, int z) {
        super(x, y, z);
        
        this.data = new BooleanArray(x * y * z);
    }
    
    public BooleanArray3(BooleanArray3 copyOf) {
        super(copyOf.sizeX, copyOf.sizeY, copyOf.sizeZ);
        
        this.data = copyOf.data.clone();
    }

    /*
    public BooleanArray3(BitArray3 copyOf) {
        this(copyOf.getSizeX(), copyOf.getSizeY(), copyOf.getSizeZ());

        for (int x = 0; x<sizeX; x++) for (int y = 0; y<sizeY; y++) for (int z = 0; z<sizeZ; z++)
            if (copyOf.contains(x, y, z)) enable(x, y, z);
    }
    */
    
    // GETTERS
    
    /* public boolean contains(int x, int y, int z) {
        return get(x, y, z);
    } */

    public boolean get(int x, int y, int z) {
        return data.get(indexOf(x, y, z));
    }
    
    public int getPadding() {
        return data.getPadding();
    }
    
    // SETTERS

    public void set(int x, int y, int z, boolean value) {
        if (value) enable(x, y, z);
        else disable(x, y, z);
    }

    public void enable(int x, int y, int z) {
        data.enable(indexOf(x, y, z));
    }

    public void disable(int x, int y, int z) {
        data.disable(indexOf(x, y, z));
    }

    // MISC
    
    @Override
    public BooleanArray3 clone() {
        return new BooleanArray3(this);
    }
    
    @Override
    public String toString() {
        return getClass().toString()+
            "{x="+getSizeX()+
            ",y="+getSizeY()+
            ",z="+getSizeZ()+
            ",data="+data+
            "}";
    }
    
    // ITERATION
    
    @NotNull
    @Override
    public BooleanArrayIterator3 iterator() {
        return new BooleanArrayIterator3();
    }
    
    public final class BooleanArrayIterator3 implements Iterator<Boolean> {

        private final BooleanArray.BooleanArrayIterator handle = data.iterator();

        private BooleanArrayIterator3() {}

        @Override
        public boolean hasNext() {
            return handle.hasNext();
        }

        @Override
        public Boolean next() {
            return handle.next();
        }

        public boolean nextBoolean() {
            return handle.nextBoolean();
        }

    }

}
