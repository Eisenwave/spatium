package net.grian.spatium.util;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrimMathTest {
    
    public final static float
    EXACT_POS= 2F,
    EXACT_NEG = -2F,
    POS = 2.5F,
    NEG = -2.5F;

    @Test
    public void floor() throws Exception {
        assertEquals((int) EXACT_POS, PrimMath.floor(EXACT_POS));
        assertEquals((int) EXACT_NEG, PrimMath.floor(EXACT_NEG));
        assertEquals((int) POS, PrimMath.floor(POS));
        assertEquals((int) NEG-1, PrimMath.floor(NEG));
    }

    @Test
    public void ceil() throws Exception {
        assertEquals((int) EXACT_POS, PrimMath.ceil(EXACT_POS));
        assertEquals((int) EXACT_NEG, PrimMath.ceil(EXACT_NEG));
        assertEquals((int) POS+1, PrimMath.ceil(POS));
        assertEquals((int) NEG, PrimMath.ceil(NEG));
    }

    @Test
    public void round() throws Exception {
        assertEquals((int) EXACT_POS, PrimMath.round(EXACT_POS));
        assertEquals((int) EXACT_NEG, PrimMath.round(EXACT_NEG));
        assertEquals((int) POS+1, PrimMath.round(POS));
        assertEquals((int) NEG-1, PrimMath.round(NEG));
    }

}
