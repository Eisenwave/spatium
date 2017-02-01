package net.grian.spatium.transform;

import net.grian.spatium.Spatium;
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
            x = POSITIVE_X.vector(),
            y = POSITIVE_Y.vector(),
            z = POSITIVE_Z.vector();
        
        Transformations.rotate(x, y90);
        Transformations.rotate(y, y90);
        Transformations.rotate(z, y90);
        assertEquals(NEGATIVE_Z.vector(), x);
        assertEquals(POSITIVE_Y.vector(), y);
        assertEquals(POSITIVE_X.vector(), z);
        
        Transformations.rotate(x, y90);
        assertEquals(NEGATIVE_X.vector(), x);
    
        Transformations.rotate(x, y90);
        assertEquals(POSITIVE_Z.vector(), x);
    }
    
}