package net.grian.spatium.coll;

import net.grian.spatium.Spatium;
import net.grian.spatium.geo.*;

public class Collisions {

	private Collisions() {}

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
	 * Tests whether two {@link Plane}s collide.
	 *
	 * @param a the first plane
	 * @param b the second plane
	 * @return whether the points collide
	 */
	public static boolean test(Plane a, Plane b) {
		return intersect(a, b) != null;
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
	 * Tests whether a {@link Ray} and a {@link AxisAlignedBB} collide.
	 *
	 *
	 * @param ray the ray
	 * @param box the bounding box
	 * @return whether the ray and the box collide
	 */
	public static boolean test(Ray ray, AxisAlignedBB box) {
		return rayCast(ray, box) != Float.NaN;
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
	public static float rayCast(Ray a, Ray b) {
		Vector
		dirA = a.getDirection(),
		dirB = b.getDirection(),
		dirC = b.getOrigin().subtract(a.getOrigin()),
		crossAB = dirA.cross(dirB),
		crossCB = dirC.cross(dirB);

		float
		absPlanarFactor = Math.abs(dirC.dot(crossAB)),
		sqrLength = crossAB.getLengthSquared();

		//is coplanar, and not parrallel
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
	public static float rayCast(Ray ray, Vector point) {
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
	public static float rayCast(Ray ray, Sphere sphere) {
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
	 * Tests where a {@link Ray} and a {@link AxisAlignedBB} collide.
	 *
	 * <br><br>The returned value is a multiplier for the directional vector of
	 * the ray at which the ray and the other object collide with each other.
	 *
	 * <br><br>The point of the collision can be obtained by setting the length
	 * of the first ray to that multiplier using {@link Ray#setLength(float)}.
	 *
	 * @param ray the ray
	 * @param box the bounding box
	 * @return where the box and the point collide or {@link Float#NaN}
	 */
	public static float rayCast(Ray ray, AxisAlignedBB box) {

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
			return Float.NaN;
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
			return Float.NaN;
		if (tzmin > tmin) tmin = tzmin;
		//if (tzmax < tmax) tmax = tzmax;

		return tmin;
	}

	public static float rayCast(Ray ray, Plane plane) {
        float dotNumerator, dotDenominator;
        Vector normal = plane.getNormal();

        dotDenominator = normal.dot( ray.getDirection() );
        if (Spatium.equals(dotDenominator, 0)) //ray and plane are parallel
            return Float.NaN;

        //calculate the distance between the linePoint and the line-plane intersection point
        dotNumerator = normal.dot( plane.getCenter().subtract(ray.getOrigin()) );

        return dotNumerator / dotDenominator;
    }

	/**
	 * Returns the intersection of two {@link AxisAlignedBB}s. There is two possible scenarios:
     * <ul>
     *     <li>the boxes don't intersect, the result is null</li>
     *     <li>the boxes intersect or touch the result is another box</li>
     * </ul>
	 *
	 * @param a the first bounding box
	 * @param b the second bounding box
	 * @return the intersection of the boxes or null
	 */
	public static AxisAlignedBB intersect(AxisAlignedBB a, AxisAlignedBB b) {
        /* Test invoked first since it is a very inexpensive way of checking for missing intersection */
		if (!test(a, b)) return null;

		return AxisAlignedBB.fromPoints(
				Math.max(a.getMinX(), b.getMinX()),
				Math.max(a.getMinY(), b.getMinY()),
				Math.max(a.getMinZ(), b.getMinZ()),
				Math.min(a.getMaxX(), b.getMaxX()),
				Math.min(a.getMaxY(), b.getMaxY()),
				Math.min(a.getMaxZ(), b.getMaxZ())
				);
	}

    /**
     * Returns the intersection of two {@link Plane}s in the form of a {@link Ray}. There is three possible scenarios:
     * <ul>
     *     <li>the planes are parallel thus there is no intersection, the result is null</li>
     *     <li>the planes are identical, the intersection is a plane, the result is null</li>
     *     <li>the planes intersect, the result is a line (ray)</li>
     * </ul>
     *
     * @param a the first plane
     * @param b the second plane
     * @return the intersection of the planes or null
     */
	public static Ray intersect(Plane a, Plane b) {
        Vector
                ac = a.getCenter(),
                bc = b.getCenter(),
                an = a.getNormal(),
                bn = b.getNormal(),
                direction = an.cross(bn),
                ldir = bn.cross(direction);

        float denominator = an.dot(ldir);
        if (Math.abs(denominator) > Spatium.EPSILON) {
            float t = an.dot( ac.subtract(bc) ) / denominator;
            /* notice the mutation of bc and ldir, which does not matter though since the result is being returned at
            this point */
            Vector origin = bc.add(ldir.multiply(t));
            return Ray.fromOriginAndDirection(origin, direction);
        }
        else return null;
    }

    /*
        linePoint = Vector3.zero;
		lineVec = Vector3.zero;

		//We can get the direction of the line of intersection of the two planes by calculating the
		//cross product of the normals of the two planes. Note that this is just a direction and the line
		//is not fixed in space yet. We need a point for that to go with the line vector.
		lineVec = Vector3.Cross(plane1Normal, plane2Normal);

		//Next is to calculate a point on the line to fix it's position in space. This is done by finding a vector from
		//the plane2 location, moving parallel to it's plane, and intersecting plane1. To prevent rounding
		//errors, this vector also has to be perpendicular to lineDirection. To get this vector, calculate
		//the cross product of the normal of plane2 and the lineDirection.
		Vector3 ldir = Vector3.Cross(plane2Normal, lineVec);

		float denominator = Vector3.Dot(plane1Normal, ldir);

		//Prevent divide by zero and rounding errors by requiring about 5 degrees angle between the planes.
		if(Mathf.Abs(denominator) > 0.006f){

			Vector3 plane1ToPlane2 = plane1Position - plane2Position;
			float t = Vector3.Dot(plane1Normal, plane1ToPlane2) / denominator;
			linePoint = plane2Position + t * ldir;

			return true;
		}

		//output not valid
		else{
			return false;
		}
     */

}
