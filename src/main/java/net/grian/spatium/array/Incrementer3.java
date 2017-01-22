package net.grian.spatium.array;

import java.util.NoSuchElementException;

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

    public int[] current() {
        return new int[] {x, y, z};
    }

    public void increment() {
        if (++x >= limX) {
            x = 0;
            if (++y >= limY) {
                y = 0;
                if (++z >= limZ)
                    throw new NoSuchElementException();
            }
        }
    }

}
