package net.grian.spatium.geo2;

import net.grian.spatium.impl.Triangle2Impl;
import eisenwave.spatium.util.PrimMath;
import org.jetbrains.annotations.NotNull;

public interface Triangle2 extends Area, Polygon2 {
    
    @NotNull
    static Triangle2 fromPoints(Vector2 a, Vector2 b, Vector2 c) {
        return new Triangle2Impl(a, b, c);
    }
    
    @NotNull
    static Triangle2 fromPoints(double ax, double ay, double bx, double by, double cx, double cy) {
        return new Triangle2Impl(ax, ay, bx, by, cx, cy);
    }
    
    //GETTERS
    
    abstract Vector2 getA();
    
    abstract Vector2 getB();
    
    abstract Vector2 getC();
    
    @Override
    default Vector2 getCenter() {
        return getA().add(getB()).add(getC()).divide(3);
    }
    
    /**
     * Returns the length of side <b>c</b> or <b>AB</b> of this triangle.
     *
     * @return the length of side a
     */
    default double getLengthAB() {
        return getB().subtract(getA()).getLength();
    }
    
    /**
     * Returns the length of side <b>a</b> or <b>BC</b> of this triangle.
     *
     * @return the length of side b
     */
    default double getLengthBC() {
        return getC().subtract(getB()).getLength();
    }
    
    /**
     * Returns the length of side <b>b</b> or <b>CA</b> of this triangle.
     *
     * @return the length of side c
     */
    default double getLengthCA() {
        return getA().subtract(getC()).getLength();
    }
    
    /**
     * Returns the area of this triangle (half surface area).
     *
     * @return the area of the triangle
     * @implNote The default implementation uses the same method as {@link net.grian.spatium.geo3.Triangle3}.
     */
    @Override
    default double getArea() {
        final Vector2
            a = getA(),
            ab = getB().subtract(a),
            ac = getC().subtract(a);
        return Math.abs( ab.getX()*ac.getY() - ab.getY()*ac.getX() ) / 2;
    }
    
    @Override
    default double getCircumference() {
        return getLengthAB() + getLengthBC() + getLengthCA();
    }
    
    // POLYGON IMPL
    
    @Override
    default int getVertexCount() {
        return 3;
    }
    
    @Override
    default Vector2 getVertex(int index) {
        switch (index) {
            case 0: return getA();
            case 1: return getB();
            case 2: return getC();
            default: throw new IndexOutOfBoundsException(index+" must be in range 0-2");
        }
    }
    
    @Override
    default Ray2 getEdge(int index) {
        switch (index) {
            case 0: return Ray2.between(getA(), getB());
            case 1: return Ray2.between(getB(), getC());
            case 2: return Ray2.between(getC(), getA());
            default: throw new IndexOutOfBoundsException(index+" must be in range 0-2");
        }
    }
    
    @Override
    default Vector2[] getVertices() {
        return new Vector2[] {getA(), getB(), getC()};
    }
    
    @Override
    default Rectangle getBoundaries() {
        final Vector2 a = getA(), b = getB(), c = getC();
        return Rectangle.fromPoints(
            PrimMath.min(a.getX(), b.getX(), c.getX()),
            PrimMath.min(a.getY(), b.getY(), c.getY()),
            PrimMath.max(a.getX(), b.getX(), c.getX()),
            PrimMath.max(a.getY(), b.getY(), c.getY()));
    }
    
    // CHECKERS
    
    @Override
    default boolean contains(double x, double y) {
        if (!getBoundaries().contains(x, y))
            return false;
        
        Vector2
            a = getA(),
            ac = getC().subtract(a),
            ab = getB().subtract(a),
            ap = Vector2.fromXY(x, y).subtract(x, y);
    
        double
            ac_ac = ac.dot(ac),
            ac_ab = ac.dot(ab),
            ac_ap = ac.dot(ap),
            ab_ab = ab.dot(ab),
            ab_ap = ab.dot(ap),
            div = 1 / (ac_ac * ab_ab - ac_ab * ac_ab),
            u = (ab_ab * ac_ap - ac_ab * ab_ap) * div,
            v = (ac_ac * ab_ap - ac_ab * ac_ap) * div;
    
        return (u >= 0) && (v >= 0) && (u + v < 1);
    }
    
    // SETTERS
    
    abstract void setA(Vector2 point);
    
    abstract void setB(Vector2 point);
    
    abstract void setC(Vector2 point);
    
    // TRANSFORMATIONS
    
    @Override
    default void translate(double x, double y) {
        setA(getA().add(x, y));
        setB(getB().add(x, y));
        setC(getC().add(x, y));
    }
    
    default void scale(double x, double y) {
        setA(getA().multiply(x, y));
        setB(getB().multiply(x, y));
        setC(getC().multiply(x, y));
    }
    
    default void scaleCentric(double x, double y) {
        Vector2 center = getCenter();
        translate(-center.getX(), -center.getY());
        scale(x, y);
        translate( center.getX(),  center.getY());
    }
    
    @Override
    default void scale(double factor) {
        scale(factor, factor);
    }
    
    @Override
    default void scaleCentric(double factor) {
        scaleCentric(factor, factor);
    }
    
    // MISC
    
    abstract Triangle2 clone();
    
}
