package net.grian.spatium.coll;

import net.grian.spatium.Spatium;
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
     * Tests whether two {@link Plane}s collide. This is equivalent to testing whether to planes are not parallel. This
     * means that two planes which are mathematically identical and collide with each other at infinitely many points
     * still do not collide.
     *
     * @param a the first plane
     * @param b the second plane
     * @return whether the points collide
     */
    public static boolean test(Plane a, Plane b) {
        double dot = Math.abs(a.getNormal().normalize().dot(b.getNormal().normalize()));
        return Spatium.equals(dot, 1);
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
     * Tests whether two {@link AxisAlignedBB3}s collide/intersect.
     *
     * @param a the first bounding box
     * @param b the second bounding box
     * @return whether the boxes collide/intersect
     */
    public static boolean test(AxisAlignedBB3 a, AxisAlignedBB3 b) {
        return
            a.getMinX() <= b.getMaxX() &&
            a.getMaxX() >= b.getMinX() &&
            a.getMinY() <= b.getMaxY() &&
            a.getMaxY() >= b.getMinY() &&
            a.getMinZ() <= b.getMaxZ() &&
            a.getMaxZ() >= b.getMinZ();
    }
    
    /**
     * Tests whether two {@link OrientedBB3}s collide/intersect.
     *
     * @param a the first bounding box
     * @param b the second bounding box
     * @return whether the boxes collide/intersect
     */
    public static boolean test(OrientedBB3 a, OrientedBB3 b) {
        //inverse transform matrix of a allows for conversion of second box to local space of first box
        //due to this, the first box can be seen as an AABB after transforming second box
        Matrix toLocalTransform = a.getTransform().getInverse();
        
        //more simple AABB & OBB intersection test
        return test(a.toAABB(), b.clone().transform(toLocalTransform));
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
        return Intersections.of(a, b) != null;
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

    //HETERO - TESTS
    
    /**
     * Tests whether an {@link AxisAlignedBB3} and a point ({@link Vector3}) collide.
     *
     * @param box the bounding box
     * @param point the point
     * @return whether the box and the point collide
     */
    public static boolean test(AxisAlignedBB3 box, Vector3 point) {
        return box.contains(point);
    }

    /**
     * Tests whether an {@link AxisAlignedBB3} and an {@link AxisPlane} collide.
     *
     * @param box the bounding box
     * @param plane the plane
     * @return whether the box and the sphere collide
     */
    public static boolean test(AxisAlignedBB3 box, AxisPlane plane) {
        double d = plane.getDepth();
        switch (plane.getAxis()) {
            case X: return (box.getMinX() <= d) != (box.getMaxX() <= d);
            case Y: return (box.getMinY() <= d) != (box.getMaxY() <= d);
            case Z: return (box.getMinZ() <= d) != (box.getMaxZ() <= d);
            default: throw new IllegalArgumentException("plane has no axis");
        }
    }
    
    /**
     * Tests whether an {@link AxisAlignedBB3} and a {@link Sphere} collide.
     *
     * @param box the bounding box
     * @param sphere the sphere
     * @return whether the box and the sphere collide
     */
    public static boolean test(AxisAlignedBB3 box, Sphere sphere) {
        Vector3 closestPoint = clamp(sphere.getCenter(), box);
        return sphere.contains(closestPoint);
    }
    
    /**
     * Tests whether an {@link AxisAlignedBB3} and a {@link OrientedBB3} collide.
     *
     * @param aabb the axis aligned box
     * @param obb the oriented box
     * @return whether the boxes collide
     */
    public static boolean test(AxisAlignedBB3 aabb, OrientedBB3 obb) {
        Vector3 closestPoint = clamp(obb.getCenter(), aabb);
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
    
    @Contract(value = "_, _, _, _ -> !null", pure = true)
    private static Vector3 clamp(double x, double y, double z, AxisAlignedBB3 bounds) {
        return Vector3.fromXYZ(
            PrimMath.clamp(bounds.getMinX(), x, bounds.getMaxX()),
            PrimMath.clamp(bounds.getMinY(), y, bounds.getMaxY()),
            PrimMath.clamp(bounds.getMinZ(), z, bounds.getMaxZ()));
    }
    
    @Contract(value = "_, _ -> !null", pure = true)
    private static Vector3 clamp(Vector3 point, AxisAlignedBB3 bounds) {
        return clamp(point.getX(), point.getY(), point.getZ(), bounds);
    }
    
    /**
     * Tests whether a {@link OrientedBB3} and a {@link Sphere} collide.
     *
     * @param box the bounding box
     * @param sphere the sphere
     * @return whether the box and the sphere collide
     */
    public static boolean test(OrientedBB3 box, Sphere sphere) {
        //get the sphere center relative to the box center
        Vector3 relCenter = sphere.getCenter().subtract(box.getCenter());
        //turn that point from world space into object space
        relCenter.transform(box.getTransform());
        
        double dx = box.getSizeX()/2, dy = box.getSizeY()/2, dz = box.getSizeZ()/2;
        AxisAlignedBB3 aabb = AxisAlignedBB3.fromPoints(-dx, -dy, -dz, dx, dy, dz);
        
        return test(aabb, Sphere.fromCenterAndRadius(relCenter, sphere.getRadius()));
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
        Ray3 sphereToPlane = Ray3.fromOD(sphere.getCenter(), plane.getNormal().multiply(-1));
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
                !(Spatium.equals(ray.getDirY(), 0) &&  Spatium.equals(ray.getDirZ(), 0)) ||
                Spatium.equals(ray.getOriginX(), plane.getDepth());
            case Y: return
                !(Spatium.equals(ray.getDirZ(), 0) &&  Spatium.equals(ray.getDirX(), 0)) ||
                Spatium.equals(ray.getOriginY(), plane.getDepth());
            case Z: return
                !(Spatium.equals(ray.getDirX(), 0) &&  Spatium.equals(ray.getDirY(), 0)) ||
                Spatium.equals(ray.getOriginZ(), plane.getDepth());
            default: throw new IllegalArgumentException("plane has no axis");
        }
    }

    /**
     * Tests whether a {@link Ray3} and a {@link Sphere} collide.
     *
     * @param ray the ray
     * @param sphere the sphere
     * @return whether the ray and the plane collide
     */
    public static boolean test(Ray3 ray, Sphere sphere) {
        return Double.isFinite(Rays.cast(ray, sphere));
    }

    /**
     * Tests whether a {@link Ray3} and a {@link AxisAlignedBB3} collide.
     *
     *
     * @param ray the ray
     * @param box the bounding box
     * @return whether the ray and the box collide
     */
    public static boolean test(Ray3 ray, AxisAlignedBB3 box) {
        return Double.isFinite(Rays.cast(ray, box));
    }
    
    /**
     * Tests whether a {@link Ray3} and a {@link AxisAlignedBB3} collide.
     *
     *
     * @param ray the ray
     * @param box the bounding box
     * @return whether the ray and the box collide
     */
    public static boolean test(Ray3 ray, OrientedBB3 box) {
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