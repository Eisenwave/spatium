package net.grian.spatium.transform;

import net.grian.spatium.Spatium;
import net.grian.spatium.cache.CacheMath;
import net.grian.spatium.geo3.Vector3;
import org.junit.Test;

import static org.junit.Assert.*;

public class TransformationsTest {

    @Test
    public void quaternionRotate() throws Exception {
        Vector3 axis = Vector3.fromXYZ(1, 0, 1).normalize();
        
        Quaternion
            r0 =   Quaternion.fromRotation(axis, Spatium.radians(0)),
            r90 =  Quaternion.fromRotation(axis, Spatium.radians(90)),
            r180 = Quaternion.fromRotation(axis, Spatium.radians(180)),
            rm90 = Quaternion.fromRotation(axis, Spatium.radians(-90));
        Vector3
            vector = Vector3.fromXYZ(1, 0, 0),
            test0 = Quaternion.product(r0, vector),
            test90 = Quaternion.product(r90, vector),
            test180 = Quaternion.product(r180, vector),
            testm90 = Quaternion.product(rm90, vector);

        assertEquals(Vector3.fromXYZ(1, 0, 0), test0);
        assertEquals(Vector3.fromXYZ(0.5F, CacheMath.HALF_SQRT_2, 0.5F), test90);
        assertEquals(Vector3.fromXYZ(0, 0, 1), test180);
        assertEquals(Vector3.fromXYZ(0.5F, -CacheMath.HALF_SQRT_2, 0.5F), testm90);
    }

}