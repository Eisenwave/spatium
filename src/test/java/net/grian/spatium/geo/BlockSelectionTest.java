package net.grian.spatium.geo;

import net.grian.spatium.util.PrimMath;
import org.junit.Test;

import static org.junit.Assert.*;

public class BlockSelectionTest {

    @Test
    public void fromPoints() throws Exception {
        BlockSelection box = BlockSelection.fromPoints(1, 2, 3, 11, 12, 13);

        assertEquals(1, box.getMinX());
        assertEquals(2, box.getMinY());
        assertEquals(3, box.getMinZ());
        assertEquals(11, box.getMaxX());
        assertEquals(12, box.getMaxY());
        assertEquals(13, box.getMaxZ());
    }

    @Test
    public void getBlockCount() throws Exception {
        for (int i = 0; i<10; i++) {
            int x = PrimMath.randomInt(100), y = PrimMath.randomInt(100), z = PrimMath.randomInt(100);
            BlockSelection box = BlockSelection.fromPoints(0, 0, 0, x, y, z);
            assertEquals(box.getBlockCount(), (x+1)*(y+1)*(z+1));
        }
    }

    @Test
    public void preserveBoundingBoxVolume() throws Exception {
        BlockSelection selection = BlockSelection.fromPoints(1, 2, 3, 4, 5, 6);

        assertEquals(selection.getVolume(), selection.toBoundingBox().getVolume(), 0);
    }

    @Test
    public void toBoundingBox() throws Exception {
        BlockSelection selection = BlockSelection.fromPoints(1, 2, 3, 4, 5, 6);

        assertEquals(AxisAlignedBB.fromPoints(1, 2, 3, 5, 6, 7), selection.toBoundingBox());
    }

    @Test
    public void equals() throws Exception {
        BlockSelection box = BlockSelection.fromPoints(1, 2, 3, 11, 12, 13);
        BlockSelection copy = BlockSelection.between(box.getMin(), box.getMax());

        assertEquals(box, box);
        assertEquals(box, copy);
    }

    @Test
    public void containsVector() throws Exception {
        BlockSelection box = BlockSelection.fromPoints(1, 2, 3, 11, 12, 13);

        assertTrue(box.contains(2, 3, 4));
        assertFalse(box.contains(0, 0, 0));
    }

    @Test
    public void containsBox() throws Exception {
        BlockSelection box = BlockSelection.fromPoints(1, 2, 3, 11, 12, 13);

        assertTrue(box.contains(box));
        assertTrue(box.contains(BlockSelection.fromPoints(2, 3, 4, 10, 11, 12)));

        assertFalse(box.contains(BlockSelection.fromPoints(0, 0, 0, 11, 12, 13)));
        assertFalse(box.contains(BlockSelection.fromPoints(0, 2, 3, 10, 12, 13)));
    }

}