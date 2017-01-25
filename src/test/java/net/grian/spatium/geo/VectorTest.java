package net.grian.spatium.geo;

import net.grian.spatium.Spatium;
import net.grian.spatium.util.PrimMath;
import org.junit.Test;

import static org.junit.Assert.*;

public class VectorTest {

    @Test
    public void random() throws Exception {
        for (int i = 0; i<10000; i++) {
            double length = PrimMath.randomDouble(1000);
            Vector random = Vector.random(length);
            assertEquals(random.getLength(), length, Spatium.EPSILON);
        }
    }

}