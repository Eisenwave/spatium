package net.grian.spatium.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class IOMathTest {

    @Test
    public void asBytes() throws Exception {
        int number = 0x01_02_03_04;
        byte[] bytes = IOMath.toBytes(number);
        assertEquals(bytes[0], 1);
        assertEquals(bytes[1], 2);
        assertEquals(bytes[2], 3);
        assertEquals(bytes[3], 4);
    }

    @Test
    public void asInt() throws Exception {
        byte[] bytes = new byte[] {1, 2, 3, 4};
        int number = IOMath.asInt(bytes);

        assertEquals(number, 0x01_02_03_04);
    }

    @Test
    public void asBytesInverted() throws Exception {
        int number = 0x01_02_03_04;
        assertEquals(number, IOMath.asInt(IOMath.toBytes(number)));
    }

    @Test
    public void invertBytes() throws Exception {
        assertEquals(0x01_02_03_04, IOMath.invertBytes(0x04_03_02_01));
    }

    @Test
    public void invertBytesTwice() throws Exception {
        int num1 = 0x01_02_03_04, num2 = 16777216, num3 = 123456789;
        assertEquals(num1, IOMath.invertBytes(IOMath.invertBytes(num1)));
        assertEquals(num2, IOMath.invertBytes(IOMath.invertBytes(num2)));
        assertEquals(num3, IOMath.invertBytes(IOMath.invertBytes(num3)));
    }

}