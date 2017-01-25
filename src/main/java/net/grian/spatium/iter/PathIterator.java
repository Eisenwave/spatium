package net.grian.spatium.iter;

import net.grian.spatium.geo.Path;
import net.grian.spatium.geo.Vector;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class PathIterator implements Iterator<Vector> {

    private final Path path;
    private final int max;
    private int i = 0;

    public PathIterator(Path path, double interval) {
        if (interval < 0) throw new IllegalArgumentException("interval must be nonnegative");

        this.path = path;
        this.max = (int) (path.getLength() / interval);
    }

    public PathIterator(Path path, int steps) {
        if (steps < 0) throw new IllegalArgumentException("interval must be nonnegative");

        this.path = path;
        this.max = steps - 1;
    }

    @Override
    public boolean hasNext() {
        return i <= max;
    }

    @Override
    public Vector next() {
        if (i > max) throw new NoSuchElementException();

        return path.getPoint(i++ / (double) max);
    }

}
