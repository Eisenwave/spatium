package net.grian.spatium.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class FastMathTest {
    
    private final static boolean PERFORMANCE_TEST = false;
    
    @Test
    public void isPower2() throws Exception {
        assertTrue(FastMath.isPower2(0));
        assertTrue(FastMath.isPower2(1));
        
        for (int i = 2; i < Short.MAX_VALUE+1; i*=2) {
            assertTrue(FastMath.isPower2(i));
            assertFalse(FastMath.isPower2(i+1));
        }
        
        if (PERFORMANCE_TEST) {
            long fast = TestUtil.millisOf(() -> {
                for (int i = 0; i < 1 << 24; i++)
                    FastMath.isPower2(i);
            });
            System.out.println("fast: "+fast);
    
            long classic = TestUtil.millisOf(() -> {
                boolean bool;
                for (int i = 0; i < 1 << 24; i++)
                    bool = FastMath.log2(i) % 0 == 0;
            });
            System.out.println("classic: "+classic);
        }
    }

    @Test
    public void invSqrt() throws Exception {
        double imprecision = 0;

        for (int i = 0; i<1_000_000; i++) {
            double random = PrimMath.randomDouble(10000);

            double normal = 1 / Math.sqrt(random);
            double fast = FastMath.invSqrt(random);

            imprecision += Math.abs(normal - fast);
        }

        System.out.println(imprecision);
    }

    /*
    @Test
    public void performance_InvSqrt() {
        {
            long now = System.nanoTime();
            for (int i = 0; i<10_000_000; i++) {
                double random = PrimMath.randomDouble(10000);
                double result = 1 / Math.sqrt(random);
            }
            double time = (System.nanoTime()-now) / CacheMath.BILLION;
            System.out.println("normal took "+ time+" s");
        }
        {
            long now = System.nanoTime();
            for (int i = 0; i<10_000_000; i++) {
                double random = PrimMath.randomDouble(10000);
                double result = LenientMath.invSqrt(random);
            }
            double time = (System.nanoTime()-now) / CacheMath.BILLION;
            System.out.println("fast took "+ time+" s");
        }
    }
    */

}