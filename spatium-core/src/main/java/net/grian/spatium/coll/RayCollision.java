package net.grian.spatium.coll;

import net.grian.spatium.coll.CollisionEngine.CollisionResult;
import net.grian.spatium.geo3.Ray3;

/**
 * A generic collision between a ray and a single target at a particular point.
 *
 * @param <T> the target type
 */
public class RayCollision<T> extends Collision<Ray3, T> {

    private final float point;

    public RayCollision(CollisionResult result, Ray3 ray, T target, float point) {
        super(result, ray, target);
        this.point = point;
    }

    /**
     * The point on the ray at which the collision occurs.
     *
     * @return the collision point
     */
    public float getPoint() {
        return point;
    }

}
