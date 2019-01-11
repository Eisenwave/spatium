package eisenwave.spatium.array;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

public class BooleanArray2Test {
    
    @Test
    public void construct() {
        BooleanArray2 array = new BooleanArray2(3, 3);
        assertEquals(3, array.getSizeX());
        assertEquals(3, array.getSizeY());
        assertEquals(1, array.getStride());
        assertEquals(61, array.getPadding());
    }
    
    @Test
    public void iterate() {
        int elements = 0;
        BooleanArray2 array = new BooleanArray2(5, 7);
        for (@SuppressWarnings("unused") boolean b : array)
            elements++;
        assertEquals(5 * 7, elements);
    }
    
    @Test
    public void getRow() {
        boolean[][] rows = {
            toBooleans("1"),
            toBooleans("011111"),
            toBooleans("111000010101001010010010010100101010100111110100001001001011"),
            toBooleans("111111111111110000000001111111111111000"),
            toBooleans("100000000000111100100101001001100010101001010010010010010101001001010010100101010101111000000" +
                "01010010101001101001001001010001010010000101100101001011101001101010100101010"),
        };
        
        int index = 0;
        for (boolean[] row : rows) {
            BooleanArray2 array = new BooleanArray2(row.length, 10);
            //System.err.println("row: " + index++ + " with stride: " + array.getStride());
            
            for (int y = 0; y < array.getSizeY(); y++) {
                for (int x = 0; x < row.length; x++)
                    array.set(x, y, row[x]);
                boolean[] actual = array.getRow(y);
                //System.err.println(y + ": EXP " + toBitString(row));
                //System.err.println(y + ": ACT " + toBitString(actual));
                assertArrayEquals(row, actual);
            }
        }
    }
    
    @Test
    public void getBinary() {
        BooleanArray2 array = new BooleanArray2(8, 1);
        array.enable(0, 0);
        array.enable(2, 0);
        array.enable(4, 0);
        array.enable(6, 0);
        
        //System.out.println(array.toString());
        //for (int i = 0; i < 8; i++)
        //    System.err.println(i + ":  " + Integer.toBinaryString(0b10000000 >>> i));
        
        for (int i = 0; i < 8; i++)
            assertTrue(((i & 1) == 0) == array.get(i, 0));
    }
    
    @Test
    public void get() {
        final int sizeX = 10, sizeY = 10, minX = 3, maxX = 8;
        
        BooleanArray2 array = new BooleanArray2(sizeX, sizeY);
        assertEquals(1, array.getStride());
        assertEquals(54, array.getPadding());
        
        for (int y = 0; y < sizeY; y++)
            for (int x = minX; x <= maxX; x++) {
                assertFalse(array.get(x, y));
                array.set(x, y, true);
                assertTrue(array.get(x, y));
            }
        
        //System.out.println(array);
        
        for (int y = 0; y < sizeY; y++)
            for (int x = minX; x <= maxX; x++) {
                array.set(x, y, false);
                assertFalse(array.get(x, y));
            }
    }
    
    private static BooleanArray2 createNoise(int sizeX, int sizeY) {
        Random random = ThreadLocalRandom.current();
        BooleanArray2 array = new BooleanArray2(sizeX, sizeY);
        
        for (int i = 0; i < sizeX * sizeY; i++) {
            int x = random.nextInt(sizeX);
            int y = random.nextInt(sizeY);
            array.flip(x, y);
        }
        
        return array;
    }
    
    @Test
    public void not() {
        final int sizeX = 100, sizeY = 100;
    
        BooleanArray2 array = createNoise(sizeX, sizeY);
        BooleanArray2 notArray = array.clone();
        notArray.not();
    
        for (int x = 0; x < sizeX; x++)
            for (int y = 0; y < sizeY; y++)
                assertEquals(array.get(x, y), !notArray.get(x, y));
    }
    
    @Test
    public void xor() {
        final int sizeX = 100, sizeY = 100;
        
        BooleanArray2 array = createNoise(sizeX, sizeY);
        BooleanArray2 notArray = array.clone();
        array.xor(notArray);
        
        for (int x = 0; x < sizeX; x++)
            for (int y = 0; y < sizeY; y++)
                assertFalse(array.get(x, y));
    }
    
    @Test
    public void getRandom() {
        final int sizeX = 100, sizeY = 100;
        
        Random random = ThreadLocalRandom.current();
        BooleanArray2 array = new BooleanArray2(sizeX, sizeY);
        
        for (int i = 0; i < sizeX * sizeY; i++) {
            int x = random.nextInt(sizeX);
            int y = random.nextInt(sizeY);
            
            boolean now = array.get(x, y);
            array.set(x, y, !now);
            assertNotEquals(now, array.get(x, y));
        }
    }
    
    @Test
    public void print() {
        final int sizeX = 10, sizeY = 1;
        
        BooleanArray2 array = new BooleanArray2(sizeX, sizeY);
        array.set(0, 0, true);
        array.set(1, 0, true);
        array.set(7, 0, true);
    }
    
    @NotNull
    private static boolean[] toBooleans(@NotNull String bitString) {
        boolean[] result = new boolean[bitString.length()];
        for (int i = 0; i < result.length; i++)
            if (bitString.charAt(i) == '1')
                result[i] = true;
        return result;
    }
    
    @NotNull
    private static String toBitString(@NotNull boolean[] bools) {
        StringBuilder builder = new StringBuilder("0x");
        
        for (int i = 0; i < bools.length; i++) {
            if (i != 0 && i % 64 == 0)
                builder.append('_');
            builder.append(bools[i]? '1' : '0');
        }
        
        return builder.toString();
    }
}
