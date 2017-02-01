package net.grian.spatium.coll;

import net.grian.spatium.geo3.Ray3;
import net.grian.spatium.geo3.Vector3;

public class DefaultCollisionEngine implements CollisionEngine {

    @Override
    public <C, T> CollisionResult test(C collider, T target) {
        if (collider instanceof Vector3)
            return testPoint((Vector3) collider, target);
        else if (collider instanceof Ray3)
            return testRay((Ray3) collider, target);
        return CollisionResult.UNKNOWN_COLLIDER;
    }

    protected CollisionResult testPoint(Vector3 point, Object target) {
        if (target instanceof Vector3) {
            return point.equals(target)? CollisionResult.POSITIVE : CollisionResult.NEGATIVE;
        }
        return CollisionResult.UNKNOWN_TARGET;
    }

    protected CollisionResult testRay(Ray3 ray, Object target) {
        if (target instanceof Vector3) {
            return Collisions.test(ray, (Vector3) target)? CollisionResult.POSITIVE : CollisionResult.NEGATIVE;
        }
        return CollisionResult.UNKNOWN_TARGET;
    }

    @Override
    public <T> RayCollision<T> rayCast(Ray3 ray, T target) {
        return null;
    }
}
