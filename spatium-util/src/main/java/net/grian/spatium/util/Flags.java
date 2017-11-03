package net.grian.spatium.util;

import org.jetbrains.annotations.Contract;

public final class Flags {

    private Flags() {}

    /**
     * Sets the flag at a given index the {@code false}
     *
     * @param flags the the flags
     * @param index the bit index
     * @return the new modified flags
     */
    @Contract(pure = true)
    public static long disable(long flags, int index) {
        if (index < 0 || index >= Long.SIZE)
            throw new IllegalArgumentException("index out of range ("+index+")");
        return flags & ~(1 << index);
    }

    /**
     * Sets the flag at a given index the {@code false}
     *
     * @param flags the the flags
     * @param index the bit index
     * @return the new modified flags
     */
    @Contract(pure = true)
    public static int disable(int flags, int index) {
        if (index < 0 || index >= Integer.SIZE)
            throw new IllegalArgumentException("index out of range ("+index+")");
        return flags & ~(1 << index);
    }

    /**
     * Sets the flag at a given index the {@code false}
     *
     * @param flags the the flags
     * @param index the bit index
     * @return the new modified flags
     */
    @Contract(pure = true)
    public static byte disable(byte flags, int index) {
        if (index < 0 || index >= Byte.SIZE)
            throw new IllegalArgumentException("index out of range ("+index+")");
        return (byte) (flags & ~(1 << index));
    }

    /**
     * Sets the flag at a given index the {@code true}
     *
     * @param flags the the flags
     * @param index the bit index
     * @return the new modified flags
     */
    @Contract(pure = true)
    public static long enable(long flags, int index) {
        if (index < 0 || index >= Long.SIZE)
            throw new IllegalArgumentException("index out of range ("+index+")");
        return flags | 1 << index;
    }

    /**
     * Sets the flag at a given index the {@code true}
     *
     * @param flags the the flags
     * @param index the bit index
     * @return the new modified flags
     */
    @Contract(pure = true)
    public static int enable(int flags, int index) {
        if (index < 0 || index >= Integer.SIZE)
            throw new IllegalArgumentException("index out of range ("+index+")");
        return flags | 1 << index;
    }

    /**
     * Sets the flag at a given index the {@code true}
     *
     * @param flags the the flags
     * @param index the bit index
     * @return the new modified flags
     */
    @Contract(pure = true)
    public static byte enable(byte flags, int index) {
        if (index < 0 || index >= Byte.SIZE)
            throw new IllegalArgumentException("index out of range ("+index+")");
        return (byte) (flags | 1 << index);
    }

    /**
     * Returns the whether the flag at a given index is 1 or 0
     *
     * @param flags the the flags
     * @param index the bit index
     */
    @Contract(pure = true)
    public static boolean get(long flags, int index) {
        if (index < 0 || index >= Long.SIZE)
            throw new IllegalArgumentException("index out of range ("+index+")");
        return (flags & (1 << index)) != 0;
    }

    /**
     * Returns the whether the flag at a given index is 1 or 0
     *
     * @param flags the the flags
     * @param index the bit index
     */
    @Contract(pure = true)
    public static boolean get(int flags, int index) {
        if (index < 0 || index >= Integer.SIZE)
            throw new IllegalArgumentException("index out of range ("+index+")");
        return (flags & (1 << index)) != 0;
    }

    /**
     * Returns the whether the flag at a given index is 1 or 0
     *
     * @param flags the the flags
     * @param index the bit index
     */
    @Contract(pure = true)
    public static boolean get(byte flags, int index) {
        if (index < 0 || index >= Byte.SIZE)
            throw new IllegalArgumentException("index out of range ("+index+")");
        return (flags & (1 << index)) != 0;
    }

    /**
     * Sets the flag at a given index to a either {@code true} or {@code false}
     *
     * @param flags the the flags
     * @param index the bit index
     * @param flag the new value
     * @return the new modified flags
     */
    @Contract(pure = true)
    public static long set(long flags, int index, boolean flag) {
        if (index < 0 || index >= Long.SIZE)
            throw new IllegalArgumentException("index out of range ("+index+")");
        return flags ^ (-(flag? 1 : 0) ^ flags) & (1 << index);
    }

    /**
     * Sets the flag at a given index to a either {@code true} or {@code false}
     *
     * @param flags the the flags
     * @param index the bit index
     * @param flag the new value
     * @return the new modified flags
     */
    @Contract(pure = true)
    public static int set(int flags, int index, boolean flag) {
        if (index < 0 || index >= Integer.SIZE)
            throw new IllegalArgumentException("index out of range ("+index+")");
        return flags ^ (-(flag? 1 : 0) ^ flags) & (1 << index);
    }

    /**
     * Sets the flag at a given index to a either {@code true} or {@code false}
     *
     * @param flags the the flags
     * @param index the bit index
     * @param flag the new value
     * @return the new modified flags
     */
    @Contract(pure = true)
    public static byte set(byte flags, int index, boolean flag) {
        if (index < 0 || index >= Byte.SIZE)
            throw new IllegalArgumentException("index out of range ("+index+")");
        return (byte) (flags ^ (-(flag? 1 : 0) ^ flags) & (1 << index));
    }
    
    @Contract(pure = true)
    public static long toggle(long flags, int index) {
        if (index < 0 || index >= Long.SIZE)
            throw new IllegalArgumentException("index out of range ("+index+")");
        return flags ^ 1 << index;
    }
    
    @Contract(pure = true)
    public static int toggle(int flags, int index) {
        if (index < 0 || index >= Integer.SIZE)
            throw new IllegalArgumentException("index out of range ("+index+")");
        return flags ^ 1 << index;
    }
    
    @Contract(pure = true)
    public static int bitSum(long number) {
        int result = 0;
        for (int i = 0; i<Long.SIZE; i++)
            if (((number >> i) & 1) == 1)
                result++;

        return result;
    }
    
    @Contract(pure = true)
    public static int bitSum(int number) {
        int result = 0;
        for (int i = 0; i<Integer.SIZE; i++)
            if (((number >> i) & 1) == 1)
                result++;

        return result;
    }
    
    @Contract(pure = true)
    public static int bitSum(short number) {
        int result = 0;
        for (int i = 0; i<Short.SIZE; i++)
            if (((number >> i) & 1) == 1)
                result++;

        return result;
    }
    
    @Contract(pure = true)
    public static int bitSum(byte number) {
        int result = 0;
        for (int i = 0; i<Byte.SIZE; i++)
            if (((number >> i) & 1) == 1)
                result++;

        return result;
    }

}
