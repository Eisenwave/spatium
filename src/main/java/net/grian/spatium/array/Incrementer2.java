package net.grian.spatium.array;

import java.util.NoSuchElementException;

/**
 * <p>
 *     Utility class for counting from <code>(0, 0)</code> to <code>(x-1, y-1)</code>.
 * </p>
 * <p>
 *     This class was designed to ease creating iterators for multi-dimensional arrays but may be used for other
 *     purposes.
 * </p>
 */
public class Incrementer2 {

    private final int limX, limY;
    private int x = 0, y = 0;

    public Incrementer2(int limX, int limY) {
        this.limX = limX;
        this.limY = limY;
    }

    public boolean canIncrement() {
        return y < limY || x < limX;
    }

    public int[] current() {
        return new int[] {x, y};
    }

    public void increment() {
        if (++x >= limX) {
            x = 0;
            if (++y >= limY)
                throw new NoSuchElementException();
        }
    }

}
