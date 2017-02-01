package net.grian.spatium.coll;

import net.grian.spatium.geo3.AxisAlignedBB3;
import net.grian.spatium.geo3.Ray3;
import net.grian.spatium.geo3.Sphere;
import net.grian.spatium.geo3.Vector3;
import org.junit.Test;

import static org.junit.Assert.*;

public class CollisionsTest {

    @Test
    public void AABB_AABB() throws Exception {
        assertFalse(Collisions.test(
                AxisAlignedBB3.fromPoints(0, 0, 0, 1, 1, 1),
                AxisAlignedBB3.fromPoints(2, 2, 2, 3, 3, 3)
        ));

        assertTrue(Collisions.test(
                AxisAlignedBB3.fromPoints(0, 0, 0, 1, 1, 1),
                AxisAlignedBB3.fromPoints(0.5F, 0.5F, 0.5F, 1.5F, 1.5F, 1.5F)
        ));
    }

    @Test
    public void Ray_AABB() throws Exception {
        assertFalse(Collisions.test(
                Ray3.fromOD(0, 0, 0, 1, 0, 1),
                AxisAlignedBB3.fromPoints(10, 10, 10, 11, 11, 11)
        ));

        assertTrue(Collisions.test(
                Ray3.fromOD(0, 0, 0, 1, 1, 1),
                AxisAlignedBB3.fromPoints(10, 10, 10, 11, 11, 11)
        ));
    }

    @Test
    public void Ray_Ray() throws Exception {
        assertFalse(Collisions.test(
                Ray3.fromOD(0, 0, 0, 1, 1, 1),
                Ray3.fromOD(1, 0, 0, 0, 0, 1)
        ));

        assertTrue(Collisions.test(
                Ray3.fromOD(1, 0, 0, -1, 0, 0),
                Ray3.fromOD(0, 0, 1, 0, 0, -1)
        ));
    }

    @Test
    public void Ray_Sphere() throws Exception {
        Sphere sphere = Sphere.fromCenterAndRadius(Vector3.fromXYZ(0, 0, 0), 1);

        assertFalse(Collisions.test(
                Ray3.fromOD(-2, 0, 0, 0, 1, 0),
                sphere
        ));

        assertTrue(Collisions.test(
                Ray3.fromOD(-2, 0, 0, 1, 0, 0),
                sphere
        ));
    }


}