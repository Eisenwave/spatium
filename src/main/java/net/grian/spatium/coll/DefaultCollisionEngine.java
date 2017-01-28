package net.grian.spatium.coll;

import net.grian.spatium.geo.Ray;
import net.grian.spatium.geo.Vector;

public class DefaultCollisionEngine implements CollisionEngine {

    @Override
    public <C, T> CollisionResult test(C collider, T target) {
        if (collider instanceof Vector)
            return testPoint((Vector) collider, target);
        else if (collider instanceof Ray)
            return testRay((Ray) collider, target);
        return CollisionResult.UNKNOWN_COLLIDER;
    }

    protected CollisionResult testPoint(Vector point, Object target) {
        if (target instanceof Vector) {
            return point.equals(target)? CollisionResult.POSITIVE : CollisionResult.NEGATIVE;
        }
        return CollisionResult.UNKNOWN_TARGET;
    }

    protected CollisionResult testRay(Ray ray, Object target) {
        if (target instanceof Vector) {
            return Collisions.test(ray, (Vector) target)? CollisionResult.POSITIVE : CollisionResult.NEGATIVE;
        }
        return CollisionResult.UNKNOWN_TARGET;
    }

    @Override
    public <T> RayCollision<T> rayCast(Ray ray, T target) {
        return null;
    }
}
