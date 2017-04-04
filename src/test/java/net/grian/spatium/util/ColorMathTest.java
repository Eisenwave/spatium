package net.grian.spatium.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class ColorMathTest {
    
    /*
    @Test
    public void xyz() throws Exception {
        float[] magenta = ColorMath.xyz(ColorMath.SOLID_MAGENTA);
        assertArrayEquals(new float[] {0.5792F, 0.2831F, 0.7281F}, magenta, 1E-5F);
    }
    */

    private final static int
    SOLID = ColorMath.fromRGB(1F, 1F, 1F, 1F),
    TRANSPARENT = ColorMath.fromRGB(1F, 1F, 1F, 0.5F),
    INVISIBLE = ColorMath.fromRGB(1F, 1F, 1F, 0F);

    @Test
    public void isTransparent() throws Exception {
        assertFalse(ColorMath.isTransparent(SOLID));
        assertTrue(ColorMath.isTransparent(TRANSPARENT));
        assertTrue(ColorMath.isTransparent(INVISIBLE));
    }

    @Test
    public void isSolid() throws Exception {
        assertTrue(ColorMath.isSolid(SOLID));
        assertFalse(ColorMath.isSolid(TRANSPARENT));
        assertFalse(ColorMath.isSolid(INVISIBLE));
    }

    @Test
    public void isVisible() throws Exception {
        assertTrue(ColorMath.isVisible(SOLID));
        assertTrue(ColorMath.isVisible(TRANSPARENT));
        assertFalse(ColorMath.isVisible(INVISIBLE));
    }

    @Test
    public void isInvisible() throws Exception {
        assertFalse(ColorMath.isInvisible(SOLID));
        assertFalse(ColorMath.isInvisible(TRANSPARENT));
        assertTrue(ColorMath.isInvisible(INVISIBLE));
    }

}