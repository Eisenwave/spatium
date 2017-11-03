package net.grian.spatium.util;

import org.jetbrains.annotations.NotNull;

public final class TestUtil {
    
    private TestUtil() {}
    
    /**
     * Returns how long it took to run a task in milliseconds.
     *
     * @param task the task
     * @return the task millis
     */
    public static long millisOf(@NotNull Runnable task) {
        long before = System.currentTimeMillis();
        task.run();
        return System.currentTimeMillis() - before;
    }
    
    /**
     * Returns how long it took to run a task multiple times in milliseconds.
     *
     * @param task the task
     * @param times the amount of times the task is run
     * @return the task millis
     */
    public static long millisOf(@NotNull Runnable task, int times) {
        long before = System.currentTimeMillis();
        while (--times >= 0)
            task.run();
        return System.currentTimeMillis() - before;
    }
    
    /**
     * Returns whether the given task runs without throwing any errors.
     *
     * @param task the task
     * @return whether the task runs without errors
     */
    public static boolean runsNoExcept(@NotNull Runnable task) {
        try {
            task.run();
            return true;
        } catch (Throwable ex) {
            return false;
        }
    }
    
    public static long usedMemory() {
        Runtime r = Runtime.getRuntime();
        return r.totalMemory() - r.freeMemory();
    }
    
}
