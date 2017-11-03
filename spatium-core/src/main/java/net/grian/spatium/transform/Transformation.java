package net.grian.spatium.transform;

import net.grian.spatium.geo3.Triangle3;
import net.grian.spatium.geo3.Vector3;

@FunctionalInterface
public interface Transformation {

    /**
     * Applies the transformation to a point.
     *
     * @param point the point
     */
    public void transform(Vector3 point);
    
    /**
     * Applies the transformation to a triangle.
     *
     * @param triangle the triangle
     */
    public default void transform(Triangle3 triangle) {
        triangle.transform(this);
    }

    /**
     * Returns a new transformation which is a composition of the given transformation and this one. The new
     * transformation will first apply this transformation and then the current given one.
     *
     * @param transform the transformation to apply before this one
     * @return a new composed transformation
     */
    public default Transformation andThen(Transformation transform) {
        return (point) -> {this.transform(point); transform.transform(point);};
    }

    /**
     * Returns a new transformation which is a composition of the current and the given transformation. The new
     * transformation will first apply the given transformation and then the current one.
     *
     * @param transform the transformation to apply before this one
     * @return a new composed transformation
     */
    public default Transformation compose(Transformation transform) {
        return (point) -> {transform.transform(point); this.transform(point);};
    }

}
