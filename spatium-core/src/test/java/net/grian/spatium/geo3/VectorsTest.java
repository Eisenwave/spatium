package net.grian.spatium.geo3;

import net.grian.spatium.util.Spatium;
import net.grian.spatium.util.PrimMath;
import org.junit.Test;

import static org.junit.Assert.*;

public class VectorsTest {
    
    @Test
    public void orthogonalize() throws Exception {
        Vector3[] v = {
            Vector3.fromXYZ(1, 1, 1),
            Vector3.fromXYZ(-1, 1, 0),
            Vector3.fromXYZ(1, 2, 1)
        };
        Vectors.orthogonalize(v);
        
        assertEquals(Vector3.fromXYZ( 1, 1, 1), v[0]);
        assertEquals(Vector3.fromXYZ(-1, 1, 0), v[1]);
        assertEquals(Vector3.fromXYZ(1/6D, 1/6D, -1/3D), v[2]);
    }
    
    @Test
    public void random() throws Exception {
        for (int i = 0; i<10000; i++) {
            double length = PrimMath.randomDouble(1000);
            Vector3 random = Vectors.random3(length);
            assertEquals(random.getLength(), length, Spatium.EPSILON);
        }
    }
    
    /*
    @Test
    public void multiples_performance() throws Exception {
        Vector3[] vectors = new Vector3[10_000_000];
        for (int i = 0; i < vectors.length; i++)
            vectors[i] = Vectors.random3(5);
        
        long time1 = TestUtil.millisOf(() -> {
            for (Vector3 v : vectors)
                Vectors.multiples(v, v);
        });
        long time2 = TestUtil.millisOf(() -> {
            for (Vector3 v : vectors)
                Vectors.multiples2(v, v);
        });
        
        System.out.println(time1 + " " + time2);
    }
    */
    
}