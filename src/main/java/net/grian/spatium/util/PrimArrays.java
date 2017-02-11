package net.grian.spatium.util;

import org.jetbrains.annotations.Contract;

import javax.annotation.Nonnull;
import java.lang.reflect.Array;
import java.util.*;

@SuppressWarnings({"WeakerAccess"})
public final class PrimArrays {
    
    private PrimArrays() {}
    
    @Contract(pure = true)
    public static long[] asLongs(@Nonnull Number[] array) {
        long[] prim = new long[array.length];
        for (int i = 0; i<array.length; i++) prim[i] = array[i].longValue();
        return prim;
    }
    
    @Contract(pure = true)
    public static int[] asInts(@Nonnull Number[] array) {
        int[] prim = new int[array.length];
        for (int i = 0; i<array.length; i++) prim[i] = array[i].intValue();
        return prim;
    }
    
    @Contract(pure = true)
    public static short[] asShorts(@Nonnull Number[] array) {
        short[] prim = new short[array.length];
        for (int i = 0; i<array.length; i++) prim[i] = array[i].shortValue();
        return prim;
    }
    
    @Contract(pure = true)
    public static byte[] asBytes(@Nonnull Number[] array) {
        byte[] prim = new byte[array.length];
        for (int i = 0; i<array.length; i++) prim[i] = array[i].byteValue();
        return prim;
    }
    
    @Contract(pure = true)
    public static char[] asChars(@Nonnull Character[] array) {
        char[] prim = new char[array.length];
        for (int i = 0; i<array.length; i++) prim[i] = array[i];
        return prim;
    }
    
    @Contract(pure = true)
    public static double[] asDoubles(@Nonnull Number[] array) {
        double[] prim = new double[array.length];
        for (int i = 0; i<array.length; i++) prim[i] = array[i].doubleValue();
        return prim;
    }
    
    @Contract(pure = true)
    public static float[] asFloats(@Nonnull Number[] array) {
        float[] prim = new float[array.length];
        for (int i = 0; i<array.length; i++) prim[i] = array[i].floatValue();
        return prim;
    }
    
    @Contract(pure = true)
    public static long[] asLongs(@Nonnull Collection<? extends Number> collection) {
        long[] prim = new long[collection.size()];
        
        int i = 0;
        for (Number val : collection) prim[i++] = val.longValue();
        return prim;
    }
    
    @Contract(pure = true)
    public static int[] asInts(@Nonnull Collection<? extends Number> collection) {
        int[] prim = new int[collection.size()];
        
        int i = 0;
        for (Number val : collection) prim[i++] = val.intValue();
        return prim;
    }
    
    @Contract(pure = true)
    public static short[] asShorts(@Nonnull Collection<? extends Number> collection) {
        short[] prim = new short[collection.size()];
        
        int i = 0;
        for (Number val : collection) prim[i++] = val.shortValue();
        return prim;
    }
    
    @Contract(pure = true)
    public static byte[] asBytes(@Nonnull Collection<? extends Number> collection) {
        byte[] prim = new byte[collection.size()];
        
        int i = 0;
        for (Number val : collection) prim[i++] = val.byteValue();
        return prim;
    }
    
    @Contract(pure = true)
    public static char[] asChars(@Nonnull Collection<? extends Character> collection) {
        char[] prim = new char[collection.size()];
        
        int i = 0;
        for (Character val : collection) prim[i++] = val;
        return prim;
    }
    
    @Contract(pure = true)
    public static double[] asDoubles(@Nonnull Collection<? extends Number> collection) {
        double[] prim = new double[collection.size()];
        
        int i = 0;
        for (Number val : collection) prim[i++] = val.doubleValue();
        return prim;
    }
    
    @Contract(pure = true)
    public static float[] asFloats(@Nonnull Collection<? extends Number> collection) {
        float[] prim = new float[collection.size()];
        
        int i = 0;
        for (Number val : collection) prim[i++] = val.floatValue();
        return prim;
    }
    
    @Contract(pure = true)
    public static <T> T[] contact(T[] tail, T[] head) {
        Class<?> component = tail.getClass().getComponentType();
        T[] array = newInstance(component, tail.length+head.length);
    
        System.arraycopy(tail, 0, array, 0, tail.length);
        System.arraycopy(head, 0, array, tail.length, head.length);
        
        return array;
    }
    
    @Contract(pure = true)
    public static long[] contact(long[] tail, long[] head) {
        long[] array = new long[tail.length+head.length];
    
        System.arraycopy(tail, 0, array, 0, tail.length);
        System.arraycopy(head, 0, array, tail.length, head.length);
        
        return array;
    }
    
    @Contract(pure = true)
    public static int[] contact(int[] tail, int[] head) {
        int[] array = new int[tail.length+head.length];
    
        System.arraycopy(tail, 0, array, 0, tail.length);
        System.arraycopy(head, 0, array, tail.length, head.length);
        
        return array;
    }
    
    @Contract(pure = true)
    public static short[] contact(short[] tail, short[] head) {
        short[] array = new short[tail.length+head.length];
    
        System.arraycopy(tail, 0, array, 0, tail.length);
        System.arraycopy(head, 0, array, tail.length, head.length);
        
        return array;
    }
    
    @Contract(pure = true)
    public static char[] contact(char[] tail, char[] head) {
        char[] array = new char[tail.length+head.length];
    
        System.arraycopy(tail, 0, array, 0, tail.length);
        System.arraycopy(head, 0, array, tail.length, head.length);
        
        return array;
    }
    
    @Contract(pure = true)
    public static byte[] contact(byte[] tail, byte[] head) {
        byte[] array = new byte[tail.length+head.length];
    
        System.arraycopy(tail, 0, array, 0, tail.length);
        System.arraycopy(head, 0, array, tail.length, head.length);
        
        return array;
    }
    
    @Contract(pure = true)
    public static double[] contact(double[] tail, double[] head) {
        double[] array = new double[tail.length+head.length];
    
        System.arraycopy(tail, 0, array, 0, tail.length);
        System.arraycopy(head, 0, array, tail.length, head.length);
        
        return array;
    }
    
    @Contract(pure = true)
    public static float[] contact(float[] tail, float[] head) {
        float[] array = new float[tail.length+head.length];
    
        System.arraycopy(tail, 0, array, 0, tail.length);
        System.arraycopy(head, 0, array, tail.length, head.length);
        
        return array;
    }
    
    //EQUAL
    
    @Contract(pure = true)
    public static boolean equal(int[] array1, int[] array2) {
        if (array1.length != array2.length) return false;
        for (int i = 0; i<array1.length; i++) {
            if (array1[i] != array2[i]) return false; 
        }
        return true;
    }
    
    @Contract(pure = true)
    public static boolean equal(short[] array1, short[] array2) {
        if (array1.length != array2.length) return false;
        for (int i = 0; i<array1.length; i++) {
            if (array1[i] != array2[i]) return false; 
        }
        return true;
    }
    
    @Contract(pure = true)
    public static boolean equal(byte[] array1, byte[] array2) {
        if (array1.length != array2.length) return false;
        for (int i = 0; i<array1.length; i++) {
            if (array1[i] != array2[i]) return false; 
        }
        return true;
    }
    
    @Contract(pure = true)
    public static boolean equal(char[] array1, char[] array2) {
        if (array1.length != array2.length) return false;
        for (int i = 0; i<array1.length; i++) {
            if (array1[i] != array2[i]) return false; 
        }
        return true;
    }
    
    @Contract(pure = true)
    public static boolean equal(double[] array1, double[] array2) {
        if (array1.length != array2.length) return false;
        for (int i = 0; i<array1.length; i++) {
            if (array1[i] != array2[i]) return false; 
        }
        return true;
    }
    
    @Contract(pure = true)
    public static boolean equal(float[] array1, float[] array2) {
        if (array1.length != array2.length) return false;
        for (int i = 0; i<array1.length; i++) {
            if (array1[i] != array2[i]) return false; 
        }
        return true;
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
    
    //PASTE
    
    public static <T> void paste(T[] tail, T[] array, int index) {
        if (tail.length < array.length+index)
            throw new IllegalArgumentException();
        System.arraycopy(array, 0, tail, index, array.length);
    }
    
    public static void paste(double[] into, double[] array, int index) {
        if (into.length < array.length+index) throw new IllegalArgumentException();
        System.arraycopy(array, 0, into, index, array.length);
    }
    
    @Contract(pure = true)
    public static boolean equal(long[] array1, long[] array2) {
        if (array1.length != array2.length)
            return false;
        for (int i = 0; i<array1.length; i++)
            if (array1[i] != array2[i]) return false;
        return true;
    }
    
    //MERGE
    
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
    
    //NEW INSTANCE
    
    @SuppressWarnings("unchecked")
    public static <T> T[] newInstance(Class<?> component, int... dimensions) {
        return (T[]) Array.newInstance(component, dimensions);
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T[] newInstance(Class<?> component, int length) {
        return (T[]) Array.newInstance(component, length);
    }
    
    //OVERLAP
    
    public static <T> boolean overlap(T[] array1, T[] array2) {
        if (array1.length < array2.length) {//iterate over shorter array to trigger contains() less often and reduce overhead
            for (T anArray1 : array1) if (contains(array2, anArray1)) return true;
        }
        else {
            assert array1.length > array2.length;
            for (T anArray2 : array2) if (contains(array1, anArray2)) return true;
        }
        
        return false;
    }
    
    public static boolean overlap(long[] array1, long[] array2) {
        if (array1.length < array2.length) {
            for (long anArray1 : array1) if (contains(array2, anArray1)) return true;
        }
        else {
            assert array1.length > array2.length;
            for (long anArray2 : array2) if (contains(array1, anArray2)) return true;
        }
        
        return false;
    }
    
    public static boolean overlap(int[] array1, int[] array2) {
        if (array1.length < array2.length) {
            for (int anArray1 : array1) if (contains(array2, anArray1)) return true;
        }
        else {
            assert array1.length > array2.length;
            for (int anArray2 : array2) if (contains(array1, anArray2)) return true;
        }
        
        return false;
    }
    
    public static boolean overlap(short[] array1, short[] array2) {
        if (array1.length < array2.length) {
            for (short anArray1 : array1) if (contains(array2, anArray1)) return true;
        }
        else {
            assert array1.length > array2.length;
            for (short anArray2 : array2) if (contains(array1, anArray2)) return true;
        }
        
        return false;
    }
    
    public static boolean overlap(char[] array1, char[] array2) {
        if (array1.length < array2.length) {
            for (char anArray1 : array1) if (contains(array2, anArray1)) return true;
        }
        else {
            assert array1.length > array2.length;
            for (char anArray2 : array2) if (contains(array1, anArray2)) return true;
        }
        
        return false;
    }
    
    public static boolean overlap(byte[] array1, byte[] array2) {
        if (array1.length < array2.length) {
            for (byte anArray1 : array1) if (contains(array2, anArray1)) return true;
        }
        else {
            assert array1.length > array2.length;
            for (byte anArray2 : array2) if (contains(array1, anArray2)) return true;
        }
        
        return false;
    }
    
    public static boolean overlap(double[] array1, double[] array2) {
        if (array1.length < array2.length) {
            for (double anArray1 : array1) if (contains(array2, anArray1)) return true;
        }
        else {
            assert array1.length > array2.length;
            for (double anArray2 : array2) if (contains(array1, anArray2)) return true;
        }
        
        return false;
    }
    
    public static boolean overlap(float[] array1, float[] array2) {
        if (array1.length < array2.length) {
            for (float anArray1 : array1) if (contains(array2, anArray1)) return true;
        }
        else {
            assert array1.length > array2.length;
            for (float anArray2 : array2) if (contains(array1, anArray2)) return true;
        }
        
        return false;
    }
    
    //PARSE
    
    private static Random random = null;
    
    private synchronized static Random initRNG() {
        return random==null? random=new Random() : random;
    }
    
    @SafeVarargs
    public static <T> T[] randomMultiple(int count, T... array) {
        Random rng = initRNG();
        T[] results = newInstance(array.getClass().getComponentType(), count);
        for (int i = 0; i<results.length; i++) {
            results[i] = array[ rng.nextInt(array.length) ];
        }
        
        return results;
    }
    
    /**
     * Returns a random member of an array.
     * 
     * @param array the array
     */
    @SafeVarargs
    public static <T> T random(T... array) {
        return array[ initRNG().nextInt(array.length) ];
    }
    
    /**
     * Returns a random member of an array.
     * 
     * @param array the array
     */
    public static long random(long... array) {
        return array[ initRNG().nextInt(array.length) ];
    }
    
    /**
     * Returns a random member of an array.
     * 
     * @param array the array
     */
    public static int random(int... array) {
        return array[ initRNG().nextInt(array.length) ];
    }
    
    /**
     * Returns a random member of an array.
     * 
     * @param array the array
     */
    public static short random(short... array) {
        return array[ initRNG().nextInt(array.length) ];
    }
    
    /**
     * Returns a random member of an array.
     * 
     * @param array the array
     */
    public static byte random(byte... array) {
        return array[ initRNG().nextInt(array.length) ];
    }
    
    /**
     * Returns a random member of an array.
     * 
     * @param array the array
     */
    public static char random(char... array) {
        return array[ initRNG().nextInt(array.length) ];
    }
    
    /**
     * Returns a random member of an array.
     * 
     * @param array the array
     */
    public static double random(double... array) {
        return array[ initRNG().nextInt(array.length) ];
    }
    
    /**
     * Returns a random member of an array.
     * 
     * @param array the array
     */
    public static float random(float... array) {
        return array[ initRNG().nextInt(array.length) ];
    }
    
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
    
    public static <T> T[] subArray(T[] array, int min, int lim) {
        if (min < 0) throw new NegativeArraySizeException("min < 0");
        if (lim < 0) throw new NegativeArraySizeException("lim < 0");
        if (min >= lim) return newInstance(array.getClass().getComponentType(), 0);
        
        int length = lim-min;
        T[] result = newInstance(array.getClass().getComponentType(), length);
        System.arraycopy(array, min, result, 0, length);
        
        return result;	
    }
    
    public static long[] subArray(long[] array, int min, int lim) {
        if (min < 0) throw new NegativeArraySizeException("min < 0");
        if (lim < 0) throw new NegativeArraySizeException("lim < 0");
        if (min > lim) return new long[0];
        
        int length = lim-min;
        long[] result = new long[length];
        System.arraycopy(array, min, result, 0, length);
        
        return result;	
    }
    
    public static int[] subArray(int[] array, int min, int lim) {
        if (lim < 0) throw new NegativeArraySizeException("lim < 0");
        if (min > lim) return new int[0];
        
        int length = lim-min;
        int[] result = new int[length];
        System.arraycopy(array, min, result, 0, length);
        
        return result;
    }
    
    public static short[] subArray(short[] array, int min, int lim) {
        if (lim < 0) throw new NegativeArraySizeException("lim < 0");
        if (min > lim) return new short[0];
        
        int length = lim-min;
        short[] result = new short[length];
        System.arraycopy(array, min, result, 0, length);
        
        return result;
    }
    
    public static char[] subArray(char[] array, int min, int lim) {
        if (lim < 0) throw new NegativeArraySizeException("lim < 0");
        if (min > lim) return new char[0];
        
        int length = lim-min;
        char[] result = new char[length];
        System.arraycopy(array, min, result, 0, length);
        
        return result;
    }
    
    public static byte[] subArray(byte[] array, int min, int lim) {
        if (lim < 0) throw new NegativeArraySizeException("lim < 0");
        if (min > lim) return new byte[0];
        
        int length = lim-min;
        byte[] result = new byte[length];
        System.arraycopy(array, min, result, 0, length);
        
        return result;
    }
    
    public static double[] subArray(double[] array, int min, int lim) {
        if (lim < 0) throw new NegativeArraySizeException("lim < 0");
        if (min > lim) return new double[0];
        
        int length = lim-min;
        double[] result = new double[length];
        System.arraycopy(array, min, result, 0, length);
        
        return result;
    }
    
    public static float[] subArray(float[] array, int min, int lim) {
        if (lim < 0) throw new NegativeArraySizeException("lim < 0");
        if (min > lim) return new float[0];
        
        int length = lim-min;
        float[] result = new float[length];
        System.arraycopy(array, min, result, 0, length);
        
        return result;
    }
    
    public static <T> T[] subArray(T[] array, int min) {
        return subArray(array, min, array.length);
    }
    
    public static long[] subArray(long[] array, int min) {
        return subArray(array, min, array.length);
    }
    
    public static int[] subArray(int[] array, int min) {
        return subArray(array, min, array.length);
    }
    
    public static short[] subArray(short[] array, int min) {
        return subArray(array, min, array.length);
    }
    
    public static char[] subArray(char[] array, int min) {
        return subArray(array, min, array.length);
    }
    
    public static double[] subArray(double[] array, int min) {
        return subArray(array, min, array.length);
    }
    
    public static float[] subArray(float[] array, int min) {
        return subArray(array, min, array.length);
    }

}
