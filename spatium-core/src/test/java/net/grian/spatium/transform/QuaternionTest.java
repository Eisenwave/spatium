package net.grian.spatium.transform;

import net.grian.spatium.util.Spatium;
import net.grian.spatium.geo3.Vector3;
import org.junit.Test;

import static org.junit.Assert.*;

import static net.grian.spatium.enums.Direction.*;

public class QuaternionTest {
    
    @SuppressWarnings("deprecation")
    @Test
    public void fromRotation_followsRHRule() throws Exception {
        Quaternion y90 = Quaternion.fromRotation(0, 1, 0, Spatium.radians(90));
    
        Vector3
            x = Vector3.fromDir(POSITIVE_X),
            y = Vector3.fromDir(POSITIVE_Y),
            z = Vector3.fromDir(POSITIVE_Z);
        
        Transformations.rotate(x, y90);
        Transformations.rotate(y, y90);
        Transformations.rotate(z, y90);
        assertEquals(Vector3.fromDir(NEGATIVE_Z), x);
        assertEquals(Vector3.fromDir(POSITIVE_Y), y);
        assertEquals(Vector3.fromDir(POSITIVE_X), z);
        
        Transformations.rotate(x, y90);
        assertEquals(Vector3.fromDir(NEGATIVE_X), x);
    
        Transformations.rotate(x, y90);
        assertEquals(Vector3.fromDir(POSITIVE_Z), x);
    }
    
}