package net.grian.spatium.polynom;

import org.junit.Test;

import static org.junit.Assert.*;

public class PolynomialsTest {
    
    @Test
    public void sum() throws Exception {
        //4x^4 + 3x^3 + 2x^2 + 1x^1 + 0
        Polynomial a = Polynomial.create(4, 3, 2, 1, 0);
        Polynomial b = Polynomial.create(-4, -3, -2, -1, 0);
        
        assertTrue(Polynomials.sum(a, b).isZero());
    }
    
}