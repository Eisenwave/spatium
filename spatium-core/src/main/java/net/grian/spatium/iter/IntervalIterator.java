package net.grian.spatium.iter;

import net.grian.spatium.geo3.Ray3;
import net.grian.spatium.geo3.Vector3;

import java.util.Iterator;

public class IntervalIterator implements Iterator<Vector3> {

    private double x, y, z, xi, yi, zi;
    private int i;

    public IntervalIterator(Ray3 ray, double interval) {
        this.x = ray.getOrgX();
        this.y = ray.getOrgY();
        this.z = ray.getOrgZ();
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
    public Vector3 next() {
        Vector3 result = Vector3.fromXYZ(x, y, z);
        i--; x += xi; y += yi; z += zi;
        return result;
    }

}
