package net.grian.spatium.geo3;

import net.grian.spatium.impl.Triangle3Impl;
import net.grian.spatium.matrix.Matrix;
import net.grian.spatium.matrix.Matrices;
import net.grian.spatium.transform.Transformation;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

/**
 * A 3D-Triangle3 defined by 3 points.
 */
public interface Triangle3 extends Polygon3, Serializable, Cloneable {
    
    @NotNull
    static Triangle3 fromPoints(Vector3 a, Vector3 b, Vector3 c) {
        return new Triangle3Impl(a, b, c);
    }
    
    @NotNull
    static Triangle3 fromPoints(
            double ax, double ay, double az,
            double bx, double by, double bz,
            double cx, double cy, double cz) {
        
        return new Triangle3Impl(
            ax, ay, az,
            bx, by, bz,
            cx, cy, cz);
    }

    //GETTERS
    
    /**
     * Returns the first triangle vertex.
     *
     * @return the first triangle vertex
     */
    abstract Vector3 getA();
    
    /**
     * Returns the second triangle vertex.
     *
     * @return the second triangle vertex
     */
    abstract Vector3 getB();
    
    /**
     * Returns the third triangle vertex.
     *
     * @return the third triangle vertex
     */
    abstract Vector3 getC();

    /**
     * Returns the length of side <b>c</b> or <b>AB</b> of this triangle.
     *
     * @return the length of side a
     */
    default double getLengthAB() {
        return Vector3.between(getA(), getB()).getLength();
    }

    /**
     * Returns the length of side <b>a</b> or <b>BC</b> of this triangle.
     *
     * @return the length of side b
     */
    default double getLengthBC() {
        return Vector3.between(getB(), getC()).getLength();
    }

    /**
     * Returns the length of side <b>b</b> or <b>CA</b> of this triangle.
     *
     * @return the length of side c
     */
    default double getLengthCA() {
        return Vector3.between(getC(), getA()).getLength();
    }
    
    default Vector3 getCenter() {
        return Vectors.average(getA(), getB(), getC());
    }

    /**
     * Returns the area of this triangle (half surface area).
     *
     * @return the area of the triangle
     *
     * @implNote The default implementation makes use of the fact that the magnitude of the cross product of two
     * vectors is equal to the area of the parallelogram between the vectors, so: <blockquote>
     *     <code>area of triangle = |C-A x B-A| / 2</code>
     * </blockquote>
     */
    default double getArea() {
        Vector3
            a = getA(),
            ab = getB().subtract(a),
            ac = getC().subtract(a);
        return ab.cross(ac).getLength() / 2;
    }
    
    default double getCircumference() {
        return getLengthAB() + getLengthBC() + getLengthCA();
    }
    
    // POLYGON IMPL
    
    @Override
    default int getVertexCount() {
        return 3;
    }
    
    @Override
    default Vector3 getVertex(int index) {
        switch (index) {
            case 0: return getA();
            case 1: return getB();
            case 2: return getC();
            default: throw new IndexOutOfBoundsException(Integer.toString(index));
        }
    }
    
    @Override
    default AxisAlignedBB getBoundaries() {
        Vector3[] minMax = Vectors.minMax(getA(), getB(), getC());
        return AxisAlignedBB.between(minMax[0], minMax[1]);
    }
    
    @Override
    default Vector3[] getVertices() {
        return new Vector3[] {getA(), getB(), getC()};
    }
    
    @Override
    default Vector3 getNormal() {
        Vector3 a = getA();
        return Vector3.between(a, getB()).cross(Vector3.between(a, getC()));
    }
    
    @Override
    default Plane getPlane() {
        return Plane.fromPointNormal(getA(), getNormal());
    }
    
    //CHECKERS

    default boolean equals(Triangle3 triangle) {
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
    default boolean contains(Vector3 point) {
        if (!getBoundaries().contains(point))
            return false;
        
        Vector3
            a = getA(),
            ac = Vector3.between(a, getC()),
            ab = Vector3.between(a, getB()),
            ap = Vector3.between(a, point);

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

    //SETTERS

    abstract void setA(Vector3 point);

    abstract void setB(Vector3 point);

    abstract void setC(Vector3 point);

    default void setCenter(double x, double y, double z) {
        Vector3 p = getCenter();
        translate(x - p.getX(), y - p.getY(), z - p.getZ());
    }

    default void setCenter(Vector3 point) {
        setCenter(point.getX(), point.getY(), point.getZ());
    }

    //TRANSFORMATIONS
    
    /**
     * Translates all points of this triangle by a given amount.
     *
     * @param x the x-translation
     * @param y the y-translation
     * @param z the z-translation
     */
    default void translate(double x, double y, double z) {
        setA(getA().add(x, y, z));
        setB(getB().add(x, y, z));
        setC(getC().add(x, y, z));
    }

    /**
     * Translates all points of this triangle by a given amount.
     *
     * @param v the translation
     */
    default void translate(Vector3 v) {
        translate(v.getX(), v.getY(), v.getZ());
    }
    
    default void transform(Matrix transform) {
        setA(Matrices.product(transform, getA()));
        setB(Matrices.product(transform, getB()));
        setC(Matrices.product(transform, getC()));
    }

    default void transform(Transformation t) {
        Vector3 a = getA(), b = getB(), c = getC();
        t.transform(a);
        t.transform(b);
        t.transform(c);
        setA(a);
        setB(b);
        setC(c);
    }
    
    default void scale(double x, double y, double z) {
        setA(getA().multiply(x, y, z));
        setB(getB().multiply(x, y, z));
        setC(getC().multiply(x, y, z));
    }
    
    default void scale(double factor) {
        scale(factor, factor, factor);
    }
    
    // MISC
    
    abstract Triangle3 clone();

}
