package net.grian.spatium.impl;

import net.grian.spatium.matrix.Matrix;
import net.grian.spatium.matrix.MatrixDimensionsException;
import net.grian.spatium.util.Strings;

import java.util.Arrays;

public class MatrixImpl implements Matrix {

    private final int rows, columns;
    private final double[] content;

    public MatrixImpl(int rows, int columns, double... content) {
        if (rows * columns != content.length)
            throw new MatrixDimensionsException("content must be "+rows+"x"+columns+" long");
        this.rows = rows;
        this.columns = columns;
        this.content = Arrays.copyOf(content, content.length);
    }

    public MatrixImpl(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.content = new double[rows * columns];
    }

    public MatrixImpl(MatrixImpl copyOf) {
        this(copyOf.rows, copyOf.columns, copyOf.content);
    }

    // GETTERS

    @Override
    public double get(int i, int j) {
        return content[i*columns + j];
    }

    private int indexOf(int i, int j) {
        return i*columns + j;
    }

    @Override
    public double[] getRow(int i) {
        validateRow(i);

        double[] result = new double[columns];
        for (int j = 0; j<columns; j++)
            result[j] = get(i, j);

        return result;
    }

    @Override
    public double[] getColumn(int j) {
        validateCol(j);

        double[] result = new double[rows];
        for (int i = 0; i<rows; i++)
            result[i] = get(i, j);

        return result;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public int getColumns() {
        return columns;
    }

    @Override
    public double getDeterminant() {
        if (rows != columns)
            throw new MatrixDimensionsException("matrix must be square matrix");
        return uncheckedDeterminant();
    }

    /**
     * Returns the determinant without checking whether the matrix is square.
     *
     * @return the determinant without square-matrix validation
     */
    private double uncheckedDeterminant() {
        if (rows == 1) return get(0, 0);
        if (rows == 2) return determinant2();
        if (rows == 3) return determinant3();
        return determinantRecursive();
    }

    /**
     * Special case formula for 2x2 matrices. (Using <a href="https://en.wikipedia.org/wiki/Cramer%27s_rule">Cramer's
     * Rule</a>)
     *
     * @return the determinant of the matrix
     */
    private double determinant2() {
        return get(0, 0)*get(1, 1) - get(0, 1)*get(1, 0);
    }

    /**
     * Special case formula for 3x3 matrices. (Using <a href="https://en.wikipedia.org/wiki/Cramer%27s_rule">Cramer's
     * Rule</a>)
     *
     * @return the determinant of the matrix
     */
    private double determinant3() {
        return
          get(0,0)*get(1,1)*get(2,2)
        + get(0,1)*get(1,2)*get(2,0)
        + get(0,2)*get(1,0)*get(2,1)
        - get(0,2)*get(1,1)*get(2,0)
        - get(0,0)*get(1,2)*get(2,1)
        - get(0,1)*get(1,0)*get(2,2);
    }

    private double determinantRecursive() {
        if (rows == 3) return determinant3();

        //Temporary matrix to paste the cofactors in to avoid constructing new matrix for every cofactor
        MatrixImpl canvas = new MatrixImpl(rows-1, rows-1);
        double result = 0;

        for (int i = 0; i<columns; i++) {
            pasteWithoutRowCol(canvas, i, 0);

            result += get(i, 0) * minusOnePow(i) * canvas.determinantRecursive();
        }

        return result;
    }

    @Override
    public MatrixImpl getCofactors() {
        if (rows != columns)
            throw new MatrixDimensionsException("matrix must be square matrix");
        if (rows == 1) return new MatrixImpl(1, 1, get(0, 0));
        if (rows == 2) return cofactors2();

        MatrixImpl result = new MatrixImpl(rows, columns);
        //Temporary matrix to paste the cofactors in to avoid constructing new matrix for every cofactor
        MatrixImpl canvas = new MatrixImpl(rows-1, columns-1);

        for (int i = 0; i<rows; i++) for (int j = 0; j<columns; j++) {
            pasteWithoutRowCol(canvas, i, j);
            double cofactor = minusOnePow(i+j) * canvas.uncheckedDeterminant();

            result.set(i, j, cofactor);
        }

        return result;
    }

    @Override
    public MatrixImpl getAdjugate() {
        MatrixImpl cofactors = getCofactors();
        cofactors.transpose();
        return cofactors;
    }

    @Override
    public MatrixImpl getInverse() {
        MatrixImpl adj = getAdjugate();
        //determinant can be obtained faster since adjugate already contains all necessary information
        double det = 0;
        for (int i = 0; i<rows; i++)
            det += adj.get(i, 0) * get(0, i);

        adj.scale(1 / det);
        return adj;
    }

    private MatrixImpl cofactors2() {
        return new MatrixImpl(2, 2,
                 get(1, 1), -get(1, 0),
                -get(0, 1),  get(0, 0));
    }

    /**
     * Pastes new values into the matrix for each (i, j) where:
     * <blockquote>
     *     <code>i == row || j == col</code>
     * </blockquote>
     *
     * @param matrix the matrix to contain the values
     * @param row the row index
     * @param col the column index
     */
    @SuppressWarnings("ConstantConditions")
    private void pasteWithoutRowCol(MatrixImpl matrix, int row, int col) {
        final int m = rows-1;

        int x = 0, y = 0;
        for (int i = 0; i<rows; i++) for (int j = 0; j<columns; j++) {
            if (i == row || j == col) continue;

            matrix.set(x, y, get(i, j));

            if(++x == m) {
                y++;
                x = 0;
            }
        }
    }

    /**
     * Returns -1 to an integer power.
     *
     * @param power the power
     * @return -1 to an integer power
     */
    private static int minusOnePow(int power) {
        return power%2 == 0 ? 1 : -1;
    }

    // SETTERS

    @Override
    public void set(int i, int j, double value) {
        content[i*columns + j] = value;
    }

    @Override
    public void swap(int i0, int j0, int i1, int j1) {
        uswap(i0, j0, i1, j1);
    }

    private void uswap(int i0, int j0, int i1, int j1) {
        final int from = indexOf(i0, j0), to = indexOf(i1, j1);
        
        double swap = content[to];
        content[to] = content[from];
        content[from] = swap;
    }

    @Override
    public void swapRows(int i0, int i1) {
        validateRow(i0);
        validateRow(i1);

        for (int j = 0; j<columns; j++) {
            final int from = indexOf(i0, j), to = indexOf(i1, j);

            double swap = content[to];
            content[to] = content[from];
            content[from] = swap;
        }
    }

    @Override
    public void swapColumns(int j0, int j1) {
        validateCol(j0);
        validateCol(j1);

        for (int i = 0; i<rows; i++) {
            final int from = indexOf(i, j0), to = indexOf(i, j1);

            double swap = content[to];
            content[to] = content[from];
            content[from] = swap;
        }
    }

    @Override
    public void transpose() {
        for (int i = 1; i<rows; i++)
            for (int j = 0; j<=i; j++) {
                uswap(i, j, j, i);
            }
    }

    @Override
    public void scale(double factor) {
        for (int k = 0; k<content.length; k++)
            content[k] *= factor;
    }

    // MISC

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Matrix && equals((Matrix) obj);
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public MatrixImpl clone() {
        return new MatrixImpl(this);
    }

    @Override
    public String toString() {
        String[] rowArray = new String[rows];
        for (int i = 0; i < rows; i++) {
            rowArray[i] = "[" + Strings.join(", ", getRow(i)) + "]";
        }
        return "[" + Strings.join(", ", rowArray) + "]";
    }

    // VALIDATE

    private void validateCol(int col) {
        if (col < 0)
            throw new IndexOutOfBoundsException("j="+col+" < 0");
        if (col >= columns)
            throw new IndexOutOfBoundsException("j="+col+" >= "+columns+" columns");
    }

    private void validateRow(int row) {
        if (row < 0)
            throw new IndexOutOfBoundsException("i="+row+" < 0");
        if (row >= rows)
            throw new IndexOutOfBoundsException("i="+row+" >= "+rows+" rows");
    }

}
