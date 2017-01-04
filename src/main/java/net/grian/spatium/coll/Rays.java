package net.grian.spatium.coll;

import net.grian.spatium.Spatium;
import net.grian.spatium.geo.*;

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
     *
     * <br><br>The returned value is a multiplier for the directional vector of
     * the ray at which the ray and the other object collide with each other.
     *
     * <br><br>The point of the collision can be obtained by setting the length
     * of the first ray to that multiplier using {@link Ray#setLength(float)}.
     *
     * @param a the first ray
     * @param b the second ray
     * @return the ray multiplier or {@link Float#NaN}
     */
    public static float cast(Ray a, Ray b) {
        Vector
                dirA = a.getDirection(),
                dirB = b.getDirection(),
                dirC = b.getOrigin().subtract(a.getOrigin()),
                crossAB = dirA.cross(dirB),
                crossCB = dirC.cross(dirB);

        float
                absPlanarFactor = Math.abs(dirC.dot(crossAB)),
                sqrLength = crossAB.getLengthSquared();

        //is coplanar, and not parallel
        if (absPlanarFactor < Spatium.EPSILON && sqrLength > Spatium.EPSILON) {
            return crossCB.dot(crossAB) / sqrLength;
        }
        else {
            return Float.NaN;
        }
    }

    /**
     * Tests where a {@link Ray} and a Point ({@link Vector}) collide.
     *
     * <br><br>The returned value is a multiplier for the directional vector of
     * the ray at which the ray and the other object collide with each other.
     *
     * <br><br>The point of the collision can be obtained by setting the length
     * of the first ray to that multiplier using {@link Ray#setLength(float)}.
     *
     * @param ray the ray
     * @param point the point
     * @return where the ray and the point collide or {@link Float#NaN}
     */
    public static float cast(Ray ray, Vector point) {
        return ray.containsAt(point);
    }

    /**
     * Tests where a {@link Ray} and a {@link Sphere} collide.
     *
     * <br><br>The returned value is a multiplier for the directional vector of
     * the ray at which the ray and the other object collide with each other.
     *
     * <br><br>The point of the collision can be obtained by setting the length
     * of the first ray to that multiplier using {@link Ray#setLength(float)}.
     *
     * @param ray the ray
     * @param sphere the sphere
     * @return where the ray and the point collide or {@link Float#NaN}
     */
    public static float cast(Ray ray, Sphere sphere) {
        Vector
                origin         = ray.getOrigin(),
                direction      = ray.getDirection(),
                center         = sphere.getCenter(),
                originToCenter = Vector.between(origin, center);

        float radius = sphere.getRadius();

        //ray not pointing towards sphere center
        if (direction.dot(originToCenter) <= 0) {

            float l = originToCenter.getLength();

            //ray origin is outside sphere
            if (l > radius)
                return Float.NaN;

                //ray origin is on the edge of sphere
            else if (Spatium.equals(l, radius))
                return 0;

                //ray origin is inside sphere
            else {
                Vector base = ray.closestPointTo(originToCenter);

                float
                        lengthCenterBase = Vector.between(center, base).getLength(),
                        lengthOriginBase = Vector.between(origin, base).getLength(),
                        dist = (float) Math.sqrt(radius * radius + lengthCenterBase * lengthOriginBase);

                return dist - lengthOriginBase;
            }

        }

        //ray pointing towards sphere center
        else {
            Vector base = ray.closestPointTo(originToCenter);
            Vector baseToCenter = Vector.between(base, center);

            float l = baseToCenter.getLength();

            //no intersection
            if (l > radius) return Float.NaN;

                //one point intersection
            else if (Spatium.equals(l, sphere.getRadius())) return base.distanceTo(origin);

                //two point intersection
            else {
                float
                        lengthCenterBase = Vector.between(center, base).getLength(),
                        lengthOriginBase = Vector.between(origin, base).getLength(),
                        dist = (float) Math.sqrt(sphere.getRadiusSquared() + lengthCenterBase * lengthOriginBase),
                        t;

                //origin outside sphere
                if (originToCenter.getLength() > radius)
                    t = lengthOriginBase - dist;

                    // origin is inside sphere
                else
                    t = lengthOriginBase + dist;

                return t;
            }
        }
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
     *     The point of the collision can be obtained by setting the length of the first ray to that multiplier using
     *     {@link Ray#setLength(float)}.
     * </p>
     *
     * @param ray the ray
     * @param plane the plane
     * @return where the ray and the plane collide or {@link Float#NaN}
     */
    public static float cast(Ray ray, Plane plane) {
        float numerator, denominator;
        Vector normal = plane.getNormal().normalize();

        denominator = normal.dot( ray.getDirection().normalize() );
        if (Spatium.equals(denominator, 0)) //ray and plane are parallel
            return Float.NaN;

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
     *     The point of the collision can be obtained by setting the length of the first ray to that multiplier using
     *     {@link Ray#setLength(float)}.
     * </p>
     *
     * @param ray the ray
     * @param plane the plane
     * @return where the ray and the plane collide or {@link Float#NaN}
     */
    public static float cast(Ray ray, AxisPlane plane) {
        switch (plane.getAxis()) {
            case X: {
                float t = (ray.getOriginX() - plane.getDepth()) / ray.getDirX();
                return Float.isFinite(t)? t : Float.NaN;
            }
            case Y: {
                float t = (ray.getOriginY() - plane.getDepth()) / ray.getDirY();
                return Float.isFinite(t)? t : Float.NaN;
            }
            case Z: {
                float t = (ray.getOriginZ() - plane.getDepth()) / ray.getDirZ();
                return Float.isFinite(t)? t : Float.NaN;
            }
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
     *     The point of the collision can be obtained by setting the length of the first ray to that multiplier using
     *     {@link Ray#setLength(float)}.
     * </p>
     *
     * @param ray the ray
     * @param slab the slab
     * @return where the ray and the plane collide or {@link Float#NaN}
     */
    public static float cast(Ray ray, Slab slab) {
        float[] entryExit = pierce(ray, slab);
        return entryExit==null? Float.NaN : entryExit[0];
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
     *     The point of the collision can be obtained by setting the length of the first ray to that multiplier using
     *     {@link Ray#setLength(float)}.
     * </p>
     *
     * @param ray the ray
     * @param box the bounding box
     * @return where the ray and the box collide or {@link Float#NaN}
     */
    public static float cast(Ray ray, AxisAlignedBB box) {
        float[] entryExit = pierce(ray, box);
        return entryExit==null? Float.NaN : entryExit[0];
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
     *     The point of the collision can be obtained by setting the length of the first ray to that multiplier using
     *     {@link Ray#setLength(float)}.
     * </p>
     *
     * @param ray the ray
     * @param box the bounding box
     * @return where the ray and the box collide or {@link Float#NaN}
     */
    public static float cast(Ray ray, OrientedBB box) {
        float[] entryExit = pierce(ray, box);
        return entryExit==null? Float.NaN : entryExit[0];
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
     *     The point of the collision can be obtained by setting the length of the first ray to that multiplier using
     *     {@link Ray#setLength(float)}.
     * </p>
     * Source:<a href="https://en.wikipedia.org/wiki/M%C3%B6ller%E2%80%93Trumbore_intersection_algorithm">
     * Möller–Trumbore intersection algorithm</a>
     *
     * @param ray the ray
     * @param triangle the triangle
     * @return where the box and the point collide or {@link Float#NaN}
     */
    public static float cast(Ray ray, Triangle triangle) {
        Vector
                o = ray.getOrigin(), d = ray.getDirection(),
                a = triangle.getA(), b = triangle.getB(), c = triangle.getC(),
                ab = Vector.between(a, b), ac = Vector.between(a, c),
                normal1 = d.cross(ab);

        float det = ab.dot(normal1);
        //ray is parallel to triangle
        if (Spatium.equals(det, 0)) return Float.NaN;
        float invDet = 1 / det;

        Vector ao = Vector.between(o, a);
        float u = ao.dot(normal1) * invDet;
        //intersection lies outside the triangle
        if (u < 0 || u > 1) return Float.NaN;

        Vector normal2 = ao.cross(ab);
        float v = d.dot(normal2) * invDet;
        //intersection lies outside the triangle
        if (v < 0 || v > 1) return Float.NaN;

        float t = ac.dot(normal2) * invDet;

        return t>Spatium.EPSILON? t : Float.NaN;
    }

    //RAY PIERCES

    /**
     * <p>
     *     Tests where a {@link Ray} enters and exits a {@link Slab}.
     * </p>
     * <p>
     *     The returned values are multipliers for the directional vector of the ray at which the ray and the other
     *     object collide with each other.
     * </p>
     * <p>
     *     The point of the collision can be obtained by setting the length of the first ray to a multiplier using
     *     {@link Ray#setLength(float)}.
     * </p>
     *
     * @param ray the ray
     * @param slab the slab
     * @return the entry and exit points of the ray or null
     */
    public static float[] pierce(Ray ray, Slab slab) {
        Vector normal = slab.getNormal().normalize();
        float denominator = normal.dot( ray.getDirection().normalize() );
        if (Spatium.equals(denominator, 0)) //ray and plane are parallel
            return null;

        Vector origin = ray.getOrigin();
        if (denominator > 0) return new float[] {
                normal.dot( slab.getMinPoint().subtract(origin) ) / denominator,
                normal.dot( slab.getMaxPoint().subtract(origin) ) / denominator
        };
        else return new float[]{
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
     *     The point of the collision can be obtained by setting the length of the first ray to a multiplier using
     *     {@link Ray#setLength(float)}.
     * </p>
     *
     * @param ray the ray
     * @param slab the slab
     * @return the entry and exit points of the ray or null
     */
    public static float[] pierce(Ray ray, AxisSlab slab) {
        float d;
        switch (slab.getAxis()) {
            case X: d = ray.getDirX(); break;
            case Y: d = ray.getDirY(); break;
            case Z: d = ray.getDirZ(); break;
            default: throw new IllegalStateException("slab has no axis");
        }
        if (Spatium.equals(d, 0)) return null;

        if (d >= 0) return new float[] {
                (slab.getMinDepth() - ray.getOriginX()) / d,
                (slab.getMaxDepth() - ray.getOriginX()) / d
        };
        else return new float[]{
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
     *     The point of the collision can be obtained by setting the length of the first ray to a multiplier using
     *     {@link Ray#setLength(float)}.
     * </p>
     *
     * @param ray the ray
     * @param box the bounding box
     * @return the entry and exit points of the ray or null
     */
    public static float[] pierce(Ray ray, AxisAlignedBB box) {

        float tmin, tmax, tymin, tymax, tzmin, tzmax;

        //x bounds
        float divx = 1 / ray.getDirX();
        if (divx >= 0) {
            tmin = (box.getMinX() - ray.getOriginX()) * divx;
            tmax = (box.getMaxX() - ray.getOriginX()) * divx;
        }
        else {
            tmin = (box.getMaxX() - ray.getOriginX()) * divx;
            tmax = (box.getMinX() - ray.getOriginX()) * divx;
        }

        //y bounds
        float divy = 1 / ray.getDirY();
        if (divy >= 0) {
            tymin = (box.getMinY() - ray.getOriginY()) * divy;
            tymax = (box.getMaxY() - ray.getOriginY()) * divy;
        }
        else {
            tymin = (box.getMaxY() - ray.getOriginY()) * divy;
            tymax = (box.getMinY() - ray.getOriginY()) * divy;
        }

        if (tmin > tymax || tymin > tmax) {
            return null;
        }
        if (tymin > tmin) tmin = tymin;
        if (tymax < tmax) tmax = tymax;

        //z bounds
        float divz = 1 / ray.getDirZ();
        if (divz >= 0) {
            tzmin = (box.getMinZ() - ray.getOriginZ()) * divz;
            tzmax = (box.getMaxZ() - ray.getOriginZ()) * divz;
        }
        else {
            tzmin = (box.getMaxZ() - ray.getOriginZ()) * divz;
            tzmax = (box.getMinZ() - ray.getOriginZ()) * divz;
        }

        if (tmin > tzmax || tzmin > tmax)
            return null;
        if (tzmin > tmin) tmin = tzmin;
        if (tzmax < tmax) tmax = tzmax;

        return new float[] {tmin, tmax};
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
     *     The point of the collision can be obtained by setting the length of the first ray to a multiplier using
     *     {@link Ray#setLength(float)}.
     * </p>
     *
     * @param ray the ray
     * @param box the bounding box
     * @return the entry and exit points of the ray or null
     */
    public static float[] pierce(Ray ray, OrientedBB box) {
        //u bounds
        float[] t = pierce(ray, box.getSlabU());
        if (t == null) return null;

        //v bounds
        float[] tv = pierce(ray, box.getSlabV());
        if (tv == null) return null;

        if (t[0] > tv[1] || tv[0] > t[1]) return null;
        if (tv[0] > t[0]) t[0] = tv[0];
        if (tv[1] < t[1]) t[1] = tv[1];

        //w bounds
        float[] tw = pierce(ray, box.getSlabW());
        if (tw == null) return null;

        if (t[0] > tw[1] || tw[0] > t[1]) return null;
        if (tw[0] > t[0]) t[0] = tw[0];
        if (tw[1] < t[1]) t[1] = tw[1];

        return t;
    }

}
