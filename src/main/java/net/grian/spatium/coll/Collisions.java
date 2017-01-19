package net.grian.spatium.coll;

import net.grian.spatium.Spatium;
import net.grian.spatium.geo.*;

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
	public static boolean test(Vector a, Vector b) {
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
		float dot = Math.abs(a.getNormal().normalize().dot(b.getNormal().normalize()));
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
     * Tests whether two {@link Triangle}s collide/intersect.
     *
     * @param a the first triangle
     * @param b the second triangle
     * @return whether the triangles collide/intersect
     */
    public static boolean test(Triangle a, Triangle b) {
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
     * Tests whether an {@link AxisAlignedBB} and a {@link Sphere} collide.
     *
     * @param box the bounding box
     * @param sphere the sphere
     * @return whether the box and the sphere collide
     */
    public static boolean test(AxisAlignedBB box, Sphere sphere) {
        float d = Rays.cast(Ray.between(sphere.getCenter(), box.getCenter()), box);
        return d * d < sphere.getRadiusSquared();
    }

    /**
     * Tests whether an {@link AxisAlignedBB} and an {@link AxisPlane} collide.
     *
     * @param box the bounding box
     * @param plane the plane
     * @return whether the box and the sphere collide
     */
    public static boolean test(AxisAlignedBB box, AxisPlane plane) {
        float d = plane.getDepth();
        switch (plane.getAxis()) {
            case X: return (box.getMinX() <= d) != (box.getMaxX() <= d);
            case Y: return (box.getMinY() <= d) != (box.getMaxY() <= d);
            case Z: return (box.getMinZ() <= d) != (box.getMaxZ() <= d);
            default: throw new IllegalArgumentException("plane has no axis");
        }
    }

    /**
     * Tests whether a {@link Sphere} and a {@link Plane} collide.
     *
     * @param sphere the sphere
     * @param plane the plane
     * @return whether the sphere and the plane collide
     */
	public static boolean test(Sphere sphere, Plane plane) {
        Ray sphereToPlane = Ray.fromOD(sphere.getCenter(), plane.getNormal().multiply(-1));
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
     * Tests whether a {@link Ray} and a {@link Ray} collide.
     *
     * @param a the first ray
     * @param b the second ray
     * @return whether the rays collide
     */
    public static boolean test(Ray a, Ray b) {
        return Float.isNaN(Rays.cast(a, b));
    }

	/**
	 * Tests whether a {@link Ray} and a Point ({@link Vector}) collide.
	 *
	 * @param ray the ray
	 * @param point the point
	 * @return whether the box and the point collide
	 */
	public static boolean test(Ray ray, Vector point) {
		return ray.contains(point);
	}

    /**
     * Tests whether a {@link Ray} and a {@link Plane} collide.
     *
     * @param ray the ray
     * @param plane the plane
     * @return whether the ray and the plane collide
     */
    public static boolean test(Ray ray, Plane plane) {
        return Float.isNaN(Rays.cast(ray, plane));
    }

    /**
     * Tests whether a {@link Ray} and a {@link AxisPlane} collide. This condition is fulfilled if either the
     * ray is not perpendicular to the plane or the ray lies within the plane.
     *
     * @param ray the ray
     * @param plane the plane
     * @return whether the ray and the plane collide
     */
    public static boolean test(Ray ray, AxisPlane plane) {
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
     * Tests whether a {@link Ray} and a {@link Sphere} collide.
     *
     * @param ray the ray
     * @param sphere the sphere
     * @return whether the ray and the plane collide
     */
    public static boolean test(Ray ray, Sphere sphere) {
        return Float.isNaN(Rays.cast(ray, sphere));
    }

	/**
	 * Tests whether a {@link Ray} and a {@link AxisAlignedBB} collide.
	 *
	 *
	 * @param ray the ray
	 * @param box the bounding box
	 * @return whether the ray and the box collide
	 */
	public static boolean test(Ray ray, AxisAlignedBB box) {
		return Float.isNaN(Rays.cast(ray, box));
	}

	/**
	 * Tests whether a {@link Sphere} and a Point collide.
	 *
	 * @param sphere the sphere
	 * @param point the point
	 * @return whether the sphere and the point collide
	 */
	public static boolean test(Sphere sphere, Vector point) {
		return sphere.contains(point);
	}

	/**
	 * Tests whether a {@link Triangle} and an {@link Plane} collide. This operation is performed by checking if not
	 * all points of triangle are on the same side of the plane.
	 *
	 * @param triangle the triangle
	 * @param plane the plane
	 * @return whether the triangle and the plane collide
	 */
	public static boolean test(Triangle triangle, Plane plane) {
		Vector
				p = plane.getPoint(),
				n = plane.getNormal();
		return !(
				Vector.between(triangle.getA(), p).dot(n) > 0 ==
				Vector.between(triangle.getB(), p).dot(n) > 0 ==
				Vector.between(triangle.getC(), p).dot(n) > 0 );
	}

	/**
	 * Tests whether a {@link Triangle} and an {@link AxisPlane} collide. This operation is performed by
	 * checking if not all points of triangle are on the same side of the plane.
	 *
	 * @param triangle the triangle
	 * @param plane the plane
	 * @return whether the triangle and the plane collide
	 */
	public static boolean test(Triangle triangle, AxisPlane plane) {
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
	 * Tests whether an {@link AxisAlignedBB} and a Point collide.
	 *
	 * @param box the bounding box
	 * @param point the point
	 * @return whether the box and the point collide
	 */
	public static boolean test(AxisAlignedBB box, Vector point) {
		return box.contains(point);
	}

	/**
	 * Tests whether an {@link AxisAlignedTSBP} and a Point collide.
	 *
	 * @param plane the plane
	 * @param point the point
	 * @return whether the box and the point collide
	 */
	public static boolean test(AxisAlignedTSBP plane, Vector point) {
		return plane.contains(point);
	}

}