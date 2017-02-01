package net.grian.spatium.geo3;

import net.grian.spatium.Spatium;
import net.grian.spatium.geo3.AxisAlignedBB3;
import net.grian.spatium.geo3.OrientedBB3;
import net.grian.spatium.geo3.Slab3;
import org.junit.Test;

import static org.junit.Assert.*;

public class OrientedBB3Test {

    @Test
    public void preserveSlabThickness() throws Exception {
        OrientedBB3 box = OrientedBB3.fromAABB(AxisAlignedBB3.fromPoints(-1, -1, 1, 1, 1, 3));
        {
            Slab3 slabX = box.getSlabX(), slabY = box.getSlabY(), slabZ = box.getSlabZ();
            assertEquals(slabX.getThickness(), 2, Spatium.EPSILON);
            assertEquals(slabY.getThickness(), 2, Spatium.EPSILON);
            assertEquals(slabZ.getThickness(), 2, Spatium.EPSILON);
        }
        box.rotateY(Spatium.radians(45));
        {
            Slab3 slabX = box.getSlabX(), slabY = box.getSlabY(), slabZ = box.getSlabZ();
            assertEquals(slabX.getThickness(), 2, Spatium.EPSILON);
            assertEquals(slabY.getThickness(), 2, Spatium.EPSILON);
            assertEquals(slabZ.getThickness(), 2, Spatium.EPSILON);
        }
    }

}