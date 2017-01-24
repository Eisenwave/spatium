package net.grian.spatium.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Utility class which helps with enumerations.
 *
 * @author Jan "Headaxe" Schultke
 *
 */
public final class Enumerations {

    /**
     * Don't you fucking use this one.
     */
    private Enumerations() {}

    /**
     * Returns a random constant of the enumeration.
     * @param <T> the enum type
     * @param enumeration the enum
     */
    public static <T extends Enum<T>> T random(T enumeration) {
        return PrimArrays.random(enumeration.getDeclaringClass().getEnumConstants());
    }

    /**
     * Returns a random constant of the enumeration.
     *
     * @param <T> the enum type
     */
    public static <T extends Enum<T>> T random(Class<T> type) {
        return PrimArrays.random(type.getEnumConstants());
    }

    /**
     * Returns the amount of enum constants of an enumeration.
     *
     * @param <T> the enum type
     * @param enumeration the enum
     */
    public static <T extends Enum<T>> int size(T enumeration) {
        return enumeration.getDeclaringClass().getEnumConstants().length;
    }

    /**
     * Returns the constant of an enumeration with a specified ordinal.
     *
     * @param enumeration the enum
     * @param ordinal the ordinal of the constant
     */
    public static <T extends Enum<T>> T getByOrdinal(T enumeration, int ordinal) {
        T[] values = enumeration.getDeclaringClass().getEnumConstants();
        if (ordinal < 0 || ordinal >= values.length)
            throw new IndexOutOfBoundsException(enumeration.getClass().getSimpleName()+" has no value with ordinal "+ordinal);

        return values[ordinal];
    }

    /**
     * Returns the constant with next higher ordinal relative to a given
     * constant.
     * @param <T> the enum type
     * @param enumeration the enum
     */
    public static <T extends Enum<T>> T next(T enumeration) {
        return getByOrdinal(enumeration, enumeration.ordinal()+1);
    }

    /**
     * Returns the constant with next lower ordinal relative to a given
     * constant.
     * @param <T> the enum type
     * @param enumeration the enum
     */
    public static <T extends Enum<T>> T previous(T enumeration) {
        return getByOrdinal(enumeration, enumeration.ordinal()-1);
    }

    /**
     * Parses an enum using a {@link Nullable} string. If the string may not be
     * assigned to an enum constant, the fallback value is being used. Use
     * {@link #parse(String, Class, Enum)} for nullable fallback values, this
     * method does not accept null as a fallback value.
     *
     * @param <T> the enum type
     * @param str the string
     * @param fallback the fallback value
     * @throws IllegalArgumentException if the fallback value is null
     * @return The parsed enum constant.
     */
    public static <T extends Enum<T>> T parse(@Nullable String str, T fallback) {
        if (fallback==null) throw new IllegalArgumentException("fallback may not be null");
        try {
            return str==null? fallback : Enum.valueOf(fallback.getDeclaringClass(), str);
        } catch (IllegalArgumentException ex) {
            return fallback;
        }
    }

    /**
     * Parses an enum using a {@link Nullable} string. If the string may not be
     * assigned to an enum constant, the fallback value is being used. The
     * fallback value is nullable.
     *
     * @param <T> the enum type
     * @param str the string
     * @param clazz the class of the enum
     * @param fallback the fallback value
     * @throws IllegalArgumentException if the class is null
     * @return The parsed enum constant.
     */
    public static <T extends Enum<T>> T parse(@Nullable String str, @Nonnull Class<T> clazz, @Nullable T fallback) {
        try {
            return str==null? fallback : Enum.valueOf(clazz, str);
        } catch (IllegalArgumentException ex) {
            return fallback;
        }
    }

    public static <T extends Enum<T>> T parse(@Nullable String str, @Nonnull Class<T> clazz) {
        return parse(str, clazz, null);
    }

    public static String[] getNames(Class<? extends Enum<?>> clazz) {
        Enum<?>[] values = clazz.getEnumConstants();
        String[] names = new String[values.length];
        for (int i = 0; i<values.length; i++)
            names[i] = values[i].name();
        return names;
    }

    public static String[] toStrings(Class<? extends Enum<?>> clazz) {
        Enum<?>[] values = clazz.getEnumConstants();
        String[] names = new String[values.length];
        for (int i = 0; i<values.length; i++)
            names[i] = values[i].toString();
        return names;
    }

}
