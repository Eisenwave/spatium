package eisenwave.spatium.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.Random;

public final class PrimArrays {
    
    private PrimArrays() {}
    
    @Contract(pure = true)
    public static long[] asLongs(@NotNull Number[] array) {
        long[] prim = new long[array.length];
        for (int i = 0; i < array.length; i++) prim[i] = array[i].longValue();
        return prim;
    }
    
    @Contract(pure = true)
    public static int[] asInts(@NotNull Number[] array) {
        int[] prim = new int[array.length];
        for (int i = 0; i < array.length; i++) prim[i] = array[i].intValue();
        return prim;
    }
    
    @Contract(pure = true)
    public static short[] asShorts(@NotNull Number[] array) {
        short[] prim = new short[array.length];
        for (int i = 0; i < array.length; i++) prim[i] = array[i].shortValue();
        return prim;
    }
    
    @Contract(pure = true)
    public static byte[] asBytes(@NotNull Number[] array) {
        byte[] prim = new byte[array.length];
        for (int i = 0; i < array.length; i++) prim[i] = array[i].byteValue();
        return prim;
    }
    
    @Contract(pure = true)
    public static char[] asChars(@NotNull Character[] array) {
        char[] prim = new char[array.length];
        for (int i = 0; i < array.length; i++) prim[i] = array[i];
        return prim;
    }
    
    @Contract(pure = true)
    public static double[] asDoubles(@NotNull Number[] array) {
        double[] prim = new double[array.length];
        for (int i = 0; i < array.length; i++) prim[i] = array[i].doubleValue();
        return prim;
    }
    
    @Contract(pure = true)
    public static float[] asFloats(@NotNull Number[] array) {
        float[] prim = new float[array.length];
        for (int i = 0; i < array.length; i++) prim[i] = array[i].floatValue();
        return prim;
    }
    
    // CONCATENATION
    
    @Contract(pure = true)
    public static <T> T[] concat(T[] tail, T[] head) {
        Class<?> component = tail.getClass().getComponentType();
        T[] array = newInstance(component, tail.length + head.length);
        
        System.arraycopy(tail, 0, array, 0, tail.length);
        System.arraycopy(head, 0, array, tail.length, head.length);
        
        return array;
    }
    
    @Contract(pure = true)
    public static long[] concat(long[] tail, long[] head) {
        long[] array = new long[tail.length + head.length];
        
        System.arraycopy(tail, 0, array, 0, tail.length);
        System.arraycopy(head, 0, array, tail.length, head.length);
        
        return array;
    }
    
    @Contract(pure = true)
    public static int[] concat(int[] tail, int[] head) {
        int[] array = new int[tail.length + head.length];
        
        System.arraycopy(tail, 0, array, 0, tail.length);
        System.arraycopy(head, 0, array, tail.length, head.length);
        
        return array;
    }
    
    @Contract(pure = true)
    public static short[] concat(short[] tail, short[] head) {
        short[] array = new short[tail.length + head.length];
        
        System.arraycopy(tail, 0, array, 0, tail.length);
        System.arraycopy(head, 0, array, tail.length, head.length);
        
        return array;
    }
    
    @Contract(pure = true)
    public static char[] concat(char[] tail, char[] head) {
        char[] array = new char[tail.length + head.length];
        
        System.arraycopy(tail, 0, array, 0, tail.length);
        System.arraycopy(head, 0, array, tail.length, head.length);
        
        return array;
    }
    
    @Contract(pure = true)
    public static byte[] concat(byte[] tail, byte[] head) {
        byte[] array = new byte[tail.length + head.length];
        
        System.arraycopy(tail, 0, array, 0, tail.length);
        System.arraycopy(head, 0, array, tail.length, head.length);
        
        return array;
    }
    
    @Contract(pure = true)
    public static double[] concat(double[] tail, double[] head) {
        double[] array = new double[tail.length + head.length];
        
        System.arraycopy(tail, 0, array, 0, tail.length);
        System.arraycopy(head, 0, array, tail.length, head.length);
        
        return array;
    }
    
    @Contract(pure = true)
    public static float[] concat(float[] tail, float[] head) {
        float[] array = new float[tail.length + head.length];
        
        System.arraycopy(tail, 0, array, 0, tail.length);
        System.arraycopy(head, 0, array, tail.length, head.length);
        
        return array;
    }
    
    @Contract(pure = true)
    @SafeVarargs
    public static <T> T[] concat(T[]... arrays) {
        int length = 0;
        for (T[] array1 : arrays) length += array1.length;
        
        T[] result = newInstance(arrays.getClass().getComponentType(), length);
        int offset = 0;
        for (T[] array : arrays) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        
        return result;
    }
    
    @Contract(pure = true)
    public static long[] concat(long[]... arrays) {
        int length = 0;
        for (long[] array1 : arrays) length += array1.length;
        
        long[] result = new long[length];
        int offset = 0;
        for (long[] array : arrays) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        
        return result;
    }
    
    @Contract(pure = true)
    public static int[] concat(int[]... arrays) {
        int length = 0;
        for (int[] array1 : arrays) length += array1.length;
        
        int[] result = new int[length];
        int offset = 0;
        for (int[] array : arrays) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        
        return result;
    }
    
    @Contract(pure = true)
    public static short[] concat(short[]... arrays) {
        int length = 0;
        for (short[] array1 : arrays) length += array1.length;
        
        short[] result = new short[length];
        int offset = 0;
        for (short[] array : arrays) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        
        return result;
    }
    
    @Contract(pure = true)
    public static char[] concat(char[]... arrays) {
        int length = 0;
        for (char[] array1 : arrays) length += array1.length;
        
        char[] result = new char[length];
        int offset = 0;
        for (char[] array : arrays) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        
        return result;
    }
    
    @Contract(pure = true)
    public static byte[] concat(byte[]... arrays) {
        int length = 0;
        for (byte[] array1 : arrays) length += array1.length;
        
        byte[] result = new byte[length];
        int offset = 0;
        for (byte[] array : arrays) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        
        return result;
    }
    
    @Contract(pure = true)
    public static double[] concat(double[]... arrays) {
        int length = 0;
        for (double[] array1 : arrays) length += array1.length;
        
        double[] result = new double[length];
        int offset = 0;
        for (double[] array : arrays) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        
        return result;
    }
    
    @Contract(pure = true)
    public static float[] concat(float[]... arrays) {
        int length = 0;
        for (float[] array1 : arrays) length += array1.length;
        
        float[] result = new float[length];
        int offset = 0;
        for (float[] array : arrays) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        
        return result;
    }
    
    //CONTAINS
    
    @Contract(pure = true)
    public static <T> boolean contains(T[] array, T value) {
        for (T anArray : array)
            if (anArray.equals(value))
                return true;
        return false;
    }
    
    @Contract(pure = true)
    public static boolean contains(long[] array, long value) {
        for (long anArray : array)
            if (anArray == value)
                return true;
        return false;
    }
    
    @Contract(pure = true)
    public static boolean contains(int[] array, int value) {
        for (int anArray : array)
            if (anArray == value)
                return true;
        return false;
    }
    
    @Contract(pure = true)
    public static boolean contains(short[] array, short value) {
        for (short anArray : array)
            if (anArray == value)
                return true;
        return false;
    }
    
    @Contract(pure = true)
    public static boolean contains(byte[] array, byte value) {
        for (byte anArray : array)
            if (anArray == value)
                return true;
        return false;
    }
    
    @Contract(pure = true)
    public static boolean contains(char[] array, char value) {
        for (char anArray : array)
            if (anArray == value)
                return true;
        return false;
    }
    
    @Contract(pure = true)
    public static boolean contains(double[] array, double value) {
        for (double anArray : array)
            if (anArray == value)
                return true;
        return false;
    }
    
    @Contract(pure = true)
    public static boolean contains(float[] array, float value) {
        for (float anArray : array)
            if (anArray == value)
                return true;
        return false;
    }
    
    //NEW INSTANCE
    
    @SuppressWarnings("unchecked")
    public static <T> T[] newInstance(Class<?> component, int... dimensions) {
        return (T[]) Array.newInstance(component, dimensions);
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T[] newInstance(Class<?> component, int length) {
        return (T[]) Array.newInstance(component, length);
    }
    
    // RANDOM VALUE
    
    private static Random random = null;
    
    private synchronized static Random initRNG() {
        return random == null? random = new Random() : random;
    }
    
    /**
     * Returns a random member of an array.
     *
     * @param array the array
     */
    @SafeVarargs
    public static <T> T random(T... array) {
        return array[initRNG().nextInt(array.length)];
    }
    
    /**
     * Returns a random member of an array.
     *
     * @param array the array
     */
    public static long random(long... array) {
        return array[initRNG().nextInt(array.length)];
    }
    
    /**
     * Returns a random member of an array.
     *
     * @param array the array
     */
    public static int random(int... array) {
        return array[initRNG().nextInt(array.length)];
    }
    
    /**
     * Returns a random member of an array.
     *
     * @param array the array
     */
    public static short random(short... array) {
        return array[initRNG().nextInt(array.length)];
    }
    
    /**
     * Returns a random member of an array.
     *
     * @param array the array
     */
    public static byte random(byte... array) {
        return array[initRNG().nextInt(array.length)];
    }
    
    /**
     * Returns a random member of an array.
     *
     * @param array the array
     */
    public static char random(char... array) {
        return array[initRNG().nextInt(array.length)];
    }
    
    /**
     * Returns a random member of an array.
     *
     * @param array the array
     */
    public static double random(double... array) {
        return array[initRNG().nextInt(array.length)];
    }
    
    /**
     * Returns a random member of an array.
     *
     * @param array the array
     */
    public static float random(float... array) {
        return array[initRNG().nextInt(array.length)];
    }
    
    // FLIP
    
    /**
     * Inverts the array order.
     *
     * @param array the array
     * @param <T> the component type
     */
    public static <T> void flip(T[] array) {
        final int lim = array.length / 2;
        
        for (int i = 0; i < lim; i++)
            swap(array, i, array.length - 1 - i);
    }
    
    /**
     * Inverts the array order.
     *
     * @param array the array
     */
    public static void flip(long[] array) {
        final int lim = array.length / 2;
        
        for (int i = 0; i < lim; i++)
            swap(array, i, array.length - 1 - i);
    }
    
    /**
     * Inverts the array order.
     *
     * @param array the array
     */
    public static void flip(int[] array) {
        final int lim = array.length / 2;
        
        for (int i = 0; i < lim; i++)
            swap(array, i, array.length - 1 - i);
    }
    
    /**
     * Inverts the array order.
     *
     * @param array the array
     */
    public static void flip(short[] array) {
        final int lim = array.length / 2;
        
        for (int i = 0; i < lim; i++)
            swap(array, i, array.length - 1 - i);
    }
    
    /**
     * Inverts the array order.
     *
     * @param array the array
     */
    public static void flip(char[] array) {
        final int lim = array.length / 2;
        
        for (int i = 0; i < lim; i++)
            swap(array, i, array.length - 1 - i);
    }
    
    /**
     * Inverts the array order.
     *
     * @param array the array
     */
    public static void flip(byte[] array) {
        final int lim = array.length / 2;
        
        for (int i = 0; i < lim; i++)
            swap(array, i, array.length - 1 - i);
    }
    
    /**
     * Inverts the array order.
     *
     * @param array the array
     */
    public static void flip(double[] array) {
        final int lim = array.length / 2;
        
        for (int i = 0; i < lim; i++)
            swap(array, i, array.length - 1 - i);
    }
    
    /**
     * Inverts the array order.
     *
     * @param array the array
     */
    public static void flip(float[] array) {
        final int lim = array.length / 2;
        
        for (int i = 0; i < lim; i++)
            swap(array, i, array.length - 1 - i);
    }
    
    // SWAP
    
    /**
     * Swaps two elements in an array.
     *
     * @param array the array
     * @param i1 the from-index
     * @param i2 the to-index
     * @param <T> the array type
     */
    public static <T> void swap(T[] array, int i1, int i2) {
        T val1 = array[i1];
        array[i1] = array[i2];
        array[i2] = val1;
    }
    
    /**
     * Swaps two elements in an array.
     *
     * @param array the array
     * @param i1 the from-index
     * @param i2 the to-index
     */
    public static void swap(long[] array, int i1, int i2) {
        long val1 = array[i1];
        array[i1] = array[i2];
        array[i2] = val1;
    }
    
    /**
     * Swaps two elements in an array.
     *
     * @param array the array
     * @param i1 the from-index
     * @param i2 the to-index
     */
    public static void swap(int[] array, int i1, int i2) {
        int val1 = array[i1];
        array[i1] = array[i2];
        array[i2] = val1;
    }
    
    /**
     * Swaps two elements in an array.
     *
     * @param array the array
     * @param i1 the from-index
     * @param i2 the to-index
     */
    public static void swap(short[] array, int i1, int i2) {
        short val1 = array[i1];
        array[i1] = array[i2];
        array[i2] = val1;
    }
    
    /**
     * Swaps two elements in an array.
     *
     * @param array the array
     * @param i1 the from-index
     * @param i2 the to-index
     */
    public static void swap(char[] array, int i1, int i2) {
        char val1 = array[i1];
        array[i1] = array[i2];
        array[i2] = val1;
    }
    
    /**
     * Swaps two elements in an array.
     *
     * @param array the array
     * @param i1 the from-index
     * @param i2 the to-index
     */
    public static void swap(byte[] array, int i1, int i2) {
        byte val1 = array[i1];
        array[i1] = array[i2];
        array[i2] = val1;
    }
    
    /**
     * Swaps two elements in an array.
     *
     * @param array the array
     * @param i1 the from-index
     * @param i2 the to-index
     */
    public static void swap(double[] array, int i1, int i2) {
        double val1 = array[i1];
        array[i1] = array[i2];
        array[i2] = val1;
    }
    
    /**
     * Swaps two elements in an array.
     *
     * @param array the array
     * @param i1 the from-index
     * @param i2 the to-index
     */
    public static void swap(float[] array, int i1, int i2) {
        float val1 = array[i1];
        array[i1] = array[i2];
        array[i2] = val1;
    }
    
}
