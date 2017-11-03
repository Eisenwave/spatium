package net.grian.spatium.coll;

import net.grian.spatium.coll.CollisionEngine.CollisionResult;

/**
 * A generic collision between a collider and a target.
 *
 * @param <C> the collider type
 * @param <T> the target type
 */
public class Collision<C, T> {

    private final C a;
    private final T b;
    private final CollisionResult result;

    public Collision(CollisionResult result, C a, T b) {
        this.result = result;
        this.a = a;
        this.b = b;
    }

    /**
     * Returns the result of the collision.
     *
     * @return the collision result
     */
    public CollisionResult getResult() {
        return result;
    }

    /**
     * Returns the collider involved in the collision.
     *
     * @return the collider
     */
    public C getCollider() {
        return a;
    }

    /**
     * Returns the target involved in the collisions.
     *
     * @return the target
     */
    public T getTarget() {
        return b;
    }

}
