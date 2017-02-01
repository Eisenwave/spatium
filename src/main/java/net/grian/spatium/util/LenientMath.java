package net.grian.spatium.util;

public final class LenientMath {

    public static float invSqrt(float x) {
        float halfX = 0.5f * x;
        int i = Float.floatToIntBits(x);
        i = 0x5f3759df - (i >> 1);
        x = Float.intBitsToFloat(i);
        x *= (1.5f - halfX * x * x);
        return x;
    }

    public static double invSqrt(double x) {
        double halfX = 0.5d * x;
        long i = Double.doubleToLongBits(x);
        i = 0x5fe6ec85e7de30daL - (i >> 1);
        x = Double.longBitsToDouble(i);
        x *= (1.5d - halfX * x * x);
        return x;
    }

}
