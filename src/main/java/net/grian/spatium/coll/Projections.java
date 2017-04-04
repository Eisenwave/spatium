package net.grian.spatium.coll;

import net.grian.spatium.Spatium;
import net.grian.spatium.enums.Axis;
import net.grian.spatium.geo2.*;
import net.grian.spatium.geo3.*;
import net.grian.spatium.transform.Quaternion;
import net.grian.spatium.util.PrimMath;
import org.jetbrains.annotations.NotNull;

public final class Projections {
    
    /**
     * Returns where the point ({@link Vector2}) is being projected onto the {@link Ray2}.
     * <p>
     *     The returned value is a multiplier for the directional vector of the ray at which the ray and the other
     *     object collide with each other.
     * </p>
     * <p>
     *     The point of the collision can be obtained by setting the hypot of the first ray to a multiplier using
     *     {@link Ray2#setLength(double)} (mutation) or {@link Ray2#getPoint(double)} (no mutation).
     * </p>
     *
     * @param ray the ray
     * @param point the point
     * @return the ray multiplier
     */
    public static double pointOnRay(Ray2 ray, Vector2 point) {
        Vector2
            direction = ray.getDirection(),
            originToPoint = Vector2.between(ray.getOrigin(), point);
        
        return originToPoint.dot(direction) / direction.getLengthSquared();
    }

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
    
    public static double[] polygonOnRay(Ray2 ray, Vector2... poly) {
        Vector2
            origin = ray.getOrigin(),
            direction = ray.getDirection();
        double[] t = new double[poly.length];
        double divisor = direction.getLengthSquared();
        
        
        for (int i = 0; i < t.length; i++) {
            Vector2 originToPoint = Vector2.between(origin, poly[i]);
            t[i] = originToPoint.dot(direction) / divisor;
        }
        
        return new double[] {PrimMath.min(t), PrimMath.max(t)};
    }
    
    public static double[] polygonOnRay(Ray2 ray, Polygon2 poly) {
        return polygonOnRay(ray, poly.getVertices());
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
        //Vector3 axisZ = Axis.Z.vector();
        
        if (Spatium.isZero(normal.getX()) && Spatium.isZero(normal.getY()))
            return orthoProjectZ(triangle.getA(), triangle.getB(), triangle.getC());
        
        /* Vector3 axis = normal.cross(axisZ).normalize();
        Quaternion quaternion = Quaternion.fromRotation(axis, normal.angleTo(axisZ)); */
        Quaternion q = Quaternion.fromRotation(normal, Axis.Z.vector());
        
        return orthoProjectZ(
            Quaternion.product(q, triangle.getA()),
            Quaternion.product(q, triangle.getB()),
            Quaternion.product(q, triangle.getC()));
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
    
    @NotNull
    public static Vector2 align(Vector3 point, Axis axis) {
        switch (axis) {
            case X: return Vector2.fromXY(point.getY(), point.getZ());
            case Y: return Vector2.fromXY(point.getX(), point.getZ());
            case Z: return Vector2.fromXY(point.getX(), point.getY());
            default: throw new IllegalArgumentException("invalid axis: "+axis);
        }
    }
    
    @NotNull
    public static Ray2 align(Ray3 ray, Axis axis) {
        return Ray2.fromOD(
            align(ray.getOrigin(), axis),
            align(ray.getDirection(), axis));
    }
    
    @NotNull
    public static Triangle2 align(Triangle3 triangle, Axis axis) {
        return Triangle2.fromPoints(
            align(triangle.getA(), axis),
            align(triangle.getB(), axis),
            align(triangle.getC(), axis));
    }
    
    @NotNull
    public static Circle align(Sphere sphere, Axis axis) {
        return Circle.fromCenterRadius(align(sphere.getCenter(), axis), sphere.getRadius());
    }

}
