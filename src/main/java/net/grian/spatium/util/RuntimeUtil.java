package net.grian.spatium.util;

public final class RuntimeUtil {
    
    public static long usedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
    
}
