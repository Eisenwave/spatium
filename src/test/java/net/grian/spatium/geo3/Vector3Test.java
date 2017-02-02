package net.grian.spatium.geo3;

import net.grian.spatium.Spatium;
import net.grian.spatium.cache.CacheMath;
import net.grian.spatium.geo3.Vector3;
import net.grian.spatium.matrix.Matrix;
import net.grian.spatium.util.PrimMath;
import org.junit.Test;

import static org.junit.Assert.*;

public class Vector3Test {

    @Test
    public void random() throws Exception {
        for (int i = 0; i<10000; i++) {
            double length = PrimMath.randomDouble(1000);
            Vector3 random = Vector3.random(length);
            assertEquals(random.getLength(), length, Spatium.EPSILON);
        }
    }

    @Test
    public void transformScale() throws Exception {
        Vector3 vector = Vector3.fromXYZ(1, 2, 3);
        Matrix transform = Matrix.fromScale(3, 2, 1);

        assertEquals(Vector3.fromXYZ(3, 4, 3), vector.transform(transform));
    }

    private final static Vector3
            POS_X = Vector3.fromXYZ(1, 0, 0),
            POS_Y = Vector3.fromXYZ(0, 1, 0),
            POS_Z = Vector3.fromXYZ(0, 0, 1),
            NEG_X = Vector3.fromXYZ(-1, 0, 0),
            NEG_Y = Vector3.fromXYZ(0, -1, 0),
            NEG_Z = Vector3.fromXYZ(0, 0, -1);

    /**
     * Tests whether X-rotation matrices follow the
     * <a href="https://en.wikipedia.org/wiki/Right-hand_rule">Right Hand Rule</a>.
     *
     * @throws Exception if the test fails
     */
    @Test
    public void transformRotX_followsRightHandRule() throws Exception {
        Vector3 vector = POS_Z.clone();

        Matrix rightHand = Matrix.fromRotX(Spatium.radians(90));
        assertEquals(NEG_Y, vector.transform(rightHand));
        assertEquals(NEG_Z, vector.transform(rightHand));
        assertEquals(POS_Y, vector.transform(rightHand));
        assertEquals(POS_Z, vector.transform(rightHand));

        Matrix leftHand = rightHand.getInverse();
        assertEquals(POS_Y, vector.transform(leftHand));
        assertEquals(NEG_Z, vector.transform(leftHand));
        assertEquals(NEG_Y, vector.transform(leftHand));
        assertEquals(POS_Z, vector.transform(leftHand));
    }

    /**
     * Tests whether Y-rotation matrices follow the
     * <a href="https://en.wikipedia.org/wiki/Right-hand_rule">Right Hand Rule</a>.
     *
     * @throws Exception if the test fails
     */
    @Test
    public void transformRotY_followsRightHandRule() throws Exception {
        Vector3 vector = POS_X.clone();

        Matrix rightHand = Matrix.fromRotY(Spatium.radians(90));
        assertEquals(NEG_Z, vector.transform(rightHand));
        assertEquals(NEG_X, vector.transform(rightHand));
        assertEquals(POS_Z, vector.transform(rightHand));
        assertEquals(POS_X, vector.transform(rightHand));

        Matrix leftHand = rightHand.getInverse();
        assertEquals(POS_Z, vector.transform(leftHand));
        assertEquals(NEG_X, vector.transform(leftHand));
        assertEquals(NEG_Z, vector.transform(leftHand));
        assertEquals(POS_X, vector.transform(leftHand));
    }

    /**
     * Tests whether Z-rotation matrices follow the
     * <a href="https://en.wikipedia.org/wiki/Right-hand_rule">Right Hand Rule</a>.
     *
     * @throws Exception if the test fails
     */
    @Test
    public void transformRotZ_followsRightHandRule() throws Exception {
        Vector3 vector = POS_X.clone();

        Matrix rightHand = Matrix.fromRotZ(Spatium.radians(90));
        assertEquals(POS_Y, vector.transform(rightHand));
        assertEquals(NEG_X, vector.transform(rightHand));
        assertEquals(NEG_Y, vector.transform(rightHand));
        assertEquals(POS_X, vector.transform(rightHand));

        Matrix leftHand = rightHand.getInverse();
        assertEquals(NEG_Y, vector.transform(leftHand));
        assertEquals(NEG_X, vector.transform(leftHand));
        assertEquals(POS_Y, vector.transform(leftHand));
        assertEquals(POS_X, vector.transform(leftHand));
    }
    
    public void performance_Inverse() throws Exception {
        for (int size = 1; size<11; size++) {
            double[] numbers = new double[size * size];
            for (int i = 0; i<numbers.length; i++) {
                numbers[i] = Math.random();
            }
            Matrix matrix = Matrix.create(size, size, numbers);

            long before = System.nanoTime();
            matrix.getInverse();
            long time = (System.nanoTime()-before);
            System.out.println("inverse of "+size+"x"+size+" matrix took "+(time / CacheMath.BILLION)+" s");
        }
    }

}