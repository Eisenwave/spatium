package net.grian.spatium.geo3;

import net.grian.spatium.util.Spatium;
import org.junit.Test;

import static org.junit.Assert.*;

public class TetrahedronTest {
    
    @Test
    public void getVolume() throws Exception {
        Tetrahedron t = Tetrahedron.fromPoints(0,0,0, 0,1,0, 0,0,1, 2,0,0);
        assertEquals(1/3D, t.getVolume(), Spatium.EPSILON);
    }
    
}