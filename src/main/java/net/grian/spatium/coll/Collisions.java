package net.grian.spatium.coll;

import net.grian.spatium.Spatium;
import net.grian.spatium.geo2.*;
import net.grian.spatium.geo3.*;
import net.grian.spatium.matrix.Matrix;
import net.grian.spatium.util.PrimMath;
import org.jetbrains.annotations.Contract;

/**
 * <p>
 *     Utility class for detecting collisions between two objects.
 * </p>
 * <p>
 *     None of these methods performs any kind of mutation upon the parameters, all returned objects are freshly
 *     constructed objects.
 * </p>
 *
 * @see Rays
 */
public final class Collisions {

    private Collisions() {}

    //AUTO - TESTS

    /**
     * Tests whether two points collide. This is equivalent to testing whether the two points are in the same position.
     *
     * @param a the first point
     * @param b the second point
     * @return whether the points collide
     */
    public static boolean test(Vector3 a, Vector3 b) {
        return a.equals(b);
    }
    
    /**
     * Tests whether two rays collide. This is equivalent to testing whether the two points are in the same position.
     *
     * @param a the first ray
     * @param b the second ray
     * @return whether the rays collide
     */
    public static boolean test(Ray2 a, Ray2 b) {
        return !a.getDirection().isMultipleOf(b.getDirection());
    }
    
    /**
     * Returns whether two {@link AxisPlane}s collide/intersect.
     *
     * @param a the first plane
     * @param b the second plane
     * @return whether the planes collide intersect
     */
    public static boolean test(AxisPlane a, AxisPlane b) {
        return a.getAxis() != b.getAxis() || Spatium.equals(a.getDepth(), b.getDepth());
    }
    
    /**
     * Tests whether two {@link Plane}s collide. This is equivalent to testing whether to planes are not parallel. This
     * means that two planes which are mathematically identical and collide with each other at infinitely many points
     * still do not collide.
     *
     * @param a the first plane
     * @param b the second plane
     * @return whether the points collide
     */
    public static boolean test(Plane a, Plane b) {
        Vector3 normalA = a.getNormal();
        Vector3 normalB = b.getNormal();
        
        return !normalA.isMultipleOf(normalB);
    }
    
    /**
     * Tests whether two {@link Circle}s collide/intersect.
     *
     * @param a the first sphere
     * @param b the second sphere
     * @return whether the spheres collide/intersect.
     */
    public static boolean test(Circle a, Circle b) {
        return a.getCenter().distanceTo(b.getCenter()) <= a.getRadius() + b.getRadius();
    }

    /**
     * Tests whether two {@link Sphere}s collide/intersect.
     *
     * @param a the first sphere
     * @param b the second sphere
     * @return whether the spheres collide/intersect.
     */
    public static boolean test(Sphere a, Sphere b) {
        return a.getCenter().distanceTo(b.getCenter()) <= a.getRadius() + b.getRadius();
    }
    
    /**
     * Tests whether two {@link AxisAlignedBB}s collide/intersect.
     *
     * @param a the first bounding box
     * @param b the second bounding box
     * @return whether the boxes collide/intersect
     */
    public static boolean test(AxisAlignedBB a, AxisAlignedBB b) {
        return
            a.getMinX() <= b.getMaxX() &&
            a.getMaxX() >= b.getMinX() &&
            a.getMinY() <= b.getMaxY() &&
            a.getMaxY() >= b.getMinY() &&
            a.getMinZ() <= b.getMaxZ() &&
            a.getMaxZ() >= b.getMinZ();
    }
    
    /**
     * Tests whether two {@link OrientedBB}s collide/intersect.
     *
     * @param a the first bounding box
     * @param b the second bounding box
     * @return whether the boxes collide/intersect
     */
    public static boolean test(OrientedBB a, OrientedBB b) {
        //inverse transform matrix of a allows for conversion of second box to local space of first box
        //due to this, the first box can be seen as an AABB after transforming second box
        Matrix toLocalTransform = a.getTransform().getInverse();
        
        //more simple AABB & OBB intersection test
        AxisAlignedBB localA = a.toAABB();
        OrientedBB localB = b.clone();
        localB.transform(toLocalTransform);
        
        return test(localA, localB);
    }

    /**
     * Tests whether two {@link AxisAlignedTSBP}s collide/intersect.
     *
     * @param a the first plane
     * @param b the second plane
     * @return whether the planes collide/intersect
     */
    public static boolean test(AxisAlignedTSBP a, AxisAlignedTSBP b) {
        return
            a.getMinX() <= b.getMaxX() &&
            a.getMaxX() >= b.getMinX() &&
            a.getMinY() <= b.getMaxY() &&
            a.getMaxY() >= b.getMinY() &&
            a.getMinZ() <= b.getMaxZ() &&
            a.getMaxZ() >= b.getMinZ();
    }

    /**
     * Tests whether two {@link Triangle3}s collide/intersect.
     *
     * @param a the first triangle
     * @param b the second triangle
     * @return whether the triangles collide/intersect
     */
    public static boolean test(Triangle3 a, Triangle3 b) {
        return Intersections.triangleTriangle(a, b) != null;
    }

    //HETERO - TESTS
    
    /**
     * Tests whether an {@link AxisAlignedBB} and a point ({@link Vector3}) collide.
     *
     * @param box the bounding box
     * @param point the point
     * @return whether the box and the point collide
     */
    public static boolean test(AxisAlignedBB box, Vector3 point) {
        return box.contains(point);
    }

    /**
     * Tests whether an {@link AxisAlignedBB} and an {@link AxisPlane} collide.
     *
     * @param box the bounding box
     * @param plane the plane
     * @return whether the box and the sphere collide
     */
    public static boolean test(AxisAlignedBB box, AxisPlane plane) {
        double d = plane.getDepth();
        switch (plane.getAxis()) {
            case X: return (box.getMinX() <= d) != (box.getMaxX() <= d);
            case Y: return (box.getMinY() <= d) != (box.getMaxY() <= d);
            case Z: return (box.getMinZ() <= d) != (box.getMaxZ() <= d);
            default: throw new IllegalArgumentException("plane has no axis");
        }
    }
    
    /**
     * Tests whether an {@link Rectangle} and a {@link Area} collide.
     *
     * @param box the rectangle
     * @param area the area
     * @return whether the box and the sphere collide
     */
    public static boolean test(Rectangle box, Area area) {
        Vector2 closestPoint = Vectors.clamp(area.getCenter(), box);
        return area.contains(closestPoint);
    }
    
    /**
     * Tests whether an {@link AxisAlignedBB} and a {@link Sphere} collide.
     *
     * @param box the bounding box
     * @param space the sphere
     * @return whether the box and the sphere collide
     */
    public static boolean test(AxisAlignedBB box, Sphere space) {
        Vector3 closestPoint = Vectors.clamp(space.getCenter(), box);
        return space.contains(closestPoint);
    }
    
    /**
     * Tests whether an {@link AxisAlignedBB} and a {@link OrientedBB} collide.
     *
     * @param aabb the axis aligned box
     * @param obb the oriented box
     * @return whether the boxes collide
     */
    public static boolean test(AxisAlignedBB aabb, OrientedBB obb) {
        Vector3 closestPoint = Vectors.clamp(obb.getCenter(), aabb);
        return obb.contains(closestPoint);
        
        /*
        //early and inexpensive cancel should the obb center be inside aabb
        if (aabb.contains(obbCen)) return true;
 
        Vector3 aabbCen = aabb.getCenter();
        Vector3 aabbSrf = Vector3.fromXYZ( // = point inside aabb closest to obb
            PrimMath.clamp(aabb.getMinX(), obbCen.getX(), aabb.getMaxX()),
            PrimMath.clamp(aabb.getMinY(), obbCen.getY(), aabb.getMaxY()),
            PrimMath.clamp(aabb.getMinZ(), obbCen.getZ(), aabb.getMaxZ()));
        
        //minimum distance between aabb center and obb center
        double t = Rays.cast(Ray3.between(aabbCen, aabbSrf), obb);
        
        //this works because t is a ray multiplier of aabbCen->aabbSrf
        //t is larger than 1 if the closest point on the obb is outside the aabb
        //if t is smaller, the closest point is inside the aabb and the boxes intersect
        return t <= 1 + Spatium.EPSILON;
        */
    }
    
    /**
     * Tests whether a {@link OrientedBB} and a {@link Sphere} collide.
     *
     * @param box the bounding box
     * @param sphere the sphere
     * @return whether the box and the sphere collide
     */
    public static boolean test(OrientedBB box, Sphere sphere) {
        //get the sphere center relative to the box center
        Vector3 relCenter = Vector3.between(box.getCenter(), sphere.getCenter());
        //turn that point from world space into object space
        relCenter.transform(box.getTransform());
        
        double dx = box.getSizeX()/2, dy = box.getSizeY()/2, dz = box.getSizeZ()/2;
        AxisAlignedBB aabb = AxisAlignedBB.fromPoints(-dx, -dy, -dz, dx, dy, dz);
        
        return test(aabb, Sphere.fromCenterRadius(relCenter, sphere.getRadius()));
    }
    
    /**
     * Tests whether a {@link Sphere} and a Point collide.
     *
     * @param sphere the sphere
     * @param point the point
     * @return whether the sphere and the point collide
     */
    public static boolean test(Sphere sphere, Vector3 point) {
        return sphere.contains(point);
    }

    /**
     * Tests whether a {@link Sphere} and a {@link Plane} collide.
     *
     * @param sphere the sphere
     * @param plane the plane
     * @return whether the sphere and the plane collide
     */
    public static boolean test(Sphere sphere, Plane plane) {
        Vector3 normal = plane.getNormal().negate();
        Ray3 sphereToPlane = Ray3.fromOD(sphere.getCenter(), normal);
        return sphereToPlane.getLengthSquared() <= sphere.getRadiusSquared();
    }

    /**
     * Tests whether a {@link Sphere} and an {@link AxisPlane} collide.
     *
     * @param sphere the sphere
     * @param plane the plane
     * @return whether the sphere and the plane collide
     */
    public static boolean test(Sphere sphere, AxisPlane plane) {
        switch (plane.getAxis()) {
            case X: return Math.abs(sphere.getX() - plane.getDepth()) <= sphere.getRadius();
            case Y: return Math.abs(sphere.getY() - plane.getDepth()) <= sphere.getRadius();
            case Z: return Math.abs(sphere.getZ() - plane.getDepth()) <= sphere.getRadius();
            default: throw new IllegalArgumentException("plane has no axis");
        }
    }
    
    /**
     * Tests whether a {@link AxisCylinder} and a point ({@link Vector3}) collide.
     *
     * @param cylinder the cylinder
     * @param point the point
     * @return whether the cylinder and the point collide
     */
    public static boolean test(AxisCylinder cylinder, Vector3 point) {
        return cylinder.contains(point);
    }
    
    /**
     * Tests whether a {@link Ray3} and a {@link Ray3} collide.
     *
     * @param a the first ray
     * @param b the second ray
     * @return whether the rays collide
     */
    public static boolean test(Ray3 a, Ray3 b) {
        return Double.isFinite(Rays.cast(a, b));
    }

    /**
     * Tests whether a {@link Ray3} and a Point ({@link Vector3}) collide.
     *
     * @param ray the ray
     * @param point the point
     * @return whether the box and the point collide
     */
    public static boolean test(Ray3 ray, Vector3 point) {
        return ray.contains(point);
    }

    /**
     * Tests whether a {@link Ray3} and a {@link Plane} collide.
     *
     * @param ray the ray
     * @param plane the plane
     * @return whether the ray and the plane collide
     */
    public static boolean test(Ray3 ray, Plane plane) {
        return Double.isFinite(Rays.cast(ray, plane));
    }

    /**
     * Tests whether a {@link Ray3} and a {@link Slab3} collide.
     *
     * @param ray the ray
     * @param slab the slab
     * @return whether the ray and the plane collide
     */
    public static boolean test(Ray3 ray, Slab3 slab) {
        /* Ray::pierce will return two finite numbers if there is an intersection due to the ray eventually hitting one
        of the two slab planes.
        However, a ray may also intersect a plane by being parallel to the slab and lying between the slab planes.
        In this case, the two returned values should be (+inf and -inf).
        Else the returned values are (+inf, +inf) or (-inf, -inf). */
        double[] entryExit = Rays.pierce(ray, slab);
        return
            Double.isFinite(entryExit[0]) ||
            entryExit[0] != entryExit[1];
    }

    /**
     * Tests whether a {@link Ray3} and a {@link AxisPlane} collide. This condition is fulfilled if either the
     * ray is not perpendicular to the plane or the ray lies within the plane.
     *
     * @param ray the ray
     * @param plane the plane
     * @return whether the ray and the plane collide
     */
    public static boolean test(Ray3 ray, AxisPlane plane) {
        switch (plane.getAxis()) {
            case X: return
                !(Spatium.isZero(ray.getDirY()) &&  Spatium.isZero(ray.getDirZ())) ||
                Spatium.equals(ray.getOrgX(), plane.getDepth());
            case Y: return
                !(Spatium.isZero(ray.getDirZ()) &&  Spatium.isZero(ray.getDirX())) ||
                Spatium.equals(ray.getOrgY(), plane.getDepth());
            case Z: return
                !(Spatium.isZero(ray.getDirX()) &&  Spatium.isZero(ray.getDirY())) ||
                Spatium.equals(ray.getOrgZ(), plane.getDepth());
            default: throw new IllegalArgumentException("plane has no axis");
        }
    }
    
    /**
     * Tests whether a {@link Ray2} and a {@link Circle} collide.
     *
     * @param ray the ray
     * @param circle the circle
     * @return whether the ray and the plane collide
     */
    @SuppressWarnings("Duplicates")
    public static boolean test(Ray2 ray, Circle circle) {
        Vector2
            origin = ray.getOrigin(),
            center = circle.getCenter();
        double radius = circle.getRadius();
        
        if (center.distanceTo(origin) <= radius) return true;
        
        Vector2 closestPoint = ray.getPoint( Projections.pointOnRay(ray, center) );
        
        return center.distanceTo(closestPoint) <= circle.getRadius();
    }

    /**
     * Tests whether a {@link Ray3} and a {@link Sphere} collide.
     *
     * @param ray the ray
     * @param sphere the sphere
     * @return whether the ray and the plane collide
     */
    public static boolean test(Ray3 ray, Sphere sphere) {
        Vector3
            origin = ray.getOrigin(),
            center = sphere.getCenter();
        double radius = sphere.getRadius();
    
        if (center.distanceTo(origin) <= radius) return true;
    
        Vector3 closestPoint = ray.getPoint( Projections.pointOnRay(ray, center) );
    
        return center.distanceTo(closestPoint) <= sphere.getRadius();
    }

    /**
     * Tests whether a {@link Ray3} and a {@link AxisAlignedBB} collide.
     *
     *
     * @param ray the ray
     * @param box the bounding box
     * @return whether the ray and the box collide
     */
    public static boolean test(Ray3 ray, AxisAlignedBB box) {
        return Double.isFinite(Rays.cast(ray, box));
    }
    
    /**
     * Tests whether a {@link Ray3} and a {@link AxisAlignedBB} collide.
     *
     *
     * @param ray the ray
     * @param box the bounding box
     * @return whether the ray and the box collide
     */
    public static boolean test(Ray3 ray, OrientedBB box) {
        return Double.isFinite(Rays.cast(ray, box));
    }

    /**
     * Tests whether a {@link Triangle3} and an {@link Plane} collide. This operation is performed by checking if not
     * all points of triangle are on the same side of the plane.
     *
     * @param triangle the triangle
     * @param plane the plane
     * @return whether the triangle and the plane collide
     */
    public static boolean test(Triangle3 triangle, Plane plane) {
        Vector3
            p = plane.getPoint(),
            n = plane.getNormal();
        return !(
            Vector3.between(triangle.getA(), p).dot(n) > 0 ==
            Vector3.between(triangle.getB(), p).dot(n) > 0 ==
            Vector3.between(triangle.getC(), p).dot(n) > 0 );
    }

    /**
     * Tests whether a {@link Triangle3} and an {@link AxisPlane} collide. This operation is performed by
     * checking if not all points of triangle are on the same side of the plane.
     *
     * @param triangle the triangle
     * @param plane the plane
     * @return whether the triangle and the plane collide
     */
    public static boolean test(Triangle3 triangle, AxisPlane plane) {
        switch (plane.getAxis()) {
            case X: return !(
                triangle.getA().getX() > plane.getDepth() ==
                triangle.getB().getX() > plane.getDepth() ==
                triangle.getC().getX() > plane.getDepth() );
            case Y: return !(
                triangle.getA().getY() > plane.getDepth() ==
                triangle.getB().getY() > plane.getDepth() ==
                triangle.getC().getY() > plane.getDepth() );
            case Z: return !(
                triangle.getA().getZ() > plane.getDepth() ==
                triangle.getB().getZ() > plane.getDepth() ==
                triangle.getC().getZ() > plane.getDepth() );
            default: throw new IllegalArgumentException("plane has no axis");
        }
    }

    /**
     * Tests whether an {@link AxisAlignedTSBP} and a Point collide.
     *
     * @param plane the plane
     * @param point the point
     * @return whether the box and the point collide
     */
    public static boolean test(AxisAlignedTSBP plane, Vector3 point) {
        return plane.contains(point);
    }

}