package net.grian.spatium.iter;

import net.grian.spatium.geo3.Path3;
import net.grian.spatium.geo3.Vector3;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class PathIterator implements Iterator<Vector3> {

    private final Path3 path;
    private final int max;
    private int i = 0;

    public PathIterator(Path3 path, double interval) {
        if (interval < 0) throw new IllegalArgumentException("interval must be nonnegative");

        this.path = path;
        this.max = (int) (path.getLength() / interval);
    }

    public PathIterator(Path3 path, int steps) {
        if (steps < 0) throw new IllegalArgumentException("interval must be nonnegative");

        this.path = path;
        this.max = steps - 1;
    }

    @Override
    public boolean hasNext() {
        return i <= max;
    }

    @Override
    public Vector3 next() {
        if (i > max) throw new NoSuchElementException();

        return path.getPoint(i++ / (double) max);
    }

}
