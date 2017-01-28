package net.grian.spatium.coll;

import net.grian.spatium.geo.Plane;
import net.grian.spatium.geo.Ray;
import net.grian.spatium.geo.Vector;

public final class Projections {

    /**
     * Returns where the point ({@link Vector}) is being projected onto the {@link Ray}.
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
     * @return the ray multiplier
     */
    public static double pointOnRay(Ray ray, Vector point) {
        Vector
                direction = ray.getDirection(),
                originToPoint = Vector.between(ray.getOrigin(), point);

        return originToPoint.dot(direction) / direction.getLengthSquared();

        //Vector dir = ray.getDirection();
        //return dir.dot(point) / dir.dot(dir);
    }

    /**
     * Returns where the point ({@link Vector}) is being projected onto the {@link Plane}.
     *
     * @param plane the plane
     * @param point the point
     * @return the ray multiplier
     */
    public static Vector pointOnPlane(Plane plane, Vector point) {
        Vector normal = plane.getNormal();
        return point.subtract( normal.multiply(point.dot(normal)) );
    }

}
