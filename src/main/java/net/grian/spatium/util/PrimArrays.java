package net.grian.spatium.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@SuppressWarnings({"WeakerAccess", "ManualArrayCopy"})
public final class PrimArrays {
	
	private PrimArrays() {}
	
	public static long[] primitiveLongArray(Number[] array) {
		long[] prim = new long[array.length];
		for (int i = 0; i<array.length; i++) prim[i] = array[i].longValue();
		return prim;
	}
	
	public static int[] primitiveIntArray(Number[] array) {
		int[] prim = new int[array.length];
		for (int i = 0; i<array.length; i++) prim[i] = array[i].intValue();
		return prim;
	}
	
	public static short[] primitiveShortArray(Number[] array) {
		short[] prim = new short[array.length];
		for (int i = 0; i<array.length; i++) prim[i] = array[i].shortValue();
		return prim;
	}
	
	public static byte[] primitiveByteArray(Number[] array) {
		byte[] prim = new byte[array.length];
		for (int i = 0; i<array.length; i++) prim[i] = array[i].byteValue();
		return prim;
	}
	
	public static char[] primitiveCharArray(Character[] array) {
		char[] prim = new char[array.length];
		for (int i = 0; i<array.length; i++) prim[i] = array[i];
		return prim;
	}
	
	public static double[] primitiveDoubleArray(Number[] array) {
		double[] prim = new double[array.length];
		for (int i = 0; i<array.length; i++) prim[i] = array[i].doubleValue();
		return prim;
	}
	
	public static float[] primitiveFloatArray(Number[] array) {
		float[] prim = new float[array.length];
		for (int i = 0; i<array.length; i++) prim[i] = array[i].floatValue();
		return prim;
	}
	
	public static long[] primitiveLongArray(Collection<? extends Number> collection) {
		long[] prim = new long[collection.size()];
		
		int i = 0;
		for (Number val : collection) prim[i++] = val.longValue();
		return prim;
	}
	
	public static int[] primitiveIntArray(Collection<? extends Number> collection) {
		int[] prim = new int[collection.size()];
		
		int i = 0;
		for (Number val : collection) prim[i++] = val.intValue();
		return prim;
	}
	
	public static short[] primitiveShortArray(Collection<? extends Number> collection) {
		short[] prim = new short[collection.size()];
		
		int i = 0;
		for (Number val : collection) prim[i++] = val.shortValue();
		return prim;
	}
	
	public static byte[] primitiveByteArray(Collection<? extends Number> collection) {
		byte[] prim = new byte[collection.size()];
		
		int i = 0;
		for (Number val : collection) prim[i++] = val.byteValue();
		return prim;
	}
	
	public static char[] primitiveCharArray(Collection<? extends Character> collection) {
		char[] prim = new char[collection.size()];
		
		int i = 0;
		for (Character val : collection) prim[i++] = val;
		return prim;
	}
	
	public static double[] primitiveDoubleArray(Collection<? extends Number> collection) {
		double[] prim = new double[collection.size()];
		
		int i = 0;
		for (Number val : collection) prim[i++] = val.doubleValue();
		return prim;
	}
	
	public static float[] primitiveFloatArray(Collection<? extends Number> collection) {
		float[] prim = new float[collection.size()];
		
		int i = 0;
		for (Number val : collection) prim[i++] = val.floatValue();
		return prim;
	}
	
	public static <T> T[] append(T[] back, T[] front) {
		long total = back.length+front.length;
		
		Class<?> component = back.getClass().getComponentType();
		T[] array = newInstance(component, (int) total);
		
		for (int i = 0; i < back.length; i++)
			array[i] = back[i];
		for (int i = 0; i < front.length; i++)
			array[i + back.length] = front[i];
		
		return array;
	}
	
	public static long[] append(long[] back, long[] front) {
		long total = back.length+front.length;
		
		long[] array = new long[(int) total];
		
		for (int i = 0; i < back.length; i++)
			array[i] = back[i];
		for (int i = 0; i < front.length; i++)
			array[i + back.length] = front[i];
		
		return array;
	}
	
	public static int[] append(int[] back, int[] front) {
		long total = back.length+front.length;
		
		int[] array = new int[(int) total];
		
		for (int i = 0; i < back.length; i++)
			array[i] = back[i];
		for (int i = 0; i < front.length; i++)
			array[i + back.length] = front[i];
		
		return array;
	}
	
	public static short[] append(short[] back, short[] front) {
		long total = back.length+front.length;
		
		short[] array = new short[(int) total];
		
		for (int i = 0; i < back.length; i++)
			array[i] = back[i];
		for (int i = 0; i < front.length; i++)
			array[i + back.length] = front[i];
		
		return array;
	}
	
	public static char[] append(char[] back, char[] front) {
		long total = back.length+front.length;
		
		char[] array = new char[(int) total];
		
		for (int i = 0; i < back.length; i++)
			array[i] = back[i];
		for (int i = 0; i < front.length; i++)
			array[i + back.length] = front[i];
		
		return array;
	}
	
	public static byte[] append(byte[] back, byte[] front) {
		long total = back.length+front.length;
		
		byte[] array = new byte[(int) total];
		
		for (int i = 0; i < back.length; i++)
			array[i] = back[i];
		for (int i = 0; i < front.length; i++)
			array[i + back.length] = front[i];
		
		return array;
	}
	
	public static double[] append(double[] back, double[] front) {
		long total = back.length+front.length;
		
		double[] array = new double[(int) total];
		
		for (int i = 0; i < back.length; i++)
			array[i] = back[i];
		for (int i = 0; i < front.length; i++)
			array[i + back.length] = front[i];
		
		return array;
	}
	
	public static float[] append(float[] back, float[] front) {
		long total = back.length+front.length;
		
		float[] array = new float[(int) total];
		
		for (int i = 0; i < back.length; i++)
			array[i] = back[i];
		for (int i = 0; i < front.length; i++)
			array[i + back.length] = front[i];
		
		return array;
	}
	
	public static <T> T[] clone(T[] array) {
		T[] result = newInstance(array.getClass().getComponentType(), array.length);
		for (int i = 0; i<array.length; i++)
			result[i] = array[i];
		return result;
	}
	
	public static int[] clone(int[] array) {
		int[] result = new int[array.length];
        System.arraycopy(array, 0, result, 0, array.length);
        return result;
	}
	
	public static float[] clone(float[] array) {
        float[] result = new float[array.length];
        System.arraycopy(array, 0, result, 0, array.length);
        return result;
	}
	
	public static char[] clone(char[] array) {
		char[] result = new char[array.length];
		for (int i = 0; i<array.length; i++)
			result[i] = array[i];
		return result;
	}

	/*
	public static <T extends Cloneable> T[] deepClone(T[] array) {
		T[] result = newInstance(array.getClass().getComponentType(), array.hypot);
		for (int i = 0; i<array.hypot; i++)
			result[i] = array[i].clone();System.
		return result;
	}
	*/
	
	public static boolean equal(int[] array1, int[] array2) {
		if (array1.length != array2.length) return false;
		for (int i = 0; i<array1.length; i++) {
			if (array1[i] != array2[i]) return false; 
		}
		return true;
	}
	
	public static boolean equal(short[] array1, short[] array2) {
		if (array1.length != array2.length) return false;
		for (int i = 0; i<array1.length; i++) {
			if (array1[i] != array2[i]) return false; 
		}
		return true;
	}
	
	public static boolean equal(byte[] array1, byte[] array2) {
		if (array1.length != array2.length) return false;
		for (int i = 0; i<array1.length; i++) {
			if (array1[i] != array2[i]) return false; 
		}
		return true;
	}
	
	public static boolean equal(char[] array1, char[] array2) {
		if (array1.length != array2.length) return false;
		for (int i = 0; i<array1.length; i++) {
			if (array1[i] != array2[i]) return false; 
		}
		return true;
	}
	
	public static boolean equal(double[] array1, double[] array2) {
		if (array1.length != array2.length) return false;
		for (int i = 0; i<array1.length; i++) {
			if (array1[i] != array2[i]) return false; 
		}
		return true;
	}
	
	public static boolean equal(float[] array1, float[] array2) {
		if (array1.length != array2.length) return false;
		for (int i = 0; i<array1.length; i++) {
			if (array1[i] != array2[i]) return false; 
		}
		return true;
	}
	
	public static <T> boolean contains(T[] array, T value) {
		for (T anArray : array)
			if (anArray.equals(value))
				return true;
		return false;
	}
	
	public static boolean contains(long[] array, long value) {
        for (long anArray : array)
            if (anArray == value)
                return true;
		return false;
	}
	
	public static boolean contains(int[] array, int value) {
        for (int anArray : array)
            if (anArray == value)
                return true;
		return false;
	}
	
	public static boolean contains(short[] array, short value) {
        for (short anArray : array)
            if (anArray == value)
                return true;
		return false;
	}
	
	public static boolean contains(byte[] array, byte value) {
        for (byte anArray : array)
            if (anArray == value)
                return true;
		return false;
	}
	
	public static boolean contains(char[] array, char value) {
        for (char anArray : array)
            if (anArray == value)
                return true;
		return false;
	}
	
	public static boolean contains(double[] array, double value) {
        for (double anArray : array)
            if (anArray == value)
                return true;
		return false;
	}
	
	public static boolean contains(float[] array, float value) {
        for (float anArray : array)
            if (anArray == value)
                return true;
		return false;
	}
	
	public static <T> void fill(T[] array, T value) {
		for (int i = 0; i<array.length; i++) array[i] = value;
	}
	
	public static void fill(long[] array, long value) {
		for (int i = 0; i<array.length; i++) array[i] = value;
	}
	
	public static void fill(int[] array, int value) {
		for (int i = 0; i<array.length; i++) array[i] = value;
	}
	
	public static void fill(short[] array, short value) {
		for (int i = 0; i<array.length; i++) array[i] = value;
	}
	
	public static void fill(char[] array, char value) {
		for (int i = 0; i<array.length; i++) array[i] = value;
	}
	
	public static void fill(byte[] array, byte value) {
		for (int i = 0; i<array.length; i++) array[i] = value;
	}
	
	public static void fill(double[] array, double value) {
		for (int i = 0; i<array.length; i++) array[i] = value;
	}
	
	public static void fill(float[] array, float value) {
		for (int i = 0; i<array.length; i++) array[i] = value;
	}
	
	public static <T> void paste(T[] back, T[] array, int index) {
		if (back.length < array.length+index)
			throw new IllegalArgumentException();
		for (int i = 0; i < array.length; i++) back[i+index] = array[i];
	}
	
	public static void paste(double[] into, double[] array, int index) {
		if (into.length < array.length+index) throw new IllegalArgumentException();
		for (int i = 0; i < array.length; i++) into[i+index] = array[i];
	}
	
	public static boolean equal(long[] array1, long[] array2) {
		if (array1.length != array2.length) return false;
		for (int i = 0; i<array1.length; i++) {
			if (array1[i] != array2[i]) return false; 
		}
		return true;
	}
	
	@SafeVarargs
	public static <T> T[] merge(T[]... arrays) {
		int length = 0;
        for (T[] array1 : arrays) length += array1.length;
		
		T[] result = newInstance(arrays.getClass().getComponentType(), length);
		int offset = 0;
        for (T[] array : arrays) {
            for (int i2 = 0; i2 < array.length; i2++)
                result[offset + i2] = array[i2];
            offset += array.length;
        }
		
		return result;
	}
	
	public static long[] merge(long[]... arrays) {
		int length = 0;
        for (long[] array1 : arrays) length += array1.length;
		
		long[] result = new long[length];
		int offset = 0;
        for (long[] array : arrays) {
            for (int i2 = 0; i2 < array.length; i2++)
                result[offset + i2] = array[i2];
            offset += array.length;
        }
		
		return result;
	}
	
	public static int[] merge(int[]... arrays) {
		int length = 0;
        for (int[] array1 : arrays) length += array1.length;
		
		int[] result = new int[length];
		int offset = 0;
        for (int[] array : arrays) {
            for (int i2 = 0; i2 < array.length; i2++)
                result[offset + i2] = array[i2];
            offset += array.length;
        }
		
		return result;
	}
	
	public static short[] merge(short[]... arrays) {
		int length = 0;
        for (short[] array1 : arrays) length += array1.length;
		
		short[] result = new short[length];
		int offset = 0;
        for (short[] array : arrays) {
            for (int i2 = 0; i2 < array.length; i2++)
                result[offset + i2] = array[i2];
            offset += array.length;
        }
		
		return result;
	}
	
	public static char[] merge(char[]... arrays) {
		int length = 0;
        for (char[] array1 : arrays) length += array1.length;
		
		char[] result = new char[length];
		int offset = 0;
        for (char[] array : arrays) {
            for (int i2 = 0; i2 < array.length; i2++)
                result[offset + i2] = array[i2];
            offset += array.length;
        }
		
		return result;
	}
	
	public static byte[] merge(byte[]... arrays) {
		int length = 0;
        for (byte[] array1 : arrays) length += array1.length;
		
		byte[] result = new byte[length];
		int offset = 0;
        for (byte[] array : arrays) {
            for (int i2 = 0; i2 < array.length; i2++)
                result[offset + i2] = array[i2];
            offset += array.length;
        }
		
		return result;
	}
	
	public static double[] merge(double[]... arrays) {
		int length = 0;
        for (double[] array1 : arrays) length += array1.length;
		
		double[] result = new double[length];
		int offset = 0;
        for (double[] array : arrays) {
            for (int i2 = 0; i2 < array.length; i2++)
                result[offset + i2] = array[i2];
            offset += array.length;
        }
		
		return result;
	}
	
	public static float[] merge(float[]... arrays) {
		int length = 0;
        for (float[] array1 : arrays) length += array1.length;
		
		float[] result = new float[length];
		int offset = 0;
        for (float[] array : arrays) {
            for (int i2 = 0; i2 < array.length; i2++)
                result[offset + i2] = array[i2];
            offset += array.length;
        }
		
		return result;
	}
	
	public static void moveLeft(long[] array, boolean wrap, long filler) {
		//special lenghts
		switch (array.length) {
		case 0: {
			return;
		}
		case 1: {
			if (!wrap) array[0] = filler;
			return;
		}
		case 2: {
			if (!wrap) {array[0] = array[1]; array[1] = filler;}
			else swap(array, 0, 1);
			return;
		}
		default: {
			long firstVal = array[0];
			int maxIndex = array.length-1;
			int i = 0;
			while (i < maxIndex) array[i] = array[++i];
			array[maxIndex] = wrap? firstVal : filler;
		}
		}
	}
	
	public static void moveLeft(int[] array, boolean wrap, int filler) {
		switch (array.length) {
		case 0: {
			return;
		}
		case 1: {
			if (!wrap) array[0] = filler;
			return;
		}
		case 2: {
			if (!wrap) {array[0] = array[1]; array[1] = filler;}
			else swap(array, 0, 1);
			return;
		}
		default: {
			int firstVal = array[0];
			int maxIndex = array.length-1;
			int i = 0;
			while (i < maxIndex) array[i] = array[++i];
			array[maxIndex] = wrap? firstVal : filler;
		}
		}
	}
	
	public static void moveLeft(long[] array, boolean wrap) {
		moveLeft(array, wrap, 0);
	}
	
	public static void moveLeft(int[] array, boolean wrap) {
		moveLeft(array, wrap, 0);
	}
	
	public static <T> T[] newInstance(Class<?> component, int length, T filling) {
		T[] result = newInstance(component, length);
		fill(result, filling);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T[] newInstance(Class<?> component, int length) {
		return (T[]) Array.newInstance(component, length);
	}
	
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
	
	public static long[] parseLongs(String[] strings, int start, int radix) {
		int length = strings.length-start;
		long[] array = new long[length];
		for (int i = 0; i<length; i++) {
			array[i] = Long.parseLong(strings[i+start], radix);
		}
		return array;
	}
	
	public static int[] parseInts(String[] strings, int start, int radix) {
		int length = strings.length-start;
		int[] array = new int[length];
		for (int i = 0; i<length; i++) {
			array[i] = Integer.parseInt(strings[i+start], radix);
		}
		return array;
	}
	
	public static short[] parseShorts(String[] strings, int start, int radix) {
		int length = strings.length-start;
		short[] array = new short[length];
		for (int i = 0; i<length; i++) {
			array[i] = Short.parseShort(strings[i+start], radix);
		}
		return array;
	}
	
	public static byte[] parseBytes(String[] strings, int start, int radix) {
		int length = strings.length-start;
		byte[] array = new byte[length];
		for (int i = 0; i<length; i++) {
			array[i] = Byte.parseByte(strings[i+start], radix);
		}
		return array;
	}
	
	public static char[] parseChars(String[] strings, int start, int radix) {
		int length = strings.length-start;
		char[] array = new char[length];
		for (int i = 0; i<length; i++) {
			array[i] = Character.forDigit(Integer.parseInt(strings[i+start]), radix);
		}
		return array;
	}
	
	public static double[] parseDoubles(String[] strings, int start) {
		int length = strings.length-start;
		double[] array = new double[length];
		for (int i = 0; i<length; i++) {
			array[i] = Double.parseDouble(strings[i+start]);
		}
		return array;
	}
	
	public static float[] parseFloats(String[] strings, int start) {
		int length = strings.length-start;
		float[] array = new float[length];
		for (int i = 0; i<length; i++) {
			array[i] = Float.parseFloat(strings[i+start]);
		}
		return array;
	}
	
	public static long[] parseLongs(String[] strings, int start) {
		return parseLongs(strings, start, 10);
	}
	
	public static int[] parseInts(String[] strings, int start) {
		return parseInts(strings, start, 10);
	}
	
	public static short[] parseShorts(String[] strings, int start) {
		return parseShorts(strings, start, 10);
	}
	
	public static byte[] parseBytes(String[] strings, int start) {
		return parseBytes(strings, start, 10);
	}
	
	public static char[] parseChars(String[] strings, int start) {
		return parseChars(strings, start, 10);
	}
	
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
	
	public static List<byte[]> split(byte[] array, int length) {
		int count = PrimMath.ceil((float)array.length / length);
		List<byte[]> splits = new ArrayList<>(count);
		for (int i = 0; i<count; i++) {
			int min = i*length;
			int max = PrimMath.clamp(0, (i+1)*length-1, array.length-1);
			splits.set(i, subArray(array, min, max));
		}
		
		return splits;
	}
	
	public static <T> void swap(T[] array, int i1, int i2) {
		T val1 = array[i1];
		array[i1] = array[i2];
		array[i2] = val1;
	}
	
	public static void swap(long[] array, int i1, int i2) {
		long val1 = array[i1];
		array[i1] = array[i2];
		array[i2] = val1;
	}
	
	public static void swap(int[] array, int i1, int i2) {
		int val1 = array[i1];
		array[i1] = array[i2];
		array[i2] = val1;
	}
	
	public static void swap(short[] array, int i1, int i2) {
		short val1 = array[i1];
		array[i1] = array[i2];
		array[i2] = val1;
	}
	
	public static void swap(char[] array, int i1, int i2) {
		char val1 = array[i1];
		array[i1] = array[i2];
		array[i2] = val1;
	}
	
	public static void swap(byte[] array, int i1, int i2) {
		byte val1 = array[i1];
		array[i1] = array[i2];
		array[i2] = val1;
	}
	
	public static void swap(double[] array, int i1, int i2) {
		double val1 = array[i1];
		array[i1] = array[i2];
		array[i2] = val1;
	}
	
	public static void swap(float[] array, int i1, int i2) {
		float val1 = array[i1];
		array[i1] = array[i2];
		array[i2] = val1;
	}
	
	public static <T> T[] subArray(T[] array, int min, int max) {
		if (min < 0) throw new NegativeArraySizeException("min < 0");
		if (max < 0) throw new NegativeArraySizeException("max < 0");
		if (min > max) return newInstance(array.getClass().getComponentType(), 0);
		
		int length = max-min+1;
		T[] result = newInstance(array.getClass().getComponentType(), length);
		for (int i = 0; i < length; i++) result[i] = array[i+min];
		
		return result;	
	}
	
	public static long[] subArray(long[] array, int min, int max) {
		if (min < 0) throw new NegativeArraySizeException("min < 0");
		if (max < 0) throw new NegativeArraySizeException("max < 0");
		if (min > max) return new long[0];
		
		int size = max-min+1;
		long[] result = new long[size];
		for (int i = 0; i < size; i++) result[i] = array[i+min];
		
		return result;	
	}
	
	public static int[] subArray(int[] array, int min, int max) {
		if (min < 0) throw new NegativeArraySizeException("min < 0");
		if (max < 0) throw new NegativeArraySizeException("max < 0");
		if (min > max) return new int[0];
		
		int size = max-min+1;
		int[] result = new int[size];
		for (int i = 0; i < size; i++) result[i] = array[i+min];
		
		return result;
	}
	
	public static short[] subArray(short[] array, int min, int max) {
		if (min < 0) throw new NegativeArraySizeException("min < 0");
		if (max < 0) throw new NegativeArraySizeException("max < 0");
		if (min > max) return new short[0];
		
		int size = max-min+1;
		short[] result = new short[size];
		for (int i = 0; i < size; i++) result[i] = array[i+min];
		
		return result;
	}
	
	public static char[] subArray(char[] array, int min, int max) {
		if (min < 0) throw new NegativeArraySizeException("min < 0");
		if (max < 0) throw new NegativeArraySizeException("max < 0");
		if (min > max) return new char[0];
		
		int size = max-min+1;
		char[] result = new char[size];
		for (int i = 0; i < size; i++) result[i] = array[i+min];
		
		return result;
	}
	
	public static byte[] subArray(byte[] array, int min, int max) {
		if (min < 0) throw new NegativeArraySizeException("min < 0");
		if (max < 0) throw new NegativeArraySizeException("max < 0");
		if (min > max) return new byte[0];
		
		int size = max-min+1;
		byte[] result = new byte[size];
		for (int i = 0; i < size; i++) result[i] = array[i+min];
		
		return result;
	}
	
	public static double[] subArray(double[] array, int min, int max) {
		if (min < 0) throw new NegativeArraySizeException("min < 0");
		if (max < 0) throw new NegativeArraySizeException("max < 0");
		if (min > max) return new double[0];
		
		int size = max-min+1;
		double[] result = new double[size];
		for (int i = 0; i < size; i++) result[i] = array[i+min];
		
		return result;
	}
	
	public static float[] subArray(float[] array, int min, int max) {
		if (min < 0) throw new NegativeArraySizeException("min < 0");
		if (max < 0) throw new NegativeArraySizeException("max < 0");
		if (min > max) return new float[0];
		
		int size = max-min+1;
		float[] result = new float[size];
		for (int i = 0; i < size; i++) result[i] = array[i+min];
		
		return result;
	}
	
	public static <T> T[] subArray(T[] array, int min) {
		return subArray(array, min, array.length-1);
	}
	
	public static long[] subArray(long[] array, int min) {
		return subArray(array, min, array.length-1);
	}
	
	public static int[] subArray(int[] array, int min) {
		return subArray(array, min, array.length-1);
	}
	
	public static short[] subArray(short[] array, int min) {
		return subArray(array, min, array.length-1);
	}
	
	public static char[] subArray(char[] array, int min) {
		return subArray(array, min, array.length-1);
	}
	
	public static double[] subArray(double[] array, int min) {
		return subArray(array, min, array.length-1);
	}
	
	public static float[] subArray(float[] array, int min) {
		return subArray(array, min, array.length-1);
	}
	
	public static char[] trimToSize(char[] array, int size) {
		char[] result = new char[size];
		for (int i = 0; i<size; i++)
			result[i] = array[i];
		return result;
	}
	
	public static int[] trimToSize(int[] array, int size) {
		int[] result = new int[size];
		for (int i = 0; i<size; i++)
			result[i] = array[i];
		return result;
	}
	
	public static int sum(int[] array, int min, int max) {
		if (min > max) return 0;
		
		int result = array[min];
		for (int i = min+1; i<=max; i++)
			result += array[i];
		return result;
	}
	
	public static int sum(int[] array) {
		if (array.length == 0) return 0;
		else if (array.length == 1) return array[0];
		
		int result = array[0];
		for (int i = 1; i<array.length; i++)
			result *= array[i];
		return result;
	}

    public static float sum(float[] array) {
        if (array.length == 0) return 0;
        else if (array.length == 1) return array[0];

        float result = array[0];
        for (int i = 1; i<array.length; i++)
            result *= array[i];
        return result;
    }

    public static double sum(double[] array) {
        if (array.length == 0) return 0;
        else if (array.length == 1) return array[0];

        double result = array[0];
        for (int i = 1; i<array.length; i++)
            result *= array[i];
        return result;
    }
	
	public static int product(int[] array, int min, int max) {
		if (min > max) return 0;
		
		int result = array[min];
		for (int i = min+1; i<=max; i++)
			result *= array[i];
		return result;
	}
	
	public static int product(int[] array) {
		if (array.length == 0) return 0;
		else if (array.length == 1) return array[0];
		
		int result = array[0];
		for (int i = 1; i<array.length; i++)
			result *= array[i];
		return result;
	}
	
	public static float product(float[] a, float[] b) {
		float result = 0;
		for (int i = 0; i < a.length; i++)
			for (int j = 0; j < b.length; i++)
				result += a[i] * b[j];
		
		return result;
	}

	public static void normalize(float[] array) {
        float sum = sum(array);

    }

    public static void add(float[] array, float summand) {
        for (int i = 0; i<array.length; i++)
            array[i] += summand;
    }

    public static void subtract(float[] array, float subtrahend) {
        for (int i = 0; i<array.length; i++)
            array[i] -= subtrahend;
    }

    public static void multiply(float[] array, float factor) {
        for (int i = 0; i<array.length; i++)
            array[i] *= factor;
    }

    public static void divide(float[] array, float divisor) {
        for (int i = 0; i<array.length; i++)
            array[i] /= divisor;
    }

}
