package net.grian.spatium.iter;

import net.grian.spatium.geo.BlockVector;
import net.grian.spatium.geo.Ray;
import net.grian.spatium.geo.Vector;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class BlockIntervalIteratorTest {

    @Test
    public void validStartEnd() throws Exception {
        Ray ray = randomRay(16, 256);

        Iterator<BlockVector> iterator = ray.blockIntervalIterator();
        BlockVector[] expexted = firstLast(ray);
        BlockVector[] actual = firstLast(iterator);

        assertEquals(expexted[0], actual[0]);
        assertEquals(expexted[1], actual[1]);
    }

    @Test
    public void connectedLine() throws Exception {
        Ray ray = randomRay(16, 64);

        Iterator<BlockVector> iterator = ray.blockIntervalIterator();
        BlockVector previous = iterator.next();

        while (iterator.hasNext()) {
            BlockVector next = iterator.next();
            try {
                assertTrue(Math.abs(next.getX()-previous.getX()) <= 1);
                assertTrue(Math.abs(next.getY()-previous.getY()) <= 1);
                assertTrue(Math.abs(next.getZ()-previous.getZ()) <= 1);
            } catch (AssertionError error) {
                System.out.println("ERROR AT: "+previous+" -> "+next);
                throw error;
            }
            previous = next;
        }
    }

    /**
     * Generates a pseudo-random ray with minimum and maximum length.
     *
     * @param min the minimum length
     * @param max the maximum length
     * @return a random ray
     */
    private static Ray randomRay(float min, float max) {
        Vector a = Vector.random(max), b = Vector.random(max);
        while (a.distanceTo(b) < min) {
            a = Vector.random(max);
            b = Vector.random(max);
        }

        return Ray.between(a, b);
    }

    private static BlockVector[] firstLast(Ray ray) {
        return new BlockVector[] {
                ray.getOrigin().toBlockVector(),
                ray.getEnd().toBlockVector()
        };
    }

    private static BlockVector[] firstLast(Iterator<BlockVector> iter) {
        assertTrue(iter.hasNext());
        BlockVector first = iter.next();
        assertTrue(iter.hasNext());
        BlockVector last = iter.next();

        while (iter.hasNext())
            last = iter.next();

        return new BlockVector[] {first, last};
    }

}