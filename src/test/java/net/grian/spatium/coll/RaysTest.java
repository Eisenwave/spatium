package net.grian.spatium.coll;

import net.grian.spatium.geo3.AxisAlignedBB;
import net.grian.spatium.geo3.Ray3;
import net.grian.spatium.geo3.Sphere;
import org.junit.Test;

import java.util.Arrays;

public class RaysTest {

    @Test
    public void Ray_Sphere() throws Exception {
        Sphere sphere = Sphere.fromCenterRadius(0, 0, 0, 10);

        Ray3 ray0 = Ray3.fromOD(-20, 0, 0, 5, 0, 0);
        System.out.println(Arrays.toString(Rays.pierce(ray0, sphere)));
    }

    @Test
    public void Ray_AABB() throws Exception {
        AxisAlignedBB box = AxisAlignedBB.fromPoints(-1, -1, -1, 1, 1, 1);
        Ray3 ray = Ray3.fromOD(-2, 0, 0, 1, 0, 0);


        System.out.println(Arrays.toString(Rays.pierce(ray, box)));
    }

}