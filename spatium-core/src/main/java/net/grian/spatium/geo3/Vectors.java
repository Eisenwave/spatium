package net.grian.spatium.geo3;

import eisenwave.spatium.util.Spatium;
import eisenwave.spatium.cache.CacheMath;
import net.grian.spatium.geo2.Rectangle;
import net.grian.spatium.geo2.Vector2;
import eisenwave.spatium.util.PrimMath;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class Vectors {
    
    private Vectors() {}
    
    /**
     * Returns the average of all the vector's coordinates.
     *
     * @param vectors the vectors
     * @return average vector
     * @throws IllegalArgumentException if the array is empty
     */
    public static Vector2 average(Vector2... vectors) {
        return sum(vectors).divide(vectors.length);
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
    
    // VECTOR PRODUCTS
    
    @Contract(pure = true)
    public static double dot(double ax, double ay, double bx, double by) {
        return ax*bx + ay*by;
    }
    
    @Contract(pure = true)
    public static double dot(double ax, double ay, double az, double bx, double by, double bz) {
        return ax*bx + ay*by + az*bz;
    }
    
    @NotNull
    @Contract(pure = true)
    public static Vector3 cross(double ax, double ay, double az, double bx, double by, double bz) {
        return Vector3.fromXYZ(
            ay*bz - az*by,
            az*bx - ax*bz,
            ax*by - ay*bx);
    }
    
    @Contract(pure = true)
    public static double triple(double ax, double ay, double az,
                                double bx, double by, double bz,
                                double cx, double cy, double cz) {
        return dot(cx, cy, cz,
            ay*bz - az*by,
            az*bx - ax*bz,
            ax*by - ay*bx);
    }
    
    @Contract(pure = true)
    public static double triple(Vector3 a, Vector3 b, Vector3 c) {
        return a.dot(b.cross(c));
    }
    
    // MIN & MAX
    
    @NotNull
    public static Vector2 min(Vector2 a, Vector2 b) {
        return Vector2.fromXY(
            Math.min(a.getX(), b.getX()),
            Math.min(a.getY(), b.getY()));
    }
    
    @NotNull
    public static Vector3 min(Vector3 a, Vector3 b) {
        return Vector3.fromXYZ(
            Math.min(a.getX(), b.getX()),
            Math.min(a.getY(), b.getY()),
            Math.min(a.getZ(), b.getZ()));
    }
    
    @NotNull
    public static Vector2 max(Vector2 a, Vector2 b) {
        return Vector2.fromXY(
            Math.max(a.getX(), b.getX()),
            Math.max(a.getY(), b.getY()));
    }
    
    @NotNull
    public static Vector3 max(Vector3 a, Vector3 b) {
        return Vector3.fromXYZ(
            Math.max(a.getX(), b.getX()),
            Math.max(a.getY(), b.getY()),
            Math.max(a.getZ(), b.getZ()));
    }
    
    @NotNull
    public static Vector3 min(Vector3... v) {
        if (v.length == 0) throw new IllegalArgumentException("no vectors given");
        if (v.length == 1) return v[0].clone();
        if (v.length == 2) return min(v[0], v[1]);
        
        double[] min = v[0].toArray();
        for (int i = 1; i < v.length; i++) {
            double[] vec = v[i].toArray();
            for (int j = 0; j < vec.length; j++)
                if (vec[j] < min[j]) min[j] = vec[j];
        }
        
        return Vector3.fromXYZ(min[0], min[1], min[2]);
    }
    
    @NotNull
    public static Vector3 max(Vector3... v) {
        if (v.length == 0) throw new IllegalArgumentException("no vectors given");
        if (v.length == 1) return v[0].clone();
        if (v.length == 2) return min(v[0], v[1]);
        
        double[] max = v[0].toArray();
        for (int i = 1; i < v.length; i++) {
            double[] vec = v[i].toArray();
            for (int j = 0; j < vec.length; j++)
                if (vec[j] > max[j]) max[j] = vec[j];
        }
        
        return Vector3.fromXYZ(max[0], max[1], max[2]);
    }
    
    @NotNull
    public static Vector2[] minMax(Vector2 a, Vector2 b) {
        final double
            ax = a.getX(), ay = a.getY(),
            bx = a.getX(), by = b.getY();
        return new Vector2[] {
            Vector2.fromXY(Math.min(ax, bx), Math.min(ay, by)),
            Vector2.fromXY(Math.max(ax, bx), Math.max(ay, by))};
    }
    
    @NotNull
    public static Vector3[] minMax(Vector3 a, Vector3 b) {
        final double
            ax = a.getX(), ay = a.getY(), az = a.getZ(),
            bx = a.getX(), by = b.getY(), bz = b.getZ();
        return new Vector3[] {
            Vector3.fromXYZ(Math.min(ax, bx), Math.min(ay, by), Math.min(az, bz)),
            Vector3.fromXYZ(Math.max(ax, bx), Math.max(ay, by), Math.min(az, bz))};
    }
    
    @SuppressWarnings("Duplicates")
    @NotNull
    public static Vector2[] minMax(Vector2... v) {
        if (v.length == 0) throw new IllegalArgumentException("no vectors given");
        if (v.length == 1) return new Vector2[] {v[0].clone(), v[0].clone()};
        if (v.length == 2) return minMax(v[0], v[1]);
        
        double[]
            min = v[0].toArray(),
            max = {min[0], min[1]};
        for (int i = 1; i < v.length; i++) {
            double[] vec = v[i].toArray();
            for (int j = 0; j < vec.length; j++) {
                double c = vec[j];
                if (c < min[j]) min[j] = c;
                if (c > max[j]) max[j] = c;
            }
        }
        
        return new Vector2[] {
            Vector2.fromXY(min[0], min[1]),
            Vector2.fromXY(max[0], max[1])
        };
    }
    
    @SuppressWarnings("Duplicates")
    @NotNull
    public static Vector3[] minMax(Vector3... v) {
        if (v.length == 0) throw new IllegalArgumentException("no vectors given");
        if (v.length == 1) return new Vector3[] {v[0].clone(), v[0].clone()};
        if (v.length == 2) return minMax(v[0], v[1]);
    
        double[]
            min = v[0].toArray(),
            max = {min[0], min[1], min[2]};
        for (int i = 1; i < v.length; i++) {
            double[] vec = v[i].toArray();
            for (int j = 0; j < vec.length; j++) {
                double c = vec[j];
                if (c < min[j]) min[j] = c;
                if (c > max[j]) max[j] = c;
            }
        }
        
        return new Vector3[] {
            Vector3.fromXYZ(min[0], min[1], min[2]),
            Vector3.fromXYZ(max[0], max[1], max[2])
        };
    }
    
    // MULTIPLES (PARALLELISM) CHECK
    
    /**
     * Returns whether the vectors <ul>
     *     <li><code>a := (ax, ay)</code></li>
     *     <li><code>b := (bx, by)</code></li>
     * </ul>
     * are multiplies of each other, meaning that <code>a = xb</code>.
     *
     * @param ax a's x-coordinate
     * @param ay a's y-coordinate
     * @param bx b's x-coordinate
     * @param by b's y-coordinate
     * @return whether the vectors are multiples of each other
     */
    @Contract(pure = true)
    public static boolean multiples(double ax, double ay, double bx, double by) {
        return
            Spatium.isZero(ax) && Spatium.isZero(bx) ||
            Spatium.isZero(ay) && Spatium.isZero(by) ||
            Spatium.equals(ax/bx, ay/by);
    }
    
    /**
     * Returns whether the vectors <ul>
     *     <li><code>a := (ax, ay, az)</code></li>
     *     <li><code>b := (bx, by, bz)</code></li>
     * </ul>
     * are multiplies of each other, meaning that <code>a = xb</code>.
     *
     * @param ax a's x-coordinate
     * @param ay a's y-coordinate
     * @param az a's z-coordinate
     * @param bx b's x-coordinate
     * @param by b's y-coordinate
     * @param bz b's z-coordinate
     * @return whether the vectors are multiples of each other
     */
    @Contract(pure = true)
    public static boolean multiples(double ax, double ay, double az, double bx, double by, double bz) {
        //the cross product of two vectors is zero if the vectors are parallel to each other
        //(the magnitude of the cross product is the area of the parallelogram between the vectors)
        return cross(ax, ay, az, bx, by, bz).isZero();
    }
    
    @Contract(pure = true)
    public static boolean multiples(Vector2 a, Vector2 b) {
        return multiples(a.getX(), a.getY(), b.getX(), b.getY());
    }
    
    @Contract(pure = true)
    public static boolean multiples(Vector3 a, Vector3 b) {
        return a.cross(b).isZero();
    }
    
    // RADIUS, YAW, PITCH TO X, Y, Z
    
    /**
     * Converts radius, yaw and pitch into x, y and z.
     *
     * @param r the radius
     * @param yaw the yaw in radians
     * @param pitch the pitch in radians
     * @return a new vector
     */
    @NotNull
    public static Vector3 rypToXYZ(double r, double yaw, double pitch) {
        //handle singularity at pitch = 90 degrees (vector pointing straight up)
        if (Spatium.equals(Math.abs(pitch), CacheMath.HALF_PI)) {
            return Vector3.fromXYZ(
                0,
                pitch >= 0? -r : r,
                0);
        } else {
            final double cos = Math.cos(pitch),
                x = Math.sin(-yaw) * cos * r,
                z = Math.cos( yaw) * cos * r,
                y = -Math.tan(pitch) * Math.sqrt(x*x + z*z);
            
            return Vector3.fromXYZ(x, y, z);
        }
    }
    
    // PROJECTIONS
    
    /**
     * <p>
     *     Returns the orthogonal projection of a vector <code>a = (ax, ay)</code> onto the basis
     *     <code>b = (bx, by)</code>.
     * </p>
     * The projection of a vector <b>a</b> onto a basis <b>b</b> is defined as:
     * <blockquote>
     *     <code>project(a,b) = ((a*b) / b*b) * b</code>
     * </blockquote>
     *
     * @param ax the vector x-coordinate
     * @param ay the vector y-coordinate
     * @param bx the basis x-coordinate
     * @param by the basis y-coordinate
     * @return the projection of a onto b
     */
    @NotNull
    public static Vector2 project(double ax, double ay, double bx, double by) {
        final double t = dot(ax, ay, bx, by) / dot(bx, by, bx, by);
        return Vector2.fromXY(t * bx, t* by);
    }
    
    /**
     * <p>
     *     Returns the orthogonal projection of a vector <code>a = (ax, ay, az)</code> onto the basis
     *     <code>b = (bx, by, bz)</code>.
     * </p>
     * The projection of a vector <b>a</b> onto a basis <b>b</b> is defined as:
     * <blockquote>
     *     <code>project(a,b) = ((a*b) / b*b) * b</code>
     * </blockquote>
     *
     * @param ax the vector x-coordinate
     * @param ay the vector y-coordinate
     * @param az the vector z-coordinate
     * @param bx the basis x-coordinate
     * @param by the basis y-coordinate
     * @param bz the basis z-coordinate
     * @return the projection of a onto b
     */
    @NotNull
    public static Vector3 project(double ax, double ay, double az, double bx, double by, double bz) {
        final double t = dot(ax, ay, az, bx, by, bz) / dot(bx, by, bz, bx, by, bz);
        return Vector3.fromXYZ(t*bx, t*by, t*bz);
    }
    
    /**
     * <p>
     *     Returns the orthogonal projection of a vector onto another basis vector.
     * </p>
     * The projection of a vector <b>a</b> onto a basis <b>b</b> is defined as:
     * <blockquote>
     *     <code>project(a,b) = ((a*b) / b*b) * b</code>
     * </blockquote>
     *
     *
     * @param a the vector
     * @param b the basis
     * @return the projection of a onto b
     */
    @NotNull
    public static Vector2 project(Vector2 a, Vector2 b) {
        return b.clone().multiply(a.dot(b) / b.getLengthSquared());
    }
    
    /**
     * Returns the orthogonal projection of a vector onto another basis vector.
     * The projection of a vector <b>a</b> onto a basis <b>b</b> is defined as:
     * <blockquote>
     *     <code>project(a,b) = ((a*b) / b*b) * b</code>
     * </blockquote>
     *
     * @param a the vector
     * @param b the basis
     * @return the projection of a onto b
     */
    @NotNull
    public static Vector3 project(Vector3 a, Vector3 b) {
        return b.clone().multiply(a.dot(b) / b.getLengthSquared());
    }
    
    // RATIO OF VECTORS
    
    /**
     * Returns the ratio a/b between the vectors <ul>
     *     <li><code>a := (ax, ay)</code></li>
     *     <li><code>b := (bx, by)</code></li>
     * </ul>
     * If the vectors are not multiples, {@link Double#NaN} is returned.
     *
     * @param ax a's x-coordinate
     * @param ay a's y-coordinate
     * @param bx b's x-coordinate
     * @param by b's y-coordinate
     * @return the ratio between the vectors or NaN
     */
    @Contract(pure = true)
    public static double ratio(double ax, double ay, double bx, double by) {
        if (Spatium.isZero(ax)) {
            if (!Spatium.isZero(bx))
                return Double.NaN;
            if (Spatium.isZero(ay) && Spatium.isZero(by))
                return 0;
            return ay / by;
        }
        else if (Spatium.isZero(ay)) {
            if (!Spatium.isZero(by))
                return Double.NaN;
            return ax / bx;
        }
    
        final double t = ax/bx;
        return Spatium.equals(t, ay/by)? t : Double.NaN;
    }
    
    /**
     * Returns ratio a/b between the vectors <ul>
     *     <li><code>a := (ax, ay, az)</code></li>
     *     <li><code>b := (bx, by, bz)</code></li>
     * </ul>
     * If the vectors are not multiples, {@link Double#NaN} is returned.
     *
     * @param ax a's x-coordinate
     * @param ay a's y-coordinate
     * @param az a's z-coordinate
     * @param bx b's x-coordinate
     * @param by b's y-coordinate
     * @param bz b's z-coordinate
     * @return whether the vectors are multiples of each other
     */
    @Contract(pure = true)
    public static double ratio(double ax, double ay, double az, double bx, double by, double bz) {
        if (Spatium.isZero(ax))
            return Spatium.isZero(bx)? Vectors.ratio(ay, az, by, bz) : Double.NaN;
    
        else if (Spatium.isZero(ay))
            return Spatium.isZero(by)? Vectors.ratio(ax, az, bx, bz) : Double.NaN;
    
        else if (Spatium.isZero(az))
            return Spatium.isZero(bz)? Vectors.ratio(ax, ay, bx, by) : Double.NaN;
    
        double ratio = ax/bx;
        return Spatium.equals(ratio, ay/by, az/bz)? ratio : Double.NaN;
    }
    
    /**
     * Returns ratio a/b between the vectors <ul>
     *     <li><code>a := (ax, ay, az)</code></li>
     *     <li><code>b := (bx, by, bz)</code></li>
     * </ul>
     * If the vectors are not multiples, {@link Double#NaN} is returned.
     *
     * @param a the first vector
     * @param b the second vector
     * @return whether the vectors are multiples of each other
     */
    @Contract(pure = true)
    public static double ratio(Vector2 a, Vector2 b) {
        return ratio(a.getX(), a.getY(), b.getX(), b.getY());
    }
    
    /**
     * Returns ratio a/b between the vectors <ul>
     *     <li><code>a := (ax, ay, az)</code></li>
     *     <li><code>b := (bx, by, bz)</code></li>
     * </ul>
     * If the vectors are not multiples, {@link Double#NaN} is returned.
     *
     * @param a the first vector
     * @param b the second vector
     * @return whether the vectors are multiples of each other
     */
    @Contract(pure = true)
    public static double ratio(Vector3 a, Vector3 b) {
        return ratio(a.getX(), a.getY(), a.getZ(), b.getX(), b.getY(), b.getZ());
    }
    
    // NORMALIZATION
    
    /**
     * Normalizes a vector.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return a normalized vector
     */
    @NotNull
    public static Vector3 normalize(double x, double y, double z) {
        double length = Spatium.hypot(x, y, z);
        return Vector3.fromXYZ(x/length, y/length, z/length);
    }
    
    /**
     * Normalizes a vector.
     *
     * @param v the vector
     */
    @NotNull
    public static Vector3 normalize(Vector3 v) {
        return v.clone().normalize();
    }
    
    // ORTHOGONALIZATION
    
    /**
     * Orthogonalizes a set of vectors using the
     * <a href="https://en.wikipedia.org/wiki/Gram%E2%80%93Schmidt_process">Gram-Schmidt Process</a>.
     *
     * @param vectors the array of vectors
     */
    public static void orthogonalize(Vector3... vectors) {
        if (vectors.length <= 1) return;
        
        for (int i = 0; i < vectors.length; i++) {
            Vector3 v = vectors[i];
            
            for (int j = 0; j < i; j++) {
                Vector3 b = vectors[j];
                v.subtract(project(v, b));
            }
        }
    }
    
    /**
     * Orthonormalizes a set of vectors using the
     * <a href="https://en.wikipedia.org/wiki/Gram%E2%80%93Schmidt_process">Gram-Schmidt</a> orthogonalization process.
     *
     * @param vectors the array of vectors
     */
    public static void orthonormalize(Vector3... vectors) {
        orthogonalize(vectors);
        for (Vector3 b : vectors)
            b.normalize();
    }
    
    // ORTHOGONAL VECTORS
    
    /**
     * <p>
     *     Returns a clockwise orthogonal vector to a given vector.
     * </p>
     * <p>
     *     This result is the given vector rotated by 90 degrees clockwise which is equivalent to multiplication with
     *     the matrix <code>[[0,1],[-1,0]]</code>.
     * </p>
     *
     * @param v the vector
     * @return a perpendicular vector
     * @see Vector2#getInverse()
     */
    @NotNull
    public static Vector2 orthoClockwise(Vector2 v) {
        return Vector2.fromXY(v.getY(), -v.getX());
    }
    
    /**
     * <p>
     *     Returns a clockwise orthogonal vector to a given vector.
     * </p>
     * <p>
     *     This result is the given vector rotated by 90 degrees counter clockwise clockwise which is equivalent to
     *     multiplication the matrix <code>[[0,-1],[1,0]]</code>.
     * </p>
     *
     * @param v the vector
     * @return a perpendicular vector
     * @see Vector2#getInverse()
     */
    @NotNull
    public static Vector2 orthoCounterClockwise(Vector2 v) {
        return Vector2.fromXY(-v.getY(), v.getX());
    }
    
    /**
     * <p>
     *     Returns some vector orthogonal to an R<sup>3</sup> vector.
     * </p>
     * <p>
     *     This method is guaranteed to return a non-zero vector unless the given vector is zero itself. In that case,
     *     zero will be returned.
     * </p>
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @param z the z-coordinate
     * @return a perpendicular vector
     */
    @SuppressWarnings("SuspiciousNameCombination")
    @NotNull
    public static Vector3 ortho(double x, double y, double z) {
        if (Spatium.isZero(x))
            return Vector3.fromXYZ(0, z, -y);
        else if (Spatium.isZero(y))
            return Vector3.fromXYZ(-z, 0, x);
        else
            return Vector3.fromXYZ(y, -x, 0);
    }
    
    /**
     * <p>
     *     Returns some vector orthogonal to an R<sup>3</sup> vector.
     * </p>
     * <p>
     *     This method is guaranteed to return a non-zero vector unless the given vector is zero itself. In that case,
     *     zero will be returned.
     * </p>
     *
     * @param v the vector
     * @return a perpendicular vector
     */
    @SuppressWarnings("SuspiciousNameCombination")
    @NotNull
    public static Vector3 ortho(Vector3 v) {
        return ortho(v.getX(), v.getY(), v.getZ());
    }
    
    // RANDOM VECTOR GENERATION
    
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
    
    // SUMS OF VECTORS
    
    /**
     * Returns the sum of two vectors.
     *
     * @param a the first vector
     * @param b the second vector
     * @return the sum of vectors
     */
    @NotNull
    public static Vector2 sum(Vector2 a, Vector2 b) {
        return Vector2.fromXY(a.getX()+b.getX(), a.getY()+b.getY());
    }
    
    /**
     * Returns <code>a + r*b</code>
     *
     * @param a the first vector
     * @param r the real multiplier of b
     * @param b the second vector
     * @return the sum of vectors
     */
    @NotNull
    public static Vector2 sum(Vector2 a, double r, Vector2 b) {
        return Vector2.fromXY(
            a.getX() + r*b.getX(),
            a.getY() + r*b.getY());
    }
    
    /**
     * Returns the sum of two vectors.
     *
     * @param a the first vector
     * @param b the second vector
     * @return the sum of vectors
     */
    @NotNull
    public static Vector3 sum(Vector3 a, Vector3 b) {
        return Vector3.fromXYZ(a.getX()+b.getX(), a.getY()+b.getY(), a.getZ()+b.getZ());
    }
    
    /**
     * Returns <code>a + r*b</code>
     *
     * @param a the first vector
     * @param r the real multiplier of b
     * @param b the second vector
     * @return the sum of vectors
     */
    @NotNull
    public static Vector3 sum(Vector3 a, double r, Vector3 b) {
        return Vector3.fromXYZ(
            a.getX() + r*b.getX(),
            a.getY() + r*b.getY(),
            a.getZ() + r*b.getZ());
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
    
}
