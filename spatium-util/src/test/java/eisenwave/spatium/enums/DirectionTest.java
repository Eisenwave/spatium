package eisenwave.spatium.enums;

import org.junit.Test;

import static org.junit.Assert.*;

public class DirectionTest {
    
    @Test
    public void opposite() throws Exception {
        for (Direction d : Direction.values()) {
            assertEquals(d.direction().opposite(), d.opposite().direction());
        }
    }
    
    @Test
    public void valueOf() throws Exception {
        for (Direction d : Direction.values()) {
            assertEquals(d, Direction.valueOf(d.axis(), d.direction()));
        }
    }
    
    @Test
    public void face() throws Exception {
        for (Direction d : Direction.values()) {
            assertEquals(d, d.face().direction());
        }
    }
    
}
