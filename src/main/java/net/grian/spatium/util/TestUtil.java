package net.grian.spatium.util;

public final class TestUtil {
    
    private TestUtil() {}
    
    public static long millisOf(Runnable task) {
        long before = System.currentTimeMillis();
        task.run();
        return System.currentTimeMillis() - before;
    }
    
    public static long millisOf(Runnable task, int times) {
        long before = System.currentTimeMillis();
        while (--times >= 0)
            task.run();
        return System.currentTimeMillis() - before;
    }
    
}
