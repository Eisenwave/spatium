package net.grian.spatium.coll;

import net.grian.spatium.util.Spatium;
import net.grian.spatium.geo3.BlockVector;
import net.grian.spatium.geo3.Vector3;
import net.grian.spatium.util.PrimMath;
import org.jetbrains.annotations.Contract;

public final class Distances {

    private Distances() {}

    /**
     * Returns the distance between two points.
     *
     * @param a the first point
     * @param b the second point
     * @return the distance between the points
     */
    @Contract(pure = true)
    public static double real(Vector3 a, Vector3 b) {
        return a.distanceTo(b);
    }

    /**
     * Returns the distance between two points.
     *
     * @param a the first point
     * @param b the second point
     * @return the distance between the points
     */
    @Contract(pure = true)
    public static double real(BlockVector a, BlockVector b) {
        return Spatium.hypot(b.getX()-a.getX(), a.getY()-b.getY(), b.getZ()-b.getZ());
    }

    /**
     * Returns the cubical distance between two given points, defined as:
     * <blockquote>
     *     <code>max( abs(x<sub>a</sub> - x<sub>b</sub>), abs(y<sub>a</sub> - y<sub>b</sub>),
     *     abs(z<sub>a</sub> - z<sub>b</sub>) )</code>
     * </blockquote>
     *
     * @param a the first point
     * @param b the second point
     * @return the cubical distance between the points
     */
    @Contract(pure = true)
    public static double cubical(Vector3 a, Vector3 b) {
        return maxAbs(
            b.getX()-a.getX(),
            a.getY()-b.getY(),
            b.getZ()-b.getZ());
    }

    /**
     * Returns the cubical distance between two given points, defined as:
     * <blockquote>
     *     <code>max( abs(x<sub>a</sub> - x<sub>b</sub>), abs(y<sub>a</sub> - y<sub>b</sub>),
     *     abs(z<sub>a</sub> - z<sub>b</sub>) )</code>
     * </blockquote>
     *
     * @param a the first point
     * @param b the second point
     * @return the cubical distance between the points
     */
    @Contract(pure = true)
    public static int cubical(BlockVector a, BlockVector b) {
        return maxAbs(
            b.getX()-a.getX(),
            b.getY()-a.getY(),
            b.getZ()-b.getZ());
    }

    /**
     * Returns the cubical hypotenuse length of three coordinates:
     * <blockquote>
     *     <code>max( abs(x), abs(y), abs(z) ),
     *     abs(z<sub>a</sub> - z<sub>b</sub>) )</code>
     * </blockquote>
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return the cubical hypotenuse length
     */
    @Contract(pure = true)
    public static double maxAbs(double x, double y, double z) {
        return PrimMath.max(Math.abs(x), Math.abs(y), Math.abs(z));
    }

    /**
     * Returns the cubical hypotenuse length of three coordinates:
     * <blockquote>
     *     <code>max( abs(x), abs(y), abs(z) ),
     *     abs(z<sub>a</sub> - z<sub>b</sub>) )</code>
     * </blockquote>
     *
     * @param v the vector
     * @return the cubical hypotenuse length
     */
    @Contract(pure = true)
    public static double maxAbs(Vector3 v) {
        return maxAbs(v.getX(), v.getY(), v.getZ());
    }

    /**
     * Returns the cubical hypotenuse length of three coordinates:
     * <blockquote>
     *     <code>max( abs(x), abs(y), abs(z) ),
     *     abs(z<sub>a</sub> - z<sub>b</sub>) )</code>
     * </blockquote>
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return the cubical hypotenuse length
     */
    @Contract(pure = true)
    public static int maxAbs(int x, int y, int z) {
        return PrimMath.max(Math.abs(x), Math.abs(y), Math.abs(z));
    }

}
