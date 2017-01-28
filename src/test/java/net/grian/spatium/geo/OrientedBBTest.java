package net.grian.spatium.geo;

import net.grian.spatium.Spatium;
import org.junit.Test;

import static org.junit.Assert.*;

public class OrientedBBTest {

    @Test
    public void preserveSlabThickness() throws Exception {
        OrientedBB box = OrientedBB.fromAABB(AxisAlignedBB.fromPoints(-1, -1, 1, 1, 1, 3));
        {
            Slab slabX = box.getSlabX(), slabY = box.getSlabY(), slabZ = box.getSlabZ();
            assertEquals(slabX.getThickness(), 2, Spatium.EPSILON);
            assertEquals(slabY.getThickness(), 2, Spatium.EPSILON);
            assertEquals(slabZ.getThickness(), 2, Spatium.EPSILON);
        }
        box.rotateY(Spatium.radians(45));
        {
            Slab slabX = box.getSlabX(), slabY = box.getSlabY(), slabZ = box.getSlabZ();
            assertEquals(slabX.getThickness(), 2, Spatium.EPSILON);
            assertEquals(slabY.getThickness(), 2, Spatium.EPSILON);
            assertEquals(slabZ.getThickness(), 2, Spatium.EPSILON);
        }
    }

}