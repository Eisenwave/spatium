package net.grian.spatium.util;

import org.junit.Test;

public class FastMathTest {

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