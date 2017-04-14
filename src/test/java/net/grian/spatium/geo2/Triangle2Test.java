package net.grian.spatium.geo2;

import net.grian.spatium.Spatium;
import net.grian.spatium.geo3.Triangle3;
import org.junit.Test;

import static org.junit.Assert.*;

public class Triangle2Test {
    
    @Test
    public void getArea() throws Exception {
        assertEquals(Triangle2.fromPoints(2, -5.8, 5, -5.8, 2, -1.8).getArea(), 6, Spatium.EPSILON);
        assertEquals(Triangle3.fromPoints(
            2, 5, -2,
            3, 7, 0,
            5, 9, 3).getArea(), 1.5, Spatium.EPSILON);
    }
    
}