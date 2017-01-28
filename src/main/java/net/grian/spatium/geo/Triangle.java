package net.grian.spatium.geo;

import net.grian.spatium.impl.TriangleImpl;

/**
 * A 3D-Triangle defined by 3 points.
 */
public interface Triangle {

    public static Triangle fromPoints(Vector a, Vector b, Vector c) {
        return new TriangleImpl(a, b, c);
    }

    public static Triangle fromPoints(
            double ax, double ay, double az,
            double bx, double by, double bz,
            double cx, double cy, double cz) {

        return fromPoints(
                Vector.fromXYZ(ax, ay, az),
                Vector.fromXYZ(bx, by, bz),
                Vector.fromXYZ(cx, cy, cz));
    }

    //GETTERS

    public abstract Vector getA();

    public abstract Vector getB();

    public abstract Vector getC();

    /**
     * Returns the length of side <b>a</b> or <b>AB</b> of this triangle.
     *
     * @return the length of side a
     */
    public abstract double getLengthA();

    /**
     * Returns the length of side <b>b</b> or <b>BC</b> of this triangle.
     *
     * @return the length of side b
     */
    public abstract double getLengthB();

    /**
     * Returns the length of side <b>c</b> or <b>CA</b> of this triangle.
     *
     * @return the length of side c
     */
    public abstract double getLengthC();

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

    /**
     * Returns the area of this triangle (half surface area). By default, this is done using
     * <a href="https://en.wikipedia.org/wiki/Heron's_formula">Heron's Formula</a>.
     *
     * @return the area of the triangle
     */
    public default double getArea() {
        final double
                a = getLengthA(), b = getLengthB(), c = getLengthC(),
                s = (a + b + c) / 2;

        return Math.sqrt(s * (s-a) * (s-b) * (s-c));
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

    /**
     * Translates all points of this triangle by a given amount.
     *
     * @param x the x-translation
     * @param y the y-translation
     * @param z the z-translation
     * @return itself
     */
    public abstract Triangle move(double x, double y, double z);

    /**
     * Translates all points of this triangle by a given amount.
     *
     * @param v the translation
     * @return itself
     */
    public default Triangle move(Vector v) {
        return move(v.getX(), v.getY(), v.getZ());
    }

    public abstract Triangle scale(double x, double y, double z);

    public default Triangle scale(double factor) {
        return scale(factor, factor, factor);
    }

}
