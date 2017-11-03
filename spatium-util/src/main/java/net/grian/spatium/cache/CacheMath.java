package net.grian.spatium.cache;

import net.grian.spatium.util.FastMath;

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
@SuppressWarnings({"SpellCheckingInspection", "unused"})
public final class CacheMath {

    private CacheMath() {}

    //list of common ratios
    public final static double
        TEN = 10,
        HUNDRED = 100,
        THOUSAND = 1000,
        MILLION = 1_000_000,
        BILLION = 1_000_000_000,
        TENTH = 1 / TEN,
        HUNDREDTH = 1 / HUNDRED,
        THOUSANDTH = 1 / THOUSAND,
        MILLIONTH = 1 / MILLION,
        BILLIONTH = 1 / BILLION,
    
        FOUR_THIRDS = 4 / 3D,
        FOUR_THIRDS_PI = FOUR_THIRDS * Math.PI;
    
    //list of common constants or numbers
    public final static double
        GOLDEN = (1 + Math.sqrt(5)) / 2,
        TAU = Math.PI * 2,
        HALF_PI = Math.PI / 2;
    
    //list of common roots
    public final static double
        SQRT_2 = Math.sqrt(2),
        SQRT_3 = Math.sqrt(3),
        HALF_SQRT_2 = SQRT_2 / 2,
        THIRD_SQRT_3 = SQRT_3 / 3,
        SQRT_HALF = Math.sqrt(1 / 2D),
        CBRT_THIRD = Math.cbrt(1 / 3D),
        INV_SQRT_2 = 1 / SQRT_2,
        INV_SQRT_3 = 1 / SQRT_3;
    
    //list of common natural logs
    public final static double
        LN_2 = Math.log(2),
        LN_10 = Math.log10(10);

    public final static double TO_RADIANS = Math.PI / 180;

    private static AsinCache cacheAsin = null;
    private static SinCache cacheSin = null;
    private static SqrtCache cacheSqrt = null;
    private static BinomCache cacheBinom = null;

    private static int sqrtPrecision = 0xFF;
    
    //MATH UTILITY

    public static int choose(int n, int k) {
        return cacheBinom==null? FastMath.choose(n, k) : cacheBinom.choose(n, k);
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
    
    /*
    public static int binlog( int bits ) {
        int log = 0;
        if( ( bits & 0xffff0000 ) != 0 ) { bits >>>= 16; log = 16; }
        if( bits >= 256 ) { bits >>>= 8; log += 8; }
        if( bits >= 16  ) { bits >>>= 4; log += 4; }
        if( bits >= 4   ) { bits >>>= 2; log += 2; }
        return log + ( bits >>> 1 );
    }
    
    public static int log2i(int number) {
        if(number == 0) return 0;
        return 31 - Integer.numberOfLeadingZeros(number);
    }
    */
    
    /*
    public static int log2nlz( int bits ) {
        if( bits == 0 )
            return 0; // or throw exception
        return 31 - Integer.numberOfLeadingZeros( bits );
    }
    */
    
    

    //CACHE CONTROL FUNCTIONS
    
    public static int getSqrtPrecision() {
        return sqrtPrecision;
    }

    public static SqrtCache getCacheSqrt() {
        return cacheSqrt;
    }

    public static AsinCache getCacheAsin() {
        return cacheAsin;
    }

    public static SinCache getCacheSin() {
        return cacheSin;
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

    @Deprecated
    public static void setSqrtCapacity(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("capacity must be at least 1");
        if (cacheSqrt==null)
            cacheSqrt = new SqrtCache(capacity, 1);
        else if (cacheSqrt.getCapacity() < capacity)
            cacheSqrt = new SqrtCache(capacity, cacheSqrt.getMax());
    }

    public static void setSqrtPrecision(int precision) {
        if (precision <= 0) throw new IllegalArgumentException("precision must be at least 1");
        if (cacheSqrt==null)
            cacheSqrt = new SqrtCache((sqrtPrecision = precision), 1);
        else if (sqrtPrecision < precision)
            cacheSqrt = new SqrtCache((int) ((sqrtPrecision = precision) * cacheSqrt.getMax()), cacheSqrt.getMax());
    }

    public static void setSqrtMaximum(double max) {
        if (max < 0) throw new IllegalArgumentException("maximum must be positive");
        if (cacheSqrt==null)
            cacheSqrt = new SqrtCache((int) (sqrtPrecision * max), max);
        else if (cacheSqrt.getMax() < max)
            cacheSqrt = new SqrtCache((int) (sqrtPrecision * max), max);
    }

}
