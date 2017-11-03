package net.grian.spatium.coll;

import net.grian.spatium.geo3.Ray3;

import java.util.Collection;

/**
 * An engine which performs collision detection operations such as testing for collisions or casting rays.
 */
public interface CollisionEngine {

    public abstract <C, T> CollisionResult test(C collider, T target);

    public abstract <T> RayCollision<T> rayCast(Ray3 ray, T target);

    public default <C, T> Collision test(C collider, Collection<T> targets) {
        if (targets.isEmpty())
            throw new IllegalArgumentException("at least one target required");

        for (T target : targets) {
            CollisionResult result = test(collider, target);
            switch (result) {
                case NEGATIVE: continue;
                case UNKNOWN_TARGET: continue;
                case POSITIVE: return new Collision<>(result, collider, target);
                case UNKNOWN_COLLIDER: return new Collision<>(result, collider, target);
                default: throw new IllegalStateException("collision has no result");
            }
        }

        return new Collision<>(CollisionResult.NEGATIVE, collider, targets);
    }

    public default <T> RayCollision<T> rayCast(Ray3 ray, Collection<T> targets) {
        if (targets.isEmpty())
            throw new IllegalArgumentException("at least one target required");

        float closestPoint = Float.POSITIVE_INFINITY;
        RayCollision<T> closest = null;

        for (T target : targets) {
            RayCollision<T> collision = rayCast(ray, target);
            switch (collision.getResult()) {
                case NEGATIVE: continue;
                case UNKNOWN_TARGET: continue;
                case UNKNOWN_COLLIDER: return collision;
                case POSITIVE:
                    float point = collision.getPoint();
                    if (point < closestPoint) {
                        closestPoint = point;
                        closest = collision;
                    }
                    break;
                default: throw new IllegalStateException("collision has no result");
            }
        }

        return closest==null? new RayCollision<>(CollisionResult.NEGATIVE, ray, null, Float.NaN) : closest;
    }

    public static enum CollisionResult {
        /** Returned when the collider is not recognized by the engine */
        UNKNOWN_COLLIDER,

        /** Returned when the collider is, but the target is not recognized by the engine */
        UNKNOWN_TARGET,

        /** Returned when no collision occurs */
        NEGATIVE,

        /** Returned when a collision occurs */
        POSITIVE
    }

}
