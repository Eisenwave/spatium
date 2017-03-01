package net.grian.spatium.geo3;

import net.grian.spatium.Spatium;
import net.grian.spatium.util.PrimMath;
import org.junit.Test;

import static org.junit.Assert.*;

public class VectorsTest {
    
    @Test
    public void orthogonalize() throws Exception {
        Vector3 v1 = Vector3.fromXYZ(1, 1, 1), v2 = Vector3.fromXYZ(-1, 1, 0), v3 = Vector3.fromXYZ(1, 2, 1);
        Vector3[] result = Vectors.orthogonalize(v1, v2, v3);
        
        assertEquals(Vector3.fromXYZ( 1, 1, 1), result[0]);
        assertEquals(Vector3.fromXYZ(-1, 1, 0), result[1]);
        assertEquals(Vector3.fromXYZ(1/6D, 1/6D, -1/3D), result[2]);
    }
    
    @Test
    public void random() throws Exception {
        for (int i = 0; i<10000; i++) {
            double length = PrimMath.randomDouble(1000);
            Vector3 random = Vectors.random3(length);
            assertEquals(random.getLength(), length, Spatium.EPSILON);
        }
    }
    
}