package net.grian.spatium.transform;

import net.grian.spatium.Spatium;
import net.grian.spatium.cache.CacheMath;
import net.grian.spatium.geo.Vector;
import org.junit.Test;

import static org.junit.Assert.*;

public class TransformationsTest {

    @Test
    public void quaternionRotate() throws Exception {
        final Vector axis = Vector.fromXYZ(1, 0, 1).normalize();
        final Quaternion
                r0 = Quaternion.fromRotation(axis, Spatium.radians(0)),
                r90 = Quaternion.fromRotation(axis, Spatium.radians(90)),
                r180 = Quaternion.fromRotation(axis, Spatium.radians(180)),
                rm90 = Quaternion.fromRotation(axis, Spatium.radians(-90));
        Vector
                vector = Vector.fromXYZ(1, 0, 0),
                test0 = vector.clone(),
                test90 = vector.clone(),
                test180 = vector.clone(),
                testm90 = vector.clone();

        Transformations.rotate(test0, r0);
        Transformations.rotate(test90, r90);
        Transformations.rotate(test180, r180);
        Transformations.rotate(testm90, rm90);

        assertEquals(Vector.fromXYZ(1, 0, 0), test0);
        assertEquals(Vector.fromXYZ(0.5F, CacheMath.HALF_SQRT_2, 0.5F), test90);
        assertEquals(Vector.fromXYZ(0, 0, 1), test180);
        assertEquals(Vector.fromXYZ(0.5F, -CacheMath.HALF_SQRT_2, 0.5F), testm90);

        /*
        for (int i = 0; i<100; i++) {
            Vector random = Vector.random(1);

            Vector expected = random.clone(), actual = random.clone();
            expected.setYaw(expected.getYaw()+90);
            Transformations.rotate(actual, yaw90);
            assertEquals(expected, actual);
        }
        */
    }

    @Test
    public void quaternionRotateYaw() throws Exception {
        final Quaternion yaw90 = Quaternion.fromYawPitchRoll(90, 0, 0);

        Vector vector = Vector.fromXYZ(1, 0, 0);
        Transformations.rotate(vector, yaw90);
        assertEquals(Vector.fromXYZ(0, 0, 1), vector);

        /*
        for (int i = 0; i<100; i++) {
            Vector random = Vector.random(1);

            Vector expected = random.clone(), actual = random.clone();
            expected.setYaw(expected.getYaw()+90);
            Transformations.rotate(actual, yaw90);
            assertEquals(expected, actual);
        }
        */
    }

    @Test
    public void quaternionRotatePitch() throws Exception {
        final Quaternion pitch90 = Quaternion.fromYawPitchRoll(0, 90, 0);

        Vector vector = Vector.fromXYZ(0, 0, 1);
        Transformations.rotate(vector, pitch90);
        assertEquals(Vector.fromXYZ(0, -1, 0), vector);

        /*
        for (int i = 0; i<100; i++) {
            Vector random = Vector.random(1);

            Vector expected = random.clone(), actual = random.clone();
            expected.setPitch(expected.getPitch()+90);
            Transformations.rotate(actual, pitch90);
            assertEquals(expected, actual);
        }
        */
    }

}