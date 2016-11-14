package net.grian.spatium.cache;

import net.grian.spatium.Spatium;

/**
 * <p>
 *     A utility class for performing expensive mathematical operations using lookup tables. The operations include, but
 *     are not limited to:
 * </p>
 * <ul>
 *     <li>sine and cosine of radians and degrees</li>
 *     <li>arcus sine, and arcus cosine</li>
 *     <li>square root of real numbers</li>
 * </ul>
 * <p>
 *     By default, there is no caches initialized in this class. They can be initialized from the outside using setters.
 *     Should there be no lookup value in the table or should the table be missing entirely, methods from {@link Math}
 *     are being used instead, in no situation is an exception thrown.
 * </p>
 */
@SuppressWarnings("SpellCheckingInspection")
public final class CacheMath {

    private CacheMath() {}

    private final static double TO_RADIANS = Math.PI / 180;

    private static AsinCache cacheAsin = null;
    private static SinCache cacheSin = null;
    private static SqrtCache cacheSqrt = null;
    private static BinomCache cacheBinom = null;

    public static int choose(int n, int k) {
        return cacheBinom==null? Spatium.choose(n, k) : cacheBinom.choose(n, k);
    }

    public static double asin(double radians) {
        return cacheAsin==null? Math.asin(radians) : cacheAsin.asin(radians);
    }

    public static double acos(double radians) {
        return cacheAsin==null? Math.acos(radians) : cacheAsin.acos(radians);
    }

    public static double sin(double radians) {
        return cacheSin==null? Math.sin(radians) : cacheSin.sin(radians);
    }

    public static double cos(double radians) {
        return cacheSin==null? Math.cos(radians) : cacheSin.cos(radians);
    }

    public static double sinDeg(double degrees) {
        return sin(degrees * TO_RADIANS);
    }

    public static double cosDeg(double degrees) {
        return cos(degrees * TO_RADIANS);
    }

    public static double sqrt(double number) {
        return cacheSqrt==null? Math.sqrt(number) : cacheSqrt.sqrt(number);
    }

    public static void setBinomCapacity(int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("capacity must be positive");
        if (cacheBinom==null || cacheBinom.getCapacity() < capacity) {
            cacheBinom = new BinomCache(capacity);
        }
    }

    public static void setAsinCapacity(int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("capacity must be positive");
        if (cacheAsin==null || cacheAsin.getCapacity() < capacity) {
            cacheAsin = new AsinCache(capacity);
        }
    }

    public static void setSinCapacity(int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("capacity must be positive");
        if (cacheSin==null || cacheSin.getCapacity() < capacity) {
            cacheSin = new SinCache(capacity);
        }
    }

    public static void setSqrtCapacity(int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("capacity must be positive");
        if (cacheSqrt==null || cacheSqrt.getCapacity() < capacity) {
            cacheSqrt = new SqrtCache(capacity, cacheSqrt==null? 1 : cacheSqrt.getMax());
        }
    }

    public static void setSqrtMaximum(double max) {
        if (max < 0) throw new IllegalArgumentException("maximum must be positive");
        if (cacheSqrt==null || cacheSqrt.getMax() < max) {
            cacheSqrt = new SqrtCache(cacheSqrt==null? 1 : cacheSqrt.getCapacity(), max);
        }
    }

}
