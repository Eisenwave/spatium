package net.grian.spatium.polynom;

import net.grian.spatium.util.PrimMath;

public final class Polynomials {
    
    private Polynomials() {}
    
    public static Polynomial sum(Polynomial a, Polynomial b) {
        final int degree = PrimMath.max(a.getDegree(), b.getDegree());
        Polynomial sum = Polynomial.create(degree);
        
        for (int i = 0; i<=degree; i++)
            sum.set(i, a.get(i)+b.get(i));
        
        return sum;
    }
    
}
