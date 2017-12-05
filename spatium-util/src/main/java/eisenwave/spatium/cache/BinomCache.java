package eisenwave.spatium.cache;

import eisenwave.spatium.util.FastMath;

public final class BinomCache {

    private final int rows;

    private final int[][] cache;

    public BinomCache(int rows) {
        this.rows = rows;
        cache = new int[rows][0];

        for (int i = 0; i<rows; i++) {
            cache[i] = new int[i + 1];
            for (int j = 0; j < i + 1; j++)
                cache[i][j] = FastMath.choose(i, j);
        }
    }

    public int choose(int n, int k) {
        if (n < 0) throw new IllegalArgumentException("n must be positive");
        if (k < 0) throw new IllegalArgumentException("k must be positive");
        if (n==k || k==0) return 1;
        if (n < rows)
            return cache[n][k];
        else
            return FastMath.choose(n, k);
    }

    public int getCapacity() {
        return rows;
    }

}
