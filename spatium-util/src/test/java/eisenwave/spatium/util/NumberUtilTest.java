package eisenwave.spatium.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class NumberUtilTest {
    
    @Test
    public void powMod() {
        assertEquals(24088, NumberUtil.powMod(2, 16206720, 60202));
    }
    
}
