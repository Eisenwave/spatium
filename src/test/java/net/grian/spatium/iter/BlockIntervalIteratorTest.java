package net.grian.spatium.iter;

import net.grian.spatium.geo.BlockVector;
import net.grian.spatium.geo.Ray;
import org.junit.Test;

import java.util.Iterator;

public class BlockIntervalIteratorTest {
    @Test
    public void next() throws Exception {
        Ray ray = Ray.fromOD(1.5F, 2.5F, 3.5F, 10, 20, 30);
        Iterator<BlockVector> iter = ray.blockIntervalIterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }

}