package net.grian.spatium.polynom;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class PolynomialsTest {
    
    @Test
    public void sum() throws Exception {
        //4x^4 + 3x^3 + 2x^2 + 1x^1 + 0
        Polynomial a = Polynomial.create(4, 3, 2, 1, 0);
        Polynomial b = Polynomial.create(-4, -3, -2, -1, 0);
        
        assertTrue(Polynomials.sum(a, b).isZero());
    }
    
    @Test
    public void solve_quadratic() throws Exception {
        double[] solution = Polynomials.solve(-1, 7, 8);
        System.out.println(Arrays.toString(solution));
    }
    
    /*
    @Test
    public void solve_cubic() throws Exception {
        double[] solution = Polynomials.solve(1, -4, -9, 36);
        System.out.println(Arrays.toString(solution));
    }
    */
    
}