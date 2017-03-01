package net.grian.spatium.coll;

import net.grian.spatium.Spatium;
import net.grian.spatium.cache.CacheMath;
import net.grian.spatium.geo2.*;
import net.grian.spatium.geo3.*;
import net.grian.spatium.util.PrimMath;
import org.jetbrains.annotations.*;

/**
 * <p>
 *     Utility class for ray casting / ray-object collision detection.
 * </p>
 * <p>
 *     None of these methods performs any kind of mutation upon the parameters, all returned objects are freshly
 *     constructed objects.
 * </p>
 */
public final class Rays {

    private Rays() {}
    
    /**
     * <p>
     *     Tests where two {@link Ray2}s collide. This will always yield a real result unless the directional vectors
     *     of the rays are parallel.
     * </p>
     * <p>
     *     This algorithm may also be used to check where line segments intersect:
     * </p>
     * <p>
     *     Let X be the line segment between points A, B and let Y be the line segment between C, D. Let R<sub>x</sub>
     *     be the ray representing X and let R<sub>y</sub> be the ray representing Y.
     *     <blockquote>
     *         <code>R<sub>x</sub> = (A, B-A)</code>
     *         <br><code>R<sub>y</sub> = (C, D-C)</code>
     *     </blockquote>
     *     The point of intersection may be obtained by invoking
     *     <code>R<sub>x</sub>.getPoint( cast(R<sub>x</sub>, R<sub>y</sub>) )</code>
     * </p>
     * <p>
     *     The returned value is a multiplier for the directional vector of the ray at which the ray and the other
     *     object collide with each other.
     * </p>
     * <p>
     *     The point of the collision can be obtained by setting the hypot of the first ray to a multiplier using
     *     {@link Ray2#setLength(double)} (mutation) or {@link Ray2#getPoint(double)} (no mutation).
     * </p>
     *
     * @param a the first ray
     * @param b the second ray
     * @return the ray multiplier or {@link Double#NaN}
     */
    public static double cast(Ray2 a, Ray2 b) {
        //c := ( -dir(a).y, dir(a).x ) = "inverse of a's direction"
        //d := org(a) - org(b) = "orgB to orgA"
        //t := (d * c) / (dir(b) * c)
        
        Vector2 inverse = a.getDirection().getInverse();
        Vector2 orgB_orgA = Vector2.between(b.getOrigin(), a.getOrigin());
        
        return orgB_orgA.dot(inverse) / b.getDirection().dot(inverse);
    }

    /**
     * Tests where two {@link Ray3}s collide.
     * <p>
     *     The returned value is a multiplier for the directional vector of the ray at which the ray and the other
     *     object collide with each other.
     * </p>
     * <p>
     *     The point of the collision can be obtained by setting the hypot of the first ray to a multiplier using
     *     {@link Ray3#setLength(double)} (mutation) or {@link Ray3#getPoint(double)} (no mutation).
     * </p>
     *
     * @param a the first ray
     * @param b the second ray
     * @return the ray multiplier or {@link Double#NaN}
     */
    public static double cast(Ray3 a, Ray3 b) {
        Vector3
                dirA = a.getDirection(),
                dirB = b.getDirection(),
                dirC = Vector3.between(a.getOrigin(), b.getOrigin()),
                crossAB = dirA.cross(dirB),
                crossCB = dirC.cross(dirB);

        double
                absPlanarFactor = Math.abs(dirC.dot(crossAB)),
                sqrLength = crossAB.getLengthSquared();

        //is coplanar, and not parallel
        if (absPlanarFactor < Spatium.EPSILON && sqrLength > Spatium.EPSILON) {
            return crossCB.dot(crossAB) / sqrLength;
        }
        else {
            return Double.NaN;
        }
    }

    /**
     * Tests where a {@link Ray3} and a Point ({@link Vector3}) collide.
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
     * @return where the ray and the point collide or {@link Double#NaN}
     */
    public static double cast(Ray3 ray, Vector3 point) {
        return ray.containsAt(point);
    }
    
    /**
     * Tests where a {@link Ray2} and a {@link Circle} collide.
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
     * @param circle the circle
     * @return where the ray and the point collide or {@link Double#NaN}
     */
    public static double cast(Ray2 ray, Circle circle) {
        double[] entryExit = pierce(ray, circle);
        return entryExit==null? Double.NaN : entryExit[0];
    }
    
    /**
     * Tests where a {@link Ray3} and a {@link Sphere} collide.
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
     * @param sphere the sphere
     * @return where the ray and the point collide or {@link Double#NaN}
     */
    public static double cast(Ray3 ray, Sphere sphere) {
        double[] entryExit = pierce(ray, sphere);
        return entryExit==null? Double.NaN : entryExit[0];
    }

    /**
     * <p>
     *     Tests where a {@link Ray3} and a {@link Plane} collide.
     * </p>
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
     * @param plane the plane
     * @return where the ray and the plane collide or {@link Double#NaN}
     */
    public static double cast(Ray3 ray, Plane plane) {
        Vector3 normal = plane.getNormal().normalize();
        Vector3 dir = ray.getDirection().normalize();
        
        double denominator = normal.dot(dir);
        if (Spatium.isZero(denominator)) //ray and plane are parallel
            return Double.NaN;

        //calculate the distance between the linePoint and the line-plane intersection point
        double numerator = normal.dot( Vector3.between(ray.getOrigin(), plane.getPoint()) );

        return numerator / denominator;
    }

    /**
     * <p>
     *     Tests where a {@link Ray3} and an {@link AxisPlane} collide.
     * </p>
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
     * @param plane the plane
     * @return where the ray and the plane collide or {@link Double#NaN}
     */
    public static double cast(Ray3 ray, AxisPlane plane) {
        switch (plane.getAxis()) {
            case X: return (ray.getOrgX() - plane.getDepth()) / ray.getDirX();
            case Y: return (ray.getOrgY() - plane.getDepth()) / ray.getDirY();
            case Z: return (ray.getOrgZ() - plane.getDepth()) / ray.getDirZ();
            default: throw new IllegalArgumentException("plane has no axis");
        }
    }

    /**
     * <p>
     *     Tests where a {@link Ray3} and a {@link Slab3} collide.
     * </p>
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
     * @param slab the slab
     * @return where the ray and the plane collide or {@link Double#NaN}
     */
    public static double cast(Ray3 ray, Slab3 slab) {
        return pierce(ray, slab)[0];
    }

    /**
     * <p>
     *     Tests where a {@link Ray3} and an {@link AxisPlane} collide.
     * </p>
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
     * @param box the bounding box
     * @return where the ray and the box collide or {@link Double#NaN}
     */
    public static double cast(Ray3 ray, AxisAlignedBB box) {
        double[] entryExit = pierce(ray, box);
        return entryExit==null? Double.NaN : entryExit[0];
    }

    /**
     * <p>
     *     Tests where a {@link Ray3} and an {@link OrientedBB} collide.
     * </p>
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
     * @param box the bounding box
     * @return where the ray and the box collide or {@link Double#NaN}
     */
    public static double cast(Ray3 ray, OrientedBB box) {
        double[] entryExit = pierce(ray, box);
        return entryExit==null? Double.NaN : entryExit[0];
    }
    
    /**
     * <p>
     *     Tests where a {@link Ray2} and a {@link Triangle2} collide.
     * </p>
     * <p>
     *     The returned value is a multiplier for the directional vector of the ray at which the ray and the other
     *     object collide with each other.
     * </p>
     * <p>
     *     The point of the collision can be obtained by setting the hypot of the first ray to a multiplier using
     *     {@link Ray2#setLength(double)} (mutation) or {@link Ray2#getPoint(double)} (no mutation).
     * </p>
     * Source:<a href="https://en.wikipedia.org/wiki/M%C3%B6ller%E2%80%93Trumbore_intersection_algorithm">
     * Möller–Trumbore intersection algorithm</a>
     *
     * @param ray the ray
     * @param triangle the triangle
     * @return where the box and the point collide or {@link Double#NaN}
     */
    public static double cast(Ray2 ray, Triangle2 triangle) {
        Vector2
            a = triangle.getA(),
            b = triangle.getB(),
            c = triangle.getC();
        
        double
            t1 = cast(ray, Ray2.between(a, b)),
            t2 = cast(ray, Ray2.between(a, c)),
            t3 = cast(ray, Ray2.between(b, c));
        
        return realMin(t1, realMin(t2, t3));
    }
    
    private static double realMin(double a, double b) {
        boolean finA = Double.isFinite(a), finB = Double.isFinite(b);
        
        if (finA && finB) return a < b? a : b;
        else if (finA) return a;
        else return b;
    }

    /**
     * <p>
     *     Tests where a {@link Ray3} and a {@link Triangle3} collide.
     * </p>
     * <p>
     *     The returned value is a multiplier for the directional vector of the ray at which the ray and the other
     *     object collide with each other.
     * </p>
     * <p>
     *     The point of the collision can be obtained by setting the hypot of the first ray to a multiplier using
     *     {@link Ray3#setLength(double)} (mutation) or {@link Ray3#getPoint(double)} (no mutation).
     * </p>
     * Source:<a href="https://en.wikipedia.org/wiki/M%C3%B6ller%E2%80%93Trumbore_intersection_algorithm">
     * Möller–Trumbore intersection algorithm</a>
     *
     * @param ray the ray
     * @param triangle the triangle
     * @return where the box and the point collide or {@link Double#NaN}
     */
    public static double cast(Ray3 ray, Triangle3 triangle) {
        Vector3
                origin = ray.getOrigin(),
                dir = ray.getDirection(),
                a = triangle.getA(),
                ab = Vector3.between(a, triangle.getB()),
                ac = Vector3.between(a, triangle.getC()),
                normal1 = dir.cross(ab);

        double det = ab.dot(normal1);
        //ray is parallel to triangle
        if (Spatium.isZero(det)) return Double.NaN;
        double invDet = 1 / det;

        Vector3 ao = Vector3.between(a, origin);
        double u = origin.dot(normal1) * invDet;
        //intersection lies outside the triangle
        if (u < 0 || u > 1) return Double.NaN;

        Vector3 normal2 = ao.cross(ab);
        double v = dir.dot(normal2) * invDet;
        //intersection lies outside the triangle
        if (v < 0 || v > 1) return Double.NaN;

        double t = ac.dot(normal2) * invDet;

        return !Spatium.isZero(t)? t : Double.NaN;
    }

    //ENTRY AND EXIT RAY CASTS
    
    /**
     * <p>
     *     Tests where a {@link Ray2} enters and exits a {@link Circle} collide. Unlike most algorithms, this one
     *     neither cancels early when the ray is inside the circle, nor when the sphere is behind the ray origin.
     * </p>
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
     * @param circle the circle
     * @return where the ray and the point collide or {@link Double#NaN}
     */
    @Nullable
    public static double[] pierce(Ray2 ray, Circle circle) {
        Vector2 center = circle.getCenter();
    
        //the ray multiplier
        double tm = Projections.pointOnRay(ray, center);
        //closest point on the line to the circle center
        Vector2 base = ray.getPoint(tm);
    
        double r = circle.getRadius();
        double dis = base.distanceTo(center);
        //closest point is outside radius
        if (dis > r) return null;
    
        double l = ray.getLength();
        //the +- offset of the ray
        //0 if ray pierces the surface of sphere, 1 if ray passes through center
        double dt = Math.cos(dis / r * CacheMath.HALF_PI) * r / l;
    
        return tm >= 0?
            new double[] {tm - dt, tm + dt} :
            new double[] {tm + dt, tm - dt};
    }

    /**
     * <p>
     *     Tests where a {@link Ray3} enters and exits a {@link Sphere} collide. Unlike most algorithms, this one
     *     neither cancels early when the ray is inside the sphere, nor when the sphere is behind the ray origin.
     * </p>
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
     * @param sphere the sphere
     * @return where the ray and the point collide or {@link Double#NaN}
     */
    @Nullable
    public static double[] pierce(Ray3 ray, Sphere sphere) {
        Vector3 center = sphere.getCenter();

        //the ray multiplier
        double tm = Projections.pointOnRay(ray, center);
        Vector3 base = ray.getPoint(tm);

        double r = sphere.getRadius();
        //closest point on the line to the circle center
        double d = base.distanceTo(center);
        //closest point is outside radius
        if (d > r) return null;

        double l = ray.getLength();
        //the +- offset of the ray
        //0 if ray pierces the surface of sphere, 1 if ray passes through center
        double dt = Math.cos(d / r * CacheMath.HALF_PI) * r / l;

        return tm >= 0?
                new double[] {tm - dt, tm + dt} :
                new double[] {tm + dt, tm - dt};
    }

    /**
     * <p>
     *     Tests where a {@link Ray3} enters and exits a {@link Slab3}.
     * </p>
     * <p>
     *     The returned values are multipliers for the directional vector of the ray at which the ray and the other
     *     object collide with each other.
     * </p>
     * <p>
     *     The point of the collision can be obtained by setting the hypot of the first ray to a multiplier using
     *     {@link Ray3#setLength(double)}.
     * </p>
     * <p>
     *     Special cases:
     *     <ul>
     *         <li>If the ray and the slab are parallel, the array will contain infinite values. In this case the
     *         ray origin is inside the slab, should the double be equal to <code>[-infinity, infinity]</code></li>
     *         <li>should it be outside negative direction, <code>[infinity, infinity]</code></li>
     *         <li>should the origin be outside in positive direction <code>[-infinity, -infinity]</code></li>
     *     </ul>
     * </p>
     *
     * @param ray the ray
     * @param slab the slab
     * @return the entry and exit points of the ray
     */
    @NotNull
    public static double[] pierce(Ray3 ray, Slab3 slab) {
        Vector3 normal = slab.getNormal().normalize();
        
        double denominator;
        {
            Vector3 dir = ray.getDirection().normalize();
            denominator = normal.dot(dir);
        }
        
        Vector3 min, max;
        {
            Vector3 origin = ray.getOrigin();
            min = slab.getMinPoint();
            max = slab.getMaxPoint();
            min.subtract(origin);
            max.subtract(origin);
        }
        
        double
            tmin = normal.dot(min) / denominator,
            tmax = normal.dot(max) / denominator;
        
        return denominator >= 0? new double[] {tmin, tmax} : new double[] {tmax, tmin};
    }

    /**
     * <p>
     *     Tests where a {@link Ray3} enters and exits an {@link AxisSlab3}.
     * </p>
     * <p>
     *     The returned values are multipliers for the directional vector of the ray at which the ray and the other
     *     object collide with each other.
     * </p>
     * <p>
     *     The point of the collision can be obtained by setting the hypot of the first ray to a multiplier using
     *     {@link Ray3#setLength(double)}.
     * </p>
     * <p>
     *     Special cases:
     *     <ul>
     *         <li>If the ray and the slab are parallel, the array will contain infinite values. In this case the
     *         ray origin is inside the slab, should the double be equal to <code>[-infinity, infinity]</code></li>
     *         <li>should it be outside negative direction, <code>[infinity, infinity]</code></li>
     *         <li>should the origin be outside in positive direction <code>[-infinity, -infinity]</code></li>
     *     </ul>
     * </p>
     *
     * @param ray the ray
     * @param slab the slab
     * @return the entry and exit points of the ray or null
     */
    @NotNull
    public static double[] pierce(Ray3 ray, AxisSlab3 slab) {
        double d;
        switch (slab.getAxis()) {
            case X: d = 1 / ray.getDirX(); break;
            case Y: d = 1 / ray.getDirY(); break;
            case Z: d = 1 / ray.getDirZ(); break;
            default: throw new IllegalStateException("slab has no axis");
        }

        if (d >= 0) return new double[] {
                (slab.getMinDepth() - ray.getOrgX()) / d,
                (slab.getMaxDepth() - ray.getOrgX()) / d
        };
        else return new double[]{
                (slab.getMaxDepth() - ray.getOrgX()) / d,
                (slab.getMinDepth() - ray.getOrgX()) / d
        };
    }
    
    /**
     * <p>
     *     Tests where a {@link Ray2} enters and exits an {@link Rectangle}.
     * </p>
     * <p>
     *     The returned values are multipliers for the directional vector of the ray at which the ray and the other
     *     object collide with each other.
     * </p>
     * <p>
     *     The point of the collision can be obtained by setting the hypot of the first ray to a multiplier using
     *     {@link Ray2#setLength(double)}.
     * </p>
     *
     * @param ray the ray
     * @param box the bounding box
     * @return the entry and exit points of the ray or null
     */
    @Nullable
    public static double[] pierce(Ray2 ray, Rectangle box) {
        
        //x-slab collision
        final double
            divX = 1 / ray.getDirX(),
            txmin = ((divX >= 0? box.getMinX() : box.getMaxX()) - ray.getOrgX()) * divX,
            txmax = ((divX >= 0? box.getMaxX() : box.getMinX()) - ray.getOrgX()) * divX;
            
        //y-slab collision
        final double
            divY = 1 / ray.getDirY(),
            tymin = ((divY >= 0? box.getMinY() : box.getMaxY()) - ray.getOrgY()) * divY,
            tymax = ((divY >= 0? box.getMaxY() : box.getMinY()) - ray.getOrgY()) * divY;
    
        if (txmin > tymax || tymin > txmax) return null;
        
        return new double[] {
            Math.max(txmin, tymin),
            Math.min(txmax, tymax)};
    }

    /**
     * <p>
     *     Tests where a {@link Ray3} enters and exits an {@link AxisAlignedBB}.
     * </p>
     * <p>
     *     The returned values are multipliers for the directional vector of the ray at which the ray and the other
     *     object collide with each other.
     * </p>
     * <p>
     *     The point of the collision can be obtained by setting the hypot of the first ray to a multiplier using
     *     {@link Ray3#setLength(double)}.
     * </p>
     *
     * @param ray the ray
     * @param box the bounding box
     * @return the entry and exit points of the ray or null
     */
    @Nullable
    public static double[] pierce(Ray3 ray, AxisAlignedBB box) {

        double tmin, tmax;

        {//x-slab collision
            final double div = 1 / ray.getDirX();
            tmin = ((div >= 0? box.getMinX() : box.getMaxX()) - ray.getOrgX()) * div;
            tmax = ((div >= 0? box.getMaxX() : box.getMinX()) - ray.getOrgX()) * div;
        }
        {//y-slab collision
            final double div = 1 / ray.getDirY();
            double tymin = ((div >= 0? box.getMinY() : box.getMaxY()) - ray.getOrgY()) * div;
            double tymax = ((div >= 0? box.getMaxY() : box.getMinY()) - ray.getOrgY()) * div;
            
            if (tmin > tymax || tymin > tmax) return null;
            if (tymin > tmin) tmin = tymin;
            if (tymax < tmax) tmax = tymax;
        }
        {//z-slab collision
            final double div = 1 / ray.getDirZ();
            double tzmin = ((div >= 0? box.getMinZ() : box.getMaxZ()) - ray.getOrgZ()) * div;
            double tzmax = ((div >= 0? box.getMaxZ() : box.getMinZ()) - ray.getOrgZ()) * div;
            
            if (tmin > tzmax || tzmin > tmax) return null;
            if (tzmin > tmin) tmin = tzmin;
            if (tzmax < tmax) tmax = tzmax;
        }
        
        return new double[] {tmin, tmax};
    }

    /**
     * <p>
     *     Tests where a {@link Ray3} enters and exits an {@link OrientedBB}.
     * </p>
     * <p>
     *     The returned values are multipliers for the directional vector of the ray at which the ray and the other
     *     object collide with each other.
     * </p>
     * <p>
     *     The point of the collision can be obtained by setting the hypot of the first ray to a multiplier using
     *     {@link Ray3#setLength(double)} (mutation) or {@link Ray3#getPoint(double)} (no mutation).
     * </p>
     *
     * @param ray the ray
     * @param box the bounding box
     * @return the entry and exit points of the ray or null
     */
    @Nullable
    public static double[] pierce(Ray3 ray, OrientedBB box) {
        double tmin, tmax;
        {
            //x bounds
            double[] tx = pierce(ray, box.getSlabX());

            tmin = tx[0];
            tmax = tx[1];
        }
        {
            //y bounds
            double[] ty = pierce(ray, box.getSlabY());

            if (tmin > ty[1] || ty[0] > tmax) return null;
            if (ty[0] > tmin) tmin = ty[0];
            if (ty[1] < tmax) tmax = ty[1];
        }
        {
            //z bounds
            double[] tz = pierce(ray, box.getSlabZ());

            if (tmin > tz[1] || tz[0] > tmax) return null;
            if (tz[0] > tmin) tmin = tz[0];
            if (tz[1] < tmax) tmax = tz[1];
        }

        return new double[] {tmin, tmax};
    }
    
    /**
     * <p>
     *     Tests where a {@link Ray3} enters and exits an {@link AxisCylinder}.
     * </p>
     * <p>
     *     The returned values are multipliers for the directional vector of the ray at which the ray and the other
     *     object collide with each other.
     * </p>
     * <p>
     *     The point of the collision can be obtained by setting the hypot of the first ray to a multiplier using
     *     {@link Ray3#setLength(double)}.
     * </p>
     *
     * @param ray the ray
     * @param cylinder the cylinder
     * @return the entry and exit points of the ray or null
     */
    @Nullable
    public static double[] pierce(Ray3 ray, AxisCylinder cylinder) {
        //min and max collision points of ray with the planes of the cylinder disks:
        //if the ray is parallel to the cylinder slab AND the origin is not inside it, we can cancel early
        double[] t = pierce(ray, cylinder.getSlab());
        
        //if the ray is parallel to the slab, tmin and tmax will be infinite
        //however, should the ray origin lie in between the cylinder planes, tmin will be neg.inf. and tmax will be
        //pos.inf (or the other way around)
        //otherwise, both tmin and tmax will be pos.inf or neg.inf and we can cancel right away
        if (!Double.isFinite(t[0]) && t[0] == t[1])
            return null;
        
        //now we have to check where the 2D ray collides with the cylinder circle
        return null;
    }

    /**
     * Tests where a moving {@link AxisAlignedBB} enters and exits a static {@link AxisAlignedBB}.
     *
     * @param a the first, moving box
     * @param motion the motion of the first bounding box
     * @param b the second, static box
     * @return the collision
     */
    @Nullable
    public static double[] pierce(AxisAlignedBB a, Vector3 motion, AxisAlignedBB b) {
        //minkowski sum of a rotated 180 degrees around origin (a') and b
        AxisAlignedBB minkowski = AxisAlignedBB.fromPoints(
                b.getMinX()-a.getMaxX(),
                b.getMinY()-a.getMaxY(),
                b.getMinZ()-a.getMaxZ(),
                b.getMaxX()-a.getMinX(),
                b.getMaxY()-a.getMinY(),
                b.getMaxZ()-a.getMinZ());

        Ray3 ray = Ray3.fromOD(0, 0, 0, motion.getX(), motion.getY(), motion.getZ());
        
        return pierce(ray, minkowski);
    }

}
