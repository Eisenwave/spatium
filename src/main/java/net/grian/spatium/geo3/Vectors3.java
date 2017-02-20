package net.grian.spatium.geo3;

import org.jetbrains.annotations.Contract;

public final class Vectors3 {
    
    private Vectors3() {}
    
    public static Vector3 random(double length) {
        double
            x = Math.random(),
            y = Math.random(),
            z = Math.random(),
            multi = length / Math.sqrt(x*x + y*y + z*z);
        
        return Vector3.fromXYZ(x*multi, y*multi, z*multi);
    }
    
    /**
     * Returns the sum of two vectors.
     *
     * @param a the first vector
     * @param b the second vector
     * @return the sum of vectors
     * @throws IllegalArgumentException if the array is empty
     */
    @Contract(value = "_, _ -> !null", pure = true)
    public static Vector3 sum(Vector3 a, Vector3 b) {
        return Vector3.fromXYZ(a.getX()+b.getX(), a.getY()+b.getY(), a.getZ()+b.getZ());
    }
    
    /**
     * Returns the sum of all the vectors's coordinates.
     *
     * @param vectors the vectors
     * @return the sum of vectors
     * @throws IllegalArgumentException if the array is empty
     */
    public static Vector3 sum(Vector3... vectors) {
        if (vectors.length == 0) throw new IllegalArgumentException("no vectors given");
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
