package net.grian.spatium.util;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Iterator;

public final class Strings {
    
    private Strings() {}
    
    private final static NumberFormat
        STD_DEC_FORMAT = DecimalFormat.getNumberInstance();
    static {
        STD_DEC_FORMAT.setMaximumFractionDigits(6);
    }
    
    public static String valueOf(double num) {
        return STD_DEC_FORMAT.format(num);
    }
    
    public static String valueOf(double num, int minDec, int maxDec) {
		NumberFormat format = DecimalFormat.getNumberInstance();
        format.setMinimumFractionDigits(minDec);
        format.setMaximumFractionDigits(maxDec);
		
        return format.format(num);
	}
	
	public static String valueOf(double num, int maxDec) {
        return valueOf(num, 0, maxDec);
    }
    
    // APPEND
    
    public static String[] append(String[] tail, String... head) {
        return PrimArrays.concat(tail, head);
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
    
    // JOIN
    
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
        for (String anArray : array) builder.append(anArray);
        
        return builder.toString();
    }
    
    public static String join(char[]... array) {
        StringBuilder builder = new StringBuilder();
        for (char[] anArray : array) builder.append(anArray);
        
        return builder.toString();
    }
    
    public static String join(Object... array) {
        StringBuilder builder = new StringBuilder();
        for (Object anArray : array) builder.append(anArray);
        
        return builder.toString();
    }
    
    public static String join(long... array) {
        StringBuilder builder = new StringBuilder();
        for (long anArray : array) builder.append(anArray);
        
        return builder.toString();
    }
    
    public static String join(int... array) {
        StringBuilder builder = new StringBuilder();
        for (int anArray : array) builder.append(anArray);
        
        return builder.toString();
    }
    
    public static String join(short... array) {
        StringBuilder builder = new StringBuilder();
        for (short anArray : array) builder.append(anArray);
        
        return builder.toString();
    }
    
    public static String join(char... array) {
        StringBuilder builder = new StringBuilder();
        for (char anArray : array) builder.append(anArray);
        
        return builder.toString();
    }
    
    public static String join(double... array) {
        StringBuilder builder = new StringBuilder();
        for (double anArray : array) builder.append(anArray);
        
        return builder.toString();
    }
    
    public static String join(float... array) {
        StringBuilder builder = new StringBuilder();
        for (float anArray : array) builder.append(anArray);
        
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
        collect.forEach(builder::append);
        
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
        for (String aCollect : collect) {
            builder.append(aCollect);
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
    
    // REPEAT
    
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
    
    public static boolean startsWithDig(String str) {
        return !str.isEmpty() && Character.isDigit(str.toCharArray()[0]);
    }
    
    public static boolean startsWithLet(String str) {
        return !str.isEmpty() && Character.isAlphabetic(str.toCharArray()[0]);
    }
    
    public static String replaceChar(String str, char replace, char with) {
        char[] array = str.toCharArray();
        for (int i = 0; i<str.length(); i++) {
            if (array[i]==replace)
                array[i] = with;
        }
        
        return new String(array);
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
     *
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
    
    public static int compare(String str0, String str1) {
        return compare(str0.toCharArray(), str1.toCharArray());
    }
    
    public static int compare(char[] str0, char[] str1) {
        if (str0.length == 0 && str1.length == 0)
            return 0;
        
        //int i = 0, j = 0;
        
        for (int i = 0, j = 0; i < str0.length && j < str1.length; i++, j++) {
            int result;
            
            if (Character.isDigit(str0[i]) && Character.isDigit(str1[j])) {
                int[] num0 = parseInt(str0, i);
                int[] num1 = parseInt(str1, j);
                
                result = Integer.compare(num0[0], num1[0]);
                if (result != 0) return result;
                result = Character.compare(str0[i], str1[j]);
                if (result != 0) return result;
                
                i = num0[1];
                j = num1[1];
            }
    
            else
                result = Character.compare(str0[i], str1[j]);
            
            if (result != 0) return result;
        }
        
        return 0;
    }
    
    @NotNull
    private static int[] parseInt(char[] chars, int off) {
        StringBuilder builder = new StringBuilder();
        
        int i = off;
        while (i < chars.length && Character.isDigit(chars[i]))
            builder.append(chars[i++]);
        
        return new int[] {Integer.parseInt(builder.toString()), off};
    }
    
}
