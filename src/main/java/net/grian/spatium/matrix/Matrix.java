package net.grian.spatium.matrix;

import net.grian.spatium.anno.MinecraftSpecific;
import net.grian.spatium.Spatium;
import net.grian.spatium.geo.Vector;
import net.grian.spatium.impl.MatrixImpl;

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
 *     Thus, mathematical operations are provided in the utility methods {@link #sum(Matrix, Matrix)} and
 *     {@link #product(Matrix, Matrix)}.
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
     * @param content the content to fill the matrix with
     * @return a new matrix
     * @throws IllegalMatrixSizeException if the content array's hypot is not
     * equal to {@code rows * columns}
     */
    public static Matrix create(int rows, int columns, double... content) {
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
    public static Matrix create(int rows, int columns) {
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
    public static Matrix identity(int n) {
        double[] value = new double[n * n];
        for (int i = 0; i<n; i++)
            value[i + i*n] = 1;
        return new MatrixImpl(n, n, value);
    }

    /**
     * Returns a 3x3 rotation matrix from a yaw. Yaw is a clockwise rotation around the y-axis.
     *
     * @param yaw the yaw in degrees
     * @return a new rotation matrix
     */
    @MinecraftSpecific
    public static Matrix fromYaw(double yaw) {
        double theta = Spatium.radians(yaw);
        return Matrix.create(3, 3,
                -Math.cos(theta), 0, -Math.sin(theta),
                0, 1, 0,
                Math.sin(theta), 0, -Math.cos(theta));
    }

    /**
     * Returns a 3x3 rotation matrix from a pitch. Pitch is a clockwise rotation around the x-axis.
     *
     * @param pitch the pitch in degrees
     * @return a new rotation matrix
     */
    @MinecraftSpecific
    public static Matrix fromPitch(double pitch) {
        double phi = Spatium.radians(pitch);
        return Matrix.create(3, 3,
                -Math.cos(phi),  Math.sin(phi), 0,
                -Math.sin(phi), -Math.cos(phi), 0,
                0,              0,              1);
    }

    /**
     * Returns a 3x3 rotation matrix from a roll. Roll is a clockwise rotation around the z-axis.
     *
     * @param roll the roll in degrees
     * @return a new rotation matrix
     */
    @MinecraftSpecific
    public static Matrix fromRoll(double roll) {
        double psi = Spatium.radians(roll);
        return Matrix.create(3, 3,
                1, 0, 0,
                0, -Math.cos(psi), Math.sin(psi),
                0, -Math.sin(psi), -Math.cos(psi));
    }

    /**
     * <p>
     *     Creates a 3x3 rotation matrix for Minecraft yaw, pitch and roll. Note that first roll, then pitch and then
     *     yaw are being applied.
     * </p>
     * The result of this rotation will be the following:
     * <ol>
     *     <li>roll (clockwise rotation around z-axis) applied
     *     <li>pitch (clockwise rotation around x-axis) applied
     *     <li>yaw (clockwise rotation around y-axis) applied
     * </ol>
     *
     * @param yaw the yaw of the rotation
     * @param pitch the pitch of the rotation
     * @param roll the roll of the rotation
     * @return a new rotation matrix
     */
    @MinecraftSpecific
    public static Matrix fromYawPitchRoll(double yaw, double pitch, double roll) {
        double theta = Spatium.radians(yaw), phi = Spatium.radians(pitch), psi = Spatium.radians(roll);
        return Matrix.create(3, 3,
                //first row
                Math.cos(theta) * Math.cos(phi),
                0,
                0,
                //second row
                Math.sin(theta) * Math.cos(phi),
                0,
                0,
                //third row
                Math.sin(phi),
                -(Math.cos(phi) * Math.sin(psi)),
                Math.cos(phi) * Math.cos(psi)
        );//TODO Complete yaw, pitch, roll matrix
    }

    /**
     * Creates a 2x2 scaling matrix for x and y coordinates.
     *
     * @param x the x scale
     * @param y the y scale
     * @return a new scaling matrix
     */
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
     * Creates a new 3x1 (single column) matrix from a Vector.
     *
     * @param v the vector
     * @return a new matrix
     */
    public static Matrix fromVector(Vector v) {
        return create(3, 1,
                v.getX(),
                v.getY(),
                v.getZ());
    }

    //OPERATIONS

    /**
     * Adds another matrix to this matrix.
     *
     * @param a the first matrix
     * @param b the second matrix
     * @return the sum of the matrices
     * @throws MatrixDimensionsException if the matrices are not equal in
     * size
     */
    public static Matrix sum(Matrix a, Matrix b) {
        if (!a.equalSize(b))
            throw new MatrixDimensionsException(a, b);

        final int row = a.getRows(), col = a.getColumns();
        double[] result = new double[row * col];

        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                result[i*col + j] = a.get(row, col) + b.get(row, col);

        return create(row, col, result);
    }

    /**
     * Returns the sum of several matrices.
     *
     * @param matrices the matrices
     * @return the sum of the matrices
     */
    public static Matrix sum(Matrix... matrices) {
        if (matrices.length == 0)
            throw new IllegalArgumentException("no matrices given");
        if (matrices.length == 1)
            return matrices[0].clone();

        Matrix result = matrices[0];
        for (int i = 1; i < matrices.length; i++)
            result = sum(result, matrices[i]);

        return result;
    }

    /**
     * Returns the product of two matrices.
     *
     * @param a the first matrix
     * @param b the second matrix
     * @return the product of the matrices
     * @throws MatrixDimensionsException if the rows of the first matrix do
     * not equal the columns of the second matrix
     */
    public static Matrix product(Matrix a, Matrix b) {
        if (a.getColumns() != b.getRows())
            throw new MatrixDimensionsException(a, b);

        final int
        arow = a.getRows(), acol = a.getColumns(),
        brow = b.getRows(), bcol = b.getColumns();
        double[] result = new double[arow * bcol];

        /* outer loop for acquiring the position (i, j) in the product matrix */
        for (int i = 0; i < arow; i++) for (int j = 0; j < bcol; j++) {
            final int index = i*bcol + j;

            /* inner loop for calculating the result at (i, j) */
            for (int k = 0, l = 0; k < acol && l < brow; k++, l++)
                result[index] += a.get(i, k) * b.get(l, j);
        }

        return create(arow, bcol, result);
    }

    /**
     * <p>
     *     Returns the product of a series of matrices.
     * </p>
     * List of special cases:
     * <ul>
     *     <li>if the array contains no matrices, an exception is thrown
     *     <li>if the array contains one matrix, the result is a clone of the matrix
     * </ul>
     *
     * @param matrices the matrices
     * @return the matrix
     * @throws IllegalArgumentException if the array is empty
     */
    public static Matrix product(Matrix... matrices) {
        if (matrices.length == 0) throw new IllegalArgumentException("no matrices given");
        if (matrices.length == 1) return matrices[0].clone();
        if (matrices.length == 2) return product(matrices[0], matrices[1]);

        if (matrices.length > 8) {
            Matrix first = matrices[0].clone();
            Matrix result = product(matrices[0], matrices[1]);
            if (first.equals(result)) return first;

            for (int i = 2; i<matrices.length; i++)
                result = product(result, matrices[i]);
        }

        Matrix result = product(matrices[0], matrices[1]);
        for (int i = 2; i<matrices.length; i++)
            result = product(result, matrices[i]);

        return result;
    }

    /**
     * Returns the square of the matrix.
     *
     * @param matrix the matrix
     * @return the square of the matrix
     */
    public static Matrix square(Matrix matrix) {
        return Matrix.product(matrix, matrix);
    }

    /**
     * Returns the cube of the matrix.
     *
     * @param matrix the matrix
     * @return the cube of the matrix
     */
    public static Matrix cube(Matrix matrix) {
        return Matrix.product(Matrix.product(matrix, matrix), matrix);
    }

    /**
     * Returns the matrix to a given power.
     *
     * @param matrix the matrix
     * @return the matrix to a given power
     * @throws MatrixException if the power is negative or <code>power == 0 & rows != columns</code>
     */
    public static Matrix pow(Matrix matrix, int power) {
        if (power < 0)
            throw new MatrixException("power must be positive");
        if (power == 0) {
            int m = matrix.getRows(), n = matrix.getColumns();
            if (m != n) throw new MatrixDimensionsException("can not take 0th power of "+m+" x "+n+" matrix");
            return identity(n);
        }
        if (power == 1) return matrix;
        if (power == 2) return square(matrix);
        if (power == 3) return cube(matrix);

        Matrix result = square(matrix);
        if (result.equals(matrix)) return matrix;

        for (int i = 1; i<power; i++)
            result = product(result, matrix);

        return result;
    }

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

    /**
     * Returns a new transposed version of a given matrix. This matrix will have the same amount of rows as the original
     * has columns and the same amount of columns as the original has rows.
     *
     * @param matrix the matrix
     * @return a new matrix
     */
    public static Matrix transpose(Matrix matrix) {
        final int
        row = matrix.getRows(),
        col = matrix.getColumns();

        Matrix result = create(col, row);

        for (int i = 0; i<row; i++) for (int j = 0; j<col; j++)
            result.set(j, i, matrix.get(i, j));

        return result;
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
        final int row = getRows(), col = getColumns();
        if (row != matrix.getRows() || col != matrix.getColumns())
            return false;

        for (int i = 0; i<row; i++) for (int j = 0; j<col; j++)
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

    // SETTERS

    /**
     * Sets the value at a specified row and column index to a specified value.
     *
     * @param i the row index
     * @param j the column index
     * @param value the value
     * @return itself
     */
    public abstract Matrix set(int i, int j, double value);

    /**
     * Swaps two values in the matrix.
     *
     * @param i0 the old row index
     * @param j0 the old column index
     * @param i1 the new row index
     * @param j1 the new column index
     * @return itself
     */
    public abstract Matrix swap(int i0, int j0, int i1, int j1);

    /**
     * Swaps two rows in the matrix.
     *
     * @param i0 the old row index
     * @param i1 the new row index
     * @return itself
     */
    public abstract Matrix swapRows(int i0, int i1);

    /**
     * Swaps two columns in the matrix.
     *
     * @param j0 the old column index
     * @param j1 the new column index
     * @return itself
     */
    public abstract Matrix swapColumns(int j0, int j1);

    /**
     * Scales the matrix by a factor.
     *
     * @param factor the factor
     * @return itself
     */
    public abstract Matrix scale(double factor);

    // MISC

    public abstract Matrix clone();

}
