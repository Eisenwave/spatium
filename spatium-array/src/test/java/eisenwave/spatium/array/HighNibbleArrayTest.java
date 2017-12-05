package eisenwave.spatium.array;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class HighNibbleArrayTest {
    
    @Test
    public void length() throws Exception {
        HighNibbleArray array = new HighNibbleArray(23);
        assertEquals(23, array.getLength());
        assertEquals(12, array.getDataLength());
    }
    
    @Test
    public void get() throws Exception {
        HighNibbleArray array = new HighNibbleArray(23);
        Random rng = new Random();
        
        for (int i = 0; i < array.getLength(); i++) {
            byte nibble = (byte) rng.nextInt(16);
            array.set(i, nibble);
            //System.out.println(i+" "+nibble);
            assertEquals(nibble, array.get(i));
        }
    }
    
}
