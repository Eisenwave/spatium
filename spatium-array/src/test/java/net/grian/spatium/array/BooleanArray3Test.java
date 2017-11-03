package net.grian.spatium.array;

import org.junit.Test;

import static org.junit.Assert.*;

public class BooleanArray3Test {

    @Test
    public void enable() throws Exception {
        BooleanArray3 map = new BooleanArray3(10, 20, 30);
        final int[] pos = {7, 13, 23};
        
        System.out.println(map);

        map.enable(pos[0], pos[1], pos[2]);
        assertTrue(map.get(pos[0], pos[1], pos[2]));
        assertFalse(map.get(pos[0], pos[1]+1, pos[2]));
    }

    @Test
    public void disable() throws Exception {
        BooleanArray3 map = new BooleanArray3(10, 20, 30);
        final int[] pos = {7, 13, 23};

        map.enable(pos[0], pos[1], pos[2]);
        assertTrue(map.get(pos[0], pos[1], pos[2]));

        map.disable(pos[0], pos[1], pos[2]);
        assertFalse(map.get(pos[0], pos[1], pos[2]));
    }

    /*
    @Test
    public void fullToggle() throws Exception {
        BooleanArray3 array = new BooleanArray3(32, 32, 32);
        System.out.println(array);

        array.forEachIndex(array::enable);
        assertEquals(array.getVolume(), array.size());

        array.forEachIndex(array::disable);
        assertEquals(0, array.size());
    }
    */

}