package net.grian.spatium.coll;

import net.grian.spatium.Spatium;
import net.grian.spatium.geo.*;

public class Collisions {
	
	private Collisions() {}
	
	/**
	 * Tests whether two points collide. This is equivalent to testing whether
	 * the two points are in the same position.
	 * 
	 * @param a the first point
	 * @param b the second point
	 * @return whether the points collide
	 */
	public static boolean test(Vector a, Vector b) {
		return a.equals(b);
	}
	
	/**
	 * Tests whether two {@link Sphere}s collide/intersect.
	 * @param a the first sphere
	 * @param b the second sphere
	 * @return whether the spheres collide/intersect.
	 */
	public static boolean test(Sphere a, Sphere b) {
		return a.getCenter().distanceTo(b.getCenter()) <= a.getRadius() + b.getRadius();
	}
	
	/**
	 * Tests whether two {@link AxisAlignedBB}s collide/intersect.
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
	 * @param ray the ray
	 * @param point the point
	 * @return whether the box and the point collide
	 */
	public static boolean test(Ray ray, Vector point) {
		return ray.contains(point);
	}
	
	/**
	 * Tests whether a {@link Ray} and a {@link AxisAlignedBB} collide.
	 * @param ray the ray
	 * @param box the bounding box
	 * @return whether the ray and the box collide
	 */
	public static boolean test(Ray ray, AxisAlignedBB box) {
		return rayCast(ray, box) != Float.NaN;
	}
	
	/**
	 * Tests whether a {@link Sphere} and a Point collide.
	 * @param sphere the sphere
	 * @param point the point
	 * @return whether the sphere and the point collide
	 */
	public static boolean test(Sphere sphere, Vector point) {
		return sphere.contains(point);
	}
	
	/**
	 * Tests whether an {@link AxisAlignedBB} and a Point collide.
	 * @param box the bounding box
	 * @param point the point
	 * @return whether the box and the point collide
	 */
	public static boolean test(AxisAlignedBB box, Vector point) {
		return box.contains(point);
	}
	
	/**
	 * Tests whether an {@link AxisAlignedTSBP} and a Point collide.
	 * @param plane the plane
	 * @param point the point
	 * @return whether the box and the point collide
	 */
	public static boolean test(AxisAlignedTSBP plane, Vector point) {
		return plane.contains(point);
	}
	
	public static float rayCast(Ray ray, Collideable c) throws UnsupportedOperationException {
		if (c instanceof Ray)
			return rayCast(ray, (Ray) c);
		else if (c instanceof Ray)
			return rayCast(ray, (Ray) c);
		else if (c instanceof Sphere)
			return rayCast(ray, (Sphere) c);
		else if (c instanceof AxisAlignedBB)
			return rayCast(ray, (AxisAlignedBB) c);
		
		else
			throw new UnsupportedOperationException("ray can not collide with "+c.getClass().getName());
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
        		dist = (float) Math.sqrt(radius * radius + lengthCenterBase * lengthOriginBase),
        		di1 = dist - lengthOriginBase;

            	return di1;
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
		if (tzmax < tmax) tmax = tzmax;
		
		return tmin;
	}
	
	/**
	 * Returns the intersection of two {@link AxisAlignedBB}s.
	 * 
	 * @param a the first bounding box
	 * @param b the second bounding box
	 * @return the intersection of the boxes.
	 */
	public static AxisAlignedBB intersect(AxisAlignedBB a, AxisAlignedBB b) {
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

}
