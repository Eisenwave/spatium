package eisenwave.spatium.util;

@SuppressWarnings("Duplicates")
public final class NumberUtil {
    
    private final static int
        HIGHEST_BIT_32 = 0b10000000_00000000_00000000_00000000;
    
    public static int powMod(int a, int x, int m) {
        if (x == 0) return 1;
        else if (x == 1) return a % m;
        
        long result = 1;
        a %= m;
        
        boolean start = false;
        for (int i = 0; i < 32; i++) {
            if (((x << i) & HIGHEST_BIT_32) != 0) {
                if (!start) {
                    start = true;
                    result = a;
                }
                else {
                    result *= result;
                    result %= m;
                    result *= a;
                    result %= m;
                }
            }
            else if (start) {
                result *= result;
                result %= m;
            }
        }
        
        return (int) result;
    }
    
}
