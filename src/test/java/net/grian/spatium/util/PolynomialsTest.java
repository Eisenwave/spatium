package net.grian.spatium.util;

import org.junit.Test;

import java.util.Arrays;

public class PolynomialsTest {
    
    @Test
    public void solve() throws Exception {
        double[] solution = Polynomials.solve(-1, 7, 8);
        System.out.println(Arrays.toString(solution));
    }
    
}