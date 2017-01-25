package net.grian.spatium.geo;

import net.grian.spatium.impl.TriangleImpl;

/**
 * A 3D-Triangle defined by 3 points.
 */
public interface Triangle {

    public static Triangle create(Vector a, Vector b, Vector c) {
        return new TriangleImpl(a, b, c);
    }

    //GETTERS

    public abstract Vector getA();

    public abstract Vector getB();

    public abstract Vector getC();

    public default Vector[] getPoints() {
        return new Vector[] {getA(), getB(), getC()};
    }

    public abstract Vector getNormal();

    public abstract Vector getCenter();

    /**
     * Returns the plane in which this triangle lies.
     *
     * @return the plane in which this triangle lies
     */
    public default Plane getPlane() {
        return Plane.fromPointNormal(getA(), getNormal());
    }

    //CHECKERS

    public default boolean equals(Triangle triangle) {
        return
                this.getA().equals(triangle.getA()) &&
                this.getB().equals(triangle.getB()) &&
                this.getC().equals(triangle.getC());
    }

    /**
     * Checks whether this triangle contains a point.
     *
     * <br><br>Source: <a href="http://blackpawn.com/texts/pointinpoly/">Barycentric Technique</a>
     *
     * @param point the point
     * @return whether this triangle contains the point
     */
    public default boolean contains(Vector point) {
        Vector
                a = getA(),
                ac = Vector.between(a, getC()),
                ab = Vector.between(a, getB()),
                ap = Vector.between(a, point);

        double
                ac_ac = ac.dot(ac),
                ac_ab = ac.dot(ab),
                ac_ap = ac.dot(ap),
                ab_ab = ab.dot(ab),
                ab_ap = ab.dot(ap),
                invDenom = 1 / (ac_ac * ab_ab - ac_ab * ac_ab),
                u = (ab_ab * ac_ap - ac_ab * ab_ap) * invDenom,
                v = (ac_ac * ab_ap - ac_ab * ac_ap) * invDenom;

        return (u >= 0) && (v >= 0) && (u + v < 1);
    }

    //SETTERS

    public abstract Triangle setA(Vector point);

    public abstract Triangle setB(Vector point);

    public abstract Triangle setC(Vector point);

    public default Triangle setCenter(double x, double y, double z) {
        Vector p = getCenter();
        return move(x - p.getX(), y - p.getY(), z - p.getZ());
    }

    public default Triangle setCenter(Vector point) {
        return move(point.subtract(getCenter()));
    }

    public abstract Triangle move(double x, double y, double z);

    public default Triangle move(Vector v) {
        return move(v.getX(), v.getY(), v.getZ());
    }

    public abstract Triangle scale(double x, double y, double z);

    public default Triangle scale(double factor) {
        return scale(factor, factor, factor);
    }

}
