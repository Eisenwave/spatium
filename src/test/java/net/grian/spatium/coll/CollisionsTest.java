package net.grian.spatium.coll;

import net.grian.spatium.geo.AxisAlignedBB;
import net.grian.spatium.geo.Ray;
import net.grian.spatium.geo.Sphere;
import net.grian.spatium.geo.Vector;
import org.junit.Test;

import static org.junit.Assert.*;

public class CollisionsTest {

    @Test
    public void testAABB_AABB() throws Exception {
        assertFalse(Collisions.test(
                AxisAlignedBB.fromPoints(0, 0, 0, 1, 1, 1),
                AxisAlignedBB.fromPoints(2, 2, 2, 3, 3, 3)
        ));

        assertTrue(Collisions.test(
                AxisAlignedBB.fromPoints(0, 0, 0, 1, 1, 1),
                AxisAlignedBB.fromPoints(0.5F, 0.5F, 0.5F, 1.5F, 1.5F, 1.5F)
        ));
    }

    @Test
    public void testRay_AABB() throws Exception {
        assertFalse(Collisions.test(
                Ray.fromOD(0, 0, 0, 1, 0, 1),
                AxisAlignedBB.fromPoints(10, 10, 10, 11, 11, 11)
        ));

        assertTrue(Collisions.test(
                Ray.fromOD(0, 0, 0, 1, 1, 1),
                AxisAlignedBB.fromPoints(10, 10, 10, 11, 11, 11)
        ));
    }

    @Test
    public void testRay_Ray() throws Exception {
        assertFalse(Collisions.test(
                Ray.fromOD(0, 0, 0, 1, 1, 1),
                Ray.fromOD(1, 0, 0, 0, 0, 1)
        ));

        assertTrue(Collisions.test(
                Ray.fromOD(1, 0, 0, -1, 0, 0),
                Ray.fromOD(0, 0, 1, 0, 0, -1)
        ));
    }

    //@Test not for now, babe
    public void testRay_Sphere() throws Exception {
        Sphere sphere = Sphere.fromCenterAndRadius(Vector.fromXYZ(0, 0, 0), 1);

        assertFalse(Collisions.test(
                Ray.fromOD(-2, 0, 0, 0, 1, 0),
                sphere
        ));

        assertTrue(Collisions.test(
                Ray.fromOD(-2, 0, 0, 1, 0, 0),
                sphere
        ));
    }


}