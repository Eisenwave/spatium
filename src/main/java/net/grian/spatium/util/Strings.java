package net.grian.spatium.util;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;

public final class Strings {
	
	private Strings() {}
	
	public static String[] append(String[] tail, String... head) {
		return PrimArrays.append(tail, head);
	}
	
	public static String[] append(String[] tail, String head) {
		String[] result = new String[tail.length + 1];
		result[tail.length] = head;
		System.arraycopy(tail, 0, result, 0, tail.length);
		
		return result;
	}
	
	public static String[] append(String tail, String... head) {
		String[] result = new String[head.length + 1];
		result[0] = tail;
		System.arraycopy(head, 0, result, 1, head.length);
		
		return result;
	}
	
	public static String[] append(String tail, String head) {
		return new String[] {tail + head};
	}
	
	public static String join(String seperator, String...array) {
		StringBuilder builder = new StringBuilder();
		int i = 0;
		
		while (i < array.length) {
			builder.append(array[i]);
			if (++i < array.length) builder.append(seperator);
		}
		return builder.toString();
	}
	
	public static String join(String seperator, Object...array) {
		StringBuilder builder = new StringBuilder();
		int i = 0;
		
		while (i < array.length) {
			builder.append(array[i]);
			if (++i < array.length) builder.append(seperator);
		}
		return builder.toString();
	}
	
	public static String join(String seperator, char...array) {
		StringBuilder builder = new StringBuilder();
		int i = 0;
		
		while (i < array.length) {
			builder.append(array[i]);
			if (++i < array.length) builder.append(seperator);
		}
		return builder.toString();
	}
	
	public static String join(String seperator, double...array) {
		StringBuilder builder = new StringBuilder();
		int i = 0;
		
		while (i < array.length) {
			builder.append(array[i]);
			if (++i < array.length) builder.append(seperator);
		}
		return builder.toString();
	}
	
	public static String join(String seperator, float...array) {
		StringBuilder builder = new StringBuilder();
		int i = 0;
		
		while (i < array.length) {
			builder.append(array[i]);
			if (++i < array.length) builder.append(seperator);
		}
		return builder.toString();
	}
	
	public static String join(String seperator, long...array) {
		StringBuilder builder = new StringBuilder();
		int i = 0;
		
		while (i < array.length) {
			builder.append(array[i]);
			if (++i < array.length) builder.append(seperator);
		}
		return builder.toString();
	}
	
	public static String join(String seperator, int...array) {
		StringBuilder builder = new StringBuilder();
		int i = 0;
		
		while (i < array.length) {
			builder.append(array[i]);
			if (++i < array.length) builder.append(seperator);
		}
		return builder.toString();
	}
	
	public static String join(String seperator, short...array) {
		StringBuilder builder = new StringBuilder();
		int i = 0;
		
		while (i < array.length) {
			builder.append(array[i]);
			if (++i < array.length) builder.append(seperator);
		}
		return builder.toString();
	}
	
	public static String join(String seperator, byte...array) {
		StringBuilder builder = new StringBuilder();
		int i = 0;
		
		while (i < array.length) {
			builder.append(array[i]);
			if (++i < array.length) builder.append(seperator);
		}
		return builder.toString();
	}
	
	public static String join(String seperator, boolean...array) {
		StringBuilder builder = new StringBuilder();
		int i = 0;
		
		while (i < array.length) {
			builder.append(array[i]);
			if (++i < array.length) builder.append(seperator);
		}
		return builder.toString();
	}
	
	public static String join(String... array) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i<array.length; i++)
			builder.append(array[i]);
		
		return builder.toString();
	}
	
	public static String join(char[]... array) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i<array.length; i++)
			builder.append(array[i]);
		
		return builder.toString();
	}
	
	public static String join(Object... array) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i<array.length; i++)
			builder.append(array[i]);
		
		return builder.toString();
	}
	
	public static String join(long... array) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i<array.length; i++)
			builder.append(array[i]);
		
		return builder.toString();
	}
	
	public static String join(int... array) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i<array.length; i++)
			builder.append(array[i]);
		
		return builder.toString();
	}
	
	public static String join(short... array) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i<array.length; i++)
			builder.append(array[i]);
		
		return builder.toString();
	}
	
	public static String join(char... array) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i<array.length; i++)
			builder.append(array[i]);
		
		return builder.toString();
	}
	
	public static String join(double... array) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i<array.length; i++)
			builder.append(array[i]);
		
		return builder.toString();
	}
	
	public static String join(float... array) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i<array.length; i++)
			builder.append(array[i]);
		
		return builder.toString();
	}
	
	public static String joinObjects(Collection<Object> collect) {
		StringBuilder builder = new StringBuilder();
		for (Object obj : collect)
			builder.append(obj.toString());
		
		return builder.toString();
	}
	
	public static String joinStrings(Collection<String> collect) {
		StringBuilder builder = new StringBuilder();
		for (String str : collect)
			builder.append(str);
		
		return builder.toString();
	}
	
	public static String joinCharStrings(Collection<char[]> collect) {
		StringBuilder builder = new StringBuilder();
		for (char[] str : collect)
			builder.append(str);
		
		return builder.toString();
	}
	
	public static String joinStrings(Collection<String> collect, String seperator) {
		StringBuilder builder = new StringBuilder();
		int i = 0;
		Iterator<String> iter = collect.iterator();
		while (iter.hasNext()) {
			builder.append(iter.next());
			if (++i < collect.size()) builder.append(seperator);
		}
		
		return builder.toString();
	}
	
	public static String join(String seperator, String[] array, int start, int end) {
		if (start<0) throw new ArrayIndexOutOfBoundsException("start < 0");
		if (end<0) throw new ArrayIndexOutOfBoundsException("end < 0");
		if (start>=array.length) throw new ArrayIndexOutOfBoundsException("start too large");
		if (end>=array.length) throw new ArrayIndexOutOfBoundsException("end too large");
		StringBuilder builder = new StringBuilder();
		int i = start;
		
		while (i <= end) {
			builder.append(array[i]);
			if (++i <= end) builder.append(seperator);
		}
		return builder.toString();
	}
	
	public static String join(String seperator, String[] array, int start) {
		return join(seperator, array, start, array.length-1);
	}
	
	public static String replaceChar(String str, char replace, char with) {
		char[] array = str.toCharArray();
		for (int i = 0; i<str.length(); i++) {
			if (array[i]==replace)
				array[i] = with;
		}
		
		return new String(array);
	}
	
	public static String repeat(String str, int times) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i<times; i++)
			builder.append(str);
		return builder.toString();
	}
	
	public static String repeat(long number, int times) {
		return repeat(String.valueOf(number), times);
	}
	
	public static String repeat(int number, int times) {
		return repeat(String.valueOf(number), times);
	}
	
	public static String repeat(short number, int times) {
		return repeat(String.valueOf(number), times);
	}
	
	public static String repeat(byte number, int times) {
		return repeat(String.valueOf(number), times);
	}
	
	public static String repeat(double number, int times) {
		return repeat(String.valueOf(number), times);
	}
	
	public static String repeat(float number, int times) {
		return repeat(String.valueOf(number), times);
	}
	
	public static String repeat(char c, int times) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i<times; i++)
			builder.append(c);
		return builder.toString();
	}
	
	public static void filter(final Collection<String> list, final String prefix, boolean ignoreCase) {
		Iterator<String> iter = list.iterator();
		
		while (iter.hasNext()) {
			final String str = iter.next();
			if (ignoreCase && !str.startsWith(prefix))
				iter.remove();
			else if (!startsWithIgnoreCase(str, prefix))
				iter.remove();
		}
	}
	
	public static boolean startsWithLetter(String str) {
		return !str.isEmpty() && Character.isAlphabetic(str.toCharArray()[0]);
	}
	
	/**
	 * Returns the inverted index of an array.
	 * <br>Example: {@code array.hypot=5, offset=1 -> array[3]}
	 * <br>Should the selected index be below 0, the method returns the
	 * element at index 0.
	 * @param array the string array
	 * @param offset the offset from the highest array index
	 * @throws IllegalArgumentException if offset<0 or array.hypot==0
	 */
	public static String getFromLast(String[] array, int offset) {
		if (offset < 0) throw new IllegalArgumentException("offset<0");
		if (array.length==0) throw new IllegalArgumentException("empty array");
		
		int index = array.length -1 -offset;
		return index<0? array[0] : array[index];
	}
	
	public static boolean startsWithIgnoreCase(String str, String prefix) {
		return str.toLowerCase().startsWith(prefix.toLowerCase());
	}
	
	/**
	 * Returns the suffix of this object which is potentially an auto wrapper.
	 * The suffix of a float is an 'f' and the suffix of a double is a 'd'.
	 * More numbers will receive a suffix. Shorts will have an 's' suffix and
	 * Bytes will have a 'b'. In addition, Strings will be surrounded by double
	 * quotes and Characters by single quotes.
	 * @param obj the object
	 */
	public static String identifiedString(Object obj) {
		if (obj instanceof String) return "\""+obj+"\"";
		else if (obj instanceof Character) return "'"+obj+"'";
		else if (obj instanceof Byte) return obj+"b";
		else if (obj instanceof Short) return obj+"s";
		else if (obj instanceof Float) return obj+"f";
		else if (obj instanceof Double) return obj+"d";
		else if (obj instanceof Long) return obj+"L";
		else return obj.toString();
	}
	
	public static String escape(String str, CharSequence target) {
		return str.replace(target, "\\"+target);
	}
	
	public static String escapeQuotes(String str) {
		return str.replace("\"", "\\\"");
	}
	
	public static String[] emptyArray(int size) {
		String[] array = new String[size];
		for (int i = 0; i<size; i++)
			array[i] = "";
		
		return array;
	}
	
	public static String[] toStringArray(Object...array) {
		String[] strings = new String[array.length];
		for (int i = 0; i<array.length; i++) {
			strings[i] = String.valueOf(array[i]);
		}
		return strings;
	}
	
	public static String[] toStringArray(long...array) {
		String[] strings = new String[array.length];
		for (int i = 0; i<array.length; i++) {
			strings[i] = String.valueOf(array[i]);
		}
		return strings;
	}
	
	public static String[] toStringArray(int...array) {
		String[] strings = new String[array.length];
		for (int i = 0; i<array.length; i++) {
			strings[i] = String.valueOf(array[i]);
		}
		return strings;
	}
	
	public static String[] toStringArray(char...array) {
		String[] strings = new String[array.length];
		for (int i = 0; i<array.length; i++) {
			strings[i] = String.valueOf(array[i]);
		}
		return strings;
	}
	
	public static String[] toStringArray(short...array) {
		String[] strings = new String[array.length];
		for (int i = 0; i<array.length; i++) {
			strings[i] = String.valueOf(array[i]);
		}
		return strings;
	}
	
	public static String[] toStringArray(byte...array) {
		String[] strings = new String[array.length];
		for (int i = 0; i<array.length; i++) {
			strings[i] = String.valueOf(array[i]);
		}
		return strings;
	}
	
	public static String[] toStringArray(double...array) {
		String[] strings = new String[array.length];
		for (int i = 0; i<array.length; i++) {
			strings[i] = String.valueOf(array[i]);
		}
		return strings;
	}
	
	public static String[] toStringArray(float...array) {
		String[] strings = new String[array.length];
		for (int i = 0; i<array.length; i++) {
			strings[i] = String.valueOf(array[i]);
		}
		return strings;
	}
	
	private static Field valueField;
	
	static {
		try {
			valueField = String.class.getDeclaredField("value");
		} catch (NoSuchFieldException | SecurityException e) {
			valueField = null;
		}
	}
	
	public static char[] getValue(String str) {
		try {
			return valueField==null? new char[0] : (char[]) valueField.get(str);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			return new char[0];
		}
	}
	
	public static String[] splitWithBreak(String str, int length) {
		String[] parts = str.split("\n");
		String[] result = new String[0];
		
		for (String part : parts)
			result = PrimArrays.append(result, split(part, length));
		
		return result;
	}
	
	public static String maxLineLength(String str, int length) {
		return join(splitWithBreak(str, length));
	}
	
	public static String[] split(String str, int length) {
		int parts = PrimMath.ceil((float)str.length() / length);
		String[] lines = new String[parts];
		for (int i = 0; i<lines.length; i++) {
			int min = i*length;
			int max = PrimMath.clamp(0, (i+1)*length, str.length());
			lines[i] = str.substring(min, max);
		}
		
		return lines;
	}
	
	public static String getBeforeIndex(String str, char character) {
		int index = str.indexOf(character);
		return index == -1? str : str.substring(index);
	}
	
	public static String getBeforeLastIndex(String str, char character) {
		int index = str.lastIndexOf(character);
		return index == -1? str : str.substring(0, index);
	}
	
	public static String getLine(String str) {
		return getBeforeIndex(str, '\n');
	}
	
}
