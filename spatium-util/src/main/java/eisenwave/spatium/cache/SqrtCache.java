package eisenwave.spatium.cache;

public class SqrtCache {

    private final int size;
    private final double max;
    private final double multi;

    private double[] cache;

    public SqrtCache(int size, double max) {
        this.size = size;
        this.max = max;
        this.multi = size/max;
        this.cache = new double[size];

        for (int i = 0; i<size; i++)
            cache[i] = Math.sqrt(i / multi);
    }

    public double sqrt(double val) {
        if (val < 0 || Double.isNaN(val)) return Double.NaN;
        if (Double.isInfinite(val)) return Double.POSITIVE_INFINITY;
        if (val == 0) return val;
        if (val >= max) return Math.sqrt(val);

        return cache[(int)(val * multi)];
    }

    /**
     * Returns the maximum input value of the {@link #sqrt(double)} method to be cached.
     *
     * @return the maximum cached value
     */
    public double getMax() {
        return max;
    }

    /**
     * Returns the amount of cached sqrt values.
     */
    public int getCapacity() {
        return size;
    }

}
