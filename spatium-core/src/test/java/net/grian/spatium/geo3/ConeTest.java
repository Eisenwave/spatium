package net.grian.spatium.geo3;

import eisenwave.spatium.util.Spatium;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConeTest {
    
    @Test
    public void getAperture() throws Exception {
        Cone cone = Cone.fromApexDirRadius(1, 2, 3, 1, 0, 0, 1);
        assertEquals(2*Spatium.radians(45), cone.getAperture(), Spatium.EPSILON);
    }
    
    @Test
    public void contains() throws Exception {
        Cone cone = Cone.fromApexDirRadius(0, 0, 0, 1, 0, 0, 1);
        assertTrue(cone.contains(0.5, 0, 0));
    }
    
}
