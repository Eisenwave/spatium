package net.grian.spatium.coll;

import net.grian.spatium.Spatium;
import net.grian.spatium.geo2.Triangle2;
import net.grian.spatium.geo3.Plane;
import net.grian.spatium.geo3.Ray3;
import net.grian.spatium.geo3.Triangle3;
import net.grian.spatium.geo3.Vector3;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProjectionsTest {
    
    @Test
    public void pointOnRay() throws Exception {
        Ray3 ray = Ray3.fromOD(0, 0, 0, 10, 0, 10);
        Vector3 point = Vector3.fromXYZ(5, 1, 5);

        assertEquals(0.5, Projections.pointOnRay(ray, point), Spatium.EPSILON);
    }
    
    @Test
    public void pointOnPlane() throws Exception {
        Vector3 point = Vector3.fromXYZ(8, 8, 8);
        Plane plane = Plane.fromPointNormal(1, 1, 1, 5, 5, 5);
        
        assertEquals(Vector3.fromXYZ(1, 1, 1), Projections.pointOnPlane(plane, point));
    }
    
    @Test
    public void flattenTriangle_preservesShapeAndSize() throws Exception {
        Triangle3 triangle3 = Triangle3.fromPoints(
            1, 0, 0,
            0, 1, 0,
            0, 0, 1);
        Triangle2 triangle2 = Projections.flatten(triangle3);
    
        assertEquals(triangle3.getLengthAB(), triangle2.getLengthAB(), Spatium.EPSILON);
        assertEquals(triangle3.getLengthBC(), triangle2.getLengthBC(), Spatium.EPSILON);
        assertEquals(triangle3.getLengthCA(), triangle2.getLengthCA(), Spatium.EPSILON);
        assertEquals(triangle3.getArea(), triangle2.getArea(), Spatium.EPSILON);
    }

}