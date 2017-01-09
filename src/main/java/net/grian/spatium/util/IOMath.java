package net.grian.spatium.util;

public final class IOMath {

    private IOMath() {}

    public static byte[] asBytes(long Long) {
        return new byte[] {
                (byte) ((Long >> 56) & 0xFF),
                (byte) ((Long >> 48) & 0xFF),
                (byte) ((Long >> 40) & 0xFF),
                (byte) ((Long >> 32) & 0xFF),
                (byte) ((Long >> 24) & 0xFF),
                (byte) ((Long >> 16) & 0xFF),
                (byte) ((Long >> 8) & 0xFF),
                (byte) (Long & 0xFF)};
    }

    public static byte[] asBytes(int Int) {
        return new byte[] {
                (byte) ((Int >> 24) & 0xFF),
                (byte) ((Int >> 16) & 0xFF),
                (byte) ((Int >> 8)  & 0xFF),
                (byte) (Int         & 0xFF)};
    }

    public static byte[] asBytes(short Short) {
        return new byte[] {(byte) ((Short >> 8) & 0xFF), (byte) (Short & 0xFF)};
    }

    public static byte[] asBytes(byte Byte) {
        return new byte[] {Byte};
    }

    public static int asInt(byte[] bytes) {
        return ((bytes[0]&0xFF)<<24) | ((bytes[1]&0xFF)<<16) | ((bytes[2]&0xFF)<<8) | (bytes[3]&0xFF);
    }

    public static int invertBytes(int Int) {
        byte[] bytes = asBytes(Int);
        return ((bytes[3]&0xFF)<<24) | ((bytes[2]&0xFF)<<16) | ((bytes[1]&0xFF)<<8) | (bytes[0]&0xFF);
    }

    public static short invertBytes(short Short) {
        byte[] bytes = asBytes(Short);
        return (short) ( (bytes[1]&0xFF)<<8 | (bytes[0]&0xFF) );
    }


}
