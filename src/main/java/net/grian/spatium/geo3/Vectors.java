package net.grian.spatium.geo3;

import net.grian.spatium.Spatium;
import net.grian.spatium.geo2.Rectangle;
import net.grian.spatium.geo2.Vector2;
import net.grian.spatium.util.PrimMath;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class Vectors {
    
    private Vectors() {}
    
    public static boolean multiples(double ax, double ay, double bx, double by) {
        return
            Spatium.isZero(ax) && Spatium.isZero(bx) ||
            Spatium.isZero(ay) && Spatium.isZero(by) ||
            Spatium.equals(ax/bx, ay/by);
    }
    
    public static boolean multiples(Vector2 a, Vector2 b) {
        return multiples(a.getX(), a.getY(), b.getX(), b.getY());
    }
    
    @NotNull
    public static Vector2 clamp(double x, double y, Rectangle bounds) {
        return Vector2.fromXY(
            PrimMath.clamp(bounds.getMinX(), x, bounds.getMaxX()),
            PrimMath.clamp(bounds.getMinY(), y, bounds.getMaxY()));
    }
    
    @NotNull
    public static Vector2 clamp(Vector2 point, Rectangle bounds) {
        return clamp(point.getX(), point.getY(), bounds);
    }
    
    @NotNull
    public static Vector3 clamp(double x, double y, double z, AxisAlignedBB bounds) {
        return Vector3.fromXYZ(
            PrimMath.clamp(bounds.getMinX(), x, bounds.getMaxX()),
            PrimMath.clamp(bounds.getMinY(), y, bounds.getMaxY()),
            PrimMath.clamp(bounds.getMinZ(), z, bounds.getMaxZ()));
    }
    
    @NotNull
    public static Vector3 clamp(Vector3 point, AxisAlignedBB bounds) {
        return clamp(point.getX(), point.getY(), point.getZ(), bounds);
    }
    
    private static VectorRandom RVG;
    
    @NotNull
    private static VectorRandom initRVG() {
        return RVG==null? RVG=new VectorRandom() : RVG;
    }
    
    /**
     * Returns a vector with given length and random direction.
     *
     * @param length the length of the vector
     * @return a new random vector
     */
    @NotNull
    public static Vector2 random2(double length) {
        return initRVG().nextVector2(length);
    }
    
    /**
     * Returns a vector with given length and random direction.
     *
     * @param length the length of the vector
     * @return a new random vector
     */
    @NotNull
    public static Vector3 random3(double length) {
        return initRVG().nextVector3(length);
    }
    
    /**
     * Returns the sum of two vectors.
     *
     * @param a the first vector
     * @param b the second vector
     * @return the sum of vectors
     * @throws IllegalArgumentException if the array is empty
     */
    @NotNull
    public static Vector2 sum(Vector2 a, Vector2 b) {
        return Vector2.fromXY(a.getX()+b.getX(), a.getY()+b.getY());
    }
    
    /**
     * Returns the sum of two vectors.
     *
     * @param a the first vector
     * @param b the second vector
     * @return the sum of vectors
     * @throws IllegalArgumentException if the array is empty
     */
    @NotNull
    public static Vector3 sum(Vector3 a, Vector3 b) {
        return Vector3.fromXYZ(a.getX()+b.getX(), a.getY()+b.getY(), a.getZ()+b.getZ());
    }
    
    /**
     * Returns the sum of all the vectors's coordinates.
     *
     * @param vectors the vectors
     * @return the sum of vectors or {@link Vector2#zero()}
     */
    public static Vector2 sum(Vector2... vectors) {
        if (vectors.length == 0) return Vector2.zero();
        if (vectors.length == 1) return vectors[0].clone();
        if (vectors.length == 2) return sum(vectors[0], vectors[1]);
        
        double x = 0, y = 0;
        for (Vector2 v : vectors) {
            x += v.getX();
            y += v.getY();
        }
        
        return Vector2.fromXY(x, y);
    }
    
    /**
     * Returns the sum of all the vectors's coordinates.
     *
     * @param vectors the vectors
     * @return the sum of vector or {@link Vector3#zero()}
     */
    public static Vector3 sum(Vector3... vectors) {
        if (vectors.length == 0) return Vector3.zero();
        if (vectors.length == 1) return vectors[0].clone();
        if (vectors.length == 2) return sum(vectors[0], vectors[1]);
        
        double x = 0, y = 0, z = 0;
        for (Vector3 v : vectors) {
            x += v.getX();
            y += v.getY();
            z += v.getZ();
        }
        
        return Vector3.fromXYZ(x, y, z);
    }
    
    /**
     * Returns the average of all the vector's coordinates.
     *
     * @param vectors the vectors
     * @return average vector
     * @throws IllegalArgumentException if the array is empty
     */
    public static Vector3 average(Vector3... vectors) {
        return sum(vectors).divide(vectors.length);
    }
    
    /**
     * Orthogonalizes a set of vectors using the
     * <a href="https://en.wikipedia.org/wiki/Gram%E2%80%93Schmidt_process">Gram-Schmidt</a> orthogonalization process.
     *
     * @param vectors the array of vectors
     * @return the orthogonalized vectors
     */
    public static Vector3[] orthogonalize(Vector3... vectors) {
        if (vectors.length == 1) return new Vector3[] {vectors[0].clone()};
        
        Vector3[] result = new Vector3[vectors.length];
        
        for (int i = 0; i<vectors.length; i++) {
            Vector3 u = vectors[i];
            Vector3 bi = result[i] = u.clone();
            
            for (int j = 0; j < i; j++) {
                System.out.println(i+", "+j);
                Vector3 bj = result[j].clone();
                double scale = u.dot(bj) / bj.dot(bj);
                bi.subtract(bj.multiply(scale));
            }
        }
        
        return result;
    }
    
    /**
     * Orthonormalizes a set of vectors using the
     * <a href="https://en.wikipedia.org/wiki/Gram%E2%80%93Schmidt_process">Gram-Schmidt</a> orthogonalization process.
     *
     * @param vectors the array of vectors
     * @return the orthonormalized vectors
     */
    public static Vector3[] orthonormalize(Vector3... vectors) {
        Vector3[] result = orthogonalize(vectors);
        for (Vector3 b : result)
            b.normalize();
        return result;
    }
    
}
