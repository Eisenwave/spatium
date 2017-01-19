package net.grian.spatium.iter;

import net.grian.spatium.geo.BlockVector;
import net.grian.spatium.geo.Ray;
import net.grian.spatium.util.PrimMath;

import java.util.Iterator;

public class BlockIntervalIterator implements Iterator<BlockVector> {

    private float x, y, z, xi, yi, zi;
    private int i;

    public BlockIntervalIterator(Ray ray) {
        final float dx = ray.getDirX(), dy = ray.getDirY(), dz = ray.getDirZ(), length = ray.getLength();
        double diagonality = diagonality(dx, dy, dz);
        float t = (float) (diagonality / length);

        this.x = ray.getOriginX();
        this.y = ray.getOriginY();
        this.z = ray.getOriginZ();
        this.xi = t * dx;
        this.yi = t * dy;
        this.zi = t * dz;

        this.i = (int) (ray.getLength() / diagonality) + 1;
    }

    @Override
    public boolean hasNext() {
        return i >= 0;
    }

    @Override
    public BlockVector next() {
        BlockVector result = BlockVector.fromXYZ(x, y, z);
        i--; x += xi; y += yi; z += zi;
        return result;
    }

    private static double diagonality(float x, float y, float z) {
        float t = 1 / absmax(x, y, z);
        return Math.sqrt(t*t * (x*x + y+y + z*z));
    }

    private static double diagonality(float x, float y) {
        float t = 1 / absmax(x, y);
        return Math.sqrt(t*t * (x*x + y+y));
    }

    private static float absmax(float a, float b) {
        return PrimMath.max(Math.abs(a), Math.abs(b));
    }

    private static float absmax(float a, float b, float c) {
        return absmax(a, absmax(b, c));
    }

    private static float absmin(float a, float b) {
        return PrimMath.min(Math.abs(a), Math.abs(b));
    }

    private static float absmin(float a, float b, float c) {
        return absmin(a, absmin(b, c));
    }

}
