package net.grian.spatium.coll;

import net.grian.spatium.geo3.AxisAlignedBB3;
import net.grian.spatium.geo3.Plane;
import net.grian.spatium.geo3.Sphere;
import net.grian.spatium.geo3.Vector3;

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
    public Vector3 findNormal(Sphere sphere, Vector3 point) {
        return Vector3.between(sphere.getCenter(), point);
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
    public Vector3 findNormal(AxisAlignedBB3 box, Vector3 point) {
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
    public Vector3 findNormal(Plane plane, Vector3 point) {
        return plane.signedDistanceTo(point) > 0?
                plane.getNormal() :
                plane.getNormal().negate();
    }

    /**
     * Returns the reflection vector of an incident vector and a normal.
     *
     * @param incident the incident vector
     * @param normal the normal vector
     * @return the reflection vector
     */
    public Vector3 reflect(Vector3 incident, Vector3 normal) {
        Vector3 d = incident.clone();
        Vector3 n = normal.normalize();
        n.multiply(2 * d.dot(n));

        return d.subtract(n);
    }

}
