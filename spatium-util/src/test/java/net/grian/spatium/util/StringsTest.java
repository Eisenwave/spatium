package net.grian.spatium.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringsTest {
    
    @Test
    public void compare() throws Exception {
        assertEquals(0, Strings.compare("str123", "str123"));
        assertEquals(-1, Strings.compare("str5abc", "str13abc"));
        
        assertEquals(1, Strings.compare("str123_10", "str123_8"));
        assertEquals(-1, Strings.compare("str123_8", "str123_10"));
    }
    
}