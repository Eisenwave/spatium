package net.grian.spatium.coll;

import net.grian.spatium.geo.Ray;

public class RayPierceCollision<T> extends Collision<Ray, T> {

    private final float entry, exit;

    public RayPierceCollision(CollisionEngine.CollisionResult result, Ray ray, T target, float entry, float exit) {
        super(result, ray, target);
        this.entry = entry;
        this.exit = exit;
    }

    public RayPierceCollision(Ray ray, T target) {
        this(CollisionEngine.CollisionResult.NEGATIVE, ray, target, Float.NaN, Float.NaN);
    }

    /**
     * The point on the ray at which the collision occurs.
     *
     * @return the ray entry point
     */
    public float getEntryPoint() {
        return entry;
    }

    /**
     * The point on the ray at which the collision occurs.
     *
     * @return the ray exit point
     */
    public float getExitPoint() {
        return exit;
    }

}
