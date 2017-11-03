package net.grian.spatium.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class SpatiumTest {
    
    private final static double
        POS_INF = Double.POSITIVE_INFINITY,
        NEG_INF = Double.NEGATIVE_INFINITY,
        NAN = Double.NaN;
    
    @Test
    public void equals_precision() throws Exception {
        final double t = 0.7777777777;
        final int exp = 100;
        
        double num = t;
        for (int i = 1; i<exp; i++)
            num *= t;
    
        assertEquals(num, Math.pow(t, exp), Spatium.EPSILON);
        assertTrue(Spatium.equals(num, Math.pow(t, exp)));
    }
    
    @Test
    public void equals_specialCases() throws Exception {
        assertTrue(Spatium.equals(POS_INF, POS_INF));
        assertTrue(Spatium.equals(NEG_INF, NEG_INF));
        assertTrue(Spatium.equals(NAN, NAN));
        
        assertFalse(Spatium.equals(NEG_INF, POS_INF));
        assertFalse(Spatium.equals(NAN, POS_INF));
    }
    
}