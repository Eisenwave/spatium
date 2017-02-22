package net.grian.spatium.iter;

import net.grian.spatium.geo3.BlockVector;
import net.grian.spatium.geo3.Ray3;
import net.grian.spatium.geo3.Vector3;
import net.grian.spatium.geo3.Vectors;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class BlockIntervalIteratorTest {

    @Test
    public void validStartEnd() throws Exception {
        Ray3 ray = randomRay(16, 256);

        Iterator<BlockVector> iterator = ray.blockIntervalIterator();
        BlockVector[] expexted = firstLast(ray);
        BlockVector[] actual = firstLast(iterator);

        assertEquals(expexted[0], actual[0]);
        assertEquals(expexted[1], actual[1]);
    }

    @Test
    public void connectedLine() throws Exception {
        Ray3 ray = randomRay(16, 64);

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
    private static Ray3 randomRay(float min, float max) {
        Vector3 a = Vectors.random(max), b = Vectors.random(max);
        while (a.distanceTo(b) < min) {
            a = Vectors.random(max);
            b = Vectors.random(max);
        }

        return Ray3.between(a, b);
    }

    private static BlockVector[] firstLast(Ray3 ray) {
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