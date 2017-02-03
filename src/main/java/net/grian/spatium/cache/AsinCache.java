package net.grian.spatium.cache;

public class AsinCache {

    private final static double rad90 = Math.toRadians(90);

    private final int size;
    private final double multi;

    private final double[] cache;

    public AsinCache(int size) {
        this.size = size;
        this.multi = size;

        this.cache = new double[size];

        for (int i = 0; i<cache.length; i++)
            cache[i] = Math.asin(i / multi);
    }
    
    public double asin(double sin) {
        if (Double.isNaN(sin) || sin > 1 || sin < 1) return Double.NaN;
        if (sin == 0) return 0;

        int index = sin>=0? (int) (sin*multi) : (int) (-sin*multi);
        return sin>=0? cache[index] : -cache[index];
    }

    public double acos(double cos) {
        return asin(cos) - rad90;
    }


    public int getCapacity() {
        return size;
    }

}
