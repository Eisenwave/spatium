package net.grian.spatium.util;

import java.util.Random;

@SuppressWarnings("WeakerAccess")
public final class PrimMath {
	
	private PrimMath() {}
	
	//// FLOOR ////
    public static long floor(double d) {
        return (long) ( (d % 1 == 0)? d : (d < 0) ? --d : d );
    }

    public static int floor(float f) {
        return (int) ( (f % 1 == 0)? f : (f < 0)? --f : f );
    }

    @Deprecated
	public static long floorAsLong(float f) {
		return (f % 1 == 0) ? (long)f : (f < 0) ? (long)--f : (long)f;
	}

    @Deprecated
	public static long floorAsLong(double d) {
		return (d % 1 == 0) ? (long)d : (d < 0) ? (long)--d : (long)d;
	}

	@Deprecated
	public static int floorAsInt(float f) {
		return (int) ( (f % 1 == 0)? f : (f < 0)? --f : f );
	}

    @Deprecated
	public static int floorAsInt(double d) {
		return (d % 1 == 0) ? (int)d : (d < 0) ? (int)--d : (int)d;
	}
	
	//// CEIL ////
    public static long ceil(double d) {
        return (long) ( (d % 1 == 0)? d : (d < 0) ? d : ++d );
    }

    public static int ceil(float f) {
        return (int) ( (f % 1 == 0)? f : (f < 0)? f : ++f );
    }

    @Deprecated
	public static long ceilAsLong(float f) {
		return (f % 1 == 0) ? (long)f : (f < 0) ? (long)f : (long)++f;
	}

    @Deprecated
	public static long ceilAsLong(double d) {
		return (d % 1 == 0) ? (long)d : (d < 0) ? (long)d : (long)++d;
	}

    @Deprecated
	public static int ceilAsInt(float f) {
		return (f % 1 == 0) ? (int)f : (f < 0) ? (int)f : (int)++f;
	}

    @Deprecated
	public static int ceilAsInt(double d) {
		return (d % 1 == 0) ? (int)d : (d < 0) ? (int)d : (int)++d;
	}
	
	//// ROUND ////
    public static long round(double d) {
        return (long) ( (d < 0) ? (d-0.5F) : (d+0.5F) );
    }

    public static int round(float f) {
        return (int) ( (f < 0) ? (f-0.5F) : (f+0.5F) );
    }

    @Deprecated
	public static long roundAsLong(float f) {
		return (f < 0) ? (long) (f-0.5f) : (long) (f+0.5f);
	}

    @Deprecated
	public static long roundAsLong(double d) {
		return (d < 0) ? (long) (d-0.5d) : (long) (d+0.5d);
	}

    @Deprecated
	public static int roundAsInt(float f) {
		return (f < 0) ? (int) (f-0.5f) : (int) (f+0.5f);
	}

    @Deprecated
	public static int roundAsInt(double d) {
		return (d < 0) ? (int) (d-0.5d) : (int) (d+0.5d);
	}
	
	//// AVERAGE ////
	public static long average(long...array) {
		return sum(array) / array.length;
	}
	
	public static int average(int...array) {
		return (int) (sum(array) / array.length);
	}
	
	public static short average(short...array) {
		return (short) (sum(array) / array.length);
	}
	
	public static byte average(byte...array) {
        return (byte) (sum(array) / array.length);
    }

    public static double average(double...array) {
        return sum(array) / array.length;
    }

    public static float average(float...array) {
        return (float) (sum(array) / array.length);
    }

    //SIGNUM
    public static int signum(long x) {
        if (x==0) return 0;
        return x > 0? 1 : -1;
    }

    public static int signum(int x) {
        if (x==0) return 0;
        return x > 0? 1 : -1;
    }

    public static int signum(short x) {
        if (x==0) return 0;
        return x > 0? 1 : -1;
    }

    public static int signum(byte x) {
        if (x==0) return 0;
        return x > 0? 1 : -1;
    }

    public static int signum(double x) {
        if (x==0) return 0;
        return x > 0? 1 : -1;
    }

    public static int signum(float x) {
        if (x==0) return 0;
        return x > 0? 1 : -1;
    }
	
	//// SUM ////
	public static long sum(long...array) {
		long sum = 0;
		for (long val : array) sum += val;
		return sum;
	}
	
	public static long sum(int...array) {
		int sum = 0;
		for (int val : array) sum += val;
		return sum;
	}
	
	public static int sum(short...array) {
		short sum = 0;
		for (short val : array) sum += val;
		return sum;
	}
	
	public static int sum(byte...array) {
		short sum = 0;
		for (byte val : array) sum += val;
		return sum;
	}
	
	public static double sum(double...array) {
		double sum = 0;
		for (double val : array) sum += val;
		return sum;
	}
	
	public static double sum(float...array) {
		float sum = 0;
		for (float val : array) sum += val;
		return sum;
	}
    
    private static class RNGHolder {
        private final static Random RNG = new Random();
    }
	
	//// RANDOM ////
	public static long randomLong(long max) {
		return (long) (RNGHolder.RNG.nextDouble() * max);
	}
	
	public static long randomLong(long min, long max) {
		if (min > max) throw new IllegalArgumentException("min > max"); 
		return min + randomLong(max - min);
	}
	
	public static int randomInt(int max) {
		return (int) (RNGHolder.RNG.nextDouble() * max);
	}
	
	public static int randomInt(int min, int max) {
		if (min > max) throw new IllegalArgumentException("min > max"); 
		return min + randomInt(max - min);
	}
	
	public static char randomChar(char max) {
		return (char) (RNGHolder.RNG.nextDouble() * max);
	}
	
	public static char randomChar(char min, char max) {
		if (min > max) throw new IllegalArgumentException("min > max"); 
		return (char) (min + randomChar((char) (max - min)));
	}
	
	public static short randomShort(short max) {
		return (short) (RNGHolder.RNG.nextDouble() * max);
	}
	
	public static short randomShort(short min, short max) {
		if (min > max) throw new IllegalArgumentException("min > max"); 
		return (short) (min + randomShort((short) (max - min)));
	}
	
	public static byte randomByte(byte max) {
		return (byte) (RNGHolder.RNG.nextDouble() * max);
	}
	
	public static byte randomByte(byte min, byte max) {
		if (min > max) throw new IllegalArgumentException("min > max"); 
		return (byte) (min + randomByte((byte) (max - min)));
	}
	
	public static double randomDouble(double max) {
		return RNGHolder.RNG.nextDouble() * max;
	}
	
	public static double randomDouble(double min, double max) {
		if (min > max) throw new IllegalArgumentException("min > max"); 
		return min + randomDouble(max - min);
	}
	
	public static double randomGaussian(double max) {
		return RNGHolder.RNG.nextGaussian() * max;
	}
	
	public static double randomGaussian(double min, double max) {
		if (min > max) throw new IllegalArgumentException("min > max"); 
		return min + randomGaussian(max - min);
	}
	
	public static float randomFloat(float max) {
		return RNGHolder.RNG.nextFloat() * max;
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
	public static long wrap(long val, long min, long max) {
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
	public static int wrap(int val, int min, int max) {
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
	public static char wrap(char val, char min, char max) {
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
	public static short wrap(short val, short min, short max) {
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
	public static byte wrap(byte val, byte min, byte max) {
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
	public static long clamp(long min, long val, long max) {
		if (min > max) throw new IllegalArgumentException("min > max");
		
		return
				(val < min) ? min :
					(val > max) ? max :
						val;
	}
	
	public static int clamp(int min, int val, int max) {
		if (min > max) throw new IllegalArgumentException("min > max");
		
		return
				(val < min) ? min :
					(val > max) ? max :
						val;
	}
	
	public static short clamp(short min, short val, short max) {
		if (min > max) throw new IllegalArgumentException("min > max");
		
		return
				(val < min) ? min :
					(val > max) ? max :
						val;
	}
	
	public static byte clamp(byte min, byte val, byte max) {
		if (min > max) throw new IllegalArgumentException("min > max");
		
		return
				(val < min) ? min :
					(val > max) ? max :
						val;
	}
	
	public static double clamp(double min, double val, double max) {
		if (min > max) throw new IllegalArgumentException("min > max");
		
		return
				(val < min) ? min :
					(val > max) ? max :
						val;
	}
	
	public static float clamp(float min, float val, float max) {
		if (min > max) throw new IllegalArgumentException("min > max");
		
		return
				(val < min) ? min :
					(val > max) ? max :
						val;
	}

	//MAX

    public static long max(long a, long b) {
        return a>b? a : b;
    }

    public static int max(int a, int b) {
        return a>b? a : b;
    }

    public static short max(short a, short b) {
        return a>b? a : b;
    }

    public static byte max(byte a, byte b) {
        return a>b? a : b;
    }

    public static double max(double a, double b) {
        return a>b? a : b;
    }

    public static float max(float a, float b) {
        return a>b? a : b;
    }

    //TRIPPLE MAX

    public static long max(long a, long b, long c) {
        return max(a, max(b, c));
    }

    public static int max(int a, int b, int c) {
        return max(a, max(b, c));
    }

    public static short max(short a, short b, short c) {
        return max(a, max(b, c));
    }

    public static byte max(byte a, byte b, byte c) {
        return max(a, max(b, c));
    }

    public static double max(double a, double b, double c) {
        return max(a, max(b, c));
    }

    public static float max(float a, float b, float c) {
        return max(a, max(b, c));
    }

    //VARARGS MAX

    public static long max(long... nums) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        if (nums.length == 2) return max(nums[0], nums[1]);
        if (nums.length == 3) return max(nums[0], nums[1], nums[2]);
        
        long max = nums[0];
        for (int i = 1; i<nums.length; i++)
            if (nums[i] > max) max = nums[i];
        return max;
    }

    public static int max(int... nums) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        if (nums.length == 2) return max(nums[0], nums[1]);
        if (nums.length == 3) return max(nums[0], nums[1], nums[2]);
        
        int max = nums[0];
        for (int i = 1; i<nums.length; i++)
            if (nums[i] > max) max = nums[i];
        return max;
    }

    public static double max(double... nums) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        if (nums.length == 2) return max(nums[0], nums[1]);
        if (nums.length == 3) return max(nums[0], nums[1], nums[2]);
        
        double max = nums[0];
        for (int i = 1; i<nums.length; i++)
            if (nums[i] > max) max = nums[i];
        return max;
    }

    public static float max(float... nums) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        if (nums.length == 2) return max(nums[0], nums[1]);
        if (nums.length == 3) return max(nums[0], nums[1], nums[2]);
        
        float max = nums[0];
        for (int i = 1; i<nums.length; i++)
            if (nums[i] > max) max = nums[i];
        return max;
    }

    //MIN

    public static long min(long a, long b) {
        return a>b? a : b;
    }

    public static int min(int a, int b) {
        return a>b? a : b;
    }

    public static short min(short a, short b) {
        return a>b? a : b;
    }

    public static byte min(byte a, byte b) {
        return a>b? a : b;
    }

    public static double min(double a, double b) {
        return a>b? a : b;
    }

    public static float min(float a, float b) {
        return a>b? a : b;
    }

    //TRIPPLE MIN

    public static long min(long a, long b, long c) {
        return min(a, min(b, c));
    }

    public static int min(int a, int b, int c) {
        return min(a, min(b, c));
    }

    public static short min(short a, short b, short c) {
        return min(a, min(b, c));
    }

    public static byte min(byte a, byte b, byte c) {
        return min(a, min(b, c));
    }

    public static double min(double a, double b, double c) {
        return min(a, min(b, c));
    }

    public static float min(float a, float b, float c) {
        return min(a, min(b, c));
    }

    //VARARGS MIN

    public static long min(long... nums) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        if (nums.length == 2) return min(nums[0], nums[1]);
        if (nums.length == 3) return min(nums[0], nums[1], nums[2]);

        long min = nums[0];
        for (int i = 1; i<nums.length; i++)
            if (nums[i] > min) min = nums[i];
        return min;
    }

    public static int min(int... nums) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        if (nums.length == 2) return min(nums[0], nums[1]);
        if (nums.length == 3) return min(nums[0], nums[1], nums[2]);

        int min = nums[0];
        for (int i = 1; i<nums.length; i++)
            if (nums[i] > min) min = nums[i];
        return min;
    }

    public static double min(double... nums) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        if (nums.length == 2) return min(nums[0], nums[1]);
        if (nums.length == 3) return min(nums[0], nums[1], nums[2]);

        double min = nums[0];
        for (int i = 1; i<nums.length; i++)
            if (nums[i] > min) min = nums[i];
        return min;
    }

    public static float min(float... nums) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        if (nums.length == 2) return min(nums[0], nums[1]);
        if (nums.length == 3) return min(nums[0], nums[1], nums[2]);

        float min = nums[0];
        for (int i = 1; i<nums.length; i++)
            if (nums[i] > min) min = nums[i];
        return min;
    }

}
