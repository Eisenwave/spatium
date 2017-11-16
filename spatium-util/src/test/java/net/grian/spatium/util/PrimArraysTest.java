package net.grian.spatium.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class PrimArraysTest {
    
    @Test
    public void flip() throws Exception {
        final int[]
            odd = {1, 2, 3, 4, 5},
            even = {1, 2, 3, 4, 5, 6};
    
        PrimArrays.flip(odd);
        PrimArrays.flip(even);
        assertArrayEquals(new int[] {5, 4, 3, 2, 1}, odd);
        assertArrayEquals(new int[] {6, 5, 4, 3, 2, 1}, even);
    }
    
}
