package net.grian.spatium.coll;

import eisenwave.spatium.util.Spatium;
import net.grian.spatium.geo2.*;
import net.grian.spatium.geo3.*;
import net.grian.spatium.matrix.Matrix;

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
        return !a.getNormal().isMultipleOf(b.getNormal());
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
     * Tests whether two {@link Rectangle}s collide/intersect.
     *
     * @param a the first rectangle
     * @param b the second rectangle
     * @return whether the boxes collide/intersect
     */
    public static boolean test(Rectangle a, Rectangle b) {
        return
            a.getMinX() <= b.getMaxX() &&
            a.getMaxX() >= b.getMinX() &&
            a.getMinY() <= b.getMaxY() &&
            a.getMaxY() >= b.getMinY();
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
     * Tests whether two {@link Circle}s collide/intersect.
     *
     * @param a the first sphere
     * @param b the second sphere
     * @return whether the spheres collide/intersect.
     */
    public static boolean test(Circle a, Circle b) {
        final double
            dx = b.getX() - a.getX(),
            dy = b.getY() - a.getY(),
            r = a.getRadius() + b.getRadius();
        
        return dx*dx + dy*dy <= r*r;
    }
    
    /**
     * Tests whether two {@link Sphere}s collide/intersect.
     *
     * @param a the first sphere
     * @param b the second sphere
     * @return whether the spheres collide/intersect.
     */
    public static boolean test(Sphere a, Sphere b) {
        final double
            dx = b.getX() - a.getX(),
            dy = b.getY() - a.getY(),
            dz = b.getZ() - a.getZ(),
            r = a.getRadius() + b.getRadius();
        
        return dx*dx + dy*dy + dz*dz <= r*r;
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
     * Tests whether a {@link Ray3} and a {@link Plane} collide.
     *
     * @param ray the ray
     * @param plane the plane
     * @return whether the ray and the plane collide
     */
    public static boolean test(Ray3 ray, Plane plane) {
        return !ray.getDirection().isOrthogonalTo(plane.getNormal());
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
                !Spatium.isZero(ray.getDirY()) ||
                !Spatium.isZero(ray.getDirZ()) ||
                Spatium.equals(ray.getOrgX(), plane.getDepth());
            case Y: return
                !Spatium.isZero(ray.getDirZ()) ||
                !Spatium.isZero(ray.getDirX()) ||
                Spatium.equals(ray.getOrgY(), plane.getDepth());
            case Z: return
                !Spatium.isZero(ray.getDirX()) ||
                !Spatium.isZero(ray.getDirY()) ||
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
    public static boolean test(Ray2 ray, Circle circle) {
        return
            // either the circle contains origin of ray, or ...
            circle.contains( ray.getOrigin() ) ||
            // the circle contains closest point on the ray to the circle center
            circle.contains( ray.getPoint(Projections.pointOnRay(ray, circle.getCenter())) );
    }

    /**
     * Tests whether a {@link Ray3} and a {@link Sphere} collide.
     *
     * @param ray the ray
     * @param sphere the sphere
     * @return whether the ray and the plane collide
     */
    public static boolean test(Ray3 ray, Sphere sphere) {
        return
            // either the sphere contains origin of ray, or ...
            sphere.contains( ray.getOrigin() ) ||
            // the sphere contains closest point on the ray to the sphere center
            sphere.contains( ray.getPoint(Projections.pointOnRay(ray, sphere.getCenter())) );
    }
    
    /**
     * Tests whether a {@link Ray2} and a {@link Polygon2} collide.
     *
     * @param ray the ray
     * @param polygon the polygon
     * @return whether the ray and the plane collide
     */
    public static boolean test(Ray2 ray, Polygon2 polygon) {
        // basically check if all points of the polygon are on the same side of the ray
        Vector2
            //ray origin
            origin = ray.getOrigin(),
            //vector ortho. to ray direction ("normal" of the "2d plane" so to speak)
            normal = ray.getDirection().getInverse();
        Vector2[] vertices = polygon.getVertices();
        
        boolean positive = normal.dot(Vector2.between(origin, vertices[0])) >= 0;
        for (int i = 1; i < vertices.length; i++) {
            //if vertex is not on the same side as the first vertex, return true
            if (normal.dot(Vector2.between(origin, vertices[0])) >= 0 != positive)
                return true;
        }
        return false;
    }
    
    /**
     * Tests whether two rays collide. This is equivalent to testing whether the two points are in the same position.
     *
     * @param ray the ray
     * @param box the rectangle
     * @return whether the rays collide
     */
    public static boolean test(Ray2 ray, Rectangle box) {
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
    public static boolean test(Ray3 ray, AxisAlignedBB box) {
        return Double.isFinite(Rays.cast(ray, box));
    }
    
    /**
     * Tests whether a {@link Ray3} and an {@link AxisAlignedBB} collide.
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
     * Tests whether a {@link Ray3} and a {@link Triangle3} collide.
     *
     *
     * @param ray the ray
     * @param triangle the triangle
     * @return whether the ray and the box collide
     */
    public static boolean test(Ray3 ray, Triangle3 triangle) {
        return Double.isFinite(Rays.cast(ray, triangle));
    }
    
    /**
     * Tests whether a {@link Ray3} and a {@link Tetrahedron} collide.
     *
     *
     * @param ray the ray
     * @param tetra the tetrahedron
     * @return whether the ray and the box collide
     */
    public static boolean test(Ray3 ray, Tetrahedron tetra) {
        return Double.isFinite(Rays.cast(ray, tetra));
    }
    
    /**
     * Tests whether two {@link Polygon2}s collide/intersect.
     *
     * @param a the first triangle
     * @param b the second triangle
     * @return whether the triangles collide/intersect
     */
    public static boolean test(Polygon2 a, Polygon2 b) {
        //early and inexpensive cancel if the polygon bounds don't intersect
        if ( !test(a.getBoundaries(), b.getBoundaries()) )
            return false;
        
        final Vector2[]
            va = a.getVertices(),
            vb = b.getVertices();
        
        for (int i = 0; i < va.length; i++) {
            Ray2 edge = a.getEdge(i);
            
            double[]
                ta = Projections.polygonOnRay(edge, a), //proj[min,max] of a onto edge
                tb = Projections.polygonOnRay(edge, b); //proj[min,max] of b onto edge
            if (ta[0] > tb[1] || ta[1] < tb[0]) //if projections don't overlap, abort
                return false;
        }
        
        return true;
    }
    
    /**
     * Tests whether a {@link Polygon2} and a point ({@link Vector2}) collide.
     *
     * @param polygon the polygon
     * @param point the point
     * @return whether the polygon and the point collide
     */
    public static boolean test(Polygon2 polygon, Vector2 point) {
        Rectangle bounds = polygon.getBoundaries();
        if (!bounds.contains(point))
            return false;
        
        //find a ray outside the polygon that points towards the point
        Ray2 ray = Ray2.fromOD(bounds.getMinX(), point.getY(), 1, 0);
        Vector2 origin = ray.getOrigin();
        double distSqr = point.distanceTo(origin);
        
        int count = 0;
        final int lim = polygon.getVertexCount();
        
        for (int i = 0; i < lim; i++) {
            Ray2 edge = polygon.getEdge(i);
            double t = Rays.cast(edge, ray);
            if (
                //ray intersects edge somewhere
                Double.isFinite(t) &&
                //intersection is inside edge range (0 to 1)
                t >= 0 || t <= 1 &&
                //intersection is actually inbetween the origin and point
                edge.getPoint(t).subtract(origin).getLengthSquared() <= distSqr)
                count++;
        }
        
        //uneven intersection count -> point inside polygon
        return count%2 == 1;
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
        final boolean first = Vector3.between(triangle.getA(), p).dot(n) > 0;
        return
            first != Vector3.between(triangle.getA(), p).dot(n) > 0 ||
            first != Vector3.between(triangle.getC(), p).dot(n) > 0;
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
        boolean first;
        switch (plane.getAxis()) {
            case X:
                first = triangle.getA().getX() > plane.getDepth();
                return
                    first != triangle.getB().getX() > plane.getDepth() ||
                    first != triangle.getC().getX() > plane.getDepth();
            case Y:
                first = triangle.getA().getY() > plane.getDepth();
                return
                    first != triangle.getB().getY() > plane.getDepth() ||
                    first != triangle.getC().getY() > plane.getDepth();
            case Z:
                first = triangle.getA().getZ() > plane.getDepth();
                return
                    first != triangle.getB().getZ() > plane.getDepth() ||
                    triangle.getC().getZ() > plane.getDepth();
            default: throw new IllegalArgumentException("plane has no axis");
        }
    }
    
}
