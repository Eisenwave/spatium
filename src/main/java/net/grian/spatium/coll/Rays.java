package net.grian.spatium.coll;

import net.grian.spatium.Spatium;
import net.grian.spatium.cache.CacheMath;
import net.grian.spatium.geo.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
     * Tests where two {@link Ray}s collide.
     * <p>
     *     The returned value is a multiplier for the directional vector of the ray at which the ray and the other
     *     object collide with each other.
     * </p>
     * <p>
     *     The point of the collision can be obtained by setting the hypot of the first ray to a multiplier using
     *     {@link Ray#setLength(double)} (mutation) or {@link Ray#getPoint(double)} (no mutation).
     * </p>
     *
     * @param a the first ray
     * @param b the second ray
     * @return the ray multiplier or {@link Double#NaN}
     */
    public static double cast(Ray a, Ray b) {
        Vector
                dirA = a.getDirection(),
                dirB = b.getDirection(),
                dirC = b.getOrigin().subtract(a.getOrigin()),
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
     * Tests where a {@link Ray} and a Point ({@link Vector}) collide.
     * <p>
     *     The returned value is a multiplier for the directional vector of the ray at which the ray and the other
     *     object collide with each other.
     * </p>
     * <p>
     *     The point of the collision can be obtained by setting the hypot of the first ray to a multiplier using
     *     {@link Ray#setLength(double)} (mutation) or {@link Ray#getPoint(double)} (no mutation).
     * </p>
     *
     * @param ray the ray
     * @param point the point
     * @return where the ray and the point collide or {@link Double#NaN}
     */
    public static double cast(Ray ray, Vector point) {
        return ray.containsAt(point);
    }

    /**
     * Tests where a {@link Ray} and a Point ({@link Sphere}) collide.
     * <p>
     *     The returned value is a multiplier for the directional vector of the ray at which the ray and the other
     *     object collide with each other.
     * </p>
     * <p>
     *     The point of the collision can be obtained by setting the hypot of the first ray to a multiplier using
     *     {@link Ray#setLength(double)} (mutation) or {@link Ray#getPoint(double)} (no mutation).
     * </p>
     *
     * @param ray the ray
     * @param sphere the sphere
     * @return where the ray and the point collide or {@link Double#NaN}
     */
    public static double cast(Ray ray, Sphere sphere) {
        double[] entryExit = pierce(ray, sphere);
        return entryExit==null? Double.NaN : entryExit[0];
    }

    /**
     * <p>
     *     Tests where a {@link Ray} and a {@link Plane} collide.
     * </p>
     * <p>
     *     The returned value is a multiplier for the directional vector of the ray at which the ray and the other
     *     object collide with each other.
     * </p>
     * <p>
     *     The point of the collision can be obtained by setting the hypot of the first ray to a multiplier using
     *     {@link Ray#setLength(double)} (mutation) or {@link Ray#getPoint(double)} (no mutation).
     * </p>
     *
     * @param ray the ray
     * @param plane the plane
     * @return where the ray and the plane collide or {@link Double#NaN}
     */
    public static double cast(Ray ray, Plane plane) {
        double numerator, denominator;
        Vector normal = plane.getNormal().normalize();

        denominator = normal.dot( ray.getDirection().normalize() );
        if (Spatium.equals(denominator, 0)) //ray and plane are parallel
            return Double.NaN;

        //calculate the distance between the linePoint and the line-plane intersection point
        numerator = normal.dot( plane.getPoint().subtract(ray.getOrigin()) );

        return numerator / denominator;
    }

    /**
     * <p>
     *     Tests where a {@link Ray} and an {@link AxisPlane} collide.
     * </p>
     * <p>
     *     The returned value is a multiplier for the directional vector of the ray at which the ray and the other
     *     object collide with each other.
     * </p>
     * <p>
     *     The point of the collision can be obtained by setting the hypot of the first ray to a multiplier using
     *     {@link Ray#setLength(double)} (mutation) or {@link Ray#getPoint(double)} (no mutation).
     * </p>
     *
     * @param ray the ray
     * @param plane the plane
     * @return where the ray and the plane collide or {@link Double#NaN}
     */
    public static double cast(Ray ray, AxisPlane plane) {
        switch (plane.getAxis()) {
            case X: return (ray.getOriginX() - plane.getDepth()) / ray.getDirX();
            case Y: return (ray.getOriginY() - plane.getDepth()) / ray.getDirY();
            case Z: return (ray.getOriginZ() - plane.getDepth()) / ray.getDirZ();
            default: throw new IllegalArgumentException("plane has no axis");
        }
    }

    /**
     * <p>
     *     Tests where a {@link Ray} and a {@link Slab} collide.
     * </p>
     * <p>
     *     The returned value is a multiplier for the directional vector of the ray at which the ray and the other
     *     object collide with each other.
     * </p>
     * <p>
     *     The point of the collision can be obtained by setting the hypot of the first ray to a multiplier using
     *     {@link Ray#setLength(double)} (mutation) or {@link Ray#getPoint(double)} (no mutation).
     * </p>
     *
     * @param ray the ray
     * @param slab the slab
     * @return where the ray and the plane collide or {@link Double#NaN}
     */
    public static double cast(Ray ray, Slab slab) {
        return pierce(ray, slab)[0];
    }

    /**
     * <p>
     *     Tests where a {@link Ray} and an {@link AxisPlane} collide.
     * </p>
     * <p>
     *     The returned value is a multiplier for the directional vector of the ray at which the ray and the other
     *     object collide with each other.
     * </p>
     * <p>
     *     The point of the collision can be obtained by setting the hypot of the first ray to a multiplier using
     *     {@link Ray#setLength(double)} (mutation) or {@link Ray#getPoint(double)} (no mutation).
     * </p>
     *
     * @param ray the ray
     * @param box the bounding box
     * @return where the ray and the box collide or {@link Double#NaN}
     */
    public static double cast(Ray ray, AxisAlignedBB box) {
        double[] entryExit = pierce(ray, box);
        return entryExit==null? Double.NaN : entryExit[0];
    }

    /**
     * <p>
     *     Tests where a {@link Ray} and an {@link OrientedBB} collide.
     * </p>
     * <p>
     *     The returned value is a multiplier for the directional vector of the ray at which the ray and the other
     *     object collide with each other.
     * </p>
     * <p>
     *     The point of the collision can be obtained by setting the hypot of the first ray to a multiplier using
     *     {@link Ray#setLength(double)} (mutation) or {@link Ray#getPoint(double)} (no mutation).
     * </p>
     *
     * @param ray the ray
     * @param box the bounding box
     * @return where the ray and the box collide or {@link Double#NaN}
     */
    public static double cast(Ray ray, OrientedBB box) {
        double[] entryExit = pierce(ray, box);
        return entryExit==null? Double.NaN : entryExit[0];
    }

    /**
     * <p>
     *     Tests where a {@link Ray} and a {@link Triangle} collide.
     * </p>
     * <p>
     *     The returned value is a multiplier for the directional vector of the ray at which the ray and the other
     *     object collide with each other.
     * </p>
     * <p>
     *     The point of the collision can be obtained by setting the hypot of the first ray to a multiplier using
     *     {@link Ray#setLength(double)} (mutation) or {@link Ray#getPoint(double)} (no mutation).
     * </p>
     * Source:<a href="https://en.wikipedia.org/wiki/M%C3%B6ller%E2%80%93Trumbore_intersection_algorithm">
     * Möller–Trumbore intersection algorithm</a>
     *
     * @param ray the ray
     * @param triangle the triangle
     * @return where the box and the point collide or {@link Double#NaN}
     */
    public static double cast(Ray ray, Triangle triangle) {
        Vector
                origin = ray.getOrigin(),
                dir = ray.getDirection(),
                a = triangle.getA(),
                ab = Vector.between(a, triangle.getB()),
                ac = Vector.between(a, triangle.getC()),
                normal1 = dir.cross(ab);

        double det = ab.dot(normal1);
        //ray is parallel to triangle
        if (Spatium.equals(det, 0)) return Double.NaN;
        double invDet = 1 / det;

        Vector ao = Vector.between(a, origin);
        double u = origin.dot(normal1) * invDet;
        //intersection lies outside the triangle
        if (u < 0 || u > 1) return Double.NaN;

        Vector normal2 = ao.cross(ab);
        double v = dir.dot(normal2) * invDet;
        //intersection lies outside the triangle
        if (v < 0 || v > 1) return Double.NaN;

        double t = ac.dot(normal2) * invDet;

        return t>Spatium.EPSILON? t : Double.NaN;
    }

    //ENTRY AND EXIT RAY CASTS

    /**
     * <p>
     *     Tests where a {@link Ray} enters and exits a {@link Sphere} collide. Unlike most algorithms, this one
     *     neither cancels early when the ray is inside the sphere, nor when the sphere is behind the ray origin.
     * </p>
     * <p>
     *     The returned value is a multiplier for the directional vector of the ray at which the ray and the other
     *     object collide with each other.
     * </p>
     * <p>
     *     The point of the collision can be obtained by setting the hypot of the first ray to a multiplier using
     *     {@link Ray#setLength(double)} (mutation) or {@link Ray#getPoint(double)} (no mutation).
     * </p>
     *
     * @param ray the ray
     * @param sphere the sphere
     * @return where the ray and the point collide or {@link Double#NaN}
     */
    public static double[] pierce(Ray ray, Sphere sphere) {
        Vector center = sphere.getCenter();

        //the ray multiplier
        double tm = Projections.pointOnRay(ray, center);
        Vector base = ray.getPoint(tm);

        double r = sphere.getRadius();
        double d = base.distanceTo(center);
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
     *     Tests where a {@link Ray} enters and exits a {@link Slab}.
     * </p>
     * <p>
     *     The returned values are multipliers for the directional vector of the ray at which the ray and the other
     *     object collide with each other.
     * </p>
     * <p>
     *     The point of the collision can be obtained by setting the hypot of the first ray to a multiplier using
     *     {@link Ray#setLength(double)}.
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
    @Nonnull
    public static double[] pierce(Ray ray, Slab slab) {
        Vector normal = slab.getNormal().normalize();
        double denominator = normal.dot( ray.getDirection().normalize() );
        //if (Spatium.equals(denominator, 0)) //ray and plane are parallel
        //    return null;

        Vector origin = ray.getOrigin();
        if (denominator > 0) return new double[] {
                normal.dot( slab.getMinPoint().subtract(origin) ) / denominator,
                normal.dot( slab.getMaxPoint().subtract(origin) ) / denominator
        };
        else return new double[] {
                normal.dot( slab.getMinPoint().subtract(origin) ) / denominator,
                normal.dot( slab.getMaxPoint().subtract(origin) ) / denominator
        };
    }

    /**
     * <p>
     *     Tests where a {@link Ray} enters and exits an {@link AxisSlab}.
     * </p>
     * <p>
     *     The returned values are multipliers for the directional vector of the ray at which the ray and the other
     *     object collide with each other.
     * </p>
     * <p>
     *     The point of the collision can be obtained by setting the hypot of the first ray to a multiplier using
     *     {@link Ray#setLength(double)}.
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
    @Nonnull
    public static double[] pierce(Ray ray, AxisSlab slab) {
        double d;
        switch (slab.getAxis()) {
            case X: d = 1 / ray.getDirX(); break;
            case Y: d = 1 / ray.getDirY(); break;
            case Z: d = 1 / ray.getDirZ(); break;
            default: throw new IllegalStateException("slab has no axis");
        }

        if (d >= 0) return new double[] {
                (slab.getMinDepth() - ray.getOriginX()) / d,
                (slab.getMaxDepth() - ray.getOriginX()) / d
        };
        else return new double[]{
                (slab.getMaxDepth() - ray.getOriginX()) / d,
                (slab.getMinDepth() - ray.getOriginX()) / d
        };
    }

    /**
     * <p>
     *     Tests where a {@link Ray} enters and exits an {@link AxisAlignedBB}.
     * </p>
     * <p>
     *     The returned values are multipliers for the directional vector of the ray at which the ray and the other
     *     object collide with each other.
     * </p>
     * <p>
     *     The point of the collision can be obtained by setting the hypot of the first ray to a multiplier using
     *     {@link Ray#setLength(double)}.
     * </p>
     *
     * @param ray the ray
     * @param box the bounding box
     * @return the entry and exit points of the ray or null
     */
    @Nullable
    public static double[] pierce(Ray ray, AxisAlignedBB box) {

        double tmin, tmax;

        {//x-slab collision
            final double div = 1 / ray.getDirX();
            tmin = ((div >= 0? box.getMinX() : box.getMaxX()) - ray.getOriginX()) * div;
            tmax = ((div >= 0? box.getMaxX() : box.getMinX()) - ray.getOriginX()) * div;
        }
        {//y-slab collision
            final double div = 1 / ray.getDirY();
            double tymin = ((div >= 0? box.getMinY() : box.getMaxY()) - ray.getOriginY()) * div;
            double tymax = ((div >= 0? box.getMaxY() : box.getMinY()) - ray.getOriginY()) * div;
            
            if (tmin > tymax || tymin > tmax) return null;
            if (tymin > tmin) tmin = tymin;
            if (tymax < tmax) tmax = tymax;
        }
        {//z-slab collision
            final double div = 1 / ray.getDirZ();
            double tzmin = ((div >= 0? box.getMinZ() : box.getMaxZ()) - ray.getOriginZ()) * div;
            double tzmax = ((div >= 0? box.getMaxZ() : box.getMinZ()) - ray.getOriginZ()) * div;
            
            if (tmin > tzmax || tzmin > tmax) return null;
            if (tzmin > tmin) tmin = tzmin;
            if (tzmax < tmax) tmax = tzmax;
        }
        
        return new double[] {tmin, tmax};
    }

    /**
     * <p>
     *     Tests where a {@link Ray} enters and exits an {@link OrientedBB}.
     * </p>
     * <p>
     *     The returned values are multipliers for the directional vector of the ray at which the ray and the other
     *     object collide with each other.
     * </p>
     * <p>
     *     The point of the collision can be obtained by setting the hypot of the first ray to a multiplier using
     *     {@link Ray#setLength(double)} (mutation) or {@link Ray#getPoint(double)} (no mutation).
     * </p>
     *
     * @param ray the ray
     * @param box the bounding box
     * @return the entry and exit points of the ray or null
     */
    @Nullable
    public static double[] pierce(Ray ray, OrientedBB box) {
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
     * Tests where a moving {@link AxisAlignedBB} enters and exits a static {@link AxisAlignedBB}.
     *
     * @param a the first, moving box
     * @param motion the motion of the first bounding box
     * @param b the second, static box
     * @return the collision
     */
    public static RayPierceCollision<AxisAlignedBB> pierce(AxisAlignedBB a, Vector motion, AxisAlignedBB b) {
        //minkowski sum of a rotated 180 degrees around origin (a') and b
        AxisAlignedBB minkowski = AxisAlignedBB.fromPoints(
                b.getMinX()-a.getMaxX(),
                b.getMinY()-a.getMaxY(),
                b.getMinZ()-a.getMaxZ(),
                b.getMaxX()-a.getMinX(),
                b.getMaxY()-a.getMinY(),
                b.getMaxZ()-a.getMinZ());

        Ray ray = Ray.fromOD(0, 0, 0, motion.getX(), motion.getY(), motion.getZ());
        double[] pierce = pierce(ray, minkowski);
        if (pierce == null) return new RayPierceCollision<>(ray, b);

        return new RayPierceCollision<>(CollisionEngine.CollisionResult.POSITIVE, ray, b, pierce[0], pierce[1]);
    }

}
