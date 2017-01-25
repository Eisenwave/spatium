package net.grian.spatium.iter;

import net.grian.spatium.geo.Ray;
import net.grian.spatium.geo.Vector;

import java.util.Iterator;

public class IntervalIterator implements Iterator<Vector> {

    private double x, y, z, xi, yi, zi;
    private int i;

    public IntervalIterator(Ray ray, double interval) {
        this.x = ray.getOriginX();
        this.y = ray.getOriginY();
        this.z = ray.getOriginZ();
        this.xi = interval * ray.getDirX();
        this.yi = interval * ray.getDirY();
        this.zi = interval * ray.getDirZ();

        this.i = (int) (ray.getLength() / interval) + 1;
    }

    @Override
    public boolean hasNext() {
        return i >= 0;
    }

    @Override
    public Vector next() {
        Vector result = Vector.fromXYZ(x, y, z);
        i--; x += xi; y += yi; z += zi;
        return result;
    }

}
