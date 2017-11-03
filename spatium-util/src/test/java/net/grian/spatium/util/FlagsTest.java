package net.grian.spatium.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class FlagsTest {

    @Test
    public void bitSum() throws Exception {
        assertEquals(0,  Flags.bitSum((byte) 0));
        assertEquals(10, Flags.bitSum((short) 0b00101101_11011110));
        assertEquals(16, Flags.bitSum((short) 0xFF_FF));
        assertEquals(32, Flags.bitSum(0xFF_FF_FF_FF));
        assertEquals(16, Flags.bitSum(0xFF_00_00_00_00_00_00_FFL));
    }

}