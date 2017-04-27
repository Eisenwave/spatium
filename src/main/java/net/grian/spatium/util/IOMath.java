package net.grian.spatium.util;

import org.jetbrains.annotations.Contract;

public final class IOMath {

    private IOMath() {}
    
    // PRIMITIVE WRITE

    @Contract("_ -> !null")
    public static byte[] toBytes(long Long) {
        return new byte[]{
            (byte) ((Long >> 56) & 0xFF),
            (byte) ((Long >> 48) & 0xFF),
            (byte) ((Long >> 40) & 0xFF),
            (byte) ((Long >> 32) & 0xFF),
            (byte) ((Long >> 24) & 0xFF),
            (byte) ((Long >> 16) & 0xFF),
            (byte) ((Long >> 8) & 0xFF),
            (byte) (Long & 0xFF)};
    }

    public static byte[] toBytes(int Int) {
        return new byte[]{
            (byte) ((Int >> 24) & 0xFF),
            (byte) ((Int >> 16) & 0xFF),
            (byte) ((Int >> 8) & 0xFF),
            (byte) (Int & 0xFF)};
    }

    public static byte[] toBytes(short Short) {
        return new byte[]{(byte) ((Short >> 8) & 0xFF), (byte) (Short & 0xFF)};
    }

    public static byte[] toBytes(byte Byte) {
        return new byte[]{Byte};
    }

    public static byte[] toBytes(double Double) {
        return toBytes(java.lang.Double.doubleToLongBits(Double));
    }

    public static byte[] toBytes(float Float) {
        return toBytes(java.lang.Float.floatToIntBits(Float));
    }

    // PRIMITIVE READ
    
    public static long asLong(byte[] bytes) {
        return
            ((long) (bytes[0] & 0xFF) << 56) |
            ((long) (bytes[1] & 0xFF) << 48) |
            ((long) (bytes[2] & 0xFF) << 40) |
            ((long) (bytes[3] & 0xFF) << 32) |
            ((bytes[4] & 0xFF) << 24) |
            ((bytes[5] & 0xFF) << 16) |
            ((bytes[6] & 0xFF) << 8) |
            (bytes[7] & 0xFF);
    }

    public static int asInt(byte[] bytes) {
        return ((bytes[0] & 0xFF) << 24) | ((bytes[1] & 0xFF) << 16) | ((bytes[2] & 0xFF) << 8) | (bytes[3] & 0xFF);
    }

    public static short asShort(byte[] bytes) {
        return (short) (((bytes[0] & 0xFF) << 8) | ((bytes[1] & 0xFF)));
    }

    public static byte asByte(byte[] bytes) {
        return bytes[0];
    }

    public static double asDouble(byte[] bytes) {
        return Double.longBitsToDouble(asLong(bytes));
    }

    public static float asFloat(byte[] bytes) {
        return Float.intBitsToFloat(asInt(bytes));
    }

    // BYTE INVERSION
    
    public static int invertBytes(int Int) {
        byte[] bytes = toBytes(Int);
        return ((bytes[3] & 0xFF) << 24) | ((bytes[2] & 0xFF) << 16) | ((bytes[1] & 0xFF) << 8) | (bytes[0] & 0xFF);
    }

    public static short invertBytes(short Short) {
        byte[] bytes = toBytes(Short);
        return (short) ((bytes[1] & 0xFF) << 8 | (bytes[0] & 0xFF));
    }

}
