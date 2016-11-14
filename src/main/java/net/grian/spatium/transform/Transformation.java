package net.grian.spatium.transform;

import net.grian.spatium.geo.Vector;

@FunctionalInterface
public interface Transformation {

    /**
     * Applies the transformation to a point.
     *
     * @param point the point
     */
    public void transform(Vector point);

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
