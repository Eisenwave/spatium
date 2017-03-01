package net.grian.spatium.coll;

import net.grian.spatium.Spatium;
import net.grian.spatium.geo2.*;
import net.grian.spatium.geo3.*;
import org.jetbrains.annotations.Nullable;

/**
 * <p>
 *     Utility class for getting intersections between two objects.
 * </p>
 * <p>
 *     None of these methods performs any kind of mutation upon the parameters, all returned objects are freshly
 *     constructed objects.
 * </p>
 */
public final class Intersections {

    private Intersections() {}

    //AUTO INTERSECTIONS
    
    /**
     * Returns the intersection of a {@link Ray2} and a {@link Ray2} in the form of a point ({@link Vector2}).
     *
     * @param a the first ray
     * @param b the second ray
     * @return a new point of intersection
     */
    @Nullable
    public static Vector2 rayRay(Ray2 a, Ray2 b) {
        double t = Rays.cast(a, b);
        return Double.isFinite(t)? a.getPoint(t) : null;
    }
    
    /**
     * Returns the intersection of two line segments in the form of a point ({@link Vector2}).
     *
     * @param a1 the org. of the first line segment
     * @param a2 the end of hte first line segment
     * @param b1 the org. of the second line segment
     * @param b2 the end of hte second line segment
     * @param infinite whether an intersection on the infinite versions of the lines is allowed
     * @return a new point of intersection
     */
    @Nullable
    public static Vector2 lineSegLineSeg(Vector2 a1, Vector2 a2, Vector2 b1, Vector2 b2, boolean infinite) {
        Ray2
            a = Ray2.between(a1, a2),
            b = Ray2.between(b1, b2);
        
        double t = Rays.cast(a, b);
        
        return Double.isFinite(t) && ( infinite || (t >= 0 && t <= 1) )?
            a.getPoint(t) :
            null;
    }

    /**
     * Returns the intersection of a {@link Ray3} and a {@link Ray3} in the form of a point ({@link Vector3}).
     *
     * @param a the first ray
     * @param b the second ray
     * @return a new point of intersection
     */
    @Nullable
    public static Vector3 rayRay(Ray3 a, Ray3 b) {
        double t = Rays.cast(a, b);
        return Double.isFinite(t)? a.getPoint(t) : null;
    }

    /**
     * Returns the intersection of two {@link AxisAlignedBB}s. There is two possible scenarios:
     * <ul>
     *     <li>the boxes don't intersect, the result is null</li>
     *     <li>the boxes intersect or touch the result is another box</li>
     * </ul>
     *
     * @param a the first bounding box
     * @param b the second bounding box
     * @return the intersection of the boxes or null
     */
    @Nullable
    public static AxisAlignedBB boxBox(AxisAlignedBB a, AxisAlignedBB b) {
        /* Test invoked first since it is a very inexpensive way of checking for no intersection */
        if (!Collisions.test(a, b)) return null;

        return AxisAlignedBB.fromPoints(
                Math.max(a.getMinX(), b.getMinX()),
                Math.max(a.getMinY(), b.getMinY()),
                Math.max(a.getMinZ(), b.getMinZ()),
                Math.min(a.getMaxX(), b.getMaxX()),
                Math.min(a.getMaxY(), b.getMaxY()),
                Math.min(a.getMaxZ(), b.getMaxZ())
        );
    }

    /**
     * Returns the intersection of two {@link Plane}s in the form of a {@link Ray3}. There is three possible scenarios:
     * <ul>
     *     <li>the planes are parallel thus there is no intersection, the result is null</li>
     *     <li>the planes are identical, the intersection is a plane, the result is null</li>
     *     <li>the planes intersect, the result is a line (ray)</li>
     * </ul>
     *
     * @param a the first plane
     * @param b the second plane
     * @return the intersection of the planes or null
     */
    @Nullable
    public static Ray3 planePlane(Plane a, Plane b) {
        Vector3
            ac = a.getPoint(),
            bc = b.getPoint(),
            an = a.getNormal(),
            bn = b.getNormal(),
            direction = an.cross(bn),
            lineDir = bn.cross(direction);

        double denominator = an.dot(lineDir);
        ac.subtract(bc);
        
        if (Spatium.isZero(denominator)) return null;
        double t = an.dot(ac) / denominator;
        bc.add(lineDir.multiply(t));
        
        return Ray3.fromOD(bc, direction);
    }

    /**
     * Returns the intersection of two {@link AxisPlane}s in the form of a {@link Ray3}.
     * There is three possible scenarios:
     * <ul>
     *     <li>the planes are parallel thus there is no intersection, the result is null</li>
     *     <li>the planes are identical, the intersection is a plane, the result is null</li>
     *     <li>the planes intersect, the result is a line (ray)</li>
     * </ul>
     *
     * @param a the first plane
     * @param b the second plane
     * @return the intersection of the planes or null
     */
    @Nullable
    public static Ray3 planePlane(AxisPlane a, AxisPlane b) {
        switch (a.getAxis()) {
            case X: switch (b.getAxis()) {
                case X: return null;
                case Y: return Ray3.fromOD(a.getDepth(), b.getDepth(), 0, 0, 0, 1);
                case Z: return Ray3.fromOD(a.getDepth(), 0, b.getDepth(), 0, 1, 0);
                default: throw new IllegalArgumentException("b has no axis");
            }
            case Y: switch (b.getAxis()) {
                case X: return Ray3.fromOD(b.getDepth(), a.getDepth(), 0, 0, 0, 1);
                case Y: return null;
                case Z: return Ray3.fromOD(0, a.getDepth(), b.getDepth(), 1, 0, 0);
                default: throw new IllegalArgumentException("b has no axis");
            }
            case Z: switch (b.getAxis()) {
                case X: return Ray3.fromOD(b.getDepth(), 0, a.getDepth(), 0, 1, 0);
                case Y: return Ray3.fromOD(0, b.getDepth(), a.getDepth(), 0, 0, 1);
                case Z: return null;
                default: throw new IllegalArgumentException("b has no axis");
            }
            default: throw new IllegalArgumentException("a has no axis");
        }
    }

    /**
     * <p>
     *     Returns the intersection of a {@link Triangle3} and an {@link Triangle3} in the form of a {@link Vector3}[].
     * </p>
     * Source: <a href="http://www.realtimerendering.com/intersections.html">Martin Held<a/>
     *
     * @param a the first triangle
     * @param b the second triangle
     * @return the origin and end of the intersection
     */
    @Nullable
    public static Vector3[] triangleTriangle(Triangle3 a, Triangle3 b) {
        Vector3[] segment = trianglePlane(a, b.getPlane()); //points on plane in which b lies
        if (segment == null) return null;
        return b.contains(segment[0]) || b.contains(segment[1]) ? segment : null;
    }

    //RAY INTERSECTIONS

    /**
     * Returns the intersection of a {@link Ray3} and a {@link Plane} in the form of a point ({@link Vector3}).
     *
     * @param ray the ray
     * @param plane the plane
     * @return a new point of intersection
     */
    @Nullable
    public static Vector3 rayPlane(Ray3 ray, Plane plane) {
        double t = Rays.cast(ray, plane);
        return Double.isFinite(t)? ray.getPoint(t) : null;
    }

    /**
     * Returns the intersection of a {@link Ray3} and an {@link AxisPlane} in the form of a point ({@link Vector3}).
     *
     * @param ray the ray
     * @param plane the plane
     * @return a new point of intersection
     */
    @Nullable
    public static Vector3 rayPlane(Ray3 ray, AxisPlane plane) {
        double t = Rays.cast(ray, plane);
        return Double.isFinite(t)? ray.getPoint(t) : null;
    }
    
    /**
     * Returns the intersection of a {@link Ray2} and a {@link Circle} in the form of a point ({@link Vector3}).
     *
     * @param ray the ray
     * @param circle the sphere
     * @return a new point of intersection
     */
    @Nullable
    public static Ray2 rayCircle(Ray2 ray, Circle circle) {
        double[] t = Rays.pierce(ray, circle);
        return t==null? null : Ray2.between( ray.getPoint(t[0]), ray.getPoint(t[1]) );
    }

    /**
     * Returns the intersection of a {@link Ray3} and a {@link Sphere} in the form of a point ({@link Vector3}).
     *
     * @param ray the ray
     * @param sphere the sphere
     * @return a new point of intersection
     */
    @Nullable
    public static Ray3 raySphere(Ray3 ray, Sphere sphere) {
        double[] t = Rays.pierce(ray, sphere);
        return t==null? null : Ray3.between( ray.getPoint(t[0]), ray.getPoint(t[1]) );
    }
    
    /**
     * Returns the intersection of a {@link Ray2} and an {@link Rectangle} in the form of a line segment ({@link Ray2}).
     *
     * @param ray the ray
     * @param box the bounding box
     * @return a new point of intersection
     */
    @Nullable
    public static Ray2 rayBox(Ray2 ray, Rectangle box) {
        double[] t = Rays.pierce(ray, box);
        return t==null? null : Ray2.between( ray.getPoint(t[0]), ray.getPoint(t[1]) );
    }

    /**
     * Returns the intersection of a {@link Ray3} and an {@link AxisAlignedBB} in the form of a point ({@link Vector3}).
     *
     * @param ray the ray
     * @param box the bounding box
     * @return a new point of intersection
     */
    @Nullable
    public static Ray3 rayBox(Ray3 ray, AxisAlignedBB box) {
        double[] t = Rays.pierce(ray, box);
        return t==null? null : Ray3.between( ray.getPoint(t[0]), ray.getPoint(t[1]) );
    }

    /**
     * Returns the intersection of a {@link Ray3} and an {@link Triangle3} in the form of a point ({@link Vector3}).
     *
     * @param ray the ray
     * @param triangle the triangle
     * @return a new point of intersection
     */
    @Nullable
    public static Vector3 rayTriangle(Ray3 ray, Triangle3 triangle) {
        double t = Rays.cast(ray, triangle);
        return Double.isFinite(t)? ray.getPoint(t) : null;
    }

    //OTHER INTERSECTIONS

    /**
     * Returns the intersection of a {@link Sphere} and a {@link Plane} in the form of a {@link Path3} which represents
     * a circle on the sphere.
     *
     * @param sphere the sphere
     * @param plane the plane
     * @return a new circular path on the sphere or null
     */
    @Nullable
    public static Path3 spherePlane(Sphere sphere, Plane plane) {
        Vector3 sc = sphere.getCenter();
        Vector3 negNormal = plane.getNormal().negate();
        
        Ray3 sphereToPlane = Ray3.fromOD(sc, negNormal);
        if (sphereToPlane.getLengthSquared() > sphere.getRadiusSquared()) return null;

        Vector3 center = sphereToPlane.getPoint(Rays.cast(sphereToPlane, plane));
        double radius = Math.cos(center.distanceTo(sc));
        return Path3.circle(center, radius, plane.getNormal());
    }

    /**
     * Returns the intersection of an {@link AxisAlignedBB} and an {@link AxisPlane} in the form of a
     * {@link Path3} which represents the edge of the bounding box at the point of intersection.
     *
     * @param box the bounding box
     * @param plane the plane
     * @return a new linear path on the box or null
     */
    @Nullable
    public static Path3 boxPlane(AxisAlignedBB box, AxisPlane plane) {
        if (!Collisions.test(box, plane)) return null;
        double depth = plane.getDepth();
        Vector3 a, b, c, d;
        switch (plane.getAxis()) {
            case X: {
                a = Vector3.fromXYZ(depth, box.getMinY(), box.getMinZ());
                b = Vector3.fromXYZ(depth, box.getMaxY(), box.getMinZ());
                c = Vector3.fromXYZ(depth, box.getMaxY(), box.getMaxZ());
                d = Vector3.fromXYZ(depth, box.getMinY(), box.getMaxZ());
                break;
            }
            case Y: {
                a = Vector3.fromXYZ(box.getMinX(), depth, box.getMinZ());
                b = Vector3.fromXYZ(box.getMaxX(), depth, box.getMinZ());
                c = Vector3.fromXYZ(box.getMaxX(), depth, box.getMaxZ());
                d = Vector3.fromXYZ(box.getMinX(), depth, box.getMaxZ());
                break;
            }
            case Z: {
                a = Vector3.fromXYZ(box.getMinX(), box.getMinY(), depth);
                b = Vector3.fromXYZ(box.getMaxX(), box.getMinY(), depth);
                c = Vector3.fromXYZ(box.getMaxX(), box.getMaxY(), depth);
                d = Vector3.fromXYZ(box.getMinX(), box.getMaxY(), depth);
                break;
            }
            default: throw new IllegalArgumentException("plane has no axis");
        }

        return Path3.linear(a, b, c, d, a);
    }

    /**
     * <p>
     *     Returns the intersection of a {@link Triangle3} and a {@link Plane} in the form of a {@link Vector3}[] which
     *     represents contains two points of intersection between the triangle and the plane.
     * </p>
     * <p>
     *     The two points are retrieved using the sides of the triangle and if they exist, will both lie within the
     *     plane.
     * </p>
     *
     * @param triangle the triangle
     * @param plane the plane
     * @return two points of intersection or null
     */
    @Nullable
    public static Vector3[] trianglePlane(Triangle3 triangle, Plane plane) {
        Ray3     //3 rays which the triangle consists of
                rayAB = Ray3.between(triangle.getA(), triangle.getB()),
                rayAC = Ray3.between(triangle.getA(), triangle.getC()),
                rayBC = Ray3.between(triangle.getB(), triangle.getC());
        double
                castAB = Rays.cast(rayAB, plane),
                castAC = Rays.cast(rayAC, plane),
                castBC = Rays.cast(rayBC, plane);

        Vector3  //3 points of ray intersections with plane
                ab = ( Double.isFinite(castAB) && castAB > 0 && castAB < 1 )? rayAB.getPoint(castAB) : null,
                ac = ( Double.isFinite(castAC) && castAC > 0 && castAC < 1 )? rayAB.getPoint(castAC) : null,
                bc = ( Double.isFinite(castBC) && castBC > 0 && castBC < 1 )? rayAB.getPoint(castBC) : null;

        if (ab == null)
            return ac == null || bc == null? null : new Vector3[] {ac, bc};
        else if (ac == null)
            return bc == null? null : new Vector3[] {ab, bc};
        else if (bc == null)
            return new Vector3[] {ab, ac};
        else //none of the points are null, the triangle must be inside the plane
            return null;
    }

}
