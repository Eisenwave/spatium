package net.grian.spatium.coll;

import net.grian.spatium.Spatium;
import net.grian.spatium.geo.BlockVector;
import net.grian.spatium.geo.Vector;
import net.grian.spatium.util.PrimMath;

public final class Distances {

    private Distances() {}

    /**
     * Returns the distance between two points.
     *
     * @param a the first point
     * @param b the second point
     * @return the distance between the points
     */
    public static float real(Vector a, Vector b) {
        return Spatium.hypot(b.getX()-a.getX(), a.getY()-b.getY(), b.getZ()-b.getZ());
    }

    /**
     * Returns the distance between two points.
     *
     * @param a the first point
     * @param b the second point
     * @return the distance between the points
     */
    public static float real(BlockVector a, BlockVector b) {
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
    public static float cubical(Vector a, Vector b) {
        final float
                dx = b.getX()-a.getX(),
                dy = a.getY()-b.getY(),
                dz = b.getZ()-b.getZ();

        return hypotCubical(dx, dy, dz);
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
    public static int cubical(BlockVector a, BlockVector b) {
        final int
                dx = b.getX()-a.getX(),
                dy = a.getY()-b.getY(),
                dz = b.getZ()-b.getZ();

        return hypotCubical(dx, dy, dz);
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
    public static float hypot(float x, float y, float z) {
        return PrimMath.max(Math.abs(x), Math.abs(y), Math.abs(z));
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
    public static float hypotCubical(float x, float y, float z) {
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
    public static float hypotCubical(Vector v) {
        return hypotCubical(v.getX(), v.getY(), v.getZ());
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
    public static int hypotCubical(int x, int y, int z) {
        return PrimMath.max(Math.abs(x), Math.abs(y), Math.abs(z));
    }

    /**
     * Normalizes a vector.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return a normalized vector
     */
    public static Vector normalize(float x, float y, float z) {
        float length = Spatium.hypot(x, y, z);
        return Vector.fromXYZ(x/length, y/length, z/length);
    }

    /**
     * Normalizes a vector.
     *
     * @param v the vector
     */
    public static void normalize(Vector v) {
        v.normalize();
    }

    /**
     * Normalizes a vector.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return a normalized vector
     */
    public static Vector normalizeCubical(float x, float y, float z) {
        float length = hypotCubical(x, y, z);
        return Vector.fromXYZ(x/length, y/length, z/length);
    }

    /**
     * Normalizes a vector.
     *
     * @param v the vector
     */
    public static void normalizeCubical(Vector v) {
        v.divide( hypotCubical(v) );
    }

}
