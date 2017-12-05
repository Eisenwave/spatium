package net.grian.spatium.geo3;

import eisenwave.spatium.util.Spatium;
import eisenwave.spatium.cache.CacheMath;
import net.grian.spatium.matrix.Matrix;
import org.junit.Test;

import static org.junit.Assert.*;

public class Vector3Test {
    
    @Test
    public void isMultipleOf() throws Exception {
        assertTrue(Vector3.fromXYZ(0, 0, 0).isMultipleOf(0, 0, 0));
        assertTrue(Vector3.fromXYZ(1, 1, 1).isMultipleOf(1, 1, 1));
        assertTrue(Vector3.fromXYZ(-42, 0, 0).isMultipleOf(9, 0, 0));
        assertTrue(Vector3.fromXYZ(-4, -5, 0).isMultipleOf(4, 5, 0));
        assertTrue(Vector3.fromXYZ(0, -5, 0).isMultipleOf(0, 100, 0));
        assertTrue(Vector3.fromXYZ(0, -1, -9).isMultipleOf(0, 100, 900));
        assertTrue(Vector3.fromXYZ(0.777, 0, 0.123).isMultipleOf(777, 0, 123));
        
        for (int i = 0; i<100_000; i++) {
            Vector3 v = Vectors.random3(1), v2 = v.clone().multiply(10);
            if (!v.isMultipleOf(v2)) {
                throw new AssertionError(v+" != r*"+v2);
            }
        }
    }
    
    @Test
    public void transformScale() throws Exception {
        Vector3 vector = Vector3.fromXYZ(1, 2, 3);
        Matrix transform = Matrix.fromScale(3, 2, 1);
        vector.transform(transform);
        
        assertEquals(Vector3.fromXYZ(3, 4, 3), vector);
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
    @SuppressWarnings("Duplicates")
    @Test
    public void transformRotX_followsRightHandRule() throws Exception {
        Vector3 vector = POS_Z.clone();

        Matrix rightHand = Matrix.fromRotX(Spatium.radians(90));
        vector.transform(rightHand); assertEquals(NEG_Y, vector);
        vector.transform(rightHand); assertEquals(NEG_Z, vector);
        vector.transform(rightHand); assertEquals(POS_Y, vector);
        vector.transform(rightHand); assertEquals(POS_Z, vector);

        Matrix leftHand = rightHand.getInverse();
        vector.transform(leftHand); assertEquals(POS_Y, vector);
        vector.transform(leftHand); assertEquals(NEG_Z, vector);
        vector.transform(leftHand); assertEquals(NEG_Y, vector);
        vector.transform(leftHand); assertEquals(POS_Z, vector);
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
        vector.transform(rightHand); assertEquals(NEG_Z, vector);
        vector.transform(rightHand); assertEquals(NEG_X, vector);
        vector.transform(rightHand); assertEquals(POS_Z, vector);
        vector.transform(rightHand); assertEquals(POS_X, vector);

        Matrix leftHand = rightHand.getInverse();
        vector.transform(leftHand); assertEquals(POS_Z, vector);
        vector.transform(leftHand); assertEquals(NEG_X, vector);
        vector.transform(leftHand); assertEquals(NEG_Z, vector);
        vector.transform(leftHand); assertEquals(POS_X, vector);
    }

    /**
     * Tests whether Z-rotation matrices follow the
     * <a href="https://en.wikipedia.org/wiki/Right-hand_rule">Right Hand Rule</a>.
     *
     * @throws Exception if the test fails
     */
    @SuppressWarnings("Duplicates")
    @Test
    public void transformRotZ_followsRightHandRule() throws Exception {
        Vector3 vector = POS_X.clone();

        Matrix rightHand = Matrix.fromRotZ(Spatium.radians(90));
        vector.transform(rightHand); assertEquals(POS_Y, vector);
        vector.transform(rightHand); assertEquals(NEG_X, vector);
        vector.transform(rightHand); assertEquals(NEG_Y, vector);
        vector.transform(rightHand); assertEquals(POS_X, vector);

        Matrix leftHand = rightHand.getInverse();
        vector.transform(leftHand); assertEquals(NEG_Y, vector);
        vector.transform(leftHand); assertEquals(NEG_X, vector);
        vector.transform(leftHand); assertEquals(POS_Y, vector);
        vector.transform(leftHand); assertEquals(POS_X, vector);
    }
    
    private final static double
        //weaker epsilon than default in spatium since yaw/pitch get/set leads to massive imprecision
        WEAK_EPSILON = Spatium.EPSILON;
    private final static int
        YAW_PITCH_TESTS = 10_000;
    
    @Test
    public void getSetYaw_equivalency() throws Exception {
        final Vector3 test = Vector3.fromXYZ(1, 1, 1);
        final double
            expLength = test.getLength(),
            expPitch = test.getPitch();
        
        for (int i = 0; i < YAW_PITCH_TESTS; i++) {
            Vector3 v = test.clone();
            double expYaw = Math.random() * 360;
            v.setYaw(expYaw);
            
            double
                actualYaw = (v.getYaw()+360)%360,
                actualPitch = v.getPitch(),
                actualLength = v.getLength();
            
            assertEquals(expYaw, actualYaw, WEAK_EPSILON);
            assertEquals(expPitch, actualPitch, WEAK_EPSILON);
            assertEquals(expLength, actualLength, WEAK_EPSILON);
        }
    }
    
    @Test
    public void getSetPitch_equivalency() throws Exception {
        final Vector3 test = Vector3.fromXYZ(1, 1, 1);
        final double
            expYaw = (test.getYaw()+360)%360,
            expLength = test.getLength();
        
        for (int i = 0; i < YAW_PITCH_TESTS; i++) {
            Vector3 v = test.clone();
            double expPitch = Math.random() * 180 - 90;
            v.setPitch(expPitch);
            
            double
                actualYaw = (v.getYaw()+360)%360,
                actualPitch = v.getPitch(),
                actualLength = v.getLength();
            
            try {
                assertEquals(expYaw, actualYaw, WEAK_EPSILON);
                assertEquals(expPitch, actualPitch, WEAK_EPSILON);
                assertEquals(expLength, actualLength, WEAK_EPSILON);
            } catch (AssertionError ex) {
                System.out.println("i: "+i);
                System.out.println("expYaw: "+expYaw);
                System.out.println("actualYaw: "+actualYaw);
                System.out.println("expPitch: "+expPitch);
                System.out.println("actualPitch: "+actualPitch);
                System.out.println("v: "+v);
                throw ex;
            }
        }
    }
    
    @Test
    public void getYawPitch_singularityHandling() throws Exception {
        Vector3 up = Vector3.fromXYZ(0, 1, 0);
        assertTrue( Double.isFinite(up.getYaw()) );
        assertTrue( Spatium.equals(up.getPitch(), -90) );
        assertEquals(up, up.clone().setYaw(90));
    
        Vector3 down = Vector3.fromXYZ(0, -1, 0);
        assertTrue( Double.isFinite(down.getYaw()) );
        assertTrue( Spatium.equals(down.getPitch(), 90) );
        assertEquals(down, down.clone().setYaw(90));
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
