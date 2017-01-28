package net.grian.spatium.coll;

import net.grian.spatium.geo.AxisAlignedBB;
import net.grian.spatium.geo.Plane;
import net.grian.spatium.geo.Sphere;
import net.grian.spatium.geo.Vector;

public final class Surfaces {

    private Surfaces() {}

    /**
     * <p>
     *     Finds the closest normal to the point on the surface of the bounding box.
     * </p>
     * <p>
     *     In this particular case the normal is always a vector pointing from the sphere center to the point.
     * </p>
     *
     * @param sphere the sphere
     * @param point the point
     * @return the surface normal
     */
    public Vector findNormal(Sphere sphere, Vector point) {
        return Vector.between(sphere.getCenter(), point);
    }

    /**
     * <p>
     *     Finds the closest normal to the point on the surface of the sphere.
     * </p>
     *
     * @param box the bounding box
     * @param point the point
     * @return the surface normal
     */
    public Vector findNormal(AxisAlignedBB box, Vector point) {
        return box.getClosestSide(point).vector();
    }

    /**
     * <p>
     *     Finds the closest normal to the point on the surface of the plane.
     * </p>
     * <p>
     *     In this particular case the normal is either the plane normal or the inverted plane normal depending on
     *     which side of the plane the point is on.
     * </p>
     *
     * @param plane the plane
     * @param point the point
     * @return the surface normal
     */
    public Vector findNormal(Plane plane, Vector point) {
        return plane.signedDistanceTo(point) > 0?
                plane.getNormal() :
                plane.getNormal().invert();
    }

    /**
     * Returns the reflection vector of an incident vector and a normal.
     *
     * @param incident the incident vector
     * @param normal the normal vector
     * @return the reflection vector
     */
    public Vector reflect(Vector incident, Vector normal) {
        Vector d = incident.clone();
        Vector n = normal.normalize();
        n.multiply(2 * d.dot(n));

        return d.subtract(n);
    }

}
