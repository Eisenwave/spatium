package net.grian.spatium.coll;

import net.grian.spatium.Spatium;
import net.grian.spatium.geo.Ray;
import net.grian.spatium.geo.Vector;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProjectionsTest {

    @Test
    public void pointOnRay() throws Exception {
        Ray ray = Ray.fromOD(0, 0, 0, 10, 0, 10);
        Vector point = Vector.fromXYZ(5, 1, 5);

        assertEquals(0.5, Projections.pointOnRay(ray, point), Spatium.EPSILON);
    }

}