package net.grian.spatium.geo3;

import net.grian.spatium.util.Spatium;
import org.junit.Test;

import static org.junit.Assert.*;

public class AxisAlignedBBTest {
    
    @Test
    public void get_basics() {
        AxisAlignedBB box = AxisAlignedBB.fromPoints(-1, -1, -1, 0, 1, 2);
        assertEquals(Vector3.fromXYZ(-0.5, 0, 0.5), box.getCenter());
        assertEquals(1, box.getSizeX(), Spatium.EPSILON);
        assertEquals(2, box.getSizeY(), Spatium.EPSILON);
        assertEquals(3, box.getSizeZ(), Spatium.EPSILON);
        assertEquals(Vector3.fromXYZ(-1, -1, -1), box.getMin());
        assertEquals(Vector3.fromXYZ(0, 1, 2), box.getMax());
        assertEquals(6, box.getVolume(), Spatium.EPSILON);
    }
    
    
}
