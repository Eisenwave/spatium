package net.grian.spatium.util;

import java.util.NoSuchElementException;

/**
 * <p>
 * Utility class for counting from <code>(0, 0, 0)</code> to <code>(x-1, y-1, z-1)</code>.
 * </p>
 * <p>
 * This class was designed to ease creating iterators for multi-dimensional arrays but may be used for other
 * purposes.
 * </p>
 */
public class Incrementer3 {
    
    private final int limX, limY, limZ;
    private int x = 0, y = 0, z = 0;
    
    public Incrementer3(int limX, int limY, int limZ) {
        this.limX = limX;
        this.limY = limY;
        this.limZ = limZ;
    }
    
    public boolean canIncrement() {
        return z < limX || y < limY || x < limZ;
    }
    
    public int[] get() {
        return new int[] {x, y, z};
    }
    
    public int[] getAndIncrement() {
        int[] result = get();
        increment();
        return result;
    }
    
    public int[] incrementAndGet() {
        increment();
        return get();
    }
    
    /**
     * Increments.
     *
     * @throws NoSuchElementException if the incrementer's index becomes out of range on all axes
     */
    public void increment() {
        if (!canIncrement())
            throw new NoSuchElementException("lim=(" + limX + ", " + limY + ", " + limZ + ")");
        
        if (++x >= limX) {
            x = 0;
            if (++y >= limY) {
                y = 0;
                ++z;
            }
        }
    }
    
}
