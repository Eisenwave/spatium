package net.grian.spatium.cache;

import net.grian.spatium.util.FastMath;
import net.grian.spatium.util.Spatium;
import org.junit.Test;

import static org.junit.Assert.*;

public class CacheMathTest {
    
    @Test
    public void log2() throws Exception {
        assertEquals(1, FastMath.log2(2), 0);
        assertEquals(2, FastMath.log2(4), 0);
        assertEquals(3, FastMath.log2(8), 0);
        assertEquals(4, FastMath.log2(16), 0);
    }
    
    /*
    @Test
    public void log2i() throws Exception {
        assertEquals(1, CacheMath.log2i(2));
        assertEquals(2, CacheMath.log2i(4));
        assertEquals(3, CacheMath.log2i(8));
        assertEquals(4, CacheMath.log2i(16));
    }
    
    @Test
    public void performance_log2() throws Exception {
        final long tests = 1_000_000_000;
        
        long naive = TestUtil.millisOf(() -> {
            for (int i = 0; i<tests; i++) {
                double log = Math.log(i) / Math.log(2);
            }
        });
        
        long regular = TestUtil.millisOf(() -> {
            for (int i = 0; i<tests; i++) {
                CacheMath.log2(i);
            }
        });
    
        long fast = TestUtil.millisOf(() -> {
            for (int i = 0; i<tests; i++) {
                CacheMath.log2i(i);
            }
        });
    
        long binlog = TestUtil.millisOf(() -> {
            for (int i = 0; i<tests; i++) {
                CacheMath.binlog(i);
            }
        });
    
        System.out.println("naive: "+naive);
        System.out.println("regular: "+regular);
        System.out.println("binlog: "+binlog);
        System.out.println("fast: "+fast);
    }
    */
    
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
