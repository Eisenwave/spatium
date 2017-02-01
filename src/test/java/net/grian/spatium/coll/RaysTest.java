package net.grian.spatium.coll;

import net.grian.spatium.geo3.AxisAlignedBB3;
import net.grian.spatium.geo3.Ray3;
import net.grian.spatium.geo3.Sphere;
import org.junit.Test;

import java.util.Arrays;

public class RaysTest {

    @Test
    public void Ray_Sphere() {
        Sphere sphere = Sphere.fromCenterAndRadius(0, 0, 0, 10);

        Ray3 ray0 = Ray3.fromOD(-20, 0, 0, 5, 0, 0);
        System.out.println(Arrays.toString(Rays.pierce(ray0, sphere)));
    }

    @Test
    public void Ray_AABB() {
        AxisAlignedBB3 box = AxisAlignedBB3.fromPoints(-1, -1, -1, 1, 1, 1);
        Ray3 ray = Ray3.fromOD(-2, 0, 0, 1, 0, 0);


        System.out.println(Arrays.toString(Rays.pierce(ray, box)));
    }

}