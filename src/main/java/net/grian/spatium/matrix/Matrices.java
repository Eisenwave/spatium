package net.grian.spatium.matrix;

import net.grian.spatium.geo3.Vector3;

public final class Matrices {
    
    private Matrices() {}
    
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
        if (!a.equalSize(b)) throw new MatrixDimensionsException(a, b);

        final int row = a.getRows(), col = a.getColumns();
        Matrix result = Matrix.create(row, col);

        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                result.set(i, j, a.get(i, j) + b.get(i, j));

        return result;
    }
    
    /**
     * Adds another matrix to this matrix.
     *
     * @param a the first matrix
     * @param b the second matrix
     * @return the sum of the matrices
     * @throws MatrixDimensionsException if the matrices are not equal in
     * size
     */
    public static Matrix difference(Matrix a, Matrix b) {
        if (!a.equalSize(b)) throw new MatrixDimensionsException(a, b);
        
        final int row = a.getRows(), col = a.getColumns();
        Matrix result = Matrix.create(row, col);
        
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                result.set(i, j, a.get(i, j) - b.get(i, j));
        
        return result;
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
        if (matrices.length == 2)
            return sum(matrices[0], matrices[1]);

        Matrix result = matrices[0];
        for (int i = 1; i < matrices.length; i++)
            result = sum(result, matrices[i]);

        return result;
    }
    
    public static Vector3 product(Matrix a, double x, double y, double z) {
        if (a.getRows() != 3 || a.getColumns() != 3)
            throw new MatrixDimensionsException("matrix must be 3x3");

        return Vector3.fromXYZ(
            a.get(0,0)*x + a.get(0,1)*y + a.get(0,2)*z,
            a.get(1,0)*x + a.get(1,1)*y + a.get(1,2)*z,
            a.get(2,0)*x + a.get(2,1)*z + a.get(2,2)*z);
    }
    
    public static Vector3 product(Matrix a, Vector3 column) {
        return product(a, column.getX(), column.getY(), column.getZ());
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

        return Matrix.create(arow, bcol, result);
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
        return product(matrix, matrix);
    }
    
    /**
     * Returns the cube of the matrix.
     *
     * @param matrix the matrix
     * @return the cube of the matrix
     */
    public static Matrix cube(Matrix matrix) {
        return product(square(matrix), matrix);
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
            return Matrix.identity(n);
        }
        if (power == 1) return matrix;
        if (power == 2) return square(matrix);
        if (power == 3) return cube(matrix);

        Matrix result = square(matrix);
        if (result.equals(matrix)) return matrix;

        for (int i = 2; i<power; i++)
            result = product(result, matrix);

        return result;
    }
    
}
