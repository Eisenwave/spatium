package net.grian.spatium.util;

import java.util.Random;

@SuppressWarnings("WeakerAccess")
public final class PrimMath {
	
	private PrimMath() {}
	
	//// FLOOR ////
	public static long floorAsLong(float f) {
		return (f % 1 == 0) ? (long)f : (f < 0) ? (long)--f : (long)f;
	}
	
	public static long floorAsLong(double d) {
		return (d % 1 == 0) ? (long)d : (d < 0) ? (long)--d : (long)d;
	}
	
	public static int floorAsInt(float f) {
		return (f % 1 == 0) ? (int)f : (f < 0) ? (int)--f : (int)f;
	}
	
	public static int floorAsInt(double d) {
		return (d % 1 == 0) ? (int)d : (d < 0) ? (int)--d : (int)d;
	}
	
	public static short floorAsShort(float f) {
		return (f % 1 == 0) ? (short)f : (f < 0) ? (short)--f : (short)f;
	}
	
	public static short floorAsShort(double d) {
		return (d % 1 == 0) ? (short)d : (d < 0) ? (short)--d : (short)d;
	}

	public static byte floorAsByte(float f) {
		return (f % 1 == 0) ? (byte)f : (f < 0) ? (byte)--f : (byte)f;
	}
	
	public static byte floorAsByte(double d) {
		return (d % 1 == 0) ? (byte)d : (d < 0) ? (byte)--d : (byte)d;
	}
	
	//// CEIL ////
	public static long ceilAsLong(float f) {
		return (f % 1 == 0) ? (long)f : (f < 0) ? (long)f : (long)++f;
	}
	
	public static long ceilAsLong(double d) {
		return (d % 1 == 0) ? (long)d : (d < 0) ? (long)d : (long)++d;
	}
	
	public static int ceilAsInt(float f) {
		return (f % 1 == 0) ? (int)f : (f < 0) ? (int)f : (int)++f;
	}
	
	public static int ceilAsInt(double d) {
		return (d % 1 == 0) ? (int)d : (d < 0) ? (int)d : (int)++d;
	}
	
	public static short ceilAsShort(float f) {
		return (f % 1 == 0) ? (short)f : (f < 0) ? (short)f : (short)++f;
	}
	
	public static short ceilAsShort(double d) {
		return (d % 1 == 0) ? (short)d : (d < 0) ? (short)d : (short)++d;
	}
	
	public static byte ceilAsByte(float f) {
		return (f % 1 == 0) ? (byte)f : (f < 0) ? (byte)f : (byte)++f;
	}
	
	public static byte ceilAsByte(double d) {
		return (d % 1 == 0) ? (byte)d : (d < 0) ? (byte)d : (byte)++d;
	}
	
	//// ROUND ////
	public static long roundAsLong(float f) {
		return (f < 0) ? (long) (f-0.5f) : (long) (f+0.5f);
	}
	
	public static long roundAsLong(double d) {
		return (d < 0) ? (long) (d-0.5d) : (long) (d+0.5d);
	}
	
	public static int roundAsInt(float f) {
		return (f < 0) ? (int) (f-0.5f) : (int) (f+0.5f);
	}
	
	public static int roundAsInt(double d) {
		return (d < 0) ? (int) (d-0.5d) : (int) (d+0.5d);
	}
	
	public static short roundAsShort(float f) {
		return (f < 0) ? (short) (f-0.5f) : (short) (f+0.5f);
	}
	
	public static short roundAsShort(double d) {
		return (d < 0) ? (short) (d-0.5d) : (short) (d+0.5d);
	}
	
	public static byte roundAsByte(float f) {
		return (f < 0) ? (byte) (f-0.5f) : (byte) (f+0.5f);
	}
	
	public static byte roundAsByte(double d) {
		return (d < 0) ? (byte) (d-0.5d) : (byte) (d+0.5d);
	}
	
	//// AVERAGE ////
	public static long averageLong(long...array) {
		return sumAsLong(array) / array.length;
	}
	
	public static int averageInt(int...array) {
		return (int) (sumAsLong(array) / array.length);
	}
	
	public static short averageShort(short...array) {
		return (short) (sumAsInt(array) / array.length);
	}
	
	public static byte averageByte(byte...array) {
		return (byte) (sumAsInt(array) / array.length);
	}
	
	//// MIN ////
	public static long minLong(long a, long b) {
		return a<=b? a : b;
	}
	
	public static int minInt(int a, int b) {
		return a<=b? a : b;
	}
	
	public static short minShort(short a, short b) {
		return a<=b? a : b;
	}
	
	//// MAX ////
	public static long maxLong(long a, long b) {
		return a>=b? a : b;
	}
	
	public static int maxInt(int a, int b) {
		return a>=b? a : b;
	}
	
	public static short maxShort(short a, short b) {
		return a>=b? a : b;
	}
	
	//// SUM ////
	public static long sumAsLong(long...array) {
		long sum = 0;
		for (long val : array) sum += val;
		return sum;
	}
	
	public static long sumAsLong(int...array) {
		long sum = 0;
		for (int val : array) sum += val;
		return sum;
	}
	
	public static long sumAsLong(short...array) {
		long sum = 0;
		for (short val : array) sum += val;
		return sum;
	}
	
	public static long sumAsLong(byte...array) {
		long sum = 0;
		for (byte val : array) sum += val;
		return sum;
	}
	
	public static int sumAsInt(int...array) {
		int sum = 0;
		for (int val : array) sum += val;
		return sum;
	}
	
	public static int sumAsInt(short...array) {
		int sum = 0;
		for (short val : array) sum += val;
		return sum;
	}
	
	public static int sumAsInt(byte...array) {
		int sum = 0;
		for (byte val : array) sum += val;
		return sum;
	}
	
	public static short sumAsShort(short...array) {
		short sum = 0;
		for (short val : array) sum += val;
		return sum;
	}
	
	public static short sumAsShort(byte...array) {
		short sum = 0;
		for (byte val : array) sum += val;
		return sum;
	}
	
	public static double sumAsDouble(double...array) {
		double sum = 0;
		for (double val : array) sum += val;
		return sum;
	}
	
	public static double sumAsDouble(float...array) {
		double sum = 0;
		for (float val : array) sum += val;
		return sum;
	}
	
	public static float sumAsFloat(float...array) {
		float sum = 0;
		for (float val : array) sum += val;
		return sum;
	}
	
	private static Random RNG;

    private static synchronized Random initRNG() {
        Random rnd = RNG;
        return (rnd == null) ? (RNG = new Random()) : rnd;
    }
	
	//// RANDOM ////
	public static long randomLong(long max) {
		return (long) (initRNG().nextDouble() * max);
	}
	
	public static long randomLong(long min, long max) {
		if (min > max) throw new IllegalArgumentException("min > max"); 
		return min + randomLong(max - min);
	}
	
	public static int randomInt(int max) {
		return (int) (initRNG().nextDouble() * max);
	}
	
	public static int randomInt(int min, int max) {
		if (min > max) throw new IllegalArgumentException("min > max"); 
		return min + randomInt(max - min);
	}
	
	public static char randomChar(char max) {
		return (char) (initRNG().nextDouble() * max);
	}
	
	public static char randomChar(char min, char max) {
		if (min > max) throw new IllegalArgumentException("min > max"); 
		return (char) (min + randomChar((char) (max - min)));
	}
	
	public static short randomShort(short max) {
		return (short) (initRNG().nextDouble() * max);
	}
	
	public static short randomShort(short min, short max) {
		if (min > max) throw new IllegalArgumentException("min > max"); 
		return (short) (min + randomShort((short) (max - min)));
	}
	
	public static byte randomByte(byte max) {
		return (byte) (initRNG().nextDouble() * max);
	}
	
	public static byte randomByte(byte min, byte max) {
		if (min > max) throw new IllegalArgumentException("min > max"); 
		return (byte) (min + randomByte((byte) (max - min)));
	}
	
	public static double randomDouble(double max) {
		return initRNG().nextDouble() * max;
	}
	
	public static double randomDouble(double min, double max) {
		if (min > max) throw new IllegalArgumentException("min > max"); 
		return min + randomDouble(max - min);
	}
	
	public static double randomGaussian(double max) {
		return initRNG().nextGaussian() * max;
	}
	
	public static double randomGaussian(double min, double max) {
		if (min > max) throw new IllegalArgumentException("min > max"); 
		return min + randomGaussian(max - min);
	}
	
	public static float randomFloat(float max) {
		return initRNG().nextFloat() * max;
	}
	
	public static float randomFloat(float min, float max) {
		if (min > max) throw new IllegalArgumentException("min > max"); 
		return min + randomFloat(max - min);
	}
	
	//// WRAP ////
	/**
	 * Wraps one number around two other numbers.
	 * When wrapping an overflowed number, the maximum boundary can't be the result,
	 * wheras when wrapping an underflowed number, the minimum boundary can't be the result.
	 * <br><br><b>Examples:</b>
	 * <table>
	 * <table border="1">
	 * <col width="40">
	 * <col width="40">
	 * <col width="40">
	 * <col width="40">
	 * <thead>
	 * <tr><th>Value</th><th>Min</th><th>Max</th><th>Result</th></tr>
	 * <thead>
	 * <tbody>
	 * <tr><td>-1</td><td>0</td><td>7</td><td>7</td></tr>
	 * <tr><td>0</td><td>0</td><td>7</td><td>0</td></tr>
	 * <tr><td>7</td><td>0</td><td>7</td><td>7</td></tr>
	 * <tr><td>8</td><td>0</td><td>7</td><td>0</td></tr>
	 * <tr><td>-1</td><td>0</td><td>1</td><td>-1</td></tr>
	 * <tr><td>0</td><td>0</td><td>1</td><td>0</td></tr>
	 * <tr><td>1</td><td>0</td><td>1</td><td>1</td></tr>
	 * <tr><td>2</td><td>0</td><td>1</td><td>0</td></tr>
	 * </tbody>
	 * </table>
	 * @param val the value
	 * @param min the minimum of the range
	 * @param max the maximum of the range
	 * @throws IllegalArgumentException if min > max
	 */
	public static long wrapLong(long val, long min, long max) {
		if (min > max) throw new IllegalArgumentException("min > max");
		
		final long size = max - min + 1;
		
		if (val < min) {
			val += size * ((min - val) / size + 1);
		}
		
		if (val > max) {
			return min + (val - min) % size;
		}
		else
			return val;
	}
	
	/**
	 * Wraps one number around two other numbers.
	 * When wrapping an overflowed number, the maximum boundary can't be the result,
	 * wheras when wrapping an underflowed number, the minimum boundary can't be the result.
	 * <br><br><b>Examples:</b>
	 * <table>
	 * <table border="1">
	 * <col width="40">
	 * <col width="40">
	 * <col width="40">
	 * <col width="40">
	 * <thead>
	 * <tr><th>Value</th><th>Min</th><th>Max</th><th>Result</th></tr>
	 * <thead>
	 * <tbody>
	 * <tr><td>-1</td><td>0</td><td>7</td><td>7</td></tr>
	 * <tr><td>0</td><td>0</td><td>7</td><td>0</td></tr>
	 * <tr><td>7</td><td>0</td><td>7</td><td>7</td></tr>
	 * <tr><td>8</td><td>0</td><td>7</td><td>0</td></tr>
	 * <tr><td>-1</td><td>0</td><td>1</td><td>-1</td></tr>
	 * <tr><td>0</td><td>0</td><td>1</td><td>0</td></tr>
	 * <tr><td>1</td><td>0</td><td>1</td><td>1</td></tr>
	 * <tr><td>2</td><td>0</td><td>1</td><td>0</td></tr>
	 * </tbody>
	 * </table>
	 * @param val the value
	 * @param min the minimum of the range
	 * @param max the maximum of the range
	 * @throws IllegalArgumentException if min > max
	 */
	public static int wrapInt(int val, int min, int max) {
		if (min > max) throw new IllegalArgumentException("min > max");
		
		final int size = max - min + 1;
		
		if (val < min) {
			val += size * ((min - val) / size + 1);
		}
		
		if (val > max) {
			return min + (val - min) % size;
		}
		else
			return val;
	}
	
	/**
	 * Wraps one number around two other numbers.
	 * When wrapping an overflowed number, the maximum boundary can't be the result,
	 * wheras when wrapping an underflowed number, the minimum boundary can't be the result.
	 * <br><br><b>Examples:</b>
	 * <table>
	 * <table border="1">
	 * <col width="40">
	 * <col width="40">
	 * <col width="40">
	 * <col width="40">
	 * <thead>
	 * <tr><th>Value</th><th>Min</th><th>Max</th><th>Result</th></tr>
	 * <thead>
	 * <tbody>
	 * <tr><td>-1</td><td>0</td><td>7</td><td>7</td></tr>
	 * <tr><td>0</td><td>0</td><td>7</td><td>0</td></tr>
	 * <tr><td>7</td><td>0</td><td>7</td><td>7</td></tr>
	 * <tr><td>8</td><td>0</td><td>7</td><td>0</td></tr>
	 * <tr><td>-1</td><td>0</td><td>1</td><td>-1</td></tr>
	 * <tr><td>0</td><td>0</td><td>1</td><td>0</td></tr>
	 * <tr><td>1</td><td>0</td><td>1</td><td>1</td></tr>
	 * <tr><td>2</td><td>0</td><td>1</td><td>0</td></tr>
	 * </tbody>
	 * </table>
	 * @param val the value
	 * @param min the minimum of the range
	 * @param max the maximum of the range
	 * @throws IllegalArgumentException if min > max
	 */
	public static char wrapChar(char val, char min, char max) {
		if (min > max) throw new IllegalArgumentException("min > max");
		
		final char size = (char) (max - min + 1);
		
		if (val < min) {
			val += size * ((min - val) / size + 1);
		}
		
		if (val > max) {
			return (char) (min + (val - min) % size);
		}
		else
			return val;
	}
	
	/**
	 * Wraps one number around two other numbers.
	 * When wrapping an overflowed number, the maximum boundary can't be the result,
	 * wheras when wrapping an underflowed number, the minimum boundary can't be the result.
	 * <br><br><b>Examples:</b>
	 * <table>
	 * <table border="1">
	 * <col width="40">
	 * <col width="40">
	 * <col width="40">
	 * <col width="40">
	 * <thead>
	 * <tr><th>Value</th><th>Min</th><th>Max</th><th>Result</th></tr>
	 * <thead>
	 * <tbody>
	 * <tr><td>-1</td><td>0</td><td>7</td><td>7</td></tr>
	 * <tr><td>0</td><td>0</td><td>7</td><td>0</td></tr>
	 * <tr><td>7</td><td>0</td><td>7</td><td>7</td></tr>
	 * <tr><td>8</td><td>0</td><td>7</td><td>0</td></tr>
	 * <tr><td>-1</td><td>0</td><td>1</td><td>-1</td></tr>
	 * <tr><td>0</td><td>0</td><td>1</td><td>0</td></tr>
	 * <tr><td>1</td><td>0</td><td>1</td><td>1</td></tr>
	 * <tr><td>2</td><td>0</td><td>1</td><td>0</td></tr>
	 * </tbody>
	 * </table>
	 * @param val the value
	 * @param min the minimum of the range
	 * @param max the maximum of the range
	 * @throws IllegalArgumentException if min > max
	 */
	public static short wrapShort(short val, short min, short max) {
		if (min > max) throw new IllegalArgumentException("min > max");
		
		final short size = (short) (max - min + 1);
		
		if (val < min) {
			val += size * ((min - val) / size + 1);
		}
		
		if (val > max) {
			return (short) (min + (val - min) % size);
		}
		else
			return val;
	}
	
	/**
	 * Wraps one number around two other numbers.
	 * When wrapping an overflowed number, the maximum boundary can't be the result,
	 * wheras when wrapping an underflowed number, the minimum boundary can't be the result.
	 * <br><br><b>Examples:</b>
	 * <table>
	 * <table border="1">
	 * <col width="40">
	 * <col width="40">
	 * <col width="40">
	 * <col width="40">
	 * <thead>
	 * <tr><th>Value</th><th>Min</th><th>Max</th><th>Result</th></tr>
	 * <thead>
	 * <tbody>
	 * <tr><td>-1</td><td>0</td><td>7</td><td>7</td></tr>
	 * <tr><td>0</td><td>0</td><td>7</td><td>0</td></tr>
	 * <tr><td>7</td><td>0</td><td>7</td><td>7</td></tr>
	 * <tr><td>8</td><td>0</td><td>7</td><td>0</td></tr>
	 * <tr><td>-1</td><td>0</td><td>1</td><td>-1</td></tr>
	 * <tr><td>0</td><td>0</td><td>1</td><td>0</td></tr>
	 * <tr><td>1</td><td>0</td><td>1</td><td>1</td></tr>
	 * <tr><td>2</td><td>0</td><td>1</td><td>0</td></tr>
	 * </tbody>
	 * </table>
	 * @param val the value
	 * @param min the minimum of the range
	 * @param max the maximum of the range
	 * @throws IllegalArgumentException if min > max
	 */
	public static byte wrapByte(byte val, byte min, byte max) {
		if (min > max) throw new IllegalArgumentException("min > max");
		
		final byte size = (byte) (max - min + 1);
		
		if (val < min) {
			val += size * ((min - val) / size + 1);
		}
		
		if (val > max) {
			return (byte) (min + (val - min) % size);
		}
		else
			return val;
	}
	
	//// CLAMP ////
	public static long clampLong(long min, long val, long max) {
		if (min > max) throw new IllegalArgumentException("min > max");
		
		return
				(val < min) ? min :
					(val > max) ? max :
						val;
	}
	
	public static int clampInt(int min, int val, int max) {
		if (min > max) throw new IllegalArgumentException("min > max");
		
		return
				(val < min) ? min :
					(val > max) ? max :
						val;
	}
	
	public static short clampShort(short min, short val, short max) {
		if (min > max) throw new IllegalArgumentException("min > max");
		
		return
				(val < min) ? min :
					(val > max) ? max :
						val;
	}
	
	public static byte clampByte(byte min, byte val, byte max) {
		if (min > max) throw new IllegalArgumentException("min > max");
		
		return
				(val < min) ? min :
					(val > max) ? max :
						val;
	}
	
	public static double clampDouble(double min, double val, double max) {
		if (min > max) throw new IllegalArgumentException("min > max");
		
		return
				(val < min) ? min :
					(val > max) ? max :
						val;
	}
	
	public static float clampFloat(float min, float val, float max) {
		if (min > max) throw new IllegalArgumentException("min > max");
		
		return
				(val < min) ? min :
					(val > max) ? max :
						val;
	}
	
	public static char clampChar(char min, char val, char max) {
		if (min > max) throw new IllegalArgumentException("min > max");
		
		return
				(val < min) ? min :
					(val > max) ? max :
						val;
	}

}
