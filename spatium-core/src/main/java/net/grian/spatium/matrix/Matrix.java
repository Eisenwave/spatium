package net.grian.spatium.matrix;

import eisenwave.spatium.util.Spatium;
import net.grian.spatium.geo3.Vector3;
import net.grian.spatium.impl.Matrix2Impl;
import net.grian.spatium.impl.MatrixImpl;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * <p>
 *     A two-dimensional matrix of numbers. This matrix, as all classes of Spatium is mutable, although in this case
 *     only to an extent.
 * </p>
 * <p>
 *     The content of the matrix can be modified using {@link #set(int, int, double)} and retrieved using
 *     {@link #get(int, int)}, however the size of the matrix is final and can not be changed.
 * </p>
 * <p>
 *     Thus, mathematical operations are provided in the utility methods {@link Matrices#sum(Matrix, Matrix)} and
 *     {@link Matrices#product(Matrix, Matrix)}.
 * </p>
 */
public interface Matrix {

    //"CONSTRUCTORS"

    /**
     * Creates a new matrix with specified content. Note that the input content
     * is being interpreted depending on the given rows and columns.
     *
     * <br><br>For example, a 3x3 matrix will be filled in the following way:
     * <blockquote>
     *   {@code [(0,0) (0,1) (0,2) (1,0) (1,1) (1,2) (2,0) (2,1) (2,2)]}
     *   <br>when the indices {@code (i,j)} represent the row index {@code (i)}
     *   and the column index {@code (j)}.
     * </blockquote>
     * Should the provided array not meet the size requirements
     * {@code hypot = rows * columns}, the matrix can not be created.
     *
     * @param rows the amount of rows of the matrix (> 0)
     * @param columns the amount of columns of the matrix (> 0)
     * @param content the content to paste the matrix with
     * @return a new matrix
     * @throws IllegalMatrixSizeException if the content array's hypot is not
     * equal to {@code rows * columns}
     */
    @NotNull
    @Contract(pure = true)
    public static Matrix create(int rows, int columns, double... content) {
        if (rows == 2 && columns == 2)
            return new Matrix2Impl(content[0], content[1], content[2], content[3]);

        return new MatrixImpl(rows, columns, content);
    }

    /**
     * Creates a new empty matrix.
     *
     * @param rows the amount of rows of the matrix (> 0)
     * @param columns the amount of columns of the matrix (> 0)
     * @return a new matrix
     * @throws IllegalMatrixSizeException if either the amount of rows or the
     * amount of columns <= 0
     */
    @NotNull
    @Contract(pure = true)
    public static Matrix create(int rows, int columns) {
        if (rows == 2 && columns == 2) return new Matrix2Impl();

        return new MatrixImpl(rows, columns);
    }

    /**
     * Creates a new n*n identity matrix.
     *
     * @param n the amount of rows and columns of the matrix (> 0)
     * @return a new matrix
     * @throws IllegalMatrixSizeException if either the amount of rows or the
     * amount of columns <= 0
     */
    @NotNull
    @Contract(pure = true)
    public static Matrix identity(int n) {
        if (n == 2) return new Matrix2Impl(1, 0, 0, 1);

        double[] value = new double[n * n];
        for (int i = 0; i<n; i++)
            value[i + i*n] = 1;
        return new MatrixImpl(n, n, value);
    }

    /**
     * <p>
     *     Returns a 3x3 rotation matrix representing a counter-clockwise rotation around the x-axis.
     * </p>
     * <p>
     *     For easier understanding, see: <a href="https://en.wikipedia.org/wiki/Right-hand_rule">Right Hand Rule</a>.
     * </p>
     *
     * @param angle the angle in radians
     * @return a new rotation matrix
     */
    @NotNull
    public static Matrix fromRotX(double angle) {
        double sin = Math.sin(angle), cos = Math.cos(angle);
        return Matrix.create(3, 3,
            1,   0,    0,
            0, cos, -sin,
            0, sin,  cos);
    }

    /**
     * <p>
     *     Returns a 3x3 rotation matrix representing a counter-clockwise rotation around the y-axis.
     * </p>
     * <p>
     *     For easier understanding, see: <a href="https://en.wikipedia.org/wiki/Right-hand_rule">Right Hand Rule</a>.
     * </p>
     *
     * @param angle the angle in radians
     * @return a new rotation matrix
     */
    @NotNull
    public static Matrix fromRotY(double angle) {
        double sin = Math.sin(angle), cos = Math.cos(angle);
        return Matrix.create(3, 3,
            cos,  0, sin,
            0,    1,    0,
            -sin, 0, cos);
    }

    /**
     * <p>
     *     Returns a 3x3 rotation matrix representing a counter-clockwise rotation around the z-axis.
     * </p>
     * <p>
     *     For easier understanding, see: <a href="https://en.wikipedia.org/wiki/Right-hand_rule">Right Hand Rule</a>.
     * </p>
     *
     * @param angle the angle in radians
     * @return a new rotation matrix
     */
    @NotNull
    public static Matrix fromRotZ(double angle) {
        double sin = Math.sin(angle), cos = Math.cos(angle);
        return Matrix.create(3, 3,
            cos, -sin,  0,
            sin,  cos,  0,
            0,      0,  1);
    }
    
    /**
     * Returns a 2x2 counter-clockwise rotation matrix with given angle.
     *
     * @param angle the angle in radians
     * @return a new rotation matrix
     */
    @NotNull
    public static Matrix fromRot2(double angle) {
        double sin = Math.sin(angle), cos = Math.cos(angle);
        
        return new Matrix2Impl(cos, -sin, sin, cos);
    }
    
    /**
     * <p>
     *     Returns a new 3x3 rotation matrix representing a counter-clockwise rotation around a given axis.
     * </p>
     * <p>
     *     This is done using the <a href="https://en.wikipedia.org/wiki/Rodrigues%27_rotation_formula">Rodrigues
     *     rotation formula</a>.
     * </p>
     *
     * @param x the axis x
     * @param y the axis y
     * @param z the axis z
     * @param angle the angle in radians
     * @return a new rotation matrix
     */
    @NotNull
    public static Matrix fromRot(double x, double y, double z, double angle) {
        //cross product matrix of vector = (x, y, z), so that: K * v = K x v (for any vector v)
        Matrix k = Matrix.create(3, 3,
             0, -z,  y,
             z,  0, -x,
            -y,  x,  0);
        
        //R = I + sin(a)K + (1-cos(a))K^2
        Matrix k2 = Matrices.square(k);
        k.scale(Math.sin(angle));
        k2.scale(1 - Math.cos(angle));
        
        return Matrices.sum(identity(3), k, k2);
    }
    
    /**
     * <p>
     *     Returns a new 3x3 rotation matrix representing a counter-clockwise rotation around a given axis.
     * </p>
     * <p>
     *     This is done using the <a href="https://en.wikipedia.org/wiki/Rodrigues%27_rotation_formula">Rodrigues
     *     rotation formula</a>.
     * </p>
     *
     * @param axis the rotation axis
     * @param angle the angle in radians
     * @return a new rotation matrix
     */
    @NotNull
    public static Matrix fromRot(Vector3 axis, double angle) {
        return fromRot(axis.getX(), axis.getY(), axis.getZ(), angle);
    }

    /* {{1,0,0},{0,x,-y},{0,y,x}} * {{x,0,y},{0,1,0},{-y,0,x}} * {{x,-y,0},{y,x,0},{0,0,1}} (x=cos, y=sin) */

    /**
     * Creates a 2x2 scaling matrix for x and y coordinates.
     *
     * @param x the x scale
     * @param y the y scale
     * @return a new scaling matrix
     */
    @NotNull
    @Contract(pure = true)
    public static Matrix fromScale(double x, double y) {
        return create(2, 2,
            x, 0,
            0, y);
    }

    /**
     * Creates a 3x3 scaling matrix for x, y and z coordinates.
     *
     * @param x the scale on the x-axis
     * @param y the scale on the y-axis
     * @param z the scale on the z-axis
     * @return a new scaling matrix
     */
    @NotNull
    @Contract(pure = true)
    public static Matrix fromScale(double x, double y, double z) {
        return create(3, 3,
            x, 0, 0,
            0, y, 0,
            0, 0, z);
    }

    /**
     * Creates a n x n scaling matrix for varying coordinates.
     *
     * @param coords the coordinates
     * @return a new scaling matrix
     */
    @NotNull
    public static Matrix fromScale(double... coords) {
        if (coords.length == 0) throw new IllegalMatrixSizeException("no coordinates given");

        final int size = coords.length;
        double[] content = new double[size * size];

        //noinspection ManualArrayCopy
        for (int i = 0; i<size; i++)
            content[i * size + i] = coords[i];

        return Matrix.create(size, size, content);
    }

    /**
     * Creates a new 3x1 (single column) matrix from a Vector3.
     *
     * @param v the vector
     * @return a new matrix
     */
    @NotNull
    public static Matrix fromVector(Vector3 v) {
        return create(3, 1,
            v.getX(),
            v.getY(),
            v.getZ());
    }

    //OPERATIONS

    /*
    public static Matrix product(double x, double y, double z, Matrix a) {
        return Matrix.fromPoints(1, 3,
                a.get(0,0)*x + a.get(1,0)*y + a.get(2,0)*z,
                a.get(0,1)*x + a.get(1,1)*y + a.get(2,1)*z,
                a.get(0,2)*x + a.get(1,2)*z + a.get(2,2)*z);
    }
    */
    
    /**
     * <p>
     *     Runs the aho algorithm on a given adjacency matrix with given accumulative and path-operations.
     * </p>
     * <p>
     *     Note the following requirements for the operations: <ul>
     *         <li>the accumulative operation must be commutative (order irrelevant)</li>
     *         <li>both operations must be associative</li>
     *     </ul>
     * </p>
     *
     * @param matrix the matrix
     * @param accuOp the accumulative operation
     * @param pathOp the path operation
     */
    /* @Contract("null,null,null -> fail")
    public static void aho(Matrix matrix, Double2DoubleFunction accuOp, Double2DoubleFunction pathOp) {
        final int n = matrix.getRows();
        if (n != matrix.getColumns())
            throw new MatrixDimensionsException("matrix must be square");
        
        for (int k = 0; k<n; k++) {
            for (int i = 0; i<n; i++) for (int j = 0; j<n; j++) {
                if (i == k || j == k) continue;
                double val = pathOp.apply(matrix.get(i, k), matrix.get(k, j));
                double set = accuOp.apply(matrix.get(i, j), val);
                matrix.set(i, j, set);
            }
        }
    } */
    
    /**
     * Runs the Floyd-Warshall algorithm on a given adjacency matrix.
     *
     * @param matrix the matrix
     */
    /* @Contract("null -> fail")
    public static void floydWarshall(Matrix matrix) {
        aho(matrix, PrimMath::min, (a, b) -> a + b);
    } */

    /**
     * Increases or decreases the size of the matrix by a specified amount
     * of rows or columns. When size is being increased, all the new rows and
     * columns are filled with zero.
     *
     * @param matrix the matrix to expand
     * @param row the amount of rows to expand by
     * @param col the amount of columns to expand by
     * @return a new matrix
     * @throws IllegalMatrixSizeException if the resulting matrix is smaller
     * than 1 in one dimension
     */
    public static Matrix bigger(Matrix matrix, int row, int col) {
        if (row == 0 && col == 0) return matrix.clone();

        final int
            arow = matrix.getRows(),
            acol = matrix.getColumns(),
            brow = arow + row,
            bcol = acol + col;

        double[] content = new double[brow * bcol];

        for (int i = 0; i < arow && i < brow; i++) for (int j = 0; j < acol && i < bcol; j++) {
            final int index = i * bcol + j;
            content[index] = matrix.get(i, j);
        }

        return create(brow, bcol, content);
    }

    // GETTERS

    /**
     * Returns the value at the specified row and column indices.
     *
     * @param row the row index
     * @param col the column index
     * @return the value at the specified row and column
     */
    public abstract double get(int row, int col);

    /**
     * Returns an array containing all values at the specified row index.
     *
     * @param row the row index
     * @return all values at the specified row index
     */
    public abstract double[] getRow(int row);

    /**
     * Returns an array containing all values at the specified column index.
     *
     * @param col the column index
     * @return all values at the specified column index
     */
    public abstract double[] getColumn(int col);

    /**
     * Returns the amount of rows of this matrix.
     * @return the amount of rows
     */
    public abstract int getRows();

    /**
     * Returns the amount of columns of this matrix.
     * @return the amount of columns
     */
    public abstract int getColumns();

    /**
     * Returns the determinant of this matrix.
     *
     * @return the determinant of this matrix
     */
    public abstract double getDeterminant();
    
    /**
     * Returns the trace of this matrix.
     *
     * @return the trace of this matrix
     */
    public default double getTrace() {
        final int n = getRows();
        if (n != getColumns()) throw new MatrixDimensionsException("matrix must be square");
        
        int trace = 0;
        for (int i = 0; i < n; i++)
            trace += get(i, i);
        
        return trace;
    }

    /**
     * Returns a matrix of equal size containing the cofactors of this matrix.
     *
     * @return the cofactors of this matrix
     */
    public abstract Matrix getCofactors();

    /**
     * Returns the adjugate matrix of this matrix.
     *
     * @return the adjugate matrix
     */
    public abstract Matrix getAdjugate();

    /**
     * Returns the inverse matrix of this matrix.
     *
     * @return the adjugate matrix
     */
    public abstract Matrix getInverse();

    // CHECKERS

    /**
     * <p>
     *     Returns whether this matrix is equal to another matrix.
     * </p>
     * <p>
     *     Two matrices A<sup>m*n</sup>, B<sup>m*n</sup> are equal if:
     * </p>
     * <blockquote>
     *     <code>A<sub>ij</sub> == B<sub>ij</sub> (0 <= i < n) (0 <= j < n)</code>
     * </blockquote>
     *
     * @param matrix the matrix
     * @return whether this matrix is equal to another matrix
     */
    public default boolean equals(Matrix matrix) {
        final int rows = getRows(), cols = getColumns();
        if (rows != matrix.getRows() || cols != matrix.getColumns())
            return false;

        for (int i = 0; i<rows; i++) for (int j = 0; j<cols; j++)
            if (!Spatium.equals(this.get(i, j), matrix.get(i, j)))
                return false;

        return true;
    }

    /**
     * Returns whether this and the compared matrix are equal in size.
     *
     * @param matrix the matrix
     * @return whether this and the compared matrix are equal in size
     */
    public default boolean equalSize(Matrix matrix) {
        return
            this.getRows()    == matrix.getRows() &&
            this.getColumns() == matrix.getColumns();
    }
    
    /**
     * Returns whether this matrix is a square matrix.
     *
     * @return whether this matrix is a square matrix
     */
    public default boolean isSquare() {
        return getRows() == getColumns();
    }
    
    /**
     * Returns whether this matrix is invertible.
     *
     * @return whether this matrix is invertible
     */
    public default boolean isInvertible() {
        try {
            return !Spatium.isZero(getDeterminant());
        } catch (MatrixDimensionsException ex) {
            return false;
        }
    }
    
    /**
     * Tests whether this matrix has a given Eigenvector.
     *
     * @param v the vector
     * @return whether v is an eigenvector
     */
    public default boolean hasEigenvector(Vector3 v) {
        return Matrices.product(this, v).isMultipleOf(v);
    }
    
    /**
     * Tests whether this matrix has a given Eigenvalue.
     *
     * @param lambda the eigenvalue
     * @return whether lambda is an eigenvalue
     */
    public default boolean hasEigenvalue(double lambda) {
        if (!isSquare()) throw new MatrixDimensionsException("matrix must be square");
        
        int n = getRows();
        Matrix subtrahend = identity(n);
        subtrahend.scale(-lambda);
        
        double det = Matrices.sum(this, subtrahend).getDeterminant();
        return !Spatium.isZero(det);
    }

    // SETTERS

    /**
     * Sets the value at a specified row and column index to a specified value.
     *
     * @param i the row index
     * @param j the column index
     * @param value the value
     */
    public abstract void set(int i, int j, double value);

    /**
     * Swaps two values in the matrix.
     *
     * @param i0 the old row index
     * @param j0 the old column index
     * @param i1 the new row index
     * @param j1 the new column index
     */
    public abstract void swap(int i0, int j0, int i1, int j1);

    /**
     * Swaps two rows in the matrix.
     *
     * @param i0 the old row index
     * @param i1 the new row index
     */
    public abstract void swapRows(int i0, int i1);

    /**
     * Swaps two columns in the matrix.
     *
     * @param j0 the old column index
     * @param j1 the new column index
     */
    public abstract void swapColumns(int j0, int j1);

    /**
     * <p>
     *     Transposes the matrix. For a Matrix <code>A\u2208\u211D<sup>n*n</sup></code> the transposed Matrix
     *     <code>A<sup>T</sup></code> at position <code>i,j < n</code> is defined as:
     *     <blockquote>
     *         <code>A<sup>T</sup><sub>ij</sub> = A<sub>ji</sub></code>
     *     </blockquote>
     * </p>
     * <p>
     *     Note that this operation mutates this Matrix object. Since the dimensions of Matrix objects are fixed,
     *     this can not be used to transpose a non-square Matrix.
     * </p>
     * <p>
     *     <b>To transpose non-square matrices, {@link Matrices#transpose(Matrix)}.</b>
     * </p>
     *
     * @throws MatrixDimensionsException if this matrix is non-square
     */
    public default void transpose() {
        int n = getRows();
        if (n != getColumns()) throw new MatrixDimensionsException("use Matrices::transpose for non-square matrices");
        
        for (int i = 1; i<n; i++)
            for (int j = 0; j<i; j++)
                swap(i, j, j, i);
    }

    /**
     * Scales the matrix by a factor. This is equivalent to multiplying every entry in the matrix with the factor.
     *
     * @param factor the factor
     */
    public abstract void scale(double factor);

    // MISC

    public abstract Matrix clone();

}
