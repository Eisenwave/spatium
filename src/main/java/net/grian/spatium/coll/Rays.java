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
     * <br><br>The point of the collision can be obtained by setting the hypot
     * of the first ray to that multiplier using {@link Ray#setLength(double)}.
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
     *
     * <br><br>The returned value is a multiplier for the directional vector of
     * the ray at which the ray and the other object collide with each other.
     *
     * <br><br>The point of the collision can be obtained by setting the hypot
     * of the first ray to that multiplier using {@link Ray#setLength(double)}.
     *
     * @param ray the ray
     * @param point the point
     * @return where the ray and the point collide or {@link Double#NaN}
     */
    public static double cast(Ray ray, Vector point) {
        return ray.containsAt(point);
    }

    /**
     * Tests where a {@link Ray} and a {@link Sphere} collide.
     *
     * <br><br>The returned value is a multiplier for the directional vector of
     * the ray at which the ray and the other object collide with each other.
     *
     * <br><br>The point of the collision can be obtained by setting the hypot
     * of the first ray to that multiplier using {@link Ray#setLength(double)}.
     *
     * @param ray the ray
     * @param sphere the sphere
     * @return where the ray and the point collide or {@link Double#NaN}
     */
    public static double cast(Ray ray, Sphere sphere) {
        return 0;

        /*
        Vector center_origin = ray.getOrigin().subtract(sphere.getCenter());
        double radiusSquared = sphere.getRadiusSquared();

        //ray origin inside sphere
        if(center_origin.dot(center_origin) <= radiusSquared) {
            return 0;
        }

        else if(center_origin.dot(ray.getDirection()) <= 0) {
            Vector v = center_origin.subtract()projection(CA,d)
            double vSquared = v.dot(v);
            if(vSquared <= radiusSquared){
                collisionPoint = C + v - multiply(normalize(d), Math.sqrt(rSquared-vSquared))
            }
            else{
                collisionPoint = none
            }
        }
        
        else return Double.NaN;

        /*
        //input
        vector A the ray origin
        vector d the ray direction
        vector C the sphere center
        double r the sphere radius
        //end of input

        vector collisionPoint
        vector v
        vector CA = A-C
        double rSquared = r*r
        double vSquared
        if(dotProduct(CA,CA) <= rSquared){
            collisionPoint = A
        }
        else if(dotProduct(CA,d) <= 0){
            v = CA - projection(CA,d)
            vSquared = dotProduct(v,v)
            if(vSquared <= rSquared){
                collisionPoint = C + v - multiply(normalize(d),squareRoot(rSquared-vSquared))
            }
            else{
                collisionPoint = none
            }
        }
        else{
            collisionPoint = none
        }
        */
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
     *     The point of the collision can be obtained by setting the hypot of the first ray to that multiplier using
     *     {@link Ray#setLength(double)}.
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
     *     The point of the collision can be obtained by setting the hypot of the first ray to that multiplier using
     *     {@link Ray#setLength(double)}.
     * </p>
     *
     * @param ray the ray
     * @param plane the plane
     * @return where the ray and the plane collide or {@link Double#NaN}
     */
    public static double cast(Ray ray, AxisPlane plane) {
        switch (plane.getAxis()) {
            case X: {
                double t = (ray.getOriginX() - plane.getDepth()) / ray.getDirX();
                return Double.isFinite(t)? t : Double.NaN;
            }
            case Y: {
                double t = (ray.getOriginY() - plane.getDepth()) / ray.getDirY();
                return Double.isFinite(t)? t : Double.NaN;
            }
            case Z: {
                double t = (ray.getOriginZ() - plane.getDepth()) / ray.getDirZ();
                return Double.isFinite(t)? t : Double.NaN;
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
     *     The point of the collision can be obtained by setting the hypot of the first ray to that multiplier using
     *     {@link Ray#setLength(double)}.
     * </p>
     *
     * @param ray the ray
     * @param slab the slab
     * @return where the ray and the plane collide or {@link Double#NaN}
     */
    public static double cast(Ray ray, Slab slab) {
        double[] entryExit = pierce(ray, slab);
        return entryExit==null? Double.NaN : entryExit[0];
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
     *     The point of the collision can be obtained by setting the hypot of the first ray to that multiplier using
     *     {@link Ray#setLength(double)}.
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
     *     The point of the collision can be obtained by setting the hypot of the first ray to that multiplier using
     *     {@link Ray#setLength(double)}.
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
     *     The point of the collision can be obtained by setting the hypot of the first ray to that multiplier using
     *     {@link Ray#setLength(double)}.
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
                o = ray.getOrigin(), d = ray.getDirection(),
                a = triangle.getA(), b = triangle.getB(), c = triangle.getC(),
                ab = Vector.between(a, b), ac = Vector.between(a, c),
                normal1 = d.cross(ab);

        double det = ab.dot(normal1);
        //ray is parallel to triangle
        if (Spatium.equals(det, 0)) return Double.NaN;
        double invDet = 1 / det;

        Vector ao = Vector.between(o, a);
        double u = ao.dot(normal1) * invDet;
        //intersection lies outside the triangle
        if (u < 0 || u > 1) return Double.NaN;

        Vector normal2 = ao.cross(ab);
        double v = d.dot(normal2) * invDet;
        //intersection lies outside the triangle
        if (v < 0 || v > 1) return Double.NaN;

        double t = ac.dot(normal2) * invDet;

        return t>Spatium.EPSILON? t : Double.NaN;
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
     *     The point of the collision can be obtained by setting the hypot of the first ray to a multiplier using
     *     {@link Ray#setLength(double)}.
     * </p>
     *
     * @param ray the ray
     * @param slab the slab
     * @return the entry and exit points of the ray or null
     */
    public static double[] pierce(Ray ray, Slab slab) {
        Vector normal = slab.getNormal().normalize();
        double denominator = normal.dot( ray.getDirection().normalize() );
        if (Spatium.equals(denominator, 0)) //ray and plane are parallel
            return null;

        Vector origin = ray.getOrigin();
        if (denominator > 0) return new double[] {
                normal.dot( slab.getMinPoint().subtract(origin) ) / denominator,
                normal.dot( slab.getMaxPoint().subtract(origin) ) / denominator
        };
        else return new double[]{
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
     *
     * @param ray the ray
     * @param slab the slab
     * @return the entry and exit points of the ray or null
     */
    public static double[] pierce(Ray ray, AxisSlab slab) {
        double d;
        switch (slab.getAxis()) {
            case X: d = ray.getDirX(); break;
            case Y: d = ray.getDirY(); break;
            case Z: d = ray.getDirZ(); break;
            default: throw new IllegalStateException("slab has no axis");
        }
        if (Spatium.equals(d, 0)) return null;

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
    public static double[] pierce(Ray ray, AxisAlignedBB box) {

        double tmin, tmax, tymin, tymax, tzmin, tzmax;

        //x bounds
        double divx = 1 / ray.getDirX();
        if (divx >= 0) {
            tmin = (box.getMinX() - ray.getOriginX()) * divx;
            tmax = (box.getMaxX() - ray.getOriginX()) * divx;
        }
        else {
            tmin = (box.getMaxX() - ray.getOriginX()) * divx;
            tmax = (box.getMinX() - ray.getOriginX()) * divx;
        }

        //y bounds
        double divy = 1 / ray.getDirY();
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
        double divz = 1 / ray.getDirZ();
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
     *     {@link Ray#setLength(double)}.
     * </p>
     *
     * @param ray the ray
     * @param box the bounding box
     * @return the entry and exit points of the ray or null
     */
    public static double[] pierce(Ray ray, OrientedBB box) {
        //u bounds
        double[] t = pierce(ray, box.getSlabU());
        if (t == null) return null;

        //v bounds
        double[] tv = pierce(ray, box.getSlabV());
        if (tv == null) return null;

        if (t[0] > tv[1] || tv[0] > t[1]) return null;
        if (tv[0] > t[0]) t[0] = tv[0];
        if (tv[1] < t[1]) t[1] = tv[1];

        //w bounds
        double[] tw = pierce(ray, box.getSlabW());
        if (tw == null) return null;

        if (t[0] > tw[1] || tw[0] > t[1]) return null;
        if (tw[0] > t[0]) t[0] = tw[0];
        if (tw[1] < t[1]) t[1] = tw[1];

        return t;
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
