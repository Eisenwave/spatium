package net.grian.spatium.cache;

import net.grian.spatium.Spatium;
import org.junit.Test;

import static org.junit.Assert.*;

public class CacheMathTest {

    @Test
    public void sqrt() throws Exception {
        final int precision = 1000, max = 100;

        CacheMath.setSqrtPrecision(precision); //100 values per unit
        assertEquals(CacheMath.getSqrtPrecision(), precision); //100 values per unit
        assertEquals((int) CacheMath.getCacheSqrt().getMax(), 1);
        assertEquals(CacheMath.getCacheSqrt().getCapacity(), precision);

        CacheMath.setSqrtMaximum(max); //max value 100
        assertEquals((int) CacheMath.getCacheSqrt().getMax(), max);
        assertEquals(CacheMath.getCacheSqrt().getCapacity(), max*precision);

        assertTrue(Spatium.equals((float) CacheMath.sqrt(67), (float) Math.sqrt(67)));
    }

}