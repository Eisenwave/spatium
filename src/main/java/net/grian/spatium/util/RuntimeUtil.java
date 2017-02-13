package net.grian.spatium.util;

import java.util.function.Supplier;

public final class RuntimeUtil {
    
    private RuntimeUtil() {}
    
    private static final Runtime RUNTIME = Runtime.getRuntime();
    
    public static long usedMemory() {
        return RUNTIME.totalMemory() - RUNTIME.freeMemory();
    }
    
    @SuppressWarnings("MismatchedReadAndWriteOfArray")
    public static <T> long sizeOf(Supplier<T> constructor, final int count) throws Exception {
        // Warm up all classes/methods
        runGC();
        usedMemory();
        
        // Array to keep strong references to allocated objects
        Object[] objects = new Object[count];
        
        long heap1 = 0;
        // Allocate count+1 objects, discard the first one
        for (int i = -1; i < count; ++ i) {
            Object object = constructor.get();
            
            if (i >= 0)
                objects[i] = object;
            else {
                object = null; // Discard the warm up object
                runGC();
                heap1 = usedMemory(); // Take a before heap snapshot
            }
        }
        
        runGC ();
        long heap2 = usedMemory();
        
        return (int) ( (heap2 - heap1) / (float) count );
    }
    
    private final static int GC_COUNT = 4;
    
    private static void runGC () throws Exception {
        for (int i = 0; i < GC_COUNT; i++)
            internalRunGC();
    }
    
    private static void internalRunGC() throws Exception {
        long
            usedMem1 = usedMemory(),
            usedMem2 = Long.MAX_VALUE;
        
        for (int i = 0; (usedMem1 < usedMem2) && (i < 500); ++ i) {
            RUNTIME.runFinalization();
            RUNTIME.gc();
            Thread.yield();
            
            usedMem2 = usedMem1;
            usedMem1 = usedMemory();
        }
    }
    
}
