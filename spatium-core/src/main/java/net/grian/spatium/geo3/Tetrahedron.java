package net.grian.spatium.geo3;

import net.grian.spatium.impl.TetrahedronImpl;
import org.jetbrains.annotations.NotNull;

public interface Tetrahedron extends Space, Cloneable {
    
    /**
     * Constructs a new tetrahedron from 4 points.
     *
     * @return a new tetrahedron
     */
    @NotNull
    static Tetrahedron fromPoints(double ax, double ay, double az,
                                  double bx, double by, double bz,
                                  double cx, double cy, double cz,
                                  double dx, double dy, double dz) {
        return new TetrahedronImpl(ax, ay, az, bx, by, bz, cx, cy, cz, dx, dy, dz);
    }
    
    /**
     * Constructs a new tetrahedron from 4 points.
     *
     * @return a new tetrahedron
     */
    @NotNull
    static Tetrahedron fromPoints(Vector3 a, Vector3 b, Vector3 c, Vector3 d) {
        return new TetrahedronImpl(a, b, c, d);
    }
    
    // GETTERS
    
    abstract Vector3 getA();
    
    abstract Vector3 getB();
    
    abstract Vector3 getC();
    
    abstract Vector3 getD();
    
    /**
     * <p>
     *     Returns one of the vertices this tetrahedron consists of:
     *     <blockquote>
     *         <code>0 -> A</code>
     *     <br><code>1 -> B</code>
     *     <br><code>2 -> C</code>
     *     <br><code>3 -> D</code>
     * </blockquote>
     * </p>
     *
     * @param index the index of the triangle in range(0,4)
     * @return the vertex
     */
    default Vector3 getVertex(int index) {
        switch (index) {
            case 0: return getA();
            case 1: return getB();
            case 2: return getC();
            case 3: return getD();
            default: throw new IndexOutOfBoundsException(index+" must be in range(0,3)");
        }
    }
    
    /**
     * <p>
     *     Returns one of the triangles this tetrahedron consists of:
     *     <blockquote>
     *         <code>0 -> ABC</code>
     *     <br><code>1 -> BCD</code>
     *     <br><code>2 -> CDA</code>
     *     <br><code>3 -> DAB</code>
     * </blockquote>
     * </p>
     *
     * @param index the index of the triangle in range(0,3)
     * @return the triangle
     */
    default Triangle3 getTriangle(int index) {
        final Vector3
            a = getVertex(index),
            b = getVertex(++index % 4),
            c = getVertex(++index % 4);
        return Triangle3.fromPoints(a, b, c);
    }
    
    /**
     * <p>
     *     Returns one of the planes this tetrahedron consists of:
     *     <blockquote>
     *         <code>0 -> ABC</code>
     *     <br><code>1 -> BCD</code>
     *     <br><code>2 -> CDA</code>
     *     <br><code>3 -> DAB</code>
     * </blockquote>
     * </p>
     *
     * @param index the index of the triangle in range(0,3)
     * @return the triangle
     */
    default Plane getPlane(int index) {
        return getTriangle(index).getPlane();
    }
    
    /**
     * <p>
     *     Returns the normal of one of the four triangles this tetrahedron concists of.
     * </p>
     * <p>
     *     The returned normal is not guaranteed to be normalized, but is guaranteed to point away from the center of
     *     the tetrahedron.
     * </p>
     *
     * @param index the index of the triangle in range(0,4)
     * @return the normal of the triangle
     */
    default Vector3 getNormal(int index) {
        return getTriangle(index).getCenter().subtract(getCenter());
    }
    
    @Override
    default Vector3 getCenter() {
        return Vectors.average(getA(), getB(), getC(), getD());
    }
    
    @Override
    default double getVolume() {
        final Vector3
            a = getA(),
            ab = getB().subtract(a),
            ac = getC().subtract(a),
            ad = getD().subtract(a);
        return Math.abs(Vectors.triple(ab, ac, ad)) / 6;
    }
    
    @Override
    default double getSurfaceArea() {
        double area = 0;
        for (int i = 0; i < 4; i++)
            area += getTriangle(i).getArea();
        return area;
    }
    
    // CHECKERS
    
    /**
     * Checks whether the tetrahedron contains a given point.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return whether the tetrahedron contains the point
     *
     * @implNote The default implementation checks whether the point is on the "back side" of the four planes that this
     * tetrahedron consists of.
     */
    @Override
    default boolean contains(double x, double y, double z) {
        for (int i = 0; i < 4; i++)
            if (getPlane(i).signedDistanceTo(x, y, z) > 0)
                return false;
        return true;
    }
    
    // SETTERS
    
    abstract void setA(Vector3 point);
    
    abstract void setB(Vector3 point);
    
    abstract void setC(Vector3 point);
    
    abstract void setD(Vector3 point);
    
    // TRANSFORMATIONS
    
    @Override
    default void translate(double x, double y, double z) {
        setA(getA().add(x, y, z));
        setB(getB().add(x, y, z));
        setC(getC().add(x, y, z));
        setD(getD().add(x, y, z));
    }
    
    default void scale(double x, double y, double z) {
        setA(getA().multiply(x, y, z));
        setB(getB().multiply(x, y, z));
        setC(getC().multiply(x, y, z));
        setD(getD().multiply(x, y, z));
    }
    
    @Override
    default void scale(double factor) {
        scale(factor, factor, factor);
    }
    
    // MISC
    
    abstract Tetrahedron clone();
    
}
