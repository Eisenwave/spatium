package net.grian.spatium.coll;

import net.grian.spatium.Spatium;
import net.grian.spatium.enums.Axis;
import net.grian.spatium.geo2.Triangle2;
import net.grian.spatium.geo3.Plane;
import net.grian.spatium.geo3.Ray3;
import net.grian.spatium.geo3.Triangle3;
import net.grian.spatium.geo3.Vector3;
import net.grian.spatium.transform.Quaternion;

public final class Projections {

    /**
     * Returns where the point ({@link Vector3}) is being projected onto the {@link Ray3}.
     * <p>
     *     The returned value is a multiplier for the directional vector of the ray at which the ray and the other
     *     object collide with each other.
     * </p>
     * <p>
     *     The point of the collision can be obtained by setting the hypot of the first ray to a multiplier using
     *     {@link Ray3#setLength(double)} (mutation) or {@link Ray3#getPoint(double)} (no mutation).
     * </p>
     *
     * @param ray the ray
     * @param point the point
     * @return the ray multiplier
     */
    public static double pointOnRay(Ray3 ray, Vector3 point) {
        Vector3
                direction = ray.getDirection(),
                originToPoint = Vector3.between(ray.getOrigin(), point);

        return originToPoint.dot(direction) / direction.getLengthSquared();

        //Vector3 dir = ray.getDirection();
        //return dir.dot(point) / dir.dot(dir);
    }

    /**
     * Returns where the point ({@link Vector3}) is being projected onto the {@link Plane}.
     *
     * @param plane the plane
     * @param point the point
     * @return the ray multiplier
     */
    public static Vector3 pointOnPlane(Plane plane, Vector3 point) {
        Vector3 offset = Vector3.between(plane.getPoint(), point);
        Vector3 normal = plane.getNormal().normalize();
    
        //equivalent but slower code, since it clones point instead of adding it to the negated normal
        /* normal.multiply(offset.dot(normal));
        return point.clone().subtract(normal); */
        
        normal.multiply(-offset.dot(normal));
        return normal.add(point);
    }
    
    /**
     * <p>
     *     Projects the triangle onto the z-axis by rotating all points so they lie on a plane parallel to the Z-plane.
     * </p>
     * <p>
     *     Rotating the vertices before nullifying z-coordinates ensures that the triangle preserves shape and size.
     * </p>
     * <p>
     *     It also ensures that degenerate cases such as ones where the triangle lies in the x- or y-axis and
     *     nullifying their z-coordinates would result in the triangle's area being nullified are safe.
     * </p>
     *
     * @param triangle the triangle
     * @return the flattened triangle
     */
    public static Triangle2 flatten(Triangle3 triangle) {
        Vector3 normal = triangle.getNormal();
        Vector3 baseZ = Axis.Z.vector();
        
        if (Spatium.equals(normal.getX(), 0) && Spatium.equals(normal.getY(), 0))
            return orthoProjectZ(triangle.getA(), triangle.getB(), triangle.getC());
        
        Vector3 axis = normal.cross(baseZ).normalize();
        Quaternion quaternion = Quaternion.fromRotation(axis, normal.angleTo(baseZ));
        
        return orthoProjectZ(
            Quaternion.product(quaternion, triangle.getA()),
            Quaternion.product(quaternion, triangle.getB()),
            Quaternion.product(quaternion, triangle.getC()));
    }
    
    /**
     * <p>
     *     Orthogonally projects three vertices onto the z-plane, forming a 2D-triangle.
     * </p>
     * <p>
     *     This is equivalent to nullifying the z-coordinates of all vertices.
     * </p>
     *
     * @param a the first vertex
     * @param b the second vertex
     * @param c the third vertex
     * @return the projected triangle
     */
    private static Triangle2 orthoProjectZ(Vector3 a, Vector3 b, Vector3 c) {
        return Triangle2.fromPoints(a.getX(), a.getY(), b.getX(), b.getY(), c.getX(), c.getY());
    }

}
