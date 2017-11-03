package net.grian.spatium.array;

import org.junit.Test;

import static org.junit.Assert.*;

public class BooleanArray2Test {
    
    @Test
    public void get() throws Exception {
        final int sizeX = 10, sizeY = 10, minX = 3, maxX = 8;
        
        BooleanArray2 array = new BooleanArray2(sizeX, sizeY);
        assertEquals(array.getStride(), 2);
        assertEquals(array.getPadding(), 6);
        
        for (int y = 0; y < sizeY; y++) for (int x = minX; x <= maxX; x++) {
            assertFalse(array.get(x, y));
            array.set(x, y, true);
            assertTrue(array.get(x, y));
        }
    
        System.out.println(array);
        
        for (int y = 0; y < sizeY; y++) for (int x = minX; x <= maxX; x++) {
            array.set(x, y, false);
            assertFalse(array.get(x, y));
        }
    }
    
    @Test
    public void print() throws Exception {
        final int sizeX = 10, sizeY = 1;
        
        BooleanArray2 array = new BooleanArray2(sizeX, sizeY);
        array.set(0, 0, true);
        array.set(1, 0, true);
        array.set(7, 0, true);
    }
    
}