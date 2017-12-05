package net.grian.spatium.iter;

import net.grian.spatium.geo3.BlockVector;
import net.grian.spatium.geo3.Ray3;
import eisenwave.spatium.util.PrimMath;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BlockIntervalIterator implements Iterator<BlockVector> {

    private final int dx, dy, dz, ix, iy, iz, dmax;
    private final Runnable increment;

    private int x, y, z, i, err0, err1;
    
    public BlockIntervalIterator(int x0, int y0, int z0, int x1, int y1, int z1) {
        this.dx = Math.abs(x1 - x0);
        this.dy = Math.abs(y1 - y0);
        this.dz = Math.abs(z1 - z0);
        this.dmax = PrimMath.max(dx, dy, dz);
        
        this.err0 = dmax / 2;
        this.err1 = err0;
        
        this.x = x0;
        this.y = y0;
        this.z = z0;
        
        this.ix = x0 < x1? 1 : -1;
        this.iy = y0 < y1? 1 : -1;
        this.iz = z0 < z1? 1 : -1;
        
        if (dx == dmax)      increment = this::incrementLeadX;
        else if (dy == dmax) increment = this::incrementLeadY;
        else                 increment = this::incrementLeadZ;
    }
    
    public BlockIntervalIterator(BlockVector org, BlockVector end) {
        this(org.getX(), org.getY(), org.getZ(), end.getX(), end.getY(), end.getZ());
    }

    public BlockIntervalIterator(Ray3 ray) {
        this(ray.getOrigin().toBlockVector(), ray.getEnd().toBlockVector());
    }

    @Override
    public boolean hasNext() {
        return i <= dmax;
    }

    @Override
    public BlockVector next() {
        if (i++ > dmax) throw new NoSuchElementException();

        BlockVector result = BlockVector.fromXYZ(x, y, z);
        increment.run();
        return result;
    }

    private void incrementLeadX() {
        x += ix;

        err0 -= dy;
        if (err0 < 0) {
            err0 += dx;
            y += iy;
        }

        err1 -= dz;
        if (err1 < 0) {
            err1 += dx;
            z += iz;
        }
    }

    private void incrementLeadY() {
        y += iy;

        err0 -= dx;
        if (err0 < 0) {
            err0 += dy;
            x += ix;
        }

        err1 -= dz;
        if (err1 < 0) {
            err1 += dy;
            z += iz;
        }
    }

    private void incrementLeadZ() {
        z += iz;

        err0 -= dy;
        if (err0 < 0) {
            err0 += dz;
            y += iy;
        }

        err1 -= dx;
        if (err1 < 0) {
            err1 += dz;
            x += ix;
        }
    }

    private BlockVector currentBlock() {
        return BlockVector.fromXYZ(x, y, z);
    }

}
