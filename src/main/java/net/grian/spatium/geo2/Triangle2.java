package net.grian.spatium.geo2;

import net.grian.spatium.impl.Triangle2Impl;

public interface Triangle2 extends Area {
    
    static Triangle2 fromPoints(Vector2 a, Vector2 b, Vector2 c) {
        return new Triangle2Impl(a, b, c);
    }
    
    static Triangle2 fromPoints(double ax, double ay, double bx, double by, double cx, double cy) {
        return new Triangle2Impl(ax, ay, bx, by, cx, cy);
    }
    
    //GETTERS
    
    abstract Vector2 getA();
    
    abstract Vector2 getB();
    
    abstract Vector2 getC();
    
    default Vector2 getCenter() {
        return getA().add(getB()).add(getC()).divide(3);
    }
    
    /**
     * Returns the length of side <b>c</b> or <b>AB</b> of this triangle.
     *
     * @return the length of side a
     */
    default double getLengthAB() {
        return Vector2.between(getA(), getB()).getLength();
    }
    
    /**
     * Returns the length of side <b>a</b> or <b>BC</b> of this triangle.
     *
     * @return the length of side b
     */
    default double getLengthBC() {
        return Vector2.between(getB(), getC()).getLength();
    }
    
    /**
     * Returns the length of side <b>b</b> or <b>CA</b> of this triangle.
     *
     * @return the length of side c
     */
    default double getLengthCA() {
        return Vector2.between(getC(), getA()).getLength();
    }
    
    /**
     * Returns the area of this triangle (half surface area). By default, this is done using
     * <a href="https://en.wikipedia.org/wiki/Heron's_formula">Heron's Formula</a>.
     *
     * @return the area of the triangle
     */
    @Override
    default double getArea() {
        final double
            ab = getLengthAB(), bc = getLengthBC(), ca = getLengthCA(),
            s = (ab + bc + ca) / 2;
        
        return Math.sqrt(s * (s-ab) * (s-bc) * (s-ca));
    }
    
    @Override
    default double getCircumference() {
        return getLengthAB() + getLengthBC() + getLengthCA();
    }
    
    //SETTERS
    
}
