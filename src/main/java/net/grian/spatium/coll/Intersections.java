package net.grian.spatium.coll;

import net.grian.spatium.Spatium;
import net.grian.spatium.geo3.*;

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
     * Returns the intersection of a {@link Ray3} and a {@link Ray3} in the form of a point ({@link Vector3}).
     *
     * @param a the first ray
     * @param b the second ray
     * @return a new point of intersection
     */
    public static Vector3 of(Ray3 a, Ray3 b) {
        return a.getPoint(Rays.cast(a, b));
    }

    /**
     * Returns the intersection of two {@link AxisAlignedBB3}s. There is two possible scenarios:
     * <ul>
     *     <li>the boxes don't intersect, the result is null</li>
     *     <li>the boxes intersect or touch the result is another box</li>
     * </ul>
     *
     * @param a the first bounding box
     * @param b the second bounding box
     * @return the intersection of the boxes or null
     */
    public static AxisAlignedBB3 of(AxisAlignedBB3 a, AxisAlignedBB3 b) {
        /* Test invoked first since it is a very inexpensive way of checking for no intersection */
        if (!Collisions.test(a, b)) return null;

        return AxisAlignedBB3.fromPoints(
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
    public static Ray3 of(Plane a, Plane b) {
        Vector3
                ac = a.getPoint(),
                bc = b.getPoint(),
                an = a.getNormal(),
                bn = b.getNormal(),
                direction = an.cross(bn),
                lineDir = bn.cross(direction);

        double denominator = an.dot(lineDir);
        ac.subtract(bc);
        
        if (Math.abs(denominator) < Spatium.EPSILON) return null;
        double t = an.dot(ac) / denominator;
        lineDir.multiply(t);
        bc.add(lineDir);
        
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
    public static Ray3 of(AxisPlane a, AxisPlane b) {
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
    public static Vector3[] of(Triangle3 a, Triangle3 b) {
        Vector3[] segment = Intersections.of(a, b.getPlane()); //points on plane in which b lies
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
    public static Vector3 of(Ray3 ray, Plane plane) {
        return ray.getPoint(Rays.cast(ray, plane));
    }

    /**
     * Returns the intersection of a {@link Ray3} and an {@link AxisPlane} in the form of a point ({@link Vector3}).
     *
     * @param ray the ray
     * @param plane the plane
     * @return a new point of intersection
     */
    public static Vector3 of(Ray3 ray, AxisPlane plane) {
        return ray.getPoint(Rays.cast(ray, plane));
    }

    /**
     * Returns the intersection of a {@link Ray3} and a {@link Sphere} in the form of a point ({@link Vector3}).
     *
     * @param ray the ray
     * @param sphere the sphere
     * @return a new point of intersection
     */
    public static Vector3 of(Ray3 ray, Sphere sphere) {
        return ray.getPoint(Rays.cast(ray, sphere));
    }

    /**
     * Returns the intersection of a {@link Ray3} and an {@link AxisAlignedBB3} in the form of a point ({@link Vector3}).
     *
     * @param ray the ray
     * @param box the bounding box
     * @return a new point of intersection
     */
    public static Vector3 of(Ray3 ray, AxisAlignedBB3 box) {
        return ray.getPoint(Rays.cast(ray, box));
    }

    /**
     * Returns the intersection of a {@link Ray3} and an {@link Triangle3} in the form of a point ({@link Vector3}).
     *
     * @param ray the ray
     * @param triangle the triangle
     * @return a new point of intersection
     */
    public static Vector3 of(Ray3 ray, Triangle3 triangle) {
        return ray.getPoint(Rays.cast(ray, triangle));
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
    public static Path3 of(Sphere sphere, Plane plane) {
        Vector3 sc = sphere.getCenter();
        Vector3 negNormal = plane.getNormal();
        negNormal.negate();
        
        Ray3 sphereToPlane = Ray3.fromOD(sc, negNormal);
        if (sphereToPlane.getLengthSquared() > sphere.getRadiusSquared()) return null;

        Vector3 center = sphereToPlane.getPoint(Rays.cast(sphereToPlane, plane));
        double radius = Math.cos(center.distanceTo(sc));
        return Path3.circle(center, radius, plane.getNormal());
    }

    /**
     * Returns the intersection of an {@link AxisAlignedBB3} and an {@link AxisPlane} in the form of a
     * {@link Path3} which represents the edge of the bounding box at the point of intersection.
     *
     * @param box the bounding box
     * @param plane the plane
     * @return a new linear path on the box or null
     */
    public static Path3 of(AxisAlignedBB3 box, AxisPlane plane) {
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
    public static Vector3[] of(Triangle3 triangle, Plane plane) {
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
