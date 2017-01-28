package net.grian.spatium.coll;

import net.grian.spatium.geo.AxisAlignedBB;
import net.grian.spatium.geo.Ray;
import net.grian.spatium.geo.Sphere;
import net.grian.spatium.geo.Vector;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class RaysTest {

    @Test
    public void Ray_Sphere() {
        Sphere sphere = Sphere.fromCenterAndRadius(0, 0, 0, 10);

        Ray ray0 = Ray.fromOD(-20, 0, 0, 5, 0, 0);
        System.out.println(Arrays.toString(Rays.pierce(ray0, sphere)));
    }

    @Test
    public void Ray_AABB() {
        AxisAlignedBB box = AxisAlignedBB.fromPoints(-1, -1, -1, 1, 1, 1);
        Ray ray = Ray.fromOD(-2, 0, 0, 1, 0, 0);


        System.out.println(Arrays.toString(Rays.pierce(ray, box)));
    }

}